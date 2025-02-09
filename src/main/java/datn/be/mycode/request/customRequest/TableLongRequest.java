package datn.be.mycode.request.customRequest;

import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class TableLongRequest {
    private Integer page;

    private Integer pageSize;

    private Long keyWord;

    private Integer status;
}
