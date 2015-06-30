/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.martijncourteaux.osxgestures4java;

import javax.swing.JComponent;

/**
 *
 * @author martijn
 */
public class RotateGestureEvent extends GestureEvent 
{
    
    private final double rotation;

    public RotateGestureEvent(JComponent source, double mouseX, double mouseY, double absMouseX, double absMouseY, double rotation)
    {
        super(source, mouseX, mouseY, absMouseX, absMouseY);
        this.rotation = rotation;
    }

    public double getRotation()
    {
        return rotation;
    }
    
}
