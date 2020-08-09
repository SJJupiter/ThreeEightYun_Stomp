package fastwave.cloud.stomp.gameRule;

import fastwave.cloud.stomp.vo.User;

import java.util.List;

//三人对战（已经比较过的人，不重复加减气，但是可以重复造成伤害）
public class ThreeCompare {
    public static void main(String[] args) {
//        ThreeCompare compare = new ThreeCompare();
//
//        User shen = new User("神健杰");
//        User wang = new User("王元赫");
//        User liang = new User("梁战");
//
//        shen.setIsCompare(false);
//        wang.setIsCompare(false);
//        liang.setIsCompare(false);
//        shen.setAttackName("yun");
//        wang.setAttackName("yun");
//        liang.setAttackName("yun");
//        System.out.println(compare.circleCompare(shen,wang,liang));
////        System.out.println(compare.compare(shen,wang));
//        System.out.println("本回合双方状态如下：");
//        System.out.println(shen.toString());
//        System.out.println(wang.toString());
//        System.out.println(liang.toString());
//
//        shen.setIsCompare(false);
//        wang.setIsCompare(false);
//        liang.setIsCompare(false);
//        shen.setAttackName("yun");
//        wang.setAttackName("yun");
//        liang.setAttackName("yun");
//        System.out.println(compare.circleCompare(shen,wang,liang));
////        System.out.println(compare.compare(shen,wang));
//        System.out.println("本回合双方状态如下：");
//        System.out.println(shen.toString());
//        System.out.println(wang.toString());
//        System.out.println(liang.toString());
//
//        shen.setIsCompare(false);
//        wang.setIsCompare(false);
//        liang.setIsCompare(false);
//        shen.setAttackName("yun");
//        wang.setAttackName("yun");
//        liang.setAttackName("yun");
//        System.out.println(compare.circleCompare(shen,wang,liang));
////        System.out.println(compare.compare(shen,wang));
//        System.out.println("本回合双方状态如下：");
//        System.out.println(shen.toString());
//        System.out.println(wang.toString());
//        System.out.println(liang.toString());
//
//
////        shen.setIsCompare(false);
////        wang.setIsCompare(false);
////        shen.setAttackUser(null);
////        wang.setAttackUser(null);
////        shen.setAttackName("biao");
////        shen.setAttackUser("王元赫");
////        wang.setAttackName("yun");
//////        wang.setAttackUser("梁战");
////        System.out.println(compare.compare(shen,wang));
////        System.out.println("本回合双方状态如下：");
////        System.out.println(shen.toString());
////        System.out.println(wang.toString());
//
//        shen.setIsCompare(false);
//        wang.setIsCompare(false);
//        liang.setIsCompare(false);
//        shen.setAttackUser(null);
//        wang.setAttackUser(null);
//        liang.setAttackUser(null);
//        shen.setAttackName("adugen");
//        shen.setAttackUser("王元赫");
//        wang.setAttackName("adugen");
//        wang.setAttackUser("梁战");
//        liang.setAttackName("adugen");
//        liang.setAttackUser("神健杰");
//        System.out.println(compare.circleCompare(shen,wang,liang));
//        System.out.println("本回合双方状态如下：");
//        System.out.println(shen.toString());
//        System.out.println(wang.toString());
//        System.out.println(liang.toString());
//
//        shen.setIsCompare(false);
//        wang.setIsCompare(false);
//        liang.setIsCompare(false);
//        shen.setAttackUser(null);
//        wang.setAttackUser(null);
//        liang.setAttackUser(null);
//        shen.setAttackName("yun");
//        shen.setAttackUser(null);
//        wang.setAttackName("biao");
//        wang.setAttackUser("梁战");
//        liang.setAttackName("yun");
//        liang.setAttackUser(null);
//        System.out.println(compare.circleCompare(shen,wang,liang));
//        System.out.println("本回合双方状态如下：");
//        System.out.println(shen.toString());
//        System.out.println(wang.toString());
//        System.out.println(liang.toString());
//
//        shen.setIsCompare(false);
//        wang.setIsCompare(false);
//        liang.setIsCompare(false);
//        shen.setAttackUser(null);
//        wang.setAttackUser(null);
//        liang.setAttackUser(null);
//        shen.setAttackName("adugen");
//        shen.setAttackUser("王元赫");
//        wang.setAttackName("biao");
//        wang.setAttackUser("梁战");
//        liang.setAttackName("yun");
//        liang.setAttackUser(null);
//        System.out.println(compare.circleCompare(shen,wang,liang));
//        System.out.println("本回合双方状态如下：");
//        System.out.println(shen.toString());
//        System.out.println(wang.toString());
//        System.out.println(liang.toString());
    }

