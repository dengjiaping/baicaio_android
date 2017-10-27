package com.cnxxp.cabbagenet.bean;

/**
 * Created by admin on 2017/4/14 0014.
 */

public class ArticleDetailBean {

    /**
     * title : 海淘晒单：TUMI 偏光太阳镜
     * author : 4799843
     * img : http://baicaiopic.b0.upaiyun.com/data/upload/article/1604/06/57049127a5743.jpg
     * intro :
     * 4月16日看了白菜哦woot特价TUMI太阳镜 下单,今天收到 正好woot又有了TUMI的专场.所以赶在还没结束的情况下 随便拍几张,仅供参考.
     * <p>
     * <p>
     * 整体重量偏轻,镜片稍偏暗,比原先购入的PILKINGTON要暗一些 可以从图中看出 ,图中还有29.9买的gunnar,一块发了吧.鼻梁无负重感 比较舒适.写的made in japan ,不知道是只有镜腿还是镜片也是
     * <p>
     * <p>
     * <p>
     * <p>
     * <p>
     * <p>
     * <p>
     * <p>
     * ...
     * info : <p style="letter-spacing:1px;color:#555555;font-family:'Microsoft yahei', PMingLiU, Verdana, Arial, Helvetica, sans-serif;font-size:14px;line-height:25.2px;white-space:normal;background-color:#FFFFFF;">
     * 4月16日看了白菜哦woot特价TUMI太阳镜 下单,今天收到 正好woot又有了TUMI的专场.所以赶在还没结束的情况下 随便拍几张,仅供参考.
     * </p>
     * <p style="letter-spacing:1px;color:#555555;font-family:'Microsoft yahei', PMingLiU, Verdana, Arial, Helvetica, sans-serif;font-size:14px;line-height:25.2px;white-space:normal;background-color:#FFFFFF;">
     * 整体重量偏轻,镜片稍偏暗,比原先购入的PILKINGTON要暗一些 可以从图中看出 ,图中还有29.9买的gunnar,一块发了吧.鼻梁无负重感 比较舒适.写的made in japan ,不知道是只有镜腿还是镜片也是
     * </p>
     * <p style="letter-spacing:1px;color:#555555;font-family:'Microsoft yahei', PMingLiU, Verdana, Arial, Helvetica, sans-serif;font-size:14px;line-height:25.2px;white-space:normal;background-color:#FFFFFF;">
     * <img src="http://baicaiopic.b0.upaiyun.com/data/upload/editer/image/2016/04/06/5704920e5a9a1.jpg" alt="" /><br />
     * <img src="http://baicaiopic.b0.upaiyun.com/data/upload/editer/image/2016/04/06/57049210ef643.jpg" alt="" /><br />
     * <img src="http://baicaiopic.b0.upaiyun.com/data/upload/editer/image/2016/04/06/570492193485c.jpg" alt="" /><br />
     * </p>
     * <p style="letter-spacing:1px;color:#555555;font-family:'Microsoft yahei', PMingLiU, Verdana, Arial, Helvetica, sans-serif;font-size:14px;line-height:25.2px;white-space:normal;background-color:#FFFFFF;">
     * <br />
     * </p>
     * likes : 0
     * zan : 0
     * comments : 1
     * add_time : 1459917680
     */

    private String title;  //标题
    private String author;  //作者
    private String img;   //商品图片
    private String intro;   //简介    
    private String info;   //详情
    private String likes;   //喜欢数
    private String zan;   //点赞数
    private String comments;   //评论数
    private String add_time;   //添加时间
    private int mylike;
    private String fenxiang;

    public String getFenxiang() {
        return fenxiang;
    }

    public void setFenxiang(String fenxiang) {
        this.fenxiang = fenxiang;
    }

    public int getMylike() {
        return mylike;
    }

    public void setMylike(int mylike) {
        this.mylike = mylike;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getLikes() {
        return likes;
    }

    public void setLikes(String likes) {
        this.likes = likes;
    }

    public String getZan() {
        return zan;
    }

    public void setZan(String zan) {
        this.zan = zan;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }
}
