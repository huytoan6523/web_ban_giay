package datn.be.mycode.request.customRequest;

import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class TableThaoTacHoaDonRequest {

    private Integer page;

    private Integer pageSize;

    private Long idHoaDon;

    private Long idNhanVien;

    private Integer status;
}
