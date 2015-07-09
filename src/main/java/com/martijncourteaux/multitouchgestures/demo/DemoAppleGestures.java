/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.martijncourteaux.multitouchgestures.demo;

import com.apple.eawt.event.GestureUtilities;
import com.apple.eawt.event.MagnificationEvent;
import com.apple.eawt.event.RotationEvent;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.Line2D;
import javax.swing.JComponent;
import javax.swing.JFrame;

/**
 *
 * @author martijn
 */
public class DemoAppleGestures
{

    private static double a = 0, l = 50;
    private static double x, y;

    public static void main(String[] args)
    {

        JFrame frame = new JFrame();
        frame.setTitle("Apple Gestures Demo");
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

        comp.addMouseWheelListener(new MouseWheelListener()
        {

            @Override
            public void mouseWheelMoved(MouseWheelEvent e)
            {
                if (e.isShiftDown())
                {
                    x -= 3.0f * e.getPreciseWheelRotation();
                } else
                {
                    y -= 3.0f * e.getPreciseWheelRotation();
                }
                comp.repaint();
            }
        });

        GestureUtilities.addGestureListenerTo(comp, new com.apple.eawt.event.GestureAdapter()
        {

            @Override
            public void magnify(MagnificationEvent me)
            {
                l *= 1.0 + me.getMagnification();
                comp.repaint();
            }

            @Override
            public void rotate(RotationEvent re)
            {
                a -= Math.toRadians(re.getRotation());
                comp.repaint();
            }

        });
    }
}
