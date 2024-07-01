package com.tripj.domain.trip.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tripj.domain.trip.model.dto.request.CreateTripRequest;
import com.tripj.domain.trip.model.dto.response.CreateTripResponse;
import com.tripj.domain.trip.repository.TripRepository;
import com.tripj.domain.trip.service.TripService;
import com.tripj.domain.user.model.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDate;

import static org.mockito.BDDMockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import(TripController.class)
@ExtendWith(MockitoExtension.class)
@WebMvcTest(useDefaultFilters = false)
@AutoConfigureMockMvc(addFilters = false)
class TripControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TripService tripService;

    @MockBean
    private User user;

//    @MockBean
//    private TripRepository tripRepository;

    @Nested
    class createTrip {

        @DisplayName("여행 계획 등록에 성공합니다.")
        @Test
        void createTrip() throws Exception {
            // given
            CreateTripRequest createTripRequest =
                    createTripRequest("여행이름", "여행목적", LocalDate.of(2022, 10, 1), LocalDate.now(), 1L);

            CreateTripResponse createTripResponse =
                    createTripResponse("여행이름", "여행목적", LocalDate.of(2022, 10, 1), LocalDate.now(), 1L);

            given(tripService.createTrip(any(), any()))
                    .willReturn(createTripResponse);

            // when // then
            mockMvc.perform(
                            post("/api/trip")
                                    .content(objectMapper.writeValueAsString(createTripRequest))
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .characterEncoding("utf-8"))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.httpStatus").value("OK"))
                    .andExpect(jsonPath("$.message").value("OK"))
                    .andExpect(jsonPath("$.data.tripName").value("여행이름"))
                    .andExpect(jsonPath("$.data.purpose").value("여행목적"))
                    .andExpect(jsonPath("$.data.startDate").value("2022-10-01"))
                    .andExpect(jsonPath("$.data.endDate").value(LocalDate.now().toString()));
        }

        @DisplayName("여행 계획 등록시 여행 이름은 필수 입니다.")
        @Test
        void createTripInvalidTripName() throws Exception {
            // given
            CreateTripRequest createTripRequest =
                    createTripRequest(null, "여행목적", LocalDate.of(2022, 10, 1), LocalDate.now(), 1L);

            CreateTripResponse createTripResponse =
                    createTripResponse("여행이름", "여행목적", LocalDate.of(2022, 10, 1), LocalDate.now(), 1L);

            given(tripService.createTrip(any(), any()))
                    .willReturn(createTripResponse);

            // when // then
            ResultActions response = mockMvc.perform(
                    post("/api/trip")
                            .accept(APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(createTripRequest))
                            .contentType(MediaType.APPLICATION_JSON)
                            .characterEncoding("utf-8"));

            response.andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.httpStatus").value("BAD_REQUEST"))
                    .andExpect(jsonPath("$.message").value("여행 이름은 필수 입니다."));

        }

    }

    private CreateTripRequest createTripRequest(String tripName, String purpose, LocalDate startDate, LocalDate endDate, Long countryId) {
        return CreateTripRequest.builder()
                .tripName(tripName)
                .purpose(purpose)
                .previous("NOW")
                .startDate(startDate)
                .endDate(endDate)
                .countryId(countryId)
                .build();
    }

    private CreateTripResponse createTripResponse(String tripName, String purpose, LocalDate startDate, LocalDate endDate, Long countryId) {
        return CreateTripResponse.builder()
                .tripName(tripName)
                .purpose(purpose)
                .previous("NOW")
                .startDate(startDate)
                .endDate(endDate)
                .countryId(countryId)
                .build();
    }


}