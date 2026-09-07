@echo off
rem ===========================================================================
rem  Kranji - start the reader or the studio.
rem
rem  WHY THIS EXISTS AT ALL: `java -jar` ignores the classpath. The reading
rem  library is a separate jar, discovered at startup through
rem  META-INF/services, and a jar launched with -jar cannot see a second one.
rem  So `java -jar kranji.jar` starts perfectly, serves the 23-article
rem  demonstration set, and says nothing about the 600 articles sitting in the
rem  folder next to it. This script exists to make that mistake impossible.
rem
rem  It is also why this is a .cmd and not a shell script. Java on Windows
rem  wants ';' between classpath entries, and a Git Bash shell rewrites a
rem  ';'-separated argument and silently drops everything after the first
rem  entry - which produces exactly the failure above, from a command line
rem  that looks correct.
rem
rem  Layout:
rem      kranji.cmd
rem      kranji.jar          the application
rem      collections\*.jar   any reading libraries; zero or more
rem
rem  Usage:
rem      kranji                    reader on 8102
rem      kranji reader 9102
rem      kranji studio 9101
rem ===========================================================================
setlocal enabledelayedexpansion

set "HERE=%~dp0"
set "APP=%HERE%kranji.jar"
set "LIBDIR=%HERE%collections"

if not exist "%APP%" (
    echo ERROR: no application jar at "%APP%"
    exit /b 1
)

set "CP=%APP%"
set /a FOUND=0
if exist "%LIBDIR%" (
    for %%J in ("%LIBDIR%\*.jar") do (
        set "CP=!CP!;%%~fJ"
        set /a FOUND+=1
        echo   library: %%~nxJ
    )
)

rem Said out loud, because it is a working server either way. A reader with no
rem collections jar is a legitimate deployment - it is what the demonstration
rem set is for - and it is also what a mislaid file looks like. The difference
rem is not visible from the app, so it gets announced here.
if !FOUND! EQU 0 (
    echo.
    echo   NOTE: no reading library found in "%LIBDIR%"
    echo         Serving the demonstration set - 23 articles.
    echo         Drop kranji-reading-collections.jar in that folder for the full library.
    echo.
)

set "MODE=%~1"
if "%MODE%"=="" set "MODE=reader"
set "PORT=%~2"
if "%PORT%"=="" set "PORT=8102"

echo   starting %MODE% on port %PORT%
java -cp "%CP%" kranji.dist.KranjiApp %MODE% %PORT%
