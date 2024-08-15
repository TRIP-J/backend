package com.tripj.domain.checklist.service;

import com.tripj.domain.checklist.model.dto.request.CreateCheckListRequest;
import com.tripj.domain.checklist.model.dto.response.CreateCheckListResponse;
import com.tripj.domain.checklist.model.dto.response.DeleteCheckListResponse;
import com.tripj.domain.checklist.model.dto.response.GetCheckListResponse;
import com.tripj.domain.checklist.model.dto.response.PackCheckListResponse;
import com.tripj.domain.checklist.model.entity.CheckList;
import com.tripj.domain.checklist.repository.CheckListRepository;
import com.tripj.domain.country.model.entity.Country;
import com.tripj.domain.country.repository.CountryRepository;
import com.tripj.domain.item.model.dto.request.CreateItemRequest;
import com.tripj.domain.item.model.dto.response.CreateItemResponse;
import com.tripj.domain.item.model.entity.Item;
import com.tripj.domain.item.repository.ItemRepository;
import com.tripj.domain.item.service.ItemService;
import com.tripj.domain.itemcate.model.entity.ItemCate;
import com.tripj.domain.itemcate.repository.ItemCateRepository;
import com.tripj.domain.trip.model.dto.request.CreateTripRequest;
import com.tripj.domain.trip.model.dto.response.CreateTripResponse;
import com.tripj.domain.trip.model.entity.Trip;
import com.tripj.domain.trip.repository.TripRepository;
import com.tripj.domain.trip.service.TripService;
import com.tripj.domain.user.constant.Role;
import com.tripj.domain.user.constant.UserType;
import com.tripj.domain.user.model.entity.User;
import com.tripj.domain.user.repository.UserRepository;
import com.tripj.global.code.ErrorCode;
import com.tripj.global.error.exception.BusinessException;
import com.tripj.global.error.exception.ForbiddenException;
import com.tripj.global.error.exception.NotFoundException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.*;


@SpringBootTest
@ActiveProfiles("test")
class CheckListServiceTest {
    @Autowired
    private TripService tripService;
    @Autowired
    private TripRepository tripRepository;
    @Autowired
    private ItemService itemService;
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private ItemCateRepository itemCateRepository;
    @Autowired
    private CountryRepository countryRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CheckListService checkListService;
    @Autowired
    private CheckListRepository checkListRepository;

    private Country country;
    private User user;
    private ItemCate itemCate;
    private Item item;

    @BeforeEach
    void setUp() {
        country = Country.builder()
                .name("일본")
                .code("JP")
                .build();
        countryRepository.save(country);

        user = User.builder()
                .userType(UserType.KAKAO)
                .email("asdf@naver.com")
                .nickname("다람지기엽지")
                .userName("홍길동")
                .role(Role.ROLE_USER)
                .build();
        userRepository.save(user);

        itemCate = ItemCate.builder()
                .itemCateName("필수품")
                .itemCateCode("ESS")
                .build();
        itemCateRepository.save(itemCate);

        item = Item.builder()
                .itemName("여권")
                .itemCate(itemCate)
                .fix("F")
                .build();
        itemRepository.save(item);

    }

    @AfterEach
    void tearDown() {
        checkListRepository.deleteAllInBatch();
        itemRepository.deleteAllInBatch();
        itemCateRepository.deleteAllInBatch();
        tripRepository.deleteAllInBatch();
        countryRepository.deleteAllInBatch();
        userRepository.deleteAllInBatch();
    }

    @Nested
    class createCheckList {

        @Test
        @DisplayName("아이템을 체크리스트에 추가 합니다.")
        void createCheckList() {
            //given
            CreateTripRequest createTripRequest =
                    createTripRequest(country.getId(), LocalDate.of(2022, 10, 1), LocalDate.now().plusDays(1));
            CreateTripResponse trip = tripService.createTrip(createTripRequest, user.getId());

            CreateItemRequest itemRequest = createItemRequest("고데기", trip.getTripId(), "N");
            CreateItemResponse item = itemService.createItem(itemRequest, user.getId());

            CreateCheckListRequest checkListRequest = createCheckListRequest(item.getItemId(), trip.getTripId());

            //when
            CreateCheckListResponse checkList = checkListService.createCheckList(checkListRequest, user.getId());

            //then
            assertThat(checkList).isNotNull();
            assertThat(checkList.getItemId()).isEqualTo(item.getItemId());
            assertThat(checkList.getTripId()).isEqualTo(trip.getTripId());
            assertThat(checkList.getCheckListId()).isEqualTo(checkList.getCheckListId());
        }

