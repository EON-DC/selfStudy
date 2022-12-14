package jpabook.jpashop.domain.dto;

import jpabook.jpashop.domain.Member;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class MemberRegisterDto {

    private Long id;

    @NotNull
    @Length(min = 1, max = 14)
    private String name;

    @NotNull
    @Length(min = 4, max = 12)
    private String password;

    private String city;
    private String street;
    private String zipcode;

    public MemberRegisterDto(Member member) {
        this.id = member.getId();
        this.name = member.getName();
        this.password = member.getPassword();
        this.city = member.getAddress().getCity();
        this.street = member.getAddress().getStreet();
        this.zipcode = member.getAddress().getZipcode();
    }
}
