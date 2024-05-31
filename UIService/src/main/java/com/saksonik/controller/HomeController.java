package com.saksonik.controller;

import com.saksonik.client.HeadManagerClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import reactor.core.publisher.Mono;

@Slf4j
@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class HomeController {
    private final HeadManagerClient headManagerClient;

    @GetMapping
    public Mono<String> getHomePage(Model model, Authentication authentication) {
        if (authentication != null) {
            return headManagerClient.getUserfeed()
                    .doOnNext(userfeed -> model.addAttribute("userfeed", userfeed))
                    .thenReturn("home/home-page");
        }
        return Mono.just("home/home-page");
    }

    @GetMapping("/custom-login")
    public String getLoginForm() {
        return "auth/login-form";
    }

    @GetMapping("/custom-verify")
    public String getVerifyForm() {
        return "auth/verify-form";
    }

    @GetMapping("/custom-password")
    public String getSetPasswordForm() {
        return "auth/set-password-form";
    }
}
