package com.fobgochod.code.middle;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Code43 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int row = scanner.nextInt();
        int col = scanner.nextInt();
        int[][] maze = new int[row][col];

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                maze[i][j] = scanner.nextInt();
            }
        }

        // 路径存储的数组
        List<Pos> path = new ArrayList<>();
        // DFS 搜索路径
        dfs1(maze, 0, 0, path);
        // 输出
        for (Pos p : path) {
            System.out.println("(" + p.x + "," + p.y + ")");
        }
    }


    private static boolean dfs1(int[][] maze, int row, int col, List<Pos> pos) {
        pos.add(new Pos(row, col));
        maze[row][col] = 1;

        if (row == maze.length - 1 || col == maze[0].length - 1) {
            return true;
        }

        // 向下
        if (row + 1 < maze.length && maze[row + 1][col] == 0) {
            if (dfs(maze, row + 1, col, pos)) {
                return true;
            }
        }
        // 向右
        if (col + 1 < maze[0].length && maze[row][col + 1] == 0) {
            if (dfs(maze, row, col + 1, pos)) {
                return true;
            }
        }
        // 向上
        if (row - 1 > 0 && maze[row - 1][col] == 0) {
            if (dfs(maze, row - 1, col, pos)) {
                return true;
            }
        }
        // 向左
        if (col - 1 > 0 && maze[row][col - 1] == 0) {
            if (dfs(maze, row, col - 1, pos)) {
                return true;
            }
        }

        pos.remove(pos.size() - 1);
        maze[row][col] = 0;
        return false;

    }

    // 返回值 标记是否找到可通行的路劲
    public static boolean dfs(int[][] map, int x, int y, List<Pos> path) {
        // 添加路径并标记已走
        path.add(new Pos(x, y));
        map[x][y] = 1;
        // 结束标志
        if (x == map.length - 1 && y == map[0].length - 1) {
            return true;
        }
        // 向下能走时
        if (x + 1 < map.length && map[x + 1][y] == 0) {
            if (dfs(map, x + 1, y, path)) {
                return true;
            }
        }
        // 向右能走时
        if (y + 1 < map[0].length && map[x][y + 1] == 0) {
            if (dfs(map, x, y + 1, path)) {
                return true;
            }
        }
        // 向上能走时
        if (x - 1 > -1 && map[x - 1][y] == 0) {
            if (dfs(map, x - 1, y, path)) {
                return true;
            }
        }
        // 向左能走时
        if (y - 1 > -1 && map[x][y - 1] == 0) {
            if (dfs(map, x, y - 1, path)) {
                return true;
            }
        }
        // 回溯
        path.remove(path.size() - 1);
        map[x][y] = 0;
        return false;
    }

    // 简单的位置类
    public static class Pos {
        int x;
        int y;

        public Pos(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}
