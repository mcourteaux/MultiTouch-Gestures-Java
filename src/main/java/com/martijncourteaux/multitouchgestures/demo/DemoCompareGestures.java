/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.martijncourteaux.multitouchgestures.demo;

import com.apple.eawt.event.GestureUtilities;
import com.apple.eawt.event.MagnificationEvent;
import com.apple.eawt.event.RotationEvent;
import com.martijncourteaux.multitouchgestures.GestureAdapter;
import com.martijncourteaux.multitouchgestures.MultiTouchGestureUtilities;
import com.martijncourteaux.multitouchgestures.event.MagnifyGestureEvent;
import com.martijncourteaux.multitouchgestures.event.RotateGestureEvent;
import com.martijncourteaux.multitouchgestures.event.ScrollGestureEvent;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.Line2D;
import javax.swing.JComponent;
import javax.swing.JFrame;

/**
 *
 * @author martijn
 */
public class DemoCompareGestures
{
    
    private static double a = 0, l = 50;
    private static double x, y;
    
    private static double aa = 0, al = 50;
    private static double ax, ay;
    
    public static void main(String args[])
    {
        JFrame frame = new JFrame();
        frame.setTitle("MultiTouch left, Apple right");
        final JComponent compMT = new JComponent()
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
        final JComponent compApple = new JComponent()
        {

            @Override
            protected void paintComponent(Graphics gg)
            {
                super.paintComponent(gg);
                Graphics2D g = (Graphics2D) gg;

                Line2D.Double line = new Line2D.Double(getWidth() * 0.5 + ax, getHeight() * 0.5 + ay, getWidth() * 0.5 + Math.cos(aa) * al + ax, getHeight() * 0.5 + Math.sin(aa) * al + ay);
                g.setColor(Color.red);
                g.setStroke(new BasicStroke(5.0f));
                g.draw(line);
            }

        };
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(1, 2));
        frame.add(compMT);
        frame.add(compApple);
        frame.setPreferredSize(new Dimension(300, 200));

        frame.setLocationRelativeTo(null);
        frame.pack();
        frame.setVisible(true);

        MultiTouchGestureUtilities.addGestureListener(compMT, new GestureAdapter()
        {

            @Override
            public void magnify(MagnifyGestureEvent e)
            {
                l *= 1.0 + e.getMagnification();
                compMT.repaint();
            }

            @Override
            public void rotate(RotateGestureEvent e)
            {
                a += e.getRotation();
                compMT.repaint();
            }

            @Override
            public void scroll(ScrollGestureEvent e)
            {
                x += e.getDeltaX();
                y += e.getDeltaY();
                compMT.repaint();
            }

        });
        
        compApple.addMouseWheelListener(new MouseWheelListener()
        {

            @Override
            public void mouseWheelMoved(MouseWheelEvent e)
            {
                if (e.isShiftDown())
                {
                    ax -= 3.0f * e.getPreciseWheelRotation();
                } else
                {
                    ay -= 3.0f * e.getPreciseWheelRotation();
                }
                compApple.repaint();
            }
        });

        GestureUtilities.addGestureListenerTo(compApple, new com.apple.eawt.event.GestureAdapter()
        {

            @Override
            public void magnify(MagnificationEvent me)
            {
                al *= 1.0 + me.getMagnification();
                compApple.repaint();
            }

            @Override
            public void rotate(RotationEvent re)
            {
                aa -= Math.toRadians(re.getRotation());
                compApple.repaint();
            }

        });
    }
}
