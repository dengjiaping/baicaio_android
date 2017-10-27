package com.cnxxp.cabbagenet.utils;

import com.cnxxp.cabbagenet.R;
import com.cnxxp.cabbagenet.bean.Emoji;

import java.util.ArrayList;

/**
 * Created by admin on 2017/8/30 0030.
 */

public class EmojiDataBaoMan {
    private static ArrayList<Emoji> emojiList;

    public static ArrayList<Emoji> getEmojiList() {
        if (emojiList == null) {
            emojiList = generateEmojis();
        }
        return emojiList;
    }

    private static ArrayList<Emoji> generateEmojis() {
        ArrayList<Emoji> list = new ArrayList<>();
        for (int i = 0; i < EmojiResArray.length; i++) {
            Emoji emoji = new Emoji();
            emoji.setImageUri(EmojiResArray[i]);
            emoji.setContent(EmojiTextArray[i]);
            list.add(emoji);
        }
        return list;
    }


    public static final int[] EmojiResArray = {
            R.drawable.bm_1,
            R.drawable.bm_2,
            R.drawable.bm_3,
            R.drawable.bm_4,
            R.drawable.bm_5,
            R.drawable.bm_6,
            R.drawable.bm_7,
            R.drawable.bm_8,
            R.drawable.bm_9,
            R.drawable.bm_10,
            R.drawable.bm_11,
            R.drawable.bm_12,
            R.drawable.bm_13,
            R.drawable.bm_14,
            R.drawable.bm_15,
            R.drawable.bm_16,
            R.drawable.bm_17,
            R.drawable.bm_18,
            R.drawable.bm_19,
            R.drawable.bm_20,
            R.drawable.bm_21,
            R.drawable.bm_22,
            R.drawable.bm_23,
            R.drawable.bm_24,
            R.drawable.bm_25,
            R.drawable.bm_26,
            R.drawable.bm_27,
            R.drawable.bm_28,
            R.drawable.bm_29,
            R.drawable.bm_30,
            R.drawable.bm_31,
            R.drawable.bm_32,
            R.drawable.bm_33,
            R.drawable.bm_34,
            R.drawable.bm_35,
            R.drawable.bm_36,
            R.drawable.bm_37,
            R.drawable.bm_38,
            R.drawable.bm_39,
            R.drawable.bm_40,
            R.drawable.bm_41,
            R.drawable.bm_42,
            R.drawable.bm_43,
            R.drawable.bm_44,
            R.drawable.bm_45,
            R.drawable.bm_46,
            R.drawable.bm_47,
            R.drawable.bm_48,
            R.drawable.bm_49,
            R.drawable.bm_50,
            R.drawable.bm_51,
            R.drawable.bm_52,
            R.drawable.bm_53,
            R.drawable.bm_54,
            R.drawable.bm_55,
            R.drawable.bm_56,
            R.drawable.bm_57,
            R.drawable.bm_58,
            R.drawable.bm_59,
            R.drawable.bm_60,
            R.drawable.bm_61,
            R.drawable.bm_62,
            R.drawable.bm_63,
            R.drawable.bm_64,
            R.drawable.bm_65,
            R.drawable.bm_66,
            R.drawable.bm_67,
            R.drawable.bm_68,
            R.drawable.bm_69,
            R.drawable.bm_70
    };

    public static final String[] EmojiTextArray = {
            "[bm做操]", "[bm抓狂]", "[bm中枪]", "[bm震惊]", "[bm赞]", "[bm喜悦]", "[bm醒悟]", "[bm兴奋]", "[bm血泪]", "[bm挖鼻孔]", "[bm吐舌头]", "[bm吐槽]", "[bm投诉]", "[bm跳绳]", "[bm调皮]", "[bm讨论]", "[bm抬腿]", "[bm思考]", "[bm生气]", "[bm亲吻]", "[bm庆幸]", "[bm内涵]", "[bm忙碌]", "[bm乱入]", "[bm卖萌]", "[bm流泪]", "[bm流口水]", "[bm流鼻涕]",
            "[bm路过]", "[bm咧嘴]", "[bm啦啦队]", "[bm哭诉]", "[bm哭泣]", "[bm苦逼]", "[bm口哨]", "[bm可爱]", "[bm紧张]", "[bm惊讶]", "[bm惊吓]", "[bm焦虑]", "[bm会心笑]", "[bm坏笑]", "[bm花痴]", "[bm厚脸皮]", "[bm好吧]", "[bm害怕]", "[bm鬼脸]", "[bm孤独]", "[bm高兴]", "[bm搞怪]", "[bm干笑]", "[bm感动]", "[bm愤懑]", "[bm反对]", "[bm踱步]", "[bm顶]", "[bm得意]", "[bm嘚瑟]", " [bm大笑]", " [bm大哭]",
            "[bm大叫]", "[bm吃惊]", "[bm馋]", "[bm彩色]", "[bm缤纷]", "[bm变身]", "[bm悲催]", "[bm暴怒]", "[bm熬夜]", "[bm暗爽]"
    };

    static {
        emojiList = generateEmojis();
    }

}
