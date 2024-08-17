package com.tripj.domain.trip.service;

import com.tripj.domain.country.model.entity.Country;
import com.tripj.domain.country.repository.CountryRepository;
import com.tripj.domain.trip.model.dto.request.CreateTripRequest;
import com.tripj.domain.trip.model.dto.response.CreateTripResponse;
import com.tripj.domain.trip.model.dto.response.GetTripCountResponse;
import com.tripj.domain.trip.model.dto.response.GetTripResponse;
import com.tripj.domain.trip.model.dto.request.UpdateTripRequest;
import com.tripj.domain.trip.model.dto.response.UpdateTripResponse;
import com.tripj.domain.trip.model.entity.Trip;
import com.tripj.domain.trip.repository.TripRepository;
import com.tripj.domain.user.model.entity.User;
import com.tripj.domain.user.repository.UserRepository;
import com.tripj.global.code.ErrorCode;
import com.tripj.global.error.exception.BusinessException;
import com.tripj.global.error.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.tripj.global.code.ErrorCode.*;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class TripService {

    private final TripRepository tripRepository;
    private final UserRepository userRepository;
    private final CountryRepository countryRepository;

    /**
     * 여행 생성
     */
    public CreateTripResponse createTrip(CreateTripRequest request,
                                         Long userId) {

        // 여행 계획은 endDate가 지나기 전까지 한 개밖에 못 만든다.
        List<Trip> trip = tripRepository.findByUserId(userId);
        trip
            .forEach(trips -> {
                if (trips.getPrevious().equals("NOW")) {
                    throw new BusinessException(ALREADY_EXISTS_TRIP);
                }
            });

        User user = userRepository.findById(userId)
            .orElseThrow(() -> new NotFoundException(E404_NOT_EXISTS_USER));

        Country country = countryRepository.findById(request.getCountryId())
            .orElseThrow(() -> new NotFoundException(E404_NOT_EXISTS_COUNTRY));

        Trip savedTrip = tripRepository.save(request.toEntity(user, country));

        return CreateTripResponse.of(savedTrip);
    }

    /**
     * 여행 수정
     */
    public UpdateTripResponse updateTrip(UpdateTripRequest request,
                                         Long tripId,
                                         Long userId) {

        Trip trip = tripRepository.findById(tripId)
            .orElseThrow(() -> new NotFoundException(E404_NOT_EXISTS_TRIP));

        Country country = countryRepository.findById(request.getCountryId())
            .orElseThrow(() -> new NotFoundException(E404_NOT_EXISTS_COUNTRY));

        if (trip.getUser().getId().equals(userId)) {
            trip.updateTrip(request.getStartDate(), request.getEndDate(), country);
        }

        return UpdateTripResponse.of(trip);
    }

    /**
     * 여행 조회
     */
    @Transactional(readOnly = true)
    public GetTripResponse getTrip(Long userId) {

        userRepository.findById(userId)
            .orElseThrow(() -> new NotFoundException(E404_NOT_EXISTS_USER));

        tripRepository.findTripPreviousIsNow(userId)
            .orElseThrow(() -> new NotFoundException(E404_NOT_EXISTS_TRIP));

        return tripRepository.getTrip(userId);
    }

    /**
     * 지난 여행 조회
     */
    @Transactional(readOnly = true)
    public List<GetTripResponse> getPastTrip(Long userId) {

        userRepository.findById(userId)
            .orElseThrow(() -> new NotFoundException(E404_NOT_EXISTS_USER));

        List<Trip> tripPreviousIsNotNow = tripRepository.findTripPreviousIsNotNow(userId);
        if (tripPreviousIsNotNow.isEmpty()) {
            throw new NotFoundException(E404_NOT_EXISTS_PAST_TRIP);
        }

        return tripRepository.getPastTrip(userId);
    }

    /**
     * Previous 변경
     */
    public void changeTripPrevious() {

        List<Trip> previousIsNow = tripRepository.findAllPreviousIsNow();

        previousIsNow
                .forEach(trip -> {
                    Long userId = trip.getUser().getId();
                    // previous 가장 큰 값 찾기
                    String maxPrevious = tripRepository.findMaxPrevious(userId);
                    if (maxPrevious != null) {
                        int nextNum = Integer.parseInt(maxPrevious.substring(1)) + 1;
                        String nextPrevious = "B" + String.format("%02d", nextNum);
                        trip.updatePrevious(nextPrevious);
                    }
                    // previous가 NOW만 있으면 B01로 변경
                    if (trip.getPrevious().equals("NOW")) {
                        trip.updatePrevious("B01");
                    }
                });
    }

    /**
     * 여행 횟수 조회
     */
    public GetTripCountResponse getTripCount(Long userId) {

        userRepository.findById(userId)
            .orElseThrow(() -> new NotFoundException(E404_NOT_EXISTS_USER));

        Long tripCount = tripRepository.countByUserId(userId);
        return GetTripCountResponse.of(userId, tripCount);
    }
}















