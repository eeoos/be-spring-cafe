package codesquad.codestagram.article.dto;

import codesquad.codestagram.article.domain.Article;

public record ArticleRequest(
        String writer,
        String title,
        String contents
) {

    public Article toEntity() {
        return new Article(
                writer,
                title,
                contents
        );
    }
}
