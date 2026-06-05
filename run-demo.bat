@echo off
chcp 65001 >nul
cd /d "%~dp0"
    echo.
    echo âŒ Maven build failed.
    pause
    exit /b %ERRORLEVEL%
)

echo.
echo Running Demo...
cd examples\Demo
call mvn compile exec:java
cd ..\..
pause
