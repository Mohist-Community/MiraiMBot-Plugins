package com.mohistmc.mohistupdate;

import com.mohistmc.miraimbot.NamedThreadFactory;
import com.mohistmc.miraimbot.plugin.MohistPlugin;
import com.mohistmc.miraimbot.plugin.Plugin;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;

@Plugin(name = "MohistUpdate",version = "0.0.2",authors = {"Mgazul"},description = "Mohist更新推送与检测")
public class Main extends MohistPlugin {

    public static ScheduledExecutorService Jenkins_UpdatePush = new ScheduledThreadPoolExecutor(1, new NamedThreadFactory("JenkinsUpdatePush"));
    public static Map<String, String> version = new ConcurrentHashMap<>();
    public static Main plugin;

    @Override
    public void onDisable() {
        JenkinsUpdatePush.stop();
    }

    @Override
    public void onEnable() {
        plugin = this;
        version.put("1.12.2", "1.12.2");
        version.put("1.7.10", "1.7.10");
        version.put("1.16.4", "1.16.4");
        version.put("1.16.3", "1.16.3");
        registerCommands(new UpdateCommand());
        JenkinsUpdatePush.start();
        getLogger().info("插件 MohistUpdate 加载成功");
    }
}
