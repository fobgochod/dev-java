package com.fobgochod.problem.od2023;

import java.util.*;

/**
 * 寻找链表的中间结点
 * 例1
 * 输入
 * 00100 4
 * 00000 4 -1
 * 00100 1 12309
 * 33218 3 00000
 * 12309 2 33218
 * 输出
 * 3
 * <p>
 * 示例2
 * 输入
 * 输出
 */
public class Code019 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Map<String, Node> nodeMap = new HashMap<>();
        String[] first = scanner.nextLine().split(" ");
        for (int i = 0; i < Integer.parseInt(first[1]); i++) {
            String[] strs = scanner.nextLine().split(" ");
            nodeMap.put(strs[0], new Node(strs[1], strs[2]));
        }

        List<String> data = new LinkedList<>();
        String address = first[0];
        while (true) {
            if (!nodeMap.containsKey(address)) {
                break;
            }
            Node node = nodeMap.get(address);
            data.add(node.data);
            address = node.next;
        }
        System.out.println(data.get(data.size() / 2));
    }

    static class Node {
        String data;
        String next;

        public Node(String data, String next) {
            this.data = data;
            this.next = next;
        }
    }
}
