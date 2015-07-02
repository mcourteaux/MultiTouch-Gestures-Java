/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.martijncourteaux.multitouchgestures;

import com.martijncourteaux.multitouchgestures.event.MagnifyGestureEvent;
import com.martijncourteaux.multitouchgestures.event.RotateGestureEvent;
import com.martijncourteaux.multitouchgestures.event.ScrollGestureEvent;

/**
 *
 * @author martijn
 */
public abstract class GestureAdapter implements GestureListener
{

    @Override
    public void magnify(MagnifyGestureEvent e)
    {
    }

    @Override
    public void rotate(RotateGestureEvent e)
    {
    }

    @Override
    public void scroll(ScrollGestureEvent e)
    {
    }
    
    
}
