package com.yjh107.minilog.service;

import com.yjh107.minilog.dto.ArticleResponseDto;
import com.yjh107.minilog.entity.Article;
import com.yjh107.minilog.entity.User;
import com.yjh107.minilog.exception.ArticleNotFoundException;
import com.yjh107.minilog.exception.UserNotFoundException;
import com.yjh107.minilog.repository.ArticleRepository;
import com.yjh107.minilog.repository.UserRepository;
import com.yjh107.minilog.util.EntityDtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/*
    게시글과 관련된 비즈니스 로직을 담당하는 서비스 클래스
    게시글의 생성, 조회, 수정, 삭제 기능 제공
    팔로우한 사용자가 작성한 게시글 피드를 조회하는 기능도 제공
 */
@Service
@Transactional(isolation = Isolation.REPEATABLE_READ)
public class ArticleService {
    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;

    @Autowired
    public ArticleService(ArticleRepository articleRepository, UserRepository userRepository) {
        this.articleRepository = articleRepository;
        this.userRepository = userRepository;
    }

    public ArticleResponseDto createArticle(String content, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException(
                        String.format("해당 아이디(%d)를 가진 사용자를 찾을 수 없습니다.", userId)));

        Article article = Article.builder().content(content).author(user).build();

        Article savedArticle = articleRepository.save(article);
        return EntityDtoMapper.toDto(savedArticle);
    }

    public void deleteArticle(Long articleId) {
        Article article = articleRepository.findById(articleId).orElseThrow(
                () -> new ArticleNotFoundException(
                        String.format("해당 아이디(%d)를 가진 게시글을 찾을 수 없습니다.", articleId)));

        articleRepository.deleteById(articleId);
    }

    public ArticleResponseDto updateArticle(Long articleId, String content) {
        Article article = articleRepository.findById(articleId).orElseThrow(
                () -> new ArticleNotFoundException(
                        String.format("해당 아이디(%d)를 가진 게시글을 찾을 수 없습니다.", articleId)));

        article.setContent(content);
        Article updatedArticle = articleRepository.save(article);

        return EntityDtoMapper.toDto(updatedArticle);
    }

    @Transactional(readOnly = true)
    public ArticleResponseDto getArticleById(Long articleId) {
        Article article = articleRepository.findById(articleId).orElseThrow(
                () -> new ArticleNotFoundException(
                        String.format("해당 아이디(%d)를 가진 게시글을 찾을 수 없습니다.", articleId)));

        return EntityDtoMapper.toDto(article);
    }

    @Transactional(readOnly = true)
    public List<ArticleResponseDto> getFeedListByFollowerId(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException(
                        String.format("해당 아이디(%d)를 가진 사용자를 찾을 수 없습니다.", userId)));

        var feedList = articleRepository.findAllByFollowerId(user.getId());
        return feedList.stream().map(EntityDtoMapper::toDto).toList();
    }

    @Transactional(readOnly = true)
    public List<ArticleResponseDto> getArticleListByUserId(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException(
                        String.format("해당 아이디(%d)를 가진 사용자를 찾을 수 없습니다.", userId)));

        var articleList = articleRepository.findAllByAuthorId(user.getId());
        return articleList.stream().map(EntityDtoMapper::toDto).toList();
    }
}
