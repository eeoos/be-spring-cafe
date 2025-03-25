package codesquad.codestagram.user.dto;

import codesquad.codestagram.user.domain.User;

public record UserUpdateRequest(
        String userId,
        String password,
        String name,
        String email
) {
    public User toEntity(Long userSeq) {
        User user = new User(userId, password, name, email);
        user.setSeq(userSeq);
        return user;
    }
}
