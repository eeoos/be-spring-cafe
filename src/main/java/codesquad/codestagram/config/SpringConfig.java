package codesquad.codestagram.config;

import codesquad.codestagram.article.repository.ArticleRepository;
import codesquad.codestagram.article.repository.impl.JdbcArticleRepository;
import codesquad.codestagram.article.repository.impl.JdbcTemplateArticleRepository;
import codesquad.codestagram.user.repository.impl.JdbcTemplateUserRepository;
import codesquad.codestagram.user.repository.impl.JdbcUserRepository;
import codesquad.codestagram.user.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class SpringConfig {

    private final DataSource datasource;

    public SpringConfig(DataSource datasource) {
        this.datasource = datasource;
    }

    @Bean
    public UserRepository userRepository() {
//        return new MemoryUserRepository();
//        return new JdbcUserRepository(datasource);
        return new JdbcTemplateUserRepository(datasource);
    }

    @Bean
    public ArticleRepository articleRepository() {
//        return new MemoryArticleRepository();
//        return new JdbcArticleRepository(datasource);
        return new JdbcTemplateArticleRepository(datasource);
    }
}
