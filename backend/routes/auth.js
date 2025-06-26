const express = require('express');
const jwt = require('jsonwebtoken');
const TaiKhoan = require('../models/TaiKhoan');
const router = express.Router();

// Register new account
router.post('/register', async (req, res) => {
    try {
        const { tendn, matkhau, quyen } = req.body;

        // Check if user already exists
        const existingUser = await TaiKhoan.findOne({ tendn });
        if (existingUser) {
            return res.status(400).json({
                success: false,
                message: 'Tên đăng nhập đã tồn tại'
            });
        }

        // Create new user
        const newUser = new TaiKhoan({
            tendn,
            matkhau,
            quyen: quyen || 'user'
        });

        await newUser.save();

        res.status(201).json({
            success: true,
            message: 'Đăng ký thành công',
            data: {
                tendn: newUser.tendn,
                quyen: newUser.quyen
            }
        });
    } catch (error) {
        console.error('Register error:', error);
        res.status(500).json({
            success: false,
            message: 'Lỗi server khi đăng ký',
            error: error.message
        });
    }
});

// Login
router.post('/login', async (req, res) => {
    try {
        const { tendn, matkhau } = req.body;

        // Find user
        const user = await TaiKhoan.findOne({ tendn });
        if (!user) {
            return res.status(401).json({
                success: false,
                message: 'Tên đăng nhập hoặc mật khẩu không đúng'
            });
        }

        // Check password
        const isPasswordValid = await user.comparePassword(matkhau);
        if (!isPasswordValid) {
            return res.status(401).json({
                success: false,
                message: 'Tên đăng nhập hoặc mật khẩu không đúng'
            });
        }

        // Generate JWT token
        const token = jwt.sign(
            { 
                userId: user._id, 
                tendn: user.tendn, 
                quyen: user.quyen 
            },
            process.env.JWT_SECRET || 'default_secret',
            { expiresIn: process.env.JWT_EXPIRES_IN || '7d' }
        );

        res.json({
            success: true,
            message: 'Đăng nhập thành công',
            data: {
                token,
                user: {
                    tendn: user.tendn,
                    quyen: user.quyen
                }
            }
        });
    } catch (error) {
        console.error('Login error:', error);
        res.status(500).json({
            success: false,
            message: 'Lỗi server khi đăng nhập',
            error: error.message
        });
    }
});

// Change password
router.put('/change-password', async (req, res) => {
    try {
        const { tendn, matkhauCu, matkhauMoi } = req.body;

        // Find user
        const user = await TaiKhoan.findOne({ tendn });
        if (!user) {
            return res.status(404).json({
                success: false,
                message: 'Không tìm thấy tài khoản'
            });
        }

        // Check old password
        const isOldPasswordValid = await user.comparePassword(matkhauCu);
        if (!isOldPasswordValid) {
            return res.status(401).json({
                success: false,
                message: 'Mật khẩu cũ không đúng'
            });
        }

        // Update password
        user.matkhau = matkhauMoi;
        await user.save();

        res.json({
            success: true,
            message: 'Đổi mật khẩu thành công'
        });
    } catch (error) {
        console.error('Change password error:', error);
        res.status(500).json({
            success: false,
            message: 'Lỗi server khi đổi mật khẩu',
            error: error.message
        });
    }
});

module.exports = router;