package datn.be.mycode.repository;

import datn.be.mycode.entity.KichCo;
import datn.be.mycode.entity.VatLieu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VatLieuRepository extends JpaRepository<VatLieu,Long> {

//    Page<ThuongHieu> findByTen(String name, Pageable pageable);
//
    Page<VatLieu> findAllByTrangThai(int trangThai, Pageable pageable);

    @Query("SELECT u FROM VatLieu u WHERE u.ten LIKE %:name%")
    Page<VatLieu> searchByTen(String name, Pageable pageable);


    List<VatLieu> findAllByTrangThai(int i);

    Optional<VatLieu> findByTen(String ten);
}
