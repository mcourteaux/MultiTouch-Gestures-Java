/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.martijncourteaux.osxgestures4java;

/**
 *
 * @author martijn
 */
public class EventDispatch
{
    
    static
    {
        System.loadLibrary("OSXGestures4JavaJNI");
    }
    
    public native void init();
    
    public native void stop();
    
    public static void dispatchMagnifyGesture(double mouseX, double mouseY, double magnification)
    {
//        System.out.println("Mag: " + mouseX + " " + mouseY + " " + magnification);
    }
    
    public static void dispatchRotateGesture(double mouseX, double mouseY, double rotation)
    {
//        System.out.println("Rot: " + mouseX + " " + mouseY + " " + rotation);
    }
    
    
}
