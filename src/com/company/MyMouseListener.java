package com.company;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MyMouseListener implements MouseListener {
    private int scale;
    private Board board;
    private int size_x, size_y;
    private MyPanel panel;
    private Color color;

    public MyMouseListener(Board board, int scale, int size_x, int size_y, MyPanel panel, Color color) {
        super();
        this.scale = scale;
        this.board = board;
        this.size_x = size_x;
        this.size_y = size_y;
        this.panel = panel;
        this.color = color;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        System.out.println((double)e.getX()/scale);
        int x = (int) ((double)e.getX()/scale);
        int y = (int) ((double)(e.getY() - 55)/scale);
        board.toggle_cell(x, y);
        panel.paint(panel.getGraphics(), scale, new int[] {x, y}, color, board.get_data()[x][y]);

    }

    public void fix(Board board, int scale, int size_x, int size_y, MyPanel panel, Color color) {
        this.scale = scale;
        this.board = board;
        this.size_x = size_x;
        this.size_y = size_y;
        this.panel = panel;
        this.color = color;
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
