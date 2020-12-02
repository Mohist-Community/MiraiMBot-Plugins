package com.mohistmc.mcserverping;

import com.mohistmc.miraimbot.cmds.manager.CommandExecutor;
import com.mohistmc.miraimbot.cmds.manager.CommandResult;
import com.mohistmc.miraimbot.cmds.manager.annotations.Command;
import com.mohistmc.miraimbot.mcserverping.MinecraftServerPing;
import com.mohistmc.miraimbot.mcserverping.MinecraftServerPingInfos;
import com.mohistmc.miraimbot.mcserverping.MinecraftServerPingOptions;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

@Command(name = "ping", description = "查询服务器信息", alias = {"c", "查服", "motd"}, usage = "#ping [ip:端口]")
public class PingCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandResult result) {
        if (result.getArgs().size() <= 0) {
            result.sendMessage("======使用检测======\n请输入IP, 格式> ip:端口");
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
                result.sendMessage(options.getHostname() + ":" + options.getPort() + " 无法访问.");
                return true;
            }

            sb.append("======MC服务器状态======").append("\n");
            sb.append("MOTD: " + reply.getStrippedDescription()).append("\n");
            MinecraftServerPingInfos.Players players = reply.getPlayers();
            sb.append("在线人数: " + players.getOnline() + "/" + players.getMax()).append("\n");

            MinecraftServerPingInfos.Version version = reply.getVersion();
            sb.append("版本: " + version.getName()).append("\n");
            sb.append("协议号: " + version.getProtocol()).append("\n");

            result.sendMessage(sb.toString());
            // String.format("图标: %s", reply.getFavicon())
        }
        return true;
    }

    public static void main(String[] args) throws IOException {
        MinecraftServerPingInfos data = new MinecraftServerPing().getPing(new MinecraftServerPingOptions().setHostname("12.mgazul.cn").setPort(25565));
        System.out.println(data.getStrippedDescription() + "\n" + data.getVersion().getName() + "\n" + data.getLatency() + "ms\n" + data.getPlayers().getOnline() + "/" + data.getPlayers().getMax());
    }
}