package datn.be.mycode.response;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class LoginResponse {

    private String username;

    private String role;
}
