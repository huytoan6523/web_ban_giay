package datn.be.mycode.repository;

import datn.be.mycode.entity.DoanChatKhachHang;
import datn.be.mycode.entity.DoanChatNhanVien;
import datn.be.mycode.entity.HopThoaiNhanVien;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DoanChatKhachHangRepository extends JpaRepository<DoanChatKhachHang, Long> {

    @Query(value = "SELECT d FROM DoanChatKhachHang d " +
            "WHERE d.thoiGian = (SELECT MAX(d2.thoiGian) FROM DoanChatKhachHang d2 WHERE d2.hopThoaiKhachHang.id = :idHopThoaiKhachHang)")
    Optional<DoanChatKhachHang> findNewestMessageByHopThoai(Long idHopThoaiKhachHang);

    Page<DoanChatKhachHang> findAllByHopThoaiKhachHangIdOrderByThoiGianDesc(Long idHopThoai, Pageable pageable);
}
