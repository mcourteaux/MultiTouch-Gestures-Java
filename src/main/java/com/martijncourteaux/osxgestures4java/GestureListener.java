/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.martijncourteaux.osxgestures4java;

import com.martijncourteaux.osxgestures4java.event.MagnifyGestureEvent;
import com.martijncourteaux.osxgestures4java.event.RotateGestureEvent;

/**
 *
 * @author martijn
 */
public interface GestureListener
{
    public void magnify(MagnifyGestureEvent e);
    
    public void rotate(RotateGestureEvent e);
}
