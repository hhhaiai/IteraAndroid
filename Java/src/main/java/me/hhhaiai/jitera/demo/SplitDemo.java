//package me.hhhaiai.jitera.demo;
//
//import java.util.Arrays;
//
///**
// * @Copyright © 2021 sanbo Inc. All rights reserved.
// * @Description: TODO
// * @Version: 1.0
// * @Create: 2021/11/25 2:55 下午
// * @author: sanbo
// */
//public class SplitDemo {
//
//    public static void main(String[] args) {
////        String[] s1 = new String[]{"com.eg.android.AlipayGphone", "files", "Sandbox", "B_d407656e84c3c5a2", "multimedia", "623039e4445e6f7d61e132b6c613822d", "38"};
////        String[] s1 = new String[]{"com.eg.android.AlipayGphone", "files", "nebulaH5App"};
//        String[] s1 = new String[]{"com.eg.android.AlipayGphone", "files", "Sandbox", "B_d407656e84c3c5a2", "multimedia", "623039e4445e6f7d61e132b6c613822d", "3d"};
////        String b[] = Arrays.copyOfRange(s1, 2, 6);// 截取索引2（包括）到索引6（不包括）的元素
////        System.out.println("1:" + getFullPath(1, s1));
//        System.out.println("1:" + getFullPath(1, s1));
//        System.out.println("2:" + getFullPath(2, s1));
//        System.out.println("3:" + getFullPath(3, s1));
//        System.out.println("4:" + getFullPath(4, s1));
//        System.out.println("5:" + getFullPath(5, s1));
//    }
//
//
//    private static String getFullPath(int currentPosition, String[] pathItems) {
//        if (currentPosition == 1) {
//            return pathItems[currentPosition];
//        }
//        String[] tempSs = Arrays.copyOfRange(pathItems, 1, currentPosition+1);
//        StringBuilder sb = new StringBuilder();
//        for (int i = 0; i < tempSs.length; i++) {
//            sb.append(tempSs[i]).append("/");
//        }
//        String result= sb.toString();
//        if (result.endsWith("/")){
//            return result.substring(0,result.length()-1);
//        }else {
//            return result;
//        }
//    }
//}
