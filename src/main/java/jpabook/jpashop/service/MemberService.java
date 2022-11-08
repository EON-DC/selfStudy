package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    /**
     * 개요 : 전체 회원에 대한 CRUD,
     */

    public Long save(Member member) {
        validateDuplicateMemberName(member);
        memberRepository.save(member);
        return member.getId();
    }

    public Member findOne(Long memberId) {
        Optional<Member> findMember = memberRepository.findById(memberId);
        if (findMember.isPresent()) {
            return findMember.get();
        }
        throw new IllegalStateException("회원 정보를 찾을 수 없습니다.");
    }

    public List<Member> findAll() {
        return memberRepository.findAll();
    }

    public void delete(Member member){
        memberRepository.delete(member);
    }

    private void validateDuplicateMemberName(Member member) {
        Optional<Member> findMember = memberRepository.findByName(member.getName());
        if (findMember.isPresent()) {
            throw new IllegalStateException("이미 존재하는 회원 이름입니다."); // 이미 존재하는 회원일 경우에 예외를 발생시킨다.
        }
    }




}
