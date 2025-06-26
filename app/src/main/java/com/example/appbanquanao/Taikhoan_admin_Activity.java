package com.example.appbanquanao;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.example.appbanquanao.managers.ApiManager;
import com.example.appbanquanao.models.TaiKhoan;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Taikhoan_admin_Activity extends AppCompatActivity {

    private ApiManager apiManager;
    private ListView lv;
    private ArrayList<TaiKhoan> mangTK;
    private TaiKhoanAdapter adapter;
    private FloatingActionButton dauconggocphai;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_taikhoan_admin);

        apiManager = new ApiManager();
        dauconggocphai = findViewById(R.id.btnthem);
        lv = findViewById(R.id.listtk);

        setupNavigation();

        dauconggocphai.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), ThemTaiKhoan_Activity.class);
            startActivity(intent);
        });

        mangTK = new ArrayList<>();
        adapter = new TaiKhoanAdapter(getApplicationContext(), R.layout.ds_taikhoan, mangTK);
        lv.setAdapter(adapter);

        loadTaiKhoanData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadTaiKhoanData(); // Reload data when returning to this activity
    }

    private void setupNavigation() {
        ImageButton btntrangchu = findViewById(R.id.btntrangchu);
        btntrangchu.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), TrangchuAdmin_Activity.class);
            startActivity(intent);
        });

        ImageButton btncanhan = findViewById(R.id.btncanhan);
        btncanhan.setOnClickListener(view -> {
            SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
            boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);

            if (!isLoggedIn) {
                Intent intent = new Intent(getApplicationContext(), Login_Activity.class);
                startActivity(intent);
            } else {
                Intent intent = new Intent(getApplicationContext(), TrangCaNhan_admin_Activity.class);
                startActivity(intent);
            }
        });

        ImageButton btndonhang = findViewById(R.id.btndonhang);
        btndonhang.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), DonHang_admin_Activity.class);
            startActivity(intent);
        });

        ImageButton btnsanpham = findViewById(R.id.btnsanpham);
        btnsanpham.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), Sanpham_admin_Activity.class);
            startActivity(intent);
        });

        ImageButton btnnhomsp = findViewById(R.id.btnnhomsp);
        btnnhomsp.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), Nhomsanpham_admin_Actvity.class);
            startActivity(intent);
        });

        ImageButton btntaikhoan = findViewById(R.id.btntaikhoan);
        btntaikhoan.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), Taikhoan_admin_Activity.class);
            startActivity(intent);
        });
    }

    private void loadTaiKhoanData() {
        apiManager.getAllTaiKhoan(new Callback<List<TaiKhoan>>() {
            @Override
            public void onResponse(Call<List<TaiKhoan>> call, Response<List<TaiKhoan>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    mangTK.clear();
                    mangTK.addAll(response.body());
                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(Taikhoan_admin_Activity.this, "Không thể tải danh sách tài khoản", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<TaiKhoan>> call, Throwable t) {
                Toast.makeText(Taikhoan_admin_Activity.this, "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}