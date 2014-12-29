rem Hopefully this works
@echo OFF
cls
set /p run=Program to compile and run?:
echo ============================================
echo If file not found errors show up ignore them
echo I am lazy and do not want to spend 5 hours
echo googling a way to do this in a way that is
echo 10 times as complicated
echo ============================================
javac -cp .;jars/slick.jar;jars/lwjgl.jar;games games/%run%.java
javac -cp .;jars/slick.jar;jars/lwjgl.jar;games games/%run%/*.java
javac -cp .;jars/slick.jar;jars/lwjgl.jar;games games/%run%/*/*.java
javac -cp .;jars/slick.jar;jars/lwjgl.jar;games games/%run%/*/*/*.java
javac -cp .;jars/slick.jar;jars/lwjgl.jar;games games/%run%/*/*/*/*.java
rem because why not
echo Compiling complete.
pause
cd games
java -Djava.library.path=../lwjglbin -cp .;../jars/slick.jar;../jars/lwjgl.jar %run%
pause