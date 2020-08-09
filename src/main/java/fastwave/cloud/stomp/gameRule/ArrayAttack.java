package fastwave.cloud.stomp.gameRule;

import java.util.ArrayList;

//攻击招式
public class ArrayAttack {
    private ArrayList<String> arrayAttack;

    public ArrayList<String> getArrayAttack() {
        return arrayAttack;
    }

    public ArrayAttack() {
        arrayAttack = new ArrayList<>();

        this.arrayAttack.add("biao");
        this.arrayAttack.add("adugen");
    }
}
