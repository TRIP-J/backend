package com.tripj.domain.item.service;

import com.tripj.domain.country.model.entity.Country;
import com.tripj.domain.country.repository.CountryRepository;
import com.tripj.domain.item.model.dto.request.CreateItemRequest;
import com.tripj.domain.item.model.dto.request.UpdateItemRequest;
import com.tripj.domain.item.model.dto.response.CreateItemResponse;
import com.tripj.domain.item.model.dto.response.UpdateItemResponse;
import com.tripj.domain.item.model.entity.Item;
import com.tripj.domain.item.repository.ItemRepository;
import com.tripj.domain.itemcate.model.entity.ItemCate;
import com.tripj.domain.itemcate.repository.ItemCateRepository;
import com.tripj.domain.trip.model.entity.Trip;
import com.tripj.domain.trip.repository.TripRepository;
import com.tripj.domain.user.model.entity.User;
import com.tripj.domain.user.repository.UserRepository;
import com.tripj.global.code.ErrorCode;
import com.tripj.global.error.exception.ForbiddenException;
import com.tripj.global.error.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private final UserRepository userRepository;
    private final ItemCateRepository itemCateRepository;
    private final CountryRepository countryRepository;
    private final TripRepository tripRepository;

    /**
     * 아이템 등록
     */
    public CreateItemResponse createItem(CreateItemRequest request, Long userId) {

        User user = userRepository.findById(userId)
            .orElseThrow(() -> new NotFoundException(ErrorCode.E404_NOT_EXISTS_USER));

        //지난 여행에 아이템 등록 불가
        Trip trip = tripRepository.findByPreviousIsNow(request.getTripId())
            .orElseThrow(() -> new NotFoundException(ErrorCode.E404_NOT_EXISTS_NOW_TRIP));

        ItemCate itemCate = itemCateRepository.findById(request.getItemCateId())
            .orElseThrow(() -> new NotFoundException(ErrorCode.E404_NOT_EXISTS_ITEM_CATE));

        Country country = countryRepository.findById(request.getCountryId())
            .orElseThrow(() -> new NotFoundException(ErrorCode.E404_NOT_EXISTS_COUNTRY));

        if (trip.getUser().getId().equals(userId)) {
            Item savedItem = itemRepository.save(request.toEntity(user, itemCate, country, trip));
            return CreateItemResponse.of(savedItem.getId());
        } else {
            throw new ForbiddenException(ErrorCode.NOT_MY_TRIP);
        }
    }

    /**
     * 아이템 수정
     */
    public UpdateItemResponse updateItem(UpdateItemRequest request, Long itemId, Long userId) {

        Item item = itemRepository.findById(itemId)
            .orElseThrow(() -> new NotFoundException(ErrorCode.E404_NOT_EXISTS_ITEM));

        if (item.getUser().getId().equals(userId)) {
            item.updateItem(request.getItemName());
        } else {
            throw new ForbiddenException("자신의 아이템만 수정 가능합니다.", ErrorCode.E403_FORBIDDEN);
        }

        return UpdateItemResponse.of(item.getId());
    }

    /**
     * Previous 변경
     */
    public void changeItemPrevious() {

        List<Item> allPreviousIsNow = itemRepository.findAllPreviousIsNow();

        allPreviousIsNow
                .forEach(item -> {
                    Long tripId = item.getTrip().getId();
                    String maxPrevious = itemRepository.findMaxPrevious(tripId);
                    if (maxPrevious != null) {
                        int nextNum = Integer.parseInt(maxPrevious.substring(1)) + 1;
                        String nextPrevious = "B" + String.format("%02d", nextNum);
                        item.updatePrevious(nextPrevious);
                    }
                    if (item.getPrevious().equals("NOW")) {
                        item.updatePrevious("B01");
                    }
                });
    }
    
}
