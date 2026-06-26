package com.example.demo.service;

import com.example.demo.dto.MemberRequest;
import com.example.demo.dto.MemberResponse;
import com.example.demo.model.Member;
import com.example.demo.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public MemberResponse create(MemberRequest memberRequest) {
        Member member = Member.builder()
                .name(memberRequest.getName())
                .email(memberRequest.getEmail())
                .age(memberRequest.getAge())
                .enabled(true).build();
        memberRepository.save(member);
        return mapToMemberResponse(member);
    }

    private MemberResponse mapToMemberResponse(Member member) {
        return MemberResponse.builder()
                .id(member.getId())
                .name(member.getName())
                .email(member.getEmail())
                .age(member.getAge())
                .build();
    }

//    public List<MemberResponse> findAll() {
//        List<Member> members = memberRepository.findAll();
//        List<MemberResponse> memberResponses = new ArrayList<>();
//        for (Member member : members) {
//            MemberResponse memberResponse = mapToMemberResponse(member);
//            memberResponses.add(memberResponse);
//        }
//        return memberResponses;
//    }

    public List<MemberResponse> findAll() {
        return memberRepository
                .findAll()
                .stream()
                .map(this::mapToMemberResponse)
                .toList();
    }
}
