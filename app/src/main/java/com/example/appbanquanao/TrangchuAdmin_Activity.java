package com.example.appbanquanao;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.appbanquanao.managers.ApiManager;
import com.example.appbanquanao.models.NhomSanPham;
import com.example.appbanquanao.models.SanPham;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TrangchuAdmin_Activity extends AppCompatActivity {

    GridView grv2;
    GridView grv1;
    ArrayList<com.example.appbanquanao.models.SanPham> mangSPgrv1; // Danh sách cho GridView

    ArrayList<NhomSanPham> mangNSPgrv2; // Danh sách cho ListView

    NhomSanPham_trangChuadmin_Adapter adapterGrv2;
    SanPham_TrangChuAdmin_Adapter adapterGrv1;
    ApiManager apiManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trangchu_admin);

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
                //kiểm tra trạng thái đăng nhập của ng dùng
                SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);

                if (!isLoggedIn) {
                    // Chưa đăng nhập, chuyển đến trang login
                    Intent intent = new Intent(getApplicationContext(),Login_Activity.class);
                    startActivity(intent);
                } else {
                    // Đã đăng nhập, chuyển đến trang 2
                    Intent intent = new Intent(getApplicationContext(), DonHang_admin_Activity.class);
                    startActivity(intent);
                }
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
        grv2=findViewById(R.id.grv2);
        grv1=findViewById(R.id.grv1);
        mangNSPgrv2= new ArrayList<>(); // Khởi tạo danh sách
        mangSPgrv1= new ArrayList<>(); // Khởi tạo danh sách
        adapterGrv2 = new NhomSanPham_trangChuadmin_Adapter(this, mangNSPgrv2, false) ;

         // false để hiển thị 4 thuộc tính
        grv2.setAdapter(adapterGrv2);

        adapterGrv1= new SanPham_TrangChuAdmin_Adapter(this, mangSPgrv1, false) ;

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
                    Toast.makeText(TrangchuAdmin_Activity.this, "Không thể tải nhóm sản phẩm", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<NhomSanPham>> call, Throwable t) {
                Toast.makeText(TrangchuAdmin_Activity.this, "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(TrangchuAdmin_Activity.this, "Không thể tải sản phẩm", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<SanPham>> call, Throwable t) {
                Toast.makeText(TrangchuAdmin_Activity.this, "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}