package com.cnxxp.cabbagenet.bean;

import java.util.List;

/**
 * Created by admin on 2017/4/6 0006.
 */

public class myGradeBean {

    /**
     * grade : 2
     * list : [{"action":"sign","add_time":"1499741981","score":"8"},{"action":"share","add_time":"1499585222","score":"10"},{"action":"share","add_time":"1499568779","score":"10"},{"action":"sign","add_time":"1499567454","score":"10"},{"action":"sign","add_time":"1499409927","score":"9"},{"action":"sign","add_time":"1499259019","score":"8"},{"action":"sign","add_time":"1498819021","score":"9"},{"action":"sign","add_time":"1498706877","score":"8"},{"action":"sign","add_time":"1498447085","score":"8"},{"action":"sign","add_time":"1498028152","score":"8"}]
     * num : 161
     */

    private String grade;
    private String num;
    private List<ListBean> list;

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * action : sign
         * add_time : 1499741981
         * score : 8
         */

        private String action;
        private String add_time;
        private String score;
        private String coin;
        private String offer;
        private String exp;

        public String getCoin() {
            return coin;
        }

        public void setCoin(String coin) {
            this.coin = coin;
        }

        public String getExp() {
            return exp;
        }

        public void setExp(String exp) {
            this.exp = exp;
        }

        public String getOffer() {
            return offer;
        }

        public void setOffer(String offer) {
            this.offer = offer;
        }

        public String getAction() {
            return action;
        }

        public void setAction(String action) {
            this.action = action;
        }

        public String getAdd_time() {
            return add_time;
        }

        public void setAdd_time(String add_time) {
            this.add_time = add_time;
        }

        public String getScore() {
            return score;
        }

        public void setScore(String score) {
            this.score = score;
        }
    }
}
