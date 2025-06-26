const express = require('express');
const SanPham = require('../models/SanPham');
const router = express.Router();

// Get all san pham
router.get('/', async (req, res) => {
    try {
        const sanPhams = await SanPham.find().sort({ createdAt: -1 });
        res.json({
            success: true,
            data: sanPhams
        });
    } catch (error) {
        console.error('Get san pham error:', error);
        res.status(500).json({
            success: false,
            message: 'Lỗi server khi lấy danh sách sản phẩm',
            error: error.message
        });
    }
});

// Get san pham by masp
router.get('/:masp', async (req, res) => {
    try {
        const sanPham = await SanPham.findOne({ masp: req.params.masp });
        if (!sanPham) {
            return res.status(404).json({
                success: false,
                message: 'Không tìm thấy sản phẩm'
            });
        }
        res.json({
            success: true,
            data: sanPham
        });
    } catch (error) {
        console.error('Get san pham by masp error:', error);
        res.status(500).json({
            success: false,
            message: 'Lỗi server khi lấy sản phẩm',
            error: error.message
        });
    }
});

// Get san pham by nhom san pham
router.get('/nhom/:maso', async (req, res) => {
    try {
        const sanPhams = await SanPham.find({ maso: req.params.maso }).sort({ createdAt: -1 });
        res.json({
            success: true,
            data: sanPhams
        });
    } catch (error) {
        console.error('Get san pham by nhom error:', error);
        res.status(500).json({
            success: false,
            message: 'Lỗi server khi lấy sản phẩm theo nhóm',
            error: error.message
        });
    }
});

// Search san pham by name
router.get('/search/:keyword', async (req, res) => {
    try {
        const keyword = req.params.keyword;
        const sanPhams = await SanPham.find({
            $or: [
                { tensp: { $regex: keyword, $options: 'i' } },
                { mota: { $regex: keyword, $options: 'i' } }
            ]
        }).sort({ createdAt: -1 });
        
        res.json({
            success: true,
            data: sanPhams
        });
    } catch (error) {
        console.error('Search san pham error:', error);
        res.status(500).json({
            success: false,
            message: 'Lỗi server khi tìm kiếm sản phẩm',
            error: error.message
        });
    }
});

// Create new san pham
router.post('/', async (req, res) => {
    try {
        const { masp, tensp, dongia, mota, ghichu, soluongkho, maso, anh } = req.body;

        // Check if masp already exists
        const existingSanPham = await SanPham.findOne({ masp });
        if (existingSanPham) {
            return res.status(400).json({
                success: false,
                message: 'Mã sản phẩm đã tồn tại'
            });
        }

        const newSanPham = new SanPham({
            masp,
            tensp,
            dongia,
            mota: mota || '',
            ghichu: ghichu || '',
            soluongkho,
            maso,
            anh: anh ? Buffer.from(anh, 'base64') : undefined
        });

        await newSanPham.save();

        res.status(201).json({
            success: true,
            message: 'Thêm sản phẩm thành công',
            data: newSanPham
        });
    } catch (error) {
        console.error('Create san pham error:', error);
        res.status(500).json({
            success: false,
            message: 'Lỗi server khi thêm sản phẩm',
            error: error.message
        });
    }
});

// Update san pham
router.put('/:masp', async (req, res) => {
    try {
        const { tensp, dongia, mota, ghichu, soluongkho, maso, anh } = req.body;
        
        const updateData = {
            tensp,
            dongia,
            mota: mota || '',
            ghichu: ghichu || '',
            soluongkho,
            maso
        };
        
        if (anh) {
            updateData.anh = Buffer.from(anh, 'base64');
        }

        const updatedSanPham = await SanPham.findOneAndUpdate(
            { masp: req.params.masp },
            updateData,
            { new: true }
        );

        if (!updatedSanPham) {
            return res.status(404).json({
                success: false,
                message: 'Không tìm thấy sản phẩm'
            });
        }

        res.json({
            success: true,
            message: 'Cập nhật sản phẩm thành công',
            data: updatedSanPham
        });
    } catch (error) {
        console.error('Update san pham error:', error);
        res.status(500).json({
            success: false,
            message: 'Lỗi server khi cập nhật sản phẩm',
            error: error.message
        });
    }
});

// Delete san pham
router.delete('/:masp', async (req, res) => {
    try {
        const deletedSanPham = await SanPham.findOneAndDelete({ masp: req.params.masp });
        
        if (!deletedSanPham) {
            return res.status(404).json({
                success: false,
                message: 'Không tìm thấy sản phẩm'
            });
        }

        res.json({
            success: true,
            message: 'Xóa sản phẩm thành công'
        });
    } catch (error) {
        console.error('Delete san pham error:', error);
        res.status(500).json({
            success: false,
            message: 'Lỗi server khi xóa sản phẩm',
            error: error.message
        });
    }
});

// Update stock quantity
router.patch('/:masp/stock', async (req, res) => {
    try {
        const { soluongkho } = req.body;
        
        const updatedSanPham = await SanPham.findOneAndUpdate(
            { masp: req.params.masp },
            { soluongkho },
            { new: true }
        );

        if (!updatedSanPham) {
            return res.status(404).json({
                success: false,
                message: 'Không tìm thấy sản phẩm'
            });
        }

        res.json({
            success: true,
            message: 'Cập nhật số lượng kho thành công',
            data: updatedSanPham
        });
    } catch (error) {
        console.error('Update stock error:', error);
        res.status(500).json({
            success: false,
            message: 'Lỗi server khi cập nhật số lượng kho',
            error: error.message
        });
    }
});

module.exports = router;