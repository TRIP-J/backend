package com.tripj.domain.trip.service;

import com.tripj.domain.trip.model.dto.CreateTripRequest;
import com.tripj.domain.trip.model.dto.CreateTripResponse;
import com.tripj.domain.trip.model.entity.Trip;
import com.tripj.domain.trip.repository.TripRepository;
import com.tripj.domain.user.model.entity.User;
import com.tripj.domain.user.repository.UserRepository;
import com.tripj.global.code.ErrorCode;
import com.tripj.global.error.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class TripService {

    private final TripRepository tripRepository;
    private final UserRepository userRepository;

    public CreateTripResponse createTrip(CreateTripRequest request, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 회원입니다.", ErrorCode.E404_NOT_EXISTS_MEMBER));

        Trip savedTrip = tripRepository.save(request.toEntity(user));

        return CreateTripResponse.of(savedTrip.getId());
    }

}
