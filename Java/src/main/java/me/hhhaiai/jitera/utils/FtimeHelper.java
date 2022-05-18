package me.hhhaiai.jitera.utils;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * @Copyright © 2021 sanbo Inc. All rights reserved.
 * @Description: 文件时间工具类
 * @Version: 1.0
 * @Create: 2021/07/208 11:56:20
 * @author: sanbo
 */
public class FtimeHelper {
    public static Long getMax(Long... lsw) {
        if (lsw != null && lsw.length > 0) {
            return getMax(Arrays.asList(lsw));
        }
        return 0L;
    }

    public static Long getMax(List<Long> pps) {
        if (pps != null && pps.size() > 0) {
            long temp = 0L;
            for (long ps : pps) {
                if (ps > temp) {
                    temp = ps;
                }
            }
            return temp;
        }

        return 0L;
    }

    public static Pair<Long, String> getMaxTime(Pair<Long, String>... times) {
        if (times != null && times.length > 0) {
            return getMaxTime(Arrays.asList(times));
        } else {
            return new Pair<Long, String>(0L, "");
        }
    }

    public static Pair<Long, String> getMaxTime(List<Pair<Long, String>> times) {
        Pair<Long, String> pr = new Pair<Long, String>(0L, "");
        try {
            if (times.size() > 0) {
                long lastestTime = 0;
                for (Pair<Long, String> p : times) {
                    if (p.first > lastestTime) {
                        pr = p;
                        lastestTime = p.first;
                    }
                }
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return pr;
    }

    /**
     * 返回数组中最接近现在的时间，若时间是未来的，则忽略
     *
     * @param paths
     * @return
     */
    public static Pair<Long, String> getFilesMaxTime(String... paths) {

        if (paths != null && paths.length > 0) {
            return getFilesMaxTime(Arrays.asList(paths));
        } else {
            return new Pair<Long, String>(0L, "");
        }
    }

    /**
     * 返回列表中最接近现在的时间，若时间是未来的，则忽略
     *
     * @param paths
     * @return
     */
    public static Pair<Long, String> getFilesMaxTime(List<String> paths) {
        Pair<Long, String> pr = new Pair<Long, String>(0L, "");
        try {
            if (paths.size() > 0) {
                long lastestTime = 0;
                for (String p : paths) {
                    Pair<Long, String> pp = getFileTime(p);
                    if (pp.first == 0) {
                        continue;
                    }
                    if (pp.first > lastestTime) {
                        pr = new Pair<Long, String>(pp.first, p);
                        lastestTime = pp.first;
                    }
                }
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return pr;
    }

    public static Pair<Long, String> getFilesMaxTime(Set<String> paths) {
        Pair<Long, String> pr = new Pair<Long, String>(0L, "");
        try {
            if (paths.size() > 0) {
                long lastestTime = 0;
                for (String p : paths) {
                    Pair<Long, String> pp = getFileTime(p);
                    if (pp.first == 0) {
                        continue;
                    }
                    if (pp.first > lastestTime) {
                        pr = new Pair<Long, String>(pp.first, p);
                        lastestTime = pp.first;
                    }
                }
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return pr;
    }

    public static Long getFileTime(File file) {
        return file.lastModified();
    }

    /**
     * 返回列表中最接近现在的时间，若时间是未来的，则忽略
     *
     * @param path
     * @return
     */
    public static Pair<Long, String> getFileTime(String path) {

        Pair<Long, String> pr = new Pair<Long, String>(0L, "");
        try {
            if (TextUtils.isEmpty(path)) {
                return pr;
            }
            File file = new File(path);
            if (file == null || !file.exists()) {
                return pr;
            }
            long time = file.lastModified();

            // 如果时间是未来的，则返回无效时间
            if (time <= System.currentTimeMillis()) {
                pr = new Pair<Long, String>(time, file.getAbsolutePath());
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return pr;
    }

    /**
     * 获取文件的文件夹路径
     *
     * @param path
     * @return
     */
    public static Pair<Long, String> getFatcherDirInfo(String path) {

        Pair<Long, String> pr = new Pair<Long, String>(0L, "");
        if (TextUtils.isEmpty(path)) {
            return pr;
        }

        return pr;
    }
}
