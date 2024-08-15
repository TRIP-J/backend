package com.tripj.domain.item.service;

import com.tripj.domain.country.model.entity.Country;
import com.tripj.domain.country.repository.CountryRepository;
import com.tripj.domain.item.model.dto.request.CreateItemRequest;
import com.tripj.domain.item.model.dto.request.UpdateItemRequest;
import com.tripj.domain.item.model.dto.response.CreateItemResponse;
import com.tripj.domain.item.model.dto.response.UpdateItemResponse;
import com.tripj.domain.item.repository.ItemRepository;
import com.tripj.domain.itemcate.model.entity.ItemCate;
import com.tripj.domain.itemcate.repository.ItemCateRepository;
import com.tripj.domain.trip.model.dto.request.CreateTripRequest;
import com.tripj.domain.trip.model.dto.response.CreateTripResponse;
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
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@ActiveProfiles("test")
class ItemServiceTest {
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

    private Country country;
    private User user;
    private ItemCate itemCate;

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
    }

    @AfterEach
    void tearDown() {
        itemRepository.deleteAllInBatch();
        itemCateRepository.deleteAllInBatch();
        tripRepository.deleteAllInBatch();
        countryRepository.deleteAllInBatch();
        userRepository.deleteAllInBatch();
    }

    @Test
    @DisplayName("현재 여혱 게획에 아이템을 등록합니다.")
    void createItemWhenPreviousIsNow()  {
        //given
        CreateTripRequest createTripRequest =
                createTripRequest(country.getId(), LocalDate.of(2022, 10, 1), LocalDate.now().plusDays(1));
        CreateTripResponse trip = tripService.createTrip(createTripRequest, user.getId());

        CreateItemRequest itemRequest = createItemRequest("고데기", trip.getTripId());

        //when
        CreateItemResponse item = itemService.createItem(itemRequest, user.getId());

        //then
        assertThat(item).isNotNull();
        assertThat(item.getItemName()).isEqualTo("고데기");
        assertThat(item.getPrevious()).isEqualTo("NOW");
    }

    @Test
    @DisplayName("지난 여혱 게획에 아이템을 등록시 예외가 발생한다.")
    void createItemWhenPreviousIsNotNow()  {
        //given
        CreateTripRequest createTripRequest =
                createTripRequest(country.getId(), LocalDate.of(2022, 10, 1), LocalDate.now());
        CreateTripResponse trip = tripService.createTrip(createTripRequest, user.getId());
        tripService.changeTripPrevious();

        CreateItemRequest itemRequest = createItemRequest("고데기", trip.getTripId());

        //when //then
        assertThatThrownBy(() -> itemService.createItem(itemRequest, user.getId()))
                .isInstanceOf(NotFoundException.class)
                .hasMessage(ErrorCode.E404_NOT_EXISTS_NOW_TRIP.getMessage());
    }

    @Nested
    class updateItem {

        @Test
        @DisplayName("아이템 수정이 성공한다.")
        void updateItem()  {
            //given
            CreateTripRequest createTripRequest =
                    createTripRequest(country.getId(), LocalDate.of(2022, 10, 1), LocalDate.now().plusDays(1));
            CreateTripResponse trip = tripService.createTrip(createTripRequest, user.getId());

            CreateItemRequest itemRequest = createItemRequest("고데기", trip.getTripId());
            CreateItemResponse item = itemService.createItem(itemRequest, user.getId());

            //when
            UpdateItemResponse updateItemResponse = itemService.updateItem(
                    UpdateItemRequest.builder()
                            .itemName("드라이기")
                            .build(),
                    item.getItemId(),
                    user.getId()
            );

            //then
            assertThat(updateItemResponse).isNotNull();
            assertThat(updateItemResponse.getItemName()).isEqualTo("드라이기");
        }

        @Test
        @DisplayName("존재하지 않는 아이템이면 수정 실패한다.")
        void updateNotExistItem() {
            //given
            CreateTripRequest createTripRequest =
                    createTripRequest(country.getId(), LocalDate.of(2022, 10, 1), LocalDate.now().plusDays(1));
            CreateTripResponse trip = tripService.createTrip(createTripRequest, user.getId());

            //when //then
            assertThatThrownBy(() -> itemService.updateItem(
                    UpdateItemRequest.builder()
                            .itemName("드라이기")
                            .build(),
                    1L,
                    user.getId()))
                    .isInstanceOf(NotFoundException.class)
                    .hasMessage(ErrorCode.E404_NOT_EXISTS_ITEM.getMessage());
        }

        @Test
        @DisplayName("자신이 등록한 아이템이 아니면 수정시 예외가 발생한다.")
        void updateNotMyItem() {
            //given
            CreateTripRequest createTripRequest =
                    createTripRequest(country.getId(), LocalDate.of(2022, 10, 1), LocalDate.now().plusDays(1));
            CreateTripResponse trip = tripService.createTrip(createTripRequest, user.getId());

            CreateItemRequest itemRequest = createItemRequest("고데기", trip.getTripId());
            CreateItemResponse item = itemService.createItem(itemRequest, user.getId());

            //when //then
            assertThatThrownBy(() -> itemService.updateItem(
                    UpdateItemRequest.builder()
                            .itemName("드라이기")
                            .build(),
                    item.getItemId(),
                    2L))
                    .isInstanceOf(ForbiddenException.class)
                    .hasMessage(ErrorCode.E403_NOT_MY_ITEM.getMessage());
        }
    }

    @Nested
    class deleteItem {

        @Test
        @DisplayName("아이템 삭제에 성공한다.")
        void deleteItem() {
            //given
            CreateTripRequest createTripRequest =
                    createTripRequest(country.getId(), LocalDate.of(2022, 10, 1), LocalDate.now().plusDays(1));
            CreateTripResponse trip = tripService.createTrip(createTripRequest, user.getId());

            CreateItemRequest itemRequest = createItemRequest("고데기", trip.getTripId());
            CreateItemResponse item = itemService.createItem(itemRequest, user.getId());

            //when
            itemService.deleteItem(item.getItemId(), user.getId());

            //then
            assertThat(itemRepository.findById(item.getItemId())).isEmpty();
        }

        @Test
        @DisplayName("지난 여행 계획의 아이템을 삭제시 예외가 발생한다.")
        void deleteItemFromPreviousTrip() {
            //given
            CreateTripRequest createTripRequest =
                    createTripRequest(country.getId(), LocalDate.of(2022, 10, 1), LocalDate.now());
            CreateTripResponse trip = tripService.createTrip(createTripRequest, user.getId());

            CreateItemRequest itemRequest = createItemRequest("고데기", trip.getTripId());
            CreateItemResponse item = itemService.createItem(itemRequest, user.getId());

            tripService.changeTripPrevious();

            //when //then
            assertThatThrownBy(() -> itemService.deleteItem(item.getItemId(), user.getId()))
                    .isInstanceOf(BusinessException.class)
                    .hasMessage(ErrorCode.NOT_ALLOWED_PAST_ITEM.getMessage());
        }

        @Test
        @DisplayName("자신이 등록한 아이템이 아니면 삭제시 예외가 발생한다.")
        void deleteNotMyItem() {
            //given
            CreateTripRequest createTripRequest =
                    createTripRequest(country.getId(), LocalDate.of(2022, 10, 1), LocalDate.now().plusDays(1));
            CreateTripResponse trip = tripService.createTrip(createTripRequest, user.getId());

            CreateItemRequest itemRequest = createItemRequest("고데기", trip.getTripId());
            CreateItemResponse item = itemService.createItem(itemRequest, user.getId());

            //when //then
            assertThatThrownBy(() -> itemService.deleteItem(item.getItemId(), 2L))
                    .isInstanceOf(ForbiddenException.class)
                    .hasMessage(ErrorCode.E403_NOT_MY_ITEM.getMessage());
        }
    }

    private CreateTripRequest createTripRequest(Long countryId, LocalDate startDate, LocalDate endDate) {
        return CreateTripRequest.builder()
                .startDate(startDate)
                .endDate(endDate)
                .countryId(countryId)
                .build();
    }

    private CreateItemRequest createItemRequest(String itemName, Long tripId) {
        return CreateItemRequest.builder()
                .itemName(itemName)
                .previous("NOW")
                .fix("N")
                .countryId(country.getId())
                .itemCateId(itemCate.getId())
                .tripId(tripId)
                .build();
    }
}