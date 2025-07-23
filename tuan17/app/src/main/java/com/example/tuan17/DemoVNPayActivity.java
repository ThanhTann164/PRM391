package com.example.tuan17;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class DemoVNPayActivity extends AppCompatActivity {
    
    private TextView tvDemoInfo;
    private Button btnTestPayment;
    private VNPayHelper vnPayHelper;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_vnpay);
        
        tvDemoInfo = findViewById(R.id.tvDemoInfo);
        btnTestPayment = findViewById(R.id.btnTestPayment);
        
        vnPayHelper = new VNPayHelper(this);
        
        // Hiển thị thông tin demo
        tvDemoInfo.setText("Demo VNPay Integration\n\n" +
                "• TMN Code: DEMO\n" +
                "• Hash Secret: DEMO\n" +
                "• URL: Sandbox\n" +
                "• Amount: 100,000 VNĐ\n\n" +
                "Bấm nút bên dưới để test thanh toán");
        
        btnTestPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Test thanh toán với số tiền 100,000 VNĐ
                String orderId = "DEMO_" + System.currentTimeMillis();
                String amount = "10000000"; // 100,000 VNĐ * 100
                String orderInfo = "Test thanh toan VNPay";
                
                Toast.makeText(DemoVNPayActivity.this, 
                    "Đang mở trang thanh toán VNPay demo...", Toast.LENGTH_LONG).show();
                
                vnPayHelper.startPayment(orderId, amount, orderInfo);
            }
        });
    }
} 