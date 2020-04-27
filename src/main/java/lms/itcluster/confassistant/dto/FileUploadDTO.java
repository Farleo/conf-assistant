package lms.itcluster.confassistant.dto;

import org.springframework.web.multipart.MultipartFile;

public class FileUploadDTO {
    private MultipartFile inpFile;

    public MultipartFile getMultipartFile() {
        return inpFile;
    }

    public void setMultipartFile(MultipartFile inpFile) {
        this.inpFile = inpFile;
    }
}
