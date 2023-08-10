package com.kbds.dgdgtalk;

import com.kbds.dgdgtalk.talk.repository.ChatRoomRepository;
import com.kbds.dgdgtalk.talk.value.ChatRoom;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.assertj.core.api.Assertions.assertThat;

/**
* <pre>
* Class Name : StompChatTest
* Description : 채팅 Test Code
*
*
*  Modification Information
*  Modify Date     Modifier        Comment
*  -----------------------------------------------
*  2022. 2. 22.     김성민            New
*  2022. 2. 28.     김성민            채팅방생성 테스트
*
* </pre>
*
* @author d220159(김성민)
* @since 2022. 2. 22.
*/

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class StompChatTest {
    @Autowired(required=false)
    private ChatRoomRepository chatRoomRepository;
    @Autowired(required=false)
    private MockMvc mockMvc;
    @Autowired(required=false)
    private MvcResult mvcResult;
    private MultiValueMap<String, String> multiValueMap;
    private ObjectMapper mapper;

    private static final Logger log = LoggerFactory.getLogger(StompChatTest.class);

    @Before
    public void reset() throws Exception{//테스트 시작전 parameter 초기화
        multiValueMap = new LinkedMultiValueMap<>();
        mapper = new ObjectMapper();
    }

    @Test
    @WithMockUser(username = "user1", password = "pwd", roles = "USER")
    public void createChatRoom() throws Exception{//채팅방 생성
        String testName = "테스트 채팅방 생성"; 
        multiValueMap.add("name", testName);
            //mock개체 생성, httpResponse 200인지 확인 후 response 값 돌려줌
        mvcResult = mockMvc.perform(post("/chat/room").secure(true).contentType(MediaType.APPLICATION_JSON).params(multiValueMap))
            .andExpect(status().isOk()).andReturn();

        String resultStr = mvcResult.getResponse().getContentAsString(Charset.forName("UTF-8")); // response의 content 내용을 String으로 받음
        ChatRoom chatRoom = mapper.readValue(resultStr, ChatRoom.class); //response를 ChatRoom의 형식으로 매핑함
        assertThat(chatRoom.getName()).isEqualTo(testName); //처음에 넣어준 테스트 네임과 일치하는지 확인
    }

    @Test
    @WithMockUser(username = "user1", password = "pwd", roles = "USER")
    public void readAllRoom() throws Exception{//방 읽어오기
        int testNum = 7;
        for (int i=0; i<testNum;i++) {
            chatRoomRepository.createChatRoom("테스트_"+i);
        }
        mvcResult = mockMvc.perform(get("/chat/rooms")).andExpect(status().isOk()).andReturn();
        String resultStr = mvcResult.getResponse().getContentAsString(Charset.forName("UTF-8"));
        ChatRoom[] list = mapper.readValue(resultStr, ChatRoom[].class);//결과값을 chatRoom Array로 매핑
        assertThat(list.length).isEqualTo(testNum);//생성된 방 갯수가 일치하는지 확인
        
        mockMvc.perform(get("/chat/room"))
            .andExpect(view().name("/chat/room"));//불러오는 채팅방 리스트 뷰 이름이 /chat/room 인지 확인
    }

    @Test
    @WithMockUser(username = "user1", password = "pwd", roles = "USER")
    public void readRoomById() throws Exception {

        chatRoomRepository.createChatRoom("테스트");
        mvcResult = mockMvc.perform(get("/chat/rooms")).andReturn();
        String resultStr = mvcResult.getResponse().getContentAsString(Charset.forName("UTF-8"));
        log.info(resultStr);
        //ChatRoom chatRoom = mapper.readValue(resultStr, ChatRoom.class);
        //String roomId = chatRoom.getRoomId();
        
        //mockMvc.perform(get("/chat/room/"+roomId)).andDo(print());
    }


//chatcontroller에 대한 테스트도 필요
}

