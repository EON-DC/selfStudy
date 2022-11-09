package jpabook.jpashop.controller;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.dto.MemberDto;
import jpabook.jpashop.domain.dto.MemberRegisterDto;
import jpabook.jpashop.service.MemberService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    // 회원 등록 폼 제공
    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("memberRegisterDto", new MemberRegisterDto());
        return "members/addForm";
    }

    @GetMapping("/list")
    public String listMember(Model model){
        List<Member> memberList = memberService.findAll();
        List<MemberDto> membersDto = new ArrayList<>();
        for (Member member : memberList) {
            membersDto.add(new MemberDto(member));
        }
        model.addAttribute("membersDto", membersDto);
        return "members/memberList";
    }

    @PostMapping("/add")
    public String save(@Validated @ModelAttribute("memberRegisterDto") MemberRegisterDto dto,
                       BindingResult result,
                       Model model,
                       RedirectAttributes redirectAttributes) {
        // binding result 에서 오류가 발견되면, 다시 입력폼으로 보내기
        // 에러가 없으면 성공로직으로 넘어간다

        if (result.hasErrors()) {
            model.addAttribute("memberRegisterDto", dto);
            return "/members/add";
        }

        // == 성공 로직 == //
        // 객체 생성, 이름 설정
        Member member = new Member();
        member.setName(dto.getName());

        // 비밀번호 설정
        member.setPassword(dto.getPassword());
        // 주소 설정
        Address address = new Address(dto.getCity(), dto.getStreet(), dto.getZipcode());
        member.setAddress(address);

        // 서비스 등록
        memberService.save(member);
        redirectAttributes.addAttribute("memberId", member.getId());

        // TODO: 등록이 완료되었으면 세션 생성


        return "redirect:/members/{memberId}";
    }

    // 회원 조회
    @GetMapping("/{memberId}")
    public String queryMember(@PathVariable(name = "memberId") String memberId, Model model) {
        Member findMember = memberService.findOne(Long.valueOf(memberId));
        MemberRegisterDto dto = new MemberRegisterDto(findMember);
        model.addAttribute("memberRegisterDto", dto);
        model.addAttribute("memberId", memberId);
        return "members/member";
    }

    // 회원 등록 수정폼 제공
    @GetMapping("/edit/{memberId}")
    public String editForm(@PathVariable String memberId, Model model) {
        Member findMember = memberService.findOne(Long.valueOf(memberId));
        MemberRegisterDto dto = new MemberRegisterDto(findMember);
        model.addAttribute("memberRegisterDto", dto);
        return "members/editForm";
    }

    // 회원 등록 수정폼 제공, 리포지토리 조회 & 변경
    @PostMapping("/edit/{memberId}")
    public String editMember(@PathVariable String memberId,
                             @Valid MemberRegisterDto dto,
                             BindingResult result,
                             Model model) {
        if (result.hasErrors()) {
            model.addAttribute("memberRegisterDto", dto);
            return "members/editForm";
        }

        // 성공 로직
        memberService.update(Long.valueOf(memberId), dto);
        return "redirect:/";

    }
}
