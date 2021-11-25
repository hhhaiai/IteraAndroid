package me.hhhaiai.jitera;

import me.hhhaiai.jitera.ifs.ISayHello;
import me.hhhaiai.jitera.logic.DeviceInfo;
import me.hhhaiai.jitera.logic.SingleLogicFileName;
import me.hhhaiai.jitera.logic.SingleLogicFullName;
import me.hhhaiai.jitera.up.UploadHelper;
import me.hhhaiai.jitera.utils.*;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @Copyright © 2021 sanbo Inc. All rights reserved.
 * @Description: 检测sdcard
 * @Version: 1.0
 * @Create: 2021/11/24 4:25 下午
 * @author: sanbo
 */
public class SdcardRunGeneralFileLists {
    public static void main(String[] args) throws JSONException {
        clear();
        getSdcardAndroid();
        System.out.println("单个文件夹重合率结果:" + SingleLogicFullName.getMemoryData());
//        System.out.println("单个文件夹重合率结果{层级,{文件名称:[应用列表]}}:" + SingleLogic.getMemoryDataWithAppList());
        JSONObject fullMomoryCount = SingleLogicFullName.getMemoryData();
        JSONObject fileMomoryCount = SingleLogicFileName.getMemoryData();
        JSONObject json = new JSONObject();
        json.put("设备信息", DeviceInfo.get());
        json.put("全路径信息", fullMomoryCount);
        json.put("文件信息", fileMomoryCount);

        UploadHelper.reportToGithub(json.toString(4));


    }

    private static void clear() {
        SingleLogicFullName.clearMemoryData();
    }

    /**
     * 解析用户的sdcard、android目录
     */
    private static void getSdcardAndroid() {
        runCommand("/sdcard/Android/media");
        runCommand("/sdcard/Android/obb");
        runCommand("/sdcard/Android/obj");
        runCommand("/sdcard/Android/data");
    }

    private static void runCommand(final String path) {

//        planA(path);

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

    private static void planA(String path) {
        CopyOnWriteArrayList<String> shellResult = ShellUtils.getArrayUseAdb("find " + path + "|xargs stat -c '%n^%X^%Y^%Z'");
        System.out.println("shellResult:" + shellResult.size());
        for (String line : shellResult) {
            if (TextUtils.isEmpty(line)
                    || line.startsWith(path + "^")
                    || line.startsWith(path + "/.^")) {
                return;
            }
            processLine(line, line.replaceAll(path + "/", ""));
        }
    }

    /**
     * 解析行,解析有效性、获取末次更新的时间
     *
     * @param baseLine
     * @param line
     */
    private static void processLine(String baseLine, String line) {

        String[] pathAndTimes = TextUtils.split(line, "^", true);
        if (pathAndTimes.length == 4) {
            String path = pathAndTimes[0];
            long lastTime = FtimeHelper.getMax(
                    Long.valueOf(pathAndTimes[1])
                    , Long.valueOf(pathAndTimes[2])
                    , Long.valueOf(pathAndTimes[3])
            );
            parserPath(baseLine, path, lastTime);
        } else {
            System.err.println("异常数据查分后的数量:[" + pathAndTimes.length + "]---源数据--->" + line);
        }
    }

    /**
     * 真正解析路径
     *
     * @param baseLine
     * @param path
     * @param lastTime
     */
    private static void parserPath(String baseLine, String path, long lastTime) {
//        System.out.println(MDate.getDateFromTimestamp(lastTime * 1000) + "-------->" + path);
        String[] pathItems = TextUtils.split(path, "/", false);
        //只有包名的元素
        if (pathItems.length == 0) {
            //只有包名，没有二级目录
            return;
        }

        if (!PkgHelper.isEfficientPkg(pathItems[0])) {
            //非法包名，一般是push或者其他标记的存储路径
            return;
        }
        // 只解析今天的文件夹
        if (!MDate.isToday(lastTime * 1000)) {
            return;
        }
//        System.out.println("["+ MDate.isToday(lastTime*1000)+"]"+baseLine);
//        System.out.println(Arrays.asList(pathItems) + "==========" + path);
        for (int i = 1; i < pathItems.length; i++) {
//            System.out.println(i + "---" + pathItems[i]);
            int layer = i + 1;
            String pkg = pathItems[0];
            String currentFileName = pathItems[i];
            String fullFileName = getFullPath(i, pathItems);
//            System.out.println("[" + i + "]_____" + currentFileName + "===========" + fullFileName + "---------pathItems:---->" + Arrays.asList(pathItems));

            SingleLogicFileName.addToMemory(layer, pkg, currentFileName);
            SingleLogicFullName.addToMemory(layer, pkg, fullFileName);
        }
    }

    private static String getFullPath(int currentPosition, String[] pathItems) {
        if (currentPosition == 1) {
            return pathItems[currentPosition];
        }
        String[] tempSs = Arrays.copyOfRange(pathItems, 1, currentPosition + 1);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < tempSs.length; i++) {
            sb.append(tempSs[i]).append("/");
        }
        String result = sb.toString();
        if (result.endsWith("/")) {
            return result.substring(0, result.length() - 1);
        } else {
            return result;
        }
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
