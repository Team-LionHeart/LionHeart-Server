package com.chiwawa.lionheart.domain.domain.articlebookmark.repository;

import static com.chiwawa.lionheart.domain.domain.article.articlebookmark.QArticleBookmark.*;

import java.util.Optional;

import com.chiwawa.lionheart.domain.domain.article.Article;
import com.chiwawa.lionheart.domain.domain.articlebookmark.ArticleBookmark;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ArticleBookmarkRepositoryImpl implements ArticleBookmarkRepositoryCustom {
	private final JPAQueryFactory queryFactory;

	@Override
	public Optional<ArticleBookmark> findByMemberIdAndArticleId(Long memberId, Article article) {
		return Optional.ofNullable(queryFactory
			.selectFrom(articleBookmark)
			.where(articleBookmark.article.eq(article))
			.where(articleBookmark.member.id.eq(memberId))
			.fetchOne());
	}
}