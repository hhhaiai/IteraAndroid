package me.hhhaiai.jitera.modules;

import me.hhhaiai.jitera.utils.ShellUtils;

import java.io.File;

/**
 * @Copyright © 2021 sanbo Inc. All rights reserved.
 * @Description: TODO
 * @Version: 1.0
 * @Create: 2021/11/29 10:35 上午
 * @author: sanbo
 */
public class Prepare {
    public static void main(String[] args) {
        prepare();
    }

    public static boolean prepare() {
        File f = new File("Git_result");
        if (f.exists() && f.isFile()) {
            f.deleteOnExit();
        }
        if (!f.exists()) {
            //need clone
            ShellUtils.shellCmd("git clone  https://github.com/hhhaiai/Git_result.git");
        } else {
            // need sync
            ShellUtils.shellCmds("cd Git_result", "git reset --hard", "git pull");
        }
        if (f.exists() && f.isDirectory()) {
            return true;
        } else {
            return false;
        }
    }
}
