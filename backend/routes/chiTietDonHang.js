const express = require('express');
const ChiTietDonHang = require('../models/ChiTietDonHang');
const router = express.Router();

// Get all chi tiet don hang
router.get('/', async (req, res) => {
    try {
        const chiTietDonHangs = await ChiTietDonHang.find().sort({ createdAt: -1 });
        res.json({
            success: true,
            data: chiTietDonHangs
        });
    } catch (error) {
        console.error('Get chi tiet don hang error:', error);
        res.status(500).json({
            success: false,
            message: 'Lỗi server khi lấy danh sách chi tiết đơn hàng',
            error: error.message
        });
    }
});

// Get chi tiet don hang by id
router.get('/:id', async (req, res) => {
    try {
        const chiTietDonHang = await ChiTietDonHang.findOne({ id_chitiet: req.params.id });
        if (!chiTietDonHang) {
            return res.status(404).json({
                success: false,
                message: 'Không tìm thấy chi tiết đơn hàng'
            });
        }
        res.json({
            success: true,
            data: chiTietDonHang
        });
    } catch (error) {
        console.error('Get chi tiet don hang by id error:', error);
        res.status(500).json({
            success: false,
            message: 'Lỗi server khi lấy chi tiết đơn hàng',
            error: error.message
        });
    }
});

// Get chi tiet don hang by order id
router.get('/order/:orderId', async (req, res) => {
    try {
        const chiTietDonHangs = await ChiTietDonHang.find({ id_dathang: req.params.orderId });
        res.json({
            success: true,
            data: chiTietDonHangs
        });
    } catch (error) {
        console.error('Get chi tiet don hang by order error:', error);
        res.status(500).json({
            success: false,
            message: 'Lỗi server khi lấy chi tiết đơn hàng theo đơn hàng',
            error: error.message
        });
    }
});

// Create new chi tiet don hang
router.post('/', async (req, res) => {
    try {
        const { id_chitiet, id_dathang, masp, soluong, dongia, anh, trangthai } = req.body;

        // Check if id_chitiet already exists
        const existingChiTiet = await ChiTietDonHang.findOne({ id_chitiet });
        if (existingChiTiet) {
            return res.status(400).json({
                success: false,
                message: 'ID chi tiết đơn hàng đã tồn tại'
            });
        }

        const newChiTietDonHang = new ChiTietDonHang({
            id_chitiet,
            id_dathang,
            masp,
            soluong,
            dongia,
            anh: anh ? Buffer.from(anh, 'base64') : undefined,
            trangthai: trangthai || 0
        });

        await newChiTietDonHang.save();

        res.status(201).json({
            success: true,
            message: 'Thêm chi tiết đơn hàng thành công',
            data: newChiTietDonHang
        });
    } catch (error) {
        console.error('Create chi tiet don hang error:', error);
        res.status(500).json({
            success: false,
            message: 'Lỗi server khi thêm chi tiết đơn hàng',
            error: error.message
        });
    }
});

// Create multiple chi tiet don hang
router.post('/bulk', async (req, res) => {
    try {
        const { chiTietList } = req.body;

        if (!Array.isArray(chiTietList) || chiTietList.length === 0) {
            return res.status(400).json({
                success: false,
                message: 'Danh sách chi tiết đơn hàng không hợp lệ'
            });
        }

        // Process each item in the list
        const processedList = chiTietList.map(item => ({
            ...item,
            anh: item.anh ? Buffer.from(item.anh, 'base64') : undefined,
            trangthai: item.trangthai || 0
        }));

        const newChiTietDonHangs = await ChiTietDonHang.insertMany(processedList);

        res.status(201).json({
            success: true,
            message: 'Thêm danh sách chi tiết đơn hàng thành công',
            data: newChiTietDonHangs
        });
    } catch (error) {
        console.error('Create bulk chi tiet don hang error:', error);
        res.status(500).json({
            success: false,
            message: 'Lỗi server khi thêm danh sách chi tiết đơn hàng',
            error: error.message
        });
    }
});

// Update chi tiet don hang
router.put('/:id', async (req, res) => {
    try {
        const { id_dathang, masp, soluong, dongia, anh, trangthai } = req.body;
        
        const updateData = {
            id_dathang,
            masp,
            soluong,
            dongia,
            trangthai
        };
        
        if (anh) {
            updateData.anh = Buffer.from(anh, 'base64');
        }

        const updatedChiTietDonHang = await ChiTietDonHang.findOneAndUpdate(
            { id_chitiet: req.params.id },
            updateData,
            { new: true }
        );

        if (!updatedChiTietDonHang) {
            return res.status(404).json({
                success: false,
                message: 'Không tìm thấy chi tiết đơn hàng'
            });
        }

        res.json({
            success: true,
            message: 'Cập nhật chi tiết đơn hàng thành công',
            data: updatedChiTietDonHang
        });
    } catch (error) {
        console.error('Update chi tiet don hang error:', error);
        res.status(500).json({
            success: false,
            message: 'Lỗi server khi cập nhật chi tiết đơn hàng',
            error: error.message
        });
    }
});

// Update chi tiet don hang status
router.patch('/:id/status', async (req, res) => {
    try {
        const { trangthai } = req.body;
        
        const updatedChiTietDonHang = await ChiTietDonHang.findOneAndUpdate(
            { id_chitiet: req.params.id },
            { trangthai },
            { new: true }
        );

        if (!updatedChiTietDonHang) {
            return res.status(404).json({
                success: false,
                message: 'Không tìm thấy chi tiết đơn hàng'
            });
        }

        res.json({
            success: true,
            message: 'Cập nhật trạng thái chi tiết đơn hàng thành công',
            data: updatedChiTietDonHang
        });
    } catch (error) {
        console.error('Update chi tiet don hang status error:', error);
        res.status(500).json({
            success: false,
            message: 'Lỗi server khi cập nhật trạng thái chi tiết đơn hàng',
            error: error.message
        });
    }
});

// Delete chi tiet don hang
router.delete('/:id', async (req, res) => {
    try {
        const deletedChiTietDonHang = await ChiTietDonHang.findOneAndDelete({ id_chitiet: req.params.id });
        
        if (!deletedChiTietDonHang) {
            return res.status(404).json({
                success: false,
                message: 'Không tìm thấy chi tiết đơn hàng'
            });
        }

        res.json({
            success: true,
            message: 'Xóa chi tiết đơn hàng thành công'
        });
    } catch (error) {
        console.error('Delete chi tiet don hang error:', error);
        res.status(500).json({
            success: false,
            message: 'Lỗi server khi xóa chi tiết đơn hàng',
            error: error.message
        });
    }
});

module.exports = router;