const mongoose = require('mongoose');

const sanPhamSchema = new mongoose.Schema({
    masp: {
        type: String,
        required: true,
        unique: true
    },
    tensp: {
        type: String,
        required: true,
        trim: true
    },
    dongia: {
        type: Number,
        required: true,
        min: 0
    },
    mota: {
        type: String,
        default: ''
    },
    ghichu: {
        type: String,
        default: ''
    },
    soluongkho: {
        type: Number,
        required: true,
        min: 0,
        default: 0
    },
    maso: {
        type: String,
        required: true,
        ref: 'NhomSanPham'
    },
    anh: {
        type: Buffer, // Store image as binary data
        required: false
    }
}, {
    timestamps: true,
    collection: 'sanpham'
});

// Add indexes for better performance
sanPhamSchema.index({ masp: 1 });
sanPhamSchema.index({ tensp: 1 });
sanPhamSchema.index({ maso: 1 });
sanPhamSchema.index({ tensp: 'text', mota: 'text' }); // Text search index

module.exports = mongoose.model('SanPham', sanPhamSchema);