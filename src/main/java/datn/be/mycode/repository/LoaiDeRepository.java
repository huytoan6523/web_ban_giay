package datn.be.mycode.repository;

import datn.be.mycode.entity.KichCo;
import datn.be.mycode.entity.LoaiDe;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LoaiDeRepository extends JpaRepository<LoaiDe,Long> {


    Page<LoaiDe> findAllByTrangThai(int trangThai, Pageable pageable);

    @Query("SELECT u FROM LoaiDe u WHERE u.ten LIKE %:name%")
    Page<LoaiDe> searchByTen(String name, Pageable pageable);


    List<LoaiDe> findAllByTrangThai(int i);

    Optional<LoaiDe> findByTen(String ten);
}
