package com.cnxxp.cabbagenet.utils;

import android.content.Context;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.widget.TextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by admin on 2017/8/29 0029.
 */

public class EmojiUtils {

    public static void setEmojiText(String str, TextView textView, Context context) {
        SpannableString spannableString = new SpannableString(str);
        Pattern pattern = null;
        pattern = Pattern.compile("\\[(\\S+?)\\]");
        Matcher matcher = pattern.matcher(str);
        Integer drawableSrc = null;
        while (matcher.find()) {
            int start = matcher.start();
            int end = matcher.end();
            if(EmojiDataAll.emojiMap().get("["+matcher.group(1)+"]")!=null){
                drawableSrc = EmojiDataAll.emojiMap().get("["+matcher.group(1)+"]").getImageUri();
            }
            if (drawableSrc != null && drawableSrc > 0) {
                spannableString.setSpan(new ImageSpan(context, drawableSrc), start, end,
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }
        textView.setText(spannableString, TextView.BufferType.SPANNABLE);
    }


}
