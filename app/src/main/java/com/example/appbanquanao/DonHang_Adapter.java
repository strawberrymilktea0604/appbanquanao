package com.example.appbanquanao;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.appbanquanao.models.DonHang;

import java.util.List;

public class DonHang_Adapter extends ArrayAdapter<DonHang> {
    public DonHang_Adapter(Context context, List<DonHang> orders) {
        super(context, 0, orders);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.ds_donhang, parent, false);
        }

        DonHang order = getItem(position);

        TextView txtMadh = convertView.findViewById(R.id.txtMahd);
        TextView txtTenKh = convertView.findViewById(R.id.txtTenKh);
        TextView txtDiaChi = convertView.findViewById(R.id.txtDiaChi);
        TextView txtSdt = convertView.findViewById(R.id.txtSdt);
        TextView txtTongThanhToan = convertView.findViewById(R.id.txtTongThanhToan);
        TextView txtNgayDatHang = convertView.findViewById(R.id.txtNgayDatHang);

        if (order != null) {
            txtMadh.setText(order.getId_dathang());
            txtTenKh.setText(order.getTenkh());
            txtDiaChi.setText(order.getDiachi());
            txtSdt.setText(order.getSdt());
            txtTongThanhToan.setText(String.valueOf(order.getTongthanhtoan()));
            txtNgayDatHang.setText(order.getNgaydathang());
        }

        return convertView;
    }
}