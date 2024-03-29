package apt.processors;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import apt.annotations.Future;
import apt.annotations.Task;

import java.util.Set;

import spoon.reflect.factory.Factory;
import spoon.reflect.code.CtArrayAccess;
import spoon.reflect.code.CtBinaryOperator;
import spoon.reflect.code.CtBlock;
import spoon.reflect.code.CtCodeSnippetExpression;
import spoon.reflect.code.CtExpression;
import spoon.reflect.code.CtInvocation;
import spoon.reflect.code.CtLocalVariable;
import spoon.reflect.code.CtStatement;
import spoon.reflect.code.CtUnaryOperator;
import spoon.reflect.code.CtVariableAccess;
import spoon.reflect.declaration.CtAnnotation;
import spoon.reflect.declaration.CtExecutable;
import spoon.reflect.declaration.CtVariable;
import spoon.reflect.reference.CtExecutableReference;
import spoon.reflect.reference.CtTypeReference;
import spoon.reflect.reference.CtVariableReference;
import spoon.support.reflect.code.CtInvocationImpl;

/**
 * This annotation processor processes the <code>Future</code> annotations that appear
 * at the declaration of a collection (i.e., List, Set, Map, Collection for other
 * types), in order to obtain collection wrappers that can contain both variables and 
 * future variables.</br>
 * For example: <b>myList</b> is a <code>List</code> that calls on <code>ParaTask</code>
 * for a collection wrapper. The object can either add future variables, or make direct
 * calls on <b>only one</b> method (e.g., <code>myList.add(foo(a)</code>). That method
 * <b>MUST</b> be annotated with the <code>Task</code> annotation. If <code>'myList'</code>
 * invokes a method (e.g., <code>add</code>), for which the argument is a statement and 
 * not an invocation expression (e.g., <code>myList.add(foo(2) + foo(3))</code>), the operation
 * is performed sequentially iff the methods are not annotated with <code>Task</code>. However,
 * if a method is annotated with <code>Task</code> it is executed asynchronously, but the 
 * invocation blocks until the result from that asynchronous task is back. That is, the 
 * corresponding future variable will not be added to the container, rather the container
 * waits until the result from the asynchronous task is back.  
 * 
 * @author Mostafa Mehrabi
 * @since  2016
 */
public class HybridCollectionProcessor extends AptAbstractFutureProcessor {
	
	private List<CtVariableAccess<?>> variableAccessArgumentsToBeProcessed = null;
	private List<CtInvocation<?>> potentialAsynchronousInvocations = null;
	private Map<CtStatement, List<CtInvocation<?>>> invocationsToBeProcessed = null;
	protected Future  thisFutureAnnotation = null;
	private boolean insideCollectionStatement = false;
	private int     encounteredInvocationArguments = 0;
	private int     newLocalVariableIndex = 0;
	protected CtTypeReference<?> thisCollectionGenericType = null;
	
	public HybridCollectionProcessor(Factory factory, Future future){
		thisFactory = factory;
		thisFutureAnnotation = future;
	}
		
	public HybridCollectionProcessor(Factory factory, Future future, CtLocalVariable<?> annotatedElement){
		this(factory, future);
		thisAnnotatedLocalElement = annotatedElement;
		thisElementName = thisAnnotatedLocalElement.getSimpleName();
		thisElementType = thisAnnotatedLocalElement.getType();
	}

	@Override
	public void process() {
		if(!validate())
			return;
		
		inspectElement();
		findCollectionInvocationArguments();
		modifySourceCode();
	}
	
	private void inspectElement(){		
		String thisStatement = getElementString();
		String collectionType = thisStatement.substring(thisStatement.indexOf("<")+1, thisStatement.indexOf(">"));
		collectionType = APTUtils.getType(collectionType);
		thisCollectionGenericType = thisFactory.Core().createTypeReference();
		thisCollectionGenericType.setSimpleName(collectionType);
	}
	
	protected String getDefaultExpression(){
		return thisAnnotatedLocalElement.getDefaultExpression().toString();
	}
	
