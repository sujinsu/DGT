package com.kbds.dgdgtalk.talk.value;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

/**
 * <pre>
 * Class Name : ChatMessage
 * Description : 채팅방 DTO
 *
 *
 *  Modification Information
 *  Modify Date     Modifier        Comment
 *  -----------------------------------------------
 *  2022. 2. 21.     허건영            New
 *  2022. 2. 23.     김성민            pub/sub 방식을 이용, DTO 간소화
 *  2022. 2. 22.     이수진            Fix for Redis
 * </pre>
 *
 * @author d220272(허건영)
 * @since 2022. 2. 21.
 */


 /**
  *  Redis 저장 객체 Serialize 가능해야함으로 Serializable 참조 및 serialVersionUID 세팅
  */
@Getter
@Setter
public class ChatRoom implements Serializable {
    
    private static final long serialVersionUID = 6494678977089006639L;

    private String roomId;
    private String name;

    public static ChatRoom create(String name) {
        ChatRoom chatRoom = new ChatRoom();
        chatRoom.roomId = UUID.randomUUID().toString();
        chatRoom.name = name;
        return chatRoom;
    }
}