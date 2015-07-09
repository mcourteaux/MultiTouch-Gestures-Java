
MultiTouch Gestures for Java
============================

This project aims to provide an easy way to enable MultiTouch touchpad gestures for Java in Swing. This project
was originally started as an alternative for the AppleJavaExtensions `com.apple.eawt` package.

Read this is you came here because `com.apple.eawt` is not working
------------------------------------------------------------------

You landed on this page, probably because you are unable to make `com.apple.eawt` compile
on newer versions of Java (JDK 7 and higher). However after creating this project, I found that there is
a workaround. You should have a look at this question on StackOverflow:
[Using internal sun classes with javac](https://stackoverflow.com/questions/4065401/using-internal-sun-classes-with-javac).
All the classes in the `com.apple.eawt` package are not included in the `$JAVA_HOME/lib/ct.sym` file, which makes
the compilation fail with an error like: 

    com.apple.eawt can not find package
    package com.apple.eawt does not exist

Adding `-XDignore.symbol.file` to your compiler flags solves it.

However, this project still has a purpose, I believe, since you can have smooth two finger scrolling.
The Apple gesture features don't report scroll events, which are way smoother than the ones you get using
a classic `MouseWheelListener`.


Supported Platforms
-------------------
Since my personal need for now is only OS X, this is supported. However, if anyone needs support
for other platforms as well, feel free to fork and create a corresponding native source file for
your platform.


Building
--------

Building the native library will automatically place the resuting binary in the resources folder
of the Java project. The Java library will automatically extract the required native binary to
a temporary file (that will be deleted after the application quits) and link against it. So,
the only thing you will have to do to be able to use this is to build the native library and
include the Java project as a dependency in Maven or add it manually to the classpath.

### Java
This project is a Maven project made in NetBeans on the Java side of it. So just open this in NetBeans
and you are basically good to go. You can compile this manually as well.

### Native Mac OS X
This is an Xcode project that resides in the mac/ directory. Just open the project in Xcode and hit
"Run". This will produce the native library file (ending in .dylib) on the right location.

Testing
-------
There is a test file provided in:

    src/main/java/com/martijncourteaux/multitouchgestures/demo/DemoSimpleGestures.java

You can have a look at that file for a sample.

You can see how this compares to the Apple Gesture support using this demo file:

    src/main/java/com/martijncourteaux/multitouchgestures/demo/DemoCompareGestures.java

Usage
-----
The usage is pretty similar to Apple's AppleJavaExtensions package from Java 6. You build your
Swing frame, just as regular. Then you can do this:

    MultiTouchGestureUtilities.addGestureListener(comp, listener);

Where `listener` is a `MultiTouchGestureListener` and `comp` is the JComponent you want to add
the listener to. **When you dispose/remove a JFrame or component with a listener, you *MUST*
remove the `MultiTouchGestureListener` using one of following techniques:**

    MultiTouchGestureUtilities.removeGestureListener(comp, listener);
    MultiTouchGestureUtilities.removeAllGestureListeners(comp);

The `MultiTouchGestureListener` interface has these methods:

    public void magnify(MagnifyGestureEvent e);
    public void rotate(RotateGestureEvent e);
    public void scroll(ScrollGestureEvent e);

Where each event type has these parameters:

 - `mouseX`: x-coordinate of the mouse in the component space.
 - `mouseX`: y-coordinate of the mouse in the component space.
 - `absMouseX`: x-coordinate of the mouse on the screen
 - `absMouseY`: y-coordinate of the mouse on the screen
 - `phase`: a `GesturePhase` enum indicating what phase the gesture is in.

A `GesturePhase` is an enum containing these values:

 - `MOMENTUM`: Indicates this event is caused by momentum (like OS X).
 - `BEGIN`: The gesture just began.
 - `CHANGED`: The gesture updated (e.g.: rotating, zooming)
 - `END`: The gesture ended.
 - `CANCELLED`: The gesture was cancelled due to some popup or other thing.
 - `OTHER`: Any other reason for an event. Most of the time this will be errornous.

**Remark**: On OS X, the `MOMENTUM` events come after the `END` event.

Remark for OS X
---------------
The built-in Java scrolling listeners are not as smooth as the one provided in this project.
So I highly recommend checking it out. This gives you the native OS X scrolling experience.


