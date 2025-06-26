const express = require('express');
const TaiKhoan = require('../models/TaiKhoan');
const router = express.Router();

// Get all tai khoan
router.get('/', async (req, res) => {
    try {
        const taiKhoans = await TaiKhoan.find().select('-matkhau').sort({ createdAt: -1 });
        res.json({
            success: true,
            data: taiKhoans
        });
    } catch (error) {
        console.error('Get tai khoan error:', error);
        res.status(500).json({
            success: false,
            message: 'Lỗi server khi lấy danh sách tài khoản',
            error: error.message
        });
    }
});

// Get tai khoan by tendn
router.get('/:tendn', async (req, res) => {
    try {
        const taiKhoan = await TaiKhoan.findOne({ tendn: req.params.tendn }).select('-matkhau');
        if (!taiKhoan) {
            return res.status(404).json({
                success: false,
                message: 'Không tìm th���y tài khoản'
            });
        }
        res.json({
            success: true,
            data: taiKhoan
        });
    } catch (error) {
        console.error('Get tai khoan by tendn error:', error);
        res.status(500).json({
            success: false,
            message: 'Lỗi server khi lấy tài khoản',
            error: error.message
        });
    }
});

// Create new tai khoan
router.post('/', async (req, res) => {
    try {
        const { tendn, matkhau, quyen } = req.body;

        // Check if tendn already exists
        const existingTaiKhoan = await TaiKhoan.findOne({ tendn });
        if (existingTaiKhoan) {
            return res.status(400).json({
                success: false,
                message: 'Tên đăng nhập đã tồn tại'
            });
        }

        const newTaiKhoan = new TaiKhoan({
            tendn,
            matkhau,
            quyen: quyen || 'user'
        });

        await newTaiKhoan.save();

        // Return without password
        const responseData = {
            tendn: newTaiKhoan.tendn,
            quyen: newTaiKhoan.quyen,
            createdAt: newTaiKhoan.createdAt,
            updatedAt: newTaiKhoan.updatedAt
        };

        res.status(201).json({
            success: true,
            message: 'Thêm tài khoản thành công',
            data: responseData
        });
    } catch (error) {
        console.error('Create tai khoan error:', error);
        res.status(500).json({
            success: false,
            message: 'Lỗi server khi thêm tài khoản',
            error: error.message
        });
    }
});

// Update tai khoan
router.put('/:tendn', async (req, res) => {
    try {
        const { matkhau, quyen } = req.body;
        
        const updateData = {};
        if (matkhau) {
            updateData.matkhau = matkhau;
        }
        if (quyen) {
            updateData.quyen = quyen;
        }

        const updatedTaiKhoan = await TaiKhoan.findOneAndUpdate(
            { tendn: req.params.tendn },
            updateData,
            { new: true }
        ).select('-matkhau');

        if (!updatedTaiKhoan) {
            return res.status(404).json({
                success: false,
                message: 'Không tìm thấy tài khoản'
            });
        }

        res.json({
            success: true,
            message: 'Cập nhật tài khoản thành công',
            data: updatedTaiKhoan
        });
    } catch (error) {
        console.error('Update tai khoan error:', error);
        res.status(500).json({
            success: false,
            message: 'Lỗi server khi cập nhật tài khoản',
            error: error.message
        });
    }
});

// Update tai khoan role
router.patch('/:tendn/role', async (req, res) => {
    try {
        const { quyen } = req.body;
        
        const updatedTaiKhoan = await TaiKhoan.findOneAndUpdate(
            { tendn: req.params.tendn },
            { quyen },
            { new: true }
        ).select('-matkhau');

        if (!updatedTaiKhoan) {
            return res.status(404).json({
                success: false,
                message: 'Không tìm thấy tài khoản'
            });
        }

        res.json({
            success: true,
            message: 'Cập nhật quyền tài khoản thành công',
            data: updatedTaiKhoan
        });
    } catch (error) {
        console.error('Update tai khoan role error:', error);
        res.status(500).json({
            success: false,
            message: 'Lỗi server khi cập nhật quyền tài khoản',
            error: error.message
        });
    }
});

// Delete tai khoan
router.delete('/:tendn', async (req, res) => {
    try {
        const deletedTaiKhoan = await TaiKhoan.findOneAndDelete({ tendn: req.params.tendn });
        
        if (!deletedTaiKhoan) {
            return res.status(404).json({
                success: false,
                message: 'Không tìm thấy t��i khoản'
            });
        }

        res.json({
            success: true,
            message: 'Xóa tài khoản thành công'
        });
    } catch (error) {
        console.error('Delete tai khoan error:', error);
        res.status(500).json({
            success: false,
            message: 'Lỗi server khi xóa tài khoản',
            error: error.message
        });
    }
});

// Get tai khoan statistics
router.get('/stats/summary', async (req, res) => {
    try {
        const totalUsers = await TaiKhoan.countDocuments();
        const adminUsers = await TaiKhoan.countDocuments({ quyen: 'admin' });
        const regularUsers = await TaiKhoan.countDocuments({ quyen: 'user' });
        const managerUsers = await TaiKhoan.countDocuments({ quyen: 'manager' });

        res.json({
            success: true,
            data: {
                totalUsers,
                adminUsers,
                regularUsers,
                managerUsers
            }
        });
    } catch (error) {
        console.error('Get tai khoan stats error:', error);
        res.status(500).json({
            success: false,
            message: 'Lỗi server khi lấy thống kê tài khoản',
            error: error.message
        });
    }
});

module.exports = router;