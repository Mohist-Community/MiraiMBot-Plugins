package com.mohistmc.mohistupdate;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.mohistmc.miraimbot.console.log4j.MiraiMBotLog;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

/**
 * @author Mgazul
 * @date 2020/4/22 23:25
 */
public class UpdateUtils {

    public static void main(String[] a){
        try {
            MiraiMBotLog.LOGGER.info(info("1.16.4"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String info(String msg) throws IOException {
        MiraiMBotLog.LOGGER.info("开始读取CI数据");
        URLConnection request = new URL("https://ci.codemc.io/job/Mohist-Community/job/Mohist-" + msg + "/lastSuccessfulBuild/api/json").openConnection();
        request.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:25.0) Gecko/20100101 Firefox/25.0");
        request.connect();
        MiraiMBotLog.LOGGER.info(((HttpURLConnection) request).getResponseCode());
        JsonElement json = new JsonParser().parse(new InputStreamReader((InputStream) request.getContent()));
        String number = json.getAsJsonObject().get("number").toString();

        String authorstring = json.getAsJsonObject().get("actions").getAsJsonArray().get(0).getAsJsonObject().get("causes").getAsJsonArray().get(0).getAsJsonObject().get("shortDescription").toString();
        String[] authors = authorstring.replace("\"", "").split(" ");
        String author = authors[authors.length - 1];

        String time = json.getAsJsonObject().get("changeSet").getAsJsonObject().get("items").getAsJsonArray().get(0).getAsJsonObject().get("date").toString().replace("+0800", "").replaceAll("\"", "");

        JsonArray items = json.getAsJsonObject().get("changeSet").getAsJsonObject().get("items").getAsJsonArray();
        String message0 = items.get(0).getAsJsonObject().get("msg").toString().replace("\"", "");
        String message = items.get(0).getAsJsonObject().get("comment").toString().replace("\"", "");
        String comment = message.replace(message0, "").replace("\\n", "");
        String sendMsg = "======Mohist更新检测======" + "\n" +
                "分支: #branche#" + "\n" +
                "构建号: #number#" + "\n" +
                "提交时间: #time#" + "\n" +
                "提交人: #author#" + "\n" +
                "提交信息: #msg#" + "\n";
        if (comment != null) sendMsg = sendMsg + comment;
        sendMsg = sendMsg
                .replace("#branche#", msg)
                .replace("#number#", number)
                .replace("#time#", time)
                .replace("#author#", author)
                .replace("#msg#", message0);
        return sendMsg;
    }
}
