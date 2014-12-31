@echo off
cls
javac -cp .;jars/slick.jar;jars/lwjgl.jar;games ArcadeMenu.java
echo Compiling Complete
pause
java -Djava.library.path=lwjglbin -cp .;jars/slick.jar;jars/lwjgl.jar;games ArcadeMenu
pause