package me.hwayeong.springbootdeveloper.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import me.hwayeong.springbootdeveloper.domain.Article;
import me.hwayeong.springbootdeveloper.dto.AddArticleRequest;
import me.hwayeong.springbootdeveloper.dto.UpdateArticleRequest;
import me.hwayeong.springbootdeveloper.repository.BlogRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor //final아 븉거나 @Notnull이 붙은 필드의 생성자 추가
@Service //빈으로 등록
public class BlogService {
    private final BlogRepository blogRepository;

    //저장
    public Article save(AddArticleRequest request,String userName) {
        return blogRepository.save(request.toEntity(userName));
    }

    //데이터 베이스에 저장되어 있는 글을 모두 가져옴
    public List<Article> findAll() {
        return blogRepository.findAll();
    }

    //글의 ID를 통해 글을 조회
    public Article findById(Long id) {
        return blogRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("not found: " + id));
    }

    //삭제
    public void delete(long id) {
        Article article = blogRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("not found: " + id));
        authorizeArticleAuthor(article);
        blogRepository.deleteById(id);
    }
    //특정 아이디 글 수정
    @Transactional //수정하면서 기존의 데이터값이 수정한 값으로 바뀐다 (=중간에 값이 바뀌는거) 이 과정이 제대로 수행되게
    public Article update(long id, UpdateArticleRequest request) {
        Article article = blogRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("not found: " + id));
        authorizeArticleAuthor(article);
        article.update(request.getTitle(), request.getContent());
        return article;
    }
    // 게시물을 작성한 유저인지 확인
    private static void authorizeArticleAuthor(Article article) {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        if (!article.getAuthor().equals(userName)) {
            throw new IllegalArgumentException("not authorized");
        }
    }
}
