package datn.be.mycode.repository;

import datn.be.mycode.entity.ChiPhieuGiamGiaSuKien;
import datn.be.mycode.entity.KhachHang;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface KhachHangRepository extends JpaRepository<KhachHang, Long> {

//    @Query("SELECT u FROM KhachHang u WHERE u.trangThai = :status")
    Page<KhachHang> findAllByTrangThai(Integer trangThai, Pageable pageable);

    @Query("SELECT u FROM KhachHang u WHERE u.hoTen LIKE %:name%")
    Page<KhachHang> searchByTen(String name, Pageable pageable);

    @Query("SELECT u FROM KhachHang u WHERE u.soDienThoai LIKE %:soDienThoai%")
    Page<KhachHang> searchBySoDienThoai(String soDienThoai, Pageable pageable);

    @Query("SELECT u FROM KhachHang u WHERE u.taiKhoan LIKE %:taiKhoan%")
    Page<KhachHang> searchByTaiKhoan(String taiKhoan, Pageable pageable);

    @Query("SELECT u FROM KhachHang u WHERE u.email LIKE %:email%")
    Page<KhachHang> searchByEmail(String email, Pageable pageable);

    @Query("SELECT u FROM KhachHang u WHERE u.id = :id")
    Optional<KhachHang> searchById(@Param("id") Long id);
//    @Query("SELECT u FROM User u WHERE u.email = :email")
//    Optional<KhachHang> findByEmail(@Param("email") String email);

    Optional<KhachHang> findByTaiKhoanAndMatKhauAndTrangThai(String taiKhoan, String matKhau,int trangThai);
    KhachHang  findByEmail(String email);
    boolean existsByEmail(String email);
    boolean existsByTaiKhoan(String taiKhoan);
}
