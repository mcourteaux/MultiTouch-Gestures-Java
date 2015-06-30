//
//  EventDispatch.cpp
//  OSXGestures4JavaJNI
//
//  Created by Martijn Courteaux on 30/06/15.
//  Copyright © 2015 Martijn Courteaux. All rights reserved.
//

#include <stdio.h>

#import <AppKit/AppKit.h>

#include "EventDispatch.h"

JNIEnv *env;
jclass jc_EventDispatch;
jmethodID jm_dispatchMagnifyGesture;
jmethodID jm_dispatchRotateGesture;


NSEventMask eventMask =
NSEventMaskGesture |
NSEventMaskMagnify|
NSEventMaskSwipe |
NSEventMaskRotate |
NSEventMaskBeginGesture |
NSEventMaskEndGesture;

CGEventRef eventTapCallback(CGEventTapProxy proxy, CGEventType type, CGEventRef eventRef, void *refcon) {
    // convert the CGEventRef to an NSEvent
    NSEvent *event = [NSEvent eventWithCGEvent:eventRef];
    
    // filter out events which do not match the mask
    if (!(eventMask & NSEventMaskFromType([event type]))) { return [event CGEvent]; }
    
    // do stuff
   // NSLog(@"eventTapCallback: [event type] = %d", [event type]);
    
    NSPoint m = [NSEvent mouseLocation];
    
    switch ([event type])
    {
        case NSEventTypeBeginGesture:
            NSLog(@"Event begin");
            break;
        case NSEventTypeEndGesture:
            NSLog(@"Gesture end");
            break;
        case NSEventTypeGesture:
            //NSLog(@"Buh: %f, %f", event.deltaX, event.deltaY);
            break;
        case NSEventTypeMagnify:
            env->CallStaticVoidMethod(jc_EventDispatch, jm_dispatchMagnifyGesture, (double) m.x, (double) m.y, event.magnification);
            break;
        case NSEventTypeSwipe:
            NSLog(@"Swipe: X = %10.6f; Y = %10.6f", event.deltaX, event.deltaY);
            break;
        case NSEventTypeRotate:
            env->CallStaticVoidMethod(jc_EventDispatch, jm_dispatchRotateGesture, (double) m.x, (double) m.y, event.rotation);
            break;
        default:
            NSLog(@"Gesture type: %d", [event type]);
            break;
    }
    
    // return the CGEventRef
    return [event CGEvent];
}

void initCGEventTap() {
    CFMachPortRef eventTap = CGEventTapCreate(kCGSessionEventTap, kCGHeadInsertEventTap, kCGEventTapOptionListenOnly, kCGEventMaskForAllEvents, eventTapCallback, nil);
    CFRunLoopAddSource(CFRunLoopGetCurrent(), CFMachPortCreateRunLoopSource(kCFAllocatorDefault, eventTap, 0), kCFRunLoopCommonModes);
    CGEventTapEnable(eventTap, true);
    CFRunLoopRun();
}

void loadJavaReferences(JNIEnv *env)
{
    ::env = env;
    jc_EventDispatch = env->FindClass("com/martijncourteaux/osxgestures4java/EventDispatch");
    jm_dispatchMagnifyGesture = env->GetStaticMethodID(jc_EventDispatch, "dispatchMagnifyGesture", "(DDD)V");
    jm_dispatchRotateGesture = env->GetStaticMethodID(jc_EventDispatch, "dispatchRotateGesture", "(DDD)V");
}

void JNICALL Java_com_martijncourteaux_osxgestures4java_EventDispatch_init(JNIEnv *env, jobject)
{
    printf("Init called!!!\n");
    loadJavaReferences(env);
    initCGEventTap();
}

void JNICALL Java_com_martijncourteaux_osxgestures4java_EventDispatch_stop(JNIEnv *, jobject)
{
    printf("Stop called!\n");
}