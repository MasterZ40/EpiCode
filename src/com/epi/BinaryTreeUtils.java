package com.epi;

import java.util.ArrayList;
import java.util.Random;

import static com.epi.BinaryTreePrototypeTemplate.BinaryTree;

/**
 * @author translated from c++ by Blazheev Alexander
 */
public class BinaryTreeUtils {
    public static BinaryTree<Integer> generate_rand_binary_tree(int n, boolean is_unique) {
        Random r = new Random();
        ArrayList<BinaryTree<Integer>> l = new ArrayList<BinaryTree<Integer>>();
        BinaryTree<Integer> root = new BinaryTree<Integer>(is_unique ? n-- : r.nextInt(Integer.MAX_VALUE));
        l.add(root);
        while(n-- > 0) {
            int x = r.nextInt(l.size());
            boolean addLeft = r.nextBoolean();
            BinaryTree<Integer> it = l.get(x);
            if(addLeft && it.getLeft() == null || !addLeft && it.getRight() == null) {
                it.setLeft(new BinaryTree<Integer>(is_unique ? n : r.nextInt(Integer.MAX_VALUE)));
                l.add(it.getLeft());
            } else {
                it.setRight(new BinaryTree<Integer>(is_unique ? n : r.nextInt(Integer.MAX_VALUE)));
                l.add(it.getRight());
            }
            if(it.getLeft() != null && it.getRight() != null) {
                l.remove(x);
            }
        }
        return root;
    }

    private static <T> void generate_preorder_helper(BinaryTree<T> r, ArrayList<T> ret) {
        if (r != null) {
            ret.add(r.getData());
            generate_preorder_helper(r.getLeft(), ret);
            generate_preorder_helper(r.getRight(), ret);
        }
    }

    public static <T> ArrayList<T> generate_preorder(BinaryTree<T> r) {
        ArrayList<T> ret = new ArrayList<T>();
        generate_preorder_helper(r, ret);
        return ret;
    }

    private static <T> void generate_inorder_helper(BinaryTree<T> r, ArrayList<T> ret) {
        if (r != null) {
            generate_inorder_helper(r.getLeft(), ret);
            ret.add(r.getData());
            generate_inorder_helper(r.getRight(), ret);
        }
    }

    public static <T> ArrayList<T> generate_inorder(BinaryTree<T> r) {
        ArrayList<T> ret = new ArrayList<T>();
        generate_inorder_helper(r, ret);
        return ret;
    }

    private static <T> void generate_postorder_helper(BinaryTree<T> r, ArrayList<T> ret) {
        if (r != null) {
            generate_postorder_helper(r.getLeft(), ret);
            generate_postorder_helper(r.getRight(), ret);
            ret.add(r.getData());
        }
    }

    public static <T> ArrayList<T> generate_postorder(BinaryTree<T> r) {
        ArrayList<T> ret = new ArrayList<T>();
        generate_postorder_helper(r, ret);
        return ret;
    }

}
