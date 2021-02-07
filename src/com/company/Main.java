package com.company;

import javax.swing.*;
import java.awt.*;

public class Main {
    private static int board_size_x = 100;
    private static int board_size_y = 101;
    private static int frame_size = 1900;
    public static int delay = 0;
    public static boolean paused = false;
    private static int scale, frame_size_x, frame_size_y;
    private static Color color;
    private static JFrame frame;
    private static MyListener listener;
    private static MyMouseListener mouse_listener;
    private static MyPanel panel;
    private static Board board;
    public static boolean standard_generation;

    public static void main(String[] args) throws InterruptedException {
        board = new Board(board_size_x, board_size_y);

        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        standard_generation = true;




        if ((int) ((double) (frame_size + 1500)/board_size_x) > (int) ((double) frame_size/board_size_y)) {
            scale = (int) ((double)frame_size/board_size_y);
        } else {
            scale = (int) ((double)(frame_size + 1500)/board_size_x);
        }
        frame_size_x = scale*board_size_x + 8;
        frame_size_y = scale*board_size_y + 70;

        frame.setSize(frame_size_x, frame_size_y);

        color = new Color((int) (255*(1 - Math.pow(Math.random(), 3))), (int) (255*(1 - Math.pow(Math.random(), 3))), (int) (255*(1 - Math.pow(Math.random(), 3))));

        panel = new MyPanel();
        frame.add(panel);
        frame.setVisible(true);
        frame.setResizable(false);
        panel.setBackground(new Color(50, 53, 53));
        listener = new MyListener(board, panel, scale, color);
        mouse_listener = new MyMouseListener(board, scale, frame_size_x, frame_size_y, panel, color);
        frame.addKeyListener(listener);
        frame.addMouseListener(mouse_listener);



        Thread.sleep(100);

        while (true) {
            if (!paused) {
                int[][] data = board.get_data();

                paint(panel, data, scale, color);

                //long time = System.nanoTime();
                board.do_best_generation();
                //System.out.println(System.nanoTime() - time);
                Thread.sleep(delay);
                int[][] rules = board.get_rules();
                frame.setTitle("Conway, size: " + board_size_x + " by " + board_size_y + ", Delay: " + delay + "Rules:" + rules_to_string(board.get_rules()));

            } else {
                Thread.sleep(1);
            }
        }
    }

    public static String rules_to_string(int[][] rules) {
        String str_rules = "";
        for (int i = 0; i < rules.length; i++) {
            for (int j = 0; j < rules[0].length; j++) {
                str_rules += " " + rules[i][j];
            }
        }
        return str_rules;
    }

    public static void paint(MyPanel panel, int[][] data, int scale, Color color) {
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[0].length; j++) {
                panel.paint(panel.getGraphics(), scale, new int[]{i, j}, color, data[i][j]);
            }
        }
    }

    public static void setup() {
        if ((int) ((double) (frame_size + 1500)/board_size_x) > (int) ((double) frame_size/board_size_y)) {
            scale = (int) ((double)frame_size/board_size_y);
        } else {
            scale = (int) ((double)(frame_size + 1500)/board_size_x);
        }
        frame_size_x = scale*board_size_x + 8;
        frame_size_y = scale*board_size_y + 70;

        frame.setSize(frame_size_x, frame_size_y);

        listener.fix(board, panel, scale, color);
        mouse_listener.fix(board, scale, frame_size_x, frame_size_y, panel, color);

    }

    public static void zoom_out() {
        board_size_x += 2;
        board_size_y += 2;
        setup();
    }

    public static void zoom_in() {
        board_size_x -= 2;
        board_size_y -= 2;
        setup();
    }

}
