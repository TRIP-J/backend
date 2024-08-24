package com.tripj.domain.trip.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tripj.domain.trip.model.dto.request.CreateTripRequest;
import com.tripj.domain.trip.model.dto.request.UpdateTripRequest;
import com.tripj.domain.trip.model.dto.response.CreateTripResponse;
import com.tripj.domain.trip.model.dto.response.GetTripCountResponse;
import com.tripj.domain.trip.model.dto.response.GetTripResponse;
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
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
                    createTripRequest(LocalDate.of(2022, 10, 1), LocalDate.now(), 1L);

            CreateTripResponse createTripResponse =
                    createTripResponse(LocalDate.of(2022, 10, 1), LocalDate.now(), 1L);

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
                    .andExpect(jsonPath("$.data.startDate").value("2022-10-01"))
                    .andExpect(jsonPath("$.data.endDate").value(LocalDate.now().toString()));
        }
    }

    @Nested
    class updateTrip {

        @Test
        @DisplayName("여행 계획을 수정에 성공합니다.")
        void updateTrip() throws Exception {
            //given
            UpdateTripRequest updateTripRequest = updateTripRequest();
            UpdateTripResponse updateTripResponse = updateTripResponse();

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
    }

    @Nested
    class getTrip {

        @Test
        @DisplayName("메인페이지에서 여행계획을 조회합니다")
        void getTrip() throws Exception {
            //given
            given(tripService.getTrip(any()))
                    .willReturn(getTripResponse("NOW", LocalDate.of(2022, 10, 1), LocalDate.now()));

            //when //then
            mockMvc.perform(
                            get("/api/trip")
                                    .contentType(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.httpStatus").value("OK"))
                    .andExpect(jsonPath("$.data.tripId").value(1L))
                    .andExpect(jsonPath("$.data.userId").value(1L))
                    .andExpect(jsonPath("$.data.tripName").value("여행이름"))
                    .andExpect(jsonPath("$.data.countryName").value("일본"))
                    .andExpect(jsonPath("$.data.purpose").value("관광"))
                    .andExpect(jsonPath("$.data.previous").value("NOW"));
        }

        @Test
        @DisplayName("지난 여행계획을 조회합니다")
        void getPastTrip() throws Exception {
            //given
            List<GetTripResponse> pastTrip =
                    Arrays.asList(
                            getTripResponse("B01", LocalDate.of(2022, 10, 1), LocalDate.of(2022, 10, 10)),
                            getTripResponse("B02", LocalDate.of(2022, 10, 11), LocalDate.of(2022, 11, 1))
                    );

            given(tripService.getPastTrip(any()))
                    .willReturn(pastTrip);

            //when //then
            mockMvc.perform(
                            get("/api/trip/past")
                                    .param("size", String.valueOf(2))
                                    .param("lastId", String.valueOf(2))
                                    .contentType(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.data.length()", is(2)));
        }
    }

    @Test
    @DisplayName("여행 횟수 조회에 성공합니다.")
    void getTripCount() throws Exception {
        //given
        GetTripCountResponse tripCountResponse = getTripCountResponse();
        given(tripService.getTripCount(any()))
                .willReturn(tripCountResponse);

        //when //then
        mockMvc.perform(
                        get("/api/trip/count")
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.httpStatus").value("OK"))
                .andExpect(jsonPath("$.data.userId").value(1L))
                .andExpect(jsonPath("$.data.tripCount").value(3L));
    }


    private CreateTripRequest createTripRequest(LocalDate startDate, LocalDate endDate, Long countryId) {
        return CreateTripRequest.builder()
                .startDate(startDate)
                .endDate(endDate)
                .countryId(countryId)
                .build();
    }

    private UpdateTripRequest updateTripRequest() {
        return UpdateTripRequest.builder()
                .startDate(LocalDate.of(2022, 10, 1))
                .endDate(LocalDate.now())
                .countryId(1L)
                .build();
    }

    private UpdateTripResponse updateTripResponse() {
        return UpdateTripResponse.builder()
                .previous("NOW")
                .startDate(LocalDate.of(2022, 10, 1))
                .endDate(LocalDate.now())
                .countryId(1L)
                .build();
    }

    private CreateTripResponse createTripResponse(LocalDate startDate, LocalDate endDate, Long countryId) {
        return CreateTripResponse.builder()
                .previous("NOW")
                .startDate(startDate)
                .endDate(endDate)
                .countryId(countryId)
                .build();
    }

    private GetTripResponse getTripResponse(String previous, LocalDate startDate, LocalDate endDate) {
        return GetTripResponse.builder()
                .tripId(1L)
                .previous(previous)
                .startDate(startDate)
                .endDate(endDate)
                .countryName("일본")
                .build();
    }

    private GetTripCountResponse getTripCountResponse() {
        return GetTripCountResponse.builder()
                .userId(1L)
                .tripCount(3L)
                .build();
    }


}