        @Test
        @DisplayName("존재하지 않는 회원이 체크리스트 추가시 예외가 발생합니다.")
        void createCheckListNotExistingUser() {
            //given
            CreateTripRequest createTripRequest =
                    createTripRequest(country.getId(), LocalDate.of(2022, 10, 1), LocalDate.now().plusDays(1));
            CreateTripResponse trip = tripService.createTrip(createTripRequest, user.getId());

            CreateItemRequest itemRequest = createItemRequest("고데기", trip.getTripId(), "N");
            CreateItemResponse item = itemService.createItem(itemRequest, user.getId());

            CreateCheckListRequest checkListRequest = createCheckListRequest(item.getItemId(), trip.getTripId());
            checkListService.createCheckList(checkListRequest, user.getId());

            //when //then
            assertThatThrownBy(() -> checkListService.createCheckList(checkListRequest, 2L))
                    .isInstanceOf(NotFoundException.class)
                    .hasMessage(ErrorCode.E404_NOT_EXISTS_USER.getMessage());
        }

        @Test
        @DisplayName("지난 여행 계획에 아이템을 체크리스트에 추가시 예외가 발생합니다.")
        void createCheckListWhenPreviousTrip() {
            //given
            CreateTripRequest createTripRequest =
                    createTripRequest(country.getId(), LocalDate.of(2022, 10, 1), LocalDate.now());
            CreateTripResponse trip = tripService.createTrip(createTripRequest, user.getId());

            CreateItemRequest itemRequest = createItemRequest("고데기", trip.getTripId(), "N");
            CreateItemResponse item = itemService.createItem(itemRequest, user.getId());

            tripService.changeTripPrevious();

            CreateCheckListRequest checkListRequest = createCheckListRequest(item.getItemId(), trip.getTripId());

            //when //then
            assertThatThrownBy(() -> checkListService.createCheckList(checkListRequest, user.getId()))
                    .isInstanceOf(NotFoundException.class)
                    .hasMessage(ErrorCode.E404_NOT_EXISTS_NOW_TRIP.getMessage());
        }

        @Test
        @DisplayName("중복된 아이템을 체크리스트에 추가시 예외가 발생합니다.")
        void createCheckListExistingItem() {
            //given
            CreateTripRequest createTripRequest =
                    createTripRequest(country.getId(), LocalDate.of(2022, 10, 1), LocalDate.now().plusDays(1));
            CreateTripResponse trip = tripService.createTrip(createTripRequest, user.getId());

            CreateItemRequest itemRequest = createItemRequest("고데기", trip.getTripId(), "N");
            CreateItemResponse item = itemService.createItem(itemRequest, user.getId());

            CreateCheckListRequest checkListRequest = createCheckListRequest(item.getItemId(), trip.getTripId());
            checkListService.createCheckList(checkListRequest, user.getId());
            CreateCheckListRequest checkListRequest2 = createCheckListRequest(item.getItemId(), trip.getTripId());

            //when //then
            assertThatThrownBy(() -> checkListService.createCheckList(checkListRequest2, user.getId()))
                    .isInstanceOf(BusinessException.class)
                    .hasMessage(ErrorCode.ALREADY_EXISTS_CHECKLIST.getMessage());
        }

        @Test
        @DisplayName("자신이 등록한 여행 계획이 아니면 체크리스트 추가시 예외가 발생합니다.")
        void createCheckListNotMyTrip() {
            //given
            CreateTripRequest createTripRequest =
                    createTripRequest(country.getId(), LocalDate.of(2022, 10, 1), LocalDate.now());
            CreateTripResponse trip = tripService.createTrip(createTripRequest, user.getId());

            User savedUser = userRepository.save(
                    User.builder()
                            .userType(UserType.KAKAO)
                            .email("asdf2@naver.com")
                            .nickname("다람지기엽지2")
                            .userName("홍길동2")
                            .role(Role.ROLE_USER)
                            .build());

            CreateItemRequest itemRequest = createItemRequest("고데기", trip.getTripId(), "N");
            CreateItemResponse item = itemService.createItem(itemRequest, user.getId());

            CreateCheckListRequest checkListRequest = createCheckListRequest(item.getItemId(), trip.getTripId());
            checkListService.createCheckList(checkListRequest, user.getId());

            //when //then
            assertThatThrownBy(() -> checkListService.createCheckList(checkListRequest, savedUser.getId()))
                    .isInstanceOf(ForbiddenException.class)
                    .hasMessage(ErrorCode.NOT_MY_CHECKLIST.getMessage());
        }
    }

    @Nested
    class deleteCheckList {

