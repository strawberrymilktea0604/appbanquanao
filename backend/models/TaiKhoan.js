const mongoose = require('mongoose');
const bcrypt = require('bcryptjs');

const taiKhoanSchema = new mongoose.Schema({
    tendn: {
        type: String,
        required: true,
        unique: true,
        trim: true,
        minlength: 3,
        maxlength: 20
    },
    matkhau: {
        type: String,
        required: true,
        minlength: 6
    },
    quyen: {
        type: String,
        required: true,
        enum: ['admin', 'user', 'manager'],
        default: 'user'
    }
}, {
    timestamps: true,
    collection: 'taikhoan'
});

// Hash password before saving
taiKhoanSchema.pre('save', async function(next) {
    if (!this.isModified('matkhau')) return next();
    
    try {
        const salt = await bcrypt.genSalt(10);
        this.matkhau = await bcrypt.hash(this.matkhau, salt);
        next();
    } catch (error) {
        next(error);
    }
});

// Compare password method
taiKhoanSchema.methods.comparePassword = async function(candidatePassword) {
    return bcrypt.compare(candidatePassword, this.matkhau);
};

// Add indexes
taiKhoanSchema.index({ tendn: 1 });
taiKhoanSchema.index({ quyen: 1 });

module.exports = mongoose.model('TaiKhoan', taiKhoanSchema);