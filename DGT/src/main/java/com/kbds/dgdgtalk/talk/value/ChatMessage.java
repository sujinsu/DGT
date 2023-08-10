package com.kbds.dgdgtalk.talk.value;

import lombok.Getter;
import lombok.Setter;

/**
 * <pre>
 * Class Name : ChatMessage
 * Description : 채팅 Message DTO
 *
 *
 *  Modification Information
 *  Modify Date     Modifier        Comment
 *  -----------------------------------------------
 *  2022. 2. 21.     허건영            New
 *
 * </pre>
 *
 * @author d220272(허건영)
 * @since 2022. 2. 21.
 */

@Getter
@Setter
public class ChatMessage {
    // 메시지 타입 : 입장, 채팅
    public enum MessageType {
        ENTER, TALK
    }
    private MessageType type; // 메시지 타입
    private String roomId; // 방번호
    private String sender; // 메시지 보낸사람
    private String message; // 메시지
}