#!/bin/bash
# ========================================================
# Script para iniciar todo el Backend en Linux
# Levanta MySQL y todos los microservicios con Docker
# ========================================================

DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" >/dev/null 2>&1 && pwd)"
cd "$DIR"

echo "=========================================="
echo "Iniciando ecosistema de microservicios con Docker..."
echo "=========================================="

# Detener procesos locales que puedan ocupar los puertos
fuser -k 8080/tcp 8081/tcp 8082/tcp 8083/tcp 3306/tcp 2>/dev/null || true

# Levantar con docker compose
docker compose up -d 2>/dev/null || sudo docker compose up -d

echo "=========================================="
echo "¡Todo el backend esta corriendo en Docker!"
echo "MySQL:           localhost:3306"
echo "API Gateway:     http://localhost:8080 (Puerto unificado para la app movil)"
echo "Auth Service:    http://localhost:8083"
echo "Alianza Service: http://localhost:8081"
echo "Catalogo Service:http://localhost:8082"
echo "=========================================="
echo "Endpoints unificados a través del Gateway:"
echo "  - POST http://localhost:8080/auth/register"
echo "  - POST http://localhost:8080/auth/login"
echo "  - GET/POST http://localhost:8080/api/solicitudes"
echo "  - GET/POST http://localhost:8080/api/productos"
echo "=========================================="
echo "Para ver logs en vivo: docker compose logs -f"
echo "Para detener los servicios usa: ./detener_servicios.sh"
