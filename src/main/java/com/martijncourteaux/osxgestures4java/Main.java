/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.martijncourteaux.osxgestures4java;

import java.io.File;

/**
 *
 * @author martijn
 */
public class Main
{
    
    public static void main(String[] args) {
        System.out.println(new File(".").getAbsoluteFile());
        EventDispatch d = new EventDispatch();
        d.init();
        
        try {
            Thread.sleep(5000);
        } catch (Exception e) {
        }
        
        d.stop();
    }
}
