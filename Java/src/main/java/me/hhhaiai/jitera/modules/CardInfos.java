package me.hhhaiai.jitera.modules;

import me.hhhaiai.jitera.utils.FileUtils;
import me.hhhaiai.jitera.utils.TextUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

/**
 * @Copyright © 2021 sanbo Inc. All rights reserved.
 * @Description: TODO
 * @Version: 1.0
 * @Create: 2021/12/7 3:12 下午
 * @author: sanbo
 */
public class CardInfos {


    public static void main(String[] args) throws JSONException {
        run();

    }

    public static void run() throws JSONException {
//        File baseDirs = new File("Git_result/info");
        File baseDirs = new File("Git_result");
        List<File> files = new ArrayList<File>();
        if (baseDirs.exists()) {
            itera(baseDirs, files);
        }
        System.out.println("一共发现文件: " + files.size());
        for (File tempFile : files) {
            try {
//            parserCardInfosSingleFile(tempFile);
                parserSubinfoSingleFile(tempFile);
            } catch (Throwable e) {
                //e.printStackTrace();
            }
        }
    }

    private static void parserSubinfoSingleFile(File tempFile) throws JSONException {
        String s = FileUtils.readContent(tempFile.getAbsolutePath());
        JSONObject o = new JSONObject(s);
        if (o.has("Subinfo")) {
            JSONObject Subinfo = o.optJSONObject("Subinfo");
            if (Subinfo.has("getprop")) {
                JSONObject getprop = Subinfo.optJSONObject("getprop");
                for (String key : getprop.keySet()) {
                    String value = getprop.optString(key, "");
                    if (key.toLowerCase(Locale.getDefault()).contains("imei")
                            || key.toLowerCase(Locale.getDefault()).contains("meid")
                    ) {
                        if (!TextUtils.isEmpty(value)
                                && !"1".equalsIgnoreCase(value)
                                && !value.contains("no")
                                && !value.contains("_")
                                && !value.contains("true")
                                && !value.contains("false")
                                && !value.contains("com.")
                        ) {

                            if (!ifs.contains(key)) {
                                if (key.endsWith("0")
                                        || key.endsWith("1")
                                        || key.endsWith("2")
                                ) {
                                    if (!ifs.contains(key.substring(0, key.length() - 1))) {
                                        System.err.println(key + "<------->" + value);
                                    } else {
//                                        System.out.println(key + "<------->" + value);
                                    }
                                } else {
                                    System.err.println(key + "<------->" + value);
                                }


                            } else {
//                                System.out.println(key + "<------->" + value);
                            }
                        }
                    }


                }
            }
        }
    }

    private static void parserCardInfosSingleFile(File tempFile) throws JSONException {
        String s = FileUtils.readContent(tempFile.getAbsolutePath());
        JSONObject o = new JSONObject(s);
        if (o.has("Cardinfo")) {
            JSONObject obj = o.optJSONObject("Cardinfo");
            if (obj != null && obj.length() > 0) {
                for (String key : obj.keySet()) {
                    String value = obj.optString(key, "");
                    if (TextUtils.isEmpty(value) || value.contains("true") || "1".equalsIgnoreCase(value)) {
                        continue;
                    }
                    if (!ifs.contains(key)) {
                        System.out.println("没有的键值: " + key + "<-------->" + obj.get(key));
                        ifs.add(key);
                    }
                }
            }
        }
    }

    private static void itera(File f, List<File> files) {
        if (f.isDirectory()) {
            File[] fs = f.listFiles();
            for (File fx : fs) {
                if (fx.isFile()) {
                    if (fx.getName().endsWith(".json")) {
                        files.add(fx);
                    }
                } else {
                    itera(fx, files);
                }
            }
        } else {
            if (f.getName().endsWith(".json")) {
                files.add(f);
            }
        }
    }

    private static List<String> ifs =
            new ArrayList<String>(Arrays.asList(
                    "ril.gsm.imei",
                    "ril.gsm.meid",
                    "ril.cdma.imei",
                    "ril.cdma.meid",
                    "ro.ril.miui.imei",
                    "ro.ril.miui.meid",
                    "persist.radio.imei",
                    "persist.radio.meid",
                    "ro.ril.oem.imei",
                    "ro.ril.oem.meid",
                    "ril.modem.imei",
                    "ril.modem.meid",
                    "gsm.device.imei",
                    "gsm.device.meid",
                    "cdma.device.imei",
                    "cdma.device.meid",
                    "sys.prop.writeimei",
                    "gsm.meid",
                    "gsm.imei",
                    "cdma.meid",
                    "cdma.imei",
                    "persist.sys.imei",
                    "persist.sys.meid",
                    "persist.sys.show.device.imei",
                    "persist.sys.updater.imei",
                    "persist.sys.updater.meid",
                    "persist.sys.vtouch.imei",
                    "persist.sys.vtouch.meid",
                    "ro.boot.imei",
                    "ro.boot.meid",
                    "ro.meizu.hardware.imei",
                    "ro.meizu.hardware.meid",
                    "vendor.oem.device.imeicache",
                    "vendor.oem.device.meidcache",
                    "vendor.radio.device.imeicache",
                    "vendor.radio.device.meidcache", "oppo.device.imeicache"));
}
