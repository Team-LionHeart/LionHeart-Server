package com.chiwawa.lionheart.api.service.article;

import static com.chiwawa.lionheart.api.service.article.articleContent.ArticleContentServiceUtils.*;
import static com.chiwawa.lionheart.api.service.article.articleTag.ArticleTagServiceUtils.*;
import static com.chiwawa.lionheart.api.service.member.MemberServiceUtils.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chiwawa.lionheart.api.service.article.dto.response.CategoryArticleDto;
import com.chiwawa.lionheart.api.service.article.dto.response.CategoryArticleResponse;
import com.chiwawa.lionheart.domain.domain.article.Article;
import com.chiwawa.lionheart.domain.domain.article.Category;
import com.chiwawa.lionheart.domain.domain.article.articleContent.ArticleContent;
import com.chiwawa.lionheart.domain.domain.article.articleContent.repository.ArticleContentRepository;
import com.chiwawa.lionheart.domain.domain.article.articleTag.repository.ArticleTagRepository;
import com.chiwawa.lionheart.domain.domain.article.repository.ArticleRepository;
import com.chiwawa.lionheart.domain.domain.articlebookmark.ArticleBookmark;
import com.chiwawa.lionheart.domain.domain.articlebookmark.repository.ArticleBookmarkRepository;
import com.chiwawa.lionheart.domain.domain.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ArticleRetrieveService {

	private final ArticleRepository articleRepository;
	private final ArticleBookmarkRepository articleBookmarkRepository;
	private final ArticleContentRepository articleContentRepository;
	private final ArticleTagRepository articleTagRepository;
	private final MemberRepository memberRepository;

	public CategoryArticleResponse findArticlesByCategory(Long memberId, Category category) {

		List<CategoryArticleDto> categoryArticles = articleRepository.findArticlesByCategory(category)
			.stream()
			.map(c -> formatCategoryArticleResponse(memberId, c))
			.collect(Collectors.toList());

		Collections.shuffle(categoryArticles);
		return CategoryArticleResponse.of(categoryArticles);
	}

	// TODO: DB 조회 성능을 위해 리팩토링
	private CategoryArticleDto formatCategoryArticleResponse(Long memberId, Article article) {

		Optional<ArticleBookmark> bookmark = articleBookmarkRepository.findArticleBookmarkByMemberAndArticle(
			findMemberById(memberRepository, memberId), article);

		ArticleContent content = findArticleFirstBodyByArticle(articleContentRepository, article);

		List<String> articleTags = findArticleTagsByArticle(articleTagRepository, article);

		return CategoryArticleDto.of(article, content, articleTags, bookmark.isPresent());
	}

}