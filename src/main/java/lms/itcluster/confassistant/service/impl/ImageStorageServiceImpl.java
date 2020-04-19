package lms.itcluster.confassistant.service.impl;

import lms.itcluster.confassistant.service.ImageStorageService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class ImageStorageServiceImpl implements ImageStorageService {

    private String uploadPath = System.getProperty("user.dir");

    @Override
    public String saveAndReturnImageLink(MultipartFile file) throws IOException {
        String uuIdFile = UUID.randomUUID().toString();
        String resultFileName = uuIdFile + file.getOriginalFilename();
        File uploadFile = new File(uploadPath + "/media/");
        if (!uploadFile.exists()) {
            uploadFile.mkdir();
        }
        File newPhoto = new File(uploadPath + "/media/" + resultFileName);
        file.transferTo(newPhoto);
        return  "/media/" + resultFileName;
    }

    @Override
    public void removeOldImage(String imageLink) {
        Path path = Paths.get(uploadPath + imageLink);
        try {
            if (Files.exists(path)) {
                Files.delete(path);
            }
        } catch (IOException e) {

        }
    }
}
