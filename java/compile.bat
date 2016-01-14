rem Hopefully this works
@echo OFF
cls
set /p run=Program to compile and run?:

javac -cp .;jars/slick.jar;jars/lwjgl.jar;games games/%run%.java
javac -cp .;jars/slick.jar;jars/lwjgl.jar;games games/%run%/*.java

rem javac -cp .;jars/slick.jar;jars/lwjgl.jar;games games/%run%/*/*.java
rem javac -cp .;jars/slick.jar;jars/lwjgl.jar;games games/%run%/*/*/*.java
rem javac -cp .;jars/slick.jar;jars/lwjgl.jar;games games/%run%/*/*/*/*.java
rem uncomment as nessecary
echo Compiling complete.
pause
cd games
java -Djava.library.path=../lwjglbin -cp .;../jars/slick.jar;../jars/lwjgl.jar %run%
pause