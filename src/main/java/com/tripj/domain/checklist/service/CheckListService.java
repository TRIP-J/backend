package com.tripj.domain.checklist.service;

import com.tripj.domain.checklist.model.dto.*;
import com.tripj.domain.checklist.model.entity.CheckList;
import com.tripj.domain.checklist.repository.CheckListRepository;
import com.tripj.domain.item.model.entity.Item;
import com.tripj.domain.item.repository.ItemRepository;
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
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CheckListService {

    private final CheckListRepository checkListRepository;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;
    private final TripRepository tripRepository;

    /**
     * 체크리스트 조회
     */
    @Transactional(readOnly = true)
    public List<GetCheckListResponse> getCheckList(Long itemCateId,
                                                   Long userId,
                                                   Long countryId) {
        return checkListRepository.getCheckList(itemCateId, userId, countryId);
    }

    /**
     * 아이템을 체크리스트에 추가
     */
    public CreateCheckListResponse createCheckList(CreateCheckListRequest request,
                                                   Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.E404_NOT_EXISTS_USER));

        Trip trip = tripRepository.findById(request.getTripId())
                .orElseThrow(() -> new NotFoundException(ErrorCode.E404_NOT_EXISTS_TRIP));

        Item item = itemRepository.findById(request.getItemId())
                .orElseThrow(() -> new NotFoundException(ErrorCode.E404_NOT_EXISTS_ITEM));

        Optional<CheckList> existingCheckList =
                checkListRepository.findByUserIdAndItemIdAndPreviousNow(userId, request.getItemId());
        if (existingCheckList.isPresent()) {
            throw new BusinessException(ErrorCode.ALREADY_EXISTS_CHECKLIST);
        }

        CheckList savedCheckList = checkListRepository.save(request.toEntity(item, user, trip));

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

        if (checkList.getItem().getFix().equals("F")) {
            // fix가 "F"인 경우 바로 삭제
            checkListRepository.deleteById(checkListId);
        } else {
            // fix가 "F"가 아닌 경우, 사용자 ID 확인
            if (checkList.getItem().getUser().getId().equals(userId)) {
                checkListRepository.deleteById(checkListId);
            } else {
                throw new ForbiddenException("아이템 삭제 권한이 없습니다.", ErrorCode.E403_FORBIDDEN);
            }
        }
        
        return DeleteCheckListResponse.of(checkListId);
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
        Optional<CheckList> checkList = checkListRepository.findById(request.getChecklistId());
        if (checkList.isPresent()) {
            if (checkList.get().getPack().equals("NO")) {
                return true;
            } else {
                return false;
            }
        } else {
            return true;
        }
    }

    /**
     * 내 체크리스트 조회
     */
    @Transactional(readOnly = true)
    public List<GetMyCheckListResponse> getMyCheckList(Long itemCateId,
                                                       Long userId,
                                                       Long tripId) {

        return checkListRepository.getMyCheckList(itemCateId, userId, tripId);
    }

}













