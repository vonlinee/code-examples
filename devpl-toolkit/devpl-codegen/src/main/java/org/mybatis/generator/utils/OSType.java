package org.mybatis.generator.utils;

/**
 * 操作系统类型枚举
 */
public enum OSType {

    ANY("any"),
    LINUX("Linux"),
    MAC_OS("Mac OS"),
    MAC_OS_X("Mac OS X"),
    WINDOWS("Windows"),
    OS2("OS/2"),
    SOLARIS("Solaris"),
    SUN_OS("SunOS"),
    MPEiX("MPE/iX"),
    HP_UX("HP-UX"),
    AIX("AIX"),
    OS390("OS/390"),
    FREEBSD("FreeBSD"),

    /**
     * IRIX是由硅谷图形公司（Silicon Graphics Inc.一般用简称：SGI，美国图形工作站生产厂商）以System V与BSD延伸程序为基础所发展成的UNIX操作系统
     */
    IRIX("Irix"),
    DIGITAL_UNIX("Digital Unix"),
    NETWARE_411("NetWare"),
    OSF1("OSF1"),
    OPEN_VMS("OpenVMS"),
    OTHERS("Others");

    OSType(String osName) {
        this.osName = osName;
    }

    public static final String CURRENT_OS_NAME = System.getProperty("os.name").toLowerCase();

    private final String osName;

    public static boolean isLinux() {
        return CURRENT_OS_NAME.contains("linux");
    }

    public static boolean isMacOS() {
        return CURRENT_OS_NAME.contains("mac") && CURRENT_OS_NAME.indexOf("os") > 0 && !CURRENT_OS_NAME.contains("x");
    }

    public static boolean isMacOSX() {
        return CURRENT_OS_NAME.contains("mac") && CURRENT_OS_NAME.indexOf("os") > 0 && CURRENT_OS_NAME.indexOf("x") > 0;
    }

    public static boolean isWindows() {
        return CURRENT_OS_NAME.contains("windows");
    }

    public static boolean isOS2() {
        return CURRENT_OS_NAME.contains("os/2");
    }

    public static boolean isSolaris() {
        return CURRENT_OS_NAME.contains("solaris");
    }

    public static boolean isSunOs() {
        return CURRENT_OS_NAME.contains("sunos");
    }

    public static boolean isMPEiX() {
        return CURRENT_OS_NAME.contains("mpe/ix");
    }

    public static boolean isHPUX() {
        return CURRENT_OS_NAME.contains("hp-ux");
    }

    public static boolean isAix() {
        return CURRENT_OS_NAME.contains("aix");
    }

    public static boolean isOS390() {
        return CURRENT_OS_NAME.contains("os/390");
    }

    public static boolean isFreeBSD() {
        return CURRENT_OS_NAME.contains("freebsd");
    }

    public static boolean isIrix() {
        return CURRENT_OS_NAME.contains("irix");
    }

    public static boolean isDigitalUnix() {
        return CURRENT_OS_NAME.contains("digital") && CURRENT_OS_NAME.indexOf("unix") > 0;
    }

    /**
     * Netware是具有多任务、多用户的网络操作系统，一般适用于各种工作站操作的系统
     */
    public static boolean isNetWare() {
        return CURRENT_OS_NAME.contains("netware");
    }

    public static boolean isOSF1() {
        return CURRENT_OS_NAME.contains("osf1");
    }

    public static boolean isOpenVMS() {
        return CURRENT_OS_NAME.contains("openvms");
    }

    /**
     * 获取当前操作系统类型
     * @return 当前操作系统类型
     */
    public static OSType localOSType() {
        if (isAix()) return OSType.AIX;
        if (isDigitalUnix()) return OSType.DIGITAL_UNIX;
        if (isFreeBSD()) return OSType.FREEBSD;
        if (isHPUX()) return OSType.HP_UX;
        if (isIrix()) return OSType.IRIX;
        if (isLinux()) return OSType.LINUX;
        if (isMacOS()) return OSType.MAC_OS;
        if (isMacOSX()) return OSType.MAC_OS_X;
        if (isMPEiX()) return OSType.MPEiX;
        if (isNetWare()) return OSType.NETWARE_411;
        if (isOpenVMS()) return OSType.OPEN_VMS;
        if (isOS2()) return OSType.OS2;
        if (isOS390()) return OSType.OS390;
        if (isOSF1()) return OSType.OSF1;
        if (isSolaris()) return OSType.SOLARIS;
        if (isSunOs()) return OSType.SUN_OS;
        if (isWindows()) return OSType.WINDOWS;
        return OSType.OTHERS;
    }

    public String getOSName() {
        return osName;
    }
}