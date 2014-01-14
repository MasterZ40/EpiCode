package com.epi;

import java.util.Arrays;
import java.util.List;

/**
 * @author translated from c++ by Blazheev Alexander
 */
public class TiesElection {
    // @include
    // V contains the number of votes for each state.
    public static long ties_election(List<Integer> V) {
        int total_votes = 0;
        for(int v : V) {
            total_votes += v;
        }

        // No way to tie if the total number of votes is odd.
        if ((total_votes & 1) != 0) {
            return 0;
        }

        long table[][] = new long[V.size() + 1][total_votes + 1];
        table[0][0] = 1;  // base condition: 1 way to reach 0.
        for (int i = 0; i < V.size(); ++i) {
            for (int j = 0; j <= total_votes; ++j) {
                table[i + 1][j] = table[i][j] + (j >= V.get(i) ? table[i][j - V.get(i)] : 0);
            }
        }
        return table[V.size()][total_votes >> 1];
    }
    // @exclude

    private static void simple_test() {
        List<Integer> votes = Arrays.asList(4, 5, 2, 7);
        System.out.println(ties_election(votes));
    }

    public static void main(String[] args) {
        simple_test();
        List<Integer> votes = Arrays.asList(9,  3, 11, 6,  55, 9,  7,  3,  29, 16, 4, 4,  20,
                11, 6, 6,  8,  8,  4,  10, 11, 16, 10, 6, 10, 3,
                5,  6, 4,  14, 5,  29, 15, 3,  18, 7,  7, 20, 4,
                9,  3, 11, 38, 6,  3,  13, 12, 5,  10, 3, 3);
        System.out.println(ties_election(votes));
    }
}
