package com.example.appbanquanao;

import androidx.appcompat.app.AppCompatActivity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appbanquanao.managers.ApiManager;
import com.example.appbanquanao.models.ChiTietDonHang;
import com.example.appbanquanao.models.DonHang;
import com.example.appbanquanao.models.GioHang;

import java.util.List;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GioHang_Activity extends AppCompatActivity {
    private ListView listView;
    private GioHangAdapter adapter;
    private GioHangManager gioHangManager;
    private Button thanhtoan;
    private ApiManager apiManager;
    private TextView txtTongTien;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gio_hang);

        apiManager = new ApiManager();
        listView = findViewById(R.id.listtk);
        thanhtoan = findViewById(R.id.btnthanhtoan);
        txtTongTien = findViewById(R.id.tongtien);
        TextView textTendn = findViewById(R.id.tendn);

        ImageView ql = findViewById(R.id.back);
        ql.setOnClickListener(v -> finish());

        SharedPreferences sharedPre = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String tendn = sharedPre.getString("tendn", null);

        if (tendn != null) {
            textTendn.setText(tendn);
        } else {
            Intent intent = new Intent(GioHang_Activity.this, Login_Activity.class);
            startActivity(intent);
            finish();
            return;
        }

        gioHangManager = GioHangManager.getInstance();
        List<GioHang> gioHangList = gioHangManager.getGioHangList();
        adapter = new GioHangAdapter(this, gioHangList, txtTongTien);
        listView.setAdapter(adapter);

        txtTongTien.setText(String.valueOf(gioHangManager.getTongTien()));

        thanhtoan.setOnClickListener(v -> showPaymentDialog());

        setupNavigation();
    }

    private void showPaymentDialog() {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.activity_thong_tin_thanh_toan);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setGravity(Gravity.CENTER);

        EditText edtTenKh = dialog.findViewById(R.id.tenkh);
        EditText edtDiaChi = dialog.findViewById(R.id.diachi);
        EditText edtSdt = dialog.findViewById(R.id.sdt);
        Button btnLuu = dialog.findViewById(R.id.btnxacnhandathang);
        TextView tvTongTien = dialog.findViewById(R.id.tienthanhtoan);

        String tongTien = txtTongTien.getText().toString();
        tvTongTien.setText(tongTien);

        btnLuu.setOnClickListener(v -> {
            String tenKh = edtTenKh.getText().toString().trim();
            String diaChi = edtDiaChi.getText().toString().trim();
            String sdt = edtSdt.getText().toString().trim();

            if (tenKh.isEmpty() || diaChi.isEmpty() || sdt.isEmpty()) {
                Toast.makeText(this, "Vui lòng điền đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
            } else {
                float tongThanhToan;
                try {
                    tongThanhToan = Float.parseFloat(tongTien.replace(",", ""));
                } catch (NumberFormatException e) {
                    Toast.makeText(this, "Có lỗi xảy ra với tổng tiền!", Toast.LENGTH_SHORT).show();
                    return;
                }

                createOrder(tenKh, diaChi, sdt, tongThanhToan, dialog);
            }
        });

        dialog.show();
    }

    private void createOrder(String tenKh, String diaChi, String sdt, float tongThanhToan, Dialog dialog) {
        String orderId = UUID.randomUUID().toString();
        DonHang donHang = new DonHang(orderId, tenKh, diaChi, sdt, tongThanhToan, null, 0);

        apiManager.createDonHang(donHang, new Callback<DonHang>() {
            @Override
            public void onResponse(Call<DonHang> call, Response<DonHang> response) {
                if (response.isSuccessful() && response.body() != null) {
                    String createdOrderId = response.body().getId_dathang();
                    createOrderDetails(createdOrderId, dialog);
                } else {
                    Toast.makeText(GioHang_Activity.this, "Đặt hàng thất bại!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DonHang> call, Throwable t) {
                Toast.makeText(GioHang_Activity.this, "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void createOrderDetails(String orderId, Dialog dialog) {
        List<GioHang> gioHangList = gioHangManager.getGioHangList();
        for (GioHang item : gioHangList) {
            String chiTietId = UUID.randomUUID().toString();
            ChiTietDonHang chiTiet = new ChiTietDonHang(
                    chiTietId,
                    orderId,
                    item.getSanPham().getMasp(),
                    item.getSoLuong(),
                    item.getSanPham().getDongia(),
                    item.getSanPham().getAnh(),
                    0
            );

            apiManager.createChiTiet(chiTiet, new Callback<ChiTietDonHang>() {
                @Override
                public void onResponse(Call<ChiTietDonHang> call, Response<ChiTietDonHang> response) {
                    // Handle individual detail creation response if needed
                }

                @Override
                public void onFailure(Call<ChiTietDonHang> call, Throwable t) {
                    // Handle failure
                }
            });
        }

        Toast.makeText(this, "Đặt hàng thành công!", Toast.LENGTH_SHORT).show();
        gioHangManager.clearGioHang();
        txtTongTien.setText("0");
        adapter.notifyDataSetChanged();
        dialog.dismiss();

        Intent intent = new Intent(GioHang_Activity.this, TrangchuNgdung_Activity.class);
        startActivity(intent);
    }

    private void setupNavigation() {
        ImageButton btntimkiem = findViewById(R.id.btntimkiem);
        ImageButton btntrangchu = findViewById(R.id.btntrangchu);
        ImageButton btncard = findViewById(R.id.btncart);
        ImageButton btndonhang = findViewById(R.id.btndonhang);
        ImageButton btncanhan = findViewById(R.id.btncanhan);

        btntimkiem.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), TimKiemSanPham_Activity.class)));
        btntrangchu.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), TrangchuNgdung_Activity.class)));
        btncard.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), GioHang_Activity.class)));

        btndonhang.setOnClickListener(v -> {
            SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
            boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);
            if (isLoggedIn) {
                String tendn = sharedPreferences.getString("tendn", null);
                Intent intent = new Intent(getApplicationContext(), DonHang_User_Activity.class);
                intent.putExtra("tendn", tendn);
                startActivity(intent);
            } else {
                startActivity(new Intent(getApplicationContext(), Login_Activity.class));
            }
        });

        btncanhan.setOnClickListener(v -> {
            SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
            boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);
            if (isLoggedIn) {
                startActivity(new Intent(getApplicationContext(), TrangCaNhan_nguoidung_Activity.class));
            } else {
                startActivity(new Intent(getApplicationContext(), Login_Activity.class));
            }
        });
    }
}