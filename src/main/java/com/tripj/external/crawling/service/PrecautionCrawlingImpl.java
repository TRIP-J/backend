package com.tripj.external.crawling.service;

import com.tripj.domain.country.model.entity.Country;
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
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
@Service
@RequiredArgsConstructor
public class PrecautionCrawlingImpl implements PrecautionCrawling {

    private final PrecautionRepository precautionRepository;
    private final CountryRepository countryRepository;

    @Override
    public List<Precaution> precautionCrawling() {

        List<Integer> idxValues = IntStream.rangeClosed(1, 500)
                .boxed()
                .collect(Collectors.toList());
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

        // country_info 안의 <h4> 태그에서 국가 이름 추출
        Element countryElement = doc.selectFirst(".country_info h4");

        if (countryElement == null) {
            log.warn("countryElement가 null입니다. 페이지를 건너뜁니다.");
            return null;
        }

        String countryName = countryElement.text();

        // 괄호 앞의 부분만 추출 (한국어 나라 이름)
        String koreanCountryName = countryName.split("\\(")[0].trim();

        Optional<Country> optionalCountry = countryRepository.findByName(koreanCountryName);
        Country country = optionalCountry.get();

        // id가 "tel_number"인 div 요소의 데이터를 추출
        Element telNumberElement = doc.getElementById("tel_number");
        Element onlineInfoElement = doc.getElementById("online_info");
        Element cultureElement = doc.getElementById("culture");
        Element incidentElement = doc.getElementById("incident");

        if (telNumberElement != null) {
            String contact = telNumberElement.text();
            String traffic = onlineInfoElement.text();
            String culture = cultureElement.text();
            String accident = incidentElement.text();

            Precaution precaution = Precaution.newPrecaution(contact, traffic, culture, accident, country);
            precautionRepository.save(precaution);
            log.info("저장 완료: {}", precaution);
            return precaution;
        } else {
            System.err.println("지정된 ID를 찾을 수 없습니다: tel_number");
            return null;
        }
    }
}
