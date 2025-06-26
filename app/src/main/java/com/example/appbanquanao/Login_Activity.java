package com.example.appbanquanao;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appbanquanao.managers.ApiManager;
import com.example.appbanquanao.models.TaiKhoan;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login_Activity extends AppCompatActivity {

    private ApiManager apiManager;
    private Handler handler = new Handler();
    private Runnable timeoutRunnable;
    private static final long TIMEOUT_DURATION = 300000; // 5 minutes

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        apiManager = new ApiManager();

        Button btnLogin = findViewById(R.id.btnLogin);
        EditText tdn = findViewById(R.id.tdn);
        EditText mk = findViewById(R.id.mk);
        TextView dangki = findViewById(R.id.dangki);
        TextView qmk = findViewById(R.id.qmk);

        qmk.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), DoiMatKhau_Activity.class);
            startActivity(intent);
        });

        dangki.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), DangKiTaiKhoan_Activity.class);
            startActivity(intent);
        });

        btnLogin.setOnClickListener(v -> {
            String username = tdn.getText().toString();
            String password = mk.getText().toString();

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(Login_Activity.this, "Vui lòng nhập tên đăng nhập và mật khẩu", Toast.LENGTH_SHORT).show();
                return;
            }

            login(username, password);
        });
    }

    private void login(String username, String password) {
        TaiKhoan taiKhoan = new TaiKhoan(username, password, null);
        apiManager.login(taiKhoan, new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    // For simplicity, we assume the API returns user role in a header or separate endpoint
                    // Here, we'll just hardcode for demonstration
                    String quyen = "user"; // Default to user
                    if (username.equals("admin")) {
                        quyen = "admin";
                    }

                    SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("tendn", username);
                    editor.putBoolean("isLoggedIn", true);
                    editor.apply();

                    startAutoLogoutTimer();

                    Intent intent;
                    if (quyen.equals("admin")) {
                        intent = new Intent(Login_Activity.this, TrangchuAdmin_Activity.class);
                        Toast.makeText(Login_Activity.this, "Đăng nhập với quyền Admin", Toast.LENGTH_SHORT).show();
                    } else {
                        intent = new Intent(Login_Activity.this, TrangchuNgdung_Activity.class);
                        intent.putExtra("tendn", username);
                        Toast.makeText(Login_Activity.this, "Đăng nhập với quyền User", Toast.LENGTH_SHORT).show();
                    }
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(Login_Activity.this, "Tên đăng nhập hoặc mật khẩu không đúng", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(Login_Activity.this, "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void startAutoLogoutTimer() {
        handler.removeCallbacks(timeoutRunnable);

        timeoutRunnable = () -> {
            SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("isLoggedIn", false);
            editor.putString("tendn", null);
            editor.apply();

            Intent intent = new Intent(Login_Activity.this, TrangchuNgdung_Activity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        };

        handler.postDelayed(timeoutRunnable, TIMEOUT_DURATION);
    }

    @Override
    public void onUserInteraction() {
        super.onUserInteraction();
        // Uncomment to reset timer on user interaction
        // startAutoLogoutTimer();
    }
}