        @Test
        @DisplayName("체크리스트에서 아이템을 삭제시 성공 합니다.")
        void deleteCheckList () {
            //given
            CreateTripRequest createTripRequest =
                    createTripRequest(country.getId(), LocalDate.of(2022, 10, 1), LocalDate.now().plusDays(1));
            CreateTripResponse trip = tripService.createTrip(createTripRequest, user.getId());

            CreateItemRequest itemRequest = createItemRequest("고데기", trip.getTripId(), "N");
            CreateItemResponse item = itemService.createItem(itemRequest, user.getId());

            CreateCheckListRequest checkListRequest = createCheckListRequest(item.getItemId(), trip.getTripId());
            CreateCheckListResponse checkList = checkListService.createCheckList(checkListRequest, user.getId());

            //when
            DeleteCheckListResponse deleteCheckListResponse = checkListService.deleteCheckList(checkList.getCheckListId(), user.getId());

            //then
            assertThat(deleteCheckListResponse.getCheckListId()).isEqualTo(checkList.getCheckListId());
        }

        @Test
        @DisplayName("존재하지 않는 회원이 체크리스트에서 아이템을 삭제시 예외 발생합니다.")
        void deleteCheckListNotExistingUser () {
            //given
            CreateTripRequest createTripRequest =
                    createTripRequest(country.getId(), LocalDate.of(2022, 10, 1), LocalDate.now().plusDays(1));
            CreateTripResponse trip = tripService.createTrip(createTripRequest, user.getId());

            CreateItemRequest itemRequest = createItemRequest("고데기", trip.getTripId(), "N");
            CreateItemResponse item = itemService.createItem(itemRequest, user.getId());

            CreateCheckListRequest checkListRequest = createCheckListRequest(item.getItemId(), trip.getTripId());
            CreateCheckListResponse checkList = checkListService.createCheckList(checkListRequest, user.getId());

            //when //then
            assertThatThrownBy(() -> checkListService.deleteCheckList(checkList.getCheckListId(), 2L))
                    .isInstanceOf(NotFoundException.class)
                    .hasMessage(ErrorCode.E404_NOT_EXISTS_USER.getMessage());
        }

        @Test
        @DisplayName("존재하지 않는 체크리스트에서 아이템을 삭제시 예외가 발생합니다.")
        void deleteCheckListNotExistingCheckList () {
            //given
            CreateTripRequest createTripRequest =
                    createTripRequest(country.getId(), LocalDate.of(2022, 10, 1), LocalDate.now().plusDays(1));
            CreateTripResponse trip = tripService.createTrip(createTripRequest, user.getId());

            CreateItemRequest itemRequest = createItemRequest("고데기", trip.getTripId(), "N");
            CreateItemResponse item = itemService.createItem(itemRequest, user.getId());

            //when //then
            assertThatThrownBy(() -> checkListService.deleteCheckList(1L, user.getId()))
                    .isInstanceOf(NotFoundException.class)
                    .hasMessage(ErrorCode.E404_NOT_EXISTS_CHECKLIST.getMessage());
        }

        @Test
        @DisplayName("체크리스트에서 고정이 아닌 아이템을 삭제시 회원정보가 다르면 예외가 발생합니다.")
        void deleteNotMyCheckList () {
            //given
            CreateTripRequest createTripRequest =
                    createTripRequest(country.getId(), LocalDate.of(2022, 10, 1), LocalDate.now().plusDays(1));
            CreateTripResponse trip = tripService.createTrip(createTripRequest, user.getId());

            User savedUser = userRepository.save(
                    User.builder()
                            .userType(UserType.KAKAO)
                            .email("asdf2@naver.com")
                            .nickname("다람지기엽지2")
                            .userName("홍길동2")
                            .role(Role.ROLE_USER)
                            .build());

            CreateItemRequest itemRequest = createItemRequest("고데기", trip.getTripId(), "N");
            CreateItemResponse item = itemService.createItem(itemRequest, user.getId());

            CreateCheckListRequest checkListRequest = createCheckListRequest(item.getItemId(), trip.getTripId());
            CreateCheckListResponse checkList = checkListService.createCheckList(checkListRequest, user.getId());

            //when //then
            assertThatThrownBy(() -> checkListService.deleteCheckList(checkList.getCheckListId(), savedUser.getId()))
                    .isInstanceOf(ForbiddenException.class)
                    .hasMessage(ErrorCode.E403_NOT_MY_CHECKLIST.getMessage());
        }
    }

    @Nested
    class packCheckList {

