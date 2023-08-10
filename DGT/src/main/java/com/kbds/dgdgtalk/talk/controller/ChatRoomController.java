package com.kbds.dgdgtalk.talk.controller;

import com.kbds.dgdgtalk.auth.util.LoginUser;
import com.kbds.dgdgtalk.auth.value.SessionUser;
import com.kbds.dgdgtalk.talk.repository.ChatRoomRepository;
import com.kbds.dgdgtalk.talk.value.ChatRoom;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * <pre>
 * Class Name : ChatRoomController
 * Description : 채팅방 컨트롤러
 *
 *
 *  Modification Information
 *  Modify Date     Modifier        Comment
 *  -----------------------------------------------
 *  2022. 2. 22.     김성민            New
 *  2022. 3.  3.     김성민    로그인 아닐 시 로그인 화면 이동
 *  2022. 3.  6      허건영    sessionUser를 model에 추가
 * </pre>
 *
 * @author d220159(김성민)
 * @since 2022. 2. 22.
 */

 /**
  * Websocket 통신 외 채팅 화면 View를 띄워주는 Controller
  */
@RequiredArgsConstructor
@Controller
@RequestMapping("/chat")
public class ChatRoomController {

    private final ChatRoomRepository chatRoomRepository;

    // 채팅 리스트 화면
    @GetMapping("/room")
    public String rooms(Model model, @LoginUser SessionUser sessionUser) throws Exception {
        if (sessionUser == null) {
            return "login";
        }

        model.addAttribute("sessionUser", sessionUser);
        return "chat/room";
    }

    // 모든 채팅방 목록 반환
    @GetMapping("/rooms")
    @ResponseBody
    public List<ChatRoom> room() {
        return chatRoomRepository.findAllRoom();
    }

    // 채팅방 생성
    @PostMapping("/room")
    @ResponseBody
    public ChatRoom createRoom(@RequestParam String name) {
        return chatRoomRepository.createChatRoom(name);
    }

    // 채팅방 입장 화면
    @GetMapping("/room/enter/{roomId}")
    public String roomDetail(Model model, @LoginUser SessionUser sessionUser, @PathVariable String roomId) {
        if (sessionUser == null) { // 채팅방에서 로그인된 사람인지 확인
            return "login";
        }

        model.addAttribute("sessionUser", sessionUser);
        model.addAttribute("roomId", roomId);
        return "chat/roomdetail";
    }

    // 특정 채팅방 조회
    @GetMapping("/room/{roomId}")
    @ResponseBody
    public ChatRoom roomInfo(@PathVariable String roomId) {
        return chatRoomRepository.findRoomById(roomId);
    }
}
