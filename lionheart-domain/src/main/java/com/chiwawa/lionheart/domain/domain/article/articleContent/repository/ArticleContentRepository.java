package com.chiwawa.lionheart.domain.domain.article.articleContent.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chiwawa.lionheart.domain.domain.article.articleContent.ArticleContent;

public interface ArticleContentRepository extends JpaRepository<ArticleContent, Long>,
	ArticleContentRepositoryCustom {
}