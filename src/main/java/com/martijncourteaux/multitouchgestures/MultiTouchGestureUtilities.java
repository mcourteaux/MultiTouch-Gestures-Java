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
import java.util.HashMap;
import javax.swing.JComponent;
import java.util.List;
import java.util.Map;
import javax.swing.SwingUtilities;

/**
 *
 * @author martijn
 */
public class MultiTouchGestureUtilities
{

    private final static HashMap<JComponent, MultiTouchClient> clients = new HashMap<JComponent, MultiTouchClient>();
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
        MultiTouchClient client = clients.get(component);
        if (client == null)
        {
            client = new MultiTouchClient(component);
            client.attachListeners();
            clients.put(component, client);
        }
        List<GestureListener> list = client.getListeners();

        list.add(listener);
        listenerCount++;
    }

    public static boolean removeGestureListener(JComponent component, GestureListener listener)
    {
        MultiTouchClient client = clients.get(component);
        if (client == null)
        {
            return false;
        }
        List<GestureListener> list = client.getListeners();
        if (list == null)
        {
            return false;
        }

        if (list.remove(listener))
        {
            if (list.isEmpty())
            {
                client.detachListeners();
                clients.remove(component);
            }
            listenerCount--;
            if (listenerCount == 0)
            {
                EventDispatch.stop();
            }
            return true;
        }
        return false;
    }
    
    public static int removeAllGestureListeners(JComponent component)
    {
        MultiTouchClient client = clients.get(component);
        if (client == null)
        {
            return 0;
        }
        client.detachListeners();
        clients.remove(component);
        List<GestureListener> list = client.getListeners();
        if (list == null)
        {
            return 0;
        }
        int c = list.size();
        list.clear();
        return c;
    }

    protected static void dispatchMagnifyGesture(double mouseX, double mouseY, double magnification, GestureEvent.Phase phase)
    {
        if (listenerCount == 0)
        {
            return;
        }

        int mXi = (int) Math.round(mouseX);
        int mYi = (int) Math.round(mouseY);

        for (Map.Entry<JComponent, MultiTouchClient> e : clients.entrySet())
        {
            JComponent c = e.getKey();
            Rectangle r = new Rectangle(c.getLocationOnScreen(), c.getSize());
            if (r.contains(mXi, mYi))
            {
                MultiTouchClient client = e.getValue();
                if (client.isInside())
                {
                    List<GestureListener> list = client.getListeners();

                    Point relP = new Point(mXi, mYi);
                    SwingUtilities.convertPointFromScreen(relP, c);
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
    }

    protected static void dispatchRotateGesture(double mouseX, double mouseY, double rotation, GestureEvent.Phase phase)
    {
        if (listenerCount == 0)
        {
            return;
        }

        int mXi = (int) Math.round(mouseX);
        int mYi = (int) Math.round(mouseY);

        for (HashMap.Entry<JComponent, MultiTouchClient> e : clients.entrySet())
        {
            JComponent c = e.getKey();
            Rectangle r = new Rectangle(c.getLocationOnScreen(), c.getSize());
            if (r.contains(mXi, mYi))
            {
                MultiTouchClient client = e.getValue();
                if (client.isInside())
                {
                    List<GestureListener> list = client.getListeners();

                    Point relP = new Point(mXi, mYi);
                    SwingUtilities.convertPointFromScreen(relP, c);
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
    }

    protected static void dispatchScrollGesture(double mouseX, double mouseY, double dX, double dY, GestureEvent.Phase phase)
    {
        if (listenerCount == 0)
        {
            return;
        }

        int mXi = (int) Math.round(mouseX);
        int mYi = (int) Math.round(mouseY);

        for (HashMap.Entry<JComponent, MultiTouchClient> e : clients.entrySet())
        {
            JComponent c = e.getKey();
            Rectangle r = new Rectangle(c.getLocationOnScreen(), c.getSize());
            if (r.contains(mXi, mYi))
            {
                MultiTouchClient client = e.getValue();
                if (client.isInside())
                {
                    List<GestureListener> list = client.getListeners();

                    Point relP = new Point(mXi, mYi);
                    SwingUtilities.convertPointFromScreen(relP, c);
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

}
