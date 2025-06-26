const jwt = require('jsonwebtoken');
const TaiKhoan = require('../models/TaiKhoan');

// Middleware to verify JWT token
const authenticateToken = async (req, res, next) => {
    try {
        const authHeader = req.headers['authorization'];
        const token = authHeader && authHeader.split(' ')[1]; // Bearer TOKEN

        if (!token) {
            return res.status(401).json({
                success: false,
                message: 'Access token is required'
            });
        }

        const decoded = jwt.verify(token, process.env.JWT_SECRET || 'default_secret');
        
        // Get user from database to ensure user still exists
        const user = await TaiKhoan.findById(decoded.userId).select('-matkhau');
        if (!user) {
            return res.status(401).json({
                success: false,
                message: 'User not found'
            });
        }

        req.user = user;
        next();
    } catch (error) {
        console.error('Auth middleware error:', error);
        return res.status(403).json({
            success: false,
            message: 'Invalid or expired token'
        });
    }
};

// Middleware to check if user is admin
const requireAdmin = (req, res, next) => {
    if (req.user && req.user.quyen === 'admin') {
        next();
    } else {
        return res.status(403).json({
            success: false,
            message: 'Admin access required'
        });
    }
};

// Middleware to check if user is admin or manager
const requireAdminOrManager = (req, res, next) => {
    if (req.user && (req.user.quyen === 'admin' || req.user.quyen === 'manager')) {
        next();
    } else {
        return res.status(403).json({
            success: false,
            message: 'Admin or Manager access required'
        });
    }
};

module.exports = {
    authenticateToken,
    requireAdmin,
    requireAdminOrManager
};