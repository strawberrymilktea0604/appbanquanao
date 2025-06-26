package com.example.appbanquanao;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appbanquanao.managers.ApiManager;
import com.example.appbanquanao.models.ChiTietDonHang;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChiTietDonHangAdapter extends ArrayAdapter<ChiTietDonHang> {

    public static final int DA_DAT_HANG = 0;
    public static final int DA_THANH_TOAN = 1;
    public static final int DANG_VAN_CHUYEN = 2;
    public static final int THANH_CONG = 3;

    private ApiManager apiManager;
    private boolean isAdmin = false;

    public ChiTietDonHangAdapter(Context context, List<ChiTietDonHang> details) {
        super(context, 0, details);
        apiManager = new ApiManager();
    }

    public void setAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ChiTietDonHang detail = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.ds_chitietdonhang, parent, false);
        }

        TextView tvID_dathang = convertView.findViewById(R.id.txt_Iddathang);
        TextView tvMaSp = convertView.findViewById(R.id.txtMasp);
        TextView tvTenSp = convertView.findViewById(R.id.txtTensp);
        TextView tvSoLuong = convertView.findViewById(R.id.txtSoLuong);
        TextView tvDonGia = convertView.findViewById(R.id.txtGia);
        ImageView ivAnh = convertView.findViewById(R.id.imgsp);
        RadioButton rdDaDatHang = convertView.findViewById(R.id.rd_da_dat_hang);
        RadioButton rdDaThanhToan = convertView.findViewById(R.id.rd_da_thanh_toan);
        RadioButton rdDangVanChuyen = convertView.findViewById(R.id.rd_dang_van_chuyen);
        RadioButton rdThanhCong = convertView.findViewById(R.id.rd_thanh_cong);

        tvID_dathang.setText(detail.getId_dathang());
        tvMaSp.setText(detail.getMasp());
        // You might need to fetch product name based on masp from your API
        tvTenSp.setText("Tên sản phẩm"); // Placeholder
        tvSoLuong.setText(String.valueOf(detail.getSoluong()));
        tvDonGia.setText(String.valueOf(detail.getDongia()));

        resetRadioButtons(convertView);
        int trangThai = detail.getTrangthai();
        switch (trangThai) {
            case DA_DAT_HANG:
                rdDaDatHang.setChecked(true);
                break;
            case DA_THANH_TOAN:
                rdDaThanhToan.setChecked(true);
                break;
            case DANG_VAN_CHUYEN:
                rdDangVanChuyen.setChecked(true);
                break;
            case THANH_CONG:
                rdThanhCong.setChecked(true);
                break;
        }

        rdDaDatHang.setEnabled(isAdmin);
        rdDaThanhToan.setEnabled(isAdmin);
        rdDangVanChuyen.setEnabled(isAdmin);
        rdThanhCong.setEnabled(isAdmin);

        if (isAdmin) {
            rdDaDatHang.setOnClickListener(v -> updateChiTietDonHang(detail, DA_DAT_HANG));
            rdDaThanhToan.setOnClickListener(v -> updateChiTietDonHang(detail, DA_THANH_TOAN));
            rdDangVanChuyen.setOnClickListener(v -> updateChiTietDonHang(detail, DANG_VAN_CHUYEN));
            rdThanhCong.setOnClickListener(v -> updateChiTietDonHang(detail, THANH_CONG));
        }

        if (detail.getAnh() != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(detail.getAnh(), 0, detail.getAnh().length);
            ivAnh.setImageBitmap(bitmap);
        } else {
            ivAnh.setImageResource(R.drawable.vest);
        }

        return convertView;
    }

    private void updateChiTietDonHang(ChiTietDonHang chiTietDonHang, int newTrangThai) {
        apiManager.updateChiTietStatus(chiTietDonHang.getId_chitiet(), newTrangThai, new Callback<ChiTietDonHang>() {
            @Override
            public void onResponse(Call<ChiTietDonHang> call, Response<ChiTietDonHang> response) {
                if (response.isSuccessful()) {
                    chiTietDonHang.setTrangthai(newTrangThai);
                    notifyDataSetChanged();
                    Toast.makeText(getContext(), "Cập nhật trạng thái thành công", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Cập nhật trạng thái thất bại", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ChiTietDonHang> call, Throwable t) {
                Toast.makeText(getContext(), "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void resetRadioButtons(View convertView) {
        RadioButton rdDaDatHang = convertView.findViewById(R.id.rd_da_dat_hang);
        RadioButton rdDaThanhToan = convertView.findViewById(R.id.rd_da_thanh_toan);
        RadioButton rdDangVanChuyen = convertView.findViewById(R.id.rd_dang_van_chuyen);
        RadioButton rdThanhCong = convertView.findViewById(R.id.rd_thanh_cong);
        rdDaDatHang.setChecked(false);
        rdDaThanhToan.setChecked(false);
        rdDangVanChuyen.setChecked(false);
        rdThanhCong.setChecked(false);
    }
}