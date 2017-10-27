package com.cnxxp.cabbagenet.utils;

import com.cnxxp.cabbagenet.R;
import com.cnxxp.cabbagenet.bean.Emoji;

import java.util.ArrayList;

/**
 * Created by admin on 2017/8/30 0030.
 */

public class EmojiDataCommen {
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
            R.drawable.commen_1,
            R.drawable.commen_2,
            R.drawable.commen_3,
            R.drawable.commen_4,
            R.drawable.commen_5,
            R.drawable.commen_6,
            R.drawable.commen_7,
            R.drawable.commen_8,
            R.drawable.commen_9,
            R.drawable.commen_10,
            R.drawable.commen_11,
            R.drawable.commen_12,
            R.drawable.commen_13,
            R.drawable.commen_14,
            R.drawable.commen_15,
            R.drawable.commen_16,
            R.drawable.commen_17,
            R.drawable.commen_18,
            R.drawable.commen_19,
            R.drawable.commen_20,
            R.drawable.commen_21,
            R.drawable.commen_22,
            R.drawable.commen_23,
            R.drawable.commen_24,
            R.drawable.commen_25,
            R.drawable.commen_26,
            R.drawable.commen_27,
            R.drawable.commen_28,
            R.drawable.commen_29,
            R.drawable.commen_30,
            R.drawable.commen_31,
            R.drawable.commen_32,
            R.drawable.commen_33,
            R.drawable.commen_34,
            R.drawable.commen_35,
            R.drawable.commen_36,
            R.drawable.commen_37,
            R.drawable.commen_38,
            R.drawable.commen_39,
            R.drawable.commen_40,
            R.drawable.commen_41,
            R.drawable.commen_42,
            R.drawable.commen_43,
            R.drawable.commen_44,
            R.drawable.commen_45,
            R.drawable.commen_46,
            R.drawable.commen_47,
            R.drawable.commen_48,
            R.drawable.commen_49,
            R.drawable.commen_50,
            R.drawable.commen_51,
            R.drawable.commen_52,
            R.drawable.commen_53,
            R.drawable.commen_54,
            R.drawable.commen_55,
            R.drawable.commen_56,
            R.drawable.commen_57,
            R.drawable.commen_58,
            R.drawable.commen_59,
            R.drawable.commen_60,
            R.drawable.commen_61,
            R.drawable.commen_62,
            R.drawable.commen_63,
            R.drawable.commen_64,
            R.drawable.commen_65,
            R.drawable.commen_66,
            R.drawable.commen_67,
            R.drawable.commen_68,
            R.drawable.commen_68,
            R.drawable.commen_70,
            R.drawable.commen_71,
            R.drawable.commen_72,
            R.drawable.commen_73,
            R.drawable.commen_74,
            R.drawable.commen_75,
            R.drawable.commen_76,
            R.drawable.commen_77,
            R.drawable.commen_78,
            R.drawable.commen_79,
            R.drawable.commen_80,
            R.drawable.commen_81,
            R.drawable.commen_82,
            R.drawable.commen_83,
            R.drawable.commen_84,
            R.drawable.commen_85,
            R.drawable.commen_86,
            R.drawable.commen_87,
            R.drawable.commen_88,
            R.drawable.commen_89,
            R.drawable.commen_90,
            R.drawable.commen_91,
            R.drawable.commen_92,
            R.drawable.commen_93,
            R.drawable.commen_94,
            R.drawable.commen_95,
            R.drawable.commen_96,
            R.drawable.commen_97,
            R.drawable.commen_98,
            R.drawable.commen_99,
            R.drawable.commen_100,
            R.drawable.commen_101,
            R.drawable.commen_102,
            R.drawable.commen_103,
            R.drawable.commen_104,
            R.drawable.commen_105,
            R.drawable.commen_106,
            R.drawable.commen_107,
            R.drawable.commen_108,
            R.drawable.commen_109,
            R.drawable.commen_110,
            R.drawable.commen_111,
            R.drawable.commen_112,
            R.drawable.commen_113,
            R.drawable.commen_114,
            R.drawable.commen_115,
            R.drawable.commen_116,
            R.drawable.commen_117,
            R.drawable.commen_118,
            R.drawable.commen_119,
            R.drawable.commen_120,
            R.drawable.commen_121,
            R.drawable.commen_122,
            R.drawable.commen_123,
            R.drawable.commen_124,
            R.drawable.commen_125,
            R.drawable.commen_126,
            R.drawable.commen_127,
            R.drawable.commen_128,
            R.drawable.commen_129,
            R.drawable.commen_130
    };

    public static final String[] EmojiTextArray = {
            "[坏笑]", "[舔屏]", "[污]", "[允悲]", "[笑而不语]", "[费解]", "[憧憬]", "[并不简单]", "[微笑]", "[嘻嘻]", "[哈哈]", "[可爱]", "[可怜]", "[挖鼻]", "[吃惊]", "[害羞]", "[挤眼]", "[闭嘴]", "[鄙视]", "[爱你]", "[泪]", "[偷笑]",
            "[亲亲]", "[生病]", "[太开心]", "[白眼]", "[右哼哼]", "[左哼哼]", "[嘘]", "[衰]", "[委屈]", "[吐]", "[哈欠]", "[抱抱_旧]", "[怒]", "[疑问]", "[馋嘴]", "[拜拜]", "[思考]", "[汗]", "[困]", "[睡]", "[钱]", "[失望]",
            "[酷]", "[色]", "[哼]","[鼓掌]",  "[晕]", "[悲伤]", "[抓狂]", "[黑线]", "[阴险]", "[怒骂]", "[互粉]", "[心]", "[伤心]", "[猪头]", "[熊猫]", "[兔子]", "[ok]", "[耶]", "[good]", "[NO]", "[赞]", "[来]", "[弱]",
            "[草泥马]", "[神马]", "[囧]", "[浮云]", "[给力]", "[围观]", "[威武]", "[话筒]", "[蜡烛]", "[蛋糕]", "[牛郎]", "[织女]", "[广告]", "[doge]", "[喵喵]", "[二哈]", "[小黄人坏笑]", "[小黄人剪刀手]", "[小黄人高兴]", "[笑cry]", "[摊手]", "[抱抱]",
            "[红包飞]", "[发红包]", "[冰川时代希德奶奶]", "[快银]", "[暴风女]", "[芒果流口水]", "[芒果点赞]", "[芒果大笑]", "[芒果得意]", "[芒果萌萌哒]", "[羊年大吉]", "[西瓜]", "[足球]", "[老妈我爱你]", "[母亲节]", "[肥皂]", "[有钱]", "[地球一小时]", "[国旗]", "[许愿]", "[风扇]", "[炸鸡和啤酒]",
            "[雪]", "[马上有对象]", "[马到成功旧]", "[青啤鸿运当头]", "[让红包飞]", "[ali做鬼脸]", "[ali哇]", "[xkl转圈]", "[酷哭熊顽皮]", "[bm可爱]", "[BOBO爱你]", "[转发]", "[得意地笑]", "[ppb鼓掌]", "[din推撞]", "[moc转发]", "[lt切克闹]", "[江南style]", "[笑哈哈]"
    };

    static {
        emojiList = generateEmojis();
    }

}
