
MultiTouch Gestures for Java
============================

This project aims to provide an easy way to enable MultiTouch touchpad gestures for Java in Swing. This project
was originally started as an alternative for the AppleJavaExtensions eawt package, since Apple dropt
the support for native OS X features in Java.

Supported Platforms
-------------------
Since my personal need for now is only OS X, this is supported. However, if anyone needs support
for other platforms as well, feel free to fork and create a corresponding native source file for
your platform.


Building
--------

### Java
This project is a Maven project made in NetBeans on the Java side of it. So just open this in NetBeans
and you are basically good to go. You can compile this manually as well.

### Native Mac OS X
This is an xcode project that resides in the mac/ directory. Just open the project in xcode and hit
"Run". This will produce the native library file (ending in .dylib) on the right location.

Testing
-------
There is a test file provided in:

    src/main/java/com/martijncourteaux/multitouchgestures/demo/DemoSimpleGestures.java

You can have a look at that file for a sample.

Usage
-----
The usage is pretty similar to Apple's AppleJavaExtensions package from Java 6. You build your
Swing frame, just as regular. Then you can do this:

    MultiTouchGestureUtilities.addGestureListener(comp, listener);

Where `listener` is a `MultiTouchGestureListener` and `comp` is the JComponent you want to add
the listener to.

The `MultiTouchGestureListener` interface has these methods:

    public void magnify(MagnifyGestureEvent e);
    public void rotate(RotateGestureEvent e);
    public void scroll(ScrollGestureEvent e);

Where each event type has these parameters:

 - `mouseX`: x-coordinate of the mouse in the component space.
 - `mouseX`: y-coordinate of the mouse in the component space.
 - `absMouseX`: x-coordinate of the mouse on the screen
 - `absMouseY`: y-coordinate of the mouse on the screen

Remark for OS X
---------------
The built-in Java scrolling listeners are not as smooth as the one provided in this project.
So I highly recommend checking it out. This gives you the native OS X scrolling experience.


