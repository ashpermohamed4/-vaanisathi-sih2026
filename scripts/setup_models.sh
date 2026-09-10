#!/bin/bash
# VaaniSathi — Download Vosk Hindi Model
# Run once before building the Android app

MODEL_URL="https://alphacephei.com/vosk/models/vosk-model-small-hi-0.22.zip"
ASSETS_DIR="app/src/main/assets"
MODEL_DIR="$ASSETS_DIR/vosk-model-small-hi-0.22"

echo "VaaniSathi Model Setup"
echo "Downloading Vosk Small Hindi (42 MB)..."

mkdir -p "$ASSETS_DIR"

if [ -d "$MODEL_DIR" ]; then
    echo "Model already exists at $MODEL_DIR"
    exit 0
fi

curl -L "$MODEL_URL" -o vosk_hindi_model.zip
echo "Extracting to $ASSETS_DIR..."
unzip -q vosk_hindi_model.zip -d "$ASSETS_DIR"
rm vosk_hindi_model.zip

echo "Model ready at: $MODEL_DIR"
echo "Build the app now: ./gradlew assembleDebug"
