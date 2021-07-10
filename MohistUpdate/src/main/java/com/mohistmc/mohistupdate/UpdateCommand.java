package com.mohistmc.mohistupdate;


import com.mohistmc.miraimbot.annotations.Command;
import com.mohistmc.miraimbot.command.CommandExecutor;
import com.mohistmc.miraimbot.command.CommandResult;

@Command("mohistupdate")
public class UpdateCommand extends CommandExecutor {

    public UpdateCommand(){
        super.label = "mohistupdate";
        super.usage = "mohistupdate";
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
            result.sendMessageOrGroup("======更新检测======\n检测失败,请输入分支哦~");
            return true;
        }
        String msg = result.getArgs().get(0);
        if (Main.version.containsKey(msg)) {
            try {
                result.sendMessageOrGroup("正在读取数据，请稍后~");
                result.sendMessageOrGroup(UpdateUtils.info(Main.version.get(msg)));
                Main.plugin.getLogger().info("CI数据读取完毕");
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                result.sendMessageOrGroup("======更新检测======\n检测失败,内部错误哦~");
                return false;
            }
        }
        return true;
    }
}
