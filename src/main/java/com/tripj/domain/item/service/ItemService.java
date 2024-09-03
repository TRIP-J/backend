package com.tripj.domain.item.service;

import com.tripj.domain.checklist.model.dto.response.GetCheckListResponse;
import com.tripj.domain.checklist.model.dto.response.GetItemListResponse;
import com.tripj.domain.checklist.repository.CheckListRepository;
import com.tripj.domain.country.model.entity.Country;
import com.tripj.domain.country.repository.CountryRepository;
import com.tripj.domain.item.constant.ItemStatus;
import com.tripj.domain.item.constant.ItemType;
import com.tripj.domain.item.model.dto.request.CreateItemRequest;
import com.tripj.domain.item.model.dto.request.UpdateItemRequest;
import com.tripj.domain.item.model.dto.response.CreateItemResponse;
import com.tripj.domain.item.model.dto.response.DeleteItemResponse;
import com.tripj.domain.item.model.dto.response.UpdateItemResponse;
import com.tripj.domain.item.model.entity.FixedItem;
import com.tripj.domain.item.model.entity.Item;
import com.tripj.domain.item.repository.FixedItemRepository;
import com.tripj.domain.item.repository.ItemRepository;
import com.tripj.domain.itemcate.model.entity.ItemCate;
import com.tripj.domain.itemcate.repository.ItemCateRepository;
import com.tripj.domain.trip.model.entity.Trip;
import com.tripj.domain.trip.repository.TripRepository;
import com.tripj.domain.user.model.entity.User;
import com.tripj.domain.user.repository.UserRepository;
import com.tripj.global.code.ErrorCode;
import com.tripj.global.error.exception.BusinessException;
import com.tripj.global.error.exception.ForbiddenException;
import com.tripj.global.error.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.tripj.domain.item.constant.ItemStatus.*;
import static com.tripj.domain.item.constant.ItemType.*;
import static com.tripj.global.code.ErrorCode.*;

@Service
@Transactional
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private final FixedItemRepository fixedItemRepository;
    private final UserRepository userRepository;
    private final ItemCateRepository itemCateRepository;
    private final TripRepository tripRepository;
    private final CheckListRepository checkListRepository;

    /**
     * 아이템 등록
     */
    public CreateItemResponse createItem(CreateItemRequest request, Long userId) {

        User user = userRepository.findById(userId)
            .orElseThrow(() -> new NotFoundException(E404_NOT_EXISTS_USER));

        //지난 여행에 아이템 등록 불가
        Trip trip = tripRepository.findByPreviousIsNow(request.getTripId())
            .orElseThrow(() -> new NotFoundException(E404_NOT_EXISTS_NOW_TRIP));

        ItemCate itemCate = itemCateRepository.findById(request.getItemCateId())
            .orElseThrow(() -> new NotFoundException(E404_NOT_EXISTS_ITEM_CATE));

        if (trip.getUser().getId().equals(userId)) {
            Item newItem = request.toEntity(user, itemCate, trip);
            newItem.updateItemType(USER_ADDED);
            Item savedItem = itemRepository.save(newItem);
            return CreateItemResponse.of(savedItem);
        } else {
            throw new ForbiddenException(NOT_MY_TRIP);
        }
    }

    /**
     * 아이템 수정
     */
    public UpdateItemResponse updateItem(
            UpdateItemRequest request, Long itemId, String itemType, Long userId) {

        // 고정 아이템 수정 불가
        if (FIXED.name().equals(itemType)) {
            throw new BusinessException(NOT_ALLOWED_FIX_ITEM);
        }

        Item item = itemRepository.findById(itemId)
            .orElseThrow(() -> new NotFoundException(E404_NOT_EXISTS_ITEM));

        if (!item.getUser().getId().equals(userId)) {
            throw new ForbiddenException(E403_NOT_MY_ITEM);
        }

        if (!"NOW".equals(item.getTrip().getPrevious())) {
            throw new BusinessException(NOT_ALLOWED_PAST_ITEM);
        }

        item.updateItem(request.getItemName());

        return UpdateItemResponse.of(item);
    }

    /**
     * 아이템 삭제
     */
    public DeleteItemResponse deleteItem(Long itemId, Long tripId, String itemType, Long userId) {

        User user = userRepository.findById(userId)
            .orElseThrow(() -> new NotFoundException(E404_NOT_EXISTS_USER));

        Trip trip = tripRepository.findById(tripId)
            .orElseThrow(() -> new NotFoundException(E404_NOT_EXISTS_TRIP));

        if (USER_ADDED.name().equals(itemType)) {
            Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new NotFoundException(E404_NOT_EXISTS_ITEM));

            if (!"NOW".equals(item.getTrip().getPrevious())) {
                throw new BusinessException(NOT_ALLOWED_PAST_ITEM);
            }

            if (item.getUser().getId().equals(userId)) {
                itemRepository.deleteById(item.getId());
            } else {
                throw new ForbiddenException(E403_NOT_MY_ITEM);
            }

        } else if (FIXED.name().equals(itemType)) {
            // 고정 아이템 삭제시에는 item에 fixedItemDelYN 을 'Y'로 등록해준다
            FixedItem fixedItem = fixedItemRepository.findById(itemId)
                .orElseThrow(() -> new NotFoundException(E404_NOT_EXISTS_FIXED_ITEM));

            itemRepository.save(Item.builder()
                .itemName(fixedItem.getItemName())
                .user(user)
                .itemCate(fixedItem.getItemCate())
                .trip(trip)
                .fixedItem(fixedItem)
                .fixedItemDelYN("Y")
                .itemType(FIXED)
                .build());
        }

        return DeleteItemResponse.of(itemId);
    }

    /**
     * 체크리스트 > 아이템 일괄 조회
     */
    @Transactional(readOnly = true)
    public List<GetItemListResponse> getItemList(Long userId, Long tripId) {

        List<GetItemListResponse> itemList = itemRepository.getItemList(userId);
        List<GetCheckListResponse> checkList = checkListRepository.getCheckList(userId, tripId);

        // checkList의 itemId와 itemType을 조합하여 Set에 저장
        Set<String> checkListItemSet = checkList.stream()
                .map(item -> item.getItemId() + "_" + item.getItemType())
                .collect(Collectors.toSet());

        // itemList를 돌면서 checkList에 있는지 확인하여 addStatus response
        for (GetItemListResponse item : itemList) {
            String itemKey = item.getItemId() + "_" + item.getItemType();
            String addStatus = checkListItemSet.contains(itemKey) ? "ALREADY" : "NOT_YET";
            item.setAddStatus(addStatus);
        }

        return itemList;
    }

    /**
     * Previous 변경
     */
//    public void changeItemPrevious() {
//
//        List<Item> allPreviousIsNow = itemRepository.findAllPreviousIsNow();
//
//        allPreviousIsNow
//                .forEach(item -> {
//                    Long tripId = item.getTrip().getId();
//                    String maxPrevious = itemRepository.findMaxPrevious(tripId);
//                    if (maxPrevious != null) {
//                        int nextNum = Integer.parseInt(maxPrevious.substring(1)) + 1;
//                        String nextPrevious = "B" + String.format("%02d", nextNum);
//                        item.updatePrevious(nextPrevious);
//                    }
//                    if (item.getPrevious().equals("NOW")) {
//                        item.updatePrevious("B01");
//                    }
//                });
//    }

}
