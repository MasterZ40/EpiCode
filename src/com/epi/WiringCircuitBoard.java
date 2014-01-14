package com.epi;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * @author translated from c++ by Blazheev Alexander
 */
public class WiringCircuitBoard {
    // @include
    public static class GraphVertex {
        public int d = -1;
        public ArrayList<GraphVertex> edges = new ArrayList<GraphVertex>();
    }

    public static boolean is_any_placement_feasible(List<GraphVertex> G) {
        for (GraphVertex v : G) {
            if (v.d == -1) {  // unvisited vertex.
                v.d = 0;
                if (!BFS(v)) {
                    return false;
                }
            }
        }
        return true;
    }

    private static boolean BFS(GraphVertex s) {
        LinkedList<GraphVertex> q = new LinkedList<GraphVertex>();
        q.addLast(s);

        while (!q.isEmpty()) {
            for (GraphVertex t : q.peek().edges) {
                if (t.d == -1) {  // unvisited vertex.
                    t.d = q.peek().d + 1;
                    q.addLast(t);
                } else if (t.d == q.peek().d) {
                    return false;
                }
            }
            q.removeFirst();
        }
        return true;
    }
    // @exclude

    private static boolean DFS(GraphVertex s) {
        for (GraphVertex t : s.edges) {
            if (t.d == -1) {
                t.d = s.d == 0 ? 1 : 0;
                if (!DFS(t)) {
                    return false;
                }
            } else if (t.d == s.d) {
                return false;
            }
        }
        return true;
    }

    private static boolean is_2_colorable(List<GraphVertex> G) {
        for (GraphVertex v : G) {
            v.d = -1;
        }

        for (GraphVertex v : G) {
            if (v.d == -1) {
                v.d = 0;
                if (!DFS(v)) {
                    return false;
                }
            }
        }
        return true;
    }

    public static void main(String[] args) {
        Random r = new Random();
        for (int times = 0; times < 9000; ++times) {
            int n;
            if (args.length == 1) {
                n = Integer.parseInt(args[0]);
            } else {
                n = r.nextInt(100) + 2;
            }
            ArrayList<GraphVertex> G = new ArrayList<GraphVertex>(n);
            for(int i = 0; i < n; i++) {
                G.add(new GraphVertex());
            }
            int m = r.nextInt(n * (n - 1) >> 1) + 1;
            System.out.println(times + " " + n + " " + m);
            boolean is_edge_exist[][] = new boolean[n][n];
            while (m-- > 0) {
                int a, b;
                do {
                    a = r.nextInt(n);
                    b = r.nextInt(n);
                } while (a == b || is_edge_exist[a][b] == true);
                is_edge_exist[a][b] = is_edge_exist[b][a] = true;
                G.get(a).edges.add(G.get(b));
                G.get(b).edges.add(G.get(a));
            }

            /*
            for(int i = 0; i < G.size(); ++i) {
                System.out.println(i);
                System.out.println(G.get(i).edges);
            }
            */

            boolean res = is_any_placement_feasible(G);
            System.out.println(res);
            assert(res == is_2_colorable(G));
        }
    }
}
