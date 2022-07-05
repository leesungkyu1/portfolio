package com.reactpractice.lee.chat;

import com.reactpractice.lee.common.CommonHeaderVO;
import com.reactpractice.lee.common.CommonResponseVO;
import com.reactpractice.lee.dao.ChatMapper;
import com.reactpractice.lee.service.ChatService;
import com.reactpractice.lee.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/chat")
public class ChatRestCtl {

    @Autowired
    private ChatService chatService;

    @GetMapping("")
    public List<ChatRoom> chatList(ChatRoom chatRoom) {
        List<ChatRoom> chatList = chatService.findAllRoom(chatRoom);

        return chatList;
    }

    @Transactional
    @PostMapping("")
    public ResponseEntity<CommonResponseVO> createChatRoom(@RequestBody HashMap<String, Object> createMap){
        int roomKey = chatService.createRoom(createMap.get("title").toString());
        ChatMember chatMember = new ChatMember();
        chatMember.setUserKeyFk((int)createMap.get("userKey"));
        chatMember.setType("master");
        chatMember.setChatRoomKeyFk(roomKey);
        chatService.chatMemberJoin(chatMember);

        return ResponseEntity.ok(CommonResponseVO.defaultResponseVO());
    }

    @PostMapping("/join")
    public ResponseEntity<CommonResponseVO> chatRoomJoin(@RequestBody HashMap<String, Object> joinMap){

        chatService.chatMemberJoin(joinMap);
        return ResponseEntity.ok(CommonResponseVO.defaultResponseVO());
    }
}
