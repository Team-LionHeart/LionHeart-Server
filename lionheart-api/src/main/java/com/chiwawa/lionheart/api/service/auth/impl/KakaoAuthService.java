package com.chiwawa.lionheart.api.service.auth.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chiwawa.lionheart.api.service.auth.AuthService;
import com.chiwawa.lionheart.api.service.auth.dto.request.LoginRequest;
import com.chiwawa.lionheart.api.service.auth.dto.request.SignUpRequest;
import com.chiwawa.lionheart.api.service.member.MemberService;
import com.chiwawa.lionheart.api.service.member.MemberServiceUtils;
import com.chiwawa.lionheart.domain.domain.member.Member;
import com.chiwawa.lionheart.domain.domain.member.MemberSocialType;
import com.chiwawa.lionheart.domain.domain.member.repository.MemberRepository;
import com.chiwawa.lionheart.external.client.auth.kakao.KakaoApiCaller;
import com.chiwawa.lionheart.external.client.auth.kakao.dto.response.KakaoProfileResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
@Transactional
public class KakaoAuthService implements AuthService {

	private static final MemberSocialType socialType = MemberSocialType.KAKAO;

	private final KakaoApiCaller kakaoApiCaller;

	private final MemberRepository memberRepository;

	private final MemberService memberService;

	@Override
	public Long signUp(SignUpRequest request) {
		KakaoProfileResponse response = kakaoApiCaller.getProfileInfo(request.getToken());
		return memberService.registerMember(request.toCreateUserDto(response.getId()));
	}

	@Override
	public Long login(LoginRequest request) {
		KakaoProfileResponse response = kakaoApiCaller.getProfileInfo(request.getToken());
		Member member = MemberServiceUtils.findMemberBySocialIdAndSocialType(memberRepository, response.getId(),
			socialType);
		return member.getId();
	}
}