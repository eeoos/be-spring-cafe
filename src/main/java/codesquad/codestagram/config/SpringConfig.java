package codesquad.codestagram.config;

import codesquad.codestagram.article.repository.ArticleRepository;
import codesquad.codestagram.article.repository.impl.JdbcArticleRepository;
import codesquad.codestagram.article.repository.impl.JdbcTemplateArticleRepository;
import codesquad.codestagram.article.repository.impl.JpaArticleRepository;
import codesquad.codestagram.user.repository.impl.JdbcTemplateUserRepository;
import codesquad.codestagram.user.repository.impl.JdbcUserRepository;
import codesquad.codestagram.user.repository.UserRepository;
import codesquad.codestagram.user.repository.impl.JpaUserRepository;
import jakarta.persistence.EntityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class SpringConfig {

    private final DataSource datasource;
    private final EntityManager em;

    public SpringConfig(DataSource datasource, EntityManager em) {
        this.datasource = datasource;
        this.em = em;
    }

    @Bean
    public UserRepository userRepository() {
//        return new MemoryUserRepository();
//        return new JdbcUserRepository(datasource);
//        return new JdbcTemplateUserRepository(datasource);
        return new JpaUserRepository(em);
    }

    @Bean
    public ArticleRepository articleRepository() {
//        return new MemoryArticleRepository();
//        return new JdbcArticleRepository(datasource);
//        return new JdbcTemplateArticleRepository(datasource);
        return new JpaArticleRepository(em);
    }
}
