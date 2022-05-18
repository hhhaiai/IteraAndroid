package me.hhhaiai.jitera.logic;

import me.hhhaiai.jitera.utils.ShellUtils;

import org.json.JSONArray;

import java.util.List;

/**
 * @Copyright © 2021 sanbo Inc. All rights reserved.
 * @Description: TODO
 * @Version: 1.0
 * @Create: 2021/11/25 4:20 下午
 * @author: sanbo
 */
public class DeviceInfo {
    //    public static void main(String[] args) {
    //        System.out.println(get());
    //    }

    public static JSONArray get() {
        JSONArray res = new JSONArray();
        List<String> props = ShellUtils.getArrayUseAdb("getprop");
        if (props == null || props.size() <= 0) {
            return res;
        }
        return new JSONArray(props);
    }

    public static String getmodel() {
        return ShellUtils.getStringUseAdb("getprop ro.product.model").replaceAll(" ", "_");
    }

    public static String getVersion() {
        return ShellUtils.getStringUseAdb("getprop ro.build.version.sdk").replaceAll(" ", "_");
    }
}
