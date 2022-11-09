package jpabook.jpashop.controller;

import jpabook.jpashop.domain.Member;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(@SessionAttribute(name = LoginController.LOGIN_MEMBER, required = false)
                       Member loginMember, Model model) {

        // session data 가 없으면..
        if (loginMember == null) {
            return "index";
        }

        // session data 가 있으면..
        return "loginIndex";
    }


}
