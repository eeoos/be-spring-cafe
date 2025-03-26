package codesquad.codestagram.article.repository.impl;

import codesquad.codestagram.article.domain.Article;
import codesquad.codestagram.article.repository.ArticleRepository;
import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.Optional;

public class JpaArticleRepository implements ArticleRepository {
    private final EntityManager em;

    public JpaArticleRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public Article save(Article article) {
        em.persist(article);
        return article;
    }

    @Override
    public Optional<Article> findById(Long articleId) {
        Article article = em.find(Article.class, articleId);
        return Optional.ofNullable(article);
    }

    @Override
    public List<Article> findAll() {
        return em.createQuery("select a from Article a", Article.class)
                .getResultList();
    }
}
