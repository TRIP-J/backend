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

    /**
     * 이미지 업로드
     */
    public void uploadBoardImg(Board board, List<MultipartFile> images) throws IOException {
        for (MultipartFile img : images) {
            if (!img.isEmpty()) {
                FileUploadResponse imgResponse = fileUploadUtil.uploadFile("image", img);
                boardImgRepository.save(BoardImg.newBoardImg(imgResponse.getFileUrl(), imgResponse.getFilePath(),board));
            }
        }
    }

    /**
     * 이미지 수정
     */
    public void updateImg(Board board, List<MultipartFile> images) throws IOException {
        List<BoardImg> oldImages = deleteImagesInS3(board);
        if (!oldImages.isEmpty()) {
            // 기존 이미지 삭제
            boardImgRepository.deleteAllInBatch(oldImages);
        }
        if (images != null) {
            // 새로운 이미지 업로드
            uploadBoardImg(board, images);
        }
    }

    /**
     * S3에서 이미지 삭제
     */
    public List<BoardImg> deleteImagesInS3(Board board) {
        List<BoardImg> imgInS3 = boardImgRepository.findAllByBoard(board);

        if (!imgInS3.isEmpty()) {
            imgInS3.forEach(image -> fileUploadUtil.deleteFile(image.getPath()));
        }
        return imgInS3;
    }

    /**
     * 업로드 가능 파일 개수 검증
     */
    public void validateImgCount(List<MultipartFile> images, Long limit) {
        if (images.size() > limit) {
            throw new InvalidException(ErrorCode.E400_INVALID_FILE_COUNT_TOO_MANY);
        }
    }







}
