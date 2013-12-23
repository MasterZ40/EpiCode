package com.epi;

import java.util.Random;

/**
 * @author translated from c++ by Blazheev Alexander
 */
public class IntersectRectangle {
    // @include
    public static class Rectangle {
        int x, y, width, height;

        public Rectangle(int x, int y, int width, int height) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
        }
        // @exclude
        public void print(String s) {
            System.out.println(s + x + " " + y + " " + width + " " + height);
        }
        // @include
    }

    public static boolean is_intersect(Rectangle R, Rectangle S) {
        return R.x <= S.x + S.width && R.x + R.width >= S.x &&
                R.y <= S.y + S.height && R.y + R.height >= S.y;
    }

    public static Rectangle intersect_rectangle(Rectangle R, Rectangle S) {
        if (is_intersect(R, S)) {
            return new Rectangle(Math.max(R.x, S.x),
                    Math.max(R.y, S.y),
                    Math.min(R.x + R.width, S.x + S.width) - Math.max(R.x, S.x),
                    Math.min(R.y + R.height, S.y + S.height) - Math.max(R.y, S.y));
        } else {
            return new Rectangle(0, 0, -1, -1);  // no intersection.
        }
    }
    // @exclude

    public static void main(String[] args) {
        for (int times = 0; times < 10000; ++times) {
            Rectangle R, S;
            if (args.length == 8) {
                R = new Rectangle(Integer.parseInt(args[0]), Integer.parseInt(args[1]),
                        Integer.parseInt(args[2]), Integer.parseInt(args[3]));
                S = new Rectangle(Integer.parseInt(args[4]), Integer.parseInt(args[5]),
                        Integer.parseInt(args[6]), Integer.parseInt(args[7]));
            } else {
                Random r = new Random();
                R = new Rectangle(r.nextInt(100) + 1, r.nextInt(100) + 1,
                        r.nextInt(100) + 1, r.nextInt(100) + 1);
                S = new Rectangle(r.nextInt(100) + 1, r.nextInt(100) + 1,
                        r.nextInt(100) + 1, r.nextInt(100) + 1);
            }
            // Intersect rectangle.
            boolean res = is_intersect(R, S);
            System.out.println(res);
            Rectangle ans = intersect_rectangle(R, S);
            ans.print("ans: ");
            assert(res == false || (ans.width >= 0 && ans.height >= 0));
        }
    }
}
