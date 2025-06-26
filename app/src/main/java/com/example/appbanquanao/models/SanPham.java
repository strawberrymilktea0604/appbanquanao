package com.example.appbanquanao.models;

public class SanPham {
    private String masp;
    private String tensp;
    private float dongia;
    private String mota;
    private String ghichu;
    private int soluongkho;
    private String maso;
    private byte[] anh;

    public SanPham() {}

    public SanPham(String masp, String tensp, float dongia, String mota, String ghichu, int soluongkho, String maso, byte[] anh) {
        this.masp = masp;
        this.tensp = tensp;
        this.dongia = dongia;
        this.mota = mota;
        this.ghichu = ghichu;
        this.soluongkho = soluongkho;
        this.maso = maso;
        this.anh = anh;
    }

    public String getMasp() {
        return masp;
    }

    public void setMasp(String masp) {
        this.masp = masp;
    }

    public String getTensp() {
        return tensp;
    }

    public void setTensp(String tensp) {
        this.tensp = tensp;
    }

    public float getDongia() {
        return dongia;
    }

    public void setDongia(float dongia) {
        this.dongia = dongia;
    }

    public String getMota() {
        return mota;
    }

    public void setMota(String mota) {
        this.mota = mota;
    }

    public String getGhichu() {
        return ghichu;
    }

    public void setGhichu(String ghichu) {
        this.ghichu = ghichu;
    }

    public int getSoluongkho() {
        return soluongkho;
    }

    public void setSoluongkho(int soluongkho) {
        this.soluongkho = soluongkho;
    }

    public String getMaso() {
        return maso;
    }

    public void setMaso(String maso) {
        this.maso = maso;
    }

    public byte[] getAnh() {
        return anh;
    }

    public void setAnh(byte[] anh) {
        this.anh = anh;
    }
}