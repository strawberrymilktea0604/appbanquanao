package com.example.appbanquanao;

import java.util.ArrayList;
import java.util.List;

public class Order {
    private int id; // ID đơn hàng
    private String tenKh;
    private String diaChi;
    private String sdt;
    private float tongTien;
    private String ngayDatHang;
    private int trangThai;
    private List<ChiTietDonHang> chiTietList; // Danh sách chi tiết đơn hàng

    public static final String[] TRANG_THAI = {
      "Đã đặt hàng", "Đã thanh toán", "Đang giao hàng", "Thành Công"
    };
    public static int DA_DAT_HANG = 0;
    public static int DA_THANH_TOAN = 1;
    public static int DANG_VAN_CHUYEN = 2;
    public static int THANH_CONG = 3;

    public Order(int id, String tenKh, String diaChi, String sdt, float tongTien, String ngayDatHang) {
        this.id = id;
        this.tenKh = tenKh;
        this.diaChi = diaChi;
        this.sdt = sdt;
        this.tongTien = tongTien;
        this.ngayDatHang = ngayDatHang;
        this.chiTietList = new ArrayList<>(); // Khởi tạo danh sách
    }

    public Order(int id, String tenKh, String diaChi, String sdt, float tongTien, String ngayDatHang, int trangThai) {
        this.id = id;
        this.tenKh = tenKh;
        this.diaChi = diaChi;
        this.sdt = sdt;
        this.tongTien = tongTien;
        this.ngayDatHang = ngayDatHang;
        this.chiTietList = new ArrayList<>(); // Khởi tạo danh sách
        this.trangThai = trangThai;
    }

    // Getter methods
    public int getId() {
        return id;
    }

    public String getTenKh() {
        return tenKh;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public String getSdt() {
        return sdt;
    }

    public float getTongTien() {
        return tongTien;
    }

    public String getNgayDatHang() {
        return ngayDatHang;
    }

    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;

    }

    public List<ChiTietDonHang> getChiTietList() {
        return chiTietList; // Getter cho danh sách chi tiết
    }

    public void setChiTietList(List<ChiTietDonHang> chiTietList) {
        this.chiTietList = chiTietList; // Setter cho danh sách chi tiết
    }
}