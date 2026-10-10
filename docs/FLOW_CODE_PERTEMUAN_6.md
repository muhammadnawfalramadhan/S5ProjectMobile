# Flow Code Pertemuan 6 — SplashScreen → Auth → Main

Bahan untuk digambar ulang di **tldraw**. Ini *flow code* (urutan baris kode yang dieksekusi),
bukan flow tampilan aplikasi. Setiap kotak = satu langkah kode, panah = urutan eksekusi.

## A. Android menjalankan aplikasi
`AndroidManifest.xml` → activity dengan `intent-filter MAIN/LAUNCHER` = **`SplashScreenActivity`**

## B. `SplashScreenActivity.onCreate()`
1. **B1** `super.onCreate()` → `ActivitySplashScreenBinding.inflate()` → `setContentView()` (logo, nama, slogan, versi tampil)
2. **B2** `txtVersi.text = "Versi " + versionName`
3. **B3** `lifecycleScope.launch { ... }` → coroutine berjalan
4. **B4** `delay(2000)` → menunggu 2 detik (simulasi ambil data)
5. **B5** `sharedPref = getSharedPreferences("user_pref", MODE_PRIVATE)`
6. **B6** `isLogin = sharedPref.getBoolean("isLogin", false)`
7. **B7** ◆ **Kondisi `isLogin`?**
   - `true`  → `tujuan = MainActivity` → lanjut ke **D**
   - `false` → `tujuan = AuthActivity` → lanjut ke **C**
8. **B8** `startActivity(Intent(this, tujuan))` → `finish()` (Splash dihapus dari back stack)

## C. `AuthActivity.onCreate()` (hanya jika belum login)
1. **C1** `setContentView()` → tampil logo, `inputUsername`, `inputPassword`, `btnLogin`
2. **C2** `sharedPref = getSharedPreferences("user_pref", MODE_PRIVATE)`
3. **C3** `btnLogin.setOnClickListener { ... }` → menunggu user menekan Login
4. **C4** User klik Login:
   - `username = inputUsername.text.toString().trim()`
   - `password = inputPassword.text.toString().trim()`
5. **C5** ◆ **Pengecekan username & password**: `username.isNotEmpty() && username == password`
   - Contoh: `nawfal` / `nawfal` → **true**
   - Contoh: `nawfal` / `12345` → **false**
   - Contoh: kosong / kosong → **false** (username tidak boleh kosong)
6. **C6a (false)** `AlertDialog` "Login Gagal — Silahkan coba lagi" → klik OK → `dismiss()` → kembali ke **C3**
7. **C6b (true)**
   - `editor = sharedPref.edit()`
   - `editor.putBoolean("isLogin", true)`
   - `editor.putString("username", username)`
   - `editor.apply()` → data tersimpan permanen di `user_pref`
   - `startActivity(Intent(this, MainActivity))` → `finish()` → lanjut ke **D**

## D. `MainActivity.onCreate()`
1. **D1** `setContentView()` → tampil menu
2. **D2** `sharedPref.getString("username", "")` → `txtSapaan = "Halo, <username>"`
3. **D3** Pasang listener: `btnWeb`, `btnMyProject`, `btnPertemuan3`, `btnProfil` (→ `ProfileActivity`), `btnLogout`
4. **D4** User klik `btnLogout` → `AlertDialog` "Apakah kamu yakin ingin logout?"
   - **"Tidak"** → `dismiss()` → tetap di **D**
   - **"Ya"** → `editor.clear()` → `editor.apply()` (isLogin & username terhapus)
     → `dismiss()` → `startActivity(AuthActivity)` → `finish()` → kembali ke **C**

## E. Buka ulang aplikasi (Test Drive)
- Sudah login, app di-close → buka lagi: **A → B → B7 (`isLogin = true`) → D** (tanpa login)
- Setelah logout → buka lagi: **A → B → B7 (`isLogin = false`) → C**

## Jawaban pertanyaan modul
> *Di mana tempat terbaik meletakkan pengecekan `isLogin` pada `onCreate()`?*

Paling awal, tepat setelah `super.onCreate()` dan **sebelum** `setContentView()`/pasang listener,
lalu langsung `startActivity()` + `finish()` + `return`. Dengan begitu layout login tidak sempat
di-render dan listener tidak dipasang sia-sia. Di versi akhir, pengecekan ini dipindahkan ke
`SplashScreenActivity` sehingga `AuthActivity` hanya mengurus proses login.
