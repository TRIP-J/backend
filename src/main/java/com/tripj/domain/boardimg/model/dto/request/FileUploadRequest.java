package com.tripj.domain.boardimg.model.dto.request;

import lombok.Getter;

@Getter
public class FileUploadRequest {

    private String originalFileName;
    private String uploadFileName;
    private String uploadFilePath;
    private String uploadFileUrl;

}
