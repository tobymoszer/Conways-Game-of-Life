package com.company;

import javax.swing.*;
import java.awt.*;

public class MyPanel extends JPanel {

    public void paint(Graphics g, int scale, int[] position, Color color, int liveliness) {


        if (liveliness == 1) {
            g.setColor(color);
        } else {
            g.setColor(new Color(50, 53, 53));
        }
        g.fillRect(scale*position[0], scale*position[1], scale, scale);

    }



}
