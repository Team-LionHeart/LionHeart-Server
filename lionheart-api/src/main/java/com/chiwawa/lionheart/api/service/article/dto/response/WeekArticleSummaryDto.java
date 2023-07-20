package com.chiwawa.lionheart.api.service.article.dto.response;

import java.util.List;

import com.chiwawa.lionheart.domain.domain.article.Article;
import com.chiwawa.lionheart.domain.domain.article.articleContent.ArticleContent;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
public class WeekArticleSummaryDto {

	@Schema(description = "아티클ID")
	private Long articleId;

	@Schema(description = "제목")
	private String title;

	@Schema(description = "메인 이미지 URL")
	private String mainImageUrl;

	@Schema(description = "메인 이미지 캡션")
	private String firstBodyContent;

	@Schema(description = "소요 시간")
	private int requiredTime;

	@Schema(description = "북마크 여부")
	private Boolean isMarked;

	@Schema(description = "오늘의 아티클 여부")
	private Boolean isTodayArticle;

	@Schema(description = "아티클 태그정보")
	private List<String> tags;

	public static WeekArticleSummaryDto of(Article article, ArticleContent content, List<String> tag,
		boolean isMarked, boolean isTodayArticle) {
		return WeekArticleSummaryDto.builder()
			.articleId(article.getId())
			.title(article.getTitle())
			.mainImageUrl(article.getMainImageUrl())
			.requiredTime(article.getRequiredTime())
			.firstBodyContent(content.getContent())
			.isMarked(isMarked)
			.isTodayArticle(isTodayArticle)
			.tags(tag)
			.build();
	}
}
