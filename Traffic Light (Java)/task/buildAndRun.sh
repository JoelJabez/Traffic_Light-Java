#!/bin/bash

cd src/

find -name "*.java" > sources.txt
javac @sources.txt
java traffic.Main

rm sources.txt

find -name "*.class" > sources.txt
for line in $(cat sources.txt)
do
    mv "$line" ../out/production/classes/
done

rm sources.txt

cd ../
