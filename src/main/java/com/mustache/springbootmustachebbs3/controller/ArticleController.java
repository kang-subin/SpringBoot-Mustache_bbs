package com.mustache.springbootmustachebbs3.controller;

import com.mustache.springbootmustachebbs3.domain.Article;
import com.mustache.springbootmustachebbs3.domain.dto.ArticleDto;
import com.mustache.springbootmustachebbs3.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
@RequestMapping("/articles")
     public class ArticleController {

        private final ArticleRepository articleRepository; // DI (Jpa)

         public ArticleController(ArticleRepository articleRepository) {
             this.articleRepository = articleRepository;
         }

        @GetMapping("") // 기본화면 list로 적용
        public String listpage(Model model){
            List<Article> articleList = articleRepository.findAll();
            model.addAttribute("articles", articleList);
            return "list";
        }


         @GetMapping("/new")
         public String createArticlesPage(){
             return "new";
         }

         @GetMapping("/{id}")
         public String showSingle(@PathVariable Long id , Model model) {
             Optional<Article> optionalArticle = articleRepository.findById(id); // Optional 클래스는 NPE 처리 (article의 변수가 null 인지 확인)
             if (!optionalArticle.isEmpty()) { // article (entity)이 null이 아닌 경우
                 model.addAttribute("article", optionalArticle.get()); // 해당하는 값은 article(view 에 있는)로 이동
                 return "show";
             } else {
                 return "error";
             }
         }

         @PostMapping("")
         public String createArticle(ArticleDto articleDto) {
          Article savedArticle = articleRepository.save(articleDto.toEntity()); //view 를 통해 dto 에 들어온 값을 article에 저장
            return String.format("redirect:/articles/%d", savedArticle.getId()); // id 조회해서 show로 이동
         }


}
