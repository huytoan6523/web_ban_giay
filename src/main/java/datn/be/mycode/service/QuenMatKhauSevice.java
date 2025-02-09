package datn.be.mycode.service;

import datn.be.mycode.entity.KhachHang;
import datn.be.mycode.repository.KhachHangRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuenMatKhauSevice {
    @Autowired
    private KhachHangRepository repository;
    public KhachHang getfindByEmail(String email){

        return repository.findByEmail(email);
}

    }
