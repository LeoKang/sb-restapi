package com.example.demo.controller;

import com.example.demo.dto.MemberRequest;
import com.example.demo.dto.MemberResponse;
import com.example.demo.repository.MemberRepository;
import com.example.demo.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.example.demo.model.Member;

import java.util.List;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberController {
    private final MemberRepository memberRepository;
    private final MemberService memberService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MemberResponse post(@RequestBody MemberRequest memberRequest) {
        return memberService.create(memberRequest);
    }

    @GetMapping
    public List<MemberResponse> getAll() {
        return memberService.findAll();
    }

    @GetMapping("/{id}")
    public Member get(@PathVariable("id") Long id) {
        return memberRepository.findById(id).orElse(null);
    }

    @PutMapping("/{id}")
    public Member put(@PathVariable("id") Long id, @RequestBody Member member) {
        member.setId(id);
        return memberRepository.save(member);
    }

    @PatchMapping("/{id}")
    public Member patch(@PathVariable("id") Long id, @RequestBody Member patch) {
        Member member = memberRepository.findById(id).orElse(null);
        if (member != null) {
            if (patch.getName() != null) member.setName(patch.getName());
            if (patch.getEmail() != null) member.setEmail(patch.getEmail());
            if (patch.getAge() != null) member.setAge(patch.getAge());
            memberRepository.save(member);
        }
        return member;
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        memberRepository.deleteById(id);
    }
}
