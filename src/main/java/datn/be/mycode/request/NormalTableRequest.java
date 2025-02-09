package datn.be.mycode.request;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class NormalTableRequest {

    private Integer page;

    private Integer pageSize;

    private String keyWord;

    private Integer status;


}
