package datn.be.mycode.request.customRequest;

import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class TableSanPhamChiTietRequest {

    private Integer page;

    private Integer pageSize;

    private String keyWord;

    private Integer status;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private Long sanPham;

    private Long mauSac;

    private Long kichCo;

    private Integer sort; // 0 la binh thuong, 1 la xap xep theo so luong < 10
}
