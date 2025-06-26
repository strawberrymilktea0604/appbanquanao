# App Ban Quan Ao - Backend API

Backend API cho ứng dụng bán quần áo sử dụng Node.js, Express và MongoDB.

## Cài đặt

### Yêu cầu hệ thống
- Node.js (v14 hoặc cao hơn)
- MongoDB (v4.4 hoặc cao hơn)
- npm hoặc yarn

### Cài đặt dependencies
```bash
cd backend
npm install
```

### Cấu hình môi trường
1. Sao chép file `.env` và cập nhật các thông số:
```bash
cp .env.example .env
```

2. Cập nhật file `.env`:
```env
MONGODB_URI=mongodb://localhost:27017/appbanquanao
PORT=3000
NODE_ENV=development
JWT_SECRET=your_jwt_secret_key_here
JWT_EXPIRES_IN=7d
BCRYPT_SALT_ROUNDS=10
```

### Khởi động MongoDB
Đảm bảo MongoDB đang chạy trên hệ thống của bạn:
```bash
# Windows
net start MongoDB

# macOS/Linux
sudo systemctl start mongod
```

### Chạy server
```bash
# Development mode với nodemon
npm run dev

# Production mode
npm start
```

Server sẽ chạy tại: `http://localhost:3000`

## API Endpoints

### Authentication
- `POST /api/auth/register` - Đăng ký tài khoản mới
- `POST /api/auth/login` - Đăng nhập
- `PUT /api/auth/change-password` - Đổi mật khẩu

### Nhóm Sản Phẩm
- `GET /api/nhom-san-pham` - Lấy tất cả nhóm sản phẩm
- `GET /api/nhom-san-pham/:maso` - Lấy nhóm sản phẩm theo mã số
- `POST /api/nhom-san-pham` - Tạo nhóm sản phẩm mới
- `PUT /api/nhom-san-pham/:maso` - Cập nhật nhóm sản phẩm
- `DELETE /api/nhom-san-pham/:maso` - Xóa nhóm sản phẩm

### Sản Phẩm
- `GET /api/san-pham` - Lấy tất cả sản phẩm
- `GET /api/san-pham/:masp` - Lấy sản phẩm theo mã
- `GET /api/san-pham/nhom/:maso` - Lấy sản phẩm theo nhóm
- `GET /api/san-pham/search/:keyword` - Tìm kiếm sản phẩm
- `POST /api/san-pham` - Tạo sản phẩm mới
- `PUT /api/san-pham/:masp` - Cập nhật sản phẩm
- `DELETE /api/san-pham/:masp` - Xóa sản phẩm
- `PATCH /api/san-pham/:masp/stock` - Cập nhật số lượng kho

### Đơn Hàng
- `GET /api/don-hang` - Lấy tất cả đơn hàng
- `GET /api/don-hang/:id` - Lấy đơn hàng theo ID
- `GET /api/don-hang/customer/:tenkh` - Lấy đơn hàng theo khách hàng
- `POST /api/don-hang` - Tạo đơn hàng mới
- `PUT /api/don-hang/:id` - Cập nhật đơn hàng
- `PATCH /api/don-hang/:id/status` - Cập nhật trạng thái đơn hàng
- `DELETE /api/don-hang/:id` - Xóa đơn hàng
- `GET /api/don-hang/stats/summary` - Thống kê đơn hàng

### Chi Tiết Đơn Hàng
- `GET /api/chi-tiet-don-hang` - Lấy tất cả chi tiết đơn hàng
- `GET /api/chi-tiet-don-hang/:id` - Lấy chi tiết đơn hàng theo ID
- `GET /api/chi-tiet-don-hang/order/:orderId` - Lấy chi tiết theo đơn hàng
- `POST /api/chi-tiet-don-hang` - Tạo chi tiết đơn hàng mới
- `POST /api/chi-tiet-don-hang/bulk` - Tạo nhiều chi tiết đơn hàng
- `PUT /api/chi-tiet-don-hang/:id` - Cập nhật chi tiết đơn hàng
- `PATCH /api/chi-tiet-don-hang/:id/status` - Cập nhật trạng thái
- `DELETE /api/chi-tiet-don-hang/:id` - Xóa chi tiết đơn hàng

### Tài Khoản
- `GET /api/tai-khoan` - Lấy tất cả tài khoản
- `GET /api/tai-khoan/:tendn` - Lấy tài khoản theo tên đăng nhập
- `POST /api/tai-khoan` - Tạo tài khoản mới
- `PUT /api/tai-khoan/:tendn` - Cập nhật tài khoản
- `PATCH /api/tai-khoan/:tendn/role` - Cập nhật quyền tài khoản
- `DELETE /api/tai-khoan/:tendn` - Xóa tài khoản
- `GET /api/tai-khoan/stats/summary` - Thống kê tài khoản

### Health Check
- `GET /api/health` - Kiểm tra trạng thái server

## Cấu trúc Database

### Collections
1. **nhomsanpham** - Nhóm sản phẩm
2. **sanpham** - Sản phẩm
3. **taikhoan** - Tài khoản người dùng
4. **dathang** - Đơn hàng
5. **chitietdonhang** - Chi tiết đơn hàng

## Trạng thái đơn hàng
- `0`: Chờ xử lý
- `1`: Đã xác nhận
- `2`: Đang giao
- `3`: Hoàn thành

## Quyền người dùng
- `user`: Người dùng thường
- `manager`: Quản lý
- `admin`: Quản trị viên

## Xử lý lỗi
API trả về response theo format:
```json
{
  "success": true/false,
  "message": "Thông báo",
  "data": {}, // Chỉ có khi success = true
  "error": "Chi tiết lỗi" // Chỉ có khi success = false
}
```

## Development
```bash
# Chạy với nodemon (auto-reload)
npm run dev

# Chạy tests (nếu có)
npm test
```

## Production
```bash
# Build và chạy production
npm start
```

## Ghi chú
- Tất cả ảnh được lưu dưới dạng Buffer (binary data) trong MongoDB
- API hỗ trợ upload ảnh dưới dạng base64
- JWT token có thời hạn 7 ngày (có thể cấu hình trong .env)
- Password được hash bằng bcrypt với salt rounds = 10