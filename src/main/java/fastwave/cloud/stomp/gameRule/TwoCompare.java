package fastwave.cloud.stomp.gameRule;

import fastwave.cloud.stomp.vo.User;

import java.util.ArrayList;

//双人对战
public class TwoCompare {
    private ArrayAttack arrayAttack;
    private ArrayNoAttack arrayNoAttack;
    private ArrayList<String> arrAttack;
    private ArrayList<String> arrNoAttack;

    public TwoCompare() {
        arrayAttack = new ArrayAttack();
        arrayNoAttack = new ArrayNoAttack();

        arrAttack = arrayAttack.getArrayAttack();
        arrNoAttack = arrayNoAttack.getArrayNoAttack();
    }

    public static void main(String[] args) {
        TwoCompare compare = new TwoCompare();
        User shen = new User("神健杰");
        User wang = new User("王元赫");

        shen.setAttackName("yun");
        wang.setAttackName("yun");
        System.out.println(compare.compare(shen, wang));
        System.out.println("本回合双方状态如下：");
        System.out.println(shen.toString());
        System.out.println(wang.toString());

        shen.setAttackName("yun");
        shen.setAttackName("yun");
        System.out.println(compare.compare(shen, wang));
        System.out.println("本回合双方状态如下：");
        System.out.println(shen.toString());
        System.out.println(wang.toString());

        shen.setAttackName("biao");
        wang.setAttackName("dang0");
        System.out.println(compare.compare(shen, wang));
        System.out.println("本回合双方状态如下：");
        System.out.println(shen.toString());
        System.out.println(wang.toString());

        shen.setAttackName("biao");
        wang.setAttackName("dang0");
        System.out.println(compare.compare(shen, wang));
        System.out.println("本回合双方状态如下：");
        System.out.println(shen.toString());
        System.out.println(wang.toString());

        shen.setAttackName("yun");
        wang.setAttackName("adugen");
        System.out.println(compare.compare(shen, wang));
        System.out.println("本回合双方状态如下：");
        System.out.println(shen.toString());
        System.out.println(wang.toString());
    }

    // 四种情况
    public String compare(User a, User b) {
        String ret = "";

        //a攻击b
        if (arrAttack.contains(a.getAttackName()) && arrNoAttack.contains(b.getAttackName())) {
            ret = aAttackB(a, b);
        }

        //b攻击a
        else if (arrNoAttack.contains(a.getAttackName()) && arrAttack.contains(b.getAttackName())) {
            ret = aAttackB(b, a);
        }

        //互不攻击
        else if (arrNoAttack.contains(a.getAttackName()) && arrNoAttack.contains(b.getAttackName())) {
            ret = noAttack(a, b);
        }

        //互相攻击
        else if (arrAttack.contains(a.getAttackName()) && arrAttack.contains(b.getAttackName())) {
            ret = attackEachother(a, b);
        }
        return ret;
    }


    //互不攻击（只有运挡）
    public String noAttack(User a, User b) {
        String retmsg = "";
        String retmsgA = "";
        String retmsgB = "";
        String aAttackName = a.getAttackName();
        String bAttackName = b.getAttackName();

        //a是运
        if ("yun".equals(aAttackName)) {
            a.setQi(a.getQi() + 1);
            retmsgA = a.getName() + "加了一口气";
            if (hasNoQi(b)) {
                b.setBlood(0);
                retmsgB = b.getName() + "被憋死!";
            } else if ("yun".equals(bAttackName)) {
                b.setQi(b.getQi() + 1);
                retmsgB = b.getName() + "加了一口气";
            } else if ("dang0".equals(bAttackName)) {

            } else if ("dang1".equals(bAttackName)) {
                b.setQi(b.getQi() - 1);
            }
        }

        //a是挡
        else if ("dang0".equals(aAttackName)) {
            if (hasNoQi(b)) {
                b.setBlood(0);
                retmsgB = b.getName() + "被憋死!";
            } else if ("yun".equals(bAttackName)) {
                b.setQi(b.getQi() + 1);
                retmsgB = b.getName() + "加了一口气";
            } else if ("dang0".equals(bAttackName)) {

            } else if ("dang1".equals(bAttackName)) {
                b.setQi(b.getQi() - 1);
            }
        }

        //a是一气挡
        else if ("dang1".equals(aAttackName)) {
            if (a.getQi() < 1) {
                a.setBlood(0);
                retmsgA = a.getName() + "憋死了";
                if (hasNoQi(b)) {
                    b.setBlood(0);
                    retmsgB = b.getName() + "被憋死!";
                }
            } else if (a.getQi() >= 1) {

                a.setQi(a.getQi() - 1);

                if (hasNoQi(b)) {
                    b.setBlood(0);
                    retmsgB = b.getName() + "被憋死!";
                } else if ("yun".equals(bAttackName)) {
                    b.setQi(b.getQi() + 1);
                    retmsgB = b.getName() + "加了一口气";
                } else if ("dang0".equals(bAttackName)) {

                } else if ("dang1".equals(bAttackName)) {
                    b.setQi(b.getQi() - 1);
                }
            }

        }

        return retmsg + "," + retmsgA + "," + retmsgB;
    }


