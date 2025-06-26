const mongoose = require('mongoose');

const nhomSanPhamSchema = new mongoose.Schema({
    maso: {
        type: String,
        required: true,
        unique: true
    },
    tennsp: {
        type: String,
        required: true,
        trim: true
    },
    anh: {
        type: Buffer, // Store image as binary data
        required: false
    }
}, {
    timestamps: true,
    collection: 'nhomsanpham'
});

// Add indexes for better performance
nhomSanPhamSchema.index({ maso: 1 });
nhomSanPhamSchema.index({ tennsp: 1 });

module.exports = mongoose.model('NhomSanPham', nhomSanPhamSchema);