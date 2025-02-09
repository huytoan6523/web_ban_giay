package datn.be.mycode.repository;

import datn.be.mycode.entity.KichCo;
import datn.be.mycode.entity.MauSac;
import datn.be.mycode.entity.ThuongHieu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MauSacRepository extends JpaRepository<MauSac, Long> {

//    Page<MauSac> findByTen(String name, Pageable pageable);

    Page<MauSac> findAllByTrangThai(int trangThai, Pageable pageable);

    @Query("SELECT u FROM MauSac u WHERE u.ten LIKE %:name%")
    Page<MauSac> searchByTen(String name, Pageable pageable);

    Optional<MauSac> findByTen(String ten);
}
