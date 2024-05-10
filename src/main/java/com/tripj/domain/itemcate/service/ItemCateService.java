package com.tripj.domain.itemcate.service;

import com.tripj.domain.itemcate.model.dto.response.GetItemCateResponse;
import com.tripj.domain.itemcate.model.entity.ItemCate;
import com.tripj.domain.itemcate.repository.ItemCateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ItemCateService {

    private final ItemCateRepository itemCateRepository;

    public List<GetItemCateResponse> getItemCate() {
        List<ItemCate> itemCateList = itemCateRepository.findAll();
        List<GetItemCateResponse> responseList = itemCateList.stream()
                .map(itemCate -> GetItemCateResponse.of(itemCate.getId(), itemCate.getItemCateName()))
                .collect(Collectors.toList());

        return responseList;
    }


}
