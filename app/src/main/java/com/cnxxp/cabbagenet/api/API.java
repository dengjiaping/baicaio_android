package com.cnxxp.cabbagenet.api;

import com.cnxxp.cabbagenet.config.Config;
import com.cnxxp.cabbagenet.httputils.VolleyHttpRequest;
import com.cnxxp.cabbagenet.httputils.VolleyInterface;

import org.json.JSONException;
import org.json.JSONObject;

public class API {
    private volatile static API api;


    private API() {
    }

    public static API getSingleton() {
        if (api == null) {
            synchronized (API.class) {
                if (api == null) {
                    api = new API();
                }
            }
        }
        return api;
    }

    /********************************
     * 用户模块
     ******************************/

    public void userAPI(String tag, JSONObject jsonObject,
                        VolleyInterface volleyInterface) {
        try {
            VolleyHttpRequest.getSingleton().RequstJsonPost(tag,
                    Config.HTTP_UTL.USER, jsonObject, volleyInterface);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //注册
    public void register(String tag, String username, String password, VolleyInterface volleyInterface) {
        JSONObject json = new JSONObject();
        try {
            json.put("api", "register");
            json.put("username", username);
            json.put("password", password);

            userAPI(tag, json, volleyInterface);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //验证码
    public void smscode(String tag, String mobile, String type, VolleyInterface volleyInterface) {
        JSONObject json = new JSONObject();
        try {
            json.put("type", type);
            json.put("api", "smscode");
            json.put("mobile", mobile);
            userAPI(tag, json, volleyInterface);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //登录
    public void login(String tag, String mobile, String password, VolleyInterface volleyInterface) {
        JSONObject json = new JSONObject();
        try {
            json.put("api", "mobilelogin");
            json.put("mobile", mobile);
            json.put("password", password);
            userAPI(tag, json, volleyInterface);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    //绑定手机
    public void bindMobile(String tag, String phone, String smsCode, String userid, VolleyInterface volleyInterface) {
        JSONObject json = new JSONObject();
        try {
            json.put("api", "bindinfo");
            json.put("mobile", phone);
            json.put("userid", userid);
            json.put("captcha", smsCode);
            userAPI(tag, json, volleyInterface);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //绑定邮箱
    public void bindEmail(String tag, String email, String smsCode, String userid, VolleyInterface volleyInterface) {
        JSONObject json = new JSONObject();
        try {
            json.put("api", "bindinfo");
            json.put("email", email);
            json.put("userid", userid);
            json.put("captcha", smsCode);
            userAPI(tag, json, volleyInterface);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //修改密码
    public void resetPassword(String tag, String password, String uid, String newpassword, VolleyInterface volleyInterface) {
        JSONObject json = new JSONObject();
        try {
            json.put("api", "resetpassword");
            json.put("password", password);
            json.put("userid", uid);
            json.put("new_password", newpassword);
            userAPI(tag, json, volleyInterface);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //手机找回密码
    public void phoneFindPwd(String tag, String phone, String smscode, String password, VolleyInterface volleyInterface) {
        JSONObject json = new JSONObject();
        try {
            json.put("api", "forgetpassword");
            json.put("password", password);
            json.put("mobile", phone);
            json.put("captcha", smscode);
            userAPI(tag, json, volleyInterface);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //邮箱/用户名找回
    public void OtherFindPwd(String tag, String username, VolleyInterface volleyInterface) {
        JSONObject json = new JSONObject();
        try {
            json.put("api", "findpwd");
            json.put("username", username);
            userAPI(tag, json, volleyInterface);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //签到
    public void sign(String tag, String userid, VolleyInterface volleyInterface) {
        JSONObject json = new JSONObject();
        try {
            json.put("api", "sign");
            json.put("userid", userid);
            userAPI(tag, json, volleyInterface);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //获取用户信息
    public void getUserInfo(String tag, String userid, VolleyInterface volleyInterface) {
        JSONObject json = new JSONObject();
        try {
            json.put("api", "getuserinfo");
            json.put("userid", userid);
            userAPI(tag, json, volleyInterface);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //修改性别
    public void updateSex(String tag, String userid, String gender, VolleyInterface volleyInterface) {
        JSONObject json = new JSONObject();
        try {
            json.put("api", "updatesex");
            json.put("userid", userid);
            json.put("gender", gender);
            userAPI(tag, json, volleyInterface);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //修改头像
    public void setImg(String tag, String userid, String image, VolleyInterface volleyInterface) {
        JSONObject json = new JSONObject();
        try {
            json.put("api", "setimg");
            json.put("userid", userid);
            json.put("base64", image);
            userAPI(tag, json, volleyInterface);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //查询收货地址
    public void getAddress(String tag, String userid, VolleyInterface volleyInterface) {
        JSONObject json = new JSONObject();
        try {
            json.put("api", "getaddress");
            json.put("userid", userid);
            userAPI(tag, json, volleyInterface);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 添加收货地址
     *
     * @param tag
     * @param consignee       收件人
     * @param zip             邮编
     * @param mobile          手机号码
     * @param address         收货地址
     * @param volleyInterface
     */
    public void addAddress(String tag, String userid, String consignee, String zip, String mobile, String address, VolleyInterface volleyInterface) {
        JSONObject json = new JSONObject();
        try {
            json.put("api", "setaddress");
            json.put("userid", userid);
            json.put("type", "add");
            json.put("addressid", "0");
            json.put("consignee", consignee);
            json.put("zip", zip);
            json.put("mobile", mobile);
            json.put("address", address);
            userAPI(tag, json, volleyInterface);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //删除收货地址
    public void deleteAddress(String tag, String userid, String addressid, VolleyInterface volleyInterface) {
        JSONObject json = new JSONObject();
        try {
            json.put("api", "setaddress");
            json.put("userid", userid);
            json.put("type", "del");
            json.put("addressid", addressid);
            json.put("consignee", "");
            json.put("zip", "");
            json.put("mobile", "");
            json.put("address", "");
            userAPI(tag, json, volleyInterface);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 修改收货地址
     *
     * @param tag
     * @param addressid       地址id
     * @param consignee       收货人姓名
     * @param zip             邮编
     * @param mobile          手机号
     * @param address         收货地址
     * @param volleyInterface
     */
    public void editAddress(String tag, String userid, String addressid, String consignee, String zip, String mobile, String address, VolleyInterface volleyInterface) {
        JSONObject json = new JSONObject();
        try {
            json.put("api", "setaddress");
            json.put("userid", userid);
            json.put("type", "edit");
            json.put("addressid", addressid);
            json.put("consignee", consignee);
            json.put("zip", zip);
            json.put("mobile", mobile);
            json.put("address", address);
            userAPI(tag, json, volleyInterface);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //我的财富
    public void myGrade(String tag, int page, String type, String userid, VolleyInterface volleyInterface) {
        JSONObject json = new JSONObject();
        try {
            json.put("api", "grade");
            json.put("page", page);
            json.put("type", type);
            json.put("userid", userid);
            userAPI(tag, json, volleyInterface);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    //我的文章
    public void myArticle(String tag, String type, int page, String userid, VolleyInterface volleyInterface) {
        JSONObject json = new JSONObject();
        try {
            json.put("api", "new_publish");
            json.put("page", page);
            json.put("type", type);
            json.put("userid", userid);
            userAPI(tag, json, volleyInterface);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 积分兑换列表
     *
     * @param tag
     * @param cid             类型  ，0实物，1虚拟   全部为空
     * @param page
     * @param volleyInterface
     */
    public void scoreList(String tag, String cid, String page, String title, VolleyInterface volleyInterface) {
        JSONObject json = new JSONObject();
        try {
            json.put("api", "scorelist");
            json.put("type", cid);
            json.put("page", page);
            json.put("title", title);
            userAPI(tag, json, volleyInterface);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //积分兑换详情
    public void scoreChangeDetail(String tag, String scoreid, VolleyInterface volleyInterface) {
        JSONObject json = new JSONObject();
        try {
            json.put("api", "scoredetails");
            json.put("scoreid", scoreid);
            userAPI(tag, json, volleyInterface);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //积分兑换的商品
    public void myScore(String tag, String userid, String page, VolleyInterface volleyInterface) {
        JSONObject json = new JSONObject();
        try {
            json.put("api", "myscore");
            json.put("userid", userid);
            json.put("page", page);
            userAPI(tag, json, volleyInterface);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 发表评论
     *
     * @param tag
     * @param info            评论内容
     * @param xid             1商品评论，2转让评论，3文章评论
     * @param id              被评论的商品、转让、文章的id
     * @param volleyInterface
     */
    public void releaseComment(String tag, String info, String xid, String id, String userid, VolleyInterface volleyInterface) {
        JSONObject json = new JSONObject();
        try {
            json.put("api", "comment");
            json.put("info", info);
            json.put("xid", xid);
            json.put("id", id);
            json.put("userid", userid);
            userAPI(tag, json, volleyInterface);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //回复评论
    public void hfComment(String tag, String commentid, String content, String userid, VolleyInterface volleyInterface) {
        JSONObject json = new JSONObject();
        try {
            json.put("api", "hf");
            json.put("commentid", commentid);
            json.put("content", content);
            json.put("userid", userid);
            userAPI(tag, json, volleyInterface);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 收藏&&取消收藏
     *
     * @param tag
     * @param id              商品或文章id。根据xid不同传不同的id
     * @param xid             1为商品 2为转让 3为文章
     * @param volleyInterface
     */
    public void setLikes(String tag, String id, String xid, String userid, VolleyInterface volleyInterface) {
        JSONObject json = new JSONObject();
        try {
            json.put("api", "setlikes");
            json.put("xid", xid);
            json.put("id", id);
            json.put("userid", userid);
            userAPI(tag, json, volleyInterface);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //删除评论
    public void deleteComment(String tag, String commentid, VolleyInterface volleyInterface) {
        JSONObject json = new JSONObject();
        try {
            json.put("api", "del_comment");
            json.put("commentid", commentid);
            userAPI(tag, json, volleyInterface);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 我的评论
     *
     * @param tag
     * @param t               是否为近期评论 0 全部 1近期
     * @param page
     * @param volleyInterface
     */
    public void myComments(String tag, String t, String page, String userid, VolleyInterface volleyInterface) {
        JSONObject json = new JSONObject();
        try {
            json.put("api", "comments");
            json.put("t", t);
            json.put("page", page);
            json.put("userid", userid);
            userAPI(tag, json, volleyInterface);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //获取头像
    public void getImage(String tag, String userid, VolleyInterface volleyInterface) {
        JSONObject json = new JSONObject();
        try {
            json.put("api", "getimg");
            json.put("userid", userid);
            userAPI(tag, json, volleyInterface);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //系统消息
    public void getMsg(String tag, String page, String userid, VolleyInterface volleyInterface) {
        JSONObject json = new JSONObject();
        try {
            json.put("api", "getmsg");
            json.put("page", page);
            json.put("userid", userid);
            userAPI(tag, json, volleyInterface);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //删除系统消息
    public void delMsg(String tag, String id, String userid, VolleyInterface volleyInterface) {
        JSONObject json = new JSONObject();
        try {
            json.put("api", "msgdel");
            json.put("mid", id);
            json.put("userid", userid);
            userAPI(tag, json, volleyInterface);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //私信列表
    public void privateLetter(String tag, String userid, String page, VolleyInterface volleyInterface) {
        JSONObject json = new JSONObject();
        try {
            json.put("api", "msglist");
            json.put("page", page);
            json.put("userid", userid);
            userAPI(tag, json, volleyInterface);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //删除一条私信会话
    public void deletePrivateletter(String tag, String userid, String ftid, VolleyInterface volleyInterface) {
        JSONObject json = new JSONObject();
        try {
            json.put("api", "msgdelall");
            json.put("ftid", ftid);
            json.put("userid", userid);
            userAPI(tag, json, volleyInterface);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //私信详情
    public void letterList(String tag, String userid, String ftid, String page, VolleyInterface volleyInterface) {
        JSONObject json = new JSONObject();
        try {
            json.put("api", "getmsg_userdetail");
            json.put("page", page);
            json.put("userid", userid);
            json.put("ftid", ftid);
            userAPI(tag, json, volleyInterface);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //回复私信
    public void msgPublish(String tag, String userid, String to_id, String content, VolleyInterface volleyInterface) {
        JSONObject json = new JSONObject();
        try {
            json.put("api", "msgpublish");
            json.put("userid", userid);
            json.put("to_id", to_id);
            json.put("content", content);
            userAPI(tag, json, volleyInterface);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    //删除私信内容条目
    public void deleleMsg(String tag, String userid, String msgid, VolleyInterface volleyInterface) {
        JSONObject json = new JSONObject();
        try {
            json.put("api", "msgdelitme");
            json.put("userid", userid);
            json.put("msgid", msgid);
            userAPI(tag, json, volleyInterface);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 我的收藏
     *
     * @param tag
     * @param type            类型 gn国内爆料，ht海淘爆料，best精品汇，sd晒单，gl攻略，zr转让
     * @param page
     * @param volleyInterface
     */
    public void myCollection(String tag, String userid, String type, String page, VolleyInterface volleyInterface) {
        JSONObject json = new JSONObject();
        try {
            json.put("api", "likes");
            json.put("userid", userid);
            json.put("type", type);
            json.put("page", page);
            userAPI(tag, json, volleyInterface);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //我的文章爆料晒单等
    public void publish(String tag, String type, String userid, String page, VolleyInterface volleyInterface) {
        JSONObject json = new JSONObject();
        try {
            json.put("api", "publish");
            json.put("type", type);
            json.put("userid", userid);
            json.put("page", page);
            userAPI(tag, json, volleyInterface);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void reportBug(String tag, String content, String userid, VolleyInterface volleyInterface) {
        JSONObject json = new JSONObject();
        try {
            json.put("api", "report_bug");
            json.put("to_id", "192792");
            json.put("userid", userid);
            json.put("content", content);
            userAPI(tag, json, volleyInterface);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //点赞
    public void zan(String tag, String id, String type, VolleyInterface volleyInterface) {
        JSONObject json = new JSONObject();
        try {
            json.put("api", "zan");
            json.put("id", id);
            json.put("type", type);
            userAPI(tag, json, volleyInterface);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //分享成功调用
    public void share(String tag, String userid, VolleyInterface volleyInterface) {
        JSONObject json = new JSONObject();
        try {
            json.put("api", "share");
            json.put("userid", userid);
            userAPI(tag, json, volleyInterface);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //兑换商品
    public void ec(String tag, String userid, String scoreid, String number, String consignee, String address, String zip
            , String mobile, VolleyInterface volleyInterface) {
        JSONObject json = new JSONObject();
        try {
            json.put("api", "ec");
            json.put("userid", userid);
            json.put("scoreid", scoreid);
            json.put("num", number);
            json.put("consignee", consignee);
            json.put("address", address);
            json.put("zip", zip);
            json.put("mobile", mobile);
            userAPI(tag, json, volleyInterface);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //第三方登录
    // 如果登录成功返回格式和正常登录一样，如果是新用户没有绑定平台则返回
    public void checkBind(String tag, String type, String keyid, VolleyInterface volleyInterface) {
        JSONObject json = new JSONObject();
        try {
            json.put("api", "checkbind");
            json.put("type", type);
            json.put("keyid", keyid);
            userAPI(tag, json, volleyInterface);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void bind(String tag, String userid, String type, String keyid, String token, VolleyInterface volleyInterface) {
        JSONObject json = new JSONObject();
        try {
            json.put("api", "bind");
            json.put("type", type);
            json.put("keyid", keyid);
            json.put("userid", userid);
            json.put("info", token);
            userAPI(tag, json, volleyInterface);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //完善用户信息
    public void registerOpen(String tag, String username, String password, String email, String gender, VolleyInterface volleyInterface) {
        JSONObject json = new JSONObject();
        try {
            json.put("api", "register_open");
            json.put("username", username);
            json.put("password", password);
            json.put("email", email);
            json.put("gender", gender);
            userAPI(tag, json, volleyInterface);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //我的优惠券
    public void tick(String tag, String userid, int page, String gq, VolleyInterface volleyInterface) {
        JSONObject json = new JSONObject();
        try {
            json.put("api", "tick");
            json.put("userid", userid);
            json.put("page", page);
            json.put("gq", gq);
            userAPI(tag, json, volleyInterface);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //优惠券兑换
    public void tickDh(String tag, String userid, String tickid, VolleyInterface volleyInterface
    ) {
        JSONObject json = new JSONObject();
        try {
            json.put("api", "tkdh");
            json.put("userid", userid);
            json.put("tickid", tickid);
            userAPI(tag, json, volleyInterface);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //优惠券列表
    public void tickList(String tag, int page, VolleyInterface volleyInterface
    ) {
        JSONObject json = new JSONObject();
        try {
            json.put("api", "ticklist");
            json.put("page", page);
            userAPI(tag, json, volleyInterface);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //获取绑定
    public void getBind(String tag, String userid, VolleyInterface volleyInterface) {
        JSONObject json = new JSONObject();
        try {
            json.put("api", "getbind");
            json.put("userid", userid);
            userAPI(tag, json, volleyInterface);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //解除绑定
    public void removeBind(String tag, String type, String usid, VolleyInterface volleyInterface) {
        JSONObject json = new JSONObject();
        try {
            json.put("api", "removebind");
            json.put("userid", usid);
            json.put("type", type);
            userAPI(tag, json, volleyInterface);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    //推送tag相关接口
    //获取用户所有推送tag
    public void notifyTagByUser(String tag, String userid, VolleyInterface volleyInterface) {
        JSONObject json = new JSONObject();
        try {
            json.put("api", "notify_tag_byuser");
            json.put("userid", userid);
            userAPI(tag, json, volleyInterface);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //创建推送tag
    public void notifyTagCreate(String tag, String push_tag, String userid, VolleyInterface volleyInterface) {
        JSONObject json = new JSONObject();
        try {
            json.put("api", "notify_tag_create");
            json.put("userid", userid);
            json.put("tag", push_tag);
            userAPI(tag, json, volleyInterface);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //修改推送tag
    public void notifyTagModify(String tag, String tag_id, String push_tag, VolleyInterface volleyInterface) {
        JSONObject json = new JSONObject();
        try {
            json.put("api", "notify_tag_modify");
            json.put("id", tag_id);
            json.put("tag", push_tag);
            userAPI(tag, json, volleyInterface);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //删除推送
    public void notifyTagDel(String TAG, String id, String userid, VolleyInterface volleyInterface) {
        JSONObject json = new JSONObject();
        try {
            json.put("api", "notify_tag_del");
            json.put("userid", userid);
            json.put("id", id);
            userAPI(TAG, json, volleyInterface);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //创建关注
    public void followTagCreate(String TAG, String tag, String userid, VolleyInterface volleyInterface) {
        JSONObject json = new JSONObject();
        try {
            json.put("api", "follow_tag_create");
            json.put("userid", userid);
            json.put("tag", tag);
            userAPI(TAG, json, volleyInterface);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //取消关注 
    public void followTagDel(String TAG, String tag, String userid, VolleyInterface volleyInterface) {
        JSONObject json = new JSONObject();
        try {
            json.put("api", "follow_tag_del");
            json.put("userid", userid);
            json.put("tag", tag);
            userAPI(TAG, json, volleyInterface);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    /**
     * 当前抽奖商品
     *"api":"lucky_list","userid":"192792","pagesize":2,"page":1
     * @param
     * @param
     * @param volleyInterface
     */
    public void luck_list(String TAG,String userid,int pagesize,int page,VolleyInterface volleyInterface){
        JSONObject json = new JSONObject();
        try {
            json.put("api", "lucky_list");
            json.put("userid", userid);
            json.put("pagesize", pagesize);
            json.put("page", page);
            userAPI(TAG, json, volleyInterface);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 往期抽奖商品
     *"api":"lucky_list_past","userid":"192792","pagesize":4,"page":1
     * @param
     * @param
     * @param volleyInterface
     */
    public void lucky_list_past(String TAG,String userid,int pagesize,int page,VolleyInterface volleyInterface){
        JSONObject json = new JSONObject();
        try {
            json.put("api", "lucky_list_past");
            json.put("userid", userid);
            json.put("pagesize", pagesize);
            json.put("page", page);
            userAPI(TAG, json, volleyInterface);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 我的抽奖
     *"api":"myluckys","userid":"192792","pagesize":4,"page":1
     * @param
     * @param
     * @param volleyInterface
     */
    public void myluckys(String TAG,String userid,int pagesize,int page,VolleyInterface volleyInterface){
        JSONObject json = new JSONObject();
        try {
            json.put("api", "myluckys");
            json.put("userid", userid);
            json.put("pagesize", pagesize);
            json.put("page", page);
            userAPI(TAG, json, volleyInterface);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /*********************************
     * 购物模块
     *******************************/

    public void shopAPI(String tag, JSONObject jsonObject,
                        VolleyInterface volleyInterface) {
        try {
            VolleyHttpRequest.getSingleton().RequstJsonPost(tag,
                    Config.HTTP_UTL.SHOP, jsonObject, volleyInterface);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    /**
     * @param tag
     * @param tp              0国内 1国外 2不限制
     * @param cid             筛选分类ID，可以为空，不为空则tp需要设置为2
     * @param page
     * @param pagesize
     * @param key             模糊搜索标题
     * @param volleyInterface
     */
    public void shopList(String tag, String tp, String cid, String origid, String page, String pagesize, String key, String isbao, VolleyInterface volleyInterface) {
        JSONObject json = new JSONObject();
        try {
            json.put("api", "shoplist");
            json.put("tp", tp);
            json.put("page", page);
            json.put("cid", cid);
            json.put("pagesize", pagesize);
            json.put("key", key);
            json.put("isbao", isbao);
            json.put("orig_id", origid);
            shopAPI(tag, json, volleyInterface);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param tag
     * @param page
     * @param cid             筛选id  可以为空
     * @param key             模糊搜索  可以为空
     * @param volleyInterface
     */
    public void tagsearch(String tag, int page, String cid, String key, String tagvalue, VolleyInterface volleyInterface) {
        JSONObject json = new JSONObject();
        try {
            json.put("api", "tagsearch");
            json.put("tag", tagvalue);
            json.put("page", page);
            json.put("cid", cid);
            json.put("key", key);
            shopAPI(tag, json, volleyInterface);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 商品详情
     *
     * @param tag
     * @param shopid          商品id
     * @param volleyInterface
     */
    public void shopItem(String tag, String shopid, String uid,String type, VolleyInterface volleyInterface) {
        JSONObject json = new JSONObject();
        try {
            json.put("api", "shopitem");
            json.put("shopid", shopid);
            json.put("userid", uid);
            json.put("type",type);
            shopAPI(tag, json, volleyInterface);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void commentItem(String tag, String shopid, String page, String xid, VolleyInterface volleyInterface) {
        JSONObject json = new JSONObject();
        try {
            json.put("api", "commentitem");
            json.put("shopid", shopid);
            json.put("page", page);
            json.put("xid", xid);
            shopAPI(tag, json, volleyInterface);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //获取url信息
    public void fetchItem(String tag, String url, VolleyInterface volleyInterface) {
        JSONObject json = new JSONObject();
        try {
            json.put("api", "fetch_item");
            json.put("url", url);
            shopAPI(tag, json, volleyInterface);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public void uploadImage1(String tag, String base64, VolleyInterface volleyInterface) {
        JSONObject json = new JSONObject();
        try {
            json.put("api", "uploadimg1");
            json.put("base64", base64);
            shopAPI(tag, json, volleyInterface);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    //发布爆料
    public void publishItem(String tag, String userid, String title, String content, String url, String price, String imgs, String img, VolleyInterface volleyInterface) {
        JSONObject json = new JSONObject();
        try {
            json.put("api", "publish_item");
            json.put("userid", userid);
            json.put("title", title);
            json.put("content", content);
            json.put("url", url);
            json.put("price", price);
            json.put("imgs", imgs);
            json.put("img", img);
            shopAPI(tag, json, volleyInterface);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    /**
     * 小时榜和天榜
     *
     * @param tag
     * @param type            类型  0为一天   1为1小时
     * @param volleyInterface
     */
    public void hourDay(String tag, String type, VolleyInterface volleyInterface) {
        JSONObject json = new JSONObject();
        try {
            json.put("api", "hourday");
            json.put("type", type);
            shopAPI(tag, json, volleyInterface);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //商品详情广告位
    public void Ad(String tag, String adid, VolleyInterface volleyInterface) {
        JSONObject json = new JSONObject();
        try {
            json.put("api", "ad");
            json.put("adid", adid);
            shopAPI(tag, json, volleyInterface);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //首页banner
    public void adB(String tag, String adid, VolleyInterface volleyInterface) {
        JSONObject json = new JSONObject();
        try {
            json.put("api", "adb");
            json.put("adid", adid);
            shopAPI(tag, json, volleyInterface);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void shopInfo(String tag, VolleyInterface volleyInterface) {
        JSONObject json = new JSONObject();
        try {
            json.put("api", "getorig");
            shopAPI(tag, json, volleyInterface);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //关注汇总
    public void shopList_g(String tag, String page, String key, VolleyInterface volleyInterface) {
        JSONObject json = new JSONObject();
        try {
            json.put("api", "shoplist_g");
            json.put("page", page);
            json.put("key", key);
            shopAPI(tag, json, volleyInterface);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /***********************
     * 文章模块
     *****************************************/
    public void articleAPI(String tag, JSONObject jsonObject,
                           VolleyInterface volleyInterface) {
        try {
            VolleyHttpRequest.getSingleton().RequstJsonPost(tag,
                    Config.HTTP_UTL.ARTICLE, jsonObject, volleyInterface);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    /**
     * 原创文章
     *
     * @param tag
     * @param isbest          是否为精华  0否 1是
     * @param cate_id         类型 0全部  9攻略  10晒单
     * @param page
     * @param key             可以为空  模糊搜索
     * @param volleyInterface
     */
    public void article(String tag, String isbest, String cate_id, String page, String key, VolleyInterface volleyInterface) {
        JSONObject json = new JSONObject();
        try {
            json.put("api", "article");
            json.put("isbest", isbest);
            json.put("cate_id", cate_id);
            json.put("page", page);
            json.put("key", key);
            articleAPI(tag, json, volleyInterface);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 文章详情
     *
     * @param tag
     * @param articleid
     * @param volleyInterface
     */
    public void articleItem(String tag, String articleid, String userid, VolleyInterface volleyInterface) {
        JSONObject json = new JSONObject();
        try {
            json.put("api", "articleitem");
            json.put("articleid", articleid);
            json.put("userid", userid);
            articleAPI(tag, json, volleyInterface);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    /************
     * 版本更新
     ******************/
    public void versionAPI(String tag, JSONObject jsonObject,
                           VolleyInterface volleyInterface) {
        try {
            VolleyHttpRequest.getSingleton().RequstJsonPost(tag,
                    Config.HTTP_UTL.VERSIONURL, jsonObject, volleyInterface);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void VersionUpData(String tag, VolleyInterface volleyInterface) {
        JSONObject json = new JSONObject();
        try {
            json.put("api", "version");
            json.put("vid", "baicaio");
            versionAPI(tag, json, volleyInterface);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    /***********************
     * 券模块
     *****************************************/
    public void quanAPI(String tag, JSONObject jsonObject,
                           VolleyInterface volleyInterface) {
        try {
            VolleyHttpRequest.getSingleton().RequstJsonPost(tag,
                    Config.HTTP_UTL.QUAN, jsonObject, volleyInterface);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void keywords(String tag, VolleyInterface volleyInterface) {
        JSONObject json = new JSONObject();
        try {
            json.put("api", "keywords");
            quanAPI(tag, json, volleyInterface);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    //"api":"search_quan","q":"可乐"
    public void search_quan(String tag,String words,String type, VolleyInterface volleyInterface) {
        JSONObject json = new JSONObject();
        try {
            json.put("api", "search_quan");
            json.put("q", words);
            json.put("s", type);
            quanAPI(tag, json, volleyInterface);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
