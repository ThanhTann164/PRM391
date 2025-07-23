package com.example.tuan17;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

public class VNPayHelper {
    
    private static final String TAG = "VNPayHelper";
    
    // Thông tin VNPay - Thay đổi khi có tài khoản thật
    private static final String VNPAY_TMN_CODE = "DEMO"; // TMN code demo
    private static final String VNPAY_HASH_SECRET = "DEMO"; // Hash secret demo
    private static final String VNPAY_URL = "https://sandbox.vnpayment.vn/paymentv2/vpcpay.html"; // URL sandbox
    private static final String VNPAY_SCHEME = "your_scheme_app"; // Scheme để quay lại app
    
    private Activity activity;
    
    public VNPayHelper(Activity activity) {
        this.activity = activity;
    }
    
    public void startPayment(String orderId, String amount, String orderInfo) {
        try {
            // Tạo URL thanh toán VNPay
            String paymentUrl = createPaymentUrl(orderId, amount, orderInfo);
            
            // Mở trình duyệt để thanh toán
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(android.net.Uri.parse(paymentUrl));
            activity.startActivity(intent);
            
            Log.d(TAG, "Payment URL: " + paymentUrl);
            
        } catch (Exception e) {
            Log.e(TAG, "Error starting payment: " + e.getMessage());
            Toast.makeText(activity, "Lỗi khởi tạo thanh toán", Toast.LENGTH_SHORT).show();
        }
    }
    
    private String createPaymentUrl(String orderId, String amount, String orderInfo) {
        // Tạo URL thanh toán demo
        // Trong thực tế, bạn cần tạo URL với đầy đủ tham số và chữ ký theo tài liệu VNPay
        
        StringBuilder url = new StringBuilder(VNPAY_URL);
        url.append("?vnp_TxnRef=").append(orderId);
        url.append("&vnp_OrderInfo=").append(orderInfo);
        url.append("&vnp_OrderType=billpayment");
        url.append("&vnp_Amount=").append(amount);
        url.append("&vnp_CurrCode=VND");
        url.append("&vnp_Locale=vn");
        url.append("&vnp_ReturnUrl=http://success.sdk.merchantbackapp");
        url.append("&vnp_TmnCode=").append(VNPAY_TMN_CODE);
        url.append("&vnp_IpAddr=127.0.0.1");
        
        return url.toString();
    }
    
    public void handleActivityResult(int requestCode, int resultCode, Intent data) {
        // Xử lý kết quả từ ResultActivity
        Log.d(TAG, "Activity result handled");
    }
} 