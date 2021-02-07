package com.company;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class MyListener implements KeyListener {
    private Board board;
    private MyPanel panel;
    private int scale;
    private Color color;


    public MyListener(Board board, MyPanel panel, int scale, Color color) {
        super();
        this.board = board;
        this.panel = panel;
        this.scale = scale;
        this.color = color;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        System.out.println(e.getKeyCode());

        if (e.getKeyCode() == 32) { // space
            if (Main.paused) {
                //System.out.println("oasijd");
                Main.paused = false;
            } else {
                Main.paused = true;
            }
        } else if (e.getKeyCode() == 80) { // p
            board.clear();
            panel.repaint();
        } else if (e.getKeyCode() == 79) { // o
            board.do_best_generation();
            Main.paint(panel, board.get_data(), scale, color);
        } else if (e.getKeyCode() == 69) { // e
            board.reset_rules();
        } else if (e.getKeyCode() == 38) { // up arrow
            Main.delay += 5;
        } else if (e.getKeyCode() == 40) { // down arrow
            if (Main.delay > 4) {
                Main.delay -= 5;
            } else if (Main.delay > 0) {
                Main.delay = 0;
            }
        } else if (e.getKeyCode() == 73) { // i
            board.populate_random();
            Main.paint(panel, board.get_data(), scale, color);
        } else if (e.getKeyCode() == 37) { // left arrow
            board.zoom_out();
            Main.zoom_out();
            /*
            try {
                Thread.sleep(100);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            */
            //Main.paint(panel, board.get_data(), scale, color);
        } else if (e.getKeyCode() == 39) { // right arrow
            board.zoom_in();
            Main.zoom_in();
            //Main.paint(panel, board.get_data(), scale, color);
        } else if (e.getKeyCode() == 71) { // g
            board.populate_gun();
            Main.paint(panel, board.get_data(), scale, color);
        } else if (e.getKeyCode() == 76) { // l
            board.populate_line();
            Main.paint(panel, board.get_data(), scale, color);
        } else if (e.getKeyCode() == 83) {
            board.popoulate_square();
            Main.paint(panel, board.get_data(), scale, color);
        } else if (e.getKeyCode() == 82) {
            board.randomize_rules();
        }

    }

    public void fix(Board board, MyPanel panel, int scale, Color color) {
        this.board = board;
        this.panel = panel;
        this.scale = scale;
        this.color = color;
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
