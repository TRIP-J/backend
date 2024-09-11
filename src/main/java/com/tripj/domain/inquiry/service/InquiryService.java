package com.tripj.domain.inquiry.service;

import com.tripj.domain.inquiry.model.dto.response.CreateInquiryRequest;
import com.tripj.domain.inquiry.model.dto.response.CreateInquiryResponse;
import com.tripj.domain.inquiry.model.entity.Inquiry;
import com.tripj.domain.inquiry.repository.InquiryRepository;
import com.tripj.domain.user.model.entity.User;
import com.tripj.domain.user.repository.UserRepository;
import com.tripj.external.mail.MailService;
import com.tripj.global.error.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.tripj.global.code.ErrorCode.E404_NOT_EXISTS_USER;

@Service
@Transactional
@RequiredArgsConstructor
public class InquiryService {

    private final InquiryRepository inquiryRepository;
    private final UserRepository userRepository;
    private final MailService mailService;

    /**
     * 문의사항 보내기
     */
    public CreateInquiryResponse createInquiry(Long userId, CreateInquiryRequest request) {

        User user = userRepository.findById(userId)
            .orElseThrow(() -> new NotFoundException(E404_NOT_EXISTS_USER));

        Inquiry inquiry = request.toEntity(request.getContent(), user);
        inquiryRepository.save(inquiry);

        //메일 전송
        String email = "team.tripj@gmail.com";
        String subject = "트립제이 운영진님 새로운 문의사항이 도착하였습니다.";
        String text = "안녕하세요. 새로운 문의사항이 도착하였습니다. 확인해보세요. \n\n "
                + request.getContent();

        //메일 전송
        mailService.send(email,subject,text);

        return CreateInquiryResponse.of(inquiry.getId());
    }
}
