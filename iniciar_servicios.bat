@echo off
REM ========================================================
REM Script para iniciar todo el Backend en Windows
REM Incluye: Docker (MySQL), Build de Microservicios y Run
REM ========================================================
setlocal enabledelayedexpansion

set "BASE_DIR=%~dp0"
cd /d "%BASE_DIR%"

echo ==========================================
echo 1. Iniciando base de datos MySQL en Docker...
echo ==========================================
docker compose up -d

echo Esperando a que MySQL responda...
timeout /t 5 /nobreak >nul

echo ==========================================
echo 2. Compilando y ejecutando alianza-service (8081)...
echo ==========================================
cd /d "%BASE_DIR%alianza-service"
call mvnw.cmd clean package -DskipTests
start "Alianza Service (8081)" cmd /k "java -jar target\alianza-service-0.0.1-SNAPSHOT.jar"

echo ==========================================
echo 3. Compilando y ejecutando catalogo-service (8082)...
echo ==========================================
cd /d "%BASE_DIR%catalogo-service"
call mvnw.cmd clean package -DskipTests
start "Catalogo Service (8082)" cmd /k "java -jar target\catalogo-service-0.0.1-SNAPSHOT.jar"

cd /d "%BASE_DIR%"
echo ==========================================
echo ¡Todo el backend esta corriendo!
echo MySQL:           localhost:3306
echo Alianza Service:  http://localhost:8081/api/solicitudes
echo Catalogo Service: http://localhost:8082/api/productos
echo ==========================================
echo Para detener los servicios ejecuta: detener_servicios.bat
pause
