package com.tripj.domain.like.model.entity;

import com.tripj.domain.board.model.entity.Board;
import com.tripj.domain.user.model.entity.User;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class LikedBoard {

    @Id
    @Column(name = "liked_board_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @JoinColumn(name = "board_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Board board;

    public static LikedBoard newLikedBoard(User user, Board board) {
        return LikedBoard.builder()
                .user(user)
                .board(board)
                .build();
    }
}
