package com.hsy.util;

public class SystemContext {
    // 保存项目部署的路径,用于文件上传
    private static String realPath;

    // 因为boot每次运行才会把本地上传到服务器,而每次运行之前服务器内上传的数据就消失了,所以为了实时显示上传的图片,我们再两个地方都保存一下
    private static String serverPath;
    // 项目路径
    private static String contentPath;

    // 默认图片宽高
    private static int IMG_WIDTH = 100;
    private static int IMG_HEIGHT = 100;

    public static String getRealPath() {
        return realPath;
    }

    public static void setRealPath(String realPath) {
        SystemContext.realPath = realPath;
    }

    public static String getServerPath() {
        return serverPath;
    }

    public static void setServerPath(String serverPath) {
        SystemContext.serverPath = serverPath;
    }

    public static String getContentPath() {
        return contentPath;
    }

    public static void setContentPath(String contentPath) {
        SystemContext.contentPath = contentPath;
    }

    public static int getIMG_WIDTH() {
        return IMG_WIDTH;
    }

    public static void setIMG_WIDTH(int iMG_WIDTH) {
        IMG_WIDTH = iMG_WIDTH;
    }

    public static int getIMG_HEIGHT() {
        return IMG_HEIGHT;
    }

    public static void setIMG_HEIGHT(int iMG_HEIGHT) {
        IMG_HEIGHT = iMG_HEIGHT;
    }

}
