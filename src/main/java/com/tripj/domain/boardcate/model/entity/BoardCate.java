package com.tripj.domain.boardcate.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardCate {

    @Id
    @Column(name = "board_cate_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String boardCateName;

    private String boardCateCode;
}
