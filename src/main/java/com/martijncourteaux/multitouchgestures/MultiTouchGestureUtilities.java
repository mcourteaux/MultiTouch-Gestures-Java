/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.martijncourteaux.multitouchgestures;

import com.martijncourteaux.multitouchgestures.event.GestureEvent;
import com.martijncourteaux.multitouchgestures.event.MagnifyGestureEvent;
import com.martijncourteaux.multitouchgestures.event.RotateGestureEvent;
import com.martijncourteaux.multitouchgestures.event.ScrollGestureEvent;
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
public class MultiTouchGestureUtilities
{

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

    protected static void dispatchMagnifyGesture(double mouseX, double mouseY, double magnification, GestureEvent.Phase phase)
    {
        if (listenerCount == 0)
        {
            return;
        }

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
                if (relP != null)
                {
                    MagnifyGestureEvent me = new MagnifyGestureEvent(c, relP.getX(), relP.getY(), mouseX, mouseY, phase, magnification);

                    for (GestureListener l : list)
                    {
                        l.magnify(me);
                    }
                }

                return;
            }
        }
    }

    protected static void dispatchRotateGesture(double mouseX, double mouseY, double rotation, GestureEvent.Phase phase)
    {
        if (listenerCount == 0)
        {
            return;
        }

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
                if (relP != null)
                {
                    RotateGestureEvent re = new RotateGestureEvent(c, relP.getX(), relP.getY(), mouseX, mouseY, phase, rotation);

                    for (GestureListener l : list)
                    {
                        l.rotate(re);
                    }
                }

                return;
            }
        }
    }

    protected static void dispatchScrollGesture(double mouseX, double mouseY, double dX, double dY, GestureEvent.Phase phase)
    {
        if (listenerCount == 0)
        {
            return;
        }

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
                if (relP != null)
                {
                    ScrollGestureEvent se = new ScrollGestureEvent(c, relP.getX(), relP.getY(), mouseX, mouseY, phase, dX, dY);

                    for (GestureListener l : list)
                    {
                        l.scroll(se);
                    }
                }

                return;
            }
        }
    }

}
