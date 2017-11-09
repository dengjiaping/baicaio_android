package com.cnxxp.cabbagenet.config;

import com.cnxxp.cabbagenet.bean.AttentionBean;
import com.cnxxp.cabbagenet.bean.UserInfo;

import java.util.HashMap;

/**
 * Created by Administrator on 2017/3/13.
 */

public class Config {
    /**
     * 网络路径的公用参数
     */
    public static class HTTP_UTL {
        public static final String URL = "http://test.baicaio.com/";
        //public static final String URL = "http://baicai.ccyou.cc/";
        public static final String USER = URL + "?g=api&m=user&a=init";//用户模块
        public static final String SHOP = URL + "?g=api&m=shop&a=init";//购物模块
        public static final String ARTICLE = URL + "?g=api&m=article&a=init";//文章模块
        public static final String QUAN = URL + "?g=api&m=quan&a=init";//文章模块
        public static final String VERSIONURL = "http://121.199.25.219:8588/qmjxs/p/interface/version/api";

    }

    /**
     * 项目中用到的公用参数
     */
    public static class PublicParams {
        public static final String from = "android";//固定值  分辨是非为android 或ios
        public static final String appkey = "xfQE6dGsc64gtIAtbp8CCtThbFgGHd35jQWDse4lSgch35d9Fd3dscxgh85dOM0355dOM035";//固定值
        public static String mSign = ""; // 接口返回的
        public static String version = "1.0";//版本号
        public static String usid = "";
        public static String uname = "";
        public static String portrait = "";
        public static boolean hasChange = false;
        public static String mobile = "";
        public static String signature = "0";
        public static UserInfo userInfo = null;
        public static HashMap<String, AttentionBean> u_attention;
    }
}
