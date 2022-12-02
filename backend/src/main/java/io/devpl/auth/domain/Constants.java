package io.devpl.auth.domain;

/**
 * 常量定义
 */
public class Constants {

    /**
     * 配置文件参数名
     */
    public static final class Properties {
        // 基础平台地址
        public static final String BASE_ADDR = "config.base-addr";
        //小程序地址
        public static final String APPLET_ADDR = "applet.addr";
    }

    /**
     * 时间格式
     */
    public static final class DateFormat {
        public static final String DATE_LINE = "yyyy-MM-dd";
        public static final String DATE_NO_LINE = "yyyyMMdd";
        public static final String TIME = "HH:mm:ss";
        public static final String DATE_TIME = "yyyy-MM-dd HH:mm:ss";
        public static final String HOUR_MIN = "HH:mm";
        public static final String DATE_HOUR_MIN = "yyyy-MM-dd HH:mm";
        public static final String CRON_DATE = "ss mm HH dd MM ? yyyy";
        public static final String CRON_TIME = "ss mm HH * * ?";
    }

    /**
     * 系统id常量
     */
    public static final class System {
        //本系统锁控
        public static final int LOCAL_LOCK = 170;
        //本系统id
        public static final String LOCAL_SYSTEM_ID = "E51";
        //中间件系统id
        public static final String MQ_SYSTEM_ID = "E80";
        //数字化系统id
        public static final String DIGITAL_SYSTEM_ID = "E01";

    }

    /**
     * 基础平台接口
     */
    public static final class Base {
        // 用户管理（令牌认证、获取在线用户信息、登出系统）
        public static final String USER_MGR = "/UserMgr/Login/Api/Login.ashx";

        // 获取教育局领导列表
        public static final String GET_EDU_LEADER = "/UserMgr/UserInfoMgr/WS/Service_UserInfo.asmx/WS_UserMgr_G_GetEduLeader";

        // 获取管理员信息
        public static final String GET_ADMIN = "/UserMgr/UserInfoMgr/WS/Service_UserInfo.asmx/WS_UserMgr_G_GetAdmin";

        // 根据用户ID获取该身份详情
        // 校端
        public static final String GET_IDENTITY_TYPE_ID = "/UserMgr/PowerMgr/GetIdentityTypeByUserID";
        // 局端 获取登录用户的默认身份标签（单一身份）
        public static final String GET_IDENTITY_BY_ID = "/BaseApi/Global/GetUserBaseIdentityInfo";

        // 根据身份代码获取该身份详情
        public static final String GET_IDENTITY_TYPE_CODE = "/UserMgr/PowerMgr/GetIdentityTypeByCode";
        //权限校验
        public static final String GET_VALIDATE_IDENTITY = "/UserMgr/PowerMgr/ValidateIdentity";
        //获取子系统地址
        public static final String GET_SUB_SYSTEM = "/BaseApi/Global/GetSubSystemsMainServerBySubjectID";

        //获取学校基本信息
        public static final String GET_SCHOOL = "/BaseApi/SysMgr/Setting/GetSchoolsInfo";
        //获取用户信息
        public static final String GET_USER = "/BaseApi/UserMgr/UserInfoMgr/SearchUserByIDsOrName";
        //获取学生信息
        public static final String GET_STUDENT = "/BaseApi/UserMgr/UserInfoMgr/GetStudent";
        //获取对应学生、班级的班主任信息
        public static final String GET_CLASS_MASTER = "/BaseApi/UserMgr/UserInfoMgr/GetGanger";
        //获取全体老师信息
        public static final String GET_TEACHER = "/BaseApi/UserMgr/UserInfoMgr/GetTeacher";
        //获取全体领导信息
        public static final String GET_LEADER = "/BaseApi/UserMgr/UserInfoMgr/GetSchoolLeader";
        //获取全体领导信息
        // public static final String GET_ADMIN = "/BaseApi/UserMgr/UserInfoMgr/GetAdmin";
        //获取年级班级信息
        public static final String GET_GRADE_CLASS = "/UserMgr/UserInfoMgr/GetGradeClassTree";
        //获取班级信息
        public static final String GET_CLASS = "/BaseApi/UserMgr/UserInfoMgr/GetClass";
        //获取年级信息
        public static final String GET_GRADE = "/BaseApi/UserMgr/UserInfoMgr/GetGrade";
        //获取当前学期信息
        public static final String GET_CURRENT_TERM = "/BaseApi/SysMgr/Setting/GetCurrentTermInfo";
        //获取历史学期信息
        public static final String GET_HISTORY_TERM = "/BaseApi/SysMgr/Setting/GetHistoryTermInfoList";
        //获取产品基本信息
        public static final String GET_PRODUCT_INFO = "/BaseApi/Global/GetBaseProductsInfo";
    }
}
