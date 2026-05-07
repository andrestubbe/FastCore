@echo off
echo === Building FastCore Native Library ===
echo.

REM Call Visual Studio Developer Command Prompt
call "C:\Program Files (x86)\Microsoft Visual Studio\2022\BuildTools\VC\Auxiliary\Build\vcvars64.bat" >nul 2>&1
if errorlevel 1 (
    call "C:\Program Files (x86)\Microsoft Visual Studio\2022\Community\VC\Auxiliary\Build\vcvars64.bat" >nul 2>&1
)
if errorlevel 1 (
    call "C:\Program Files (x86)\Microsoft Visual Studio\2022\Professional\VC\Auxiliary\Build\vcvars64.bat" >nul 2>&1
)
if errorlevel 1 (
    call "C:\Program Files (x86)\Microsoft Visual Studio\2022\Enterprise\VC\Auxiliary\Build\vcvars64.bat" >nul 2>&1
)
if errorlevel 1 (
    echo Error: Visual Studio 2022 not found. Please install Visual Studio 2022 with C++ development tools.
    exit /b 1
)

REM Create build directory
if not exist build mkdir build
cd build

REM Compile with cl.exe
echo Compiling fastcore.cpp...
cl.exe /LD /EHsc /std:c++17 /I..\native\include /I"C:\Program Files\Java\jdk-25\include" /I"C:\Program Files\Java\jdk-25\include\win32" ..\native\src\fastcore.cpp ..\native\src\index.cpp ..\native\src\search.cpp ..\native\src\watch.cpp /Fe:fastcore.dll
if errorlevel 1 (
    echo Error: Compilation failed.
    cd ..
    exit /b 1
)

REM Clean up intermediate files
del *.obj >nul 2>&1
del *.exp >nul 2>&1
del *.lib >nul 2>&1

cd ..
echo.
echo === Build Complete ===
echo fastcore.dll is now in the build folder.
pause
