package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.init.Init;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static jpabook.jpashop.domain.init.Init.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Test
    @DisplayName("회원 등록을 합니다.")
    public void Test01() {
        Member member = createMember();
        memberService.save(member);

        Member findMember = memberService.findOne(member.getId());
        assertThat(findMember).isEqualTo(member);
    }

    @Test
    @DisplayName("중복 회원 등록 시 IllegalStateException 예외를 발생시킵니다.")
    public void test02() throws Exception {
        //given
        Member member = createMember();
        Member member2 = new Member();
        member2.setName(member.getName());

        //when
        memberService.save(member);


        //then
        assertThatThrownBy(() -> {
            memberService.save(member2);
        }).isInstanceOf(IllegalStateException.class);
    }

    @Test
    @DisplayName("전체 회원을 조회할 수 있습니다.")
    public void test03() throws Exception {
        //given
        Member member1 = createMember();
        Member member2 = createMember();
        //when
        memberService.save(member1);
        memberService.save(member2);

        //then
        List<Member> members = memberService.findAll();
        assertThat(members.size()).isEqualTo(2);
        assertThat(members).containsExactlyInAnyOrder(member1, member2);

    }


}