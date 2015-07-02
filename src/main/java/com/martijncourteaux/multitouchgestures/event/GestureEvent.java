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
public class GestureEvent
{
    
    public enum Phase
    {
        MOMENTUM(0), BEGIN(1), CHANGED(2), END(3), CANCELLED(4), OTHER(-1);
        
        private final int code;

        private Phase(int code)
        {
            this.code = code;
        }

        public int getCode()
        {
            return code;
        }
        
        public static Phase getByCode(int code)
        {
            for (Phase p : values())
            {
                if (p.getCode() == code) return p;
            }
            return OTHER;
        }
    }
    
    private final JComponent source;
    private final double mouseX, mouseY;
    private final double absMouseX, absMouseY;
    private final Phase phase;

    public GestureEvent(JComponent source, double mouseX, double mouseY, double absMouseX, double absMouseY, Phase phase)
    {
        this.source = source;
        this.mouseX = mouseX;
        this.mouseY = mouseY;
        this.absMouseX = absMouseX;
        this.absMouseY = absMouseY;
        this.phase = phase;
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

    public Phase getPhase()
    {
        return phase;
    }
}
