package com.tripj.domain.checklist.service;

import com.tripj.domain.checklist.model.dto.CreateCheckListRequest;
import com.tripj.domain.checklist.model.dto.CreateCheckListResponse;
import com.tripj.domain.checklist.model.dto.GetCheckListResponse;
import com.tripj.domain.checklist.model.entity.CheckList;
import com.tripj.domain.checklist.repository.CheckListRepository;
import com.tripj.domain.item.model.entity.Item;
import com.tripj.domain.item.repository.ItemRepository;
import com.tripj.domain.user.model.entity.User;
import com.tripj.domain.user.repository.UserRepository;
import com.tripj.global.code.ErrorCode;
import com.tripj.global.error.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CheckListService {

    private final CheckListRepository checkListRepository;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;

    //체크리스트 조회
    @Transactional(readOnly = true)
    public List<GetCheckListResponse> getCheckList(Long itemCateId, Long userId) {
        return checkListRepository.getCheckList(itemCateId, userId);
    }

    //아이템을 체크리스트에 추가
    public CreateCheckListResponse createCheckList(CreateCheckListRequest request,
                                                   Long userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 회원입니다.", ErrorCode.E404_NOT_EXISTS_USER));

        Item item = itemRepository.findById(request.getItemId())
                .orElseThrow(() -> new NotFoundException("존재하지 않는 아이템입니다.", ErrorCode.E404_NOT_EXISTS_ITEM));

        CheckList savedCheckList = checkListRepository.save(request.toEntity(item));

        return CreateCheckListResponse.of(savedCheckList.getId());
    }





}
