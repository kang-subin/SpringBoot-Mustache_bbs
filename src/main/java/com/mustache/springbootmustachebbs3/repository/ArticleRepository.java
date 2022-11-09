package com.mustache.springbootmustachebbs3.repository;

import com.mustache.springbootmustachebbs3.domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository <Article, Long> { // jpaRepository는 구현체를 알아서 만들어 줌
}
