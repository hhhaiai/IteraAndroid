package me.hhhaiai.iteraandroid;

import android.content.Context;
import android.util.Log;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import junit.framework.TestCase;

import me.hhhaiai.iteraandroid.utils.ISayHello;
import me.hhhaiai.iteraandroid.utils.Shell;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

/**
 * @Copyright © 2021 sanbo Inc. All rights reserved.
 * @Description: TODO
 * @Version: 1.0
 * @Create: 2021/11/24 4:01 下午
 * @author: sanbo
 */
@RunWith(AndroidJUnit4.class)
public class IteraSdcard extends TestCase {
    Context mContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
    private List<String> lines = new ArrayList<String>();

    @Test
    public void getAppSdcardInfo() {
        log("getAppSdcardInfo beging....");
        Shell.getArrays(
                "find  /sdcard/ |xargs stat -c '%n %X %Y %Z'",
                new ISayHello() {
                    @Override
                    public void onProcessLine(String line) {
                        log(line);
                    }
                },
                false);
        log("getAppSdcardInfo end....");
    }

    private void log(String sth) {
        Log.i("sanbo", sth);
    }
}
