package codesquad.codestagram.user.dto;

import codesquad.codestagram.user.domain.User;

public record SignUpRequest(
        String userId,
        String password,
        String name,
        String email
) {
    public User toEntity(){
        return new User(userId, password, name, email);
    }
}
