"""
VaaniSathi — Offline Vosk Small Hindi ASR Benchmark
Measures latency and accuracy of Vosk ASR on standard classroom Hindi speech samples.
"""
import os
import sys
import time
import json
import wave

# Ensure UTF-8 output on all operating systems
if sys.platform == "win32":
    sys.stdout.reconfigure(encoding="utf-8", errors="replace")
    sys.stderr.reconfigure(encoding="utf-8", errors="replace")

try:
    from vosk import Model, KaldiRecognizer
except ImportError:
    print("Vosk is not installed. Install via: pip install vosk")
    sys.exit(1)

MODEL_DIR = os.getenv("VOSK_MODEL_PATH", "vosk-model-small-hi-0.22")

def run_benchmark():
    print("=" * 65)
    print("VaaniSathi — Vosk Small Hindi ASR Benchmark")
    print(f"Model Path: {MODEL_DIR}")
    print("=" * 65)

    if not os.path.exists(MODEL_DIR):
        print(f"Error: Model directory '{MODEL_DIR}' not found.")
        print("Please run 'scripts/setup_models.sh' or download vosk-model-small-hi-0.22.")
        return

    load_start = time.time()
    model = Model(MODEL_DIR)
    print(f"Model initialized in {(time.time() - load_start)*1000:.1f}ms\n")

    test_files = [
        ("hindi_pcm_0.wav", "बच्चों आज हम गिनती सीखेंगे"),
        ("hindi_pcm_1.wav", "यह क्या है"),
        ("hindi_pcm_2.wav", "शाबाश बहुत अच्छा")
    ]

    results = []
    print(f"{'File':<18} | {'Ground Truth':<25} | {'Recognized':<25} | {'Latency':<8}")
    print("-" * 85)

    for filename, ground_truth in test_files:
        if not os.path.exists(filename):
            print(f"{filename:<18} | File not found in workspace (skipping)")
            continue

        wf = wave.open(filename, "rb")
        if wf.getnchannels() != 1 or wf.getsampwidth() != 2 or wf.getcomptype() != "NONE":
            print(f"Audio file {filename} must be WAV format mono PCM.")
            continue

        rec = KaldiRecognizer(model, wf.getframerate())
        rec.SetWords(True)

        start = time.time()
        while True:
            data = wf.readframes(4000)
            if len(data) == 0:
                break
            rec.AcceptWaveform(data)

        final_res = json.loads(rec.FinalResult())
        latency_ms = (time.time() - start) * 1000
        recognized_text = final_res.get("text", "")
        results.append(latency_ms)

        print(f"{filename:<18} | {ground_truth:<25} | {recognized_text:<25} | {latency_ms:.0f}ms")

    if results:
        mean_lat = sum(results) / len(results)
        min_lat = min(results)
        max_lat = max(results)
        print("\n" + "=" * 65)
        print(f"Mean Latency : {mean_lat:.0f}ms")
        print(f"Min Latency  : {min_lat:.0f}ms")
        print(f"Max Latency  : {max_lat:.0f}ms")
        print(f"PS Constraint: ≤ 3,000ms")
        print(f"Verdict      : {'PASS ✅ (Tier 1 Edge Approved)' if mean_lat <= 3000 else 'FAIL ❌'}")
        print("=" * 65)

if __name__ == "__main__":
    run_benchmark()
