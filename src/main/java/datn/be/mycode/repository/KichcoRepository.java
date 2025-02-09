package datn.be.mycode.repository;

import datn.be.mycode.entity.KichCo;
import datn.be.mycode.entity.TheLoai;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface KichcoRepository extends JpaRepository<KichCo, Long>  {

     Page<KichCo> findAllByTrangThai(int trangThai, Pageable pageable);

     @Query("SELECT u FROM KichCo u WHERE u.ten LIKE %:name%")
     Page<KichCo> searchByTen(String name, Pageable pageable);

     Optional<KichCo> findByTen(String ten);
}
