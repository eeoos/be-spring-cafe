package codesquad.codestagram.user.repository.impl;

import codesquad.codestagram.user.domain.User;
import codesquad.codestagram.user.repository.UserRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataJpaUserRepository extends JpaRepository<User, Long>, UserRepository {
}
