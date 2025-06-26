package com.example.appbanquanao;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.example.appbanquanao.managers.ApiManager;
import com.example.appbanquanao.models.DonHang;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DonHang_admin_Activity extends AppCompatActivity {

    private ApiManager apiManager;
    private ListView listView;
    private DonHang_Adapter donHangAdapter;

    private Button btnDaDatHang, btnDaThanhToan, btnDangVanChuyen, btnThanhCong;

    private List<DonHang> orders;
    private List<DonHang> filteredOrders;

    private static final int DA_DAT_HANG = 0;
    private static final int DA_THANH_TOAN = 1;
    private static final int DANG_VAN_CHUYEN = 2;
    private static final int THANH_CONG = 3;
    private int currentStatus = DA_DAT_HANG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_don_hang_admin);

        apiManager = new ApiManager();
        listView = findViewById(R.id.listViewChiTiet);
        btnDaDatHang = findViewById(R.id.btn_da_dat_hang);
        btnDaThanhToan = findViewById(R.id.btn_da_thanh_toan);
        btnDangVanChuyen = findViewById(R.id.btn_dang_van_chuyen);
        btnThanhCong = findViewById(R.id.btn_thanh_cong);

        setupListeners();
        loadDonHang();
    }

    private void setupListeners() {
        listView.setOnItemClickListener((parent, view, position, id) -> {
            DonHang order = donHangAdapter.getItem(position);
            if (order != null) {
                Intent intent = new Intent(DonHang_admin_Activity.this, ChiTietDonHang_Admin_Activity.class);
                intent.putExtra("donHangId", order.getId_dathang());
                startActivity(intent);
            }
        });

        btnDaDatHang.setOnClickListener(v -> updateStatus(DA_DAT_HANG));
        btnDaThanhToan.setOnClickListener(v -> updateStatus(DA_THANH_TOAN));
        btnDangVanChuyen.setOnClickListener(v -> updateStatus(DANG_VAN_CHUYEN));
        btnThanhCong.setOnClickListener(v -> updateStatus(THANH_CONG));

        // Navigation buttons
        ImageButton btntrangchu = findViewById(R.id.btntrangchu);
        btntrangchu.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), TrangchuAdmin_Activity.class)));

        ImageButton btncanhan = findViewById(R.id.btncanhan);
        btncanhan.setOnClickListener(v -> {
            SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
            boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);
            if (!isLoggedIn) {
                startActivity(new Intent(getApplicationContext(), Login_Activity.class));
            } else {
                startActivity(new Intent(getApplicationContext(), TrangCaNhan_admin_Activity.class));
            }
        });

        ImageButton btndonhang = findViewById(R.id.btndonhang);
        btndonhang.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), DonHang_admin_Activity.class)));

        ImageButton btnsanpham = findViewById(R.id.btnsanpham);
        btnsanpham.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), Sanpham_admin_Activity.class)));

        ImageButton btnnhomsp = findViewById(R.id.btnnhomsp);
        btnnhomsp.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), Nhomsanpham_admin_Actvity.class)));

        ImageButton btntaikhoan = findViewById(R.id.btntaikhoan);
        btntaikhoan.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), Taikhoan_admin_Activity.class)));
    }

    private void updateStatus(int status) {
        currentStatus = status;
        resetBackgroundColor();
        switch (status) {
            case DA_DAT_HANG:
                btnDaDatHang.setBackgroundColor(Color.GRAY);
                break;
            case DA_THANH_TOAN:
                btnDaThanhToan.setBackgroundColor(Color.GRAY);
                break;
            case DANG_VAN_CHUYEN:
                btnDangVanChuyen.setBackgroundColor(Color.GRAY);
                break;
            case THANH_CONG:
                btnThanhCong.setBackgroundColor(Color.GRAY);
                break;
        }
        filterOrders();
    }

    private void resetBackgroundColor() {
        btnDaDatHang.setBackgroundColor(Color.TRANSPARENT);
        btnDaThanhToan.setBackgroundColor(Color.TRANSPARENT);
        btnDangVanChuyen.setBackgroundColor(Color.TRANSPARENT);
        btnThanhCong.setBackgroundColor(Color.TRANSPARENT);
    }

    private void loadDonHang() {
        apiManager.getAllDonHang(new Callback<List<DonHang>>() {
            @Override
            public void onResponse(Call<List<DonHang>> call, Response<List<DonHang>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    orders = response.body();
                    updateStatus(DA_DAT_HANG); // Set initial state
                } else {
                    Toast.makeText(DonHang_admin_Activity.this, "Không thể tải đơn hàng", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<DonHang>> call, Throwable t) {
                Toast.makeText(DonHang_admin_Activity.this, "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void filterOrders() {
        if (orders == null) return;

        filteredOrders = new ArrayList<>();
        for (DonHang order : orders) {
            if (order.getTrangthai() == currentStatus) {
                filteredOrders.add(order);
            }
        }

        donHangAdapter = new DonHang_Adapter(this, filteredOrders);
        listView.setAdapter(donHangAdapter);
    }
}