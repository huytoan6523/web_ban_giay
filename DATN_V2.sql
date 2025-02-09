CREATE DATABASE DATN_SD40


/*NHÂN VIÊN*/
-- Bảng chức vụ
CREATE TABLE chuc_vu
(
    id BIGINT IDENTITY(1,1) PRIMARY KEY NOT NULL,
    ten NVARCHAR(100) DEFAULT NULL,
    ngay_tao DATETIME NULL,
    ngay_sua DATETIME NULL,
    trang_thai INT
);

-- Bảng Nhân viên
CREATE TABLE nhan_vien
(
    id BIGINT IDENTITY(1,1) PRIMARY KEY NOT NULL,
    ma NVARCHAR(100) NOT NULL,
    ho_ten NVARCHAR(MAX) DEFAULT NULL,
    ngay_sinh DATETIME NULL,
    ngay_tao DATETIME NULL,
    sdt NVARCHAR(11),
    gioi_tinh INT,
    dia_chi NVARCHAR(MAX),
    hinh_anh NVARCHAR(MAX),
    email NVARCHAR(MAX),
    tai_khoan NVARCHAR(MAX),
    mat_khau NVARCHAR(MAX),
    trang_thai INT,

    id_chuc_vu BIGINT
        REFERENCES chuc_vu(id),
);

/*KHÁCH HÀNG*/


-- Bảng Khách hàng
CREATE TABLE khach_hang
(
    id BIGINT IDENTITY(1,1) PRIMARY KEY NOT NULL,
    ma NVARCHAR(100) NOT NULL,
    ho_ten NVARCHAR(MAX) DEFAULT NULL,
    ngay_sinh DATETIME NULL,
    ngay_tao DATETIME NULL,
    sdt NVARCHAR(11),
    gioi_tinh INT,
    email NVARCHAR(MAX),
    tai_khoan NVARCHAR(MAX),
    mat_khau NVARCHAR(MAX),
    trang_thai INT
);

-- Bảng địa chỉ

CREATE TABLE dia_chi
(
    id BIGINT IDENTITY(1,1) PRIMARY KEY NOT NULL,
    dia_chi NVARCHAR(MAX),
    trang_thai INT,

    id_khach_hang BIGINT
        REFERENCES khach_hang(id)
);


/*SẢN PHẨM*/

--Bảng thương hiệu

CREATE TABLE thuong_hieu
(
    id BIGINT IDENTITY(1,1) PRIMARY KEY NOT NULL,
    ten NVARCHAR(100) DEFAULT NULL,
    ngay_tao DATETIME NULL,
    ngay_sua DATETIME NULL,
    trang_thai INT
);

--Bảng Màu sắc

CREATE TABLE mau_sac
(
    id BIGINT IDENTITY(1,1) PRIMARY KEY NOT NULL,
    ten NVARCHAR(100) DEFAULT NULL,
    ma_mau NVARCHAR(MAX),
    ngay_tao DATETIME NULL,
    ngay_sua DATETIME NULL,
    trang_thai INT
);

--Bảng Thể loại

CREATE TABLE the_loai
(
    id BIGINT IDENTITY(1,1) PRIMARY KEY NOT NULL,
    ten NVARCHAR(100) DEFAULT NULL,
    ngay_tao DATETIME NULL,
    ngay_sua DATETIME NULL,
    trang_thai INT
);


--Bảng Kích cỡ

CREATE TABLE kich_co
(
    id BIGINT IDENTITY(1,1) PRIMARY KEY NOT NULL,
    ten NVARCHAR(100) DEFAULT NULL,
    ngay_tao DATETIME NULL,
    ngay_sua DATETIME NULL,
    trang_thai INT
);

--Bảng giảm giá
CREATE TABLE giam_gia
(
    id BIGINT IDENTITY(1,1) PRIMARY KEY NOT NULL,
    ten NVARCHAR(MAX) NULL,
    ngay_tao DATETIME NULL,
    ngay_sua DATETIME NULL,
    ngay_bat_dau DATETIME NULL,
    ngay_ket_thuc DATETIME NULL,
    muc_giam INT,
    trang_thai INT
);

--Bảng loại cổ

