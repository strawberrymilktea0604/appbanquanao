package com.example.appbanquanao;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.appbanquanao.managers.ApiManager;
import com.example.appbanquanao.models.TaiKhoan;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ThemTaiKhoan_Activity extends AppCompatActivity {
    private ApiManager apiManager;
    private RadioButton admin;
    private RadioButton user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_tai_khoan);

        apiManager = new ApiManager();

        Button btnadd = findViewById(R.id.btnadd);
        EditText tendn = findViewById(R.id.tdn);
        EditText matkhau = findViewById(R.id.mk);
        admin = findViewById(R.id.admin);
        user = findViewById(R.id.user);

        btnadd.setOnClickListener(view -> {
            String username = tendn.getText().toString().trim();
            String password = matkhau.getText().toString().trim();
            String quyen = "";

            if (admin.isChecked()) {
                quyen = "admin";
            } else if (user.isChecked()) {
                quyen = "user";
            }

            if (username.isEmpty() || password.isEmpty() || quyen.isEmpty()) {
                Toast.makeText(ThemTaiKhoan_Activity.this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                return;
            }

            TaiKhoan taiKhoan = new TaiKhoan(username, password, quyen);
            
            apiManager.createTaiKhoan(taiKhoan, new Callback<TaiKhoan>() {
                @Override
                public void onResponse(Call<TaiKhoan> call, Response<TaiKhoan> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(ThemTaiKhoan_Activity.this, "Thêm tài khoản thành công", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(ThemTaiKhoan_Activity.this, Taikhoan_admin_Activity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(ThemTaiKhoan_Activity.this, "Thêm tài khoản thất bại", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<TaiKhoan> call, Throwable t) {
                    Toast.makeText(ThemTaiKhoan_Activity.this, "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });
    }
}