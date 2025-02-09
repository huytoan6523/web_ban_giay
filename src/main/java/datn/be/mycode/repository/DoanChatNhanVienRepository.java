package datn.be.mycode.repository;

import datn.be.mycode.entity.DoanChatNhanVien;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DoanChatNhanVienRepository extends JpaRepository<DoanChatNhanVien, Long> {

    @Query(value = "SELECT d FROM DoanChatNhanVien d " +
            "WHERE d.thoiGian = (SELECT MAX(d2.thoiGian) FROM DoanChatNhanVien d2 WHERE d2.hopThoaiNhanVien.id = :idHopThoai)")
    Optional<DoanChatNhanVien> findNewestMessageByHopThoai(Long idHopThoai);

    Page<DoanChatNhanVien> findAllByHopThoaiNhanVienIdOrderByThoiGianDesc(Long idHopThoai, Pageable pageable);
}
