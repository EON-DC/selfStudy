package jpabook.jpashop.domain.dto;

import lombok.Data;

@Data
public class MemberRegisterDto {

    private String name;
    private String password;
    private String city;
    private String street;
    private String zipcode;
}
