package com.tripj.external.crawling.controller;

import com.tripj.domain.precation.model.entity.Precaution;
import com.tripj.external.crawling.service.PrecautionCrawling;
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
@RequestMapping("/api/crawling")
@Tag(name = "crawling", description = "주의사항 크롤링 API")
public class PrecautionCrawlingController {

    private final PrecautionCrawling precautionCrawling;

    @Operation(
            summary = "주의사항 크롤링 API",
            description = "주의사항 데이터를 크롤링 합니다."
    )
    @GetMapping("/parsing")
    public RestApiResponse<List<Precaution>> parsing() {
        return RestApiResponse.success(precautionCrawling.precautionCrawling());
    }




}
