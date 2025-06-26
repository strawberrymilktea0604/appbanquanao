const mongoose = require('mongoose');

const chiTietDonHangSchema = new mongoose.Schema({
    id_chitiet: {
        type: String,
        required: true,
        unique: true
    },
    id_dathang: {
        type: String,
        required: true,
        ref: 'DonHang'
    },
    masp: {
        type: String,
        required: true,
        ref: 'SanPham'
    },
    soluong: {
        type: Number,
        required: true,
        min: 1
    },
    dongia: {
        type: Number,
        required: true,
        min: 0
    },
    anh: {
        type: Buffer, // Store image as binary data
        required: false
    },
    trangthai: {
        type: Number,
        default: 0,
        enum: [0, 1, 2, 3] // 0: Chờ xử lý, 1: Đã xác nhận, 2: Đang giao, 3: Hoàn thành
    }
}, {
    timestamps: true,
    collection: 'chitietdonhang'
});

// Add indexes for better performance
chiTietDonHangSchema.index({ id_chitiet: 1 });
chiTietDonHangSchema.index({ id_dathang: 1 });
chiTietDonHangSchema.index({ masp: 1 });
chiTietDonHangSchema.index({ trangthai: 1 });

module.exports = mongoose.model('ChiTietDonHang', chiTietDonHangSchema);