package com.epi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * @author translated from c++ by Blazheev Alexander
 */
public class PickingUpCoins {
    // @include
    public static int pick_up_coins(List<Integer> C) {
        int T[][] = new int[C.size()][C.size()];
        for(int t[] : T) {
            Arrays.fill(t, -1);
        }
        return pick_up_coins_helper(C, 0, C.size() - 1, T);
    }

    private static int pick_up_coins_helper(List<Integer> C,
                             int a,
                             int b,
                             int T[][]) {
        if (a > b) {
            return 0;  // base condition.
        }

        if (T[a][b] == -1) {
            T[a][b] = Math.max(C.get(a) + Math.min(pick_up_coins_helper(C, a + 2, b, T),
                    pick_up_coins_helper(C, a + 1, b - 1, T)),
                    C.get(b) + Math.min(pick_up_coins_helper(C, a + 1, b - 1, T),
                            pick_up_coins_helper(C, a, b - 2, T)));
        }
        return T[a][b];
    }
    // @exclude

    public static void main(String[] args) {
        Random r = new Random();
        ArrayList<Integer> C = new ArrayList<Integer>();
        if (args.length >= 1) {
            for (int i = 1; i < args.length; ++i) {
                C.add(Integer.parseInt(args[i]));
            }
        } else {
            int size = r.nextInt(1000) + 1;
            for (int i = 0; i < size; ++i) {
                C.add(r.nextInt(100));
            }
        }
        System.out.println(C);
        System.out.println(pick_up_coins(C));
    }
}
