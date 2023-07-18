#!/bin/bash

clear
cd src/

find -name "*.java" > sources.txt
javac @sources.txt
java traffic.Main

rm sources.txt

find -name "*.class" > sources.txt
for line in $(cat sources.txt)
do
    rm "$line"
done

rm sources.txt

cd ../