CREATE TABLE loai_co
(
    id BIGINT IDENTITY(1,1) PRIMARY KEY NOT NULL,
    ten NVARCHAR(100) DEFAULT NULL,
    ngay_tao DATETIME NULL,
    ngay_sua DATETIME NULL,
    trang_thai INT
);

--Bảng vật liệu

CREATE TABLE vat_lieu
(
    id BIGINT IDENTITY(1,1) PRIMARY KEY NOT NULL,
    ten NVARCHAR(100) DEFAULT NULL,
    ngay_tao DATETIME NULL,
    ngay_sua DATETIME NULL,
    trang_thai INT
);

--Bảng loại đế

CREATE TABLE loai_de
(
    id BIGINT IDENTITY(1,1) PRIMARY KEY NOT NULL,
    ten NVARCHAR(100) DEFAULT NULL,
    ngay_tao DATETIME NULL,
    ngay_sua DATETIME NULL,
    trang_thai INT
);

-- Bảng Sản phẩm
CREATE TABLE san_pham
(
    id BIGINT IDENTITY(1,1) PRIMARY KEY NOT NULL,
    ma AS 'PROD' + RIGHT('00000' + CAST(id AS VARCHAR(5)), 5) PERSISTED,
    ten NVARCHAR(255) NULL,
    mo_ta NVARCHAR(MAX) NULL,
    hinh_anh VARCHAR(MAX),
    trong_luong INT,
    be_mat_su_dung NVARCHAR(100),
    cong_nghe NVARCHAR(100),
    kieu_dang NVARCHAR(100),
    gia_thap_nhat MONEY NULL,
    gia_cao_nhat MONEY NULL,
    ngay_tao DATETIME NULL,
    ngay_sua DATETIME NULL,
    nguoi_tao NVARCHAR(100) NULL,
    trang_thai INT,


    id_giam_gia BIGINT
        REFERENCES giam_gia(id),
    id_thuong_hieu BIGINT
        REFERENCES thuong_hieu(id),
    id_the_loai BIGINT
        REFERENCES the_loai(id),

);

--Bảng chi tiết sản phẩm

CREATE TABLE chi_tiet_san_pham
(
    id BIGINT IDENTITY(1,1) PRIMARY KEY NOT NULL,
    ngay_tao DATETIME NULL,
    ngay_sua DATETIME NULL,
    hinh_anh VARCHAR(MAX),
    gia_nhap MONEY NULL,
    gia_ban MONEY NULL,
    so_luong INT,
    trang_thai INT DEFAULT 0,

    id_san_pham BIGINT
        REFERENCES san_pham(id),
    id_mau_sac BIGINT
        REFERENCES mau_sac(id),
    id_kich_co BIGINT
        REFERENCES kich_co(id),

);

--Bảng thao tác sản phẩm
CREATE TABLE thao_tac_san_pham
(
    id BIGINT IDENTITY(1,1) PRIMARY KEY NOT NULL,
    thao_tac NVARCHAR(MAX),
    ngay_tao DATETIME,
    trang_thai INT,

    id_san_pham BIGINT
        REFERENCES san_pham(id),
    id_nhan_vien BIGINT
        REFERENCES nhan_vien(id),

);


CREATE TABLE phuong_thuc_thanh_toan
(
    id BIGINT IDENTITY(1,1) PRIMARY KEY NOT NULL,
    ten NVARCHAR(MAX) NULL,
    ngay_tao DATETIME NULL,
    ngay_sua DATETIME NULL,
    trang_thai INT,
);


/*GIỎ HÀNG*/

--Bảng giỏ hàng

CREATE TABLE gio_hang
(
    id BIGINT IDENTITY(1,1) PRIMARY KEY NOT NULL,
    ma VARCHAR(max),
    ngay_tao DATETIME NULL,
    ngay_sua DATETIME NULL,
    ngay_thanh_toan DATETIME NULL,
    ghi_chu NVARCHAR(MAX),
    ten_nguoi_nhan NVARCHAR(MAX),
    sdt_nguoi_nhan NVARCHAR(MAX),
    dia_chi_nguoi_nhan NVARCHAR(MAX),
    phi_ship Money,
    tong_tien MONEY,
    tong_tien_san_pham_chua_giam NVARCHAR(MAX),
    tong_tien_giam NVARCHAR(MAX),
    trang_thai INT DEFAULT 0,

    id_phuong_thuc_thanh_toan BIGINT
        REFERENCES phuong_thuc_thanh_toan(id),
    id_khach_hang BIGINT
        REFERENCES khach_hang(id)
);

