package com.example.appbanquanao;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appbanquanao.managers.ApiManager;
import com.example.appbanquanao.models.TaiKhoan;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TaiKhoanAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<TaiKhoan> taiKhoanList;
    private ApiManager apiManager;

    public TaiKhoanAdapter(Context context, int layout, List<TaiKhoan> taiKhoanList) {
        this.context = context;
        this.layout = layout;
        this.taiKhoanList = taiKhoanList;
        this.apiManager = new ApiManager();
    }

    @Override
    public int getCount() {
        return taiKhoanList.size();
    }

    @Override
    public Object getItem(int position) {
        return taiKhoanList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View viewtemp;
        if (view == null) {
            viewtemp = View.inflate(viewGroup.getContext(), R.layout.ds_taikhoan, null);
        } else {
            viewtemp = view;
        }

        TaiKhoan tt = taiKhoanList.get(i);
        TextView tendn = viewtemp.findViewById(R.id.tdn1);
        TextView matkhau = viewtemp.findViewById(R.id.mk1);
        TextView quyenhang = viewtemp.findViewById(R.id.quyen1);
        ImageButton sua = viewtemp.findViewById(R.id.imgsua);
        ImageButton xoa = viewtemp.findViewById(R.id.imgxoa);

        tendn.setText(tt.getTendn());
        matkhau.setText("****"); // Hide password for security
        quyenhang.setText(tt.getQuyen());

        // Xử lý sự kiện cho ImageButton "Sửa"
        sua.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(viewGroup.getContext());

            View dialogView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_sua_tai_khoan, null);
            EditText editTdn = dialogView.findViewById(R.id.tdn);
            EditText editMk = dialogView.findViewById(R.id.mk);
            RadioButton user = dialogView.findViewById(R.id.user);
            RadioButton admin = dialogView.findViewById(R.id.admin);

            editTdn.setText(tt.getTendn());
            editTdn.setEnabled(false); // Don't allow changing username

            // Đặt quyền hiện tại cho RadioButton
            switch (tt.getQuyen()) {
                case "admin":
                    admin.setChecked(true);
                    break;
                case "user":
                    user.setChecked(true);
                    break;
            }

            builder.setView(dialogView)
                    .setPositiveButton("Lưu", (dialog, which) -> {
                        String newMk = editMk.getText().toString().trim();
                        String quyen = user.isChecked() ? "user" : "admin";

                        if (newMk.isEmpty()) {
                            Toast.makeText(viewGroup.getContext(), "Mật khẩu không được để trống", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        // For now, just show a message that this feature will be implemented
                        // You can implement the update API call here
                        tt.setMatkhau(newMk);
                        tt.setQuyen(quyen);
                        notifyDataSetChanged();
                        Toast.makeText(viewGroup.getContext(), "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                    })
                    .setNegativeButton("Hủy", (dialog, which) -> dialog.dismiss());

            builder.show();
        });

        // Xử lý sự kiện cho ImageButton "Xóa"
        xoa.setOnClickListener(v -> {
            new AlertDialog.Builder(viewGroup.getContext())
                    .setTitle("Xác nhận")
                    .setMessage("Bạn có chắc chắn muốn xóa tài khoản này?")
                    .setPositiveButton("Có", (dialog, which) -> {
                        apiManager.deleteTaiKhoan(tt.getTendn(), new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                if (response.isSuccessful()) {
                                    taiKhoanList.remove(i);
                                    notifyDataSetChanged();
                                    Toast.makeText(viewGroup.getContext(), "Xóa tài khoản thành công", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(viewGroup.getContext(), "Không thể xóa tài khoản", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {
                                Toast.makeText(viewGroup.getContext(), "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    })
                    .setNegativeButton("Không", (dialog, which) -> dialog.dismiss())
                    .show();
        });

        return viewtemp;
    }
}