    //a攻击b（a是攻击招式，b不是）
    public String aAttackB(User a, User b) {
        String retmsg = "";
        String retmsgA = "";
        String retmsgB = "";
        String aAttackName = a.getAttackName();
        String bAttackName = b.getAttackName();

        //a是镖
        if ("biao".equals(aAttackName) && a.getQi() < 1) {
            a.setBlood(0);
            retmsgA = a.getName() + "被憋死!";
            if (hasNoQi(b)) {
                b.setBlood(0);
                retmsg = "平局";
                retmsgB = b.getName() + "被憋死!";
            }
        } else if ("biao".equals(aAttackName) && a.getQi() >= 1) {

            a.setQi(a.getQi() - 1);

            if (hasNoQi(b)) {
                b.setBlood(0);
                retmsgB = b.getName() + "被憋死!";
            } else if ("yun".equals(bAttackName)) {
                b.setBlood(b.getBlood() - 1);
                retmsg = a.getName() + "把" + b.getName() + "打死!";
            } else if ("dang0".equals(bAttackName)) {
                retmsg = "什么都没发生";
            } else if ("dang1".equals(bAttackName)) {
                b.setQi(b.getQi() - 1);
                retmsg = "什么都没发生";
            }
        }

        //a是阿杜跟
        else if ("adugen".equals(aAttackName) && a.getQi() < 2) {
            a.setBlood(0);
            retmsgA = a.getName() + "被憋死!";
            if (hasNoQi(b)) {
                b.setBlood(0);
                retmsg = "平局";
                retmsgB = b.getName() + "被憋死!";
            }
        } else if ("adugen".equals(aAttackName) && a.getQi() >= 2) {

            a.setQi(a.getQi() - 2);

            if (hasNoQi(b)) {
                b.setBlood(0);
                retmsgB = b.getName() + "被憋死!";
            } else if ("yun".equals(bAttackName)) {
                b.setBlood(b.getBlood() - 1);
                retmsg = a.getName() + "把" + b.getName() + "打死!";
            } else if ("dang0".equals(bAttackName)) {
                b.setBlood(b.getBlood() - 1);
                retmsg = a.getName() + "把" + b.getName() + "打死!";
            } else if ("dang1".equals(bAttackName)) {
                b.setQi(b.getQi() - 1);
                retmsg = "什么都没发生";
            }
        }

        return retmsg + "," + retmsgA + "," + retmsgB;
    }


