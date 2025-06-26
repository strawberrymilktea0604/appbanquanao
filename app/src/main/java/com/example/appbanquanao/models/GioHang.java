package com.example.appbanquanao.models;

public class GioHang {
    private String id;
    private String tendn;
    private String masp;
    private String tensp;
    private float dongia;
    private int soluong;
    private byte[] anh;
    private ChiTietSanPham sanPham; // For compatibility with GioHangManager

    public GioHang() {}

    public GioHang(String id, String tendn, String masp, String tensp, float dongia, int soluong, byte[] anh) {
        this.id = id;
        this.tendn = tendn;
        this.masp = masp;
        this.tensp = tensp;
        this.dongia = dongia;
        this.soluong = soluong;
        this.anh = anh;
    }

    // Constructor for GioHangManager compatibility
    public GioHang(ChiTietSanPham sanPham, int soluong) {
        this.sanPham = sanPham;
        this.masp = sanPham.getMasp();
        this.tensp = sanPham.getTensp();
        this.dongia = sanPham.getDongia();
        this.soluong = soluong;
        this.anh = sanPham.getAnh();
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getTendn() { return tendn; }
    public void setTendn(String tendn) { this.tendn = tendn; }

    public String getMasp() { return masp; }
    public void setMasp(String masp) { this.masp = masp; }

    public String getTensp() { return tensp; }
    public void setTensp(String tensp) { this.tensp = tensp; }

    public float getDongia() { return dongia; }
    public void setDongia(float dongia) { this.dongia = dongia; }

    public int getSoluong() { return soluong; }
    public void setSoluong(int soluong) { this.soluong = soluong; }

    // Alias for compatibility
    public int getSoLuong() { return soluong; }
    public void setSoLuong(int soluong) { this.soluong = soluong; }

    public byte[] getAnh() { return anh; }
    public void setAnh(byte[] anh) { this.anh = anh; }

    // For GioHangManager compatibility
    public ChiTietSanPham getSanPham() { 
        if (sanPham == null) {
            sanPham = new ChiTietSanPham(id, masp, tensp, dongia, "", "", soluong, "", anh);
        }
        return sanPham; 
    }
    public void setSanPham(ChiTietSanPham sanPham) { this.sanPham = sanPham; }

    public float getTongGia() {
        return dongia * soluong;
    }
}