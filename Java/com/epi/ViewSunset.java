package com.epi;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedList;
import java.util.Random;

/**
 * @author translated from c++ by Blazheev Alexander
 */
public class ViewSunset {
    // @include
    public static <T extends Comparable<T>> LinkedList<Pair<Integer, T>> examine_buildings_with_sunset(InputStream sin) {
        int idx = 0;  // building's index.
        T height;
        // Stores (building_idx, building_height) pair with sunset views.
        LinkedList<Pair<Integer, T>> buildings_with_sunset = new LinkedList<Pair<Integer, T>>();
        try {
            ObjectInputStream osin = new ObjectInputStream(sin);
            while(true) {
                height = (T) osin.readObject();
                while(!buildings_with_sunset.isEmpty() &&
                        height.compareTo(buildings_with_sunset.getLast().getSecond()) >= 0) {
                    buildings_with_sunset.removeLast();
                }
                buildings_with_sunset.addLast(new Pair<Integer, T>(idx++, height));
            }
        } catch(ClassNotFoundException e) {
            System.out.println(e.getMessage());
        } catch(IOException e) {
            // Catching when there no more objects in InputStream
        }
        return buildings_with_sunset;
    }
    // @exclude

    public static void main(String[] args) {
        Random r = new Random();
        try {
            for(int times = 0; times < 1000; ++times) {
                int n;
                if(args.length == 1) {
                    n = Integer.parseInt(args[0]);
                } else {
                    n = r.nextInt(10000) + 1;
                }
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(baos);
                for (int i = 0; i < n; ++i) {
                    Integer height = r.nextInt(2 * n) + 1;
                    oos.writeObject(height);
                }
                ByteArrayInputStream sin = new ByteArrayInputStream(baos.toByteArray());
                LinkedList<Pair<Integer, Integer>> res = examine_buildings_with_sunset(sin);
                Pair<Integer, Integer> prev = res.pop();
                System.out.println(prev);
                while(!res.isEmpty()) {
                    Pair<Integer, Integer> current = res.pop();
                    System.out.println(current);
                    assert(prev.getFirst() < current.getFirst());
                    assert(prev.getSecond() > current.getSecond());
                    prev = current;
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
