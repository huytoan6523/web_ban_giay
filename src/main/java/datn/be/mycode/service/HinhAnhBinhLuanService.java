package datn.be.mycode.service;

import datn.be.mycode.repository.HinhAnhBinhLuanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HinhAnhBinhLuanService {
    @Autowired
    HinhAnhBinhLuanRepository hinhAnhBinhLuanRepository;


}
