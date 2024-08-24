package com.tripj.domain.checklist.service;

import com.tripj.domain.checklist.model.dto.request.CreateCheckListRequest;
import com.tripj.domain.checklist.model.dto.response.*;
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
import java.util.stream.Collectors;

import static com.tripj.global.code.ErrorCode.*;

@Service
@Transactional
@RequiredArgsConstructor
public class CheckListService {

    private final CheckListRepository checkListRepository;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;
    private final TripRepository tripRepository;

    /**
     * 체크리스트에 담은 아이템 일괄 조회
     */
    @Transactional(readOnly = true)
    public List<GetCheckListResponse> getCheckList(
            Long userId, Long tripId) {

        List<GetCheckListResponse> checkList = checkListRepository.getCheckList(userId, tripId);

        if (checkList.isEmpty()) {
            throw new NotFoundException(E404_NOT_EXISTS_CHECKLIST);
        }

        return checkList;
    }

    /**
     * 아이템을 체크리스트에 추가
     */
    public CreateCheckListResponse createCheckList(CreateCheckListRequest request,
                                                   Long userId) {

        User user = userRepository.findById(userId)
            .orElseThrow(() -> new NotFoundException(E404_NOT_EXISTS_USER));

        // 지난 여행에 아이템을 체크리스트에 등록 불가
        Trip trip = tripRepository.findByPreviousIsNow(request.getTripId())
            .orElseThrow(() -> new NotFoundException(E404_NOT_EXISTS_NOW_TRIP));

        // 고정 아이템 + 자신의 현재 아이템만 추가 가능
        Item item = itemRepository.findItemsByUserAndPreviousIsNow(request.getItemId(), userId)
            .orElseThrow(() -> new NotFoundException(E404_NOT_EXISTS_NOW_ITEM));

        // 중복 체크
        Optional<CheckList> existingCheckList =
                checkListRepository.findCheckListByUserItemAndCurrentTrip(userId, request.getItemId(), trip.getId());
        if (existingCheckList.isPresent()) {
            throw new BusinessException(ALREADY_EXISTS_CHECKLIST);
        }

        if (trip.getUser().getId().equals(userId)) {
            CheckList savedCheckList = checkListRepository.save(request.toEntity(item, user, trip));
            return CreateCheckListResponse.of(savedCheckList);
        } else {
            throw new ForbiddenException(NOT_MY_CHECKLIST);
        }
    }

    /**
     * 아이템을 체크리스트에서 삭제
     */
    public DeleteCheckListResponse deleteCheckList(Long checklistId, Long userId) {

        userRepository.findById(userId)
            .orElseThrow(() -> new NotFoundException(E404_NOT_EXISTS_USER));

        CheckList checkList = checkListRepository.findById(checklistId)
            .orElseThrow(() -> new NotFoundException(E404_NOT_EXISTS_CHECKLIST));

        if (!checkList.getUser().getId().equals(userId)) {
            throw new ForbiddenException(E403_NOT_MY_CHECKLIST);
        }
        checkListRepository.deleteById(checklistId);

        return DeleteCheckListResponse.of(checklistId);
    }

    /**
     * 체크리스트에 추가한 아이템 체크박스 클릭
     */
    public PackCheckListResponse packCheckList(Long checklistId, Long userId) {

        userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(E404_NOT_EXISTS_USER));

        CheckList checkList = checkListRepository.findById(checklistId)
                .orElseThrow(() -> new NotFoundException(E404_NOT_EXISTS_CHECKLIST));

        if (!checkList.getUser().getId().equals(userId)) {
            throw new ForbiddenException(E403_NOT_MY_CHECKLIST);
        }

        //체크박스 누르기
        boolean addPack = addPack(checklistId);

        if (addPack == true) {
            //true이면 YES 로 변경
            checkList.updatePack(checklistId,"YES");
        } else {
            //false이면 다시 NO로 변경
            checkList.updatePack(checklistId,"NO");
        }
        return PackCheckListResponse.of(checkList);
    }

    private boolean addPack(Long checklistId) {
        //이미 챙긴 아이템인지 확인
        if (validatePacked(checklistId)) {
            return true;
        } else {
            //이미 챙겼으면 체크박스 취소
            return false;
        }
    }

    private boolean validatePacked(Long checklistId) {
//        return checkListRepository.findByItemIdAndChecklistIdAndPack(request.getItemId(), request.getCheckListId(), "NO").isEmpty();
        Optional<CheckList> checkList = checkListRepository.findById(checklistId);
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
     * Previous 변경
     */
//    public void changeCheckListPrevious() {
//        List<CheckList> allPreviousIsNow = checkListRepository.findAllPreviousIsNow();
//
//        allPreviousIsNow
//                .forEach(checkList -> {
//                    Long tripId = checkList.getTrip().getId();
//                    String maxPrevious = checkListRepository.findMaxPrevious(tripId);
//                    if (maxPrevious != null) {
//                        int nextNum = Integer.parseInt(maxPrevious.substring(1)) + 1;
//                        String nextPrevious = "B" + String.format("%02d", nextNum);
//                        checkList.updatePrevious(nextPrevious);
//                    }
//                    if (checkList.getPrevious().equals("NOW")) {
//                        checkList.updatePrevious("B01");
//                    }
//                }
//        );
//    }
}