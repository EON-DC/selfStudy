package jpabook.jpashop.domain;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "member_id")
    private Long id;

    private String name;
    private String password;

    @Embedded
    private Address address;

    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>();

    //== 비즈니스 메서드 ==//

    // 회원 정보 수정
    public void update(String name, String password, String city, String street, String zipcode) {
        this.name = name;
        this.password = password;
        this.address = new Address(city, street, zipcode);
    }

}
