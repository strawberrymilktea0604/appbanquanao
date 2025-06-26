package com.example.appbanquanao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.appbanquanao.models.ChiTietDonHang;
import com.example.appbanquanao.models.SanPham;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    public DatabaseHelper(Context context) {
        super(context, "banhang.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create tables if needed
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Handle database upgrades
    }

    // Temporary methods to avoid compilation errors
    // These should be replaced with API calls
    public List<ChiTietDonHang> getChiTietByOrderId(int orderId) {
        return new ArrayList<>();
    }

    public List<ChiTietDonHang> getAllChiTietDonHang() {
        return new ArrayList<>();
    }

    public List<SanPham> getProductsByNhomSpId(String nhomSpId) {
        return new ArrayList<>();
    }

    public ArrayList<SanPham> searchSanPhamByName(String query) {
        return new ArrayList<>();
    }
}