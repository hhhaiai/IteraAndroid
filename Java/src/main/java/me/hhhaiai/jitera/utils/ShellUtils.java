package me.hhhaiai.jitera.utils;

import me.hhhaiai.jitera.ifs.ISayHello;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class ShellUtils {
    public static List<String> getResultArray(String cmd) {
        List<String> result = new ArrayList<String>();
        Process proc = null;
        BufferedInputStream in = null;
        BufferedReader br = null;
        InputStreamReader is = null;
        InputStream ii = null;
        DataOutputStream os = null;
        OutputStream pos = null;
        try {
            proc = Runtime.getRuntime().exec("sh");
            pos = proc.getOutputStream();
            os = new DataOutputStream(pos);

            // donnot use os.writeBytes(commmand), avoid chinese charset error
            os.write(cmd.getBytes());
            os.writeBytes("\n");
            os.flush();
            //exitValue
            os.writeBytes("exit\n");
            os.flush();
            ii = proc.getInputStream();
            in = new BufferedInputStream(ii);
            is = new InputStreamReader(in);
            br = new BufferedReader(is);
            String line = "";
            while ((line = br.readLine()) != null) {
                if (!TextUtils.isEmpty(line) && !result.contains(line)) {
                    result.add(line);
                }
            }
        } catch (Throwable e) {
            e.printStackTrace();
        } finally {
            safeClose(pos, ii, br, is, in, os);
        }

        return result;
    }

    public static String getResultString(String cmd) {
        String result = "";
        Process proc = null;
        BufferedInputStream in = null;
        BufferedReader br = null;
        InputStreamReader is = null;
        InputStream ii = null;
        StringBuilder sb = new StringBuilder();
        DataOutputStream os = null;
        OutputStream pos = null;
        try {
            proc = Runtime.getRuntime().exec("sh");
            pos = proc.getOutputStream();
            os = new DataOutputStream(pos);

            os.write(cmd.getBytes());
            os.writeBytes("\n");
            os.flush();
            //exitValue
            os.writeBytes("exit\n");
            os.flush();
            ii = proc.getInputStream();
            in = new BufferedInputStream(ii);
            is = new InputStreamReader(in);
            br = new BufferedReader(is);
            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\n");
            }
            if (sb.length() > 0) {
                return sb.substring(0, sb.length() - 1);
            }
            result = String.valueOf(sb);
            if (!TextUtils.isEmpty(result)) {
                result = result.trim();
            }
        } catch (Throwable e) {
            e.printStackTrace();
        } finally {
            safeClose(pos, ii, br, is, in, os);
        }

        return result;
    }

    public static String getStringUseAdb(String cmd) {
        String result = "";
        Process proc = null;
        BufferedInputStream in = null;
        BufferedReader br = null;
        InputStreamReader is = null;
        InputStream ii = null;
        StringBuilder sb = new StringBuilder();
        DataOutputStream os = null;
        OutputStream pos = null;
        try {
            proc = Runtime.getRuntime().exec("adb shell");
            pos = proc.getOutputStream();
            os = new DataOutputStream(pos);

            os.write(cmd.getBytes());
            os.writeBytes("\n");
            os.flush();
            os.writeBytes("exit\n");
            os.flush();
            ii = proc.getInputStream();
            in = new BufferedInputStream(ii);
            is = new InputStreamReader(in);
            br = new BufferedReader(is);
            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\n");
            }
            if (sb.length() > 0) {
                return sb.substring(0, sb.length() - 1);
            }
            result = String.valueOf(sb);
            if (!TextUtils.isEmpty(result)) {
                result = result.trim();
            }
        } catch (Throwable e) {
        } finally {
            safeClose(pos, ii, br, is, in, os);
        }

        return result;
    }


    public static CopyOnWriteArrayList<String> getArrayUseAdb(String cmd) {
        return getArrayUseAdb(cmd, null, null);
    }

    public static CopyOnWriteArrayList<String> getArrayUseAdb(String cmd, String replaceBeforeStr, String replaceAfterStr) {
        Process proc = null;
        BufferedInputStream in = null;
        BufferedReader br = null;
        InputStreamReader is = null;
        InputStream ii = null;
        DataOutputStream os = null;
        OutputStream pos = null;
        CopyOnWriteArrayList<String> results = new CopyOnWriteArrayList<>();
        try {
            proc = Runtime.getRuntime().exec("adb shell");
            pos = proc.getOutputStream();
            os = new DataOutputStream(pos);

            // donnot use os.writeBytes(commmand), avoid chinese charset error
            os.write(cmd.getBytes());
            os.writeBytes("\n");
            os.flush();
            //exitValue
            os.writeBytes("exit\n");
            os.flush();
            ii = proc.getInputStream();
            in = new BufferedInputStream(ii);
            is = new InputStreamReader(in);
            br = new BufferedReader(is);
            String line = "", processedLine = "";
            while ((line = br.readLine()) != null) {
                processedLine = "";
                if (!TextUtils.isEmpty(line)) {
                    processedLine = processLine(line, replaceBeforeStr, replaceAfterStr);
                    results.add(processedLine.trim());
                } else {
                    //空值暂不处理
                }


            }
        } catch (Throwable e) {
            e.printStackTrace();
        } finally {
            safeClose(pos, ii, br, is, in, os);
        }

        return results;
    }

    public static void getArraysUseAdb(String cmd
            , final ISayHello call) {
        getArraysUseAdb(cmd, call, null, null, false);
    }

    /**
     * 核心工作方法
     *
     * @param cmd          执行指令
     * @param call         回调函数
     * @param isNeedResult 需要返回值
     * @return
     */
    public static CopyOnWriteArrayList<String> getArraysUseAdb(String cmd
            , final ISayHello call
            , String replaceBeforeStr
            , String replaceAfterStr
            , boolean isNeedResult) {

        Process proc = null;
        BufferedInputStream in = null;
        BufferedReader br = null;
        InputStreamReader is = null;
        InputStream ii = null;
        DataOutputStream os = null;
        OutputStream pos = null;
        CopyOnWriteArrayList<String> results = new CopyOnWriteArrayList<>();
        try {
            proc = Runtime.getRuntime().exec("adb shell");
            pos = proc.getOutputStream();
            os = new DataOutputStream(pos);

            // donnot use os.writeBytes(commmand), avoid chinese charset error
            os.write(cmd.getBytes());
            os.writeBytes("\n");
            os.flush();
            //exitValue
            os.writeBytes("exit\n");
            os.flush();
            ii = proc.getInputStream();
            in = new BufferedInputStream(ii);
            is = new InputStreamReader(in);
            br = new BufferedReader(is);
            String line = "", processedLine = "";
            while ((line = br.readLine()) != null) {
                processedLine = "";
                if (!TextUtils.isEmpty(line)) {
                    processedLine = processLine(line, replaceBeforeStr, replaceAfterStr);
                    if (isNeedResult) {
                        results.add(processedLine.trim());
                    }
                    if (call != null) {
                        call.onProcessLine(processedLine);
                    }
                } else {
                    //空值暂不处理
                }


            }
        } catch (Throwable e) {
            e.printStackTrace();
        } finally {
            safeClose(pos, ii, br, is, in, os);
        }

        return results;
    }

    private static String processLine(String line, String replaceBeforeStr, String replaceAfterStr) {


        // 被替换的值不能为空,不然替换个毛
        if (TextUtils.isEmpty(replaceBeforeStr)) {
            return line;
        } else {
            // 替换和被替换的值不能同时为空,不然替换个毛线
            if (TextUtils.isEmpty(replaceAfterStr)) {
                return line;
            } else {
                // 替换后的值
                return line.replace(replaceBeforeStr, replaceAfterStr);
            }
        }
    }


    private static void safeClose(Closeable... obj) {
        if (obj != null && obj.length > 0) {
            for (Closeable close : obj
            ) {

                try {
                    close.close();
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            }
        }


    }

}
