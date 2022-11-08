package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Test
    @DisplayName("생성한 회원을 리포지토리에서 id로 조회할 수 있어야합니다.")
    void Test1() {
        Member member = createMember();

        memberRepository.save(member);

        Member findMember
                = memberRepository.findById(member.getId()).get();
        assertThat(findMember).isEqualTo(member);

    }

    @Test
    @DisplayName("없는 아이디를 입력할 경우 exception을 발생시킨다.")
    public void test02() throws Exception {
        //given
        Member member = createMember();
        memberRepository.save(member);
        //when & then
        assertThatThrownBy(() -> memberRepository.findById(-1L).get())
                .isInstanceOf(NoSuchElementException.class);

    }

    @Test
    @DisplayName("이름으로 조회가 가능합니다.")
    public void test03() throws Exception {
        //given
        Member member = createMember();
        memberRepository.save(member);
        //when
        Optional<Member> findMember
                = memberRepository.findByName(member.getName());

        //then
        assertThat(findMember.get()).isEqualTo(member);
    }

    @Test
    @DisplayName("없는 이름을 조회할 경우 NoSuchElementException 반환합니다..")
    public void test04() {
        //given
        Member member = createMember();
        memberRepository.save(member);
        //when
        assertThatThrownBy(() -> {
            memberRepository.findByName(null).get();
        }).isInstanceOf(NoSuchElementException.class);

    }

    private Member createMember() {
        Member member = new Member();
        member.setName("park");

        Address address = new Address("seoul", "taeheran-ro", "22203");
        member.setAddress(address);

        return member;
    }
}