package com.tripj.domain.checklist.service;

import com.tripj.domain.checklist.model.dto.*;
import com.tripj.domain.checklist.model.entity.CheckList;
import com.tripj.domain.checklist.repository.CheckListRepository;
import com.tripj.domain.item.model.entity.Item;
import com.tripj.domain.item.repository.ItemRepository;
import com.tripj.domain.user.model.entity.User;
import com.tripj.domain.user.repository.UserRepository;
import com.tripj.global.code.ErrorCode;
import com.tripj.global.error.exception.ForbiddenException;
import com.tripj.global.error.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CheckListService {

    private final CheckListRepository checkListRepository;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;

    /**
     * 체크리스트 조회
     */
    @Transactional(readOnly = true)
    public List<GetCheckListResponse> getCheckList(Long itemCateId, Long userId) {
        return checkListRepository.getCheckList(itemCateId, userId);
    }

    /**
     * 아이템을 체크리스트에 추가
     */
    public CreateCheckListResponse createCheckList(CreateCheckListRequest request,
                                                   Long userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 회원입니다.", ErrorCode.E404_NOT_EXISTS_USER));

        Item item = itemRepository.findById(request.getItemId())
                .orElseThrow(() -> new NotFoundException("존재하지 않는 아이템입니다.", ErrorCode.E404_NOT_EXISTS_ITEM));

        CheckList savedCheckList = checkListRepository.save(request.toEntity(item));

        return CreateCheckListResponse.of(savedCheckList.getId());
    }

    /**
     * 아이템을 체크리스트에서 삭제
     */
    public DeleteCheckListResponse deleteCheckList(Long checkListId, Long userId) {

        userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.E404_NOT_EXISTS_USER));

        CheckList checkList = checkListRepository.findById(checkListId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.E404_NOT_EXISTS_CHECKLIST));

        if (checkList.getItem().getUser().getId().equals(userId)) {
            checkListRepository.deleteById(checkListId);
            return DeleteCheckListResponse.of(checkListId);
        } else {
            throw new ForbiddenException("아이템 삭제 권한이 없습니다.", ErrorCode.E403_FORBIDDEN);
        }
    }

    /**
     * 체크리스트에 추가한 아이템 체크박스 클릭
     */
    public PackCheckListResponse packCheckList(PackCheckListRequest request, Long userId) {

        userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.E404_NOT_EXISTS_USER));

        CheckList checkList = checkListRepository.findById(request.getChecklistId())
                .orElseThrow(() -> new NotFoundException(ErrorCode.E404_NOT_EXISTS_CHECKLIST));

        //체크박스 누르기
        boolean addPack = addPack(request);

        if (addPack == true) {
            //true이면 YES 로 변경
            checkList.updatePack(request.getChecklistId(),"YES");
        } else {
            //false이면 다시 NO로 변경
            checkList.updatePack(request.getChecklistId(),"NO");
        }
        return PackCheckListResponse.of(checkList.getId(), checkList.getPack());
    }

    private boolean addPack(PackCheckListRequest request) {
        //이미 챙긴 아이템인지 확인
        if (validatePacked(request)) {
            return true;
        } else {
            //이미 챙겼으면 체크박스 취소
            return false;
        }
    }

    private boolean validatePacked(PackCheckListRequest request) {
//        return checkListRepository.findByItemIdAndChecklistIdAndPack(request.getItemId(), request.getCheckListId(), "NO").isEmpty();
        Optional<PackCheckListResponse> packedCheckList = checkListRepository.findByItemIdAndId(request.getItemId(), request.getChecklistId());
        if (packedCheckList.isPresent()) {
            if (packedCheckList.get().getPack().equals("NO")) {
                return true;
            } else {
                return false;
            }
        } else {
            return true;
        }
    }

}