        @Test
        @DisplayName("체크리스트에서 추가한 아이템 체크박스 클릭시 챙김 상태가 성공합니다.")
        void packCheckListStatusIsNO() {
            //given
            CreateTripRequest createTripRequest =
                    createTripRequest(country.getId(), LocalDate.of(2022, 10, 1), LocalDate.now().plusDays(1));
            CreateTripResponse trip = tripService.createTrip(createTripRequest, user.getId());

            CreateItemRequest itemRequest = createItemRequest("고데기", trip.getTripId(), "N");
            CreateItemResponse item = itemService.createItem(itemRequest, user.getId());

            CreateCheckListRequest checkListRequest = createCheckListRequest(item.getItemId(), trip.getTripId());
            CreateCheckListResponse checkList = checkListService.createCheckList(checkListRequest, user.getId());

            //when
            PackCheckListResponse packCheckListResponse = checkListService.packCheckList(checkList.getCheckListId(), user.getId());

            //then
            assertThat(packCheckListResponse.getChecklistId()).isEqualTo(checkList.getCheckListId());
            assertThat(packCheckListResponse.getPack()).isEqualTo("YES");
        }

        @Test
        @DisplayName("체크리스트에서 챙긴 아이템 체크박스 클릭시 챙김 상태가 취소됩니다.")
        void packCheckListStatusIsYES() {
            //given
            CreateTripRequest createTripRequest =
                    createTripRequest(country.getId(), LocalDate.of(2022, 10, 1), LocalDate.now().plusDays(1));
            CreateTripResponse trip = tripService.createTrip(createTripRequest, user.getId());

            CreateItemRequest itemRequest = createItemRequest("고데기", trip.getTripId(), "N");
            CreateItemResponse item = itemService.createItem(itemRequest, user.getId());

            CreateCheckListRequest checkListRequest = createCheckListRequest(item.getItemId(), trip.getTripId());
            CreateCheckListResponse checkList = checkListService.createCheckList(checkListRequest, user.getId());

            checkListService.packCheckList(checkList.getCheckListId(), user.getId());

            //when
            PackCheckListResponse packCheckListResponse2 = checkListService.packCheckList(checkList.getCheckListId(), user.getId());

            //then
            assertThat(packCheckListResponse2.getChecklistId()).isEqualTo(checkList.getCheckListId());
            assertThat(packCheckListResponse2.getPack()).isEqualTo("NO");
        }
    }



    @Disabled
    @Test
    @DisplayName("자신의 카테고리별 체크리스트를 조회 합니다.")
    void getCheckList() {
        //given
        CreateTripRequest createTripRequest =
                createTripRequest(country.getId(), LocalDate.of(2022, 10, 1), LocalDate.now().plusDays(1));
        CreateTripResponse trip = tripService.createTrip(createTripRequest, user.getId());

        CreateItemRequest itemRequest = createItemRequest("고데기", trip.getTripId(), "N");
        CreateItemResponse item = itemService.createItem(itemRequest, user.getId());
        CreateItemRequest fixedItemRequest = createItemRequest("여권", trip.getTripId(), "F");
        CreateItemResponse fixedItem = itemService.createItem(fixedItemRequest, user.getId());

        CreateCheckListRequest checkListRequest = createCheckListRequest(item.getItemId(), trip.getTripId());
        checkListService.createCheckList(checkListRequest, user.getId());
        CreateCheckListRequest checkListRequest2 = createCheckListRequest(fixedItem.getItemId(), trip.getTripId());
        checkListService.createCheckList(checkListRequest2, user.getId());

        //when
        List<GetCheckListResponse> checkList = checkListService.getCheckList(itemCate.getId(), user.getId(), country.getId());

        //then
        assertThat(checkList).hasSize(2)
                .extracting("itemName", "itemCateName", "fix")
                .containsExactlyInAnyOrder(
                        tuple("고데기", "필수품", "N"),
                        tuple("여권", "필수품", "F")
                );
    }

    private CreateCheckListRequest createCheckListRequest(Long itemId, Long tripId) {
        return CreateCheckListRequest.builder()
                .itemId(itemId)
                .tripId(tripId)
                .build();
    }

    private CreateTripRequest createTripRequest(Long countryId, LocalDate startDate, LocalDate endDate) {
        return CreateTripRequest.builder()
                .startDate(startDate)
                .endDate(endDate)
                .countryId(countryId)
                .build();
    }

    private CreateItemRequest createItemRequest(String itemName, Long tripId, String fix) {
        return CreateItemRequest.builder()
                .itemName(itemName)
                .previous("NOW")
                .fix(fix)
                .countryId(country.getId())
                .itemCateId(itemCate.getId())
                .tripId(tripId)
                .build();
    }



}