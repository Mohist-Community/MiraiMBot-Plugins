package com.mohistmc.mohistupdate;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.mohistmc.miraimbot.MiraiMBot;
import com.mohistmc.miraimbot.console.log4j.MiraiMBotLog;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class JenkinsUpdatePush implements Runnable {

    public static List<String> ver = new ArrayList<>();

    static {
        ver.add("1.12.2");
        ver.add("1.16.4");
        ver.add("1.16.3");
        ver.add("1.7.10");
    }

    @Override
    public void run() {
        for (String s : ver) {
            try {
                URLConnection request = new URL("https://ci.codemc.io/job/Mohist-Community/job/Mohist-" + s + "/lastSuccessfulBuild/api/json").openConnection();
                request.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:25.0) Gecko/20100101 Firefox/25.0");
                request.connect();
                JsonElement json = new JsonParser().parse(new InputStreamReader((InputStream) request.getContent()));
                int number = Integer.parseInt(json.getAsJsonObject().get("number").toString());

                String authorstring = json.getAsJsonObject().get("actions").getAsJsonArray().get(0).getAsJsonObject().get("causes").getAsJsonArray().get(0).getAsJsonObject().get("shortDescription").toString();
                String[] authors = authorstring.replace("\"", "").split(" ");
                String author = authors[authors.length-1];

                String time = json.getAsJsonObject().get("changeSet").getAsJsonObject().get("items").getAsJsonArray().get(0).getAsJsonObject().get("date").toString().replace("+0800", "").replaceAll("\"", "");

                JsonArray items = json.getAsJsonObject().get("changeSet").getAsJsonObject().get("items").getAsJsonArray();
                String message0 = items.get(0).getAsJsonObject().get("msg").toString().replace("\"", "");
                String message = items.get(0).getAsJsonObject().get("comment").toString().replace("\"", "");
                String comment = message.replace(message0, "").replace("\\n", "");

                String ymlver = s.replace(".", "-");
                // 没有缓存时写入
                if(MiraiMBot.yaml.get("mohist_number." + ymlver) == null) {
                    MiraiMBot.yaml.set("mohist_number." + ymlver, number);
                    MiraiMBot.saveYaml(MiraiMBot.yaml, MiraiMBot.file);
                }
                if (MiraiMBot.yaml.getInt("mohist_number." + ymlver) < number) {
                    String sendMsg = "======Mohist更新推送======" + "\n" +
                            "分支: #branche#" + "\n" +
                            "构建号: #number#" + "\n" +
                            "提交时间: #time#" + "\n" +
                            "提交人: #author#" + "\n" +
                            "提交信息: #msg#" + "\n";
                    if (comment != null) sendMsg = sendMsg + comment;
                    sendMsg = sendMsg
                            .replace("#branche#", s)
                            .replace("#number#", String.valueOf(number))
                            .replace("#time#", time)
                            .replace("#author#", author)
                            .replace("#msg#", message0);
                    Main.plugin.sendGroupMessage(793311898L, sendMsg);
                    Main.plugin.sendGroupMessage(782534813L, sendMsg);
                    MiraiMBot.yaml.set("mohist_number." + ymlver, number);
                    MiraiMBot.saveYaml(MiraiMBot.yaml, MiraiMBot.file);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void start() {
        MiraiMBotLog.LOGGER.info("开始运行更新推送程序");
        Main.Jenkins_UpdatePush.scheduleAtFixedRate(new JenkinsUpdatePush(), 1000 * 1, 1000 * 60, TimeUnit.MILLISECONDS);
    }

    public static void stop() {
        Main.Jenkins_UpdatePush.shutdown();
    }
}
