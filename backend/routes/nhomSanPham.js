const express = require('express');
const NhomSanPham = require('../models/NhomSanPham');
const router = express.Router();

// Get all nhom san pham
router.get('/', async (req, res) => {
    try {
        const nhomSanPhams = await NhomSanPham.find().sort({ createdAt: -1 });
        res.json({
            success: true,
            data: nhomSanPhams
        });
    } catch (error) {
        console.error('Get nhom san pham error:', error);
        res.status(500).json({
            success: false,
            message: 'Lỗi server khi lấy danh sách nhóm sản phẩm',
            error: error.message
        });
    }
});

// Get nhom san pham by maso
router.get('/:maso', async (req, res) => {
    try {
        const nhomSanPham = await NhomSanPham.findOne({ maso: req.params.maso });
        if (!nhomSanPham) {
            return res.status(404).json({
                success: false,
                message: 'Không tìm thấy nhóm sản phẩm'
            });
        }
        res.json({
            success: true,
            data: nhomSanPham
        });
    } catch (error) {
        console.error('Get nhom san pham by maso error:', error);
        res.status(500).json({
            success: false,
            message: 'Lỗi server khi lấy nhóm sản phẩm',
            error: error.message
        });
    }
});

// Create new nhom san pham
router.post('/', async (req, res) => {
    try {
        const { maso, tennsp, anh } = req.body;

        // Check if maso already exists
        const existingNhom = await NhomSanPham.findOne({ maso });
        if (existingNhom) {
            return res.status(400).json({
                success: false,
                message: 'Mã số nhóm sản phẩm đã tồn tại'
            });
        }

        const newNhomSanPham = new NhomSanPham({
            maso,
            tennsp,
            anh: anh ? Buffer.from(anh, 'base64') : undefined
        });

        await newNhomSanPham.save();

        res.status(201).json({
            success: true,
            message: 'Thêm nhóm sản phẩm thành công',
            data: newNhomSanPham
        });
    } catch (error) {
        console.error('Create nhom san pham error:', error);
        res.status(500).json({
            success: false,
            message: 'Lỗi server khi thêm nhóm sản phẩm',
            error: error.message
        });
    }
});

// Update nhom san pham
router.put('/:maso', async (req, res) => {
    try {
        const { tennsp, anh } = req.body;
        
        const updateData = { tennsp };
        if (anh) {
            updateData.anh = Buffer.from(anh, 'base64');
        }

        const updatedNhomSanPham = await NhomSanPham.findOneAndUpdate(
            { maso: req.params.maso },
            updateData,
            { new: true }
        );

        if (!updatedNhomSanPham) {
            return res.status(404).json({
                success: false,
                message: 'Không tìm thấy nhóm sản phẩm'
            });
        }

        res.json({
            success: true,
            message: 'Cập nhật nhóm sản phẩm thành công',
            data: updatedNhomSanPham
        });
    } catch (error) {
        console.error('Update nhom san pham error:', error);
        res.status(500).json({
            success: false,
            message: 'Lỗi server khi cập nhật nhóm sản phẩm',
            error: error.message
        });
    }
});

// Delete nhom san pham
router.delete('/:maso', async (req, res) => {
    try {
        const deletedNhomSanPham = await NhomSanPham.findOneAndDelete({ maso: req.params.maso });
        
        if (!deletedNhomSanPham) {
            return res.status(404).json({
                success: false,
                message: 'Không tìm thấy nhóm sản phẩm'
            });
        }

        res.json({
            success: true,
            message: 'Xóa nhóm sản phẩm thành công'
        });
    } catch (error) {
        console.error('Delete nhom san pham error:', error);
        res.status(500).json({
            success: false,
            message: 'Lỗi server khi xóa nhóm sản phẩm',
            error: error.message
        });
    }
});

module.exports = router;