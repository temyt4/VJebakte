package com.server.controllers;

import com.server.domain.CommMessage;
import com.server.domain.Community;
import com.server.domain.User;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class ControllerUtil {
    public static void editAvatar(User user,
                                  @RequestParam MultipartFile file, String uploadPath) throws IOException {
        if (file != null && !file.getOriginalFilename().isEmpty()) {
            File uploadDir = new File(uploadPath);

            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }

            String uuIDFile = UUID.randomUUID().toString();
            String resultFileName = uuIDFile + "." + file.getOriginalFilename();

            file.transferTo(new File(uploadPath + "/" + resultFileName));

            user.setUser_avatar(resultFileName);

        }
    }

    public static void editAvatar(Community community,
                                  @RequestParam MultipartFile file, String uploadPath) throws IOException {
        if (file != null && !file.getOriginalFilename().isEmpty()) {
            File uploadDir = new File(uploadPath);

            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }

            String uuIDFile = UUID.randomUUID().toString();
            String resultFileName = uuIDFile + "." + file.getOriginalFilename();

            file.transferTo(new File(uploadPath + "/" + resultFileName));

            community.setAvatar(resultFileName);

        }
    }
}
