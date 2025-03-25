package codesquad.codestagram.user.repository.impl;

import codesquad.codestagram.user.domain.User;
import codesquad.codestagram.user.repository.UserRepository;
import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.Optional;

public class JpaUserRepository implements UserRepository {
    private final EntityManager em;

    public JpaUserRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public Long save(User user) {
        em.persist(user);
        return user.getSeq();
    }

    @Override
    public Optional<User> findBySeq(Long seq) {
        User user = em.find(User.class, seq);
        return Optional.ofNullable(user);
    }

    @Override
    public List<User> findAll() {
        return em.createQuery("select m from User m", User.class)
                .getResultList();
    }

    @Override
    public Long update(User updatedUser) {
        User user = em.find(User.class, updatedUser.getSeq());
        if (user != null) {
            user.setUserId(updatedUser.getUserId());
            user.setPassword(updatedUser.getPassword());
            user.setName(updatedUser.getName());
            user.setEmail(updatedUser.getEmail());
        }
        return (user != null) ? user.getSeq() : null;
    }
}
