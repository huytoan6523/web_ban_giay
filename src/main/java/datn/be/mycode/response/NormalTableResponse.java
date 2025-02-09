package datn.be.mycode.response;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class NormalTableResponse<T> {

    private List<T> item;

    private int page;

    private int pageSize;

    private Long totalItem;
}