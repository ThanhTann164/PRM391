package com.example.tuan17;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

import java.util.List;
public class DonHang_Adapter extends ArrayAdapter<Order> {
    public DonHang_Adapter(Context context, List<Order> orders) {
        super(context, 0, orders);
    }

    @Override

        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.ds_donhang, parent, false);
            }

            Order order = getItem(position);
        TextView txtMadh = convertView.findViewById(R.id.txtMahd);
            TextView txtTenKh = convertView.findViewById(R.id.txtTenKh);
            TextView txtDiaChi = convertView.findViewById(R.id.txtDiaChi);
            TextView txtSdt = convertView.findViewById(R.id.txtSdt);
            TextView txtTongThanhToan = convertView.findViewById(R.id.txtTongThanhToan);
            TextView txtNgayDatHang = convertView.findViewById(R.id.txtNgayDatHang);
            ImageView imgsp = convertView.findViewById(R.id.imgsp_donhang);

        txtTenKh.setText(order.getTenKh());
            txtDiaChi.setText(order.getDiaChi());
            txtSdt.setText(order.getSdt());
            txtTongThanhToan.setText(String.valueOf(order.getTongTien()));
            txtNgayDatHang.setText(order.getNgayDatHang());
        txtMadh.setText(String.valueOf(order.getId()));

        // Hiển thị hình ảnh sản phẩm đầu tiên trong đơn hàng (nếu có)
        if (order.getChiTietList() != null && !order.getChiTietList().isEmpty()) {
            byte[] anh = order.getChiTietList().get(0).getAnh();
            if (anh != null && anh.length > 0) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(anh, 0, anh.length);
                imgsp.setImageBitmap(bitmap);
            } else {
                imgsp.setImageResource(R.drawable.vest); // Ảnh mặc định
            }
        } else {
            imgsp.setImageResource(R.drawable.vest); // Ảnh mặc định
        }
            return convertView;
        }
}
