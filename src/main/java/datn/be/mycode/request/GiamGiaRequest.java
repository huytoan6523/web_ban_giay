package datn.be.mycode.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class GiamGiaRequest {
    private Long id;
    private String ten;
    private LocalDateTime ngayBatDau;
    private LocalDateTime ngayKetThuc;
    private int mucGiam;
    private int trangThai;
}
