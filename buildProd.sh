#!/bin/bash

export JAVA_HOME=/opt/JDK13-64bit/

mvn clean install

sudo $JAVA_HOME/bin/java -jar target/mazahireuloom.jar
