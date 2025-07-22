package com.example.tuan17;


import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class Database extends SQLiteOpenHelper {

    public Database(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }


    //truy vấn không trả kết quả
    public void QueryData(String sql){
        SQLiteDatabase database=getWritableDatabase();
        database.execSQL(sql);

    }
    public void QueryDulieu(String sql, byte[]... params) {
        SQLiteDatabase database = this.getWritableDatabase();
        SQLiteStatement statement = database.compileStatement(sql);

        for (int i = 0; i < params.length; i++) {
            statement.bindBlob(i + 1, params[i]); // Gán blob vào câu lệnh
        }

        statement.executeInsert(); // Hoặc executeUpdate/Delete tùy thuộc vào câu lệnh
        database.close();
    }
    //truy vấn có kết quả
    public Cursor GetData(String sql){
        SQLiteDatabase database=getReadableDatabase();
        return database.rawQuery(sql,null);
    }
    // Phương thức để thực hiện câu lệnh SQL với một tham số blob
    public void QueryData(String sql, byte[] param) {
        SQLiteDatabase database = this.getWritableDatabase();
        SQLiteStatement statement = database.compileStatement(sql);
        statement.bindBlob(1, param); // Gán blob vào câu lệnh
        statement.executeInsert();
        database.close();
    }

    //dùng trong trang thêm thông tin nhóm sp
    public void QueryData(String sql, Object... args) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(sql, args); // Sử dụng args để truyền tham số
        db.close();
    }
    // Thêm phương thức xóa nhóm sản phẩm
    public void deleteNhomSanPham(String maSo) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("nhomsanpham", "maso = ?", new String[]{maSo});
        db.close();
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS nhomsanpham ("
                + "maso INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "tennsp NVARCHAR(200), "
                + "anh BLOB)");
        // Thêm lệnh tạo bảng sanpham
        db.execSQL("CREATE TABLE IF NOT EXISTS sanpham(" +
                "masp INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "tensp NVARCHAR(200), " +
                "dongia FLOAT, " +
                "mota TEXT, " +
                "ghichu TEXT, " +
                "soluongkho INTEGER, " +
                "maso INTEGER, " +
                "anh BLOB)");
        // Thêm lệnh tạo bảng taikhoan
        db.execSQL("CREATE TABLE IF NOT EXISTS taikhoan (" +
                "tendn TEXT PRIMARY KEY, " +
                "matkhau TEXT, " +
                "quyen TEXT, " +
                "diachi TEXT, " +
                "gioitinh TEXT, " +
                "sdt TEXT)");
    }

    public List<Order> getDonHangByTenKh(String tenKh) {
        List<Order> orders = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM Dathang WHERE tenkh = ?";
        Cursor cursor = db.rawQuery(query, new String[]{tenKh});

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0); // id_dathang
                String diaChi = cursor.getString(2); // diachi
                String sdt = cursor.getString(3); // sdt
                float tongThanhToan = cursor.getFloat(4); // tongthanhtoan
                String ngayDatHang = cursor.getString(5); // ngaydathang

                Order order = new Order(id, tenKh, diaChi, sdt, tongThanhToan, ngayDatHang);
                // Nạp chi tiết sản phẩm cho đơn hàng
                order.setChiTietList(getChiTietByOrderId(id, db));
                orders.add(order);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return orders;
    }


    public List<Order> getAllDonHang() {
        List<Order> orders = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        try {
            String query = "SELECT * FROM Dathang";
            cursor = db.rawQuery(query, null); // Cần đối số thứ 2 là null

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    int id = cursor.getInt(0); // id_dathang
                    String tenKh = cursor.getString(1); // tenkh
                    String diaChi = cursor.getString(2); // diachi
                    String sdt = cursor.getString(3); // sdt
                    float tongThanhToan = cursor.getFloat(4); // tongthanhtoan
                    String ngayDatHang = cursor.getString(5); // ngaydathang

                    Order order = new Order(id, tenKh, diaChi, sdt, tongThanhToan, ngayDatHang);
                    // Nạp chi tiết sản phẩm cho đơn hàng
                    order.setChiTietList(getChiTietByOrderId(id, db));
                    orders.add(order);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace(); // In ra lỗi nếu có
        } finally {
            if (cursor != null) {
                cursor.close(); // Đóng cursor để tránh rò rỉ bộ nhớ
            }
            db.close(); // Đóng database sau khi hoàn thành
        }

        return orders;
    }

    // Hàm lấy chi tiết sản phẩm cho 1 đơn hàng (bao gồm ảnh)
    public List<ChiTietDonHang> getChiTietByOrderId(int orderId, SQLiteDatabase db) {
        List<ChiTietDonHang> chiTietList = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT id_chitiet, id_dathang, masp, soluong, dongia, anh FROM Chitietdonhang WHERE id_dathang = ?", new String[]{String.valueOf(orderId)});
        if (cursor.moveToFirst()) {
            do {
                int id_chitiet = cursor.getInt(0);
                int id_dathang = cursor.getInt(1);
                int masp = cursor.getInt(2);
                int soluong = cursor.getInt(3);
                float dongia = cursor.getFloat(4);
                byte[] anh = cursor.getBlob(5);
                chiTietList.add(new ChiTietDonHang(id_chitiet, id_dathang, masp, soluong, dongia, anh));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return chiTietList;
    }
}
