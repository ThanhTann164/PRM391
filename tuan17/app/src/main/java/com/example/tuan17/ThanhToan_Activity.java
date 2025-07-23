package com.example.tuan17;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ThanhToan_Activity extends AppCompatActivity {
    
    private TextView tvTongTien, tvMaDonHang;
    private Button btnThanhToanVNPay, btnThanhToanTienMat;
    private VNPayHelper vnPayHelper;
    
    private String orderId;
    private String totalAmount;
    private String orderInfo;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thanh_toan);
        
        // Khởi tạo VNPay Helper
        vnPayHelper = new VNPayHelper(this);
        
        // Lấy dữ liệu từ Intent
        Intent intent = getIntent();
        totalAmount = intent.getStringExtra("totalAmount");
        orderId = intent.getStringExtra("orderId");
        orderInfo = intent.getStringExtra("orderInfo");
        
        // Khởi tạo views
        initViews();
        
        // Hiển thị thông tin
        displayOrderInfo();
        
        // Xử lý sự kiện
        setupEventListeners();
    }
    
    private void initViews() {
        tvTongTien = findViewById(R.id.tvTongTien);
        tvMaDonHang = findViewById(R.id.tvMaDonHang);
        btnThanhToanVNPay = findViewById(R.id.btnThanhToanVNPay);
        btnThanhToanTienMat = findViewById(R.id.btnThanhToanTienMat);
    }
    
    private void displayOrderInfo() {
        if (totalAmount != null) {
            tvTongTien.setText("Tổng tiền: " + formatCurrency(totalAmount));
        }
        
        if (orderId != null) {
            tvMaDonHang.setText("Mã đơn hàng: " + orderId);
        }
    }
    
    private void setupEventListeners() {
        btnThanhToanVNPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startVNPayPayment();
            }
        });
        
        btnThanhToanTienMat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleCashPayment();
            }
        });
    }
    
    private void startVNPayPayment() {
        if (totalAmount == null || orderId == null) {
            Toast.makeText(this, "Thông tin đơn hàng không hợp lệ", Toast.LENGTH_SHORT).show();
            return;
        }
        
        // Chuyển đổi số tiền sang định dạng VNPay (nhân 100)
        String vnpayAmount = String.valueOf(Long.parseLong(totalAmount) * 100);
        
        // Hiển thị thông báo demo
        Toast.makeText(this, "Đang mở trang thanh toán VNPay demo...", Toast.LENGTH_LONG).show();
        
        // Bắt đầu thanh toán VNPay
        vnPayHelper.startPayment(orderId, vnpayAmount, orderInfo);
    }
    
    private void handleCashPayment() {
        // Xử lý thanh toán tiền mặt
        Toast.makeText(this, "Đã chọn thanh toán tiền mặt", Toast.LENGTH_SHORT).show();
        
        // Cập nhật trạng thái đơn hàng trong database
        updateOrderStatus("pending"); // Chờ thanh toán
        
        // Chuyển về màn hình đơn hàng
        Intent intent = new Intent(this, DonHang_User_Activity.class);
        startActivity(intent);
        finish();
    }
    
    private void updateOrderStatus(String status) {
        // Cập nhật trạng thái đơn hàng trong database
        // TODO: Implement database update
    }
    
    private String formatCurrency(String amount) {
        try {
            long amountLong = Long.parseLong(amount);
            return String.format("%,d VNĐ", amountLong);
        } catch (NumberFormatException e) {
            return amount + " VNĐ";
        }
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        
        // Xử lý kết quả từ VNPay
        vnPayHelper.handleActivityResult(requestCode, resultCode, data);
    }
} 