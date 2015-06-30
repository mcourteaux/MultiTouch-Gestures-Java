/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.martijncourteaux.osxgestures4java;

import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JComponent;
import java.util.List;
/**
 *
 * @author martijn
 */
public class OSXGestureUtilities
{
    private final static HashMap<JComponent, List<GestureListener>> listeners = new HashMap<JComponent, List<GestureListener>>();
    
    
    public static void addGestureListener(JComponent component, GestureListener listener)
    {
        List<GestureListener> list = listeners.get(component);
        if (list == null)
        {
            list = new ArrayList<GestureListener>();
            listeners.put(component, list);
        }
        
        list.add(listener);
    }
    
    public static boolean removeGestureListener(JComponent component, GestureListener listener)
    {
        List<GestureListener> list = listeners.get(component);
        if (list == null)
        {
            return false;
        }
        
        return list.remove(listener);
    }
}
