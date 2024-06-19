package com.tripj.domain.trip.service;

import com.tripj.domain.country.model.entity.Country;
import com.tripj.domain.country.repository.CountryRepository;
import com.tripj.domain.trip.model.dto.request.CreateTripRequest;
import com.tripj.domain.trip.model.dto.response.CreateTripResponse;
import com.tripj.domain.trip.repository.TripRepository;
import com.tripj.domain.user.constant.Role;
import com.tripj.domain.user.constant.UserType;
import com.tripj.domain.user.model.entity.User;
import com.tripj.domain.user.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
@ActiveProfiles("test")
class TripServiceTest {
    
    @Autowired
    private TripService tripService;
    @Autowired
    private TripRepository tripRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CountryRepository countryRepository;

    @AfterEach
    void tearDown() {
        tripRepository.deleteAllInBatch();
        countryRepository.deleteAllInBatch();
        userRepository.deleteAllInBatch();
    }

    @Test
    @DisplayName("여행 계획을 최초 등록한다.")
    void createTrip_whenBeforeExistingTrip() {
        // given
        CreateTripRequest createTripRequest = createTripRequest();
        User user = createUser();
        userRepository.save(user);

        Country country = createCountry();
        countryRepository.save(country);

        // when
        CreateTripResponse trip = tripService.createTrip(createTripRequest, 1L);

        // then
        assertNotNull(trip);
        assertEquals("즐거운 오사카 여행", trip.getTripName());
        assertEquals("여행", trip.getPurpose());
        assertEquals("NOW", trip.getPrevious());
        assertEquals(LocalDate.of(2022, 10, 1), trip.getStartDate());
        assertEquals(LocalDate.of(2022, 10, 10), trip.getEndDate());
    }


    private CreateTripRequest createTripRequest() {
        return CreateTripRequest.builder()
                .tripName("즐거운 오사카 여행")
                .purpose("여행")
                .previous("NOW")
                .startDate(LocalDate.of(2022, 10, 1))
                .endDate(LocalDate.of(2022, 10, 10))
                .countryId(1L)
                .build();
    }

    private User createUser() {
        return User.builder()
                .id(1L)
                .userType(UserType.KAKAO)
                .email("asdf@naver.com")
                .nickname("다람지기엽지")
                .userName("홍길동")
                .role(Role.ROLE_USER)
                .build();
    }

    private Country createCountry() {
        return Country.builder()
                .id(1L)
                .name("일본")
                .code("JP")
                .build();
    }

}