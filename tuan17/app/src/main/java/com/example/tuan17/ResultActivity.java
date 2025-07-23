package com.example.tuan17;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

public class ResultActivity extends AppCompatActivity {
    
    private static final String TAG = "ResultActivity";
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        
        // Xử lý intent data từ VNPay
        handlePaymentResult();
    }
    
    private void handlePaymentResult() {
        Intent intent = getIntent();
        Uri data = intent.getData();
        
        if (data != null) {
            String scheme = data.getScheme();
            String host = data.getHost();
            String path = data.getPath();
            
            Log.d(TAG, "Payment result - Scheme: " + scheme + ", Host: " + host + ", Path: " + path);
            
            // Kiểm tra kết quả thanh toán dựa trên URL
            if ("http".equals(scheme) && "success.sdk.merchantbackapp".equals(host)) {
                // Thanh toán thành công
                handlePaymentSuccess(data);
            } else if ("http".equals(scheme) && "fail.sdk.merchantbackapp".equals(host)) {
                // Thanh toán thất bại
                handlePaymentFailure(data);
            } else if ("http".equals(scheme) && "cancel.sdk.merchantbackapp".equals(host)) {
                // Thanh toán bị hủy
                handlePaymentCancel(data);
            } else {
                // Kết quả không xác định
                handleUnknownResult(data);
            }
        } else {
            Log.e(TAG, "No data received from payment");
            Toast.makeText(this, "Không nhận được kết quả thanh toán", Toast.LENGTH_SHORT).show();
            finish();
        }
    }
    
    private void handlePaymentSuccess(Uri data) {
        Log.d(TAG, "Payment Success");
        Toast.makeText(this, "Thanh toán thành công!", Toast.LENGTH_LONG).show();
        
        // TODO: Cập nhật trạng thái đơn hàng trong database
        // TODO: Gửi thông báo cho ThanhToan_Activity
        
        // Chuyển về màn hình đơn hàng
        Intent intent = new Intent(this, DonHang_User_Activity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
    
    private void handlePaymentFailure(Uri data) {
        Log.d(TAG, "Payment Failed");
        Toast.makeText(this, "Thanh toán thất bại!", Toast.LENGTH_LONG).show();
        
        // TODO: Cập nhật trạng thái đơn hàng trong database
        // TODO: Gửi thông báo cho ThanhToan_Activity
        
        // Quay lại màn hình thanh toán
        Intent intent = new Intent(this, ThanhToan_Activity.class);
        startActivity(intent);
        finish();
    }
    
    private void handlePaymentCancel(Uri data) {
        Log.d(TAG, "Payment Cancelled");
        Toast.makeText(this, "Thanh toán đã bị hủy!", Toast.LENGTH_LONG).show();
        
        // TODO: Cập nhật trạng thái đơn hàng trong database
        // TODO: Gửi thông báo cho ThanhToan_Activity
        
        // Quay lại màn hình thanh toán
        Intent intent = new Intent(this, ThanhToan_Activity.class);
        startActivity(intent);
        finish();
    }
    
    private void handleUnknownResult(Uri data) {
        Log.w(TAG, "Unknown payment result: " + data.toString());
        Toast.makeText(this, "Kết quả thanh toán không xác định", Toast.LENGTH_SHORT).show();
        
        // Quay lại màn hình thanh toán
        Intent intent = new Intent(this, ThanhToan_Activity.class);
        startActivity(intent);
        finish();
    }
} 