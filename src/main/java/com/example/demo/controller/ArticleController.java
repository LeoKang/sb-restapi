package com.example.demo.controller;

import com.example.demo.dto.ArticleResponse;
import com.example.demo.service.ArticleService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/articles")
@RequiredArgsConstructor
public class ArticleController {
    private final ArticleService articleService;

    @GetMapping
    public List<ArticleResponse> getByMember(@RequestParam(name = "memberId", required = false) Long memberId) {
        if (memberId == null) {
            return articleService.findAll();
        } else {
            return articleService.findByMemberId(memberId);
        }
    }

    @GetMapping("/{id}")
    public ArticleResponse get(@PathVariable("id") Long id) {
        return articleService.findById(id);
    }

    @GetMapping("/{id}/articles")
    public void getArticle(@PathVariable("id") Long id, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getSession().getServletContext().getRequestDispatcher("/api/articles?memberId=" + id)
                .forward(request, response);
    }
}
