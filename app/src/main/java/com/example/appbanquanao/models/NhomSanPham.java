package com.example.appbanquanao.models;

public class NhomSanPham {
    private String maso;
    private String tennsp;
    private byte[] anh;

    public NhomSanPham() {}

    public NhomSanPham(String maso, String tennsp, byte[] anh) {
        this.maso = maso;
        this.tennsp = tennsp;
        this.anh = anh;
    }

    public String getMaso() {
        return maso;
    }

    public void setMaso(String maso) {
        this.maso = maso;
    }

    public String getTennsp() {
        return tennsp;
    }

    public void setTennsp(String tennsp) {
        this.tennsp = tennsp;
    }

    public byte[] getAnh() {
        return anh;
    }

    public void setAnh(byte[] anh) {
        this.anh = anh;
    }
}