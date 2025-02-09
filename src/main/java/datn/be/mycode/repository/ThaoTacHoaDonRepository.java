package datn.be.mycode.repository;


import datn.be.mycode.entity.ThaoTacHoaDon;
import datn.be.mycode.response.ThaoTacHoaDonResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;


@Repository
public interface ThaoTacHoaDonRepository extends JpaRepository<ThaoTacHoaDon, Long> {

    @Query("SELECT new datn.be.mycode.response.ThaoTacHoaDonResponse(tthd.id, tthd.thaoTac, tthd.ngayTao, tthd.trangThai, tthd.hoaDon, tthd.nhanVien)" +
            "FROM ThaoTacHoaDon tthd " +
            "WHERE " +
            "(:idHoaDon IS NULL OR tthd.hoaDon.id = :idHoaDon) AND " +
            "(:idNhanVien IS NULL OR tthd.nhanVien.id = :idNhanVien)"
    )
    Page<ThaoTacHoaDonResponse> searchAll(Long idHoaDon, Long idNhanVien, Pageable pageable);

    List<ThaoTacHoaDon> findAllByHoaDonId(Long idHoaDon);

}
