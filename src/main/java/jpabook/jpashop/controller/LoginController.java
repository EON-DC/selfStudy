package jpabook.jpashop.controller;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.dto.LoginDto;
import jpabook.jpashop.service.LoginService;
import jpabook.jpashop.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Collection;
import java.util.Enumeration;

@Controller
@RequestMapping("/login")
@RequiredArgsConstructor
@Slf4j
public class LoginController {

    public static final String LOGIN_MEMBER = "loginMember";

    private final MemberService memberService;
    private final LoginService loginService;


    @GetMapping
    public String loginForm(Model model) {
        model.addAttribute("loginDto", new LoginDto());
        return "login/loginForm";
    }

    @PostMapping
    public String login(@Valid LoginDto dto, BindingResult result, Model model
    , HttpServletRequest request) {
        // loginMember 조회되는지 확인
        Member loginMember = loginService.login(dto.getName(), dto.getPassword());
        if (loginMember == null) {
            // 조회 실패된 경우, global error 포함
            result.reject("loginFail", "아이디 또는 비밀번호가 틀렸습니다.");
        }

        if (result.hasErrors()) {
            model.addAttribute("loginDto", dto);
            return "login/loginForm";
        }

        // 로그인이 성공했다면
        HttpSession session = request.getSession();
        session.setAttribute(LOGIN_MEMBER, loginMember);

        return "redirect:/";
    }

}
