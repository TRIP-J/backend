package com.tripj.domain.board.model.entity;

import com.tripj.domain.boardcate.model.entity.BoardCate;
import com.tripj.domain.comment.model.entity.Comment;
import com.tripj.domain.common.entity.BaseEntity;
import com.tripj.domain.common.entity.BaseTimeEntity;
import com.tripj.domain.like.model.entity.LikedBoard;
import com.tripj.domain.user.model.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.*;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Board extends BaseTimeEntity {

    @Id
    @Column(name = "board_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "board_cate_id")
    private BoardCate boardCate;

    @OneToMany(mappedBy = "board")
    private List<Comment> comment = new ArrayList<>();

    @OneToMany(mappedBy = "board")
    private List<LikedBoard> likedBoard = new ArrayList<>();

    private String title;

    private String content;

    public static Board newBoard(String title, String content,
                                User user, BoardCate boardCate) {
        return Board.builder()
                .title(title)
                .content(content)
                .user(user)
                .boardCate(boardCate)
                .build();
    }

    /**
     * 게시글 수정
     */
    public void updateBoard(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
