package cn.tragoedia.bbs.utils;

public interface Constant {

    /**
     * 激活成功
     */
    int ACTIVATION_SUCCESS = 0;

    /**
     * 重复激活
     */
    int ACTIVATION_REPEAT = 1;

    /**
     * 激活失败
     */
    int ACTIVATION_FAILURE = 2;

    /**
     * 默认超时时间
     */
    int DEFAULT_EXPIRED_SECONDS = 3600 * 12; // 12小时

    /**
     * 记住状态超时时间
     */
    int REMEMBER_EXPIRED_SECONDS = 3600 * 24 * 30; // 1月
}
