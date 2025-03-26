package codesquad.codestagram.article.repository;

import codesquad.codestagram.article.domain.Article;

import java.util.List;
import java.util.Optional;

public interface ArticleRepository {
    Article save(Article article);

    Optional<Article> findById(Long articleId);

    List<Article> findAll();
}
