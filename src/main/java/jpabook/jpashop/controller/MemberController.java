package jpabook.jpashop.controller;

import jpabook.jpashop.domain.dto.MemberRegisterDto;
import jpabook.jpashop.service.MemberService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;

    // 회원 등록 폼 제공
    @GetMapping("/add")
    public String addForm(Model model){
        model.addAttribute("memberRegisterDto", new MemberRegisterDto());
        return "member/addForm";
    }

    @PostMapping("/add")
    public String save(@ModelAttribute("memberRegisterDto") MemberRegisterDto dto, BindingResult result, Model model){
        return "member/addForm";
    }

    // 회원 등록 폼 받고, 리포지토리 등록
    // 회원 등록 수정폼 제공
    // 회원 등록 수정폼 제공, 리포지토리 조회 & 변경


}
