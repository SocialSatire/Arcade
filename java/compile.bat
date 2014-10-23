::Hopefully this works
@echo OFF
del games/*.class
cls
javac -cp .;jars/slick.jar;jars/lwjgl.jar games/*.java
echo Complete
cd games
set /p run=Program to run?:
java -Djava.library.path=../lwjglbin -cp .;../jars/slick.jar;../jars/lwjgl.jar %run%
pause