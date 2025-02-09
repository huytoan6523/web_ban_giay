package datn.be.mycode.service;

import datn.be.mycode.entity.KhachHang;
import datn.be.mycode.entity.TheLoai;
import datn.be.mycode.entity.ThongBao;
import datn.be.mycode.repository.KhachHangRepository;
import datn.be.mycode.repository.ThongBaoRepository;
import datn.be.mycode.request.ThongBao.ThongBaoGet;
import datn.be.mycode.response.NormalTableResponse;
import datn.be.mycode.response.erroResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ThongBaoService {

    @Autowired
    private ThongBaoRepository thongBaoRepository;
    @Autowired
    private KhachHangRepository khachHangRepository;


    public NormalTableResponse<ThongBao> getThongBao(ThongBaoGet request) {

        if (request.getPage() ==  null) {
            request.setPage(1);
        }
        if (request.getPageSize() == null) {
            request.setPageSize(10);
        }


        Pageable pageable = PageRequest.of(request.getPage() - 1,request.getPageSize());

        var khachHang = khachHangRepository.findById(request.getIdKhachHang()).orElse(null);


        var thongBaoPage = thongBaoRepository.findAllByKhachHangIdOrderByNgayTaoAndDaDoc(khachHang.getId(), pageable);

        NormalTableResponse<ThongBao> thongBaoNormalTableResponse = new NormalTableResponse<>();
        thongBaoNormalTableResponse.setItem(thongBaoPage.getContent());
        thongBaoNormalTableResponse.setPage(request.getPage());
        thongBaoNormalTableResponse.setPageSize(request.getPageSize());
        thongBaoNormalTableResponse.setTotalItem(thongBaoPage.getTotalElements());

        return thongBaoNormalTableResponse;
    }

    public Integer getCountThongBao(Long idKhachHang) {
        var khachHang = khachHangRepository.findById(idKhachHang).orElse(null);
        if(khachHang == null) return 0;
        return thongBaoRepository.countByDaDocFalse(khachHang.getId());
    }


    public ResponseEntity<?> setThongBao(Long idKhachHang) {
        var khachHang = khachHangRepository.findById(idKhachHang).orElse(null);
        if(khachHang == null) return getErro("Khách hàng này không tồn tại");
        var thongBaoList = thongBaoRepository.findAllByKhachHangId(khachHang.getId());
        if (thongBaoList.isEmpty()) return getErro("Khách hàng không có thông báo nào");
//        if(thongBao.isDaDoc()) return getErro("Thông báo này đã được xem");
        var thongBaoChuaDoc = thongBaoList.stream().filter(thongBao -> thongBao.isDaDoc() == false).collect(Collectors.toList());
        thongBaoChuaDoc.forEach(thongBao -> {
            thongBao.setDaDoc(true);
            thongBaoRepository.save(thongBao);
        });


        return ResponseEntity.ok(thongBaoChuaDoc);
    }

    public ResponseEntity<?> setThongBaoTungCai(Long idThongBao) {
        var thongBao = thongBaoRepository.findById(idThongBao).orElse(null);
        if (thongBao == null) return getErro("Khách hàng không có thông báo nào");
        if(thongBao.isDaDoc()) return getErro("Thông báo này đã được xem");

        thongBao.setDaDoc(true);

        return ResponseEntity.ok(thongBaoRepository.save(thongBao));
    }

    private ResponseEntity<?> getErro(String s){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new erroResponse(s));
    }
}
