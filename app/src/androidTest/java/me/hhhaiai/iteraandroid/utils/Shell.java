package me.hhhaiai.iteraandroid.utils;

import android.database.Cursor;
import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.Closeable;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.nio.channels.FileLock;
import java.util.ArrayList;
import java.util.List;

/**
 * @Copyright © 2021 sanbo Inc. All rights reserved.
 * @Description: TODO
 * @Version: 1.0
 * @Create: 2021/11/24 4:03 下午
 * @author: sanbo
 */
public class Shell {
    public static List<String> getResultArrays(String cmd) {
        return getArrays(cmd, null, true);
    }

    /**
     * 核心工作方法
     *
     * @param cmd          执行指令
     * @param call         回调函数
     * @param isNeedResult 需要返回值
     * @return
     */
    public static List<String> getArrays(String cmd, final ISayHello call, boolean isNeedResult) {

        if (TextUtils.isEmpty(cmd)) {
            log(new Exception(" cmd ["+cmd+"] is null"));
            return null;
        }
        List<String> result = new ArrayList<String>();
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
            // don't use os.writeBytes(commmand), avoid chinese charset error
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
                if (!TextUtils.isEmpty(line)) {
                    line = line.trim();
                    if (isNeedResult && !result.contains(line)) {
                        result.add(line);
                    }
                    if (call != null) {
                        call.onProcessLine(line);
                    }
                }
            }
        } catch (Throwable e) {
            log(e);
        } finally {
            safeClose(pos, ii, br, is, in, os);
        }
        return result;
    }
    public static void safeClose(Object... os) {
        if (os != null && os.length > 0) {
            for (Object o : os) {
                if (o != null) {
                    try {
                        if (o instanceof HttpURLConnection) {
                            ((HttpURLConnection) o).disconnect();
                        } else if (o instanceof Closeable) {
                            ((Closeable) o).close();
                        } else if (o instanceof FileLock) {
                            ((FileLock) o).release();
                        } else if (o instanceof Cursor) {
                            ((Cursor) o).close();
                        }

                    } catch (Throwable e) {
                        log(e);
                    }
                }
            }
        }
    }
    private static void log(Throwable sth) {
        Log.e("sanbo",Log.getStackTraceString(sth));
    }
}
