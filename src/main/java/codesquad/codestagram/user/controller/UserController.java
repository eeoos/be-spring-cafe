package codesquad.codestagram.user.controller;

import codesquad.codestagram.user.domain.User;
import codesquad.codestagram.user.dto.SignUpRequest;
import codesquad.codestagram.user.dto.UserUpdateRequest;
import codesquad.codestagram.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
//@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/users/signUp")
    public String signUp(@ModelAttribute SignUpRequest request,
                         RedirectAttributes redirectAttributes) { // @ModelAttribute 공부,
        try {
            userService.join(request.toEntity());
            return "redirect:/users";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());

            return "redirect:/users/join";
        }
    }

    @GetMapping("/users")
    public String showUserList(Model model) {
        List<User> users = userService.findUsers();
        model.addAttribute("users", users);
        return "user/list";
    }

    @GetMapping("/users/{userSeq}")
    public String showUserProfile(@PathVariable Long userSeq, Model model) {
        User user = userService.findUser(userSeq);
        model.addAttribute("user", user);
        return "user/profile";
    }

    @GetMapping("/users/{userSeq}/verify")
    public String verifyPasswordForm(@PathVariable Long userSeq, Model model) {
        userService.findUser(userSeq);

        model.addAttribute("seq", userSeq);
        return "user/passwordVerifyForm";
    }

    @PostMapping("/users/{userSeq}/verify-password")
    public String verifyPassword(@PathVariable Long userSeq,
                                 @RequestParam String password,
                                 RedirectAttributes redirectAttributes) {

        try {
            boolean isVerified = userService.verifyPassword(userSeq, password);

            if (isVerified) {
                return "redirect:/users/" + userSeq + "/form";
            }
            redirectAttributes.addFlashAttribute("errorMessage", "비밀번호가 일치하지 않습니다.");
            return "redirect:/users/" + userSeq + "/verify";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/users/" + userSeq + "/verify";
        }

    }

    @GetMapping("/users/{userSeq}/form")
    public String updateUserProfileForm(@PathVariable Long userSeq, Model model) {
        User user = userService.findUser(userSeq);
        model.addAttribute("user", user);
        return "user/updateForm";
    }


    @PutMapping("/users/{userSeq}")
    public String update(@PathVariable Long userSeq,
                         @ModelAttribute UserUpdateRequest request,
                         RedirectAttributes redirectAttributes) {
        try {
            userService.updateUser(request.toEntity(userSeq));
            return "redirect:/users";
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/users/" + userSeq + "/form";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "회원정보 수정 중 오류가 발생했습니다: " + e.getMessage());
            return "redirect:/users/" + userSeq + "/form";
        }
    }
}
