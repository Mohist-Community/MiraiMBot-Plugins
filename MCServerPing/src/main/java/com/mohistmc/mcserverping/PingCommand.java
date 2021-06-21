package com.mohistmc.mcserverping;

import com.mohistmc.miraimbot.annotations.Command;
import com.mohistmc.miraimbot.command.CommandExecutor;
import com.mohistmc.miraimbot.command.CommandResult;
import com.mohistmc.miraimbot.minecraft.mcserverping.MinecraftServerPing;
import com.mohistmc.miraimbot.minecraft.mcserverping.MinecraftServerPingInfos;
import com.mohistmc.miraimbot.minecraft.mcserverping.MinecraftServerPingOptions;
import java.io.IOException;

@Command("ping")
public class PingCommand extends CommandExecutor {

    public PingCommand(){
        super.label = "ping";
        super.usage = "ping ip:端口";
        super.description = "";
        super.noshow = false;
        super.opCan = false;
        super.onlyOp = false;
        super.permissionEnable = false;
        super.permission = "";
        super.type = Command.Type.ALL;
    }

    @Override
    public boolean onCommand(CommandResult result) {
        if (result.getArgs().size() <= 0) {
            result.sendMessageOrGroup("======使用检测======\n请输入IP, 格式> ip:端口");
            return true;
        } else {
            String msg = result.getArgs().get(0);
            StringBuilder sb = new StringBuilder();
            String[] ip = msg.split(":");
            MinecraftServerPingOptions options;
            if (ip.length == 2) {
                options = new MinecraftServerPingOptions().setHostname(ip[0]).setPort(Integer.parseInt(ip[1]));
            } else {
                options =  new MinecraftServerPingOptions().setHostname(ip[0]);
            }

            MinecraftServerPingInfos reply;

            try {
                reply = new MinecraftServerPing().getPing(options);
            } catch (IOException ex) {
                result.sendMessageOrGroup(options.getHostname() + ":" + options.getPort() + " 无法访问.");
                return true;
            }

            sb.append("======MC服务器状态======").append("\n");
            sb.append("MOTD: " + reply.getStrippedDescription()).append("\n");
            MinecraftServerPingInfos.Players players = reply.getPlayers();
            sb.append("在线人数: " + players.getOnline() + "/" + players.getMax()).append("\n");

            MinecraftServerPingInfos.Version version = reply.getVersion();
            sb.append("版本: " + version.getName()).append("\n");
            sb.append("协议号: " + version.getProtocol()).append("\n");

            result.sendMessageOrGroup(sb.toString());
            // String.format("图标: %s", reply.getFavicon())
        }
        return false;
    }
}