package com.drx.epi;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author translated from c++ by Blazheev Alexander
 */
public class LargestRectangleUnderSkyline {
    // @include
    public static int calculate_largest_rectangle(List<Integer> A) {
        // Calculate L.
        LinkedList<Integer> s = new LinkedList<Integer>();
        ArrayList<Integer> L = new ArrayList<Integer>();
        for (int i = 0; i < A.size(); ++i) {
            while (!s.isEmpty() && A.get(s.peek()) >= A.get(i)) {
                s.pop();
            }
            L.add(s.isEmpty() ? -1 : s.peek());
            s.push(i);
        }

        // Clear stack for calculating R.
        while (!s.isEmpty()) {
            s.pop();
        }
        int R[] = new int[A.size()];
        for (int i = A.size() - 1; i >= 0; --i) {
            while (!s.isEmpty() && A.get(s.peek()) >= A.get(i)) {
                s.pop();
            }
            R[i] = s.isEmpty() ? A.size() : s.peek();
            s.push(i);
        }

        // For each A[i], find its maximum area include it.
        int max_area = 0;
        for (int i = 0; i < A.size(); ++i) {
            max_area = Math.max(max_area, A.get(i) * (R[i] - L.get(i) - 1));
        }
        return max_area;
    }
    // @exclude
}
