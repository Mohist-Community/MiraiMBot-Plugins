package com.mohistmc.mohistupdate;

import com.mohistmc.miraimbot.cmds.manager.CommandExecutor;
import com.mohistmc.miraimbot.cmds.manager.CommandResult;
import com.mohistmc.miraimbot.cmds.manager.annotations.Command;
import com.mohistmc.miraimbot.console.log4j.MiraiMBotLog;

@Command(name = "update", usage = "#update")
public class UpdateCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandResult result) {
        if (result.getArgs().size() <= 0) {
            result.sendMessage("======更新检测======\n检测失败,请输入分支哦~");
            return true;
        }
        String msg = result.getArgs().get(0);
        if (Main.version.containsKey(msg)) {
            try {
                result.sendMessage("正在读取数据，请稍后~");
                result.sendMessage(UpdateUtils.info(Main.version.get(msg)));
                MiraiMBotLog.LOGGER.info("CI数据读取完毕");
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                result.sendMessage("======更新检测======\n检测失败,内部错误哦~");
                return false;
            }
        }
        return true;
    }
}
