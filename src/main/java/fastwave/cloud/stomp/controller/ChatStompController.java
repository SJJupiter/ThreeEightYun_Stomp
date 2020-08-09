package fastwave.cloud.stomp.controller;

import com.alibaba.fastjson.JSON;
import fastwave.cloud.stomp.gameRule.ThreeCompare;
import fastwave.cloud.stomp.vo.RoomInfo;
import fastwave.cloud.stomp.vo.User;
import io.netty.util.internal.InternalThreadLocalMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
public class ChatStompController {
    @Autowired
    private SimpMessagingTemplate template;

    @MessageMapping("/sendToUser")
    public void sendToUser(Map<String,String> params) {
        String fromUserId = params.get("fromUserId");
        String toUserId = params.get("toUserId");
        String msg = fromUserId + ":" + params.get("msg");
        String destination1 = "/queue/user_" + toUserId;
        String destination2 = "/queue/user_" + fromUserId;
        template.convertAndSend(destination1, msg);
        template.convertAndSend(destination2, msg);
        System.out.println(msg);
    }

    @MessageMapping("/sendToRoom")
    public void sendToRoom(Map<String,String> params) {
        String fromUserId = params.get("fromUserId");
        String roomId = params.get("roomId");
        String msg = fromUserId + ":" + params.get("msg");
        String destination = "/topic/room_" + roomId;
        template.convertAndSend(destination, msg);
        System.out.println("房间"+roomId+": "+msg);
    }

}
