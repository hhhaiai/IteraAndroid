package me.hhhaiai.jitera.utils;

public class TextUtils {

    /**
     * Returns true if the string is null or 0-length.
     *
     * @param str the string to be examined
     * @return true if str is null or zero length
     */
    public static boolean isEmpty(CharSequence str) {
        return str == null || str.length() == 0;
    }


    /**
     * 处理文件路径含空格的情况
     *
     * @param text
     * @return
     */
    public static String replaceSpaceForFilePath(String text) {
        if (text.contains(" ")) {
            text = text.replaceAll(" ", "\\\\ ");
        }
        return text;
    }
}
