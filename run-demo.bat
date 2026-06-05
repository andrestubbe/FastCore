@echo off
    echo.
    echo âŒ Maven build failed.
    pause
    exit /b %ERRORLEVEL%
)

echo.
echo Running Demo...
cd examples\Demo
call mvn -q compile exec:java
cd ..\..
pause
