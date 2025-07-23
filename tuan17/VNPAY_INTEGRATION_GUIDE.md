# Hướng dẫn tích hợp VNPay vào dự án Android

## Tổng quan
Dự án này đã được tích hợp VNPay để xử lý thanh toán trực tuyến. Hiện tại đang sử dụng môi trường demo/sandbox.

## Cấu trúc đã thêm

### 1. Files đã tạo:
- `VNPayHelper.java` - Class xử lý thanh toán VNPay
- `ThanhToan_Activity.java` - Màn hình thanh toán
- `ResultActivity.java` - Xử lý kết quả thanh toán
- `DemoVNPayActivity.java` - Màn hình demo VNPay
- `activity_thanh_toan.xml` - Layout màn hình thanh toán
- `activity_result.xml` - Layout xử lý kết quả
- `activity_demo_vnpay.xml` - Layout demo
- `button_vnpay.xml` - Style nút VNPay
- `button_cash.xml` - Style nút tiền mặt

### 2. Cấu hình đã thêm:
- **build.gradle (Project)**: Thêm flatDir repository
- **build.gradle (App)**: Thêm dependencies VNPay
- **AndroidManifest.xml**: Thêm activities và permissions

## Cách sử dụng

### Demo VNPay:
1. Mở app
2. Ở màn hình đăng nhập, bấm "Demo VNPay Integration"
3. Bấm "Test Thanh Toán VNPay"
4. Trình duyệt sẽ mở trang thanh toán VNPay demo

### Thanh toán thật:
1. Thêm sản phẩm vào giỏ hàng
2. Bấm "Thanh toán"
3. Chọn "Thanh toán VNPay"
4. Hoàn tất thanh toán trên trang VNPay

## Cấu hình cho production

### 1. Đăng ký tài khoản VNPay:
- Liên hệ VNPay để đăng ký merchant account
- Lấy TMN Code và Hash Secret

### 2. Cập nhật VNPayHelper.java:
```java
private static final String VNPAY_TMN_CODE = "YOUR_TMN_CODE"; // Thay bằng TMN code thật
private static final String VNPAY_HASH_SECRET = "YOUR_HASH_SECRET"; // Thay bằng hash secret thật
private static final String VNPAY_URL = "https://vnpayment.vn/paymentv2/vpcpay.html"; // URL production
```

### 3. Thêm file AAR:
- Tải file `merchant-1.0.25.aar` từ VNPay
- Copy vào thư mục `app/libs/`

### 4. Cập nhật scheme:
- Thay đổi `your_scheme_app` trong AndroidManifest.xml thành scheme thật của bạn

## Luồng thanh toán

1. **Khởi tạo thanh toán**: `VNPayHelper.startPayment()`
2. **Mở trang VNPay**: Trình duyệt mở trang thanh toán
3. **Người dùng thanh toán**: Trên trang VNPay
4. **Kết quả**: VNPay redirect về `ResultActivity`
5. **Xử lý kết quả**: `ResultActivity` xử lý và chuyển màn hình

## Các action từ VNPay SDK

- `SuccessBackAction`: Thanh toán thành công
- `FaildBackAction`: Thanh toán thất bại  
- `WebBackAction`: Hủy thanh toán
- `AppBackAction`: Người dùng bấm back
- `CallMobileBankingApp`: Mở app mobile banking

## Lưu ý quan trọng

1. **Demo mode**: Hiện tại đang dùng demo, không thể thanh toán thật
2. **Security**: Không commit TMN Code và Hash Secret lên git
3. **Testing**: Test kỹ trước khi deploy production
4. **Error handling**: Xử lý các trường hợp lỗi network, timeout

## Troubleshooting

### Lỗi build:
- Kiểm tra file AAR đã có trong `app/libs/`
- Sync project với Gradle
- Clean và rebuild project

### Lỗi thanh toán:
- Kiểm tra internet connection
- Kiểm tra TMN Code và Hash Secret
- Kiểm tra URL return trong VNPayHelper

### Lỗi redirect:
- Kiểm tra scheme trong AndroidManifest.xml
- Kiểm tra intent-filter của ResultActivity 