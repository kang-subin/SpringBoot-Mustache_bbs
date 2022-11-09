package com.mustache.springbootmustachebbs3.domain.dto;

import com.mustache.springbootmustachebbs3.domain.Article;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor

public class ArticleDto {
    private String title;
    private String content;

    public Article toEntity(){ // entity로 변환
        return new Article(title, content);

    }
}