    // yun dang0 dang1 biao adugen
    //互相攻击（有攻击目标，只有攻击招式）
    public String attackEachother(User a, User b) {
        String retmsg = "";
        String retmsgA = "";
        String retmsgB = "";
        String aAttackName = a.getAttackName();
        String bAttackName = b.getAttackName();

        //a是镖
        if ("biao".equals(aAttackName) && a.getQi() < 1) {
            a.setBlood(0);
            retmsgA = a.getName() + "被憋死!";
            if (hasNoQi(b)) {
                b.setBlood(0);
                retmsg = "平局";
                retmsgB = b.getName() + "被憋死!";
            }
        } else if ("biao".equals(aAttackName) && a.getQi() >= 1) {

            a.setQi(a.getQi() - 1);

            if (hasNoQi(b)) {
                b.setBlood(0);
                retmsgB = b.getName() + "被憋死!";
            } else if ("biao".equals(bAttackName)) {
                b.setQi(b.getQi() - 1);
                retmsg = "什么都没发生";
            } else if ("adugen".equals(bAttackName)) {
                b.setQi(a.getQi() - 2);
                a.setBlood(a.getBlood() - 1);
                retmsg = b.getName() + "把" + a.getName() + "打死!";
            }
        }

        //a是阿杜跟
        else if ("adugen".equals(aAttackName) && a.getQi() < 2) {
            a.setBlood(0);
            retmsgA = a.getName() + "被憋死!";
            if (hasNoQi(b)) {
                b.setBlood(0);
                retmsg = "平局";
                retmsgB = b.getName() + "被憋死!";
            }
        } else if ("adugen".equals(aAttackName) && a.getQi() >= 2) {

            a.setQi(a.getQi() - 2);

            if (hasNoQi(b)) {
                b.setBlood(0);
                retmsgB = b.getName() + "被憋死!";
            } else if ("biao".equals(bAttackName)) {
                b.setQi(b.getQi() - 1);
                b.setBlood(b.getBlood() - 1);
                retmsg = a.getName() + "把" + b.getName() + "打死!";
            } else if ("adugen".equals(bAttackName)) {
                b.setQi(a.getQi() - 2);
                retmsg = "什么都没发生";
            }
        }
        return retmsg + "," + retmsgA + "," + retmsgB;


//        //a是运
//        if("yun".equals(a.getAttackName())){
//            if(hasNoQi(b)){
//                b.setBlood(0);
//                retmsg = b.getName() + "被憋死!";
//            }else if(("biao".equals(b.getAttackName()) && b.getQi()>=1 ) ||  ("adugen".equals(b.getAttackName()) && b.getQi()>=2)){
//                a.setBlood(a.getBlood()-1);
//                retmsg = a.getName() + "扣一滴血";
//            }else if("yun".equals(b.getAttackName())){
//                a.setQi(a.getQi()+1);
//                b.setQi(b.getQi()+1);
//                retmsg = a.getName() + b.getName() + "都加一口气";
//            }else{
//                a.setQi(a.getQi()+1);
//                retmsg = a.getName() + "加一口气";
//            }
//        }
//
//        //a是挡
//        else if("dang0".equals(a.getAttackName())){
//            if(hasNoQi(b)){
//                b.setBlood(0);
//                retmsg = b.getName() + "被憋死!";
//            }else if("adugen".equals(b.getAttackName())){
//                a.setBlood(a.getBlood()-1);
//                retmsg = a.getName() + "扣一滴血";
//            }else if("yun".equals(b.getAttackName())){
//                b.setQi(b.getQi()+1);
//                retmsg = b.getName() + "加一口气";
//            }else{
//                retmsg = "没有事情发生";
//            }
//        }
//
//        //a是一气挡
//        else if("dang1".equals(a.getAttackName())){
//            if(hasNoQi(b)){
//                b.setBlood(0);
//                retmsg = b.getName() + "被憋死!";
//            }else if("yun".equals(b.getAttackName())){
//                b.setQi(b.getQi()+1);
//                retmsg = b.getName() + "加一口气";
//            }else {
//                retmsg = "没有事情发生";
//            }
//        }
//
//        //a是攻击者
//        else {
//            retmsg = attackEachother(b,a);
//        }

    }


    public boolean hasNoQi(User user) {
        if (("biao".equals(user.getAttackName()) && user.getQi() < 1)
                || ("adugen".equals(user.getAttackName()) && user.getQi() < 2)
                || ("dang1".equals(user.getAttackName()) && user.getQi() < 1)) {
            return true;
        }
        return false;
    }
}

