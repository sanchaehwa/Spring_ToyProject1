package me.hwayeong.springbootdeveloper.service;

import lombok.RequiredArgsConstructor;
import me.hwayeong.springbootdeveloper.repository.BlogRepository;
import me.hwayeong.springbootdeveloper.repository.CommentRepository;
import me.hwayeong.springbootdeveloper.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

public class CommentService {
    private final CommentRepository commentRepository;
    private final BlogRepository blogRepository;
    private final UserRepository userRepository;

    public void writeComent(Long boardId, C)

}
