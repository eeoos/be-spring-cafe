package codesquad.codestagram.article.repository.impl;

import codesquad.codestagram.article.domain.Article;
import codesquad.codestagram.article.repository.ArticleRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataJpaArticleRepository extends JpaRepository<Article, Long>, ArticleRepository {
}
