const express = require('express');
const mongoose = require('mongoose');
const cors = require('cors');
const dotenv = require('dotenv');
const bodyParser = require('body-parser');

// Import routes
const authRoutes = require('./routes/auth');
const nhomSanPhamRoutes = require('./routes/nhomSanPham');
const sanPhamRoutes = require('./routes/sanPham');
const donHangRoutes = require('./routes/donHang');
const chiTietDonHangRoutes = require('./routes/chiTietDonHang');
const taiKhoanRoutes = require('./routes/taiKhoan');

// Load environment variables
dotenv.config();

const app = express();
const PORT = process.env.PORT || 3000;

// Middleware
app.use(cors());
app.use(bodyParser.json({ limit: '50mb' }));
app.use(bodyParser.urlencoded({ extended: true, limit: '50mb' }));
app.use(express.json());

// MongoDB connection
mongoose.connect(process.env.MONGODB_URI || 'mongodb://127.0.0.1:27017/appbanquanao', {
    useNewUrlParser: true,
    useUnifiedTopology: true,
    family: 4, // Force IPv4
})
.then(() => {
    console.log('Connected to MongoDB successfully');
})
.catch((error) => {
    console.error('MongoDB connection error:', error);
    process.exit(1);
});

// Routes
app.use('/api/auth', authRoutes);
app.use('/api/nhom-san-pham', nhomSanPhamRoutes);
app.use('/api/san-pham', sanPhamRoutes);
app.use('/api/don-hang', donHangRoutes);
app.use('/api/chi-tiet-don-hang', chiTietDonHangRoutes);
app.use('/api/tai-khoan', taiKhoanRoutes);

// Health check endpoint
app.get('/api/health', (req, res) => {
    res.json({ 
        status: 'OK', 
        message: 'Server is running',
        timestamp: new Date().toISOString()
    });
});

// Error handling middleware
app.use((err, req, res, next) => {
    console.error(err.stack);
    res.status(500).json({ 
        success: false, 
        message: 'Something went wrong!',
        error: process.env.NODE_ENV === 'development' ? err.message : 'Internal server error'
    });
});

// 404 handler
app.use('*', (req, res) => {
    res.status(404).json({ 
        success: false, 
        message: 'Route not found' 
    });
});

app.listen(PORT, () => {
    console.log(`Server is running on port ${PORT}`);
    console.log(`Health check: http://localhost:${PORT}/api/health`);
});

module.exports = app;