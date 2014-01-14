package com.epi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * @author translated from c++ by Blazheev Alexander
 */
public class ScorePermutation {
    // @include
    public static long count_permutations(int k, List<Integer> score_ways) {
        long permutations[] = new long[k + 1];
        permutations[0] = 1;  // one way to reach 0.
        for (int i = 0; i <= k; ++i) {
            for (int score : score_ways) {
                if (i >= score) {
                    permutations[i] += permutations[i - score];
                }
            }
        }
        return permutations[k];
    }
    // @exclude

    private static void simple_test() {
        int k = 12;
        List<Integer> score_ways = Arrays.asList(2, 3, 7);
        assert(18 == count_permutations(k, score_ways));
    }

    public static void main(String[] args) {
        simple_test();
        Random r = new Random();
        int k;
        ArrayList<Integer> score_ways = new ArrayList<Integer>();
        if (args.length == 0) {
            k = r.nextInt(1000);
            int size = r.nextInt(50) + 1;
            for (int i = 0; i < size; ++i) {
                score_ways.add(r.nextInt(1000) + 1);
            }
        } else if (args.length == 2) {
            k = Integer.parseInt(args[0]);
            int size = Integer.parseInt(args[1]);
            for (int i = 0; i < size; ++i) {
                score_ways.add(r.nextInt(1000) + 1);
            }
        } else {
            k = Integer.parseInt(args[0]);
            for (int i = 2; i < args.length; ++i) {
                score_ways.add(Integer.parseInt(args[i]));
            }
        }
        System.out.println(count_permutations(k, score_ways));
    }
}

