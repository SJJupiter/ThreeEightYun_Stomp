package fastwave.cloud.stomp.controller;

import com.alibaba.fastjson.JSON;
import fastwave.cloud.stomp.gameRule.ThreeCompare;
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
public class GameStompController {
    @Autowired
    private SimpMessagingTemplate template;

    //死亡颜色红色
    String deadColorStyle = "background-color: #ee2746;  border-radius: 5px;";
    String aliveColorStyle = "background-color: #0bbcd6;  border-radius: 5px;";

    @MessageMapping("/sendAttack")
    public void sendAttack(Map<String,String> params) {
        String fromUserId = params.get("fromUserId");
        String roomId = params.get("roomId");
        String choosedAttack = params.get("choosedAttack");
        String choosedUser = params.get("choosedUser");
        int selfBlood = Integer.parseInt(params.get("blood"));
        int selfQi = Integer.parseInt(params.get("qi"));
        User user = new User(fromUserId);
        user.setBlood(selfBlood);
        user.setQi(selfQi);
        user.setAttackName(choosedAttack);
        user.setAttackUser(choosedUser);
        user.setIsCompare(false);
        user.setHasNoQi(false);

        RoomInfo room = MapPool.roomMap.get(roomId);
        //已经点击攻击的UserList
        List<User> readyUserList = room.getAttackedUserList();
        readyUserList.add(user);
        MapPool.roomMap.put(roomId,room);

        System.out.println("房间： "+roomId+"-->用户： "+fromUserId + "--> 攻击招式：" + choosedAttack + "--> 攻击目标：" + choosedUser);

        template.convertAndSend("/topic/room_"+roomId, "玩家"+fromUserId+"已经攻击......");


        System.out.println("已攻击人数： "+MapPool.roomMap.get(roomId).getAttackedUserList().size()+"-->剩余人数： "+MapPool.roomMap.get(roomId).getLeftPlayerNum());

        //攻击人满则比较
        if(MapPool.roomMap.get(roomId).getAttackedUserList().size()== MapPool.roomMap.get(roomId).getLeftPlayerNum()){
            //比较
            ThreeCompare compare = new ThreeCompare();
            String result = compare.circleCompare(readyUserList);

            //玩家信息更新
            Map<String,Object> attackedUserFlash = new HashMap<>();
            for (int i=0;i<MapPool.roomMap.get(roomId).getAttackedUserList().size();i++){
                attackedUserFlash.put("user"+i, readyUserList.get(i).getName());
                attackedUserFlash.put("blood"+i, readyUserList.get(i).getBlood());
                attackedUserFlash.put("qi"+i, readyUserList.get(i).getQi());
                 //上回合攻击招式和攻击目标
                attackedUserFlash.put("lastAttackWay"+i, readyUserList.get(i).getAttackName());
                attackedUserFlash.put("lastAttackUser"+i, readyUserList.get(i).getAttackUser());
                //判断是否死亡
                if(readyUserList.get(i).getBlood()==0){
                    attackedUserFlash.put("lifeColor"+i, deadColorStyle);
                    attackedUserFlash.put("attackbtnDisabled"+i, true);
                    MapPool.roomMap.get(roomId).setLeftPlayerNum(MapPool.roomMap.get(roomId).getLeftPlayerNum()-1);
                }else {
                    attackedUserFlash.put("lifeColor"+i, aliveColorStyle);
                    attackedUserFlash.put("attackbtnDisabled"+i, false);
                }
            }
            attackedUserFlash.put("leftNum", readyUserList.size());
            String destination = "/topic/attackResult_" + roomId;
            template.convertAndSend(destination, JSON.toJSONString(attackedUserFlash));

            System.out.println(readyUserList);
            System.out.println("房间"+roomId+"-->结果: "+result);
            System.out.println();

            //判断游戏是否结束
            if (MapPool.roomMap.get(roomId).getLeftPlayerNum()==0){
                String gameOverMsg = "平局!!!";
//                //获胜场数加一
//                for (User item : MapPool.roomMap.get(roomId).getOnlineUserList()){
//                    item.setWinNum(item.getWinNum()+1);
//                }
                template.convertAndSend("/topic/gameOver_" + roomId,gameOverMsg);
                MapPool.roomMap.get(roomId).setLeftPlayerNum(MapPool.roomMap.get(roomId).getPlayerNum());
            }
            if (MapPool.roomMap.get(roomId).getLeftPlayerNum()==1){
                String winner =  readyUserList.get(0).getName();
                String gameOverMsg = winner+" is WINNER!!!";
                template.convertAndSend("/topic/gameOver_" + roomId, gameOverMsg);
                MapPool.roomMap.get(roomId).setLeftPlayerNum(MapPool.roomMap.get(roomId).getPlayerNum());
            }

            readyUserList.clear();
            MapPool.roomMap.put(roomId,room);
        }else {
            String destination = "/queue/attackResult_" + fromUserId;
            template.convertAndSend(destination, "请等待其他玩家攻击完成......");
        }

    }
}