    public ThreeCompare() {
    }

    //
//    public String circleCompare(User a, User b, User c){
//        StringBuilder sb =  new StringBuilder();
////        if(a.getBlood()!=0 && b.getBlood()!=0 )
//            sb.append(compare(a, b));
//
//        sb.append("-----");
////        if(b.getBlood()!=0 && c.getBlood()!=0 )
//            sb.append(compare(b, c));
//
//        sb.append("-----");
////        if(c.getBlood()!=0 && a.getBlood()!=0 )
//            sb.append(compare(c, a));
//
//        return sb.toString();
//    }
    public String circleCompare(List<User> userList) {
        StringBuilder sb = new StringBuilder();

        int len = userList.size();
        for (int i = 0; i < len - 1; i++) {
            for (int j = i + 1; j < len; j++) {
//                if (userList.get(i).getBlood()!=0 && userList.get(j).getBlood()!=0){
                sb.append(compare(userList.get(i), userList.get(j)));
                sb.append("-----");
//                }
            }
        }

        return sb.toString();
    }


    // 三大种情况
    //1、ab互不选择（1、a非攻击，b非攻击；2、a非攻击，b攻击其他人；3、a攻击其他人，b非攻击；4、a攻击其他人，b攻击其他人）
    //2、单向选择，a选择（攻击）b，b未选择a（分为1、b使用非攻击招式；2、b攻击其他人两种情况；2.3、；2.4、ab相反）
    //3、ab互相选择（3.1、攻击）
    public String compare(User a, User b) {
        String ret = "";
        String aAttackUser = a.getAttackUser();
        String bAttackUser = b.getAttackUser();

        //1.1
        if ("".equals(aAttackUser) && "".equals(bAttackUser)) {
            ret = noAttack_1(a, b);
            System.out.println("情况是1.1");
        }
        //1.2
        else if ("".equals(aAttackUser) && (!bAttackUser.equals(a.getName()))) {
            ret = noAttack_2(a, b);
            System.out.println("情况是1.2");
        }
        //1.3
        else if (((!"".equals(aAttackUser)) && !aAttackUser.equals(b.getName())) && "".equals(bAttackUser)) {
            ret = noAttack_2(b, a);
            System.out.println("情况是1.3");
        }
        //1.4
        else if (((!"".equals(aAttackUser)) && !aAttackUser.equals(b.getName())) && (!bAttackUser.equals(a.getName()))) {
            ret = noAttack_4(a, b);
            System.out.println("情况是1.4");
        }

        //2.1
        if ((!"".equals(aAttackUser)) && aAttackUser.equals(b.getName()) && "".equals(bAttackUser)) {
            ret = aAttackB_1(a, b);
            System.out.println("情况是2.1");
        }
        //2.2
        else if ((!"".equals(aAttackUser)) && aAttackUser.equals(b.getName()) && ((!"".equals(bAttackUser)) && !bAttackUser.equals(a.getName()))) {
            ret = aAttackB_2(a, b);
            System.out.println("情况是2.2");
        }
        //2.3
        else if ((!"".equals(bAttackUser)) && bAttackUser.equals(a.getName()) && "".equals(aAttackUser)) {
            ret = aAttackB_1(b, a);
            System.out.println("情况是2.3");
        }
        //2.4
        else if ((!"".equals(bAttackUser)) && bAttackUser.equals(a.getName()) && ((!"".equals(aAttackUser)) && !aAttackUser.equals(b.getName()))) {
            ret = aAttackB_2(b, a);
            System.out.println("情况是2.4");
        }

        //3.1
        if ((!"".equals(aAttackUser)) && aAttackUser.equals(b.getName()) && (!"".equals(bAttackUser)) && bAttackUser.equals(a.getName())) {
            ret = attackEachother(a, b);
            System.out.println("情况是3.1");
        }

        return ret;
    }


