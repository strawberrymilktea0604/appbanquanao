package com.example.appbanquanao;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.appbanquanao.models.TaiKhoan;

import java.util.ArrayList;
import java.util.List;

public class SuaTaiKhoan_Activity extends AppCompatActivity {
    Database database;

    List<TaiKhoan> mangTK;
    TaiKhoanAdapter adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sua_tai_khoan);


        mangTK = new ArrayList<>();
        adapter = new TaiKhoanAdapter(getApplicationContext(), R.layout.ds_taikhoan, mangTK);
//        lv.setAdapter(adapter);
        database = new Database(this, "banhang.db", null, 1);
        database.QueryData("CREATE TABLE IF NOT EXISTS taikhoan(tendn VARCHAR(20) PRIMARY KEY, matkhau VARCHAR(50), quyen VARCHAR(50))");

    }
}