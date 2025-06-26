package com.example.appbanquanao;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.example.appbanquanao.managers.ApiManager;
import com.example.appbanquanao.models.NhomSanPham;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Nhomsanpham_admin_Actvity extends AppCompatActivity {
    private ListView lv;
    private FloatingActionButton addButton;
    private ArrayList<NhomSanPham> mangNSP;
    private NhomSanPhamAdapter adapter;
    private ApiManager apiManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nhomsanpham_admin_actvity);

        initializeViews();
        apiManager = new ApiManager();
        loadDataFromApi();
        ImageButton btntrangchu=findViewById(R.id.btntrangchu);
        btntrangchu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent a=new Intent(getApplicationContext(),TrangchuAdmin_Activity.class);
                startActivity(a);
            }
        });
        ImageButton btncanhan=findViewById(R.id.btncanhan);
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
                    Intent intent = new Intent(getApplicationContext(), TrangCaNhan_admin_Activity.class);
                    startActivity(intent);
                }
            }
        });
        ImageButton btndonhang=findViewById(R.id.btndonhang);
        btndonhang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent a=new Intent(getApplicationContext(),DonHang_admin_Activity.class);
                startActivity(a);
            }
        });
        ImageButton btnsanpham    =findViewById(R.id.btnsanpham);
        btnsanpham.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent a=new Intent(getApplicationContext(),Sanpham_admin_Activity.class);
                startActivity(a);
            }
        });
        ImageButton btnnhomsp   =findViewById(R.id.btnnhomsp);
        btnnhomsp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent a=new Intent(getApplicationContext(),Nhomsanpham_admin_Actvity.class);
                startActivity(a);
            }
        });
        ImageButton btntaikhoan    =findViewById(R.id.btntaikhoan);
        btntaikhoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent a=new Intent(getApplicationContext(),Taikhoan_admin_Activity.class);
                startActivity(a);
            }
        });
        addButton.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), ThemNhomSanPham_Activity.class);
            startActivity(intent);
        });
    }

    private void initializeViews() {
        lv = findViewById(R.id.listtk);
        addButton = findViewById(R.id.btnthem);
        mangNSP = new ArrayList<>();

        adapter = new NhomSanPhamAdapter(Nhomsanpham_admin_Actvity.this, mangNSP, true);

        lv.setAdapter(adapter);
    }

    
    private void loadDataFromApi() {
        apiManager.getAllNhomSanPham(new Callback<List<NhomSanPham>>() {
            @Override
            public void onResponse(Call<List<NhomSanPham>> call, Response<List<NhomSanPham>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    mangNSP.clear();
                    mangNSP.addAll(response.body());
                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(Nhomsanpham_admin_Actvity.this, "Không thể tải dữ liệu nhóm sản phẩm", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<NhomSanPham>> call, Throwable t) {
                Toast.makeText(Nhomsanpham_admin_Actvity.this, "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadDataFromApi(); // Reload data when returning to this activity
    }
    private byte[] convertBitmapToByteArray(int resourceId) {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), resourceId);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }
}