	protected String getElementString(){
		return thisAnnotatedLocalElement.toString();
	}
	
	private boolean validate(){
		String defaultExpression = getDefaultExpression();
		if(!(defaultExpression.contains(APTUtils.getGetWrapperSyntax()))){
			System.out.println("ANNOTATION PROCESSING ERROR FOR:\n" + thisAnnotatedLocalElement);
			System.out.println("\nFUTURE ANNOTATION MUST BE USED FOR DECLARING COLLECTIONS THAT INVOKE: pt.runtime.ParaTask.getPtWrapper"
					+ "\nEXAMPLE: List<Integer> myList = ParaTask.getPtWrapper(new ArrayList<Integer>())");
			return false;
		}
		return true;
	}
	

	/*
	 * This method first modifies every invocation on a collection wrapper that involves variable access expressions.
	 * That is, directly using variables or future variables for invocations on the collection wrapper. 
	 * In the next stage, the invocations that use method invocation as their arguments are processed, and compiled. 
	 * At the end, the declaration statement is changed to declaring and casting the collection wrapper. 
	 * Note, that the order of processes is very important. 
	 * 
	 * @see sp.processors.PtAnnotationProcessor#modifySourceCode()
	 */
	@Override
	protected void modifySourceCode() {
		/*
		 * order of modifications is important. Modify collection declaration
		 * changes AST node configurations apparently, and causes some of the 
		 * collected information to become null!
		 */
		modifyVarAccessExpressions();
		modifyInvocationExpressions();
		modifyCollectionDeclaration();
	}
	
	protected void modifyCollectionDeclaration(){
		insertStatementBeforeDeclaration(thisAnnotatedLocalElement);
		changeDeclarationName(thisAnnotatedLocalElement);
		insertStatementAfterDeclaration(thisAnnotatedLocalElement);
	}
	
	protected List<CtStatement> findVarAccessStatements(){
		List<CtStatement> varAccessStatements = null;
		varAccessStatements = APTUtils.findVarAccessOtherThanFutureDefinition(thisAnnotatedLocalElement.getParent(CtBlock.class), thisAnnotatedLocalElement);
		return varAccessStatements;
	}
		
	/*
	 * Finds all the arguments, both variable access and method invocations, that are used 
	 * in an invocation on the collection wrapper. That is, finding arguments that fit within
	 * one of the following invocation formats:
	 * 
	 * myList.add(_taskIDReplacement_.getReturnResult())
	 * 
	 * or
	 * 
	 * @Future
	 * Obj a = foo();
	 * myList.add( a ) --> where "a" is a future variable that is not processed yet
	 * 
	 * or
	 * 
	 * myList.add(foo(a)) --> where "foo" is a function annotated with @Task
	 * 
	 * The rest of the cases do not need to be processed. 
	 */
	private void findCollectionInvocationArguments(){
		List<CtStatement> containingStatements = findVarAccessStatements();
		listOfContainingNodes = APTUtils.listAllExpressionsOfStatements(containingStatements);
		//System.out.println("containing statements: " + mapOfContainingStatements); 

		
		variableAccessArgumentsToBeProcessed = new ArrayList<>(); //lists all variable access arguments in an invocation on collection wrapper, that may comply with the second case
		invocationsToBeProcessed = new HashMap<>(); //lists all invocations encountered within an invocation on collection wrapper, that may comply with first and third cases 
		
		for(ASTNode node : listOfContainingNodes){
			CtStatement statement = node.getStatement();

			/*
			 * collects the invocations that are used within an invocation on the collection (e.g., myList.add(foo(a) + foox(b)); )
			 * for further investigations, if any of the methods is supposed to be processed asynchronously (i.e., has @Task annotation).
			*/
			potentialAsynchronousInvocations = new ArrayList<>(); 
			/*
			 * indicates if we have found an invocation statement on the collection, 
			 * in which case, the arguments are collected to see if they fit within 
			 * any of the formats mentioned above.
			*/
			insideCollectionStatement = false; 
		
			if(statement instanceof CtInvocationImpl<?>){
				CtInvocationImpl<?> invocation = (CtInvocationImpl<?>) statement;
				CtExpression<?> target = invocation.getTarget();
				if(target != null){
					if(targetIsThisElement(target)){
						insideCollectionStatement = true;
						List<CtExpression<?>> arguments = invocation.getArguments();
						for(CtExpression<?> argument : arguments)
							findArgumentsToProcess(argument);
					}					
				}
			}
			
			/*
			 * If the statement is not an invocation on the collection, then it may contain expressions that are
			 * invocations on the collection. Therefore, its expressions need to be further inspected. 
			 */
			if(!insideCollectionStatement){
				for (int index = 0; index < node.getNumberOfExpressions(); index++){
					findArgumentsToProcess(node.getExpression(index));
				}
			}
			
			// if there are invocations within the statement that must be inspected, then add them to the list. 
			if(potentialAsynchronousInvocations.size() != 0){
				invocationsToBeProcessed.put(statement, potentialAsynchronousInvocations);
			}
		}
	}
	
