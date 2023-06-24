#!/bin/bash

export JAVA_HOME=/disk1/dev/graalvm-ce

mvn clean install -DskipTests

sudo $JAVA_HOME/bin/java -jar target/mazahireuloom.jar
