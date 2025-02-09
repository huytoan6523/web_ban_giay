package datn.be.mycode.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class AupdateDB {
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void updateColumnTypes() {

        entityManager.createNativeQuery("ALTER TABLE doan_chat_nhan_vien ALTER COLUMN hinh_anh NVARCHAR(max)").executeUpdate();
//        dia chi
        entityManager.createNativeQuery("ALTER TABLE dia_chi ALTER COLUMN tinh_thanh NVARCHAR(50)").executeUpdate();
        entityManager.createNativeQuery("ALTER TABLE dia_chi ALTER COLUMN quan_huyen NVARCHAR(50)").executeUpdate();
        entityManager.createNativeQuery("ALTER TABLE dia_chi ALTER COLUMN phuong_xa NVARCHAR(50)").executeUpdate();
        entityManager.createNativeQuery("ALTER TABLE dia_chi ALTER COLUMN ghi_chu NVARCHAR(max)").executeUpdate();

//        chuc vu
        entityManager.createNativeQuery("ALTER TABLE chuc_vu ALTER COLUMN ten NVARCHAR(150)").executeUpdate();

//        doan chat
        entityManager.createNativeQuery("ALTER TABLE doan_chat ALTER COLUMN tin_nhan_cuoi NVARCHAR(max)").executeUpdate();

//        giam gia
        entityManager.createNativeQuery("ALTER TABLE giam_gia ALTER COLUMN ten NVARCHAR(100)").executeUpdate();

//        gio hang
        entityManager.createNativeQuery("ALTER TABLE gio_hang ALTER COLUMN ghi_chu NVARCHAR(max)").executeUpdate();
        entityManager.createNativeQuery("ALTER TABLE gio_hang ALTER COLUMN ten_nguoi_nhan NVARCHAR(50)").executeUpdate();
        entityManager.createNativeQuery("ALTER TABLE gio_hang ALTER COLUMN tinh_thanh NVARCHAR(50)").executeUpdate();
        entityManager.createNativeQuery("ALTER TABLE gio_hang ALTER COLUMN quan_huyen NVARCHAR(50)").executeUpdate();
        entityManager.createNativeQuery("ALTER TABLE gio_hang ALTER COLUMN phuong_xa NVARCHAR(50)").executeUpdate();

//        hoa don
        entityManager.createNativeQuery("ALTER TABLE hoa_don ALTER COLUMN ghi_chu NVARCHAR(max)").executeUpdate();
        entityManager.createNativeQuery("ALTER TABLE hoa_don ALTER COLUMN ten_nguoi_nhan NVARCHAR(50)").executeUpdate();
        entityManager.createNativeQuery("ALTER TABLE hoa_don ALTER COLUMN tinh_thanh NVARCHAR(50)").executeUpdate();
        entityManager.createNativeQuery("ALTER TABLE hoa_don ALTER COLUMN quan_huyen NVARCHAR(50)").executeUpdate();
        entityManager.createNativeQuery("ALTER TABLE hoa_don ALTER COLUMN phuong_xa NVARCHAR(50)").executeUpdate();

//        khach hang
        entityManager.createNativeQuery("ALTER TABLE khach_hang ALTER COLUMN ho_ten NVARCHAR(150)").executeUpdate();
        entityManager.createNativeQuery("ALTER TABLE khach_hang ALTER COLUMN tai_Khoan NVARCHAR(50)").executeUpdate();

//        kich co
        entityManager.createNativeQuery("ALTER TABLE kich_co ALTER COLUMN ten NVARCHAR(50)").executeUpdate();

//        loai co
        entityManager.createNativeQuery("ALTER TABLE loai_co ALTER COLUMN ten NVARCHAR(50)").executeUpdate();

//        loai de
        entityManager.createNativeQuery("ALTER TABLE loai_de ALTER COLUMN ten NVARCHAR(50)").executeUpdate();

//        mau sac
        entityManager.createNativeQuery("ALTER TABLE mau_sac ALTER COLUMN ten NVARCHAR(50)").executeUpdate();

//        nhan vien
        entityManager.createNativeQuery("ALTER TABLE nhan_vien ALTER COLUMN ho_ten NVARCHAR(50)").executeUpdate();
        entityManager.createNativeQuery("ALTER TABLE nhan_vien ALTER COLUMN tai_Khoan NVARCHAR(50)").executeUpdate();

//        phuong thuc thanh toan
        entityManager.createNativeQuery("ALTER TABLE phuong_thuc_thanh_toan ALTER COLUMN ten NVARCHAR(50)").executeUpdate();

//        san pham
        entityManager.createNativeQuery("ALTER TABLE san_pham ALTER COLUMN ten NVARCHAR(50)").executeUpdate();
        entityManager.createNativeQuery("ALTER TABLE san_pham ALTER COLUMN mo_ta NVARCHAR(max)").executeUpdate();
        entityManager.createNativeQuery("ALTER TABLE san_pham ALTER COLUMN be_mat_su_dung NVARCHAR(50)").executeUpdate();
        entityManager.createNativeQuery("ALTER TABLE san_pham ALTER COLUMN cong_nghe NVARCHAR(50)").executeUpdate();
        entityManager.createNativeQuery("ALTER TABLE san_pham ALTER COLUMN kieu_dang NVARCHAR(50)").executeUpdate();

//        su kien
//        entityManager.createNativeQuery("EXEC sp_rename 'su-kien' , 'su_kien'").executeUpdate();
        entityManager.createNativeQuery("ALTER TABLE su_kien ALTER COLUMN ten_su_kien NVARCHAR(50)").executeUpdate();
        entityManager.createNativeQuery("ALTER TABLE su_kien ALTER COLUMN mo_ta NVARCHAR(max)").executeUpdate();

//        thao tac hoa don
        entityManager.createNativeQuery("ALTER TABLE thao_tac_hoa_don ALTER COLUMN thao_tac NVARCHAR(50)").executeUpdate();

//        the loai
        entityManager.createNativeQuery("ALTER TABLE the_loai ALTER COLUMN ten NVARCHAR(50)").executeUpdate();

//        thong bao
        entityManager.createNativeQuery("ALTER TABLE thong_bao ALTER COLUMN noi_dung NVARCHAR(max)").executeUpdate();

//        thuong hieu
        entityManager.createNativeQuery("ALTER TABLE thuong_hieu ALTER COLUMN ten NVARCHAR(50)").executeUpdate();

//        vat lieu
        entityManager.createNativeQuery("ALTER TABLE vat_lieu ALTER COLUMN ten NVARCHAR(50)").executeUpdate();

//        voucher
        entityManager.createNativeQuery("ALTER TABLE voucher ALTER COLUMN ten_voucher NVARCHAR(50)").executeUpdate();

    }
}