	protected CtVariable<?> getCurrenAnnotatedtElement(){
		return thisAnnotatedLocalElement;
	}
	
	/*
	 * Modifies expressions of variables, which are sent as arguments to method invocations on the collection object. 
	 * Variables that are sent as arguments will be changed to taskID equivalent, if the variable is declared as a
	 * future variable by the programmer. 
	 * 
	 * For example:
	 * @Future
	 * int a = foo(x); 
	 * myList.add(a);
	 * 
	 * turns into:
	 * TaskID _aTaskID_ = _aTaskInfo_.start(x);
	 * myList.add(_aTaskID_);
	 * 
	 * myList is a TaskID aware collection. 
	 */
	private void modifyVarAccessExpressions(){
		for(CtVariableAccess<?> varAccess : variableAccessArgumentsToBeProcessed){
			String varName = varAccess.toString();
			CtVariable<?> currentAnnotatedElement= getCurrenAnnotatedtElement();
			
			if(APTUtils.isTaskIDReplacement(currentAnnotatedElement, varName)){
				modifyWithTaskIDReplacement(varAccess);
			}
			
			else{
				CtVariable<?> declaration = APTUtils.getDeclarationStatement(varAccess.getParent(CtStatement.class), varName);
				CtLocalVariable<?> declarationStatement = (CtLocalVariable<?>) declaration;
				if(declarationStatement != null){
					Future future = APTUtils.getFutureAnnotation(declarationStatement);
					if(future != null){
						modifyWithFutuerObject(future, declarationStatement, varAccess);
					}
				}
			}			
		}
	}
	
	/*
	 * Modifies future variables that are declared after the declaration of the this collection object; therefore they
	 * are not processed by the annotation processor yet. So, they have to be processed manually.
	 */
	private void modifyWithFutuerObject(Future future, CtStatement declarationStatement, CtVariableAccess<?> varAccess){
		CtLocalVariable<?> annotatedElement = (CtLocalVariable<?>) declarationStatement;
		InvocationProcessor processor = new InvocationProcessor(thisFactory, future, annotatedElement);
		processor.process();
		
		//Annotation processed, so remove it!
		List<CtAnnotation<? extends Annotation>> annotations = new ArrayList<>();
		List<CtAnnotation<? extends Annotation>> actualAnnotations = declarationStatement.getAnnotations();
		for(CtAnnotation<? extends Annotation> annotation : actualAnnotations){
			Annotation actualAnnotation = annotation.getActualAnnotation();
			if(!(actualAnnotation instanceof Future))
				annotations.add(annotation);
		}
		
		declarationStatement.setAnnotations(annotations);
		modifyWithTaskIDReplacement(varAccess);
	}
	
