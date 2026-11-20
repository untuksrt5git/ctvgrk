# CTVGRK – Cloud Cam Viewer (Android)

Versi Android hybrid (Kotlin + Chaquopy) dari [cloud-cam-viewer](https://github.com/Bahrombekk/cloud-cam-viewer).

Target fitur mirip [Ningali / cctv.kbji.web.id](https://cctv.kbji.web.id):
- Login EZVIZ / Hik-Connect
- List kamera
- (Nanti) Live view multi-kamera
- (Nanti) Local decrypt + restream

## Status Saat Ini (v0.1.0)

- [x] Project Android + Chaquopy siap build
- [x] UI login + list kamera
- [x] GitHub Actions otomatis build Debug APK
- [ ] Integrasi penuh library `cloudcam` (masih mock)
- [ ] Live stream viewer
- [ ] Multi-camera grid
- [ ] Background service

## Cara Build dengan GitHub Actions

1. Push semua file ke branch `main` atau `master`
2. Buka tab **Actions** di repository
3. Tunggu workflow **Build Debug APK** selesai
4. Download artifact **ctvgrk-debug-apk**

Atau tekan **Run workflow** secara manual.

## Struktur Project

```
ctvgrk/
├── .github/workflows/build.yml   ← GitHub Actions
├── app/
│   ├── build.gradle.kts          ← Chaquopy + dependencies
│   └── src/main/
│       ├── java/.../MainActivity.kt
│       ├── python/cloudcam_android.py   ← Bridge Python
│       └── res/...
├── build.gradle.kts
├── settings.gradle.kts
└── README.md
```

## Catatan Penting

- Versi ini masih memakai **mock data** agar bisa langsung build & install.
- Integrasi nyata dengan `cloud-cam-viewer` akan ditambahkan di tahap berikutnya (copy source `cloudcam` ke `app/src/main/python/` dan sesuaikan `cloudcam_android.py`).
- APK yang dihasilkan adalah **debug** (bisa langsung di-install di HP dengan "Install from unknown sources").

## Langkah Selanjutnya

1. Integrasikan source asli `cloudcam`
2. Implementasi live view (ExoPlayer / OpenCV)
3. Multi-camera grid
4. Simpan session & verification code

---

Dibuat untuk repo: https://github.com/untuksrt5git/ctvgrk
