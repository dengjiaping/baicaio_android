package com.cnxxp.cabbagenet.utils;

import com.cnxxp.cabbagenet.R;
import com.cnxxp.cabbagenet.bean.Emoji;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * Created by admin on 2017/8/30 0030.
 */

public class EmojiDataAll {
    private static ArrayList<Emoji> emojiList;
    private static HashMap<String, Emoji> emojiMap;

    public static HashMap<String, Emoji> emojiMap() {
        if (emojiMap == null) {
            emojiMap = generateEmojis();
        }
        return emojiMap;
    }

    private static HashMap<String,Emoji> generateEmojis() {
        HashMap<String,Emoji> map= new HashMap<>();
        for (int i = 0; i < EmojiResArray.length; i++) {
            Emoji emoji = new Emoji();
            emoji.setImageUri(EmojiResArray[i]);
            emoji.setContent(EmojiTextArray[i]);
            map.put(EmojiTextArray[i],emoji);
        }
        return map;
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
            R.drawable.commen_130,
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
            R.drawable.bm_70,
            R.drawable.funny_1,
            R.drawable.funny_2,
            R.drawable.funny_3,
            R.drawable.funny_4,
            R.drawable.funny_5,
            R.drawable.funny_6,
            R.drawable.funny_7,
            R.drawable.funny_8,
            R.drawable.funny_9,
            R.drawable.funny_10,
            R.drawable.funny_11,
            R.drawable.funny_12,
            R.drawable.funny_13,
            R.drawable.funny_14,
            R.drawable.funny_15,
            R.drawable.funny_16,
            R.drawable.funny_17,
            R.drawable.funny_18,
            R.drawable.funny_19,
            R.drawable.funny_20,
            R.drawable.funny_21,
            R.drawable.funny_22,
            R.drawable.funny_23,
            R.drawable.funny_24,
            R.drawable.funny_25,
            R.drawable.funny_26,
            R.drawable.funny_27,
            R.drawable.funny_28,
            R.drawable.funny_29,
            R.drawable.funny_30,
            R.drawable.funny_31,
            R.drawable.funny_32,
            R.drawable.funny_33,
            R.drawable.funny_34,
            R.drawable.funny_35,
            R.drawable.funny_36,
            R.drawable.funny_37,
            R.drawable.funny_38,
            R.drawable.funny_39,
            R.drawable.funny_40,
            R.drawable.funny_41,
            R.drawable.funny_42,
            R.drawable.funny_43,
            R.drawable.funny_44,
            R.drawable.funny_45,
            R.drawable.funny_46,
            R.drawable.funny_47,
            R.drawable.funny_48,
            R.drawable.funny_49,
            R.drawable.funny_50,
            R.drawable.funny_51,
            R.drawable.funny_52,
            R.drawable.funny_53,
            R.drawable.funny_54,
            R.drawable.funny_55,
            R.drawable.funny_56,
            R.drawable.funny_57,
            R.drawable.funny_58,
            R.drawable.funny_59,
            R.drawable.funny_60,
            R.drawable.funny_61,
            R.drawable.funny_62,
            R.drawable.funny_63,
            R.drawable.funny_64,
            R.drawable.funny_65,
            R.drawable.funny_66,
            R.drawable.funny_67,
            R.drawable.funny_68,
            R.drawable.funny_69,
            R.drawable.funny_70,
            R.drawable.funny_71,
            R.drawable.funny_72,
            R.drawable.funny_73,
            R.drawable.funny_74,
            R.drawable.funny_75,
            R.drawable.funny_76,
            R.drawable.funny_77,
            R.drawable.funny_78,
            R.drawable.funny_79,
            R.drawable.funny_80,
            R.drawable.funny_81,
            R.drawable.funny_82,
            R.drawable.funny_83,
            R.drawable.funny_84,
            R.drawable.funny_85,
            R.drawable.funny_86
    };

