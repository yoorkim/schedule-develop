package com.example.scheduledevelop.service;

import com.example.scheduledevelop.config.PasswordEncoder;
import com.example.scheduledevelop.dto.LoginRequestDto;
import com.example.scheduledevelop.dto.MemberResponseDto;
import com.example.scheduledevelop.dto.SignUpRequestDto;
import com.example.scheduledevelop.dto.UpdateMemberRequestDto;
import com.example.scheduledevelop.entity.Member;
import com.example.scheduledevelop.repository.MemberRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @PersistenceContext
    private EntityManager entityManager; // EntityManager 주입

    @Transactional
    public MemberResponseDto signUp(SignUpRequestDto requestDto) {
        if (memberRepository.findMemberByEmail(requestDto.getEmail()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "이미 가입된 이메일입니다.");
        }

        String encodedPwd = passwordEncoder.encode(requestDto.getPwd());

        if (passwordEncoder.matches(requestDto.getPwd(), encodedPwd)) {
            Member member = new Member(requestDto.getMemberName(), requestDto.getEmail(), encodedPwd);
            Member savedMember = memberRepository.save(member);
            return new MemberResponseDto(savedMember.getId(), savedMember.getMemberName(), savedMember.getEmail(),
                    savedMember.getCreatedAt(), savedMember.getModifiedAt());
        }
        return null;
    }

    @Transactional
    public Member login(LoginRequestDto requestDto) {
        Member member = memberRepository.findMemberByEmailOrElseThrow(requestDto.getEmail());
        if (!passwordEncoder.matches(requestDto.getPwd(), member.getPwd())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "올바른 비밀번호가 아닙니다.");
        }

        return member;
    }

    @Transactional(readOnly = true)
    public List<MemberResponseDto> findAll() {
        List<Member> memberList = memberRepository.findAll();
        List<MemberResponseDto> dtoList = new ArrayList<>();

        for (Member member : memberList) {
            dtoList.add(new MemberResponseDto(member.getId(), member.getMemberName(), member.getEmail(),
                    member.getCreatedAt(), member.getModifiedAt()));
        }

        return dtoList;
    }

    @Transactional(readOnly = true)
    public MemberResponseDto findById(Long id) {
        Member findMember = memberRepository.findByIdOrElseThrow(id);

        return new MemberResponseDto(findMember.getId(), findMember.getMemberName(), findMember.getEmail(),
                findMember.getCreatedAt(), findMember.getModifiedAt());
    }

    @Transactional
    public Member update(Long id, UpdateMemberRequestDto requestDto) {
        Member findMember = memberRepository.findByIdOrElseThrow(id);

        findMember.setMemberName(requestDto.getMemberName());
        findMember.setEmail(requestDto.getEmail());

        entityManager.flush();

        return findMember;
    }

    @Transactional
    public void delete(Long id) {
        Member findMember = memberRepository.findByIdOrElseThrow(id);
        memberRepository.delete(findMember);
    }
}
