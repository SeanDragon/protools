package pro.tools.system;


import pro.tools.constant.StrConst;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

/**
 * shell相关工具
 *
 * @author SeanDragon
 */
public final class ToolShell {

    private ToolShell() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 是否是在root下执行命令
     *
     * @param toolCommandExec
     *         包含下面四个属性 commands          命令数组 isRoot            是否需要root权限执行 isNeedResultMsg   是否需要结果消息 isNeedEnter
     *         是否需要回车
     *
     * @return ToolCommandResult
     */
    public static ToolCommandResult execCmd(ToolCommandExec toolCommandExec) throws IOException {
        int result = -1;
        if (toolCommandExec.getCommands() == null || toolCommandExec.getCommands().length == 0) {
            return new ToolCommandResult(result, null, null);
        }

        String[] commands = toolCommandExec.getCommands();
        boolean isNeedResultMsg = toolCommandExec.isNeedResultMsg();
        boolean isNeedEnter = toolCommandExec.isNeedEnter();

        Process process = null;
        StringBuilder successMsg = null;
        StringBuilder errorMsg = null;

        try {
            process = Runtime.getRuntime().exec(ToolSystem.isWindows() ? "cmd" : "sh");
            try (DataOutputStream os = new DataOutputStream(process.getOutputStream())) {
                for (String command : commands) {
                    if (command == null) {
                        continue;
                    }
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
                try (BufferedReader successResult = new BufferedReader(new InputStreamReader(process.getInputStream(), StrConst.DEFAULT_CHARSET_NAME));
                     BufferedReader errorResult = new BufferedReader(new InputStreamReader(process.getErrorStream(), StrConst.DEFAULT_CHARSET_NAME))) {
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
                }
            }
        } finally {
            if (process != null) {
                process.destroy();
            }
        }
        return new ToolCommandResult(
                result,
                successMsg == null ? null : successMsg.toString(),
                errorMsg == null ? null : errorMsg.toString()
        );
    }

    /**
     * 返回的命令结果
     */
    public static class ToolCommandResult {
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

        ToolCommandResult(int result, String successMsg, String errorMsg) {
            this.result = result;
            this.successMsg = successMsg;
            this.errorMsg = errorMsg;
        }
    }

    /**
     * 执行命令用的对象
     */
    public static class ToolCommandExec {

        /**
         * 命令集合
         */
        private String[] commands;
        /**
         * 是否需要返回信息
         */
        private boolean isNeedResultMsg;
        /**
         * 是否需要回车
         */
        private boolean isNeedEnter;

        public ToolCommandExec(boolean isNeedResultMsg, boolean isNeedEnter, List<String> commandList) {
            this.commands = commandList.toArray(getCommands());
            this.isNeedResultMsg = isNeedResultMsg;
            this.isNeedEnter = isNeedEnter;
        }

        public ToolCommandExec(boolean isNeedResultMsg, boolean isNeedEnter, String... commands) {
            this.commands = commands;
            this.isNeedResultMsg = isNeedResultMsg;
            this.isNeedEnter = isNeedEnter;
        }

        public String[] getCommands() {
            return commands;
        }

        public ToolCommandExec setCommands(String[] commands) {
            this.commands = commands;
            return this;
        }

        public ToolCommandExec setCommands(List<String> commandList) {
            this.commands = commandList.toArray(getCommands());
            return this;
        }

        public boolean isNeedResultMsg() {
            return isNeedResultMsg;
        }

        public ToolCommandExec setNeedResultMsg(boolean needResultMsg) {
            isNeedResultMsg = needResultMsg;
            return this;
        }

        public boolean isNeedEnter() {
            return isNeedEnter;
        }

        public ToolCommandExec setNeedEnter(boolean needEnter) {
            isNeedEnter = needEnter;
            return this;
        }
    }
}