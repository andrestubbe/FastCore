@echo off
setlocal enabledelayedexpansion
title FastCore - Platform & FFM Showcase

chcp 65001 >nul
cd /d "%~dp0"

echo ================================================================================
echo   FastCore Showcase — Native FFM Linker and Cross-Platform JNI Base
echo ================================================================================
echo.

set CP=..\..\target\FastCore-0.1.1.jar;target\classes

java --enable-preview -Dfile.encoding=UTF-8 -cp "%CP%" fastcore.Demo %*

echo.
pause
