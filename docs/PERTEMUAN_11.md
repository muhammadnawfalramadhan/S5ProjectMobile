# Pertemuan 11 — Adapter, RecyclerView, Home Grid & Onboard Screen

Project: **SI-KSP Mobile** (Koperasi Simpan Pinjam Afdeling 2 PT BSP)
Muhammad Nawfal Ramadhan — 2455301229

## 1. Yang dikerjakan

| Tugas | Implementasi | File utama |
|---|---|---|
| 1 CustomAdapter | `JadwalAngsuranAdapter` (turunan `BaseAdapter`) untuk `ListView` jadwal angsuran di tab **Pinjaman** | `Pinjaman/JadwalAngsuranAdapter.kt`, `Pinjaman/Angsuran.kt`, `item_angsuran.xml`, `fragment_pinjaman.xml` |
| 1 RecyclerView | `MenuHomeAdapter` untuk grid menu Home (`GridLayoutManager` 4 kolom). RecyclerView Riwayat (`TransaksiAdapter`) juga dipakai ulang untuk "Aktivitas Terakhir" di Home | `Home/MenuHomeAdapter.kt`, `Home/MenuHome.kt`, `item_menu_home.xml` |
| Home: Background Drawable + Grid | Header gradasi biru→indigo (`bg_home_header`), background halaman (`bg_home`), kartu saldo transparan (`bg_saldo_card`), kotak ikon menu (`bg_menu_icon`) + grid 8 menu | `fragment_home.xml`, `drawable/bg_*.xml`, `HomeFragment.kt` |
| Onboard Screen | 3 halaman (Simpanan, Pinjaman, Riwayat) memakai `ViewPager2` + `FragmentStateAdapter` + `DotsIndicator`, tombol Lewati/Lanjut/Mulai | `onboarding/*`, `activity_onboarding.xml`, `fragment_onboarding1..3.xml` |

### Alur Onboarding
```
SplashScreen ──(app_pref.onboarding_selesai == false)──► OnboardingActivity ──► Auth / Base
            └─(sudah pernah onboarding)──► isLogin ? BaseActivity : AuthActivity
Home ► menu "Panduan" ► OnboardingActivity (dari_home = true) ► kembali ke Home
```
Status onboarding disimpan di SharedPreferences `app_pref` (terpisah dari `user_pref`)
supaya tidak ikut terhapus saat logout.

### CustomAdapter vs RecyclerView (yang dipakai di project ini)
| | `JadwalAngsuranAdapter` (BaseAdapter + ListView) | `MenuHomeAdapter` (RecyclerView.Adapter) |
|---|---|---|
| Daur ulang view | manual lewat `convertView` + `tag` | otomatis lewat `ViewHolder` |
| Bentuk tampilan | hanya list vertikal | list / grid lewat `LayoutManager` |
| Method wajib | `getCount`, `getItem`, `getItemId`, `getView` | `onCreateViewHolder`, `onBindViewHolder`, `getItemCount` |
| Klik item | `setOnItemClickListener` di ListView | lambda `onMenuClick` dari adapter |

## 2. Perbandingan dengan GrandDesain sebelumnya

Acuan: SI-KSP Web (React + Tailwind) dari mata kuliah RPL 2
(https://github.com/muhammadnawfalramadhan/SI-KSP) dan tampilan mobile P10.

| Bagian | GrandDesain (SI-KSP Web) | SI-KSP Mobile P10 | SI-KSP Mobile P11 |
|---|---|---|---|
| Navigasi | Sidebar: Dashboard, Anggota, Simpanan, Pinjaman, Angsuran, Transaksi, Laporan, Profil | Bottom Navigation 5 menu | Bottom Navigation + **grid menu** di Home sebagai shortcut |
| Dashboard / Home | Sapaan "Halo, Nama!", kartu gradasi biru-indigo **Kas Anggota**, tombol Setoran/Cairkan | Logo + tombol-tombol vertikal | Header **gradasi biru-indigo** (background drawable), sapaan, kartu **Saldo Simpanan & Sisa Pinjaman** |
| Aktivitas | Card "Aktivitas Terakhir" + "Lihat Semua" | Belum ada di Home | "Aktivitas Terakhir" (3 transaksi) + "Lihat Semua" → tab Riwayat |
| Angsuran | Halaman tersendiri `/angsuran` | Halaman Pinjaman masih placeholder | Ringkasan pinjaman + progres + **jadwal angsuran (ListView + CustomAdapter)** |
| Transaksi | Tabel riwayat transaksi | RecyclerView Riwayat + filter tanggal (branch terpisah) | Digabung ke branch utama |
| Pengenalan aplikasi | Tidak ada | Tidak ada | **Onboard Screen** 3 halaman + DotsIndicator |
| Tema | Light/dark, aksen emerald & biru | Dark, aksen hijau & biru | Dark, aksen hijau & biru + indigo dari web |

Yang belum diambil dari GrandDesain: Manajemen Anggota dan Laporan (fitur admin), karena versi
mobile difokuskan untuk anggota.

## 3. Git

Riwayat branch sebelum P11:
```
* P10                                  (feature/bottom-navigation)
* Quiz Pertemuan 8: Register With Gmail, ...
| * feat: pasang RecyclerView dan tambah DatePicker ...   (feature/riwayat-fitur-baru)
| * feat: tambah data transaksi, adapter, dan layout item ...
| * comit hasil p7
|/
* eee comit
* feat: tambah icon, menu, dan string untuk bottom navigation
* Pertemuan 6: SplashScreen, Login SharedPreferences, Logout, Profil Developer
```

Perintah untuk menampilkan riwayat Git (untuk screenshot laporan):
```bash
git log --oneline --graph --all
```
```bash
git show --stat HEAD
```
