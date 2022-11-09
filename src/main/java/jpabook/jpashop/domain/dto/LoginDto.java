package jpabook.jpashop.domain.dto;

import jpabook.jpashop.domain.Member;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class LoginDto {

    private String name;

    private String password;


    public LoginDto(Member member) {
        this.name = member.getName();
        this.password = member.getPassword();
    }

}
