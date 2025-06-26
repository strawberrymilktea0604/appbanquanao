package com.example.appbanquanao.models;

public class DonHang {
    private String id_dathang;
    private String tenkh;
    private String diachi;
    private String sdt;
    private float tongthanhtoan;
    private String ngaydathang;
    private int trangthai;

    public DonHang() {}

    public DonHang(String id_dathang, String tenkh, String diachi, String sdt, float tongthanhtoan, String ngaydathang, int trangthai) {
        this.id_dathang = id_dathang;
        this.tenkh = tenkh;
        this.diachi = diachi;
        this.sdt = sdt;
        this.tongthanhtoan = tongthanhtoan;
        this.ngaydathang = ngaydathang;
        this.trangthai = trangthai;
    }

    public String getId_dathang() {
        return id_dathang;
    }

    public void setId_dathang(String id_dathang) {
        this.id_dathang = id_dathang;
    }

    public String getTenkh() {
        return tenkh;
    }

    public void setTenkh(String tenkh) {
        this.tenkh = tenkh;
    }

    public String getDiachi() {
        return diachi;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public float getTongthanhtoan() {
        return tongthanhtoan;
    }

    public void setTongthanhtoan(float tongthanhtoan) {
        this.tongthanhtoan = tongthanhtoan;
    }

    public String getNgaydathang() {
        return ngaydathang;
    }

    public void setNgaydathang(String ngaydathang) {
        this.ngaydathang = ngaydathang;
    }

    public int getTrangthai() {
        return trangthai;
    }

    public void setTrangthai(int trangthai) {
        this.trangthai = trangthai;
    }
}