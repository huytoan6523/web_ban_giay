package datn.be.mycode.request.customRequest;

import datn.be.mycode.entity.GiamGia;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class TableSanPhamRequest {

    private Integer page;

    private Integer pageSize;

    private String keyWord;

    private Integer status;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private Long giamGia,thuongHieu, theLoai, loaiCo, vatLieu, loaiDe;

    private Integer sort; // 0 la binh thuong, 1 la xap xep theo so luong < 10
}
