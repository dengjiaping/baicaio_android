package com.cnxxp.cabbagenet.utils;

import com.cnxxp.cabbagenet.R;
import com.cnxxp.cabbagenet.bean.Emoji;

import java.util.ArrayList;

/**
 * Created by admin on 2017/8/30 0030.
 */

public class EmojiDataFunny {
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
            "[doge]", "[喵喵]", "[二哈]", "[织]", "[兔子]", "[神马]", "[浮云]", "[给力]", "[萌]", "[熊猫]", "[互粉]", "[围观]", "[扔鸡蛋]", "[奥特曼]", "[威武]", "[伤心]", "[热吻]", "[囧]", "[orz]", "[宅]", "[帅]",
            "[猪头]", "[实习]", "[骷髅]", "[便便]", "[黄牌]", "[红牌]", "[跳舞花]", "[礼花]", "[打针]", "[闪]", "[啦啦]", "[吼吼]", "[庆祝]", "[嘿]", "[团]", "[圆]", "[男孩儿]", "[女孩儿]", "[炸鸡啤酒]", "[做鬼脸]", "[22]",
            "[00]", "[2]", "[3]", "[6]", "[7]", "[1]", "[9]", "[4]", "[5]", "[8]", "[z]", "[y]", "[x]", "[v]", "[u]", "[t]", "[s]", "[r]", "[q]", "[p]", "[n]", "[l]", "[k]", "[j]", "[h]",
            "[g]", "[d]", "[a]", "[w]", "[点]", "[o]", "[m]", "[i]", "[e]", "[c]", "[b]", "[鸭梨]", "[省略号]", "[kiss]", "[雪人]", "[小丑]", "[问号]", "[叹号]", "[句号]"

    };

    static {
        emojiList = generateEmojis();
    }

}
