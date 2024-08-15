package com.tripj.domain.trip.repository;

import com.tripj.domain.country.model.entity.Country;
import com.tripj.domain.country.repository.CountryRepository;
import com.tripj.domain.trip.model.dto.request.CreateTripRequest;
import com.tripj.domain.trip.model.entity.Trip;
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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

@SpringBootTest
@ActiveProfiles("test")
class TripRepositoryTest {

    @Autowired
    private TripService tripService;
    @Autowired
    private TripRepository tripRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CountryRepository countryRepository;

    private Country country;
    private User user;

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
    }

    @AfterEach
    void tearDown() {
        tripRepository.deleteAllInBatch();
        countryRepository.deleteAllInBatch();
        userRepository.deleteAllInBatch();
    }

    @DisplayName("EndDate가 오늘 날짜이며, 진행중인 여행 계획들을 조회 합니다.")
    @Test
    void findAllPreviousIsNow() {
        //given
        CreateTripRequest createTripRequest =
                createTripRequest(country.getId(), LocalDate.of(2024, 06, 20), LocalDate.now());
        tripService.createTrip(createTripRequest, user.getId());

        // when
        List<Trip> allPreviousIsNow = tripRepository.findAllPreviousIsNow();

        // then
        assertThat(allPreviousIsNow).hasSize(1)
                .extracting("startDate", "endDate", "tripName", "purpose", "previous")
                .containsExactly(
                        tuple(LocalDate.of(2024, 06, 20), LocalDate.now(), "즐거운 오사카 여행", "여행", "NOW")
                );
    }

    @DisplayName("여행 계획중 Previous 값이 제일 높은 여행 계획을 조회 합니다.")
    @Test
    void findMaxPrevious() {
        // given
        CreateTripRequest createTripRequest =
                createTripRequest(country.getId(), LocalDate.of(2024, 06, 20), LocalDate.now());
        tripService.createTrip(createTripRequest, user.getId());
        tripService.changeTripPrevious();

        CreateTripRequest createTripRequest2 =
                createTripRequest(country.getId(), LocalDate.of(2024, 06, 20), LocalDate.now());
        tripService.createTrip(createTripRequest2, user.getId());
        tripService.changeTripPrevious();

        // when
        String maxPrevious = tripRepository.findMaxPrevious(user.getId());

        // then
        assertThat(maxPrevious).isEqualTo("B02");
    }

    private CreateTripRequest createTripRequest(Long countryId, LocalDate startDate, LocalDate endDate) {
        return CreateTripRequest.builder()
                .startDate(startDate)
                .endDate(endDate)
                .countryId(countryId)
                .build();
    }


}