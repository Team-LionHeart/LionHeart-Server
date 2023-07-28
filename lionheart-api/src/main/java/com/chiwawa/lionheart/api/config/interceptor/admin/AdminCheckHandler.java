package com.chiwawa.lionheart.api.config.interceptor.admin;

import static com.chiwawa.lionheart.common.constant.message.AuthErrorMessage.*;
import static com.chiwawa.lionheart.common.exception.ErrorCode.*;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.chiwawa.lionheart.api.service.member.MemberServiceUtils;
import com.chiwawa.lionheart.common.exception.model.ForbiddenException;
import com.chiwawa.lionheart.common.util.JwtUtils;
import com.chiwawa.lionheart.domain.domain.member.Member;
import com.chiwawa.lionheart.domain.domain.member.MemberRole;
import com.chiwawa.lionheart.domain.domain.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class AdminCheckHandler {

	private final JwtUtils jwtUtils;
	private final MemberRepository memberRepository;

	//TODO: hasRole로 메서드명 변경하고 hasAdminAuthority를 return하도록 변경하는건 어떨까?
	public boolean hasRole(HttpServletRequest request) {
		String bearerToken = request.getHeader("Authorization");
		if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
			String accessToken = bearerToken.substring("Bearer ".length());
			return hasAdminAuthority(accessToken);
		}
		throw new ForbiddenException(ADMIN_ERROR_MESSAGE, FORBIDDEN_ADMIN_EXCEPTION);
	}

	//TODO: null 사용하지 않고 Optional로 변하면 어떨까
	private boolean hasAdminAuthority(String accessToken) {
		if (jwtUtils.validateToken(accessToken)) {
			Long memberId = jwtUtils.getMemberIdFromJwt(accessToken);
			if (memberId != null) {
				Member member = MemberServiceUtils.findMemberById(memberRepository, memberId);
				return member.getRole().equals(MemberRole.ADMIN);
			}
		}
		return false;
	}
}
