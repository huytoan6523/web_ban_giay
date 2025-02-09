package datn.be.mycode.request;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class DanhGiaSanPhamRequest {

    private Long id;
    private int sao;
    private String binhLuan;
    private List<String> listHinhAnh;

}
