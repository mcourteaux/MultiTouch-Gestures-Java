#!/bin/bash

DIR_OUT=OSXGestures4JavaJNI/OSXGestures4JavaJNI
CP="-classpath target/classes"
P="com.martijncourteaux.osxgestures4java"

javah -o $DIR_OUT/EventDispatch.h $CP ${P}.EventDispatch
