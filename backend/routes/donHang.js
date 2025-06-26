const express = require('express');
const DonHang = require('../models/DonHang');
const router = express.Router();

// Get all don hang
router.get('/', async (req, res) => {
    try {
        const donHangs = await DonHang.find().sort({ ngaydathang: -1 });
        res.json({
            success: true,
            data: donHangs
        });
    } catch (error) {
        console.error('Get don hang error:', error);
        res.status(500).json({
            success: false,
            message: 'Lỗi server khi lấy danh sách đơn hàng',
            error: error.message
        });
    }
});

// Get don hang by id
router.get('/:id', async (req, res) => {
    try {
        const donHang = await DonHang.findOne({ id_dathang: req.params.id });
        if (!donHang) {
            return res.status(404).json({
                success: false,
                message: 'Không tìm thấy đơn hàng'
            });
        }
        res.json({
            success: true,
            data: donHang
        });
    } catch (error) {
        console.error('Get don hang by id error:', error);
        res.status(500).json({
            success: false,
            message: 'Lỗi server khi lấy đơn hàng',
            error: error.message
        });
    }
});

// Get don hang by customer name
router.get('/customer/:tenkh', async (req, res) => {
    try {
        const donHangs = await DonHang.find({ tenkh: req.params.tenkh }).sort({ ngaydathang: -1 });
        res.json({
            success: true,
            data: donHangs
        });
    } catch (error) {
        console.error('Get don hang by customer error:', error);
        res.status(500).json({
            success: false,
            message: 'Lỗi server khi lấy đơn hàng theo khách hàng',
            error: error.message
        });
    }
});

// Create new don hang
router.post('/', async (req, res) => {
    try {
        const { id_dathang, tenkh, diachi, sdt, tongthanhtoan, trangthai } = req.body;

        // Check if id_dathang already exists
        const existingDonHang = await DonHang.findOne({ id_dathang });
        if (existingDonHang) {
            return res.status(400).json({
                success: false,
                message: 'ID đơn hàng đã tồn tại'
            });
        }

        const newDonHang = new DonHang({
            id_dathang,
            tenkh,
            diachi,
            sdt,
            tongthanhtoan,
            trangthai: trangthai || 0
        });

        await newDonHang.save();

        res.status(201).json({
            success: true,
            message: 'Tạo đơn hàng thành công',
            data: newDonHang
        });
    } catch (error) {
        console.error('Create don hang error:', error);
        res.status(500).json({
            success: false,
            message: 'Lỗi server khi tạo đơn hàng',
            error: error.message
        });
    }
});

// Update don hang
router.put('/:id', async (req, res) => {
    try {
        const { tenkh, diachi, sdt, tongthanhtoan, trangthai } = req.body;
        
        const updateData = {
            tenkh,
            diachi,
            sdt,
            tongthanhtoan,
            trangthai
        };

        const updatedDonHang = await DonHang.findOneAndUpdate(
            { id_dathang: req.params.id },
            updateData,
            { new: true }
        );

        if (!updatedDonHang) {
            return res.status(404).json({
                success: false,
                message: 'Không tìm thấy đơn hàng'
            });
        }

        res.json({
            success: true,
            message: 'Cập nhật đơn hàng thành công',
            data: updatedDonHang
        });
    } catch (error) {
        console.error('Update don hang error:', error);
        res.status(500).json({
            success: false,
            message: 'Lỗi server khi cập nhật đơn hàng',
            error: error.message
        });
    }
});

// Update don hang status
router.patch('/:id/status', async (req, res) => {
    try {
        const { trangthai } = req.body;
        
        const updatedDonHang = await DonHang.findOneAndUpdate(
            { id_dathang: req.params.id },
            { trangthai },
            { new: true }
        );

        if (!updatedDonHang) {
            return res.status(404).json({
                success: false,
                message: 'Không tìm thấy đơn hàng'
            });
        }

        res.json({
            success: true,
            message: 'Cập nhật trạng thái đơn hàng thành công',
            data: updatedDonHang
        });
    } catch (error) {
        console.error('Update don hang status error:', error);
        res.status(500).json({
            success: false,
            message: 'Lỗi server khi cập nhật trạng thái đơn hàng',
            error: error.message
        });
    }
});

// Delete don hang
router.delete('/:id', async (req, res) => {
    try {
        const deletedDonHang = await DonHang.findOneAndDelete({ id_dathang: req.params.id });
        
        if (!deletedDonHang) {
            return res.status(404).json({
                success: false,
                message: 'Không tìm thấy đơn hàng'
            });
        }

        res.json({
            success: true,
            message: 'Xóa đơn hàng thành công'
        });
    } catch (error) {
        console.error('Delete don hang error:', error);
        res.status(500).json({
            success: false,
            message: 'Lỗi server khi xóa đơn hàng',
            error: error.message
        });
    }
});

// Get don hang statistics
router.get('/stats/summary', async (req, res) => {
    try {
        const totalOrders = await DonHang.countDocuments();
        const pendingOrders = await DonHang.countDocuments({ trangthai: 0 });
        const completedOrders = await DonHang.countDocuments({ trangthai: 3 });
        
        const totalRevenue = await DonHang.aggregate([
            { $match: { trangthai: 3 } },
            { $group: { _id: null, total: { $sum: '$tongthanhtoan' } } }
        ]);

        res.json({
            success: true,
            data: {
                totalOrders,
                pendingOrders,
                completedOrders,
                totalRevenue: totalRevenue.length > 0 ? totalRevenue[0].total : 0
            }
        });
    } catch (error) {
        console.error('Get don hang stats error:', error);
        res.status(500).json({
            success: false,
            message: 'Lỗi server khi lấy thống kê đơn hàng',
            error: error.message
        });
    }
});

module.exports = router;