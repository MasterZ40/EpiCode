package com.drx.epi;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

/**
 * @author translated from c++ by Blazheev Alexander
 */
public class SearchMajority {
    // @include
    public static String majority_search(InputStream sin) {
        String candidate = "";
        String buf;
        int count = 0;
        Scanner s = new Scanner(sin);
        while (s.hasNextLine()) {
            buf = s.nextLine();
            if (count == 0) {
                candidate = buf;
                count = 1;
            } else if (candidate.equals(buf)) {
                ++count;
            } else {
                --count;
            }
        }
        return candidate;
    }
    // @exclude

    private static String rand_string(int len) {
        Random r = new Random();
        StringBuilder ret = new StringBuilder(len);
        while(len-- > 0) {
            ret.append((char)(r.nextInt(26) + 'a'));
        }
        return ret.toString();
    }

    private static void check_ans(List<String> stream, String ans) {
        Collections.sort(stream);
        int count = 1;
        boolean find = false;
        for (int i = 1; i < stream.size(); ++i) {
            if (!stream.get(i).equals(stream.get(i - 1))) {
                if (count * 2 >= stream.size()) {
                    assert(ans.equals(stream.get(i - 1)));
                    find = true;
                }
                count = 1;
            } else {
                ++count;
            }
        }
        if (count * 2 >= stream.size()) {
            assert(ans.equals(stream.get(stream.size() - 1)));
            find = true;
        }
        assert(find == true);
    }

    public static void main(String[] args) {
        Random r = new Random();
        for (int times = 0; times < 10000; ++times) {
            int n;
            ArrayList<String> stream = new ArrayList<String>();
            if (args.length == 1) {
                n = Integer.parseInt(args[0]);
            } else {
                n = r.nextInt(1000) + 1;
            }
            for (int i = 0; i < n; ++i) {
                stream.add(rand_string(r.nextInt(5) + 1));
            }
            // generate the majority
            for (int i = 0; i < n; ++i) {
                stream.add(stream.get(stream.size() - 1));
            }
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            OutputStreamWriter osw = new OutputStreamWriter(baos);
            try {
                for (int i = 0; i < stream.size(); ++i) {
                    osw.write(stream.get(i) + "\n");
                }
                osw.close();
            } catch(IOException e) {
                System.out.println("IOException: " + e.getMessage());
            }
            ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
            String ret = majority_search(bais);
            System.out.println(ret);
            check_ans(stream, ret);
        }
    }
}
