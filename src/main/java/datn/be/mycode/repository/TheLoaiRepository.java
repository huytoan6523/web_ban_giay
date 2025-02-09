package datn.be.mycode.repository;

import datn.be.mycode.entity.TheLoai;
import datn.be.mycode.entity.ThuongHieu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TheLoaiRepository extends JpaRepository<TheLoai, Long> {

    //Page<TheLoai> findByTen(String name, Pageable pageable);

    Page<TheLoai> findAllByTrangThai(int trangThai, Pageable pageable);

    @Query("SELECT u FROM TheLoai u WHERE u.ten LIKE %:name%")
    Page<TheLoai> searchByTen(String name, Pageable pageable);
    List<TheLoai> findAll();

    Optional<TheLoai> findByTen(String ten);
}
