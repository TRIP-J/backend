package com.tripj.domain.boardimg.service;

import com.tripj.domain.board.model.entity.Board;
import com.tripj.domain.boardimg.model.entity.BoardImg;
import com.tripj.domain.boardimg.repository.BoardImgRepository;
import com.tripj.domain.common.dto.response.FileUploadResponse;
import com.tripj.global.code.ErrorCode;
import com.tripj.global.error.exception.InvalidException;
import com.tripj.global.util.FileUploadUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class BoardImgService {

    private final BoardImgRepository boardImgRepository;
    private final FileUploadUtil fileUploadUtil;

    public void uploadBoardImg(Board board, List<MultipartFile> images) throws IOException {
        for (MultipartFile img : images) {
            if (!img.isEmpty()) {
                FileUploadResponse imgResponse = fileUploadUtil.uploadFile("image", img);
                boardImgRepository.save(BoardImg.newBoardImg(imgResponse.getFileUrl(), imgResponse.getFilePath(),board));
            }
        }
    }

    public void validateImgCount(List<MultipartFile> images, Long limit) {
        if (images.size() > limit) {
            throw new InvalidException(ErrorCode.E400_INVALID_FILE_COUNT_TOO_MANY);
        }
    }





}
