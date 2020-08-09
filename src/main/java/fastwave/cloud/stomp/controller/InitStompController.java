package fastwave.cloud.stomp.controller;

import com.alibaba.fastjson.JSON;
import fastwave.cloud.stomp.vo.RoomInfo;
import fastwave.cloud.stomp.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class InitStompController {
    @Autowired
    private SimpMessagingTemplate template;
    //重名序号
    int idx=1;

    //建立连接初始页面
    @MessageMapping("/sendUserInfo")
    public void sendTheUserInfo(Map<String,String> params) {
        String userId = params.get("userId");
        String roomId = params.get("roomId");
        int playerNum = Integer.parseInt(params.get("playerNum"));

        System.out.println("新在线用户："+ userId+"--->进入房间"+roomId);

        User user = new User(userId);

        //判断是否已经有房间
        if (!MapPool.roomMap.containsKey(roomId)){
            RoomInfo room = new RoomInfo();
            room.setLeftPlayerNum(1);
            room.getOnlineUserList().add(user);
            room.setPlayerNum(playerNum);
            MapPool.roomMap.put(roomId,room);
        }else{
            RoomInfo room = MapPool.roomMap.get(roomId);

            //判断是否重名
            for (User item : room.getOnlineUserList()) {
                if (item.getName().equals(userId)){
                    String responseMsg = "有玩家重名，名字自动添加序号！！！";
                    user.setName(userId+idx);
                    idx++;
                    String destination = "/topic/room_" + roomId;
                    template.convertAndSend(destination, responseMsg);
                    System.out.println("玩家重名！！！");
                }
            }

            int lPlayerNum = room.getLeftPlayerNum();
            room.setLeftPlayerNum(lPlayerNum+1);
            room.getOnlineUserList().add(user);
            MapPool.roomMap.put(roomId,room);
        }

        //判断人是否满
        if (MapPool.roomMap.containsKey(roomId)){
            if (MapPool.roomMap.get(roomId).getLeftPlayerNum() > MapPool.roomMap.get(roomId).getPlayerNum()){
                String responseMsg = "isFull";
                String destination = "/queue/ifFull_" + userId;
                template.convertAndSend(destination, responseMsg);
                System.out.println("房间号"+ roomId +"人数已满，请去其他房间！");
                System.out.println("房间："+ roomId+"--->在线人数为："+ MapPool.roomMap.get(roomId).getOnlineUserList().size()+
                        "-->几人场"+MapPool.roomMap.get(roomId).getPlayerNum()+
                        "-->用户有：" + MapPool.roomMap.get(roomId).getOnlineUserList());
                return;
            }
        }


        //用户列表
        List<User> onlineUserList = MapPool.roomMap.get(roomId).getOnlineUserList();
        System.out.println("房间："+ roomId+"--->在线人数为："+ onlineUserList.size()+
                "-->几人场"+MapPool.roomMap.get(roomId).getPlayerNum()+
                "-->用户有：" + onlineUserList);

        if(onlineUserList.size()==playerNum){
            //刷新当前房间的布局，需要发送几人场，在线人数，所有玩家名
            Map<String,Object> flashRoomInfo = new HashMap<>();
            flashRoomInfo.put("realPlayerNum", MapPool.roomMap.get(roomId).getPlayerNum());
            flashRoomInfo.put("onlineNum", MapPool.roomMap.get(roomId).getLeftPlayerNum());
            for (int i=0;i<playerNum;i++){
                flashRoomInfo.put("user"+i, MapPool.roomMap.get(roomId).getOnlineUserList().get(i).getName());
            }
            for (int i=0;i<playerNum;i++){
                flashRoomInfo.put("blood"+i, 1);
            }
            for (int i=0;i<playerNum;i++){
                flashRoomInfo.put("qi"+i, 0);
            }
            for (int i = 0; i < playerNum; i++) {
                flashRoomInfo.put("lifeColor"+i, "background-color: #0bbcd6;  border-radius: 5px;");
                flashRoomInfo.put("attackbtnDisabled"+i, false);
            }
            String destination = "/topic/roomFlash_" + roomId;
            template.convertAndSend(destination, JSON.toJSONString(flashRoomInfo));
        }
    }

    //退出房间
    @MessageMapping("/sendDisconnectInfo")
    public void sendDisconnectInfo(Map<String,String> params) {

        String userId = params.get("userId");
        String roomId = params.get("roomId");

        System.out.println("新在线用户："+ userId+"--->离开房间"+roomId);

        RoomInfo room = MapPool.roomMap.get(roomId);
        List<User> onlineUserList = room.getOnlineUserList();
        onlineUserList.removeIf(item -> item.getName().equals(userId));
//        for (User item : onlineUserList) {
//            if (item.getName().equals(userId)){
//                onlineUserList.remove(item);
//            }
//        }

        //判断房间是否还有人
        if (onlineUserList.size()==0){
            MapPool.roomMap.remove(roomId);
            System.out.println("玩家全部退出，房间已销毁！");
        }
        MapPool.roomMap.put(roomId,room);

        System.out.println("房间："+ roomId+"--->人数为："+ onlineUserList.size());
    }

    //准备下一局
    @MessageMapping("/sendReadyNextInfo")
    public void sendReadyNextInfo(Map<String,String> params) {
        String userId = params.get("userId");
        String roomId = params.get("roomId");

        int playerNum = MapPool.roomMap.get(roomId).getPlayerNum();
        List<User> readyNextUserList = MapPool.roomMap.get(roomId).getReadyNextUserList();
        List<User> onlineUserList = MapPool.roomMap.get(roomId).getOnlineUserList();
        if(onlineUserList.size() < playerNum){
            readyNextUserList.clear();
        }

        User user = new User(userId);
        readyNextUserList.add(user);
        String msg = userId+"： 已准备下一局......";
        template.convertAndSend("/topic/room_" + roomId, msg);
        System.out.println(msg);

        if(readyNextUserList.size() == playerNum){
            //刷新当前房间的布局，需要发送几人场，在线人数，所有玩家名
            Map<String,Object> flashRoomInfo = new HashMap<>();
            flashRoomInfo.put("realPlayerNum", MapPool.roomMap.get(roomId).getPlayerNum());
            flashRoomInfo.put("onlineNum", MapPool.roomMap.get(roomId).getLeftPlayerNum());
            for (int i=0;i<playerNum;i++){
                flashRoomInfo.put("user"+i, readyNextUserList.get(i).getName());
            }
            for (int i=0;i<playerNum;i++){
                flashRoomInfo.put("blood"+i, 1);
            }
            for (int i=0;i<playerNum;i++){
                flashRoomInfo.put("qi"+i, 0);
            }
            for (int i = 0; i < playerNum; i++) {
                flashRoomInfo.put("lifeColor"+i, "background-color: #0bbcd6;  border-radius: 5px;");
                flashRoomInfo.put("attackbtnDisabled"+i, false);
            }
            String destination = "/topic/roomFlash_" + roomId;
            template.convertAndSend(destination, JSON.toJSONString(flashRoomInfo));

            readyNextUserList.clear();
        }
    }
}