--Bảng chi tiết giỏ hàng
CREATE TABLE gio_hang_chi_tiet
(
    id BIGINT IDENTITY(1,1) PRIMARY KEY NOT NULL,
    so_luong INT,
    gia_hien_hanh MONEY,
    gia_da_giam MONEY,
    trang_thai INT,

    id_gio_hang BIGINT
        REFERENCES gio_hang(id),
    id_chi_tiet_san_pham BIGINT
        REFERENCES chi_tiet_san_pham(id),

);

/*HÓA ĐƠN*/
--Bảng hóa đơn
CREATE TABLE hoa_don
(
    id BIGINT IDENTITY(1,1) PRIMARY KEY NOT NULL,
    ma VARCHAR(max),
    ngay_tao DATETIME NULL,
    ngay_thanh_toan DATETIME NULL,
    ghi_chu NVARCHAR(MAX),
    ten_nguoi_nhan NVARCHAR(MAX),
    sdt_nguoi_nhan NVARCHAR(MAX),
    dia_chi_nguoi_nhan NVARCHAR(MAX),
    phi_ship Money,
    tong_tien MONEY,
    tong_tien_san_pham_chua_giam NVARCHAR(MAX),
    tong_tien_giam NVARCHAR(MAX),
    trang_thai INT DEFAULT 0,

    id_phuong_thuc_thanh_toan BIGINT
        REFERENCES phuong_thuc_thanh_toan(id),
    id_nhan_vien BIGINT
        REFERENCES nhan_vien(id),
    id_khach_hang BIGINT
        REFERENCES khach_hang(id)

);
--Bảng hóa đơn chi tiết
CREATE TABLE hoa_don_chi_tiet
(
    id BIGINT IDENTITY(1,1) PRIMARY KEY NOT NULL,
    so_luong INT,
    gia_hien_hanh MONEY,
    gia_da_giam MONEY,
    TrangThai INT,

    id_hoa_don BIGINT
        REFERENCES hoa_don(id),
    id_chi_tiet_san_pham BIGINT
        REFERENCES chi_tiet_san_pham(id),

);

--Bảng thao tác hóa đơn
CREATE TABLE thao_tac_hoa_don
(
    id BIGINT IDENTITY(1,1) PRIMARY KEY NOT NULL,
    thao_tac NVARCHAR(MAX),
    ngay_tao DATETIME,
    trang_thai INT,

    id_hoa_don BIGINT
        REFERENCES hoa_don(id),
    id_nhan_vien BIGINT
        REFERENCES nhan_vien(id),

);


/*voucher*/
--Bảng voucher
CREATE TABLE voucher
(
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    ma_voucher VARCHAR(50) NULL,
    ten_voucher NVARCHAR(50) NULL,
    ngay_bat_dau DATETIME NULL,
    ngay_ket_thuc DATETIME NULL,
    ngay_tao DATETIME NULL,
    ngay_sua DATETIME NULL,
    so_luong INT NULL,
    phan_tram_giam int NULL,
    giam_toi_da MONEY NULL,
    gia_tri_don_toi_thieu MONEY NULL,
    trang_thai INT,
);

--Bảng voucher hóa đơn
CREATE TABLE voucher_hoa_don
(
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    ngay_tao DATETIME NULL,
    ngay_sua DATETIME NULL,
    trang_thai INT,

    id_voucher BIGINT
        REFERENCES voucher(id),
    id_hoa_don BIGINT
        REFERENCES hoa_don(id),
);

--Bảng voucher gio hang
CREATE TABLE voucher_gio_hang
(
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    ngay_tao DATETIME NULL,
    ngay_sua DATETIME NULL,
    trang_thai INT,

    id_voucher BIGINT
        REFERENCES voucher(id),
    id_gio_hang BIGINT
        REFERENCES gio_hang(id),
);

--Bảng voucher khách hàng
CREATE TABLE voucher_khach_hang
(
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    ngay_tao DATETIME NULL,
    ngay_sua DATETIME NULL,
    so_luong INT,
    trang_thai INT,

    id_voucher BIGINT
        REFERENCES voucher(id),
    id_khach_hang BIGINT
        REFERENCES khach_hang(id)
);
