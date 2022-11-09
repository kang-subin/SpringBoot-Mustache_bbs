package com.mustache.springbootmustachebbs3.domain.dto;

import com.mustache.springbootmustachebbs3.domain.Article;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor

public class ArticleDto {
    private  Long id;
    private String title;
    private String content;

    public Article toEntity(){ // entity로 변환
        return new Article(id, title, content); // id 값도 entity에 들어옴

    }
}
