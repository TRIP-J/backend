package com.tripj.domain.precation.controller;

import com.tripj.domain.precation.model.dto.GetPrecautionListResponse;
import com.tripj.domain.precation.service.PrecautionService;
import com.tripj.global.model.RestApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/precaution")
@Tag(name = "precaution", description = "주의사항 API")
public class PrecautionController {

    private final PrecautionService precautionService;

    @Operation(
            summary = "주의사항 조회 API",
            description = "주의사항 전체 조회"
    )
    @GetMapping("")
    public RestApiResponse <List<GetPrecautionListResponse>> getPrecautionList(Long countryId) {
        return RestApiResponse.success(precautionService.getPrecautionList(countryId));
    }

}
