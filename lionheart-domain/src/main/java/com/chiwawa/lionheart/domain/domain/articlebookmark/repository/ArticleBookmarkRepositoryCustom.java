package com.chiwawa.lionheart.domain.domain.articlebookmark.repository;

import java.util.Optional;

import com.chiwawa.lionheart.domain.domain.article.Article;
import com.chiwawa.lionheart.domain.domain.articlebookmark.ArticleBookmark;

public interface ArticleBookmarkRepositoryCustom {

	Optional<ArticleBookmark> findByMemberIdAndArticleId(Long memberId, Article article);
}