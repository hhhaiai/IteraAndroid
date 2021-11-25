package me.hhhaiai.jitera.up;

import ff.jnezha.jnt.cs.GithubHelper;
import me.hhhaiai.jitera.utils.MDate;
import me.hhhaiai.jitera.utils.TextUtils;

import java.net.InetAddress;
import java.util.Base64;
import java.util.Map;

/**
 * @Copyright © 2021 sanbo Inc. All rights reserved.
 * @Description: TODO
 * @Version: 1.0
 * @Create: 2021/11/25 3:55 下午
 * @author: sanbo
 */
public class UploadHelper {
    private static final String MSG_COMMIT;

    static {
        Map<String, String> map = System.getenv();
        String userName = map.get("USERNAME");
        String computerName = map.get("COMPUTERNAME");
        if (TextUtils.isEmpty(userName)) {
            userName = System.getProperty("user.name");
        }
        if (TextUtils.isEmpty(computerName)) {
            try {
                computerName = InetAddress.getLocalHost().getHostName();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        MSG_COMMIT = String.format("Upload by  %s via %s . time: %s", userName, computerName, MDate.getNow());
    }

    public static String getk(String k) {
        String s = k.replaceAll("-", "");
        try {
            byte[] bs = Base64.getDecoder().decode(s.getBytes("UTF-8"));
            return new String(bs);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return "";
    }


//    public static void main(String[] args) {
//        reportToGithub("ss");
//    }

    public static void reportToGithub(String content) {
        GithubHelper.createFile("hhhaiai", "Git_result",
                "/gx/" + MDate.get("yyyy-MM-dd_HH:mm:ss") + ".txt"
                , getk("--Z2hwX0pzUTVHZm1PS0ltY1phZXFPTlFwRTJjMDJuM25TbzI3NldJaw==-")
                , content, MSG_COMMIT);

    }


}
