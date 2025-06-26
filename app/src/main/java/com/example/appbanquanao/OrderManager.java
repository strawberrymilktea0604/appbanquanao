package com.example.appbanquanao;

import android.content.Context;
import com.example.appbanquanao.managers.ApiManager;
import com.example.appbanquanao.models.DonHang;
import com.example.appbanquanao.models.ChiTietDonHang;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import java.util.UUID;

public class OrderManager {
    private ApiManager apiManager;

    public OrderManager(Context context) {
        apiManager = new ApiManager();
    }

    // Interface for callbacks
    public interface OrderCallback {
        void onSuccess(String orderId);
        void onError(String error);
    }

    public interface OrderDetailsCallback {
        void onSuccess();
        void onError(String error);
    }

    // Thêm đơn hàng mới
    public void addOrder(String tenKh, String diaChi, String sdt, float tongThanhToan, OrderCallback callback) {
        String orderId = UUID.randomUUID().toString();
        DonHang donHang = new DonHang(orderId, tenKh, diaChi, sdt, tongThanhToan, null, 0);
        
        apiManager.createDonHang(donHang, new Callback<DonHang>() {
            @Override
            public void onResponse(Call<DonHang> call, Response<DonHang> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body().getId_dathang());
                } else {
                    callback.onError("Không thể tạo đơn hàng");
                }
            }

            @Override
            public void onFailure(Call<DonHang> call, Throwable t) {
                callback.onError("Lỗi kết nối: " + t.getMessage());
            }
        });
    }

    // Thêm chi tiết đơn hàng mới
    public void addOrderDetails(String idDonHang, String masp, int soluong, float dongia, byte[] anh, OrderDetailsCallback callback) {
        String chiTietId = UUID.randomUUID().toString();
        ChiTietDonHang chiTiet = new ChiTietDonHang(chiTietId, idDonHang, masp, soluong, dongia, anh, 0);
        
        apiManager.createChiTiet(chiTiet, new Callback<ChiTietDonHang>() {
            @Override
            public void onResponse(Call<ChiTietDonHang> call, Response<ChiTietDonHang> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess();
                } else {
                    callback.onError("Không thể tạo chi tiết đơn hàng");
                }
            }

            @Override
            public void onFailure(Call<ChiTietDonHang> call, Throwable t) {
                callback.onError("Lỗi kết nối: " + t.getMessage());
            }
        });
    }
}
