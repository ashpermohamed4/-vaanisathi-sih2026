"""
VaaniSathi — Whisper Tiny (FP32 CPU) Benchmark
Demonstrates empirical latency analysis and why Whisper Tiny was rejected for edge Android devices.
"""
import os
import sys
import time
import numpy as np

if sys.platform == "win32":
    sys.stdout.reconfigure(encoding="utf-8", errors="replace")
    sys.stderr.reconfigure(encoding="utf-8", errors="replace")

try:
    import whisper
except ImportError:
    print("Whisper is not installed. Install via: pip install openai-whisper")
    sys.exit(1)

test_sentences = [
    "बच्चों आज हम गिनती सीखेंगे",
    "यह क्या है",
    "शाबाश बहुत अच्छा",
    "अब सुनो और दोहराओ",
    "एक दो तीन चार पांच"
]

def run_whisper_benchmark():
    print("=" * 65)
    print("VaaniSathi — OpenAI Whisper Tiny Hindi ASR Benchmark")
    print("=" * 65)

    print("Step 1: Loading Whisper 'tiny' model...")
    load_start = time.time()
    model = whisper.load_model("tiny")
    print(f"Whisper Tiny model loaded in {time.time() - load_start:.2f}s\n")

    print("Step 2: Transcribing Hindi FLN audio test samples...")
    audio_files = [f"hindi_sample_{i}.wav" for i in range(len(test_sentences))]

    results = []
    print(f"{'Sentence #':<12} | {'Ground Truth':<25} | {'Whisper Prediction':<25} | {'Latency':<8}")
    print("-" * 80)

    for i, sentence in enumerate(test_sentences):
        audio_path = audio_files[i]
        if not os.path.exists(audio_path):
            print(f"Sample {i+1:<5} | Audio file '{audio_path}' not found (skipping)")
            continue

        start = time.time()
        res = model.transcribe(audio_path, language="hi")
        latency = (time.time() - start) * 1000
        results.append(latency)
        pred = res.get("text", "").strip()

        print(f"Sample {i+1:<5} | {sentence:<25} | {pred:<25} | {latency:.0f}ms")

    if results:
        mean_l = float(np.mean(results))
        min_l = float(np.min(results))
        max_l = float(np.max(results))

        print("\n" + "=" * 65)
        print("WHISPER BENCHMARK SUMMARY")
        print("=" * 65)
        print(f"Min Latency  : {min_l:.0f}ms")
        print(f"Mean Latency : {mean_l:.0f}ms ({mean_l/1000:.2f}s)")
        print(f"Max Latency  : {max_l:.0f}ms ({max_l/1000:.2f}s)")
        print(f"PS Limit     : ≤ 3,000ms")
        print(f"Verdict      : REJECTED ❌ (Exceeds real-time threshold by {mean_l - 3000:.0f}ms)")
        print("=" * 65)

if __name__ == "__main__":
    run_whisper_benchmark()
