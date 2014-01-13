package com.drx.epi;

import java.util.Arrays;

import static com.drx.epi.SudokuCheck.*;

/**
 * @author translated from c++ by Blazheev Alexander
 */
public class SudokuSolve {
    // @include
    public static boolean solve_Sudoku(int A[][]) {
        if (!is_valid_Sudoku(A)) {
            System.out.println("Initial configuration violates constraints.");
            return false;
        }

        if (solve_Sudoku_helper(A, 0, 0)) {
            for (int i = 0; i < A.length; ++i) {
                System.out.println(Arrays.toString(A[i]));
            }
            return true;
        } else {
            System.out.println("No solution exists.");
            return false;
        }
    }

    private static boolean solve_Sudoku_helper(int A[][], int i, int j) {
        if (i == A.length) {
            i = 0;  // starts a new row.
            if (++j == A[i].length) {
                return true;  // Entire matrix has been filled without conflict.
            }
        }

        // Skips nonempty entries.
        if (A[i][j] != 0) {
            return solve_Sudoku_helper(A, i + 1, j);
        }

        for (int val = 1; val <= A.length; ++val) {
            // Note: practically, it's substantially quicker to check if entry val
            // conflicts with any of the constraints if we add it at (i,j) before
            // adding it, rather than adding it and then calling is_valid_Sudoku.
            // The reason is that we know we are starting with a valid configuration,
            // and the only entry which can cause a problem is entryval at (i,j).
            if (valid_to_add(A, i, j, val)) {
                A[i][j] = val;
                if (solve_Sudoku_helper(A, i + 1, j)) {
                    return true;
                }
            }
        }

        A[i][j] = 0;  // undos assignment.
        return false;
    }

    private static boolean valid_to_add(int A[][], int i, int j, int val) {
        // Check row constraints.
        for (int k = 0; k < A.length; ++k) {
            if (val == A[k][j]) {
                return false;
            }
        }

        // Check column constraints.
        for (int k = 0; k < A.length; ++k) {
            if (val == A[i][k]) {
                return false;
            }
        }

        // Check region constraints.
        int region_size = (int) Math.sqrt(A.length);
        int I = i / region_size, J = j / region_size;
        for (int a = 0; a < region_size; ++a) {
            for (int b = 0; b < region_size; ++b) {
                if (val == A[region_size * I + a][region_size * J + b]) {
                    return false;
                }
            }
        }
        return true;
    }
    // @exclude

    public static void main(String[] args) {
        int A[][] = new int[9][];
        A[0] = new int[]{0, 2, 6, 0, 0, 0, 8, 1, 0};
        A[1] = new int[]{3, 0, 0, 7, 0, 8, 0, 0, 6};
        A[2] = new int[]{4, 0, 0, 0, 5, 0, 0, 0, 7};
        A[3] = new int[]{0, 5, 0, 1, 0, 7, 0, 9, 0};
        A[4] = new int[]{0, 0, 3, 9, 0, 5, 1, 0, 0};
        A[5] = new int[]{0, 4, 0, 3, 0, 2, 0, 5, 0};
        A[6] = new int[]{1, 0, 0, 0, 3, 0, 0, 0, 2};
        A[7] = new int[]{5, 0, 0, 2, 0, 4, 0, 0, 9};
        A[8] = new int[]{0, 3, 8, 0, 0, 0, 4, 6, 0};
        solve_Sudoku(A);
    }
}
