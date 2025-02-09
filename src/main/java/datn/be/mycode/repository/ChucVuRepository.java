package datn.be.mycode.repository;

import datn.be.mycode.entity.ChucVu;
import datn.be.mycode.entity.TheLoai;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChucVuRepository extends JpaRepository<ChucVu, Long> {

    Page<ChucVu> findAllByTrangThai(int trangThai, Pageable pageable);

    @Query("SELECT u FROM ChucVu u WHERE u.ten LIKE %:name%")
    Page<ChucVu> searchByTen(String name, Pageable pageable);

    Optional<ChucVu> findByTen(String ten);
}
