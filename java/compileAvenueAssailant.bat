@echo OFF
cls
javac -cp .;jars/slick.jar;jars/lwjgl.jar;games games/AvenueAssailant.java
javac -cp .;jars/slick.jar;jars/lwjgl.jar;games games/AvenueAssailant/*.java
echo Compiling complete.
pause
cd games
java -Djava.library.path=../lwjglbin -cp .;../jars/slick.jar;../jars/lwjgl.jar AvenueAssailant
pause