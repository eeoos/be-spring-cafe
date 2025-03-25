package codesquad.codestagram.article.controller;

import codesquad.codestagram.article.domain.Article;
import codesquad.codestagram.article.dto.ArticleRequest;
import codesquad.codestagram.article.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class ArticleController {

    private final ArticleService articleService;

    @Autowired
    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @PostMapping("/articles")
    public String create(@ModelAttribute ArticleRequest request) {
        articleService.create(request.toEntity());

        return "redirect:/";
    }

    @GetMapping("/")
    public String articleList(Model model) {
        List<Article> articles = articleService.findArticles();
        model.addAttribute("articles", articles);
        return "index";
    }

    @GetMapping("/articles/{articleId}")
    public String showArticle(@PathVariable Long articleId, Model model) {
        Article article = articleService.findArticle(articleId);
        model.addAttribute("article", article);
        return "qna/show";
    }
}
