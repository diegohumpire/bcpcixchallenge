#!/bin/sh

./gradlew build

docker build --build-arg JAR_FILE=./build/libs/bcpcixchallenge-0.0.1.jar -t bcpcix/challenge .