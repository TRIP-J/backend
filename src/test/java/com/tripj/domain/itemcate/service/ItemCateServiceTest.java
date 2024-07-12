package com.tripj.domain.itemcate.service;

import com.tripj.domain.itemcate.model.dto.response.GetItemCateResponse;
import com.tripj.domain.itemcate.model.entity.ItemCate;
import com.tripj.domain.itemcate.repository.ItemCateRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

@SpringBootTest
@ActiveProfiles("test")
class ItemCateServiceTest {

    @Autowired
    private ItemCateService itemCateService;
    @Autowired
    private ItemCateRepository itemCateRepository;

    @AfterEach
    void tearDown() {
        itemCateRepository.deleteAllInBatch();
    }

    @Test
    @DisplayName("아이템 카테고리를 조회합니다.")
    void getItemCate() {
        //given
        ItemCate cate1 = createItemCate(1L, "추천템");
        ItemCate cate2 = createItemCate(2L, "필수품");
        ItemCate cate3 = createItemCate(3L, "의류");
        itemCateRepository.saveAll(List.of(cate1, cate2, cate3));

        //when
        List<GetItemCateResponse> itemCate = itemCateService.getItemCate();

        //then
        assertThat(itemCate).hasSize(3)
                .extracting("itemCateId", "itemCateName")
                .containsExactlyInAnyOrder(
                        tuple(1L, "추천템"),
                        tuple(2L, "필수품"),
                        tuple(3L, "의류")
                );
    }

    private ItemCate createItemCate(Long itemCateId, String itemCate) {
        return ItemCate.builder()
                .id(itemCateId)
                .itemCateName(itemCate)
                .build();
    }

}