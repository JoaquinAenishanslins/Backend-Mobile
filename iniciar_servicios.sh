#!/bin/bash
# ========================================================
# Script para iniciar todo el Backend en Linux
# Incluye: Docker (MySQL), Build de Microservicios y Run
# ========================================================

# Ir al directorio donde se encuentra este script
DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" >/dev/null 2>&1 && pwd)"
cd "$DIR"

echo "=========================================="
echo "1. Iniciando base de datos MySQL en Docker..."
echo "=========================================="
docker compose up -d

# Esperar unos segundos para que MySQL esté listo para recibir conexiones
echo "Esperando a que MySQL responda..."
sleep 5

echo "=========================================="
echo "2. Compilando y ejecutando alianza-service (8081)..."
echo "=========================================="
cd "$DIR/alianza-service"
chmod +x ./mvnw
./mvnw clean package -DskipTests
nohup java -jar target/*.jar > ../alianza-service.log 2>&1 &
PID_ALIANZA=$!
echo "-> alianza-service iniciado con PID $PID_ALIANZA (Logs: alianza-service.log)"

echo "=========================================="
echo "3. Compilando y ejecutando catalogo-service (8082)..."
echo "=========================================="
cd "$DIR/catalogo-service"
chmod +x ./mvnw
./mvnw clean package -DskipTests
nohup java -jar target/*.jar > ../catalogo-service.log 2>&1 &
PID_CATALOGO=$!
echo "-> catalogo-service iniciado con PID $PID_CATALOGO (Logs: catalogo-service.log)"

cd "$DIR"
echo "=========================================="
echo "¡Todo el backend esta corriendo!"
echo "MySQL:           localhost:3306"
echo "Alianza Service:  http://localhost:8081/api/solicitudes"
echo "Catalogo Service: http://localhost:8082/api/productos"
echo "=========================================="
echo "Para detener los servicios usa: ./detener_servicios.sh"
