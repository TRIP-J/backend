package com.tripj.domain.inquiry.repository;

import com.tripj.domain.inquiry.model.entity.Inquiry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InquiryRepository extends JpaRepository<Inquiry, Long> {

    void deleteByUserId(Long userId);
}


