#!/bin/sh

export PATH=$PATH:ant/bin

ant/bin/ant  -f ../build.xml build.war
