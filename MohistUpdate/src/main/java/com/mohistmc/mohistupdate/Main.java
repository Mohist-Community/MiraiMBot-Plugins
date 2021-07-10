package com.mohistmc.mohistupdate;

import com.mohistmc.miraimbot.annotations.Plugin;
import com.mohistmc.miraimbot.command.CommandManager;
import com.mohistmc.miraimbot.plugin.MohistPlugin;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;

@Plugin(value = "MohistUpdate",version = "0.0.2",authors = {"Mgazul"},description = "Mohist更新推送与检测")
public class Main extends MohistPlugin {

    public static ScheduledExecutorService Jenkins_UpdatePush = new ScheduledThreadPoolExecutor(3);
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
        version.put("1.16.5", "1.16.5");
        version.put("1.7.10", "1.7.10");
        CommandManager.register(new UpdateCommand());
        JenkinsUpdatePush.start();
        saveDefaultConfig();
        getLogger().info("插件 MohistUpdate 加载成功");
    }
}
