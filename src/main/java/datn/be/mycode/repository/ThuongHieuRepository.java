package datn.be.mycode.repository;

import datn.be.mycode.entity.ChucVu;
import datn.be.mycode.entity.ThuongHieu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ThuongHieuRepository extends JpaRepository<ThuongHieu,Long> {

//    Page<ThuongHieu> findByTen(String name, Pageable pageable);

    Page<ThuongHieu> findAllByTrangThai(int trangThai, Pageable pageable);

    List<ThuongHieu> findAllByTrangThai(int trangThai);

    @Query("SELECT u FROM ThuongHieu u WHERE u.ten LIKE %:name%")
    Page<ThuongHieu> searchByTen(String name, Pageable pageable);
    List<ThuongHieu> findAll();

    Optional<ThuongHieu> findByTen(String ten);

}
