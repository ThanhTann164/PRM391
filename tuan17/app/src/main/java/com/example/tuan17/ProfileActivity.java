package com.example.tuan17;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        TextView tvUsername = findViewById(R.id.tv_profile_username);
        TextView tvDiaChi = findViewById(R.id.tv_profile_diachi);
        TextView tvGioiTinh = findViewById(R.id.tv_profile_gioitinh);
        TextView tvSDT = findViewById(R.id.tv_profile_sdt);

        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String tendn = sharedPreferences.getString("tendn", null);
        if (tendn != null) {
            Database database = new Database(this, "banhang.db", null, 1);
            Cursor cursor = database.GetData("SELECT diachi, gioitinh, sdt FROM taikhoan WHERE tendn = '" + tendn + "'");
            tvUsername.setText(tendn);
            if (cursor.moveToFirst()) {
                String diachi = cursor.getString(0);
                String gioitinh = cursor.getString(1);
                String sdt = cursor.getString(2);
                tvDiaChi.setText(diachi);
                tvGioiTinh.setText(gioitinh);
                tvSDT.setText(sdt);
            }
            cursor.close();
        }
    }
} 