	/*
	 * Modifies future variables that are declared before the declaration of this collection object; therefore they
	 * are already processed. Their variable syntax is changed from <varName> to <varNameTaskID>.getReturnResult();
	 * This method changes <varNameTaskID>.getReturnResult() to <varNameTaskID>
	 * 
	 * For example:
	 * myList.add(varNameTaskID.getReturnResult()); to myList.add(varNameTaskID);
	 */
	private void modifyWithTaskIDReplacement(CtVariableAccess<?> varAccess){
		if(!(varAccess.getParent() instanceof CtInvocation<?>))
			return;
		String varName = varAccess.toString();
		varName = APTUtils.getOriginalName(varName);
		varName = varName.trim();
		varName = APTUtils.getTaskIDName(varName);
		CtVariableReference varRef = thisFactory.Core().createFieldReference();
		varRef.setSimpleName(varName);
		varAccess.setVariable(varRef);
	}
	
	/*
	 * Processes every method invocation that is used as an argument in invocations
	 * made on the collection wrapper. 
	 * That is, it sends invocations one by one to the 'modifyWithInvocation' method.
	 */
	private void modifyInvocationExpressions(){
		Set<CtStatement> statements = invocationsToBeProcessed.keySet();
		for(CtStatement statement : statements){
			List<CtInvocation<?>> invocations = invocationsToBeProcessed.get(statement);
			modifyWithInvocation(statement, invocations);
		}
	}
	
	/*
	 * Creates a future variable declaration for the method that is invoked (e.g., "foo(a) in myList.add(foo(a))")
	 * and alters that argument accordingly (i.e., changes that to the corresponding TaskID object), iff that method
	 * call is the only argument; otherwise the program blocks until the result is back for that asynchronous task. 
	 * This is the case only when the method is annotated with @Task. 
	 */
	private void modifyWithInvocation(CtStatement parentStatement, List<CtInvocation<?>> invocations){
		for(CtInvocation<?> invocation : invocations){
			if(hasTaskAnnotation(invocation)){
				newLocalVariableIndex++;
				CtLocalVariable<?> newLocalVariable = thisFactory.Core().createLocalVariable();
				
				//declare a variable that receives the result of the invocaiton. 
				CtTypeReference invocationType = invocation.getExecutable().getType();
				newLocalVariable.setType(invocationType);
			
				String newVariableName = invocation.getExecutable().getSimpleName() + "_" + newLocalVariableIndex;
				newLocalVariable.setSimpleName(newVariableName);	
				
				//This order of replacement is very important, otherwise incorrect expressions will be replaced. 
				CtVariableAccess<?> replacingVariable = thisFactory.Core().createVariableRead();
				replacingVariable.setVariable((CtVariableReference)newLocalVariable.getReference());
				invocation.replace(replacingVariable);
			
				
				newLocalVariable.setDefaultExpression((CtExpression)invocation);
				parentStatement.insertBefore(newLocalVariable);
				
				InvocationProcessor processor = new InvocationProcessor(thisFactory, thisFutureAnnotation, newLocalVariable, 
							true, parentStatement, invocation);
				processor.process();
				
				if(replacingVariable.getParent() instanceof CtInvocation<?>){
					String newTaskIDName = APTUtils.getTaskIDName(newVariableName);
					CtVariableReference newTaskIDReference = thisFactory.Core().createLocalVariableReference();
					newTaskIDReference.setSimpleName(newTaskIDName);
					replacingVariable.setVariable(newTaskIDReference);
				}
			}
		}		
	}
	
	
//----------------------------------------------------HELPER METHODS---------------------------------------------------
		
	private boolean hasTaskAnnotation(CtInvocation<?> methodInvocation){
		CtExecutableReference<?> executableReference = methodInvocation.getExecutable();
		CtExecutable<?> executable = executableReference.getDeclaration();
		List<CtAnnotation<? extends Annotation>> annotations = executable.getAnnotations();
		for(CtAnnotation<? extends Annotation> annotation : annotations){
			Annotation actualAnnotation  = annotation.getActualAnnotation();
			if(actualAnnotation instanceof Task){
				return true;
			}
		}
		return false;
	}	
	
