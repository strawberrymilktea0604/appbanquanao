package com.example.appbanquanao;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appbanquanao.managers.ApiManager;
import com.example.appbanquanao.models.NhomSanPham;
import com.example.appbanquanao.models.SanPham;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TrangchuNgdung_Activity extends AppCompatActivity {
    GridView grv2;
    GridView grv1;
    ArrayList<com.example.appbanquanao.models.SanPham> mangSPgrv1; // Danh sách cho GridView
    ArrayList<NhomSanPham> mangNSPgrv2; // Danh sách cho ListView
    NhomSanPhamAdapter adapterGrv2;
    SanPhamAdapter adapterGrv1;
    ApiManager apiManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trangchu_ngdung);

        ImageButton btntimkiem = findViewById(R.id.btntimkiem);
        ImageButton btntrangchu = findViewById(R.id.btntrangchu);
        ImageButton btncard = findViewById(R.id.btncart);
        ImageButton btndonhang = findViewById(R.id.btndonhang);
        ImageButton btncanhan = findViewById(R.id.btncanhan);
        EditText timkiem = findViewById(R.id.timkiem);
        TextView textTendn = findViewById(R.id.tendn); // TextView hiển thị tên đăng nhập
        grv2 = findViewById(R.id.grv2);
        grv1 = findViewById(R.id.grv1);

        // Lấy tên đăng nhập từ SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String tendn = sharedPreferences.getString("tendn", null);

        // Kiểm tra tên đăng nhập
        if (tendn != null) {
            textTendn.setText(tendn);
        } else {
            Intent intent = new Intent(TrangchuNgdung_Activity.this, Login_Activity.class);
            startActivity(intent);
            finish(); // Kết thúc activity nếu chưa đăng nhập
            return;
        }
        grv2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Lấy đối tượng nhóm sản phẩm từ adapter
                NhomSanPham nhomSanPham = mangNSPgrv2.get(position);

                if (nhomSanPham != null) {
                    // Chuyển đến DanhMucSanPham_Activity và truyền mã của nhóm sản phẩm
                    Intent intent = new Intent(TrangchuNgdung_Activity.this, DanhMucSanPham_Activity.class);
                    intent.putExtra("nhomSpId", nhomSanPham.getMaso()); // Gửi mã nhóm sản phẩm
                    startActivity(intent);
                }
            }
        });

        // Gửi tên đăng nhập qua Intent trong sự kiện click
        btntrangchu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TrangchuNgdung_Activity.this, DonHang_User_Activity.class);
                // Gửi tên đăng nhập qua Intent
                intent.putExtra("tendn", tendn); // sử dụng biến tendn đã được xác nhận
                startActivity(intent);
            }
        });
        btntimkiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TrangchuNgdung_Activity.this, TimKiemSanPham_Activity.class);

                // Gửi tên đăng nhập qua Intent
                intent.putExtra("tendn", tendn); // Sử dụng biến tendn đã được lấy từ SharedPreferences

                startActivity(intent);
            }
        });

        btndonhang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TrangchuNgdung_Activity.this, DonHang_User_Activity.class);
                intent.putExtra("tendn", tendn); // Gửi tên đăng nhập
                startActivity(intent);
            }
        });
        btncard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TrangchuNgdung_Activity.this, GioHang_Activity.class);
                intent.putExtra("tendn", tendn); // Gửi tên đăng nhập
                startActivity(intent);
            }
        });
        btncanhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TrangchuNgdung_Activity.this, TrangCaNhan_nguoidung_Activity.class);
                intent.putExtra("tendn", tendn); // Gửi tên đăng nhập
                startActivity(intent);
            }
        });
        // Các sự kiện khác
        timkiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TrangchuNgdung_Activity.this, TimKiemSanPham_Activity.class);

                // Gửi tên đăng nhập qua Intent
                intent.putExtra("tendn", tendn); // Sử dụng biến tendn đã được lấy từ SharedPreferences

                startActivity(intent);
            }
        });

        // Khởi tạo danh sách và adapter
        mangNSPgrv2 = new ArrayList<>();
        mangSPgrv1 = new ArrayList<>();
        adapterGrv2 = new NhomSanPhamAdapter(this, mangNSPgrv2, false);
        adapterGrv1 = new SanPhamAdapter(this, mangSPgrv1, false);
        grv2.setAdapter(adapterGrv2);
        grv1.setAdapter(adapterGrv1);

        apiManager = new ApiManager();

        loadNhomSanPhamFromApi();
        loadSanPhamFromApi();
    }

    private void loadNhomSanPhamFromApi() {
        apiManager.getAllNhomSanPham(new Callback<List<NhomSanPham>>() {
            @Override
            public void onResponse(Call<List<NhomSanPham>> call, Response<List<NhomSanPham>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    mangNSPgrv2.clear();
                    List<NhomSanPham> nhomSanPhamList = response.body();
                    
                    // Lấy tối đa 10 items
                    int maxItems = Math.min(nhomSanPhamList.size(), 10);
                    for (int i = 0; i < maxItems; i++) {
                        mangNSPgrv2.add(nhomSanPhamList.get(i));
                    }
                    
                    adapterGrv2.notifyDataSetChanged();
                } else {
                    Toast.makeText(TrangchuNgdung_Activity.this, "Không thể tải nhóm sản phẩm", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<NhomSanPham>> call, Throwable t) {
                Toast.makeText(TrangchuNgdung_Activity.this, "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadSanPhamFromApi() {
        apiManager.getAllSanPham(new Callback<List<SanPham>>() {
            @Override
            public void onResponse(Call<List<SanPham>> call, Response<List<SanPham>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    mangSPgrv1.clear();
                    List<SanPham> sanPhamList = response.body();
                    
                    // Lấy tối đa 8 items
                    int maxItems = Math.min(sanPhamList.size(), 8);
                    for (int i = 0; i < maxItems; i++) {
                        mangSPgrv1.add(sanPhamList.get(i));
                    }
                    
                    adapterGrv1.notifyDataSetChanged();
                } else {
                    Toast.makeText(TrangchuNgdung_Activity.this, "Không thể tải sản phẩm", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<SanPham>> call, Throwable t) {
                Toast.makeText(TrangchuNgdung_Activity.this, "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
