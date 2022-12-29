package org.mybatis.generator.utils;

public class OSinfo {

    private static final String OS_NAME = System.getProperty("os.name").toLowerCase();

    private static final OSinfo _instance = new OSinfo();

    private EPlatform platform;

    private OSinfo() {
    }

    public static boolean isLinux() {
        return OS_NAME.contains("linux");
    }

    public static boolean isMacOS() {
        return OS_NAME.contains("mac") && OS_NAME.indexOf("os") > 0 && !OS_NAME.contains("x");
    }

    public static boolean isMacOSX() {

        return OS_NAME.indexOf("mac") >= 0 && OS_NAME.indexOf("os") > 0 && OS_NAME.indexOf("x") > 0;
    }

    public static boolean isWindows() {
        return OS_NAME.contains("windows");
    }

    public static boolean isOS2() {

        return OS_NAME.indexOf("os/2") >= 0;
    }

    public static boolean isSolaris() {

        return OS_NAME.indexOf("solaris") >= 0;
    }

    public static boolean isSunOS() {

        return OS_NAME.indexOf("sunos") >= 0;
    }

    public static boolean isMPEiX() {

        return OS_NAME.indexOf("mpe/ix") >= 0;
    }

    public static boolean isHPUX() {

        return OS_NAME.indexOf("hp-ux") >= 0;
    }

    public static boolean isAix() {

        return OS_NAME.indexOf("aix") >= 0;
    }

    public static boolean isOS390() {
        return OS_NAME.indexOf("os/390") >= 0;
    }

    public static boolean isFreeBSD() {
        return OS_NAME.indexOf("freebsd") >= 0;
    }

    public static boolean isIrix() {
        return OS_NAME.indexOf("irix") >= 0;
    }

    public static boolean isDigitalUnix() {
        return OS_NAME.indexOf("digital") >= 0 && OS_NAME.indexOf("unix") > 0;
    }

    public static boolean isNetWare() {
        return OS_NAME.indexOf("netware") >= 0;
    }

    public static boolean isOSF1() {
        return OS_NAME.indexOf("osf1") >= 0;
    }

    public static boolean isOpenVMS() {
        return OS_NAME.indexOf("openvms") >= 0;
    }

    /**
     * 获取操作系统名字
     * @return 操作系统名
     */
    public static EPlatform getOSname() {
        if (isAix()) {
            _instance.platform = EPlatform.AIX;
        } else if (isDigitalUnix()) {
            _instance.platform = EPlatform.Digital_Unix;
        } else if (isFreeBSD()) {
            _instance.platform = EPlatform.FreeBSD;
        } else if (isHPUX()) {
            _instance.platform = EPlatform.HP_UX;
        } else if (isIrix()) {
            _instance.platform = EPlatform.Irix;
        } else if (isLinux()) {
            _instance.platform = EPlatform.Linux;
        } else if (isMacOS()) {
            _instance.platform = EPlatform.Mac_OS;
        } else if (isMacOSX()) {
            _instance.platform = EPlatform.Mac_OS_X;
        } else if (isMPEiX()) {
            _instance.platform = EPlatform.MPEiX;
        } else if (isNetWare()) {
            _instance.platform = EPlatform.NetWare_411;
        } else if (isOpenVMS()) {
            _instance.platform = EPlatform.OpenVMS;
        } else if (isOS2()) {
            _instance.platform = EPlatform.OS2;
        } else if (isOS390()) {
            _instance.platform = EPlatform.OS390;
        } else if (isOSF1()) {
            _instance.platform = EPlatform.OSF1;
        } else if (isSolaris()) {
            _instance.platform = EPlatform.Solaris;
        } else if (isSunOS()) {
            _instance.platform = EPlatform.SunOS;
        } else if (isWindows()) {
            _instance.platform = EPlatform.Windows;
        } else {
            _instance.platform = EPlatform.Others;
        }
        return _instance.platform;
    }
}
