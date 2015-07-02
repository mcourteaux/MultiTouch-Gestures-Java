/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.martijncourteaux.multitouchgestures.event;

import javax.swing.JComponent;

/**
 *
 * @author martijn
 */
public class RotateGestureEvent extends GestureEvent 
{
    
    private final double rotation;

    public RotateGestureEvent(JComponent source, double mouseX, double mouseY, double absMouseX, double absMouseY, Phase phase, double rotation)
    {
        super(source, mouseX, mouseY, absMouseX, absMouseY, phase);
        this.rotation = rotation;
    }

    public double getRotation()
    {
        return rotation;
    }
    
}
