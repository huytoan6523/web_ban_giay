package datn.be.mycode.request.customRequest;

import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class TableHoaDonRequest {

    private Integer page;

    private Integer pageSize;

    private String keyWord;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private Long idKhachHang;

    private Long idNhanVien;

    private Integer status;
}
