package fastwave.cloud.stomp.vo;

import java.util.ArrayList;
import java.util.List;

public class RoomInfo {
    private int leftPlayerNum;
    private int playerNum;
    //倒计时时间
    private int time;
    private List<User> onlineUserList;
    //用来每回合的比较，回合结束时就清空
    private List<User> AttackedUserList;
    //准备好下一局的list
    private List<User> readyNextUserList;

    public RoomInfo() {
        //游戏中剩余玩家人数
        this.leftPlayerNum = 0;
        //几人场
        this.playerNum = 0;
        this.time = 0;
        //在线玩家池
        this.onlineUserList = new ArrayList<>();
        //已经攻击的玩家池
        AttackedUserList = new ArrayList<>();

        readyNextUserList = new ArrayList<>();
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public List<User> getReadyNextUserList() {
        return readyNextUserList;
    }

    public void setReadyNextUserList(List<User> readyNextUserList) {
        this.readyNextUserList = readyNextUserList;
    }

    public List<User> getOnlineUserList() {
        return onlineUserList;
    }

    public void setOnlineUserList(List<User> onlineUserList) {
        this.onlineUserList = onlineUserList;
    }

    public List<User> getAttackedUserList() {
        return AttackedUserList;
    }

    public void setAttackedUserList(List<User> AttackedUserList) {
        this.AttackedUserList = AttackedUserList;
    }

    public int getLeftPlayerNum() {
        return leftPlayerNum;
    }

    public void setLeftPlayerNum(int leftPlayerNum) {
        this.leftPlayerNum = leftPlayerNum;
    }

    public int getPlayerNum() {
        return playerNum;
    }

    public void setPlayerNum(int playerNum) {
        this.playerNum = playerNum;
    }
}
