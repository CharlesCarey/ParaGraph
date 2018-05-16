package bfs;

import graph.*;
import java.util.*;

public class BFSParallelisable {
    Queue<Vertex> _waitlist = new LinkedList<>();
    HashMap<Integer, Queue<Vertex>> _bags = new HashMap<>();
    HashMap<Vertex, Integer> _distance = new HashMap();
    Graph _graph;

    public void run(Graph graph, Vertex source) {
        _graph = graph;
        int cores = 2;

        // Initalise bags
        for (int i = 1; i <= cores; i++) {
            _bags.put(i, new LinkedList<Vertex>());
        }

        _distance.put(source, 0);
        _waitlist.add(source);

        while (!_waitlist.isEmpty()) {
            // sequential if too small
            if (_waitlist.size() < cores) {
                // Process entire level
                int size = _waitlist.size();
                for (int i = 0; i < size; i++) {
                    Vertex current = _waitlist.poll();
                    Iterable<Vertex> adjacent = graph.adjacentVertices(current);
                    for (Vertex v : adjacent) {
                        if (!_distance.containsKey(v)) {
                            _distance.put(v, _distance.get(current) + 1);
                            _waitlist.add(v);
                        }
                    }
                }
            } else {
                // Would like to use _bags for this
                split(_waitlist, _bags, cores);

                // will be the parallel function
                for (int i = 1; i <= cores; i++) {
                    processBag(i);
                }

                // Merge _bags
                Queue<Vertex> newWaitlist = new LinkedList<>();
                for (int i = 1; i <= cores; i++) {
                    Queue<Vertex> bag = _bags.get(i);
                    while (!bag.isEmpty()) {
                        newWaitlist.add(bag.poll());
                    }
                }

                _waitlist = newWaitlist;

            }
        }

        for (Vertex v : _distance.keySet()) {
            System.out.println(v.name() + ": " + _distance.get(v));
        }
    }

    private void split(Queue<Vertex> waitlist, HashMap<Integer, Queue<Vertex>> _bags, int cores) {
        int size = waitlist.size();
        for (int i = 0; i < size; i++) {
            _bags.get(i % cores + 1).add(_waitlist.poll());
        }
    }

    public void processBag(int core) {
        // /Get the bag for the core
        Queue<Vertex> coreWaitlist = _bags.get(core);
        Queue<Vertex> nextLevelWaitList = new LinkedList<>();

        while (!coreWaitlist.isEmpty()) {
            Vertex current = coreWaitlist.poll();
            Iterable<Vertex> adjacent = _graph.adjacentVertices(current);
            for (Vertex v : adjacent) {
                if (!_distance.containsKey(v)) {
                    _distance.put(v, _distance.get(current) + 1);
                    nextLevelWaitList.add(v);
                }
            }
        }

        _bags.put(core, nextLevelWaitList);
    }
}
