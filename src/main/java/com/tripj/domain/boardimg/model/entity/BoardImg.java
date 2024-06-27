package com.tripj.domain.boardimg.model.entity;

import com.tripj.domain.board.model.entity.Board;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Entity
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardImg {

    @Id
    @Column(name = "board_img_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String url;

    private String path;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    public static BoardImg newBoardImg(String url, String path, Board board) {
        return BoardImg.builder()
                .url(url)
                .path(path)
                .board(board)
                .build();
    }



}
