package com.tripj.domain.item.service;

import com.tripj.domain.country.model.entity.Country;
import com.tripj.domain.country.repository.CountryRepository;
import com.tripj.domain.item.model.dto.CreateItemRequest;
import com.tripj.domain.item.model.dto.CreateItemResponse;
import com.tripj.domain.item.model.entity.Item;
import com.tripj.domain.item.repository.ItemRepository;
import com.tripj.domain.itemcate.model.entity.ItemCate;
import com.tripj.domain.itemcate.repository.ItemCateRepository;
import com.tripj.domain.user.model.entity.User;
import com.tripj.domain.user.repository.UserRepository;
import com.tripj.global.code.ErrorCode;
import com.tripj.global.error.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private final UserRepository userRepository;
    private final ItemCateRepository itemCateRepository;
    private final CountryRepository countryRepository;

    //아이템 등록
    public CreateItemResponse createItem(CreateItemRequest request, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 회원입니다.", ErrorCode.E404_NOT_EXISTS_USER));

        ItemCate itemCate = itemCateRepository.findById(request.getItemCateId())
                .orElseThrow(() -> new NotFoundException("존재하지 않는 아이템 카테고리입니다.", ErrorCode.E404_NOT_EXISTS_ITEM_CATE));

        Country country = countryRepository.findById(request.getCountryId())
                .orElseThrow(() -> new NotFoundException("존재하지 않는 국가입니다.", ErrorCode.E404_NOT_EXISTS_COUNTRY));

        Item savedItem = itemRepository.save(request.toEntity(user, itemCate, country));

        return CreateItemResponse.of(savedItem.getId());
    }

}
