package com.tripj.domain.checklist.model.entity;

import com.tripj.domain.common.entity.BaseTimeEntity;
import com.tripj.domain.item.model.entity.Item;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CheckList extends BaseTimeEntity {

    @Id
    @Column(name = "checklist_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    private String pack;

    public static CheckList newCheckList(Item item, String pack) {
        return CheckList.builder()
                .item(item)
                .pack("NO")
                .build();
    }

    // 체크리스트에 담은 아이템 챙겼을시 update
    public void updatePack(Long checkListId, String yes) {
        this.id = checkListId;
        this.pack = yes;
    }
}
