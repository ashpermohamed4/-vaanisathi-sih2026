#!/usr/bin/env bash
# ==============================================================================
# VaaniSathi (वाणीसाथी) — Offline Model Setup Script
# Downloads and unpacks the 42 MB Vosk Small Hindi model for offline edge ASR.
# ==============================================================================

set -e

MODEL_NAME="vosk-model-small-hi-0.22"
MODEL_ZIP="${MODEL_NAME}.zip"
MODEL_URL="https://alphacephei.com/vosk/models/${MODEL_ZIP}"
TARGET_DIR="app/src/main/assets/models"

echo "=========================================================="
echo " VaaniSathi: Setting up Offline Vosk Hindi ASR Model"
echo "=========================================================="

mkdir -p "${TARGET_DIR}"

if [ -d "${TARGET_DIR}/${MODEL_NAME}" ]; then
    echo "✅ Model already installed at ${TARGET_DIR}/${MODEL_NAME}"
    exit 0
fi

echo "📥 Downloading Vosk Small Hindi Model (42 MB)..."
if command -v curl >/dev/null 2>&1; then
    curl -L -O "${MODEL_URL}"
elif command -v wget >/dev/null 2>&1; then
    wget "${MODEL_URL}"
else
    echo "❌ Error: Neither curl nor wget found. Please download manually from ${MODEL_URL}"
    exit 1
fi

echo "📦 Extracting model archive..."
if command -v unzip >/dev/null 2>&1; then
    unzip -q "${MODEL_ZIP}" -d "${TARGET_DIR}/"
    rm "${MODEL_ZIP}"
else
    echo "⚠️ Warning: 'unzip' command not found. Please extract ${MODEL_ZIP} into ${TARGET_DIR}/"
fi

echo "✅ Model setup completed successfully!"
echo "Model location: ${TARGET_DIR}/${MODEL_NAME}"
