package com.mohistmc.mcserverping;

import com.mohistmc.miraimbot.annotations.Plugin;
import com.mohistmc.miraimbot.command.CommandManager;
import com.mohistmc.miraimbot.plugin.MohistPlugin;

@Plugin(value  = "MCServerPing",version = "0.2",authors = {"Mgazul"},description = "查询服务器信息")
public class Main extends MohistPlugin {

    public static Main plugin;

    @Override
    public void onDisable() {
        super.onDisable();
    }

    @Override
    public void onEnable() {
        plugin = this;
        CommandManager.register(new PingCommand());
        //saveDefaultConfig();
        //getLogger().info("插件 MCServerPing 加载成功");
    }
}
