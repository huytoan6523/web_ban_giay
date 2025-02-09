package datn.be.mycode.repository;

import datn.be.mycode.entity.PhieuDoiTra;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PhieuDoiTraRepository extends JpaRepository<PhieuDoiTra, Long> {

    @Query("SELECT h FROM PhieuDoiTra h WHERE " +
            "(:keyWord IS null OR h.khachHang.soDienThoai LIKE :keyWord OR h.khachHang.hoTen LIKE %:keyWord% OR h.khachHang.ma LIKE :keyWord% OR h.ma LIKE :keyWord%) AND " +
            "(:theLoai IS null OR h.theLoai = :theLoai) AND " +
            "h.trangThai = :status " +
            "ORDER BY h.ngayTao DESC "
    )
    Page<PhieuDoiTra> findAllByKhachHangAndStatus(String keyWord, Integer status, Integer theLoai, Pageable pageable);

    Page<PhieuDoiTra> findAllByKhachHangIdOrderByNgayTaoDesc(Long idKhachHang, Pageable pageable);

    List<PhieuDoiTra> findByHoaDonCuId(Long idHoaDonCu);
}
