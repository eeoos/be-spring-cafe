package codesquad.codestagram.user.service;

import codesquad.codestagram.user.domain.User;
import codesquad.codestagram.user.dto.SignUpRequest;
import codesquad.codestagram.user.dto.UserUpdateRequest;
import codesquad.codestagram.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User join(User user) {

//        validatePassword(user.getPassword());
        //todo: 중복 회원에 대한 검증 필요
        return userRepository.save(user);
    }

    public User findUser(Long seq) {
        return userRepository.findBySeq(seq).orElseThrow(
                () -> new NoSuchElementException("존재하지 않는 회원입니다.")
        );
    }
    public List<User> findUsers() {
        return userRepository.findAll();
    }

    public boolean verifyPassword(Long userSeq, String inputPassword) {
        User user = findUser(userSeq);
        return inputPassword.equals(user.getPassword());
    }
    public User updateUser(User updatedUser) {
        User findUser = findUser(updatedUser.getSeq());

        if (updatedUser.getPassword().equals(findUser.getPassword())) {
            throw new IllegalArgumentException("새 비밀번호가 현재 비밀번호와 동일합니다. 다른 비밀번호를 입력해주세요.");
        }

        if (!updatedUser.getPassword().isEmpty()) {
            validatePassword(updatedUser.getPassword());
        }

        String newPassword = updatedUser.getPassword().isEmpty() ? findUser.getPassword() : updatedUser.getPassword();

        findUser.setPassword(newPassword);
        findUser.setName(updatedUser.getName());
        findUser.setEmail(updatedUser.getEmail());

        return userRepository.update(findUser);
    }

    private void validatePassword(String password) {
        validatePasswordLength(password);
        validatePasswordUpperCase(password);
        validatePasswordDigit(password);
        validatePasswordSpecialChar(password);
    }

    private void validatePasswordLength(String password) {
        if (password.length() < 8) {
            throw new IllegalArgumentException("비밀번호는 최소 8자 이상이어야 합니다.");
        }

        if (password.length() > 20) {
            throw new IllegalArgumentException("비밀번호는 최대 20자 이하여야 합니다.");
        }
    }

    private void validatePasswordUpperCase(String password) {
        if (!password.chars().anyMatch(Character::isUpperCase)) {
            throw new IllegalArgumentException("비밀번호는 최소 하나의 대문자를 포함해야 합니다.");
        }
    }

    private void validatePasswordDigit(String password) {
        if (!password.chars().anyMatch(Character::isDigit)) {
            throw new IllegalArgumentException("비밀번호는 최소 하나의 숫자를 포함해야 합니다.");
        }
    }

    private void validatePasswordSpecialChar(String password) {
        String specialChars = "!@#$%^&*()_-+=<>?/[]{}|";
        boolean hasSpecialChar = false;

        for (char c : password.toCharArray()) {
            if (specialChars.contains(String.valueOf(c))) {
                hasSpecialChar = true;
                break;
            }
        }

        if (!hasSpecialChar) {
            throw new IllegalArgumentException("비밀번호는 최소 하나의 특수 문자를 포함해야 합니다.");
        }
    }
}
