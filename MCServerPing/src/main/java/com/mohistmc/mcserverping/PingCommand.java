package com.mohistmc.mcserverping;

import com.mohistmc.miraimbot.cmds.manager.CommandExecutor;
import com.mohistmc.miraimbot.cmds.manager.CommandResult;
import com.mohistmc.miraimbot.cmds.manager.annotations.Command;
import com.mohistmc.miraimbot.mcserverping.MCPing;
import com.mohistmc.miraimbot.mcserverping.MCPingOptions;
import com.mohistmc.miraimbot.mcserverping.MCPingResponse;
import java.io.IOException;

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
            MCPingOptions options;
            if (ip.length == 2) {
                options = MCPingOptions.builder().hostname(ip[0]).port(Integer.valueOf(ip[1]).intValue()).build();
            } else {
                options = MCPingOptions.builder().hostname(ip[0]).build();
            }

            MCPingResponse reply;

            try {
                reply = MCPing.getPing(options);
            } catch (IOException ex) {
                result.sendMessage(options.getHostname() + " 无法访问.");
                return true;
            }

            MCPingResponse.Description description = reply.getDescription();
            sb.append("======MC服务器状态======").append("\n");
            sb.append("MOTD: " + description.getStrippedText()).append("\n");
            MCPingResponse.Players players = reply.getPlayers();
            sb.append("在线人数: " + players.getOnline() + "/" + players.getMax()).append("\n");

            MCPingResponse.Version version = reply.getVersion();
            sb.append("版本: " + version.getName()).append("\n");
            sb.append("协议号: " + version.getProtocol()).append("\n");

            result.sendMessage(sb.toString());
            // String.format("图标: %s", reply.getFavicon())
        }
        return true;
    }
}