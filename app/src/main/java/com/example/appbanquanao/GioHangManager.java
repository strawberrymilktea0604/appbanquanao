package com.example.appbanquanao;

import com.example.appbanquanao.models.ChiTietSanPham;
import com.example.appbanquanao.models.GioHang;

import java.util.ArrayList;
import java.util.List;

public class GioHangManager {
    private static GioHangManager instance;
    private List<GioHang> items;

    private GioHangManager() {
        items = new ArrayList<>();
    }

    public static synchronized GioHangManager getInstance() {
        if (instance == null) {
            instance = new GioHangManager();
        }
        return instance;
    }

    public List<GioHang> getGioHangList() {
        return items;
    }

    public void addItem(ChiTietSanPham sanPham) {
        for (GioHang item : items) {
            if (item.getSanPham().getMasp().equals(sanPham.getMasp())) {
                item.setSoLuong(item.getSoLuong() + 1);
                return;
            }
        }
        items.add(new GioHang(sanPham, 1));
    }

    public void removeItem(int position) {
        if (position >= 0 && position < items.size()) {
            items.remove(position);
        }
    }

    public float getTongTien() {
        float tong = 0;
        for (GioHang item : items) {
            tong += item.getTongGia();
        }
        return tong;
    }

    public void clearGioHang() {
        items.clear();
    }
}