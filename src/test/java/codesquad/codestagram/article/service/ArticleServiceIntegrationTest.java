package codesquad.codestagram.article.service;

import codesquad.codestagram.article.domain.Article;
import codesquad.codestagram.article.repository.ArticleRepository;
import codesquad.codestagram.article.repository.impl.MemoryArticleRepository;
import codesquad.codestagram.user.domain.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
@DisplayName("ArticleService: 질문 관리 기능 테스트")
class ArticleServiceIntegrationTest {

    @Autowired
    ArticleService articleService;
    @Autowired
    ArticleRepository repository;


    @Test
    @DisplayName("질문 등록 후 동일한 id로 조회하면 등록된 질문 정보를 반환해야 한다.")
    void create() {
        // given: 테스트용 Article 객체 생성
        Article article = new Article(
                "글쓴이",
                "제목",
                "내용"
        );

        // when: Article 객체 등록, 조회
        Long savedId = articleService.create(article);
        Article findArticle = articleService.findArticle(savedId);

        // then: 테스트용 Article 객체와 조회된 객체는 같아야 한다.
        compare(article, findArticle);
    }

    @Test
    @DisplayName("저장되지 않은 Article 객체를 조회하면 IllegalStateException 예외가 발생해야 한다.")
    void findArticle() {
        // given: 테스트용 Article 객체 생성
        Article article = new Article(
                "글쓴이",
                "제목",
                "내용"
        );
        article.setArticleId(100000L);

        // when: 저장되지 않은 테스트용 Article의 아이디를 통해 조회할 때
        // then: NoSuchElementException이 발생해야 함
        assertThatThrownBy(
                () -> articleService.findArticle(article.getArticleId())
        ).isInstanceOf(NoSuchElementException.class);
    }
    @Test
    @DisplayName("저장된 모든 질문 목록이 올바른 순서로 반환되어야 한다.")
    void findArticles() {
        // given: 테스트용 Article 객체 4개 생성 후 저장
        Article article1 = new Article("글쓴이1", "제목1", "내용1");
        Article article2 = new Article("글쓴이2", "제목2", "내용2");
        Article article3 = new Article("글쓴이3", "제목3", "내용3");
        Article article4 = new Article("글쓴이4", "제목4", "내용4");
        repository.save(article1);
        repository.save(article2);
        repository.save(article3);
        repository.save(article4);

        // when: 저장된 Article 객체 리스트 조회
        List<Article> result = repository.findAll();

        // then: 조회된 Article 객체 리스트가 테스트용 Article 객체 리스트와 사이즈, 참조값이 동일해야 함
        List<Article> expectArticles = new ArrayList<>(List.of(article1, article2, article3, article4));
        for (int i = 0; i < expectArticles.size(); i++) {
            compare(expectArticles.get(i), result.get(i));
        }
        assertThat(result).hasSize(4);
    }

    private void compare(Article actual, Article expect) {
        assertThat(actual.getWriter()).isEqualTo(expect.getWriter());
        assertThat(actual.getTitle()).isEqualTo(expect.getTitle());
        assertThat(actual.getContent()).isEqualTo(expect.getContent());
    }
}
