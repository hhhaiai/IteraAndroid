package me.hhhaiai.jitera.logic;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 获取单个文件夹的重合率
 */
public class SingleLogicFileName {

    // {层级,{文件名称:[应用列表]}}
    private static Map<Long, Map<String, List<String>>> mTempDataForLimitOnePackageOnce =
            new HashMap<Long, Map<String, List<String>>>();

    // {层级:{文件名称,次数}}
    private static Map<Long, Map<String, Long>> mLayerAndFilevCount =
            new HashMap<Long, Map<String, Long>>();

    public static JSONObject getMemoryDataWithAppList() {
        return new JSONObject(mTempDataForLimitOnePackageOnce);
    }

    public static JSONObject getMemoryData() {
        for (Map.Entry<Long, Map<String, List<String>>> entry :
                mTempDataForLimitOnePackageOnce.entrySet()) {
            long layer = entry.getKey();
            Map<String, List<String>> info = entry.getValue();
            Map<String, Long> layerInfo = new HashMap<>();

            for (Map.Entry<String, List<String>> entry1 : info.entrySet()) {
                String path = entry1.getKey();
                long len = entry1.getValue().size();
                layerInfo.put(path, len);
            }
            mLayerAndFilevCount.put(layer, layerInfo);
        }
        return new JSONObject(mLayerAndFilevCount);
    }

    public static void clearMemoryData() {
        mTempDataForLimitOnePackageOnce.clear();
        mLayerAndFilevCount.clear();
    }

    /**
     * 按包为维度统计单独
     *
     * @param layer
     * @param pkg
     * @param fileName
     */
    public static void addToMemory(long layer, String pkg, String fileName) {
        //        System.out.println("["+layer+"]"+pkg+"------------>"+fileName);

        if (isLimitOnePackageOnce(layer, pkg, fileName)) {
            //            if (!mLayerAndFilevCount.containsKey(layer)) {
            //                Map<String, Long> info = new HashMap<String, Long>();
            //                info.put(fileName, 1L);
            //                mLayerAndFilevCount.put(layer, info);
            //            } else {
            //                Map<String, Long> info = mLayerAndFilevCount.get(layer);
            //                if (!info.containsKey(fileName)) {
            //                    info.put(fileName, 1L);
            //                } else {
            //                    info.put(fileName, info.get(fileName) + 1);
            //                }
            //                mLayerAndFilevCount.put(layer, info);
            //            }
        }
    }

    /**
     * 是否改成已经使用
     *
     * @param layer
     * @param pkg
     * @param fileName
     * @return
     */
    private static boolean isLimitOnePackageOnce(long layer, String pkg, String fileName) {
        if (!mTempDataForLimitOnePackageOnce.containsKey(layer)) {
            // {文件名称:[应用列表]}
            Map<String, List<String>> info = new HashMap<>();
            List<String> pkgList = new ArrayList<>();
            pkgList.add(pkg);
            info.put(fileName, pkgList);
            mTempDataForLimitOnePackageOnce.put(layer, info);
            return false;
        } else {
            Map<String, List<String>> info = mTempDataForLimitOnePackageOnce.get(layer);
            if (!info.containsKey(fileName)) {
                List<String> pkgList = new ArrayList<>();
                pkgList.add(pkg);
                info.put(fileName, pkgList);
                mTempDataForLimitOnePackageOnce.put(layer, info);
                return false;
            } else {
                List<String> pkgList = info.get(fileName);
                if (!pkgList.contains(pkg)) {
                    pkgList.add(pkg);
                    info.put(fileName, pkgList);
                    mTempDataForLimitOnePackageOnce.put(layer, info);
                    return false;
                } else {
                    return true;
                }
            }
        }
    }
}
