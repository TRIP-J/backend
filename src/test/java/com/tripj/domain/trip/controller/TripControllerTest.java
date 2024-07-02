package com.tripj.domain.trip.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tripj.domain.trip.model.dto.request.CreateTripRequest;
import com.tripj.domain.trip.model.dto.request.UpdateTripRequest;
import com.tripj.domain.trip.model.dto.response.CreateTripResponse;
import com.tripj.domain.trip.model.dto.response.UpdateTripResponse;
import com.tripj.domain.trip.service.TripService;
import com.tripj.global.error.GlobalExceptionHandler;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ContextConfiguration(classes = TripController.class)
@Import({TripController.class, GlobalExceptionHandler.class})
@WebMvcTest(controllers = {TripController.class}, excludeAutoConfiguration = {SecurityAutoConfiguration.class})
class TripControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TripService tripService;

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

            CreateTripRequest createTripRequest =
                    createTripRequest(null, "여행목적", LocalDate.of(2022, 10, 1), LocalDate.now(), 1L);

            mockMvc.perform(
                    post("/api/trip")
                            .content(objectMapper.writeValueAsString(createTripRequest))
                            .contentType(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.httpStatus").value("400 BAD_REQUEST"))
                    .andExpect(jsonPath("$.message").value("여행 이름은 필수로 입력해 주세요"));
        }
    }

    @Nested
    class updateTrip {

        @Test
        @DisplayName("여행 계획을 수정에 성공합니다.")
        void updateTrip() throws Exception {
            //given
            UpdateTripRequest updateTripRequest = updateTripRequest("여행이름");
            UpdateTripResponse updateTripResponse = updateTripResponse("여행이름");

            given(tripService.updateTrip(any(), any(), any()))
                    .willReturn(updateTripResponse);

            //when //then
            mockMvc.perform(
                            post("/api/trip/{tripId}", 1L)
                                    .content(objectMapper.writeValueAsString(updateTripRequest))
                                    .contentType(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.httpStatus").value("OK"))
                    .andExpect(jsonPath("$.message").value("OK"))
                    .andExpect(jsonPath("$.data.tripName").value("여행이름"))
                    .andExpect(jsonPath("$.data.purpose").value("여행목적"))
                    .andExpect(jsonPath("$.data.startDate").value("2022-10-01"))
                    .andExpect(jsonPath("$.data.endDate").value(LocalDate.now().toString()));
        }

        @Test
        @DisplayName("여행 계획을 수정 시 여행 이름은 필수입니다.")
        void updateTripInvalidTripName() throws Exception {
            //given
            UpdateTripRequest updateTripRequest = updateTripRequest(null);
            UpdateTripResponse updateTripResponse = updateTripResponse("여행이름");

            given(tripService.updateTrip(any(), any(), any()))
                    .willReturn(updateTripResponse);

            //when //then
            mockMvc.perform(
                            post("/api/trip/{tripId}", 1L)
                                    .content(objectMapper.writeValueAsString(updateTripRequest))
                                    .contentType(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.httpStatus").value("400 BAD_REQUEST"))
                    .andExpect(jsonPath("$.message").value("여행 이름은 필수로 입력해 주세요"));
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

    private UpdateTripRequest updateTripRequest(String tripName) {
        return UpdateTripRequest.builder()
                .tripName(tripName)
                .purpose("여행목적")
                .previous("NOW")
                .startDate(LocalDate.of(2022, 10, 1))
                .endDate(LocalDate.now())
                .countryId(1L)
                .build();
    }

    private UpdateTripResponse updateTripResponse(String tripName) {
        return UpdateTripResponse.builder()
                .tripName(tripName)
                .purpose("여행목적")
                .previous("NOW")
                .startDate(LocalDate.of(2022, 10, 1))
                .endDate(LocalDate.now())
                .countryId(1L)
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