    //1.1、ab互不选择（a非攻击，b非攻击）
    public String noAttack_1(User a, User b) {
        String retmsg = "";
        String retmsgA = "";
        String retmsgB = "";
        String aAttackName = a.getAttackName();
        String bAttackName = b.getAttackName();

        if (hasNoQi(a) || a.isHasNoQi()) {
            a.setBlood(0);
            retmsgA = a.getName() + "被憋死!";
            if (hasNoQi(b) || b.isHasNoQi()) {
                b.setBlood(0);
                retmsgB = b.getName() + "被憋死!";
            }
            return retmsg + "," + retmsgA + "," + retmsgB;
        }

        //a是运
        else if ("yun".equals(aAttackName)) {
            if (!a.getIsCompare()) {
                a.setQi(a.getQi() + 1);
            }
            retmsgA = a.getName() + "加了一口气";
            if (hasNoQi(b)) {
                b.setBlood(0);
                retmsgB = b.getName() + "被憋死!";
            } else if ("yun".equals(bAttackName)) {
                if (!b.getIsCompare()) {
                    b.setQi(b.getQi() + 1);
                }
                retmsgB = b.getName() + "加了一口气";
            } else if ("dang0".equals(bAttackName)) {

            } else if ("dang1".equals(bAttackName)) {
                if (!b.getIsCompare()) {
                    b.setQi(b.getQi() - 1);
                }
            }
        }

        //a是挡
        else if ("dang0".equals(aAttackName)) {
            if (hasNoQi(b)) {
                b.setBlood(0);
                retmsgB = b.getName() + "被憋死!";
            } else if ("yun".equals(bAttackName)) {
                if (!b.getIsCompare()) {
                    b.setQi(b.getQi() + 1);
                }
                retmsgB = b.getName() + "加了一口气";
            } else if ("dang0".equals(bAttackName)) {

            } else if ("dang1".equals(bAttackName)) {
                if (!b.getIsCompare()) {
                    b.setQi(b.getQi() - 1);
                }
            }
        }

        //a是一气挡
        else if ("dang1".equals(aAttackName)) {
            if (!a.getIsCompare()) {
                a.setQi(a.getQi() - 1);
            }

            if (hasNoQi(b)) {
                b.setBlood(0);
                retmsgB = b.getName() + "被憋死!";
            } else if ("yun".equals(bAttackName)) {
                if (!b.getIsCompare()) {
                    b.setQi(b.getQi() + 1);
                }
                retmsgB = b.getName() + "加了一口气";
            } else if ("dang0".equals(bAttackName)) {

            } else if ("dang1".equals(bAttackName)) {
                if (!b.getIsCompare()) {
                    b.setQi(b.getQi() - 1);
                }
            }
        }

        a.setIsCompare(true);
        b.setIsCompare(true);

        return retmsg + "," + retmsgA + "," + retmsgB;
    }