	protected void printVariableAccessArguments(){
		System.out.println("Printing Variable Access Expressions");
		for(CtVariableAccess<?> variableAccess : variableAccessArgumentsToBeProcessed){
			System.out.println("----------------------------------------------------");
			printVarAccessComponents(variableAccess);
			System.out.println("----------------------------------------------------");
		}
	}
	
	protected void printStatementInvocations(){
		System.out.println("Printing Invocations That Were Found In Statements");
		
		if(invocationsToBeProcessed.size() == 0){
			System.out.println("no invocations to be processed");
			return;
		}
		
		for(Entry<CtStatement, List<CtInvocation<?>>> entry : invocationsToBeProcessed.entrySet()){	
			List<CtInvocation<?>> invocations = entry.getValue();
			CtStatement statement = entry.getKey();
			
			for(CtInvocation<?> invocation : invocations){
				System.out.println("----------------------------------------------------");
				System.out.println("Invocation: " + invocation + " from statement: " + statement);
				System.out.println("----------------------------------------------------");	
			}
		}
	}
	
	/*
	 * Indicates if the initial statement being inspected is an invocation on the collection wrapper,
	 * or if an invocation on the collection wrapper has been encountered within a statement or expression.
	 */
	private boolean isInsideCollection(){
		if(insideCollectionStatement || (encounteredInvocationArguments != 0))
			return true;
		return false;
	}
	
	protected void insertStatementBeforeDeclaration(CtStatement statement){
		CtInvocation invokePT = thisFactory.Core().createInvocation();
		CtExecutableReference executable = thisFactory.Core().createExecutableReference();
		CtCodeSnippetExpression invocationTarget = thisFactory.Core().createCodeSnippetExpression();
		CtCodeSnippetExpression invocationArgument = thisFactory.Core().createCodeSnippetExpression();
		
		executable.setSimpleName("processingInParallel");
		invocationTarget.setValue(APTUtils.getParaTaskSyntax());
		invocationArgument.setValue("true");
		List<CtExpression<?>> arguments = new ArrayList<>();
		arguments.add(invocationArgument);
		
		invokePT.setExecutable(executable); //processingInParallel
		invokePT.setTarget(invocationTarget);//pt.runtime.ParaTask
		invokePT.setArguments(arguments);//true
		
		statement.insertBefore(invokePT);
//		CtBlock<?> parentBlock = thisAnnotatedElement.getParent(CtBlock.class);
//		StatementMatcherFilter<CtStatement> filter = new StatementMatcherFilter<CtStatement>(thisAnnotatedElement);
//		parentBlock.insertBefore(filter, invokePT);
	}
	
	protected void changeDeclarationName(CtVariable<?> collectionWrapper){
		String newName = APTUtils.getTaskName(thisElementName);
		collectionWrapper.setSimpleName(newName);
	}
	
	/*
	 * This method adds a casting statement after the declaration of the collection. That is done to 
	 * allow using the overloading functions that accept TaskID objects. 
	 */
	protected void insertStatementAfterDeclaration(CtStatement statement){
		String newTypeString = getCollectionType() + "<" + thisCollectionGenericType + ">";
		CtTypeReference newType = thisFactory.Core().createTypeReference();
		newType.setSimpleName(newTypeString);
		List<CtTypeReference> typeCast = new ArrayList<>();
		typeCast.add(newType);
		
		CtLocalVariable<?> castedColleciton = thisFactory.Core().createLocalVariable();
	
		CtVariableAccess varAccess = thisFactory.Core().createVariableRead();
		
		CtVariableReference varRef = thisFactory.Core().createFieldReference();
		varRef.setSimpleName(APTUtils.getTaskName(thisElementName));
		varAccess.setVariable(varRef);
		varAccess.setTypeCasts(typeCast);
		
		castedColleciton.setType(newType);
		castedColleciton.setSimpleName(thisElementName);
		castedColleciton.setDefaultExpression(varAccess);
		
		statement.insertAfter(castedColleciton);
//		CtBlock<?> parentBlock = thisAnnotatedElement.getParent(CtBlock.class);
//		StatementMatcherFilter<CtStatement> filter = new StatementMatcherFilter<CtStatement>(thisAnnotatedElement);
//		parentBlock.insertAfter(filter, castedColleciton);
	}	
	
