package com.example.appbanquanao.network;

import com.example.appbanquanao.models.SanPham;
import com.example.appbanquanao.models.NhomSanPham;
import com.example.appbanquanao.models.TaiKhoan;
import com.example.appbanquanao.models.DonHang;
import com.example.appbanquanao.models.ChiTietDonHang;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiService {
    // Authentication
    @POST("auth/register")
    Call<Void> register(@Body TaiKhoan taiKhoan);

    @POST("auth/login")
    Call<Void> login(@Body TaiKhoan taiKhoan);

    @PUT("auth/change-password")
    Call<Void> changePassword(@Body Object body);

    // NhomSanPham
    @GET("nhom-san-pham")
    Call<List<NhomSanPham>> getAllNhom();

    @GET("nhom-san-pham/{maso}")
    Call<NhomSanPham> getNhom(@Path("maso") String maso);

    @POST("nhom-san-pham")
    Call<NhomSanPham> createNhom(@Body NhomSanPham nosp);

    @PUT("nhom-san-pham/{maso}")
    Call<NhomSanPham> updateNhom(@Path("maso") String maso, @Body NhomSanPham nosp);

    @DELETE("nhom-san-pham/{maso}")
    Call<Void> deleteNhom(@Path("maso") String maso);

    // SanPham
    @GET("san-pham")
    Call<List<SanPham>> getAllSanPham();

    @GET("san-pham/{masp}")
    Call<SanPham> getSanPham(@Path("masp") String masp);

    @GET("san-pham/nhom/{maso}")
    Call<List<SanPham>> getSanPhamByNhom(@Path("maso") String maso);

    @GET("san-pham/search/{keyword}")
    Call<List<SanPham>> searchSanPham(@Path("keyword") String keyword);

    @POST("san-pham")
    Call<SanPham> createSanPham(@Body SanPham sp);

    @PUT("san-pham/{masp}")
    Call<SanPham> updateSanPham(@Path("masp") String masp, @Body SanPham sp);

    @DELETE("san-pham/{masp}")
    Call<Void> deleteSanPham(@Path("masp") String masp);

    @PATCH("san-pham/{masp}/stock")
    Call<SanPham> updateStock(@Path("masp") String masp, @Body Object body);

    // DonHang
    @GET("don-hang")
    Call<List<DonHang>> getAllDonHang();

    @GET("don-hang/{id}")
    Call<DonHang> getDonHang(@Path("id") String id);

    @GET("don-hang/customer/{tenkh}")
    Call<List<DonHang>> getDonHangByCustomer(@Path("tenkh") String tenkh);

    @POST("don-hang")
    Call<DonHang> createDonHang(@Body DonHang dh);

    @PUT("don-hang/{id}")
    Call<DonHang> updateDonHang(@Path("id") String id, @Body DonHang dh);

    @PATCH("don-hang/{id}/status")
    Call<DonHang> updateDonHangStatus(@Path("id") String id, @Body Object body);

    @DELETE("don-hang/{id}")
    Call<Void> deleteDonHang(@Path("id") String id);

    // ChiTietDonHang
    @GET("chi-tiet-don-hang")
    Call<List<ChiTietDonHang>> getAllChiTiet();

    @GET("chi-tiet-don-hang/{id}")
    Call<ChiTietDonHang> getChiTiet(@Path("id") String id);

    @GET("chi-tiet-don-hang/order/{orderId}")
    Call<List<ChiTietDonHang>> getChiTietByOrder(@Path("orderId") String orderId);

    @POST("chi-tiet-don-hang")
    Call<ChiTietDonHang> createChiTiet(@Body ChiTietDonHang ct);

    @POST("chi-tiet-don-hang/bulk")
    Call<List<ChiTietDonHang>> createChiTietBulk(@Body Object body);

    @PUT("chi-tiet-don-hang/{id}")
    Call<ChiTietDonHang> updateChiTiet(@Path("id") String id, @Body ChiTietDonHang ct);

    @PATCH("chi-tiet-don-hang/{id}/status")
    Call<ChiTietDonHang> updateChiTietStatus(@Path("id") String id, @Body Object body);

    @DELETE("chi-tiet-don-hang/{id}")
    Call<Void> deleteChiTiet(@Path("id") String id);

    // TaiKhoan
    @GET("tai-khoan")
    Call<List<TaiKhoan>> getAllTaiKhoan();

    @GET("tai-khoan/{tendn}")
    Call<TaiKhoan> getTaiKhoan(@Path("tendn") String tendn);

    @POST("tai-khoan")
    Call<TaiKhoan> createTaiKhoan(@Body TaiKhoan tk);

    @PUT("tai-khoan/{tendn}")
    Call<TaiKhoan> updateTaiKhoan(@Path("tendn") String tendn, @Body TaiKhoan tk);

    @PATCH("tai-khoan/{tendn}/role")
    Call<TaiKhoan> updateTaiKhoanRole(@Path("tendn") String tendn, @Body Object body);

    @DELETE("tai-khoan/{tendn}")
    Call<Void> deleteTaiKhoan(@Path("tendn") String tendn);
}