    //1.2、ab互不选择（2、a非攻击，b攻击其他人）
    public String noAttack_2(User a, User b) {
        String retmsg = "";
        String retmsgA = "";
        String retmsgB = "";
        String aAttackName = a.getAttackName();
        String bAttackName = b.getAttackName();

        if (hasNoQi(a) || a.isHasNoQi()) {
            a.setBlood(0);
            retmsgA = a.getName() + "被憋死!";
            if (hasNoQi(b) || b.isHasNoQi()) {
                b.setBlood(0);
                retmsgB = b.getName() + "被憋死!";
            }
            return retmsg + "," + retmsgA + "," + retmsgB;
        }

        //a是运
        else if ("yun".equals(aAttackName)) {
            if (!a.getIsCompare()) {
                a.setQi(a.getQi() + 1);
            }
            retmsgA = a.getName() + "加了一口气";
            if (hasNoQi(b)) {
                b.setBlood(0);
                retmsgB = b.getName() + "被憋死!";
            } else if ("biao".equals(bAttackName)) {
                if (!b.getIsCompare()) {
                    b.setQi(b.getQi() - 1);
                }
            } else if ("adugen".equals(bAttackName)) {
                if (!b.getIsCompare()) {
                    b.setQi(b.getQi() - 2);
                }
            }
        }

        //a是挡
        else if ("dang0".equals(aAttackName)) {
            if (hasNoQi(b)) {
                b.setBlood(0);
                retmsgB = b.getName() + "被憋死!";
            } else if ("biao".equals(bAttackName)) {
                if (!b.getIsCompare()) {
                    b.setQi(b.getQi() - 1);
                }
            } else if ("adugen".equals(bAttackName)) {
                if (!b.getIsCompare()) {
                    b.setQi(b.getQi() - 2);
                }
            }
        }

        //a是一气挡
        else if ("dang1".equals(aAttackName)) {

            if (!a.getIsCompare()) {
                a.setQi(a.getQi() - 1);
            }

            if (hasNoQi(b)) {
                b.setBlood(0);
                retmsgB = b.getName() + "被憋死!";
            } else if ("biao".equals(bAttackName)) {
                if (!b.getIsCompare()) {
                    b.setQi(b.getQi() - 1);
                }
            } else if ("adugen".equals(bAttackName)) {
                if (!b.getIsCompare()) {
                    b.setQi(b.getQi() - 2);
                }
            }
        }

        a.setIsCompare(true);
        b.setIsCompare(true);

        return retmsg + "," + retmsgA + "," + retmsgB;
    }

    //1.4、ab互不选择（4、a攻击其他人，b攻击其他人）
    public String noAttack_4(User a, User b) {
        String retmsg = "";
        String retmsgA = "";
        String retmsgB = "";
        String aAttackName = a.getAttackName();
        String bAttackName = b.getAttackName();

        if (hasNoQi(a) || a.isHasNoQi()) {
            a.setBlood(0);
            retmsgA = a.getName() + "被憋死!";
            if (hasNoQi(b) || b.isHasNoQi()) {
                b.setBlood(0);
                retmsgB = b.getName() + "被憋死!";
            }
            return retmsg + "," + retmsgA + "," + retmsgB;
        }

        //a是镖
        else if ("biao".equals(aAttackName)) {
            if (!a.getIsCompare()) {
                a.setQi(a.getQi() - 1);
            }

            if (hasNoQi(b)) {
                b.setBlood(0);
                retmsgB = b.getName() + "被憋死!";
            } else if ("biao".equals(bAttackName)) {
                if (!b.getIsCompare()) {
                    b.setQi(b.getQi() - 1);
                }
            } else if ("adugen".equals(bAttackName)) {
                if (!b.getIsCompare()) {
                    b.setQi(a.getQi() - 2);
                }
            }
        }

        //a是阿杜跟
        else if ("adugen".equals(aAttackName)) {

            if (!a.getIsCompare()) {
                a.setQi(a.getQi() - 2);
            }

            if (hasNoQi(b)) {
                b.setBlood(0);
                retmsgB = b.getName() + "被憋死!";
            } else if ("biao".equals(bAttackName)) {
                if (!b.getIsCompare()) {
                    b.setQi(b.getQi() - 1);
                }
            } else if ("adugen".equals(bAttackName)) {
                if (!b.getIsCompare()) {
                    b.setQi(a.getQi() - 2);
                }
            }
        }

        a.setIsCompare(true);
        b.setIsCompare(true);

        return retmsg + "," + retmsgA + "," + retmsgB;
    }


