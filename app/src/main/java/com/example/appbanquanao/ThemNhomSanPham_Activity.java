package com.example.appbanquanao;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.appbanquanao.managers.ApiManager;
import com.example.appbanquanao.models.NhomSanPham;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ThemNhomSanPham_Activity extends AppCompatActivity {
    private EditText tennsp;
    private ApiManager apiManager;
    private ImageView imgnsp;
    private Uri imageUri;
    private Button btnthem;
    private boolean isLoading = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_nhom_san_pham);

        apiManager = new ApiManager();
        tennsp = findViewById(R.id.ten);
        imgnsp = findViewById(R.id.imgnsp);
        Button chonimgbs = findViewById(R.id.btnAddImg);
        btnthem = findViewById(R.id.btnadd);
        ImageView imgback = findViewById(R.id.imgback);

        // Back button functionality
        imgback.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), Nhomsanpham_admin_Actvity.class);
            startActivity(intent);
            finish();
        });

        chonimgbs.setOnClickListener(view -> openDrawableImagePicker());

        btnthem.setOnClickListener(view -> {
            if (isLoading) {
                return; // Prevent multiple submissions
            }

            String tenNsp = tennsp.getText().toString().trim();

            if (tenNsp.isEmpty()) {
                Toast.makeText(ThemNhomSanPham_Activity.this, "Vui lòng nhập tên nhóm sản phẩm!", Toast.LENGTH_SHORT).show();
                tennsp.requestFocus();
                return;
            }

            if (tenNsp.length() < 2) {
                Toast.makeText(ThemNhomSanPham_Activity.this, "Tên nhóm sản phẩm phải có ít nhất 2 ký tự!", Toast.LENGTH_SHORT).show();
                tennsp.requestFocus();
                return;
            }

            if (imageUri == null) {
                Toast.makeText(ThemNhomSanPham_Activity.this, "Vui lòng chọn ảnh cho nhóm sản phẩm!", Toast.LENGTH_SHORT).show();
                return;
            }

            byte[] imageBytes = getBytesFromUri(imageUri);
            if (imageBytes == null) {
                Toast.makeText(ThemNhomSanPham_Activity.this, "Lỗi khi lấy ảnh!", Toast.LENGTH_SHORT).show();
                return;
            }

            setLoading(true);
            createNhomSanPham(tenNsp, imageBytes);
        });
    }

    private void setLoading(boolean loading) {
        isLoading = loading;
        btnthem.setEnabled(!loading);
        btnthem.setText(loading ? "Đang thêm..." : "Thêm");
    }

    private void createNhomSanPham(String tenNsp, byte[] imageBytes) {
        String maso = UUID.randomUUID().toString();
        NhomSanPham nhomSanPham = new NhomSanPham(maso, tenNsp, imageBytes);

        apiManager.createNhomSanPham(nhomSanPham, new Callback<NhomSanPham>() {
            @Override
            public void onResponse(Call<NhomSanPham> call, Response<NhomSanPham> response) {
                setLoading(false);
                if (response.isSuccessful()) {
                    Toast.makeText(ThemNhomSanPham_Activity.this, "Thêm nhóm sản phẩm thành công!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), Nhomsanpham_admin_Actvity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(ThemNhomSanPham_Activity.this, "Thêm nhóm sản phẩm thất bại!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<NhomSanPham> call, Throwable t) {
                setLoading(false);
                Toast.makeText(ThemNhomSanPham_Activity.this, "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void openDrawableImagePicker() {
        final String[] imageNames = {"vest", "aococtay", "aolen", "dahoi", "giaydong", "giaythethao", "khoac1", "quanau", "quantat", "vay", "somi"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Chọn ảnh từ drawable");
        builder.setItems(imageNames, (dialog, which) -> {
            String selectedImageName = imageNames[which];
            int resourceId = getResources().getIdentifier(selectedImageName, "drawable", getPackageName());
            imgnsp.setImageResource(resourceId);
            imageUri = Uri.parse("android.resource://" + getPackageName() + "/" + resourceId);
        });
        builder.show();
    }

    private byte[] getBytesFromUri(Uri uri) {
        try {
            InputStream inputStream = getContentResolver().openInputStream(uri);
            ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
            int bufferSize = 1024;
            byte[] buffer = new byte[bufferSize];
            int len;
            while ((len = inputStream.read(buffer)) != -1) {
                byteBuffer.write(buffer, 0, len);
            }
            return byteBuffer.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}