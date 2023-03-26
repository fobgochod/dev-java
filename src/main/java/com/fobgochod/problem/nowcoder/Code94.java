package com.fobgochod.problem.nowcoder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedHashMap;
import java.util.Map;

public class Code94 {

    public static void main(String[] args) throws IOException {

        Map<String, Integer> result = new LinkedHashMap<>();

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String str;
        while ((str = br.readLine()) != null) {
            int candidate = Integer.parseInt(str);
            String[] name = br.readLine().split(" ");
            int voter = Integer.parseInt(br.readLine());
            String[] vote = br.readLine().split(" ");

            for (String s : name) {
                result.put(s, 0);
            }
            result.put("Invalid", 0);

            for (String s : vote) {
                if (result.containsKey(s)) {
                    result.put(s, result.get(s) + 1);
                } else {
                    result.put("Invalid", result.get("Invalid") + 1);
                }
            }

            for (Map.Entry<String, Integer> entry : result.entrySet()) {
                System.out.println(entry.getKey() + " : " + entry.getValue());

            }
        }
    }
}
