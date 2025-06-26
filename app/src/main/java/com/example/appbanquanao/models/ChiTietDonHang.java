package com.example.appbanquanao.models;

public class ChiTietDonHang {
    private String id_chitiet;
    private String id_dathang;
    private String masp;
    private int soluong;
    private float dongia;
    private byte[] anh;
    private int trangthai;

    public ChiTietDonHang() {}

    public ChiTietDonHang(String id_chitiet, String id_dathang, String masp, int soluong, float dongia, byte[] anh, int trangthai) {
        this.id_chitiet = id_chitiet;
        this.id_dathang = id_dathang;
        this.masp = masp;
        this.soluong = soluong;
        this.dongia = dongia;
        this.anh = anh;
        this.trangthai = trangthai;
    }

    public String getId_chitiet() {
        return id_chitiet;
    }

    public void setId_chitiet(String id_chitiet) {
        this.id_chitiet = id_chitiet;
    }

    public String getId_dathang() {
        return id_dathang;
    }

    public void setId_dathang(String id_dathang) {
        this.id_dathang = id_dathang;
    }

    public String getMasp() {
        return masp;
    }

    public void setMasp(String masp) {
        this.masp = masp;
    }

    public int getSoluong() {
        return soluong;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }

    public float getDongia() {
        return dongia;
    }

    public void setDongia(float dongia) {
        this.dongia = dongia;
    }

    public byte[] getAnh() {
        return anh;
    }

    public void setAnh(byte[] anh) {
        this.anh = anh;
    }

    public int getTrangthai() {
        return trangthai;
    }

    public void setTrangthai(int trangthai) {
        this.trangthai = trangthai;
    }
}