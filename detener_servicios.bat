@echo off
REM ========================================================
REM Script para detener todo el Backend en Windows
REM ========================================================
setlocal enabledelayedexpansion

set "BASE_DIR=%~dp0"
cd /d "%BASE_DIR%"

echo ==========================================
echo Deteniendo contenedores Docker...
echo ==========================================

docker compose down

REM Liberar puertos por seguridad
for /f "tokens=5" %%a in ('netstat -aon ^| findstr ":8080 :8081 :8082 :8083 "') do (
    taskkill /F /PID %%a 2>nul
)

echo ==========================================
echo Todo el Backend ha sido detenido exitosamente.
echo ==========================================
pause
