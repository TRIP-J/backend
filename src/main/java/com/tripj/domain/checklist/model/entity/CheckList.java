package com.tripj.domain.checklist.model.entity;

import com.tripj.domain.common.entity.BaseTimeEntity;
import com.tripj.domain.item.model.entity.Item;
import com.tripj.domain.trip.model.entity.Trip;
import com.tripj.domain.user.model.entity.User;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trip_id")
    private Trip trip;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private String pack;

    private String previous;

    public static CheckList newCheckList(Item item, User user, Trip trip) {
        return CheckList.builder()
                .item(item)
                .user(user)
                .trip(trip)
                .pack("NO")
                .previous("NOW")
                .build();
    }

    // 체크리스트에 담은 아이템 챙겼을시 update
    public void updatePack(Long checkListId, String yes) {
        this.id = checkListId;
        this.pack = yes;
    }

    // checkList previous update
    public void updatePrevious(String nextPrevious) {
        this.previous = nextPrevious;
    }
}
