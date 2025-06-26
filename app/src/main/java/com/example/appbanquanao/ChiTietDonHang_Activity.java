package com.example.appbanquanao;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.appbanquanao.managers.ApiManager;
import com.example.appbanquanao.models.ChiTietDonHang;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChiTietDonHang_Activity extends AppCompatActivity {

    private ApiManager apiManager;
    private ListView listViewChiTiet;
    private ChiTietDonHangAdapter chiTietAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_don_hang);

        apiManager = new ApiManager();
        listViewChiTiet = findViewById(R.id.listtk);

        ImageButton btntimkiem = findViewById(R.id.btntimkiem);
        ImageButton btntrangchu = findViewById(R.id.btntrangchu);
        ImageButton btncard = findViewById(R.id.btncart);
        ImageButton btndonhang = findViewById(R.id.btndonhang);
        ImageButton btncanhan = findViewById(R.id.btncanhan);
        ImageView ql = findViewById(R.id.back);
        ql.setOnClickListener(v -> finish());

        String donHangId = getIntent().getStringExtra("donHangId");

        if (donHangId != null && !donHangId.isEmpty()) {
            loadChiTietDonHang(donHangId);
        } else {
            loadAllChiTietDonHang();
        }

        setupNavigation(btntimkiem, btntrangchu, btncard, btndonhang, btncanhan);
    }

    private void loadChiTietDonHang(String orderId) {
        apiManager.getChiTietByOrder(orderId, new Callback<List<ChiTietDonHang>>() {
            @Override
            public void onResponse(Call<List<ChiTietDonHang>> call, Response<List<ChiTietDonHang>> response) {
                if (response.isSuccessful() && response.body() != null && !response.body().isEmpty()) {
                    chiTietAdapter = new ChiTietDonHangAdapter(ChiTietDonHang_Activity.this, response.body());
                    listViewChiTiet.setAdapter(chiTietAdapter);
                } else {
                    Toast.makeText(ChiTietDonHang_Activity.this, "Không tìm thấy chi tiết cho đơn hàng!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<ChiTietDonHang>> call, Throwable t) {
                Toast.makeText(ChiTietDonHang_Activity.this, "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadAllChiTietDonHang() {
        apiManager.getAllChiTiet(new Callback<List<ChiTietDonHang>>() {
            @Override
            public void onResponse(Call<List<ChiTietDonHang>> call, Response<List<ChiTietDonHang>> response) {
                if (response.isSuccessful() && response.body() != null && !response.body().isEmpty()) {
                    chiTietAdapter = new ChiTietDonHangAdapter(ChiTietDonHang_Activity.this, response.body());
                    listViewChiTiet.setAdapter(chiTietAdapter);
                } else {
                    Toast.makeText(ChiTietDonHang_Activity.this, "Không có chi tiết đơn hàng nào!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<ChiTietDonHang>> call, Throwable t) {
                Toast.makeText(ChiTietDonHang_Activity.this, "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupNavigation(ImageButton btntimkiem, ImageButton btntrangchu, ImageButton btncard, ImageButton btndonhang, ImageButton btncanhan) {
        btncard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //kiểm tra trạng thái đăng nhập của ng dùng
                SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);

                if (!isLoggedIn) {
                    // Chưa đăng nhập, chuyển đến trang login
                    Intent intent = new Intent(getApplicationContext(),Login_Activity.class);
                    startActivity(intent);
                } else {
                    // Đã đăng nhập, chuyển đến trang 2
                    Intent intent = new Intent(getApplicationContext(), GioHang_Activity.class);
                    startActivity(intent);
                }
            }
        });
        btntrangchu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Đã đăng nhập, chuyển đến trang đơn hàng
                Intent intent = new Intent(getApplicationContext(), TrangchuNgdung_Activity.class);

                startActivity(intent);
            }
        });
        btndonhang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //kiểm tra trạng thái đăng nhập của ng dùng
                SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);

                // Đã đăng nhập, chuyển đến trang đơn hàng
                Intent intent = new Intent(getApplicationContext(), DonHang_User_Activity.class);

                startActivity(intent);
            }

        });
        btncanhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //kiểm tra trạng thái đăng nhập của ng dùng
                SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);

                if (!isLoggedIn) {
                    // Chưa đăng nhập, chuyển đến trang login
                    Intent intent = new Intent(getApplicationContext(),Login_Activity.class);
                    startActivity(intent);
                } else {
                    // Đã đăng nhập, chuyển đến trang 2
                    Intent intent = new Intent(getApplicationContext(), TrangCaNhan_nguoidung_Activity.class);
                    startActivity(intent);
                }
            }
        });

        btntimkiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent a=new Intent(getApplicationContext(),TimKiemSanPham_Activity.class);
                startActivity(a);
            }
        });
    }
}