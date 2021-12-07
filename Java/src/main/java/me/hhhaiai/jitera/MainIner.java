package me.hhhaiai.jitera;

import me.hhhaiai.jitera.modules.CardInfos;
import me.hhhaiai.jitera.modules.Prepare;
import org.json.JSONException;

/**
 * @Copyright © 2021 sanbo Inc. All rights reserved.
 * @Description: TODO
 * @Version: 1.0
 * @Create: 2021/11/29 10:36 上午
 * @author: sanbo
 */
public class MainIner {
    public static void main(String[] args) throws Exception {
        // 1. prepare
//        Prepare.prepare();
//        //2. sdcard dir see
//        SdcardRunGeneralFileLists.run();
        // 3. parser CardInfo
        CardInfos.run();
    }
}
