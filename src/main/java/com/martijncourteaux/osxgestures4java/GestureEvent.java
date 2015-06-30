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
public class GestureEvent
{
    private final JComponent source;
    private final double mouseX, mouseY;
    private final double absMouseX, absMouseY;

    public GestureEvent(JComponent source, double mouseX, double mouseY, double absMouseX, double absMouseY)
    {
        this.source = source;
        this.mouseX = mouseX;
        this.mouseY = mouseY;
        this.absMouseX = absMouseX;
        this.absMouseY = absMouseY;
    }

    public JComponent getSource()
    {
        return source;
    }

    public double getMouseX()
    {
        return mouseX;
    }

    public double getMouseY()
    {
        return mouseY;
    }

    public double getAbsMouseX()
    {
        return absMouseX;
    }

    public double getAbsMouseY()
    {
        return absMouseY;
    }
    
}
