package com.tripj.domain.item.service;

import com.tripj.domain.checklist.model.dto.response.GetCheckListResponse;
import com.tripj.domain.checklist.model.dto.response.GetItemListResponse;
import com.tripj.domain.checklist.repository.CheckListRepository;
import com.tripj.domain.item.constant.SetItemCate;
import com.tripj.domain.item.model.dto.request.CreateItemRequest;
import com.tripj.domain.item.model.dto.request.CreateSetItemRequest;
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
import com.tripj.global.error.exception.BusinessException;
import com.tripj.global.error.exception.ForbiddenException;
import com.tripj.global.error.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

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
     * 세트 아이템 등록
     */
    public List<GetItemListResponse> createSetItem(
            CreateSetItemRequest request, Long userId, SetItemCate setItemCate) {

        //TODO : 같은 세트 추가시 validation

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(E404_NOT_EXISTS_USER));

        //지난 여행에 아이템 등록 불가
        Trip trip = tripRepository.findByPreviousIsNow(request.getTripId())
                .orElseThrow(() -> new NotFoundException(E404_NOT_EXISTS_NOW_TRIP));

        ItemCate itemCate = itemCateRepository.findById(request.getItemCateId())
                .orElseThrow(() -> new NotFoundException(E404_NOT_EXISTS_ITEM_CATE));

        List<String> itemsToAdd = setItemsByCategory.get(setItemCate);

        if (!trip.getUser().getId().equals(userId)) {
            throw new ForbiddenException(NOT_MY_TRIP);
        }

        // 기존 추천템에 있던 아이템 전부 삭제
        itemRepository.deleteByTripIdAndItemCateId(trip.getId(), itemCate.getId());

        // 새로운 세트 아이템 추가
        for (String itemName : itemsToAdd) {
            Item setItem = request.toEntity(user, itemCate, trip, itemName);
            setItem.updateItemType(SET_ITEM);
            itemRepository.save(setItem);
        }

        // 등록 후 아이템 일괄 조회 API 호출
        return getItemList(userId, trip.getId());
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

        if (USER_ADDED.name().equals(itemType) || SET_ITEM.name().equals(itemType)) {
            deleteUserOrSetItem(itemId, userId);
        } else if (FIXED.name().equals(itemType)) {
            deleteFixedItem(itemId, user, trip);
        }

        return DeleteItemResponse.of(itemId);
    }

    /**
     * 사용자 추가 또는 세트 아이템 삭제 처리
     */
    private void deleteUserOrSetItem(Long itemId, Long userId) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new NotFoundException(E404_NOT_EXISTS_ITEM));

        if (!"NOW".equals(item.getTrip().getPrevious())) {
            throw new BusinessException(NOT_ALLOWED_PAST_ITEM);
        }

        if (item.getUser().getId().equals(userId)) {
            checkListRepository.deleteByItemId(itemId);
            itemRepository.deleteById(item.getId());
        } else {
            throw new ForbiddenException(E403_NOT_MY_ITEM);
        }
    }

    /**
     * 고정 아이템 삭제 처리
     */
    private void deleteFixedItem(Long itemId, User user, Trip trip) {
        FixedItem fixedItem = fixedItemRepository.findById(itemId)
                .orElseThrow(() -> new NotFoundException(E404_NOT_EXISTS_FIXED_ITEM));

        checkListRepository.deleteByFixedItemId(fixedItem.getId());

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

    private static final Map<SetItemCate, List<String>> setItemsByCategory = Map.of(
            SetItemCate.BASIC, List.of("기본세트 예시1", "기본세트예시2", "기본세트예시3"),
            SetItemCate.PEDDLER, List.of("보부상세트 예시1", "보부상세트 예시2, 보부상세트 예시3"),
            SetItemCate.MINIMAL, List.of("미니멀세트 예시1", "미니멀세트 예시2")
    );

}
