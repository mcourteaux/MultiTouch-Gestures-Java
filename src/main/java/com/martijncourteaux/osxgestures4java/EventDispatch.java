/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.martijncourteaux.osxgestures4java;

import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.SwingUtilities;

/**
 *
 * @author martijn
 */
class EventDispatch
{

    private static Thread gestureEventThread;

    static
    {
        System.loadLibrary("OSXGestures4JavaJNI");
    }

    public static native void init();

    private static native void start();

    public static native void stop();

    public static void startInSeperateThread()
    {
        if (gestureEventThread != null)
        {
            if (gestureEventThread.isAlive())
            {
                return;
            }
        }

        gestureEventThread = new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                init();
                start();
            }
        }, "Gesture Event Thread");
        gestureEventThread.start();
    }

    public static void dispatchMagnifyGesture(final double mouseX, final double mouseY, final double magnification, final int phase)
    {
        SwingUtilities.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
                OSXGestureUtilities.dispatchMagnifyGesture(mouseX, d.height - mouseY, magnification);
            }
        });

    }

    public static void dispatchRotateGesture(final double mouseX, final double mouseY, final double rotation, final int phase)
    {
        SwingUtilities.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
                OSXGestureUtilities.dispatchRotateGesture(mouseX, d.height - mouseY, -Math.toRadians(rotation));
            }
        });
    }

    public static void dispatchScrollWheelEvent(double mouseX, double mouseY, double deltaX, double deltaY)
    {
        System.out.println("Scroll: " + deltaX + " " + deltaY);
    }

}
