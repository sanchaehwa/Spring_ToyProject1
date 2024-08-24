package me.hwayeong.springbootdeveloper.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.hwayeong.springbootdeveloper.domain.Article;
@NoArgsConstructor
@AllArgsConstructor
@Getter
//dto(단순하게 데이터를 옮기기 위해 사용하는 전달자 역할 별도의 비즈니스 로직이 필요없음)
public class AddArticleRequest {
    private String title;
    private String content;

    public Article toEntity() {
        return Article.builder().title(title).content(content).build();
    }
}
