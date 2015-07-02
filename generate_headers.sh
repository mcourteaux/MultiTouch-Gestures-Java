#!/bin/bash

### MAC OS X ###
DIR_OUT=mac/
CP="-classpath target/classes"
P="com.martijncourteaux.multitouchgestures"

javah -o $DIR_OUT/EventDispatch.h $CP ${P}.EventDispatch


### WINDOWS AND LINUX NOT SUPPORTED YET ###


