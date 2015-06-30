/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.martijncourteaux.osxgestures4java;

import com.martijncourteaux.osxgestures4java.event.MagnifyGestureEvent;
import com.martijncourteaux.osxgestures4java.event.RotateGestureEvent;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JComponent;
import java.util.List;
/**
 *
 * @author martijn
 */
public class OSXGestureUtilities
{
    
    static
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
    
    private final static HashMap<JComponent, List<GestureListener>> listeners = new HashMap<JComponent, List<GestureListener>>();
    private static int listenerCount = 0;

    public static int getListenerCount()
    {
        return listenerCount;
    }
    
    public static void addGestureListener(JComponent component, GestureListener listener)
    {
        if (listenerCount == 0)
        {
            EventDispatch.startInSeperateThread();
        }
        List<GestureListener> list = listeners.get(component);
        if (list == null)
        {
            list = new ArrayList<GestureListener>();
            listeners.put(component, list);
        }
        
        list.add(listener);
        listenerCount++;
    }
    
    public static boolean removeGestureListener(JComponent component, GestureListener listener)
    {
        List<GestureListener> list = listeners.get(component);
        if (list == null)
        {
            return false;
        }
        
        if (list.remove(listener))
        {
            listenerCount--;
            if (listenerCount == 0)
            {
                EventDispatch.stop();
            }
            return true;
        }
        return false;
    }
    
    protected static void dispatchMagnifyGesture(double mouseX, double mouseY, double magnification)
    {
        if (listenerCount == 0) return;
        
        int mXi = (int) Math.round(mouseX);
        int mYi = (int) Math.round(mouseY);
        
        for (HashMap.Entry<JComponent, List<GestureListener>> e : listeners.entrySet())
        {
            JComponent c = e.getKey();
            Rectangle r = new Rectangle(c.getLocationOnScreen(), c.getSize());
            if (r.contains(mXi, mYi))
            {
                List<GestureListener> list = e.getValue();
                
                Point relP = c.getMousePosition(true);
                
                MagnifyGestureEvent me = new MagnifyGestureEvent(c, relP.getX(), relP.getY(), mouseX, mouseY, magnification);
                
                for (GestureListener l : list)
                {
                    l.magnify(me);
                }
                
                return;
            }
        }
    }
    
    protected static void dispatchRotateGesture(double mouseX, double mouseY, double rotation)
    {
        if (listenerCount == 0) return;
        
        
        int mXi = (int) Math.round(mouseX);
        int mYi = (int) Math.round(mouseY);
        
        for (HashMap.Entry<JComponent, List<GestureListener>> e : listeners.entrySet())
        {
            JComponent c = e.getKey();
            Rectangle r = new Rectangle(c.getLocationOnScreen(), c.getSize());
            if (r.contains(mXi, mYi))
            {
                List<GestureListener> list = e.getValue();
                
                Point relP = c.getMousePosition(true);
                
                RotateGestureEvent re = new RotateGestureEvent(c, relP.getX(), relP.getY(), mouseX, mouseY, rotation);
                
                for (GestureListener l : list)
                {
                    l.rotate(re);
                }
                
                return;
            }
        }
    }
}
