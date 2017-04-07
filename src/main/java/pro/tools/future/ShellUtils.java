package pro.tools.future;


import pro.tools.constant.SystemConstant;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

/**
 * shell相关工具
 *
 * @author sd
 */
public class ShellUtils {

    private ShellUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 是否是在root下执行命令
     *
     * @param commandExec 包含下面四个属性
     *                    commands          命令数组
     *                    isRoot            是否需要root权限执行
     *                    isNeedResultMsg   是否需要结果消息
     *                    isNeedEnter       是否需要回车
     * @return CommandResult
     */
    public static CommandResult execCmd(CommandExec commandExec) {
        int result = -1;
        if (commandExec.getCommands() == null || commandExec.getCommands().length == 0) {
            return new CommandResult(result, null, null);
        }

        String[] commands = commandExec.getCommands();
        boolean isNeedResultMsg = commandExec.isNeedResultMsg();
        boolean isNeedEnter = commandExec.isNeedEnter();

        Process process = null;
        StringBuilder successMsg = null;
        StringBuilder errorMsg = null;

        try {
            process = Runtime.getRuntime().exec(SystemConstant.isWindows ? "cmd" : "sh");
            try (DataOutputStream os = new DataOutputStream(process.getOutputStream())) {
                for (String command : commands) {
                    if (command == null) continue;
                    os.write(command.getBytes());
                    os.writeBytes("\n");
                    os.flush();
                }
                os.writeBytes("exit\n");
                os.flush();
                result = process.waitFor();
            } catch (InterruptedException | IOException e) {
                e.printStackTrace();
            }
            if (isNeedResultMsg) {
                try (BufferedReader successResult = new BufferedReader(new InputStreamReader(process.getInputStream(), "UTF-8"));
                     BufferedReader errorResult = new BufferedReader(new InputStreamReader(process.getErrorStream(), "UTF-8"))) {
                    successMsg = new StringBuilder();
                    errorMsg = new StringBuilder();
                    String s;
                    char entry = isNeedEnter ? '\n' : ' ';
                    while ((s = successResult.readLine()) != null) {
                        successMsg.append(s).append(entry);
                    }
                    while ((s = errorResult.readLine()) != null) {
                        errorMsg.append(s).append(entry);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (process != null) {
                process.destroy();
            }
        }
        return new CommandResult(
                result,
                successMsg == null ? null : successMsg.toString(),
                errorMsg == null ? null : errorMsg.toString()
        );
    }

    /**
     * 返回的命令结果
     */
    public static class CommandResult {
        /**
         * 结果码
         **/
        public int result;
        /**
         * 成功信息
         **/
        public String successMsg;
        /**
         * 错误信息
         **/
        public String errorMsg;

        CommandResult(int result, String successMsg, String errorMsg) {
            this.result = result;
            this.successMsg = successMsg;
            this.errorMsg = errorMsg;
        }
    }

    /**
     * 执行命令用的对象
     */
    public static class CommandExec {

        //命令集合
        private String[] commands;
        //是否需要返回信息
        private boolean isNeedResultMsg;
        //是否需要回车
        private boolean isNeedEnter;

        public CommandExec(boolean isNeedResultMsg, boolean isNeedEnter, List<String> commandList) {
            this.commands = commandList.toArray(getCommands());
            this.isNeedResultMsg = isNeedResultMsg;
            this.isNeedEnter = isNeedEnter;
        }

        public CommandExec(boolean isNeedResultMsg, boolean isNeedEnter, String... commands) {
            this.commands = commands;
            this.isNeedResultMsg = isNeedResultMsg;
            this.isNeedEnter = isNeedEnter;
        }

        public String[] getCommands() {
            return commands;
        }

        public CommandExec setCommands(List<String> commandList) {
            this.commands = commandList.toArray(getCommands());
            return this;
        }

        public CommandExec setCommands(String[] commands) {
            this.commands = commands;
            return this;
        }

        public boolean isNeedResultMsg() {
            return isNeedResultMsg;
        }

        public CommandExec setNeedResultMsg(boolean needResultMsg) {
            isNeedResultMsg = needResultMsg;
            return this;
        }

        public boolean isNeedEnter() {
            return isNeedEnter;
        }

        public CommandExec setNeedEnter(boolean needEnter) {
            isNeedEnter = needEnter;
            return this;
        }
    }
}