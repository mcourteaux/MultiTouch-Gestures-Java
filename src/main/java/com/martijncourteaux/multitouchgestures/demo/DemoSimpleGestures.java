/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.martijncourteaux.multitouchgestures.demo;

import com.martijncourteaux.multitouchgestures.GestureAdapter;
import com.martijncourteaux.multitouchgestures.MultiTouchGestureUtilities;
import com.martijncourteaux.multitouchgestures.event.MagnifyGestureEvent;
import com.martijncourteaux.multitouchgestures.event.RotateGestureEvent;
import com.martijncourteaux.multitouchgestures.event.ScrollGestureEvent;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import javax.swing.JComponent;
import javax.swing.JFrame;

/**
 *
 * @author martijn
 */
public class DemoSimpleGestures
{

    private static double a = 0, l = 50;
    private static double x, y;
    
    public static void main(String[] args)
    {

        JFrame frame = new JFrame();
        frame.setTitle("MultiTouch Gestures Demo");
        final JComponent comp = new JComponent()
        {

            @Override
            protected void paintComponent(Graphics gg)
            {
                super.paintComponent(gg);
                Graphics2D g = (Graphics2D) gg;

                Line2D.Double line = new Line2D.Double(getWidth() * 0.5 + x, getHeight() * 0.5 + y, getWidth() * 0.5 + Math.cos(a) * l + x, getHeight() * 0.5 + Math.sin(a) * l + y);
                g.setColor(Color.red);
                g.setStroke(new BasicStroke(5.0f));
                g.draw(line);
            }

        };
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.add(comp, BorderLayout.CENTER);
        frame.setPreferredSize(new Dimension(300, 200));

        frame.setLocationRelativeTo(null);
        frame.pack();
        frame.setVisible(true);
        
        MultiTouchGestureUtilities.addGestureListener(comp, new GestureAdapter()
        {

            @Override
            public void magnify(MagnifyGestureEvent e)
            {
                l *= 1.0 + e.getMagnification();
                comp.repaint();
            }

            @Override
            public void rotate(RotateGestureEvent e)
            {
                a += e.getRotation();
                comp.repaint();
            }

            @Override
            public void scroll(ScrollGestureEvent e)
            {
                x += e.getDeltaX();
                y += e.getDeltaY();
                comp.repaint();
            }
            
        });
    }
}
