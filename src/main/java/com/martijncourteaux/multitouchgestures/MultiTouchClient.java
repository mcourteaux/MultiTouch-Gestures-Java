/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.martijncourteaux.multitouchgestures;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComponent;

/**
 *
 * @author martijn
 */
class MultiTouchClient implements MouseMotionListener, MouseListener
{

    private final JComponent component;
    private final List<GestureListener> listeners;
    private boolean inside;

    public MultiTouchClient(JComponent component)
    {
        this.component = component;
        this.listeners = new ArrayList<GestureListener>();
    }

    public void attachListeners()
    {
        inside = false;
        component.addMouseListener(this);
        component.addMouseMotionListener(this);
    }

    public void detachListeners()
    {
        inside = false;
        component.removeMouseListener(this);
        component.removeMouseMotionListener(this);
    }

    public JComponent getComponent()
    {
        return component;
    }

    public List<GestureListener> getListeners()
    {
        return listeners;
    }

    public boolean isInside()
    {
        return inside;
    }

    @Override
    public void mouseDragged(MouseEvent e)
    {
        inside = true;
        if (e.getX() < 0 || component.getWidth() >= e.getX())
        {
            inside = false;
        }
        if (e.getY() < 0 || component.getHeight() >= e.getY())
        {
            inside = false;
        }
    }

    @Override
    public void mouseMoved(MouseEvent e)
    {
        inside = true;
    }

    @Override
    public void mouseClicked(MouseEvent e)
    {
        inside = true;
    }

    @Override
    public void mousePressed(MouseEvent e)
    {
        inside = true;
    }

    @Override
    public void mouseReleased(MouseEvent e)
    {
        inside = true;
        if (e.getX() < 0 || component.getWidth() >= e.getX())
        {
            inside = false;
        }
        if (e.getY() < 0 || component.getHeight() >= e.getY())
        {
            inside = false;
        }
    }

    @Override
    public void mouseEntered(MouseEvent e)
    {
        inside = true;
    }

    @Override
    public void mouseExited(MouseEvent e)
    {
        inside = false;
    }

}
