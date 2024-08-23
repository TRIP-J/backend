package com.tripj.domain.checklist.repository;

import com.tripj.domain.checklist.model.dto.request.CreateCheckListRequest;
import com.tripj.domain.checklist.model.entity.CheckList;
import com.tripj.domain.checklist.service.CheckListService;
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
import com.tripj.domain.trip.repository.TripRepository;
import com.tripj.domain.trip.service.TripService;
import com.tripj.domain.user.constant.Role;
import com.tripj.domain.user.constant.UserType;
import com.tripj.domain.user.model.entity.User;
import com.tripj.domain.user.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class CheckListRepositoryTest {
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

    @Test
    @DisplayName("체크리스트에 아이템 추가시 중복체크를 합니다.")
    void findByUserIdAndItemIdAndTripIdAndPreviousNow() {
        //given
        CreateTripRequest createTripRequest =
                createTripRequest(country.getId(), LocalDate.of(2022, 10, 1), LocalDate.now().plusDays(1));
        CreateTripResponse trip = tripService.createTrip(createTripRequest, user.getId());

        CreateItemRequest itemRequest = createItemRequest("고데기", trip.getTripId(), "N");
        CreateItemResponse item = itemService.createItem(itemRequest, user.getId());

        CreateCheckListRequest checkListRequest = createCheckListRequest(item.getItemId(), trip.getTripId());
        checkListService.createCheckList(checkListRequest, user.getId());

        //when
        Optional<CheckList> duplicateItem = checkListRepository.findByUserIdAndItemIdAndTripIdAndPreviousNow(user.getId(), item.getItemId(), trip.getTripId());

        // then
        assertThat(duplicateItem).isPresent();
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
                .itemCateId(itemCate.getId())
                .tripId(tripId)
                .build();
    }

}