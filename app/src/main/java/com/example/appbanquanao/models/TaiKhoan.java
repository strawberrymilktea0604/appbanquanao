package com.example.appbanquanao.models;

public class TaiKhoan {
    private String tendn;
    private String matkhau;
    private String quyen;

    public TaiKhoan() {}

    public TaiKhoan(String tendn, String matkhau, String quyen) {
        this.tendn = tendn;
        this.matkhau = matkhau;
        this.quyen = quyen;
    }

    public String getTendn() {
        return tendn;
    }

    public void setTendn(String tendn) {
        this.tendn = tendn;
    }

    public String getMatkhau() {
        return matkhau;
    }

    public void setMatkhau(String matkhau) {
        this.matkhau = matkhau;
    }

    public String getQuyen() {
        return quyen;
    }

    public void setQuyen(String quyen) {
        this.quyen = quyen;
    }
}