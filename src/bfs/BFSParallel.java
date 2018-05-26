package bfs;//####[1]####
//####[1]####
import graph.*;//####[3]####
import java.util.*;//####[5]####
import java.util.concurrent.*;//####[6]####
//####[6]####
//-- ParaTask related imports//####[6]####
import pt.runtime.*;//####[6]####
import java.util.concurrent.ExecutionException;//####[6]####
import java.util.concurrent.locks.*;//####[6]####
import java.lang.reflect.*;//####[6]####
import pt.runtime.GuiThread;//####[6]####
import java.util.concurrent.BlockingQueue;//####[6]####
import java.util.ArrayList;//####[6]####
import java.util.List;//####[6]####
//####[6]####
public class BFSParallel {//####[8]####
    static{ParaTask.init();}//####[8]####
    /*  ParaTask helper method to access private/protected slots *///####[8]####
    public void __pt__accessPrivateSlot(Method m, Object instance, TaskID arg, Object interResult ) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {//####[8]####
        if (m.getParameterTypes().length == 0)//####[8]####
            m.invoke(instance);//####[8]####
        else if ((m.getParameterTypes().length == 1))//####[8]####
            m.invoke(instance, arg);//####[8]####
        else //####[8]####
            m.invoke(instance, arg, interResult);//####[8]####
    }//####[8]####
    //####[9]####
    Queue<LayerVertex> _waitlist = new LinkedList<LayerVertex>();//####[9]####
    //####[10]####
    HashMap<Integer, Queue<LayerVertex>> _bags = new HashMap();//####[10]####
    //####[11]####
    ConcurrentHashMap<LayerVertex, Integer> _distance = new ConcurrentHashMap();//####[11]####
    //####[12]####
    Graph _graph;//####[12]####
    //####[14]####
    public ConcurrentHashMap<LayerVertex, Integer> run(Graph graph, LayerVertex source) {//####[14]####
        _graph = graph;//####[15]####
        int cores = 1;//####[16]####
        for (int i = 0; i < cores; i++) //####[19]####
        {//####[19]####
            _bags.put(i, new LinkedList<LayerVertex>());//####[20]####
        }//####[21]####
        _distance.put(source, 0);//####[23]####
        _waitlist.add(source);//####[24]####
        while (!_waitlist.isEmpty()) //####[26]####
        {//####[26]####
            if (_waitlist.size() < cores * 1) //####[28]####
            {//####[28]####
                int size = _waitlist.size();//####[30]####
                for (int i = 0; i < size; i++) //####[31]####
                {//####[31]####
                    LayerVertex current = _waitlist.poll();//####[32]####
                    Iterable<LayerVertex> adjacent = graph.adjacentVertices(current);//####[33]####
                    for (LayerVertex v : adjacent) //####[34]####
                    {//####[34]####
                        if (!_distance.containsKey(v)) //####[35]####
                        {//####[35]####
                            _distance.put(v, _distance.get(current) + 1);//####[36]####
                            _waitlist.add(v);//####[37]####
                        }//####[38]####
                    }//####[39]####
                }//####[40]####
            } else {//####[41]####
                split(_waitlist, _bags, cores);//####[43]####
                try {//####[46]####
                    TaskIDGroup tasks = processBag();//####[47]####
                    tasks.waitTillFinished();//####[48]####
                } catch (ExecutionException ex) {//####[50]####
                    System.out.println("Execution Exception");//####[51]####
                } catch (InterruptedException ex) {//####[52]####
                    System.out.println("Interrupted Exception");//####[53]####
                }//####[54]####
                Queue<LayerVertex> newWaitlist = new LinkedList();//####[57]####
                for (int i = 0; i < cores; i++) //####[58]####
                {//####[58]####
                    Queue<LayerVertex> bag = _bags.get(i);//####[59]####
                    while (!bag.isEmpty()) //####[60]####
                    {//####[60]####
                        newWaitlist.add(bag.poll());//####[61]####
                    }//####[62]####
                }//####[63]####
                _waitlist = newWaitlist;//####[65]####
            }//####[67]####
        }//####[68]####
        return _distance;//####[70]####
    }//####[71]####
    //####[73]####
    private void split(Queue<LayerVertex> waitlist, HashMap<Integer, Queue<LayerVertex>> _bags, int cores) {//####[73]####
        int size = waitlist.size();//####[74]####
        for (int i = 0; i < size; i++) //####[75]####
        {//####[75]####
            _bags.get(i % cores).add(_waitlist.poll());//####[76]####
        }//####[77]####
    }//####[78]####
    //####[80]####
    private static volatile Method __pt__processBag__method = null;//####[80]####
    private synchronized static void __pt__processBag__ensureMethodVarSet() {//####[80]####
        if (__pt__processBag__method == null) {//####[80]####
            try {//####[80]####
                __pt__processBag__method = ParaTaskHelper.getDeclaredMethod(new ParaTaskHelper.ClassGetter().getCurrentClass(), "__pt__processBag", new Class[] {//####[80]####
                        //####[80]####
                });//####[80]####
            } catch (Exception e) {//####[80]####
                e.printStackTrace();//####[80]####
            }//####[80]####
        }//####[80]####
    }//####[80]####
    public TaskIDGroup<Void> processBag() {//####[80]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[80]####
        return processBag(new TaskInfo());//####[80]####
    }//####[80]####
    public TaskIDGroup<Void> processBag(TaskInfo taskinfo) {//####[80]####
        // ensure Method variable is set//####[80]####
        if (__pt__processBag__method == null) {//####[80]####
            __pt__processBag__ensureMethodVarSet();//####[80]####
        }//####[80]####
        taskinfo.setParameters();//####[80]####
        taskinfo.setMethod(__pt__processBag__method);//####[80]####
        taskinfo.setInstance(this);//####[80]####
        return TaskpoolFactory.getTaskpool().enqueueMulti(taskinfo, 1);//####[80]####
    }//####[80]####
    public void __pt__processBag() {//####[80]####
        int core = CurrentTask.relativeID();//####[81]####
        Queue<LayerVertex> coreWaitlist = _bags.get(core);//####[84]####
        Queue<LayerVertex> nextLevelWaitList = new LinkedList();//####[85]####
        while (!coreWaitlist.isEmpty()) //####[88]####
        {//####[88]####
            LayerVertex current = coreWaitlist.poll();//####[89]####
            Iterable<LayerVertex> adjacent = _graph.adjacentVertices(current);//####[90]####
            for (LayerVertex v : adjacent) //####[91]####
            {//####[91]####
                if (!_distance.containsKey(v)) //####[92]####
                {//####[92]####
                    _distance.put(v, _distance.get(current) + 1);//####[93]####
                    nextLevelWaitList.add(v);//####[94]####
                }//####[95]####
            }//####[96]####
        }//####[97]####
        _bags.put(core, nextLevelWaitList);//####[99]####
    }//####[100]####
//####[100]####
}//####[100]####
