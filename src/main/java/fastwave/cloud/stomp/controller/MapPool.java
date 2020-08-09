package fastwave.cloud.stomp.controller;

import fastwave.cloud.stomp.vo.RoomInfo;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class MapPool {
    //房间号：人数
    public static HashMap<String, RoomInfo> roomMap = new HashMap<>();
}
