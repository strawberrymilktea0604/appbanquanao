const mongoose = require('mongoose');
const sqlite3 = require('sqlite3').verbose();
const path = require('path');
require('dotenv').config();

// Import models
const NhomSanPham = require('../models/NhomSanPham');
const SanPham = require('../models/SanPham');
const TaiKhoan = require('../models/TaiKhoan');
const DonHang = require('../models/DonHang');
const ChiTietDonHang = require('../models/ChiTietDonHang');

// SQLite database path (adjust if needed)
const sqliteDbPath = path.join(__dirname, '../../banhang.db');

async function connectMongoDB() {
    try {
        await mongoose.connect(process.env.MONGODB_URI || 'mongodb://localhost:27017/appbanquanao', {
            useNewUrlParser: true,
            useUnifiedTopology: true,
        });
        console.log('Connected to MongoDB');
    } catch (error) {
        console.error('MongoDB connection error:', error);
        process.exit(1);
    }
}

function connectSQLite() {
    return new Promise((resolve, reject) => {
        const db = new sqlite3.Database(sqliteDbPath, (err) => {
            if (err) {
                console.error('SQLite connection error:', err);
                reject(err);
            } else {
                console.log('Connected to SQLite database');
                resolve(db);
            }
        });
    });
}

async function migrateNhomSanPham(sqliteDb) {
    return new Promise((resolve, reject) => {
        sqliteDb.all("SELECT * FROM nhomsanpham", async (err, rows) => {
            if (err) {
                console.error('Error fetching nhomsanpham:', err);
                reject(err);
                return;
            }

            try {
                for (const row of rows) {
                    const nhomSanPham = new NhomSanPham({
                        maso: row.maso.toString(),
                        tennsp: row.tennsp,
                        anh: row.anh
                    });
                    await nhomSanPham.save();
                }
                console.log(`Migrated ${rows.length} nhom san pham records`);
                resolve();
            } catch (error) {
                console.error('Error migrating nhomsanpham:', error);
                reject(error);
            }
        });
    });
}

async function migrateSanPham(sqliteDb) {
    return new Promise((resolve, reject) => {
        sqliteDb.all("SELECT * FROM sanpham", async (err, rows) => {
            if (err) {
                console.error('Error fetching sanpham:', err);
                reject(err);
                return;
            }

            try {
                for (const row of rows) {
                    const sanPham = new SanPham({
                        masp: row.masp.toString(),
                        tensp: row.tensp,
                        dongia: row.dongia,
                        mota: row.mota || '',
                        ghichu: row.ghichu || '',
                        soluongkho: row.soluongkho || 0,
                        maso: row.maso.toString(),
                        anh: row.anh
                    });
                    await sanPham.save();
                }
                console.log(`Migrated ${rows.length} san pham records`);
                resolve();
            } catch (error) {
                console.error('Error migrating sanpham:', error);
                reject(error);
            }
        });
    });
}

async function migrateTaiKhoan(sqliteDb) {
    return new Promise((resolve, reject) => {
        sqliteDb.all("SELECT * FROM taikhoan", async (err, rows) => {
            if (err) {
                console.error('Error fetching taikhoan:', err);
                reject(err);
                return;
            }

            try {
                for (const row of rows) {
                    // Note: Passwords in SQLite might not be hashed, 
                    // so we'll need to hash them during migration
                    const taiKhoan = new TaiKhoan({
                        tendn: row.tendn,
                        matkhau: row.matkhau, // Will be hashed by the pre-save middleware
                        quyen: row.quyen || 'user'
                    });
                    await taiKhoan.save();
                }
                console.log(`Migrated ${rows.length} tai khoan records`);
                resolve();
            } catch (error) {
                console.error('Error migrating taikhoan:', error);
                reject(error);
            }
        });
    });
}

async function migrateDonHang(sqliteDb) {
    return new Promise((resolve, reject) => {
        sqliteDb.all("SELECT * FROM Dathang", async (err, rows) => {
            if (err) {
                console.error('Error fetching Dathang:', err);
                reject(err);
                return;
            }

            try {
                for (const row of rows) {
                    const donHang = new DonHang({
                        id_dathang: row.id_dathang.toString(),
                        tenkh: row.tenkh,
                        diachi: row.diachi,
                        sdt: row.sdt,
                        tongthanhtoan: row.tongthanhtoan,
                        ngaydathang: row.ngaydathang ? new Date(row.ngaydathang) : new Date(),
                        trangthai: row.trangthai || 0
                    });
                    await donHang.save();
                }
                console.log(`Migrated ${rows.length} don hang records`);
                resolve();
            } catch (error) {
                console.error('Error migrating dathang:', error);
                reject(error);
            }
        });
    });
}

async function migrateChiTietDonHang(sqliteDb) {
    return new Promise((resolve, reject) => {
        sqliteDb.all("SELECT * FROM Chitietdonhang", async (err, rows) => {
            if (err) {
                console.error('Error fetching Chitietdonhang:', err);
                reject(err);
                return;
            }

            try {
                for (const row of rows) {
                    const chiTietDonHang = new ChiTietDonHang({
                        id_chitiet: row.id_chitiet.toString(),
                        id_dathang: row.id_dathang.toString(),
                        masp: row.masp.toString(),
                        soluong: row.soluong,
                        dongia: row.dongia,
                        anh: row.anh,
                        trangthai: row.trangthai || 0
                    });
                    await chiTietDonHang.save();
                }
                console.log(`Migrated ${rows.length} chi tiet don hang records`);
                resolve();
            } catch (error) {
                console.error('Error migrating chitietdonhang:', error);
                reject(error);
            }
        });
    });
}

async function migrate() {
    try {
        console.log('Starting migration from SQLite to MongoDB...');
        
        // Connect to databases
        await connectMongoDB();
        const sqliteDb = await connectSQLite();

        // Clear existing data (optional - comment out if you want to keep existing data)
        console.log('Clearing existing MongoDB data...');
        await NhomSanPham.deleteMany({});
        await SanPham.deleteMany({});
        await TaiKhoan.deleteMany({});
        await DonHang.deleteMany({});
        await ChiTietDonHang.deleteMany({});

        // Migrate data
        await migrateNhomSanPham(sqliteDb);
        await migrateSanPham(sqliteDb);
        await migrateTaiKhoan(sqliteDb);
        await migrateDonHang(sqliteDb);
        await migrateChiTietDonHang(sqliteDb);

        console.log('Migration completed successfully!');
        
        // Close connections
        sqliteDb.close();
        await mongoose.connection.close();
        
    } catch (error) {
        console.error('Migration failed:', error);
        process.exit(1);
    }
}

// Run migration if this file is executed directly
if (require.main === module) {
    migrate();
}

module.exports = { migrate };