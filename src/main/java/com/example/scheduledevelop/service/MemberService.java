package com.example.scheduledevelop.service;

import com.example.scheduledevelop.dto.MemberResponseDto;
import com.example.scheduledevelop.dto.SignUpRequestDto;
import com.example.scheduledevelop.dto.UpdateMemberRequestDto;
import com.example.scheduledevelop.entity.Member;
import com.example.scheduledevelop.repository.MemberRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    @PersistenceContext
    private EntityManager entityManager; // EntityManager 주입

    @Transactional
    public MemberResponseDto signUp(SignUpRequestDto requestDto) {
        Member member = new Member(requestDto.getMemberName(), requestDto.getEmail());
        Member savedMember = memberRepository.save(member);

        return new MemberResponseDto(savedMember.getId(), savedMember.getMemberName(), savedMember.getEmail(),
                savedMember.getCreatedAt(), savedMember.getModifiedAt());
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
    public MemberResponseDto update(Long id, UpdateMemberRequestDto requestDto) {
        Member findMember = memberRepository.findByIdOrElseThrow(id);

        findMember.setMemberName(requestDto.getMemberName());

        entityManager.flush();

        return new MemberResponseDto(findMember.getId(), findMember.getMemberName(), findMember.getEmail(),
                findMember.getCreatedAt(), findMember.getModifiedAt());
    }

    @Transactional
    public void delete(Long id) {
        Member findMember = memberRepository.findByIdOrElseThrow(id);
        memberRepository.delete(findMember);
    }
}