    public static final String[] EmojiTextArray = {
            "[坏笑]", "[舔屏]", "[污]", "[允悲]", "[笑而不语]", "[费解]", "[憧憬]", "[并不简单]", "[微笑]", "[嘻嘻]", "[哈哈]", "[可爱]", "[可怜]", "[挖鼻]", "[吃惊]", "[害羞]", "[挤眼]", "[闭嘴]", "[鄙视]", "[爱你]", "[泪]", "[偷笑]",
            "[亲亲]", "[生病]", "[太开心]", "[白眼]", "[右哼哼]", "[左哼哼]", "[嘘]", "[衰]", "[委屈]", "[吐]", "[哈欠]", "[抱抱_旧]", "[怒]", "[疑问]", "[馋嘴]", "[拜拜]", "[思考]", "[汗]", "[困]", "[睡]", "[钱]", "[失望]",
            "[酷]", "[色]", "[哼]", "[鼓掌]", "[晕]", "[悲伤]", "[抓狂]", "[黑线]", "[阴险]", "[怒骂]", "[互粉]", "[心]", "[伤心]", "[猪头]", "[熊猫]", "[兔子]", "[ok]", "[耶]", "[good]", "[NO]", "[赞]", "[来]", "[弱]",
            "[草泥马]", "[神马]", "[囧]", "[浮云]", "[给力]", "[围观]", "[威武]", "[话筒]", "[蜡烛]", "[蛋糕]", "[牛郎]", "[织女]", "[广告]", "[doge]", "[喵喵]", "[二哈]", "[小黄人坏笑]", "[小黄人剪刀手]", "[小黄人高兴]", "[笑cry]", "[摊手]", "[抱抱]",
            "[红包飞]", "[发红包]", "[冰川时代希德奶奶]", "[快银]", "[暴风女]", "[芒果流口水]", "[芒果点赞]", "[芒果大笑]", "[芒果得意]", "[芒果萌萌哒]", "[羊年大吉]", "[西瓜]", "[足球]", "[老妈我爱你]", "[母亲节]", "[肥皂]", "[有钱]", "[地球一小时]", "[国旗]", "[许愿]", "[风扇]", "[炸鸡和啤酒]",
            "[雪]", "[马上有对象]", "[马到成功旧]", "[青啤鸿运当头]", "[让红包飞]", "[ali做鬼脸]", "[ali哇]", "[xkl转圈]", "[酷哭熊顽皮]", "[bm可爱]", "[BOBO爱你]", "[转发]", "[得意地笑]", "[ppb鼓掌]", "[din推撞]", "[moc转发]", "[lt切克闹]", "[江南style]", "[笑哈哈]",
            "[bm做操]", "[bm抓狂]", "[bm中枪]", "[bm震惊]", "[bm赞]", "[bm喜悦]", "[bm醒悟]", "[bm兴奋]", "[bm血泪]", "[bm挖鼻孔]", "[bm吐舌头]", "[bm吐槽]", "[bm投诉]", "[bm跳绳]", "[bm调皮]", "[bm讨论]", "[bm抬腿]", "[bm思考]", "[bm生气]", "[bm亲吻]", "[bm庆幸]", "[bm内涵]", "[bm忙碌]", "[bm乱入]", "[bm卖萌]", "[bm流泪]", "[bm流口水]", "[bm流鼻涕]",
            "[bm路过]", "[bm咧嘴]", "[bm啦啦队]", "[bm哭诉]", "[bm哭泣]", "[bm苦逼]", "[bm口哨]", "[bm可爱]", "[bm紧张]", "[bm惊讶]", "[bm惊吓]", "[bm焦虑]", "[bm会心笑]", "[bm坏笑]", "[bm花痴]", "[bm厚脸皮]", "[bm好吧]", "[bm害怕]", "[bm鬼脸]", "[bm孤独]", "[bm高兴]", "[bm搞怪]", "[bm干笑]", "[bm感动]", "[bm愤懑]", "[bm反对]", "[bm踱步]", "[bm顶]", "[bm得意]", "[bm嘚瑟]", " [bm大笑]", " [bm大哭]",
            "[bm大叫]", "[bm吃惊]", "[bm馋]", "[bm彩色]", "[bm缤纷]", "[bm变身]", "[bm悲催]", "[bm暴怒]", "[bm熬夜]", "[bm暗爽]",
            "[doge]", "[喵喵]", "[二哈]", "[织]", "[兔子]", "[神马]", "[浮云]", "[给力]", "[萌]", "[熊猫]", "[互粉]", "[围观]", "[扔鸡蛋]", "[奥特曼]", "[威武]", "[伤心]", "[热吻]", "[囧]", "[orz]", "[宅]", "[帅]",
            "[猪头]", "[实习]", "[骷髅]", "[便便]", "[黄牌]", "[红牌]", "[跳舞花]", "[礼花]", "[打针]", "[闪]", "[啦啦]", "[吼吼]", "[庆祝]", "[嘿]", "[团]", "[圆]", "[男孩儿]", "[女孩儿]", "[炸鸡啤酒]", "[做鬼脸]", "[22]",
            "[00]", "[2]", "[3]", "[6]", "[7]", "[1]", "[9]", "[4]", "[5]", "[8]", "[z]", "[y]", "[x]", "[v]", "[u]", "[t]", "[s]", "[r]", "[q]", "[p]", "[n]", "[l]", "[k]", "[j]", "[h]",
            "[g]", "[d]", "[a]", "[w]", "[点]", "[o]", "[m]", "[i]", "[e]", "[c]", "[b]", "[鸭梨]", "[省略号]", "[kiss]", "[雪人]", "[小丑]", "[问号]", "[叹号]", "[句号]"

    };

    static {
        emojiMap = generateEmojis();
    }

}
