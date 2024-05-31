package com.saksonik.controller;

import com.saksonik.client.HeadManagerClient;
import com.saksonik.dto.classes.ClassDTO;
import com.saksonik.dto.user.UserDTO;
import com.saksonik.dto.user.UserRegistration;
import com.saksonik.dto.userfeed.UserfeedDTO;
import com.saksonik.service.KeycloakUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.thymeleaf.context.LazyContextVariable;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final HeadManagerClient headManagerClient;
    private final KeycloakUserService keycloakUserService;

    @GetMapping("/users/create")
    public String getCreateUserPage(@ModelAttribute("userfeed") Mono<UserfeedDTO> userfeed,
                                    @ModelAttribute("credentials") UserRegistration credentials,
                                    Model model) {
        model.addAttribute("userfeed", userfeed.block());
        model.addAttribute("credentials", credentials);

        model.addAttribute("classes", new LazyContextVariable<List<ClassDTO>>() {
            @Override
            protected List<ClassDTO> loadValue() {
                return headManagerClient.getAllClasses()
                        .collectList()
                        .block();
            }
        });

        model.addAttribute("students", new LazyContextVariable<List<UserDTO>>() {
            @Override
            protected List<UserDTO> loadValue() {
                return headManagerClient.findAllStudents()
                        .collectList()
                        .block();
            }
        });

        return "admin/users/create";
    }

    @PostMapping("/users/create")
    public String createUser(@Valid @ModelAttribute("credentials") UserRegistration credentials,
                             BindingResult bindingResult,
                             Model model) {
        log.info("Creating user {}", credentials);

        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getAllErrors()
                    .stream()
                    .map(ObjectError::getDefaultMessage)
                    .toList());
            model.addAttribute("credentials", credentials);

            return "admin/users/create";
        } else {
            keycloakUserService.createUser(credentials);
            return "redirect:/";
//            return headManagerClient.createMeeting(meeting)
//                    .thenReturn("redirect:/meeting-schedule/list/%s".formatted(classId));
        }

    }

//    @GetMapping("/users/edit")
//    public String getEditUserPage(Model model) {
//        new LazyContextVariable<>()
//    }
//
//
//
//    @GetMapping("/classes/create")
//    public String getCreateClassesPage(Model model) {
//        new LazyContextVariable<>()
//    }
//
//    @GetMapping("/classes/edit")
//    public String getEditClassesPage(Model model) {
//        new LazyContextVariable<>()
//    }
//
//
//
//    @GetMapping("/lesson-timetable/create")
//    public String getCreateLessonTimetablePage(Model model) {
//        new LazyContextVariable<>()
//    }
//
//    @GetMapping("/lesson-timetable/edit")
//    public String getEditLessonTimetablePage(Model model) {
//        new LazyContextVariable<>()
//    }
//
//
//
//    @GetMapping("/call-schedule/create")
//    public String getCreateCallSchedulePage(Model model) {
//        new LazyContextVariable<>()
//    }
//
//    @GetMapping("/call-schedule/edit")
//    public String getEditCallSchedulePage(Model model) {
//        new LazyContextVariable<>()
//    }

    @ModelAttribute(name = "userfeed", binding = false)
    public Mono<UserfeedDTO> loadUserfeed() {
        return headManagerClient.getUserfeed();
    }
}
