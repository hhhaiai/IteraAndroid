package me.hhhaiai.iteraandroid;

import android.content.Context;
import android.util.Log;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import junit.framework.TestCase;

import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @Copyright © 2021 sanbo Inc. All rights reserved.
 * @Description: TODO
 * @Version: 1.0
 * @Create: 2021/11/24 3:58 下午
 * @author: sanbo
 */
@RunWith(AndroidJUnit4.class)
public class MainActivityTest extends TestCase {
    Context mContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

    @Test
    public void testTest1() {
        Log.i("sanbo", "testTest1...");
    }
}