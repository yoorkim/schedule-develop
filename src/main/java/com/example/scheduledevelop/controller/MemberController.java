package com.example.scheduledevelop.controller;

import com.example.scheduledevelop.dto.LoginRequestDto;
import com.example.scheduledevelop.dto.MemberResponseDto;
import com.example.scheduledevelop.dto.SignUpRequestDto;
import com.example.scheduledevelop.dto.UpdateMemberRequestDto;
import com.example.scheduledevelop.entity.Member;
import com.example.scheduledevelop.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/signup")
    public ResponseEntity<MemberResponseDto> signUp(@Valid @RequestBody SignUpRequestDto requestDto) {
        return new ResponseEntity<>(memberService.signUp(requestDto), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<MemberResponseDto> login(@Valid @RequestBody LoginRequestDto requestDto, HttpServletRequest request) {
        Member member = memberService.login(requestDto);

        HttpSession session = request.getSession(true);
        session.setAttribute("sessionKey", member);

        MemberResponseDto responseDto = new MemberResponseDto(member.getId(), member.getMemberName(), member.getEmail(), member.getCreatedAt(), member.getModifiedAt());
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    // 로그인된 사용자 정보 조회
    @GetMapping("/me")
    public ResponseEntity<String> getSessionInfo(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("sessionKey") == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
        }

        Member member = (Member) session.getAttribute("sessionKey");
        return ResponseEntity.ok("현재 로그인한 사용자 email: " + member.getEmail());
    }

    @GetMapping
    public ResponseEntity<List<MemberResponseDto>> findAll() {
        return new ResponseEntity<>(memberService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MemberResponseDto> finfById(@PathVariable Long id) {
        return new ResponseEntity<>(memberService.findById(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MemberResponseDto> updateMember(
            @PathVariable Long id,
            @Valid @RequestBody UpdateMemberRequestDto requestDto,
            HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        Member loggedInMember = (Member) session.getAttribute("sessionKey");

        // 로그인된 사용자가 아닌 다른 사용자의 정보를 수정하려고 하면 예외 발생
        if (!loggedInMember.getId().equals(id)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "수정할 수 없습니다.");
        }

        Member updatedMember = memberService.update(id, requestDto);
        // 세션 갱신
        session.setAttribute("sessionKey", updatedMember);

        MemberResponseDto responseDto = new MemberResponseDto(updatedMember.getId(), updatedMember.getMemberName(), updatedMember.getEmail(), updatedMember.getCreatedAt(), updatedMember.getModifiedAt());
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMember(@PathVariable Long id, HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        if (session != null && session.getAttribute("sessionKey") != null) {
            Member loggedInMember = (Member) session.getAttribute("sessionKey");

            // 로그인된 사용자가 아닌 다른 사용자를 삭제하려고 하면 예외 발생
            if (!loggedInMember.getId().equals(id)) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "삭제할 수 없습니다.");
            }

            memberService.delete(id);
            session.invalidate();  // 로그아웃
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
