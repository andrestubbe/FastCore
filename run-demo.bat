@echo off
echo Building main project...
call mvn -q clean package -DskipTests
if %ERRORLEVEL% NEQ 0 (
    echo.
    echo ❌ Maven build failed.
    pause
    exit /b %ERRORLEVEL%
)

echo.
echo Running Demo...
cd examples\Demo
call mvn -q compile exec:java
cd ..\..
pause
