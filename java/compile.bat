rem Hopefully this works
@echo OFF
cls
javac -cp .;jars/slick.jar;jars/lwjgl.jar;games games/*.java
javac -cp .;jars/slick.jar;jars/lwjgl.jar;games games/KevCycles/*.java
echo Complete
cd games
set /p p1=Player 1?:
set /p p2=Player 2?:
set /p run=Program to run?:
java -Djava.library.path=../lwjglbin -cp .;../jars/slick.jar;../jars/lwjgl.jar %run% %p1% %p2%
pause