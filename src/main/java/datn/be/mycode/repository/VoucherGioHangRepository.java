package datn.be.mycode.repository;

import datn.be.mycode.entity.VoucherGioHang;
import datn.be.mycode.entity.VoucherKhachHang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VoucherGioHangRepository extends JpaRepository<VoucherGioHang,Long> {

    List<VoucherGioHang> findAllByGioHangId(Long idGioHang);
}
