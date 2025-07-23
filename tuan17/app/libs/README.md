# Hướng dẫn thêm file VNPay SDK

## Bước 1: Tải file merchant-1.0.25.aar
- Tải file `merchant-1.0.25.aar` từ VNPay
- Copy file vào thư mục này: `app/libs/merchant-1.0.25.aar`

## Bước 2: Sync project
- Mở Android Studio
- Nhấn "Sync Now" khi có thông báo
- Hoặc vào File > Sync Project with Gradle Files

## Bước 3: Test thanh toán
- Chạy app
- Thêm sản phẩm vào giỏ hàng
- Bấm thanh toán
- Chọn "Thanh toán VNPay"
- Test với tài khoản demo của VNPay

## Lưu ý:
- File AAR này chỉ dùng để demo
- Để thanh toán thật, cần đăng ký tài khoản merchant với VNPay
- Thay đổi TMN_CODE và HASH_SECRET trong VNPayHelper.java 