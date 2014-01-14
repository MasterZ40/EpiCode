package com.epi;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Random;

/**
 * @author translated from c++ by Blazheev Alexander
 */
public class GeneratingABSqrt2 {
    // @include
    public static class Num implements Comparable<Num>{
        private int a, b;
        private double val;

        public Num(int a, int b) {
            this.a = a;
            this.b = b;
            val = a + b * Math.sqrt(2);
        }

        public int getA() {
            return a;
        }

        public void setA(int a) {
            this.a = a;
        }

        public int getB() {
            return b;
        }

        public void setB(int b) {
            this.b = b;
        }

        public double getVal() {
            return val;
        }

        public void setVal(double val) {
            this.val = val;
        }

        @Override
        public int compareTo(Num o) {
            return Double.valueOf(val).compareTo(o.getVal());
        }

        @Override
        public boolean equals(Object o) {
            if(this == o) return true;
            if(o == null || getClass() != o.getClass()) return false;

            Num num = (Num) o;

            if(a != num.a) return false;
            if(b != num.b) return false;

            return true;
        }

        @Override
        public int hashCode() {
            int result = a;
            result = 31*result + b;
            return result;
        }

        @Override
        public String toString() {
            return a + " " + b + " " + val;
        }
    }

    public static ArrayList<Num> generate_first_k(int k) {
        PriorityQueue<Num> min_heap = new PriorityQueue<Num>();
        ArrayList<Num> smallest = new ArrayList<Num>();
        HashSet<Num> hash = new HashSet<Num>();

        // Initial for 0 + 0 * sqrt(2).
        min_heap.add(new Num(0, 0));
        hash.add(new Num(0, 0));

        while (smallest.size() < k) {
            Num s = min_heap.remove();
            smallest.add(s);
            hash.remove(s);

            // Add the next two numbers derived from s.
            Num c1 = new Num(s.a + 1, s.b);
            Num c2 = new Num(s.a, s.b + 1);
            if (hash.add(c1)) {
                min_heap.add(c1);
            }
            if (hash.add(c2)) {
                min_heap.add(c2);
            }
        }
        return smallest;
    }
    // @exclude

    public static void main(String[] args) {
        Random r = new Random();
        for (int times = 0; times < 1000; ++times) {
            int k;
            if (args.length == 1) {
                k = Integer.parseInt(args[0]);
            } else {
                k = r.nextInt(10000) + 1;
            }
            List<Num> ans = generate_first_k(k);
            for (int i = 0; i < ans.size(); ++i) {
                System.out.println(ans.get(i));
                if (i > 0) {
                    assert(ans.get(i).getVal() >= ans.get(i - 1).getVal());
                }
            }
        }
    }
}
