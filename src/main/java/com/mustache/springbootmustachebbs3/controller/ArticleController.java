package com.mustache.springbootmustachebbs3.controller;

import com.mustache.springbootmustachebbs3.domain.Article;
import com.mustache.springbootmustachebbs3.domain.dto.ArticleDto;
import com.mustache.springbootmustachebbs3.repository.ArticleRepository;
import lombok.Getter;
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
            return String.format("redirect:/articles/%d", savedArticle.getId()); // id 조회해서 show로 이동ㅍ (model은 controller에 있는 값을 view로 전달함 , 이 경우 entity에서 id 값만 가져와서 view에 보여주는 방식이 아니라 주소에 삽입하는 형식이라서 model 사용 안한듯?
         }

        @GetMapping("/{id}/edit") // edit 눌렀을 때 보여지는 화면
            public String edit(@PathVariable Long id , Model model) {
            Optional<Article> optionalArticle = articleRepository.findById(id); //entity 값
            if (!optionalArticle.isEmpty()) {
                model.addAttribute("article", optionalArticle.get());
                return "edit";
            } else {
                model.addAttribute("massage", String.format("%d가 없습니다.", id));
                return "error";
            }
         }

            @PostMapping("/{id}/update") // edit 에서 sumit 누르면
            public String update(@PathVariable Long id, ArticleDto articleDto) { // dto에 id 생성자를 뚫어주지 않으면 update가 아니라 insert가 됨
                log.info("title:{} content:{}", articleDto.getTitle(), articleDto.getContent()); //변경된 값 log 출력
                Article article = articleRepository.save(articleDto.toEntity()); // id 값이 들어와서 해당 id 의 title, content 내용이 entity 저장 됨 (insert가 아니라 update)
                return String.format("redirect:/articles/%d", article.getId()); // 수정된 값의 show 로 이동
            }


     @GetMapping("/{id}/delete")
        public String delete(@PathVariable Long id, Model model) {
        articleRepository.deleteById(id);
        model.addAttribute("message", String.format("id: %d가 지워졌습니다.", id));
        return "redirect:/articles" ;
        }


        }


