package com.tripj.domain.trip.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tripj.domain.trip.model.dto.request.CreateTripRequest;
import com.tripj.domain.trip.service.TripService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
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

    @Nested
    class createTrip {

        @DisplayName("여행 계획 등록에 성공합니다.")
        @Test
        void createTrip() throws Exception {
            // given
            CreateTripRequest createTripRequest =
                    createTripRequest(1L, LocalDate.of(2022, 10, 1), LocalDate.now());

            // when // then
            mockMvc.perform(
                            post("/api/trip")
                                    .content(objectMapper.writeValueAsString(createTripRequest))
                                    .contentType(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isOk());
        }
    }

    private CreateTripRequest createTripRequest(Long countryId, LocalDate startDate, LocalDate endDate) {
        return CreateTripRequest.builder()
                .tripName("즐거운 오사카 여행")
                .purpose("여행")
                .previous("NOW")
                .startDate(startDate)
                .endDate(endDate)
                .countryId(countryId)
                .build();
    }


}