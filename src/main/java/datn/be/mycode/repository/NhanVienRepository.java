package datn.be.mycode.repository;

import datn.be.mycode.entity.NhanVien;
import datn.be.mycode.entity.loginTest;
import datn.be.mycode.request.customRequest.TableNhanVienRequest;
import datn.be.mycode.response.LoginResponse;
import datn.be.mycode.response.NhanVienResPonse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface NhanVienRepository extends JpaRepository<NhanVien,Long> {

        Page<NhanVien> findByHoTen(String name, Pageable pageable);

        Page<NhanVienResPonse> findAllByTrangThai(int trangThai, Pageable pageable);

    @Query("SELECT nv " +
            "FROM NhanVien nv " +
            "WHERE " +
            "(:#{#request.keyWord} IS null OR nv.hoTen LIKE %:#{#request.keyWord}%) AND " +
            "(:#{#request.idChucVu} IS null OR nv.chucVu.id = :#{#request.idChucVu}) AND " +
            "(:#{#request.status} IS NULL OR nv.trangThai = :#{#request.status})"
    )
    Page<NhanVien> searchAll(TableNhanVienRequest request, Pageable pageable);

    @Query("SELECT nv " +
            "FROM NhanVien nv " +
            "WHERE " +
            "(:#{#request.keyWord} IS null OR nv.soDienThoai LIKE %:#{#request.keyWord}%) AND " +
            "(:#{#request.idChucVu} IS null OR nv.chucVu.id = :#{#request.idChucVu}) AND " +
            "(:#{#request.status} IS NULL OR nv.trangThai = :#{#request.status})"
    )
    Page<NhanVien> searchAllBySDT(TableNhanVienRequest request, Pageable pageable);

    @Query("SELECT nv " +
            "FROM NhanVien nv " +
            "WHERE " +
            "(:#{#request.keyWord} IS null OR nv.taiKhoan LIKE %:#{#request.keyWord}%) AND " +
            "(:#{#request.idChucVu} IS null OR nv.chucVu.id = :#{#request.idChucVu}) AND " +
            "(:#{#request.status} IS NULL OR nv.trangThai = :#{#request.status})"
    )
    Page<NhanVien> searchAllByUsername(TableNhanVienRequest request, Pageable pageable);

    @Query("SELECT nv " +
            "FROM NhanVien nv " +
            "WHERE " +
            "(:#{#request.keyWord} IS null OR nv.email LIKE %:#{#request.keyWord}%) AND " +
            "(:#{#request.idChucVu} IS null OR nv.chucVu.id = :#{#request.idChucVu}) AND " +
            "(:#{#request.status} IS NULL OR nv.trangThai = :#{#request.status})"
    )
    Page<NhanVien> searchAllByEmail(TableNhanVienRequest request, Pageable pageable);

    @Query("SELECT nv " +
            "FROM NhanVien nv " +
            "WHERE " +
            "(nv.taiKhoan LIKE :#{#request.username}) AND " +
            "(nv.matKhau = :#{#request.pass}) AND " +
            "(nv.trangThai = 1)"
    )
    NhanVien getAccount(loginTest request);
}
