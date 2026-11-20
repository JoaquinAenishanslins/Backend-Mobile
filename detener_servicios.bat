@echo off
REM ========================================================
REM Script para detener todo el Backend en Windows
REM ========================================================
setlocal enabledelayedexpansion

set "BASE_DIR=%~dp0"
cd /d "%BASE_DIR%"

echo ==========================================
echo Deteniendo microservicios Spring Boot...
echo ==========================================

REM Detener procesos escuchando en puertos 8081 y 8082
for /f "tokens=5" %%a in ('netstat -aon ^| findstr ":8081 "') do (
    taskkill /F /PID %%a 2>nul
)

for /f "tokens=5" %%a in ('netstat -aon ^| findstr ":8082 "') do (
    taskkill /F /PID %%a 2>nul
)

echo Microservicios detenidos.

echo ==========================================
echo Deteniendo contenedor Docker de MySQL...
echo ==========================================
docker compose down

echo ==========================================
echo Todo el Backend ha sido detenido exitosamente.
echo ==========================================
pause
