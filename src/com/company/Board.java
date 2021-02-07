package com.company;

public class Board {
    private int size_x;
    private int size_y;
    private int[][] data;
    private int[][] neighbor_moves = new int[][] {
            {1, 1},
            {1, 0},
            {1, -1},
            {0, -1},
            {-1, -1},
            {-1, 0},
            {-1, 1},
            {0, 1}
    };
    private int[][] rules = new int[][] {
            {2, 3},
            {3, 3}
    };

    public Board(int size_x, int size_y) {
        this.size_x = size_x;
        this.size_y = size_y;
        setup_line();
    }

    public Board(int size_x, int size_y, int[][] data) {
        this.size_x = size_x;
        this.size_y = size_y;
        this.data = data;
    }

    private void setup_empty() {
        data = new int[size_x][size_y];
    }

    private void setup_line() {
        data = new int[size_x][size_y];

        for (int i = 10; i < size_x - 10; i++) {
            data[i][size_y/2] = 1;
        }
    }

    private void setup_square() {
        data = new int[size_x][size_y];

        for (int i = 10; i < size_x - 10; i++) {
            data[i][10] = 1;
            data[i][size_y - 11] = 1;
        }
        for (int i = 10; i < size_y - 10; i++) {
            data[10][i] = 1;
            data[size_x - 11][i] = 1;
        }
    }

    private void setup_gun() {
        data = new int[size_x][size_y];
        data[1][5] = 1;
        data[1][6] = 1;
        data[2][5] = 1;
        data[2][6] = 1;
        data[11][5] = 1;
        data[11][6] = 1;
        data[11][7] = 1;
        data[12][4] = 1;
        data[12][8] = 1;
        data[13][3] = 1;
        data[13][9] = 1;
        data[14][3] = 1;
        data[14][9] = 1;
        data[15][6] = 1;
        data[16][4] = 1;
        data[16][8] = 1;
        data[17][5] = 1;
        data[17][6] = 1;
        data[17][7] = 1;
        data[18][6] = 1;
        data[21][3] = 1;
        data[21][4] = 1;
        data[21][5] = 1;
        data[22][3] = 1;
        data[22][4] = 1;
        data[22][5] = 1;
        data[23][2] = 1;
        data[23][6] = 1;
        data[25][1] = 1;
        data[25][2] = 1;
        data[25][6] = 1;
        data[25][7] = 1;
        data[35][3] = 1;
        data[35][4] = 1;
        data[36][3] = 1;
        data[36][4] = 1;

    }

    private void setup_random() {
        data = new int[size_x][size_y];
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[0].length; j++) {
                if (Math.random() < .5) {
                    data[i][j] = 1;
                }
            }
        }
    }

    public void popoulate_square() {
        setup_square();
    }

    public void populate_gun() {
        setup_gun();
    }

    public void clear() {
        setup_empty();
    }

    public void populate_random() {
        setup_random();
    }

    public void zoom_out() {
        int[][] data_copy = arr_arr_copy(data);
        data = new int[data_copy.length + 2][data_copy[0].length+2];
        size_x += 2;
        size_y += 2;

        for (int i = 1; i < data.length - 1; i++) {
            for (int j = 1; j < data[0].length - 1; j++) {
                data[i][j] = data_copy[i-1][j-1];
            }
        }
    }

    public void toggle_cell(int x, int y) {
        if (data[x][y] == 1) {
            data[x][y] = 0;
        } else {
            data[x][y] = 1;
        }
    }

    public void do_generation() {
        Board copy = copy();

        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[0].length; j++) {
                if (data[i][j] == 1) {

                    if (copy.neighbors(i, j) < 2) {
                        kill(i, j);
                    }
                    if (copy.neighbors(i, j) > 3) {
                        kill(i, j);
                    }
                } else {
                    if (copy.neighbors(i, j) == 3) {
                        give_life(i, j);
                    }
                }
            }
        }
    }

    public void randomize_rules() {
        for (int i = 0; i < rules.length; i++) {
            for (int j = 0; j < rules[0].length; j++) {
                rules[i][j] = (int)(Math.random()*6)+1;
            }
        }
    }

    public void reset_rules() {
        rules = new int[][] {
                {2, 3},
                {3, 3}
        };
    }

    public int[][] get_rules() {
        return rules;
    }

    public void do_better_generation() {
        int[][] neighbors = neighbor_copy();

        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[0].length; j++) {
                if (data[i][j] == 1) {

                    if (neighbors[i][j] < 2 || neighbors[i][j] > 3) {
                        kill(i, j);
                    }

                } else if (neighbors[i][j] == 3) {
                    give_life(i, j);
                }
            }
        }
    }

    public void do_best_generation() {
        int[][] neighbors = neighbor_copy();

        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[0].length; j++) {
                if (data[i][j] == 1) {

                    if (neighbors[i][j] < rules[0][0] || neighbors[i][j] > rules[0][1]) {
                        kill(i, j);
                    }

                } else if (neighbors[i][j] == rules[1][0] || neighbors[i][j] == rules[1][1]) {
                    give_life(i, j);
                }
            }
        }
    }

    public void do_experimental_generation() {
        int[][] neighbors = neighbor_copy();

        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[0].length; j++) {
                if (data[i][j] == 1) {

                    if (neighbors[i][j] < 2 || neighbors[i][j] > 3) {
                        kill(i, j);
                    }

                } else if (neighbors[i][j] == 3  || neighbors[i][j] == 2) {
                    give_life(i, j);
                }
            }
        }
    }

    private int[][] neighbor_copy() {
        int[][] neighbors = new int[data.length][data[0].length];
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[0].length; j++) {
                neighbors[i][j] = neighbors(i, j);
            }
        }
        return neighbors;
    }

    public void kill(int x, int y) {
        data[x][y] = 0;
    }

    public int[][] get_data() {
        return data;
    }

    public void give_life(int x, int y) {
        data[x][y] = 1;
    }

    public Board copy() {
        return new Board(size_x, size_y, arr_arr_copy(data));
    }

    private int[][] arr_arr_copy(int[][] array) {
        int[][] result = new int[array.length][array[0].length];

        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[0].length; j++) {
                result[i][j] = array[i][j];
            }
        }

        return result;
    }

    public int neighbors(int x, int y) {
        int neighbors = 0;

        for (int[] move: neighbor_moves) {
            if (valid_move(move, x, y)) {
                if (data[x + move[0]][y + move[1]] == 1) {
                    neighbors++;
                }
            }
        }
        return neighbors;
    }

    private boolean valid_move(int[] move, int x, int y) {
        if (x + move[0] < 0 || y + move[1] < 0 || x + move[0] >= size_x || y + move[1] >= size_y) {
            return false;
        }
        return true;
    }

    public void zoom_in() {
        int[][] data_copy = arr_arr_copy(data);
        data = new int[data_copy.length - 2][data_copy[0].length-2];
        size_x -= 2;
        size_y -= 2;

        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[0].length; j++) {
                data[i][j] = data_copy[i+1][j+1];
            }
        }
    }

    public void populate_line() {
        setup_line();
    }
}
