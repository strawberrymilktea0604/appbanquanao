package com.example.appbanquanao.managers;

import com.example.appbanquanao.models.*;
import com.example.appbanquanao.network.ApiClient;
import com.example.appbanquanao.network.ApiService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class ApiManager {
    private ApiService apiService;

    public ApiManager() {
        apiService = ApiClient.getClient().create(ApiService.class);
    }

    // NhomSanPham methods
    public void getAllNhomSanPham(Callback<List<NhomSanPham>> callback) {
        apiService.getAllNhom().enqueue(callback);
    }

    public void createNhomSanPham(NhomSanPham nhom, Callback<NhomSanPham> callback) {
        apiService.createNhom(nhom).enqueue(callback);
    }

    public void updateNhomSanPham(String maso, NhomSanPham nhom, Callback<NhomSanPham> callback) {
        apiService.updateNhom(maso, nhom).enqueue(callback);
    }

    public void deleteNhomSanPham(String maso, Callback<Void> callback) {
        apiService.deleteNhom(maso).enqueue(callback);
    }

    // SanPham methods
    public void getAllSanPham(Callback<List<SanPham>> callback) {
        apiService.getAllSanPham().enqueue(callback);
    }

    public void getSanPhamByNhom(String maso, Callback<List<SanPham>> callback) {
        apiService.getSanPhamByNhom(maso).enqueue(callback);
    }

    public void searchSanPham(String keyword, Callback<List<SanPham>> callback) {
        apiService.searchSanPham(keyword).enqueue(callback);
    }

    public void createSanPham(SanPham sanPham, Callback<SanPham> callback) {
        apiService.createSanPham(sanPham).enqueue(callback);
    }

    public void updateSanPham(String masp, SanPham sanPham, Callback<SanPham> callback) {
        apiService.updateSanPham(masp, sanPham).enqueue(callback);
    }

    public void deleteSanPham(String masp, Callback<Void> callback) {
        apiService.deleteSanPham(masp).enqueue(callback);
    }

    // DonHang methods
    public void getAllDonHang(Callback<List<DonHang>> callback) {
        apiService.getAllDonHang().enqueue(callback);
    }

    public void getDonHangByCustomer(String tenkh, Callback<List<DonHang>> callback) {
        apiService.getDonHangByCustomer(tenkh).enqueue(callback);
    }

    public void createDonHang(DonHang donHang, Callback<DonHang> callback) {
        apiService.createDonHang(donHang).enqueue(callback);
    }

    public void updateDonHangStatus(String id, int status, Callback<DonHang> callback) {
        StatusUpdate statusUpdate = new StatusUpdate(status);
        apiService.updateDonHangStatus(id, statusUpdate).enqueue(callback);
    }

    // ChiTietDonHang methods
    public void getAllChiTiet(Callback<List<ChiTietDonHang>> callback) {
        apiService.getAllChiTiet().enqueue(callback);
    }

    public void getChiTietByOrder(String orderId, Callback<List<ChiTietDonHang>> callback) {
        apiService.getChiTietByOrder(orderId).enqueue(callback);
    }

    public void createChiTiet(ChiTietDonHang chiTiet, Callback<ChiTietDonHang> callback) {
        apiService.createChiTiet(chiTiet).enqueue(callback);
    }

    public void updateChiTietStatus(String id, int status, Callback<ChiTietDonHang> callback) {
        StatusUpdate statusUpdate = new StatusUpdate(status);
        apiService.updateChiTietStatus(id, statusUpdate).enqueue(callback);
    }

    // TaiKhoan methods
    public void getAllTaiKhoan(Callback<List<TaiKhoan>> callback) {
        apiService.getAllTaiKhoan().enqueue(callback);
    }

    public void createTaiKhoan(TaiKhoan taiKhoan, Callback<TaiKhoan> callback) {
        apiService.createTaiKhoan(taiKhoan).enqueue(callback);
    }

    public void deleteTaiKhoan(String tendn, Callback<Void> callback) {
        apiService.deleteTaiKhoan(tendn).enqueue(callback);
    }

    // Auth methods
    public void login(TaiKhoan taiKhoan, Callback<Void> callback) {
        apiService.login(taiKhoan).enqueue(callback);
    }

    public void register(TaiKhoan taiKhoan, Callback<Void> callback) {
        apiService.register(taiKhoan).enqueue(callback);
    }

    // Helper class for status updates
    public static class StatusUpdate {
        private int trangthai;

        public StatusUpdate(int trangthai) {
            this.trangthai = trangthai;
        }

        public int getTrangthai() {
            return trangthai;
        }

        public void setTrangthai(int trangthai) {
            this.trangthai = trangthai;
        }
    }
}