	protected String getCollectionType(){
		String currentType = thisElementType.toString();
		if(currentType.contains("List"))
			return APTUtils.getHybridListSyntax();
		else if (currentType.contains("Set"))
			return APTUtils.getHybridSetSyntax();
		else if (currentType.contains("Map"))
			return APTUtils.getHybridMapSyntax();
		else
			return APTUtils.getHybridCollectionSyntax();
	}		

	/*
	 * Finds all variable-access arguments, as well as the method calls that are used
	 * within the invocation on a collection wrapper, for further processing.
	 */
	private void findArgumentsToProcess(CtExpression<?> expression){
		/*
		 * if the expression is a variable-access, and it is within an invocation on the collection, 
		 * it needs to be remembered for further inspections. 
		 */
		if(expression instanceof CtVariableAccess<?>){
			if(isInsideCollection()){
				CtVariableAccess<?> variableAccess = (CtVariableAccess<?>) expression;
				variableAccessArgumentsToBeProcessed.add(variableAccess);
			}
		}
		
		else if (expression instanceof CtBinaryOperator<?>){
			CtBinaryOperator<?> binaryOperator = (CtBinaryOperator<?>) expression;
			findArgumentsToProcess(binaryOperator.getLeftHandOperand());
			findArgumentsToProcess(binaryOperator.getRightHandOperand());
		}
		
		else if(expression instanceof CtInvocationImpl<?>){
			CtInvocationImpl<?> invocation = (CtInvocationImpl<?>) expression;
			boolean thisCollectionInvocation = false;
			
			if(isInsideCollection()){
				/*
				 * There might be methods inside the collection invocation, that are supposed
				 * to be processed in parallel (i.e., with @Task annotation). So, given that 
				 * a method call inside collection invocation (e.g., list.add(foo(3));) is not
				 * targeting the collection itself, we add it for further inspections. 
				 */
				CtExpression<?> target = invocation.getTarget();
				
				if(target != null){//if method call is on an object
					if(!targetIsThisElement(target)){
						//System.out.println("invocation: " + invocation + " added");
						potentialAsynchronousInvocations.add(invocation);
					}
				}
				else{
					//System.out.println("invocation: " + invocation + " added");
					potentialAsynchronousInvocations.add(invocation);
				}
			}
			
			CtExpression<?> target = invocation.getTarget();
			if(target != null){
				if(targetIsThisElement(target)){
					encounteredInvocationArguments++; //we use a global counter because collectionInvocations may be nested. 
					thisCollectionInvocation = true; //every cycle of recursion uses its own boolean flag!
				}
			}
			
			List<CtExpression<?>> arguments = invocation.getArguments();
			for(CtExpression<?> argument : arguments){
				findArgumentsToProcess(argument);
			}
			
			/*
			 * if we deduct the global value for every invocation, even those invocation that are not 
			 * on the current collection, then we loose track of the number of nested collection invocations.  
			 */
			if(thisCollectionInvocation)
				encounteredInvocationArguments--;
		}
		else if (expression instanceof CtUnaryOperator<?>){
			CtUnaryOperator<?> unaryOperator = (CtUnaryOperator<?>) expression;
			findArgumentsToProcess(unaryOperator.getOperand());
		}
		else if (expression instanceof CtArrayAccess<?, ?>){
			CtArrayAccess<?, ?> arrayAccess = (CtArrayAccess<?, ?>) expression;
			findArgumentsToProcess(arrayAccess.getIndexExpression());
		}		
	}
	
	private boolean targetIsThisElement(CtExpression<?> target){
		String targetString = target.toString();
		String[] targetElements = targetString.split("\\.");
		if((targetElements[targetElements.length-1].trim()).equals(thisElementName)){
			return true;
		}
		return false;
	}
//----------------------------------------------------HELPER METHODS---------------------------------------------------
}
