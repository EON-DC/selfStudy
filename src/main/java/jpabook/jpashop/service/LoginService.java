package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final MemberRepository memberRepository;

    // 로그인 기능
    public Member login(String name, String password) {
        Optional<Member> findMember = memberRepository.findByName(name);
        if (findMember.isPresent()) {
            if (findMember.get().getPassword().equals(password)) {
                return findMember.get();
            } else {
                return null;
            }
        }
        return null;
    }


    // 로그아웃은 해당 세션을 controller에서 invalidate 한다

}
