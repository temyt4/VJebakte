package com.server.controllers;

import com.server.domain.*;
import com.server.domain.dto.MessageDto;
import com.server.services.CommunityService;
import com.server.services.UserService;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * created by xev11
 */

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

    public static void commentPhoto(Comment comment,
                                    @RequestParam MultipartFile file, String uploadPath) throws IOException {
        if (file != null && !file.getOriginalFilename().isEmpty()) {
            File uploadDir = new File(uploadPath);

            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }

            String uuIDFile = UUID.randomUUID().toString();
            String resultFileName = uuIDFile + "." + file.getOriginalFilename();

            file.transferTo(new File(uploadPath + "/" + resultFileName));

            comment.setFilename(resultFileName);

        }
    }

    public static void addPhoto(Album album,
                                @RequestParam MultipartFile file, String uploadPath, UserService userService, User currentUser) throws IOException {
        if (file != null && !file.getOriginalFilename().isEmpty()) {
            File uploadDir = new File(uploadPath);

            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }

            String uuIDFile = UUID.randomUUID().toString();
            String resultFileName = uuIDFile + "." + file.getOriginalFilename();

            file.transferTo(new File(uploadPath + "/" + resultFileName));

            Photo photo = new Photo(resultFileName);
            photo.setAlbumId(album.getId());
            userService.addNewPhoto(photo, album, currentUser);

        }
    }

    public static List<ChatMessage> setToSortedList(Set<ChatMessage> messages, User friend) {
        return messages.stream().filter(chatMessage -> chatMessage.getFriendId().equals(friend.getId()))
                .collect(Collectors.toList());
    }

    public static void setFile(ChatMessage chatMessage,
                               @RequestParam MultipartFile file, String uploadPath) throws IOException {
        if (file != null && !file.getOriginalFilename().isEmpty()) {
            File uploadDir = new File(uploadPath);

            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }

            String uuIDFile = UUID.randomUUID().toString();
            String resultFileName = uuIDFile + "." + file.getOriginalFilename();

            file.transferTo(new File(uploadPath + "/" + resultFileName));

            chatMessage.setFilename(resultFileName);

        }
    }

    public static List<MessageDto> getAllMessages(User user, UserService userService, CommunityService communityService) {

        Set<MessageDto> messages = new HashSet<>();

        Set<User> friends = user.getFriends();

        for (User u : friends) {
            messages.addAll(userService.findUserMessageDtoById(u.getId()));
        }
        Set<Community> communities = user.getCommunities();
        for (Community community : communities) {
            messages.addAll(communityService.findMessageDtoByName(community.getName()));
        }

        return messages.stream().sorted((o1, o2) -> o2.getCreatedDate().compareTo(o1.getCreatedDate())).collect(Collectors.toList());

    }
}
