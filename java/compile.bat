::Hopefully this works
@echo OFF
:compile
cls
javac -cp slick/lib/*.jar *.java
echo Complete
set /p again=Again? [y/n]?:
if %again% == y goto compile