package com.tripj.domain.comment.model.entity;

import com.tripj.domain.board.model.entity.Board;
import com.tripj.domain.user.model.entity.User;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Entity
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment {

    @Id
    @Column(name = "comment_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @JoinColumn(name = "board_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Board board;

    private String content;

    public static Comment newComment(User user, Board board, String content) {
        return Comment.builder()
                .user(user)
                .board(board)
                .content(content)
                .build();
    }

    /**
     * 댓글 수정
     */
    public void updateComment(String content) {
        this.content = content;
    }
}
