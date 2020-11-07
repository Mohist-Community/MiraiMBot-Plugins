package com.mohistmc.mcserverping;

import com.mohistmc.miraimbot.plugin.MohistPlugin;
import com.mohistmc.miraimbot.plugin.Plugin;

@Plugin(name = "MCServerPing",version = "0.0.1",authors = {"Mgazul"},description = "查询服务器信息")
public class Main extends MohistPlugin {

    public static Main plugin;

    @Override
    public void onDisable() {
        super.onDisable();
    }

    @Override
    public void onEnable() {
        plugin = this;
        registerCommands(new PingCommand());
        getLogger().info("插件 MCServerPing 加载成功");
    }
}
