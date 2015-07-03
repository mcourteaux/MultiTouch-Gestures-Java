/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.martijncourteaux.multitouchgestures;

import com.martijncourteaux.multitouchgestures.event.GestureEvent.Phase;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import javax.swing.SwingUtilities;

/**
 *
 * @author martijn
 */
class EventDispatch
{

    private static final boolean supported;
    private static Thread gestureEventThread;

    private static void printNativeNotFound(UnsatisfiedLinkError err, String name)
    {
        System.err.println(err);
        System.err.println("Put the " + name + " file in one of these folders:");
        String[] paths = System.getProperty("java.library.path").split(":");
        for (String path : paths)
        {
            System.err.println("\t" + path);
        }
        System.err.println();
    }

    private static void extractNative(String nativeName)
    {
        try
        {
            System.out.print("Extracting native library");

            InputStream is = EventDispatch.class.getResourceAsStream(nativeName);
            if (is == null)
            {
                System.out.println(" [ERROR] Could not load library.");
                return;
            }
            File outFile = new File("./" + nativeName);
            OutputStream os = new FileOutputStream(outFile);
            byte[] buff = new byte[1024 * 8];
            int bytesRead;
            while ((bytesRead = is.read(buff)) != -1)
            {
                os.write(buff, 0, bytesRead);
                System.out.print(".");
            }
            System.out.println();

            os.flush();
            os.close();
            is.close();
            outFile.deleteOnExit();
        } catch (Exception e)
        {
            System.out.println(" [ERROR] Could not load library:");
            e.printStackTrace();
        }
    }

    static
    {
        boolean supp = false;
        String os = System.getProperty("os.name").toLowerCase();
        if (os.contains("mac os x"))
        {
            try
            {
                extractNative("libmtg_mac.dylib");
                System.loadLibrary("mtg_mac");
                supp = true;
            } catch (UnsatisfiedLinkError err)
            {
                printNativeNotFound(err, "libmtg_mac.dylib");
            }
        } else
        {
            System.out.println("[MULTITOUCH GESTURES] Only Mac OS X is supported at the moment.");
        }

        if (supp)
        {
            Runtime.getRuntime().addShutdownHook(new Thread(new Runnable()
            {

                @Override
                public void run()
                {
                    System.out.println("In shutdownhook. Stopping Event Tap.");
                    EventDispatch.stop();
                }
            }));
        }

        supported = supp;
    }

    public static native void init();

    private static native void start();

    public static native void stop();

    public static void startInSeperateThread()
    {
        if (!supported)
        {
            return;
        }

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
                MultiTouchGestureUtilities.dispatchMagnifyGesture(mouseX, d.height - mouseY, magnification, Phase.getByCode(phase));
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
                MultiTouchGestureUtilities.dispatchRotateGesture(mouseX, d.height - mouseY, -Math.toRadians(rotation), Phase.getByCode(phase));
            }
        });
    }

    public static void dispatchScrollWheelEvent(final double mouseX, final double mouseY, final double deltaX, final double deltaY, final int phase)
    {
        SwingUtilities.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
                MultiTouchGestureUtilities.dispatchScrollGesture(mouseX, d.height - mouseY, deltaX, deltaY, Phase.getByCode(phase));
            }
        });
    }

}
