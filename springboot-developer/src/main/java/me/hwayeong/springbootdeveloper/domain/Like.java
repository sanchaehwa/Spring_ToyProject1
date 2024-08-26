package me.hwayeong.springbootdeveloper.domain;


import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import static javax
//게시물 좋아요
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name ="Like")

public class Like {
    @Id
    @GeneratedValue(strategy = GenerationType)

}