    //2.1、a选择（攻击）b，b未选择a（b使用非攻击招式）
    public String aAttackB_1(User a, User b) {

        String retmsg = "";
        String retmsgA = "";
        String retmsgB = "";
        String aAttackName = a.getAttackName();
        String bAttackName = b.getAttackName();

        if (hasNoQi(a) || a.isHasNoQi()) {
            a.setBlood(0);
            retmsgA = a.getName() + "被憋死!";
            if (hasNoQi(b) || b.isHasNoQi()) {
                b.setBlood(0);
                retmsgB = b.getName() + "被憋死!";
            }
            return retmsg + "," + retmsgA + "," + retmsgB;
        }
        //a是镖
        else if ("biao".equals(aAttackName)) {

            if (!a.getIsCompare()) {
                a.setQi(a.getQi() - 1);
            }

            if (hasNoQi(b)) {
                b.setBlood(0);
                retmsgB = b.getName() + "被憋死!";
            } else if ("yun".equals(bAttackName)) {
                b.setBlood(b.getBlood() - 1);
                retmsg = a.getName() + "把" + b.getName() + "打死!";
                a.setQi(a.getQi()+1);
            } else if ("dang0".equals(bAttackName)) {
                retmsg = "什么都没发生";
            } else if ("dang1".equals(bAttackName)) {
                if (!b.getIsCompare()) {
                    b.setQi(b.getQi() - 1);
                }
                retmsg = "什么都没发生";
            }
        }

        //a是阿杜跟
        else if ("adugen".equals(aAttackName)) {

            if (!a.getIsCompare()) {
                a.setQi(a.getQi() - 2);
            }

            if (hasNoQi(b)) {
                b.setBlood(0);
                retmsgB = b.getName() + "被憋死!";
            } else if ("yun".equals(bAttackName)) {
                b.setBlood(b.getBlood() - 1);
                retmsg = a.getName() + "把" + b.getName() + "打死!";
                a.setQi(a.getQi()+1);
            } else if ("dang0".equals(bAttackName)) {
                b.setBlood(b.getBlood() - 1);
                retmsg = a.getName() + "把" + b.getName() + "打死!";
                a.setQi(a.getQi()+1);
            } else if ("dang1".equals(bAttackName)) {
                if (!b.getIsCompare()) {
                    b.setQi(b.getQi() - 1);
                }
                retmsg = "什么都没发生";
            }
        }

        a.setIsCompare(true);
        b.setIsCompare(true);

        return retmsg + retmsgA + retmsgB;
    }

    //2.2、a选择（攻击）b，b未选择a（b攻击其他人）
    public String aAttackB_2(User a, User b) {

        String retmsg = "";
        String retmsgA = "";
        String retmsgB = "";
        String aAttackName = a.getAttackName();
        String bAttackName = b.getAttackName();

        if (hasNoQi(a) || a.isHasNoQi()) {
            a.setBlood(0);
            retmsgA = a.getName() + "被憋死!";
            if (hasNoQi(b) || b.isHasNoQi()) {
                b.setBlood(0);
                retmsgB = b.getName() + "被憋死!";
            }
            return retmsg + "," + retmsgA + "," + retmsgB;
        }

        //a是镖
        else if ("biao".equals(aAttackName)) {

            if (!a.getIsCompare()) {
                a.setQi(a.getQi() - 1);
            }

            if (hasNoQi(b)) {
                b.setBlood(0);
                retmsgB = b.getName() + "被憋死!";
            } else if ("biao".equals(bAttackName)) {
                if (!b.getIsCompare()) {
                    b.setQi(b.getQi() - 1);
                }
                b.setBlood(b.getBlood() - 1);
                retmsg = a.getName() + "把" + b.getName() + "打死!";
                a.setQi(a.getQi()+1);
            } else if ("adugen".equals(bAttackName)) {
                if (!b.getIsCompare()) {
                    b.setQi(b.getQi() - 2);
                }
                b.setBlood(b.getBlood() - 1);
                retmsg = a.getName() + "把" + b.getName() + "打死!";
                a.setQi(a.getQi()+1);
            }
        }

        //a是阿杜跟
        else if ("adugen".equals(aAttackName)) {

            if (!a.getIsCompare()) {
                a.setQi(a.getQi() - 2);
            }

            if (hasNoQi(b)) {
                b.setBlood(0);
                retmsgB = b.getName() + "被憋死!";
            } else if ("biao".equals(bAttackName)) {
                if (!b.getIsCompare()) {
                    b.setQi(b.getQi() - 1);
                }
                retmsg = a.getName() + "把" + b.getName() + "打死!";
                a.setQi(a.getQi()+1);
            } else if ("adugen".equals(bAttackName)) {
                if (!b.getIsCompare()) {
                    b.setQi(b.getQi() - 2);
                }
                retmsg = a.getName() + "把" + b.getName() + "打死!";
                a.setQi(a.getQi()+1);
            }
        }

        a.setIsCompare(true);
        b.setIsCompare(true);

        return retmsg + retmsgA + retmsgB;
    }


