<<<<<<< HEAD
# App Bán Quần Áo - Android + Node.js Backend

Dự án ứng dụng bán quần áo với Android frontend và Node.js backend sử dụng MongoDB.

## Cấu trúc dự án

```
appbanquanao/
├── app/                    # Android app
├── backend/               # Node.js backend
└── README.md
```

## Yêu cầu hệ thống

### Backend
- Node.js (v14 hoặc cao hơn)
- MongoDB (v4.4 hoặc cao hơn)
- npm hoặc yarn

### Android App
- Android Studio
- Android SDK (API level 24 trở lên)
- Java 17

## Cài đặt và chạy

### 1. Backend Setup

```bash
# Di chuyển vào thư mục backend
cd backend

# Cài đặt dependencies
npm install

# Khởi động MongoDB (nếu chưa chạy)
# Windows:
net start MongoDB
# macOS/Linux:
sudo systemctl start mongod

# Chạy server
npm run dev
```

Server sẽ chạy tại: `http://localhost:3000`

### 2. Android App Setup

1. Mở Android Studio
2. Import project từ thư mục `app/`
3. Sync Gradle files
4. Chạy app trên emulator hoặc device

## API Endpoints

### Authentication
- `POST /api/auth/login` - Đăng nhập
- `POST /api/auth/register` - Đăng ký

### Nhóm Sản Phẩm
- `GET /api/nhom-san-pham` - Lấy tất cả nhóm sản phẩm
- `POST /api/nhom-san-pham` - Tạo nhóm sản phẩm mới
- `PUT /api/nhom-san-pham/:maso` - Cập nhật nhóm sản phẩm
- `DELETE /api/nhom-san-pham/:maso` - Xóa nhóm sản phẩm

### Sản Phẩm
- `GET /api/san-pham` - Lấy tất cả sản phẩm
- `GET /api/san-pham/nhom/:maso` - Lấy sản phẩm theo nhóm
- `GET /api/san-pham/search/:keyword` - Tìm kiếm sản phẩm
- `POST /api/san-pham` - Tạo sản phẩm mới
- `PUT /api/san-pham/:masp` - Cập nhật sản phẩm
- `DELETE /api/san-pham/:masp` - Xóa sản phẩm

### Đơn Hàng
- `GET /api/don-hang` - Lấy tất cả đơn hàng
- `GET /api/don-hang/customer/:tenkh` - Lấy đơn hàng theo khách hàng
- `POST /api/don-hang` - Tạo đơn hàng mới
- `PATCH /api/don-hang/:id/status` - Cập nhật trạng thái đơn hàng

### Chi Tiết Đơn Hàng
- `GET /api/chi-tiet-don-hang/order/:orderId` - Lấy chi tiết theo đơn hàng
- `POST /api/chi-tiet-don-hang` - Tạo chi tiết đơn hàng
- `PATCH /api/chi-tiet-don-hang/:id/status` - Cập nhật trạng thái

### Tài Khoản
- `GET /api/tai-khoan` - Lấy tất cả tài khoản
- `POST /api/tai-khoan` - Tạo tài khoản mới
- `DELETE /api/tai-khoan/:tendn` - Xóa tài khoản

## Thay đổi chính

### Đã loại bỏ SQLite
- Xóa tất cả file `Database.java` và `DatabaseHelper.java`
- Loại bỏ tất cả import và sử dụng SQLite
- Thay thế bằng Retrofit API calls

### Thêm MongoDB Backend
- Node.js server với Express
- MongoDB với Mongoose ODM
- RESTful API endpoints
- JWT authentication (cơ bản)

### Cập nhật Android App
- Thêm Retrofit dependencies
- Tạo API client và service interfaces
- Cập nhật tất cả Activities và Adapters
- Thay thế synchronous SQLite calls bằng asynchronous API calls

## Cấu hình

### Backend Configuration
Chỉnh sửa file `backend/.env`:
```env
MONGODB_URI=mongodb://localhost:27017/appbanquanao
PORT=3000
JWT_SECRET=your_secret_key
```

### Android Configuration
Trong `ApiClient.java`, đảm bảo BASE_URL đúng:
```java
private static final String BASE_URL = "http://10.0.2.2:3000/api/"; // cho emulator
// hoặc
private static final String BASE_URL = "http://192.168.1.x:3000/api/"; // cho device thật
```

## Troubleshooting

### Backend không kết nối được MongoDB
- Kiểm tra MongoDB đã chạy: `mongosh`
- Kiểm tra connection string trong `.env`

### Android app không kết nối được backend
- Kiểm tra BASE_URL trong `ApiClient.java`
- Đảm bảo backend đang chạy
- Kiểm tra network permissions trong `AndroidManifest.xml`

### Build errors
- Clean và rebuild project
- Sync Gradle files
- Kiểm tra dependencies trong `build.gradle`

## Tài khoản mặc định

Khi chạy lần đầu, có thể tạo tài khoản admin:
- Username: `admin`
- Password: `1234`
- Role: `admin`

## Ghi chú

- Tất cả ảnh được lưu dưới dạng Buffer trong MongoDB
- API hỗ trợ upload ảnh base64
- Sử dụng UUID cho các ID thay vì auto-increment
- Retrofit xử lý async calls, cần callback handling
=======
# appbanquanao
<<<<<<< HEAD
Code rác vãi l đừng ai để ý =)) chưa code xong đâu
>>>>>>> 341150cd1f916ea9321e0b6be3e58519b233bbc3
=======
>>>>>>> 41ea773e5a65593dff73e26b2c3adfd2fafd75ed
