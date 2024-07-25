package com.tripj.external.crawling.service;

import com.tripj.domain.country.repository.CountryRepository;
import com.tripj.domain.precation.model.entity.Precaution;
import com.tripj.domain.precation.repository.PrecautionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PrecautionCrawlingImpl implements PrecautionCrawling {

    private final PrecautionRepository precautionRepository;
    private final CountryRepository countryRepository;

    @Override
    public List<Precaution> precautionCrawling() {

        List<Integer> idxValues = List.of(189, 377); // 크롤링할 idx 값들
        List<Document> crawledPages = new ArrayList<>();
        List<Precaution> precautions = new ArrayList<>();

        for (int idx : idxValues) {
            String url = "https://www.0404.go.kr/dev/country_view.mofa?idx=" + idx + "&hash=&chkvalue=no2&stext=&group_idx=1&alert_level=0";
            try {
                Document doc = Jsoup.connect(url).get();
                crawledPages.add(doc);
                log.info("크롤링 성공: {}", url);

                Precaution precaution = extractAndSaveData(doc);
                precautions.add(precaution);

            } catch (IOException e) {
                log.info("크롤링 실패: {}", url);
                e.printStackTrace();
            }
        }
        return precautions;
    }

    private Precaution extractAndSaveData(Document doc) {
        // id가 "tel_number"인 div 요소의 데이터를 추출
        Element telNumberElement = doc.getElementById("tel_number");

        if (telNumberElement != null) {
            String content = telNumberElement.text();

            Precaution precaution = Precaution.newPrecaution(null, content);
            precautionRepository.save(precaution);
            log.info("저장 완료: {}", precaution);
            return precaution;
        } else {
            System.err.println("지정된 ID를 찾을 수 없습니다: tel_number");
            return null;
        }
    }

}
