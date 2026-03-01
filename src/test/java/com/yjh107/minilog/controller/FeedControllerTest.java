package com.yjh107.minilog.controller;

import com.yjh107.minilog.dto.ArticleResponseDto;
import com.yjh107.minilog.security.JwtUtil;
import com.yjh107.minilog.service.ArticleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(FeedController.class)
@MockBean(JpaMetamodelMappingContext.class)
@WithMockUser(username = "testuser")
public class FeedControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ArticleService articleService;

    @MockBean
    private JwtUtil jwtUtil;

    private LocalDateTime fixtureDateTime = LocalDateTime.of(2025, 1, 1, 0, 0, 0);
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
    private String formattedFixtureDateTime = fixtureDateTime.format(formatter);

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetFeedList() throws Exception {
        ArticleResponseDto articleResponseDto =
                ArticleResponseDto.builder()
                        .articleId(1L)
                        .content("Test Content")
                        .authorId(1L)
                        .authorName("Test User")
                        .createdAt(fixtureDateTime)
                        .build();
        when(articleService.getFeedListByFollowerId(anyLong()))
                .thenReturn(Collections.singletonList(articleResponseDto));

        mockMvc
                .perform(get("/api/v2/feed?followerId=1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].articleId").value(1L))
                .andExpect(jsonPath("$[0].content").value("Test Content"))
                .andExpect(jsonPath("$[0].authorId").value(1L))
                .andExpect(jsonPath("$[0].authorName").value("Test User"))
                .andExpect(jsonPath("$[0].createdAt").value(formattedFixtureDateTime));
    }
}