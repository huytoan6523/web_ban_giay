package datn.be.mycode.request;

import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ThaoTacSanPhamRequest {
    private Long id;
    private String ThaoTac;
    private LocalDateTime NgaySua;
    private int trangThai;


}
