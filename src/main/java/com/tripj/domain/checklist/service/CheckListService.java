package com.tripj.domain.checklist.service;

import com.tripj.domain.checklist.model.dto.GetCheckListResponse;
import com.tripj.domain.checklist.repository.CheckListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CheckListService {

    private final CheckListRepository checkListRepository;

    //체크리스트 조회
    public List<GetCheckListResponse> getCheckList(Long itemCateId, Long userId) {
        return checkListRepository.getCheckList(itemCateId, userId);
    }




}
