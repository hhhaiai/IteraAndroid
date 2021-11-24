package me.hhhaiai.jitera;

import me.hhhaiai.jitera.ifs.ISayHello;
import me.hhhaiai.jitera.utils.ShellUtils;
import me.hhhaiai.jitera.utils.TextUtils;

/**
 * @Copyright © 2021 sanbo Inc. All rights reserved.
 * @Description: 检测sdcard
 * @Version: 1.0
 * @Create: 2021/11/24 4:25 下午
 * @author: sanbo
 */
public class SdcardRun {
    public static void main(String[] args) {
        getSdcardAndroid();
    }

    /**
     * 解析用户的sdcard、android目录
     */
    private static void getSdcardAndroid() {
        runCommond("/sdcard/Android/media");
        runCommond("/sdcard/Android/obb");
        runCommond("/sdcard/Android/obj");
        runCommond("/sdcard/Android/data");
    }

    private static void runCommond(final String path) {
        ShellUtils.getArraysUseAdb("find " + path + "|xargs stat -c '%n^%X^%Y^%Z'", new ISayHello() {
            @Override
            public void onProcessLine(String line) {
                if (TextUtils.isEmpty(line)
                        || line.startsWith(path + "^")
                        || line.startsWith(path + "/.^")) {
                    return;
                }
                processLine(line, line.replaceAll(path + "/", ""));
            }
        });
    }

    /**
     * 解析行
     *
     * @param baseLine
     * @param line
     */
    private static void processLine(String baseLine, String line) {
        System.out.println(line);
    }

    /**
     * 获取所有的sdcard文件
     */
    private static void getAllSdcard() {
        ShellUtils.getArraysUseAdb("find /sdcard/|xargs stat -c '%n^%X^%Y^%Z'", new ISayHello() {
            @Override
            public void onProcessLine(String line) {
                System.out.println(line);
            }
        });
    }
}
