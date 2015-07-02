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
public class ScrollGestureEvent extends GestureEvent
{
    private final double dX, dY;

    public ScrollGestureEvent(JComponent source, double mouseX, double mouseY, double absMouseX, double absMouseY, Phase phase, double dX, double dY)
    {
        super(source, mouseX, mouseY, absMouseX, absMouseY, phase);
        this.dX = dX;
        this.dY = dY;
    }

    public double getDeltaX()
    {
        return dX;
    }

    public double getDeltaY()
    {
        return dY;
    }
    
}
