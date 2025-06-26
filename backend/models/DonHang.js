const mongoose = require('mongoose');

const donHangSchema = new mongoose.Schema({
    id_dathang: {
        type: String,
        required: true,
        unique: true
    },
    tenkh: {
        type: String,
        required: true,
        trim: true
    },
    diachi: {
        type: String,
        required: true,
        trim: true
    },
    sdt: {
        type: String,
        required: true,
        trim: true
    },
    tongthanhtoan: {
        type: Number,
        required: true,
        min: 0
    },
    ngaydathang: {
        type: Date,
        default: Date.now
    },
    trangthai: {
        type: Number,
        default: 0,
        enum: [0, 1, 2, 3] // 0: Chờ xử lý, 1: Đã xác nhận, 2: Đang giao, 3: Hoàn thành
    }
}, {
    timestamps: true,
    collection: 'dathang'
});

// Add indexes for better performance
donHangSchema.index({ id_dathang: 1 });
donHangSchema.index({ tenkh: 1 });
donHangSchema.index({ ngaydathang: -1 });
donHangSchema.index({ trangthai: 1 });

module.exports = mongoose.model('DonHang', donHangSchema);