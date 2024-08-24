package me.hwayeong.springbootdeveloper.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.hwayeong.springbootdeveloper.domain.Article;
import me.hwayeong.springbootdeveloper.dto.AddArticleRequest;
import me.hwayeong.springbootdeveloper.dto.UpdateArticleRequest;
import me.hwayeong.springbootdeveloper.repository.BlogRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import static org.assertj.core.api.Assertions.assertThat;



import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;



@SpringBootTest
@AutoConfigureMockMvc

class BlogApiControllerTest {
    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper; //직렬화, 역직렬화를 위한 클래스

    @Autowired
    private WebApplicationContext context;

    @Autowired
    BlogRepository blogRepository;

    @BeforeEach //test 실행 전 실행하는 매서드
    public void mockMvcSetUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        blogRepository.deleteAll();
    }
    @DisplayName("addArticle: 블로그 글 추가에 성공한다")
    @Test
    public void addArticle() throws Exception {
        //given (글 추가에 필요한 요청 객체를 만듬)
        final String url = "/api/articles";
        final String title = "title";
        final String content = "content";
        final AddArticleRequest userRequest = new AddArticleRequest(title, content);

        //JSON 형태로 변환
        final String requestBody = objectMapper.writeValueAsString(userRequest);
        //when(JSON 변환하고 요청 본문에 보냄으로써 글 추가_
        ResultActions result = mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestBody)
        );
        //then(글이 잘 추가가 되었는지 확인)
        result.andExpect(status().isCreated());
        List<Article> articles = blogRepository.findAll(); //저장된 글을 모두 가져옴
        assertThat(articles.size()).isEqualTo(1); //글이 정상적으로 등록된거면 1
        assertThat(articles.get(0).getTitle()).isEqualTo(title);
        //첫번쨰 글의 제목이 요청한 제목과 일치하는지 확인
    }
    @DisplayName("findAllArticles: 블로그 글 목록 조회에 성공")
    @Test
    public void findAllArticles() throws Exception {
        //given (글 찾는데 필요한 요청 객체를 만듬)
        final String url = "/api/articles";
        final String title = "title";
        final String content = "content";

        blogRepository.save(Article.builder()
                .title(title)
                .content(content)
                .build());
        //when
        final  ResultActions resultActions = mockMvc.perform(get(url)
                .accept(MediaType.APPLICATION_JSON));
        //then
        resultActions.andExpect(status().isOk()) .andExpect(jsonPath("$[0].title").value(title)) .andExpect(jsonPath("$[0].content").value(content));
    }
    @DisplayName("findlArticle: 블로그 글 목록 조회에 성공")
    @Test
    public void findArticle() throws Exception {
        //given (글 찾는데 필요한 요청 객체를 만듬)
        final String url = "/api/articles/{id}";
        final String title = "title";
        final String content = "content";

        Article savedArticle = blogRepository.save(Article.builder().title(title). content(content). build());

        //when
        final  ResultActions resultActions = mockMvc.perform(get(url,savedArticle.getId()));
        //then
        resultActions.andExpect(status().isOk()) .andExpect(jsonPath("$.title").value(title)) .andExpect(jsonPath("$.content").value(content) );
    }
    @DisplayName("deleteArticle: 블로그 글 삭제")
    @Test
    public void deleteArticle() throws Exception {
        //given (글 삭제에 필요한 요청 객체를 만듬)
        final String url = "/api/articles/{id}";
        final String title = "title";
        final String content = "content";

        Article savedArticle = blogRepository.save(Article.builder().title(title). content(content). build());

        //when
        mockMvc.perform(delete(url,savedArticle.getId())).andExpect(status().isOk()); //200 andExpect 서버 요청 성공적
        //then
        List<Article> articles = blogRepository.findAll();
        assertThat(articles).isEmpty();
    }
    @DisplayName("updateArticle: 블로그 글 수정에 성공함")
    @Test
    public void updateArticle() throws Exception {
        //given (글 추가에 필요한 요청 객체를 만듬)
        final String url = "/api/articles/{id}";
        final String title = "title";
        final String content = "content";

        Article savedArticle = blogRepository.save(Article.builder().title(title). content(content). build());
        final String newTitle = "newTitle";
        final String newContent = "newContent";

        UpdateArticleRequest request = new UpdateArticleRequest(newTitle, newContent);

        //when(JSON 변환하고 요청 본문에 보냄으로써 글 추가_
        ResultActions result = mockMvc.perform(put(url,savedArticle.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(request)));
        //then(글이 잘 추가가 되었는지 확인)
        result.andExpect(status().isOk());
        Article article = blogRepository.findById(savedArticle.getId()).get();
        assertThat(article.getTitle()).isEqualTo(newTitle);
        assertThat(article.getContent()).isEqualTo(newContent);

    }

}