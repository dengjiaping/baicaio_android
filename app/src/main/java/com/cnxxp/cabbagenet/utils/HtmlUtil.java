package com.cnxxp.cabbagenet.utils;

import com.yuyh.library.imgsel.utils.LogUtils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Created by admin on 2017/4/14 0014.
 */

public class HtmlUtil {
    public static String getNewContent(String htmltext) {

        Document doc = Jsoup.parse(htmltext);
        Elements elements = doc.getElementsByTag("img");

        for (Element element : elements) {
            element.attr("width", "100%").attr("height", "auto");
            String imgurl = element.attr("src");
            if ((imgurl.startsWith("/"))) {
                element.attr("src", "http://www.baicaio.com" + imgurl);
            }
        }
        Elements elements1 = doc.getElementsByTag("p");
        for (Element element : elements1) {
            element.attr("style", "color:#807d80;font-size:14px;letter-spacing:1px; font-family:'Times New Roman';font-weight:200");
        }
        Elements element2 = doc.getElementsByTag("a");
        for (Element element : element2) {
            element.attr("style", "text-decoration:none;color:#3dc399");
        }
        Elements elements3 = doc.getElementsByTag("span");
        for (Element element : elements3) {
            element.attr("style","color:#807d80;font-size:14px;letter-spacing:1px; font-family:'Times New Roman';font-weight:200");
        }
        LogUtils.e(doc.toString());
        return doc.toString();
    }


}
