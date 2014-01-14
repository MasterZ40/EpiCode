package com.epi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 * @author translated from c++ by Blazheev Alexander
 */
public class TheoryOfEquality {
    // @include
    public static class Constraint {
        public int a, b;

        public Constraint(int a, int b) {
            this.a = a;
            this.b = b;
        }

        public String toString() {
            return a + " " + b;
        }
    }

    public static class GraphVertex {
        public int group = -1;  // represents the connected component it belongs.
        public ArrayList<GraphVertex> edges = new ArrayList<GraphVertex>();
    }

    public static boolean are_constraints_satisfied(
            List<Constraint> E,    // Equality constraints.
            List<Constraint> I) {  // Inequality constraints.
        HashMap<Integer, GraphVertex> G = new HashMap<Integer, GraphVertex>();
        // Build graph G according to E.
        for (Constraint e : E) {
            if(!G.containsKey(e.a)) {
                G.put(e.a, new GraphVertex());
            }
            if(!G.containsKey(e.b)) {
                G.put(e.b, new GraphVertex());
            }
            G.get(e.a).edges.add(G.get(e.b));
            G.get(e.b).edges.add(G.get(e.a));
        }

        // Assign group index for each connected component.
        int group_count = 0;
        for (GraphVertex vertex : G.values()) {
            if (vertex.group == -1) {        // is a unvisited vertex.
                vertex.group = group_count++;  // assigns a group index.
                DFS(vertex);
            }
        }

        // Examine each inequality constraint to see if there is a violation.
        for (Constraint i : I) {
            if(!G.containsKey(i.a)) {
                G.put(i.a, new GraphVertex());
            }
            if(!G.containsKey(i.b)) {
                G.put(i.b, new GraphVertex());
            }
            if (G.get(i.a).group != -1 && G.get(i.b).group != -1 &&
                    G.get(i.a).group == G.get(i.b).group) {
                return false;
            }
        }
        return true;
    }

    private static void DFS(GraphVertex u) {
        for (GraphVertex v : u.edges) {
            if (v.group == -1) {
                v.group = u.group;
                DFS(v);
            }
        }
    }
    // @exclude

    private static void small_test() {
        ArrayList<Constraint> E = new ArrayList<Constraint>();
        ArrayList<Constraint> I = new ArrayList<Constraint>();
        E.add(new Constraint(0, 1));
        I.add(new Constraint(2, 3));
        assert(are_constraints_satisfied(E, I));
        E.clear();
        I.clear();
        // Example on the book.
        E.add(new Constraint(1, 2));
        E.add(new Constraint(2, 3));
        E.add(new Constraint(3, 4));
        I.add(new Constraint(1, 4));
        assert(!are_constraints_satisfied(E, I));
    }

    public static void main(String[] args) {
        small_test();
        Random r = new Random();
        int n, m, k;
        if (args.length == 1) {
            n = Integer.parseInt(args[0]);
            m = r.nextInt( n * (n - 1) >> 1) + 1;
            k = r.nextInt((n * (n - 1) >> 1) - m) + 1;
        } else if (args.length == 2) {
            n = Integer.parseInt(args[0]);
            m = Integer.parseInt(args[1]);
            k = r.nextInt((n * (n - 1) >> 1) - m) + 1;
        } else {
            n = r.nextInt(100) + 2;
            m = r.nextInt( n * (n - 1) >> 1) + 1;
            k = r.nextInt((n * (n - 1) >> 1) - m) + 1;
        }
        System.out.println("n = " + n + ", m = " + m + ", k = " + k);
        boolean have_edges[][] = new boolean[n][n];
        ArrayList<Constraint> E = new ArrayList<Constraint>();
        for (int i = 0; i < m; ++i) {
            int a, b;
            do {
                a = r.nextInt(n);
                b = r.nextInt(n);
            } while (a == b || have_edges[a][b] == true);
            have_edges[a][b] = have_edges[b][a] = true;
            E.add(new Constraint(a, b));
        }
        ArrayList<Constraint> I = new ArrayList<Constraint>();
        for (int i = 0; i < k; ++i) {
            int a, b;
            do {
                a = r.nextInt(n);
                b = r.nextInt(n);
            } while (a == b || have_edges[a][b] == true);
            have_edges[a][b] = have_edges[b][a] = true;
            I.add(new Constraint(a, b));
        }
        //System.out.println("equal constraint\n" + E);
        //System.out.println("not equal constraint\n" + I);

        boolean res = are_constraints_satisfied(E, I);
        System.out.println(res);
    }
}
