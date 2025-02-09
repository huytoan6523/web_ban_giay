package datn.be.mycode.repository;

import datn.be.mycode.entity.GiamGia;

import datn.be.mycode.entity.KichCo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface GiamGiaRepository extends JpaRepository<GiamGia,Long> {
    Page<GiamGia> findByTen(String name, Pageable pageable);

    Page<GiamGia> findAllByTrangThai(int trangThai, Pageable pageable);

    List<GiamGia> findAllByTrangThai(int trangThai);

    List<GiamGia> findAllByNgayBatDauBeforeAndNgayKetThucAfter(LocalDateTime ngayBatDau, LocalDateTime ngayHienTai);

    Optional<GiamGia> findByTen(String ten);
}
