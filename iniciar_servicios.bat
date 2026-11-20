@echo off
REM ========================================================
REM Script para iniciar todo el Backend en Windows con Docker
REM ========================================================
setlocal enabledelayedexpansion

set "BASE_DIR=%~dp0"
cd /d "%BASE_DIR%"

echo ==========================================
echo Iniciando ecosistema de microservicios con Docker...
echo ==========================================

REM Liberar puertos si estuviesen tomados
for /f "tokens=5" %%a in ('netstat -aon ^| findstr ":8080 :8081 :8082 :8083 "') do (
    taskkill /F /PID %%a 2>nul
)

docker compose up -d

echo ==========================================
echo ¡Todo el backend esta corriendo en Docker!
echo MySQL:           localhost:3306
echo API Gateway:     http://localhost:8080 (Puerto unificado para Android)
echo Auth Service:    http://localhost:8083
echo Alianza Service: http://localhost:8081
echo Catalogo Service:http://localhost:8082
echo ==========================================
echo Endpoints unificados a traves del Gateway:
echo   - POST http://localhost:8080/auth/register
echo   - POST http://localhost:8080/auth/login
echo   - GET/POST http://localhost:8080/api/solicitudes
echo   - GET/POST http://localhost:8080/api/productos
echo ==========================================
echo Para detener los servicios ejecuta: detener_servicios.bat
pause
