package com.tripj.external.crawling.service;

import com.tripj.domain.country.model.entity.Country;
import com.tripj.domain.country.repository.CountryRepository;
import com.tripj.domain.precation.model.entity.Precaution;
import com.tripj.domain.precation.repository.PrecautionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PrecautionCrawlingImpl implements PrecautionCrawling {

    private final PrecautionRepository precautionRepository;
    private final CountryRepository countryRepository;

    @Override
    public List<Precaution> precautionCrawling() {

        List<Integer> idxValues = getIntegers();
        List<Document> crawledPages = new ArrayList<>();
        List<Precaution> precautions = new ArrayList<>();

        for (int idx : idxValues) {
            String url = "https://www.0404.go.kr/dev/country_view.mofa?idx=" + idx + "&hash=&chkvalue=no2&stext=&group_idx=1&alert_level=0";
            try {
                Document doc = Jsoup.connect(url).get();
                crawledPages.add(doc);
                log.info("크롤링 성공: {}", url);

                Precaution precaution = extractAndSaveData(doc, idx);
                precautions.add(precaution);

            } catch (IOException e) {
                log.info("크롤링 실패: {}", url);
                e.printStackTrace();
            }
        }
        return precautions;
    }

    private Precaution extractAndSaveData(Document doc, int idx) {

        // country_info 안의 <h4> 태그에서 국가 이름 추출
        Element countryElement = doc.selectFirst(".country_info h4");

        if (countryElement == null) {
            log.info("countryElement가 null입니다. 페이지를 건너뜁니다.");
            return null;
        }

        String countryName = countryElement.text();

        // 괄호 앞의 부분만 추출 (한국어 나라 이름)
        String koreanCountryName = countryName.split("\\(")[0].trim();

        Optional<Country> optionalCountry = countryRepository.findByName(koreanCountryName);
        if (!optionalCountry.isPresent()) {
            log.info("해당 국가를 찾을 수 없습니다: {}", koreanCountryName);
            return null;
        }
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

            Optional<Precaution> existingCountry = precautionRepository.findByCountry(country);

            if (existingCountry.isPresent()) {
                Precaution existingPrecaution = existingCountry.get();

                if (!existingPrecaution.getContact().equals(contact) ||
                        !existingPrecaution.getTraffic().equals(traffic) ||
                        !existingPrecaution.getCulture().equals(culture) ||
                        !existingPrecaution.getAccident().equals(accident)) {

                    precautionRepository.delete(existingPrecaution);
                    Precaution precaution = Precaution.newPrecaution(contact, traffic, culture, accident, country, idx);
                    precautionRepository.save(precaution);
                } else {
                    log.info("변경된 내용이 없습니다. 업데이트하지 않습니다.");
                    return existingPrecaution;
                }
            } else {
                Precaution precaution = Precaution.newPrecaution(contact, traffic, culture, accident, country, idx);
                precautionRepository.save(precaution);
                log.info("새로운 데이터 저장 완료: {}", precaution);
                return precaution;
            }
        } else {
            log.info("지정된 ID를 찾을 수 없습니다: tel_number");
            return null;
        }
        return null;
    }

    @NotNull
    private static List<Integer> getIntegers() {
        List<Integer> idxValues = Arrays.asList(
                2, 5, 7, 11, 13, 14, 15, 18, 20, 21, 22, 23, 25, 27, 28, 31, 33, 34, 36,
                37, 39, 40, 43, 45, 48, 49, 55, 56, 57, 58, 60, 61, 62, 63, 65, 68, 69,
                75, 82, 85, 86, 87, 91, 92, 93, 94, 98, 104, 105, 107, 112, 114, 120,
                122, 124, 125, 126, 127, 128, 129, 130, 131, 134, 135, 138, 139, 141,
                150, 151, 154, 155, 156, 157, 159, 162, 163, 164, 165, 166, 167, 168,
                169, 174, 176, 177, 178, 179, 181, 183, 186, 187, 189, 190, 191, 193,
                195, 197, 199, 200, 201, 202, 204, 206, 209, 212, 213, 214, 215, 216,
                218, 225, 228, 230, 233, 235, 237, 239, 240, 243, 244, 246, 248, 249,
                251, 252, 254, 255, 258, 259, 260, 284, 285, 287, 288, 289, 290, 291,
                292, 294, 295, 296, 297, 298, 299, 300, 301, 302, 304, 306, 307, 308,
                309, 310, 311, 312, 313, 314, 315, 316, 318, 319, 320, 321, 322, 323,
                324, 325, 326, 327, 328, 329, 330, 331, 332, 333, 334, 335, 337, 338,
                339, 340, 341, 344, 345, 366, 367, 368, 369, 370, 371, 372, 373, 375,
                377, 378, 380, 390, 398, 399
        );
        return idxValues;
    }
}