    // yun dang0 dang1 biao adugen
    //3.1、ab互相选择（攻击）
    public String attackEachother(User a, User b) {

        String retmsg = "";
        String retmsgA = "";
        String retmsgB = "";
        String aAttackName = a.getAttackName();
        String bAttackName = b.getAttackName();

        if (hasNoQi(a) || a.isHasNoQi()) {
            a.setBlood(0);
            retmsgA = a.getName() + "被憋死!";
            if (hasNoQi(b) || b.isHasNoQi()) {
                b.setBlood(0);
                retmsgB = b.getName() + "被憋死!";
            }
            return retmsg + "," + retmsgA + "," + retmsgB;
        }

        //a是镖
        else if ("biao".equals(aAttackName)) {
            if (!a.getIsCompare()) {
                a.setQi(a.getQi() - 1);
            }

            if (hasNoQi(b)) {
                b.setBlood(0);
                retmsgB = b.getName() + "被憋死!";
            } else if ("biao".equals(bAttackName)) {
                if (!b.getIsCompare()) {
                    b.setQi(b.getQi() - 1);
                }
                retmsg = "什么都没发生";
            } else if ("adugen".equals(bAttackName)) {
                if (!b.getIsCompare()) {
                    b.setQi(a.getQi() - 2);
                }
                a.setBlood(a.getBlood() - 1);
                retmsg = b.getName() + "把" + a.getName() + "打死!";
                b.setQi(b.getQi()+1);
            }
        }

        //a是阿杜跟
        else if ("adugen".equals(aAttackName)) {

            if (!a.getIsCompare()) {
                a.setQi(a.getQi() - 2);
            }

            if (hasNoQi(b)) {
                b.setBlood(0);
                retmsgB = b.getName() + "被憋死!";
            } else if ("biao".equals(bAttackName)) {
                if (!b.getIsCompare()) {
                    b.setQi(b.getQi() - 1);
                }
                b.setBlood(b.getBlood() - 1);
                retmsg = a.getName() + "把" + b.getName() + "打死!";
                a.setQi(a.getQi()+1);
            } else if ("adugen".equals(bAttackName)) {
                if (!b.getIsCompare()) {
                    b.setQi(a.getQi() - 2);
                }
                retmsg = "什么都没发生";
            }
        }

        a.setIsCompare(true);
        b.setIsCompare(true);

        return retmsg + retmsgA + retmsgB;

    }


//    public boolean hasNoQi(User user){
//        if(!user.getIsCompare()){
//            if( ("biao".equals(user.getAttackName()) && user.getQi()<1)
//                    ||  ("adugen".equals(user.getAttackName()) && user.getQi()<2)
//                    ||  ("dang1".equals(user.getAttackName()) && user.getQi()<1)){
//                return true;
//            }
//            return false;
//        }else {
//            return false;
//        }
//    }

    //判断气够不够
    public boolean hasNoQi(User user) {
        if (!user.getIsCompare()) {
            if (("biao".equals(user.getAttackName()) && user.getQi() < 1)
                    || ("adugen".equals(user.getAttackName()) && user.getQi() < 2)
                    || ("dang1".equals(user.getAttackName()) && user.getQi() < 1)) {
                user.setHasNoQi(true);
                return true;
            }
            return false;
        } else {
            return false;
        }
    }
}