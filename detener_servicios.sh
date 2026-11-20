#!/bin/bash
# ========================================================
# Script para detener todo el Backend en Linux
# ========================================================

DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" >/dev/null 2>&1 && pwd)"
cd "$DIR"

echo "=========================================="
echo "Deteniendo contenedores Docker..."
echo "=========================================="

docker compose down 2>/dev/null || sudo docker compose down

# Asegurar liberacion de puertos locales
fuser -k 8080/tcp 8081/tcp 8082/tcp 8083/tcp 2>/dev/null || true

echo "=========================================="
echo "Todo el Backend ha sido detenido exitosamente."
echo "=========================================="
