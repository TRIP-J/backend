package com.tripj.domain.item.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tripj.domain.item.model.dto.request.CreateItemRequest;
import com.tripj.domain.item.model.dto.request.UpdateItemRequest;
import com.tripj.domain.item.model.dto.response.CreateItemResponse;
import com.tripj.domain.item.model.dto.response.DeleteItemResponse;
import com.tripj.domain.item.model.dto.response.UpdateItemResponse;
import com.tripj.domain.item.service.ItemService;
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

import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.springframework.http.MediaType.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ContextConfiguration(classes = ItemController.class)
@Import({ItemController.class, GlobalExceptionHandler.class})
@WebMvcTest(controllers = {ItemController.class}, excludeAutoConfiguration = {SecurityAutoConfiguration.class})
class ItemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ItemService itemService;

    @Nested
    class createItem {

        @Test
        @DisplayName("아이템 등록에 성공합니다.")
        void createItem() throws Exception {

            //given
            CreateItemRequest createItemRequest = createItemRequest("포마드");
            CreateItemResponse createItemResponse = createItemResponse();
            given(itemService.createItem(any(), any()))
                    .willReturn(createItemResponse);

            //when //then
            mockMvc.perform(
                            post("/api/item")
                                    .contentType(APPLICATION_JSON)
                                    .content(objectMapper.writeValueAsString(createItemRequest))
                    )
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.data.itemName").value("포마드"))
                    .andExpect(jsonPath("$.data.previous").value("NOW"))
                    .andExpect(jsonPath("$.data.countryId").value(1L))
                    .andExpect(jsonPath("$.data.itemCateId").value(1L))
                    .andExpect(jsonPath("$.data.tripId").value(1L));
        }

        @Test
        @DisplayName("아이템 등록시 아이템명은 필수 입니다.")
        void createItemInvalidItemName() throws Exception {
            //given
            CreateItemRequest createItemRequest = createItemRequest(null);
            CreateItemResponse createItemResponse = createItemResponse();
            given(itemService.createItem(any(), any()))
                    .willReturn(createItemResponse);

            //when //then
            mockMvc.perform(
                            post("/api/item")
                                    .contentType(APPLICATION_JSON)
                                    .content(objectMapper.writeValueAsString(createItemRequest))
                    )
                    .andDo(print())
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.httpStatus").value("400 BAD_REQUEST"))
                    .andExpect(jsonPath("$.message").value("아이템명은 필수로 입력해 주세요."));
        }
    }

    @Nested
    class updateItem {

        @Test
        @DisplayName("아이템 수정에 성공합니다.")
        void updateItem() throws Exception {
            //given
            UpdateItemRequest updateItemRequest = updateItemRequest("면도기");
            UpdateItemResponse updateItemResponse = updateItemResponse();
            given(itemService.updateItem(any(), any(), any()))
                    .willReturn(updateItemResponse);

            //when //then
            mockMvc.perform(
                            post("/api/item/{itemId}", 1L)
                                    .contentType(APPLICATION_JSON)
                                    .content(objectMapper.writeValueAsString(updateItemRequest))
                    )
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.data.itemName").value("포마드"));
        }

        @Test
        @DisplayName("아이템 수정시 아이템명은 필수 입니다.")
        void createItemInvalidItemName() throws Exception {
            //given
            UpdateItemRequest updateItemRequest = updateItemRequest(null);
            UpdateItemResponse updateItemResponse = updateItemResponse();
            given(itemService.updateItem(any(), any(), any()))
                    .willReturn(updateItemResponse);

            //when //then
            mockMvc.perform(
                            post("/api/item/{itemId}", 1L)
                                    .contentType(APPLICATION_JSON)
                                    .content(objectMapper.writeValueAsString(updateItemRequest))
                    )
                    .andDo(print())
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.httpStatus").value("400 BAD_REQUEST"))
                    .andExpect(jsonPath("$.message").value("아이템명은 필수로 입력해 주세요."));
        }
    }

    @Test
    @DisplayName("아이템 삭제에 성공합니다")
    void deleteItem() throws Exception {
        //given
        given(itemService.deleteItem(any(), any()))
                .willReturn(new DeleteItemResponse(1L));

        //when //then
        mockMvc.perform(
                        delete("/api/item/{itemId}", 1L))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.itemId").value(1L));
    }

    private CreateItemRequest createItemRequest(String itemName) {
        return CreateItemRequest.builder()
                .itemName(itemName)
                .previous("NOW")
                .fix("F")
                .countryId(1L)
                .itemCateId(1L)
                .tripId(1L)
                .build();
    }

    private CreateItemResponse createItemResponse() {
        return CreateItemResponse.builder()
                .itemName("포마드")
                .previous("NOW")
                .countryId(1L)
                .itemCateId(1L)
                .tripId(1L)
                .build();
    }

    private UpdateItemRequest updateItemRequest(String itemName) {
        return UpdateItemRequest.builder()
                .itemName(itemName)
                .build();
    }

    private UpdateItemResponse updateItemResponse() {
        return UpdateItemResponse.builder()
                .itemName("포마드")
                .build();
    }



}