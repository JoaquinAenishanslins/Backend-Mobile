#!/bin/bash
# ========================================================
# Script para detener todo el Backend en Linux
# ========================================================

DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" >/dev/null 2>&1 && pwd)"
cd "$DIR"

echo "=========================================="
echo "Deteniendo microservicios Spring Boot..."
echo "=========================================="

# Detener procesos Java en puertos 8081 y 8082
fuser -k 8081/tcp 2>/dev/null || true
fuser -k 8082/tcp 2>/dev/null || true

# Alternativa matando procesos jar específicos si quedan vivos
pkill -f "alianza-service" 2>/dev/null || true
pkill -f "catalogo-service" 2>/dev/null || true

echo "Microservicios detenidos."

echo "=========================================="
echo "Deteniendo contenedor Docker de MySQL..."
echo "=========================================="
docker compose down

echo "=========================================="
echo "Todo el Backend ha sido detenido exitosamente."
echo "=========================================="
