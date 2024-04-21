package com.tripj.domain.trip.service;

import com.tripj.domain.country.model.entity.Country;
import com.tripj.domain.country.repository.CountryRepository;
import com.tripj.domain.trip.model.dto.CreateTripRequest;
import com.tripj.domain.trip.model.dto.CreateTripResponse;
import com.tripj.domain.trip.model.dto.GetTripResponse;
import com.tripj.domain.trip.model.dto.UpdateTripRequest;
import com.tripj.domain.trip.model.entity.Trip;
import com.tripj.domain.trip.repository.TripRepository;
import com.tripj.domain.user.model.entity.User;
import com.tripj.domain.user.repository.UserRepository;
import com.tripj.global.code.ErrorCode;
import com.tripj.global.error.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.E404_NOT_EXISTS_USER));

        Country country = countryRepository.findById(request.getCountryId())
                .orElseThrow(() -> new NotFoundException(ErrorCode.E404_NOT_EXISTS_COUNTRY));

        Trip savedTrip = tripRepository.save(request.toEntity(user, country));

        return CreateTripResponse.of(savedTrip.getId());
    }

    /**
     * 여행 수정
     */
    public CreateTripResponse updateTrip(UpdateTripRequest request,
                                         Long tripId,
                                         Long userId) {

        Trip trip = tripRepository.findById(tripId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.E404_NOT_EXISTS_TRIP));

        Country country = countryRepository.findById(request.getCountryId())
                .orElseThrow(() -> new NotFoundException(ErrorCode.E404_NOT_EXISTS_COUNTRY));

        if (trip.getUser().getId().equals(userId)) {
            trip.updateTrip(request.getTripName(), request.getPurpose(),
                            request.getStartDate(), request.getEndDate(),
                            country);
        }

        return CreateTripResponse.of(tripId);
    }

    /**
     * 여행 조회
     */
    @Transactional(readOnly = true)
    public List<GetTripResponse> getTrip(Long userId) {

        userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.E404_NOT_EXISTS_USER));

        return tripRepository.getTrip(userId);
    }

    /**
     * 지난 여행 조회
     */
    @Transactional(readOnly = true)
    public List<GetTripResponse> getPastTrip(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.E404_NOT_EXISTS_USER));

        tripRepository.findById(user.getTrip().get(0).getId())
                .orElseThrow(() -> new NotFoundException(ErrorCode.E404_NOT_EXISTS_TRIP));

        return tripRepository.getPastTrip(userId);
    }







}















