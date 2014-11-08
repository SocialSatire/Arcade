rem Hopefully this works
@echo OFF
del games/*.class;games/KevCycles/*.class
rem if you have a problem with that then you have an issue
cls
javac -cp .;jars/slick.jar;jars/lwjgl.jar;games games/*.java
javac -cp .;jars/slick.jar;jars/lwjgl.jar;games games/KevCycles/*.java
echo Complete
cd games
set /p run=Program to run?:
java -Djava.library.path=../lwjglbin -cp .;../jars/slick.jar;../jars/lwjgl.jar %run%
pause