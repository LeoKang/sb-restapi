package com.example.demo.service;

import com.example.demo.dto.MemberRequest;
import com.example.demo.dto.MemberResponse;
import com.example.demo.repository.MemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class MemberServiceTests {
    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MemberService memberService;

    @AfterEach
    public void doAfterEach() {
        memberRepository.deleteAll();
    }

    @Test
    @DisplayName("회원 추가 및 조회")
    public void testUsers() {
        MemberRequest userRequest = MemberRequest.builder().name("윤서준").age(10).build();
        MemberResponse userResponse = memberService.create(userRequest);
        assertThat(userResponse.getId()).isNotNull();

        userRequest = MemberRequest.builder().name("윤광철").age(43).build();
        userResponse = memberService.create(userRequest);
        assertThat(userResponse.getId()).isNotNull();

        List<MemberResponse> results = memberService.findAll();
        assertThat(results.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("트랜잭션 커밋 테스트")
    public void testTransactionalCommit() {
        List<MemberRequest> userRequests = List.of(
                MemberRequest.builder().name("윤서준").email("SeojunYoon@hanbit.co.kr").age(10).build(),
                MemberRequest.builder().name("윤광철").email("KwangcheolYoon@hanbit.co.kr").age(43).build(),
                MemberRequest.builder().name("김도윤").email("DoyunKim@hanbit.co.kr").age(11).build(),
                MemberRequest.builder().name("공미영").email("MiyeongKong@hanbit.co.kr").age(28).build()
        );

        try {
            memberService.createBatch(userRequests);
        } catch (Exception ignored) {
        }
        assertThat(memberRepository.count()).isEqualTo(4);
    }

    @Test
    @DisplayName("트랜잭션 롤백 테스트")
    public void testTransactionalRollback() {
        List<MemberRequest> userRequests = List.of(
                MemberRequest.builder().name("윤서준").email("SeojunYoon@hanbit.co.kr").age(10).build(),
                MemberRequest.builder().name("윤광철").email("KwangcheolYoon@hanbit.co.kr").age(43).build(),
                MemberRequest.builder().name("김도윤").email("SeojunYoon@hanbit.co.kr").age(11).build(),
                MemberRequest.builder().name("공미영").email("MiyeongKong@hanbit.co.kr").age(28).build()
        );
        try {
            memberService.createBatch(userRequests);
        } catch (Exception e) {

        }
        assertThat(memberRepository.count()).isEqualTo(0);
    }
}
