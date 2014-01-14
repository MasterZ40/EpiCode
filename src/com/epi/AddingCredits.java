package com.drx.epi;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeMap;

/**
 * @author translated from c++ by Blazheev Alexander
 */
public class AddingCredits {
    // @include
    public static class ClientsCreditsInfo {
        private int offset_ = 0;
        private HashMap<String, Integer> credits_ = new HashMap<String, Integer>();
        private TreeMap<Integer, Set<String>> inverse_credits_ = new TreeMap<Integer, Set<String>>();

        public boolean insert(String s, int c) {
            if (!credits_.containsKey(s)) {
                credits_.put(s, c - offset_);
                Set<String> set = inverse_credits_.get(c - offset_);
                if(set == null) {
                    set = new HashSet<String>();
                    inverse_credits_.put(c - offset_, set);
                }
                set.add(s);
                return true;
            }
            return false;
        }

        public boolean remove(String s) {
            Integer credits_it = credits_.get(s);
            if(credits_it != null) {
                inverse_credits_.get(credits_it).remove(s);
                if(inverse_credits_.get(credits_it).isEmpty())
                    inverse_credits_.remove(credits_it);
                credits_.remove(s);
                return true;
            }
            return false;
        }

        public int lookup(String s) {
            Integer it = credits_.get(s);
            return it == null ? -1 : it + offset_;
        }

        public void addAll(int C) {
            offset_ += C;
        }

        public String max() {
            return inverse_credits_.isEmpty()
                    ? ""
                    : inverse_credits_.lastEntry().getValue().iterator().next();
        }
    }
    // @exclude

    public static void main(String[] args) {
        ClientsCreditsInfo a = new ClientsCreditsInfo();
        assert(a.max().isEmpty());
        assert(!a.remove("foo"));
        assert(a.insert("foo", 1));
        assert(!a.insert("foo", 10));
        assert(a.insert("bar", 2));
        a.addAll(5);
        assert(a.insert("widget", 3));
        a.addAll(5);
        a.insert("dothis", 4);
        assert(11 == a.lookup("foo"));
        assert(12 == a.lookup("bar"));
        assert(8 == a.lookup("widget"));
        assert(4 == a.lookup("dothis"));
        assert(a.remove("foo"));
        assert(-1 == a.lookup("foo"));
        assert(a.max().equals("bar"));
        assert(a.insert("xyz", 13));
        assert(a.max().equals("xyz"));
        a.insert("dd", 15);
        assert(a.max().equals("dd"));
    }
}
