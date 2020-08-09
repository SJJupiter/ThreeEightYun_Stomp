package fastwave.cloud.stomp.controller;

import com.alibaba.fastjson.JSON;
import fastwave.cloud.stomp.gameRule.ThreeCompare;
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

    StringBuilder sb = new StringBuilder();

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
        //已经点击攻击的UserList
        List<User> readyUserList = MapPool.roomMap.get(roomId).getAttackedUserList();
        readyUserList.add(user);

        sb.append("(用户： "+fromUserId + ")--> (攻击招式：" + choosedAttack + ")--> (攻击目标：" + choosedUser+ ")\n");
        System.out.println("房间： "+roomId+"-->用户： "+fromUserId + "--> 攻击招式：" + choosedAttack + "--> 攻击目标：" + choosedUser);

        //攻击人满则比较
        if(readyUserList.size()== MapPool.roomMap.get(roomId).getLeftPlayerNum()){
            //比较
            ThreeCompare compare = new ThreeCompare();
            String result = compare.circleCompare(readyUserList);

            //玩家信息更新
            Map<String,Object> attackedUserFlash = new HashMap<>();
            for (int i=0;i<readyUserList.size();i++){
                attackedUserFlash.put("user"+i, readyUserList.get(i).getName());
            }
            for (int i=0;i<readyUserList.size();i++){
                attackedUserFlash.put("blood"+i, readyUserList.get(i).getBlood());
            }
            for (int i=0;i<readyUserList.size();i++){
                attackedUserFlash.put("qi"+i, readyUserList.get(i).getQi());
            }
            //判断是否死亡
            for (int i = 0; i < readyUserList.size(); i++) {
                if(readyUserList.get(i).getBlood()==0){
                    attackedUserFlash.put("lifeColor"+i, deadColorStyle);
                    attackedUserFlash.put("attackbtnDisabled"+i, true);
                    attackedUserFlash.put("nextbtnDisabled"+i, false);
                    MapPool.roomMap.get(roomId).setLeftPlayerNum(MapPool.roomMap.get(roomId).getLeftPlayerNum()-1);
                }else {
                    attackedUserFlash.put("lifeColor"+i, aliveColorStyle);
                    attackedUserFlash.put("btnDisabled"+i, false);
                }
               }
            attackedUserFlash.put("leftNum",readyUserList.size());
            attackedUserFlash.put("result",sb.toString());
            String destination = "/topic/attackResult_" + roomId;
            template.convertAndSend(destination, JSON.toJSONString(attackedUserFlash));

            System.out.println(readyUserList);
            System.out.println("房间"+roomId+"-->结果: "+result);
            System.out.println();

            //清空sb，否则带上上轮
            sb.setLength(0);


            //判断游戏是否结束
            if (MapPool.roomMap.get(roomId).getLeftPlayerNum()==0){
                template.convertAndSend("/topic/room_" + roomId,"平局!!!");
                MapPool.roomMap.get(roomId).setLeftPlayerNum(MapPool.roomMap.get(roomId).getPlayerNum());
            }
            if (MapPool.roomMap.get(roomId).getLeftPlayerNum()==1){
                template.convertAndSend("/topic/room_" + roomId,readyUserList.get(0).getName()+" is WINNER!!!");
                MapPool.roomMap.get(roomId).setLeftPlayerNum(MapPool.roomMap.get(roomId).getPlayerNum());
            }

            readyUserList.clear();
        }else {
            String destination = "/queue/attackResult_" + fromUserId;
            template.convertAndSend(destination, "请等待其他玩家攻击完成......");
        }

    }
}
