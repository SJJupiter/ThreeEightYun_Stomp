package fastwave.cloud.stomp.vo;

public class User {
    //气
    private int Qi;
    //血量
    private int blood;
    //用户名
    private String name;

    // yun dang0 dang1 biao adugen
    private String attackName;

    //攻击目标（攻击招式才能选择目标）
    private String attackUser;

    //是否已经被比较
    private boolean isCompare;

    //判断气足不足够
    private boolean hasNoQi;

    public User() {
    }


    public User(String name) {
        this.Qi = 0;
        this.blood = 1;
        this.name = name;
        this.attackName = "";
        this.attackUser = "";
        this.isCompare = false;
        this.hasNoQi = false;
    }

    public boolean isHasNoQi() {
        return hasNoQi;
    }

    public void setHasNoQi(boolean hasNoQi) {
        this.hasNoQi = hasNoQi;
    }

    public boolean getIsCompare() {
        return isCompare;
    }

    public void setIsCompare(boolean compare) {
        isCompare = compare;
    }

    public String getAttackUser() {
        return attackUser;
    }

    public void setAttackUser(String attackUser) {
        this.attackUser = attackUser;
    }

    public int getQi() {
        return Qi;
    }

    public void setQi(int qi) {
        Qi = qi;
    }

    public int getBlood() {
        return blood;
    }

    public void setBlood(int blood) {
        this.blood = blood;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAttackName() {
        return attackName;
    }

    public void setAttackName(String attackName) {
        this.attackName = attackName;
    }

    @Override
    public String toString() {
        return "User{" +
                "Qi=" + Qi +
                ", blood=" + blood +
                ", name='" + name + '\'' +
                ", attackName='" + attackName + '\'' +
                ", attackUser=" + attackUser +
                ", isCompare=" + isCompare +
                '}';
    }
}
