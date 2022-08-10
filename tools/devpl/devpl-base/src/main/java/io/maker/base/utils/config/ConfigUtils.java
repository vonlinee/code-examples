package io.maker.base.utils.config;

import com.google.common.util.concurrent.RateLimiter;

import java.util.concurrent.TimeUnit;

import io.maker.base.utils.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Jason Song(song_s@ctrip.com)
 */
public class ConfigUtils {

    private static final Logger logger = LoggerFactory.getLogger(ConfigUtils.class);
    private int refreshInterval = 5;
    private final TimeUnit refreshIntervalTimeUnit = TimeUnit.MINUTES;
    private int connectTimeout = 1000; //1 second
    private int readTimeout = 5000; //5 seconds
    private String cluster;
    private int loadConfigQPS = 2; //2 times per second
    private int longPollQPS = 2; //2 times per second
    //for on error retry
    private long onErrorRetryInterval = 1;//1 second
    private TimeUnit onErrorRetryIntervalTimeUnit = TimeUnit.SECONDS;//1 second
    //for typed config cache of parser result, e.g. integer, double, long, etc.
    private long maxConfigCacheSize = 500;//500 cache key
    private long configCacheExpireTime = 1;//1 minute
    private TimeUnit configCacheExpireTimeUnit = TimeUnit.MINUTES;//1 minute
    private long longPollingInitialDelayInMills = 2000;//2 seconds
    private boolean autoUpdateInjectedSpringProperties = true;
    private final RateLimiter warnLogRateLimiter;
    private boolean propertiesOrdered = false;
    private boolean propertyNamesCacheEnabled = false;
    private boolean propertyFileCacheEnabled = true;

    public ConfigUtils() {
        warnLogRateLimiter = RateLimiter.create(0.017); // 1 warning log output per minute
        initRefreshInterval();
        initConnectTimeout();
        initReadTimeout();
        initQPS();
        initMaxConfigCacheSize();
        initLongPollingInitialDelayInMills();
    }

    /**
     * Get the cluster name for the current application.
     *
     * @return the cluster name, or "default" if not specified
     */
    public String getCluster() {
        return cluster;
    }

    private void initConnectTimeout() {
        String customizedConnectTimeout = System.getProperty("apollo.connectTimeout");
        if (!Validator.isNullOrEmpty(customizedConnectTimeout)) {
            try {
                connectTimeout = Integer.parseInt(customizedConnectTimeout);
            } catch (Throwable ex) {
                logger.error("Config for apollo.connectTimeout is invalid: {}", customizedConnectTimeout);
            }
        }
    }

    public int getConnectTimeout() {
        return connectTimeout;
    }

    private void initReadTimeout() {
        String customizedReadTimeout = System.getProperty("apollo.readTimeout");
        if (!Validator.isNullOrEmpty(customizedReadTimeout)) {
            try {
                readTimeout = Integer.parseInt(customizedReadTimeout);
            } catch (Throwable ex) {
                logger.error("Config for apollo.readTimeout is invalid: {}", customizedReadTimeout);
            }
        }
    }

    public int getReadTimeout() {
        return readTimeout;
    }

    private void initRefreshInterval() {
        String customizedRefreshInterval = System.getProperty("apollo.refreshInterval");
        if (!Validator.isNullOrEmpty(customizedRefreshInterval)) {
            try {
                refreshInterval = Integer.parseInt(customizedRefreshInterval);
            } catch (Throwable ex) {
                logger.error("Config for apollo.refreshInterval is invalid: {}", customizedRefreshInterval);
            }
        }
    }

    public int getRefreshInterval() {
        return refreshInterval;
    }

    public TimeUnit getRefreshIntervalTimeUnit() {
        return refreshIntervalTimeUnit;
    }

    private void initQPS() {
        String customizedLoadConfigQPS = System.getProperty("apollo.loadConfigQPS");
        if (!Validator.isNullOrEmpty(customizedLoadConfigQPS)) {
            try {
                loadConfigQPS = Integer.parseInt(customizedLoadConfigQPS);
            } catch (Throwable ex) {
                logger.error("Config for apollo.loadConfigQPS is invalid: {}", customizedLoadConfigQPS);
            }
        }
        String customizedLongPollQPS = System.getProperty("apollo.longPollQPS");
        if (!Validator.isNullOrEmpty(customizedLongPollQPS)) {
            try {
                longPollQPS = Integer.parseInt(customizedLongPollQPS);
            } catch (Throwable ex) {
                logger.error("Config for apollo.longPollQPS is invalid: {}", customizedLongPollQPS);
            }
        }
    }

    public int getLoadConfigQPS() {
        return loadConfigQPS;
    }

    public int getLongPollQPS() {
        return longPollQPS;
    }

    public long getOnErrorRetryInterval() {
        return onErrorRetryInterval;
    }

    public TimeUnit getOnErrorRetryIntervalTimeUnit() {
        return onErrorRetryIntervalTimeUnit;
    }


    public boolean isOSWindows() {
        String osName = System.getProperty("os.name");
        if (Validator.isNullOrEmpty(osName)) {
            return false;
        }
        return osName.startsWith("Windows");
    }

    private void initMaxConfigCacheSize() {
        String customizedConfigCacheSize = System.getProperty("apollo.configCacheSize");
        if (!Validator.isNullOrEmpty(customizedConfigCacheSize)) {
            try {
                maxConfigCacheSize = Long.parseLong(customizedConfigCacheSize);
            } catch (Throwable ex) {
                logger.error("Config for apollo.configCacheSize is invalid: {}", customizedConfigCacheSize);
            }
        }
    }

    public long getMaxConfigCacheSize() {
        return maxConfigCacheSize;
    }

    public long getConfigCacheExpireTime() {
        return configCacheExpireTime;
    }

    public TimeUnit getConfigCacheExpireTimeUnit() {
        return configCacheExpireTimeUnit;
    }

    private void initLongPollingInitialDelayInMills() {
        String customizedLongPollingInitialDelay = System
                .getProperty("apollo.longPollingInitialDelayInMills");
        if (!Validator.isNullOrEmpty(customizedLongPollingInitialDelay)) {
            try {
                longPollingInitialDelayInMills = Long.parseLong(customizedLongPollingInitialDelay);
            } catch (Throwable ex) {
                logger.error("Config for apollo.longPollingInitialDelayInMills is invalid: {}",
                        customizedLongPollingInitialDelay);
            }
        }
    }

    public long getLongPollingInitialDelayInMills() {
        return longPollingInitialDelayInMills;
    }


    public boolean isAutoUpdateInjectedSpringPropertiesEnabled() {
        return autoUpdateInjectedSpringProperties;
    }

    public boolean isPropertiesOrderEnabled() {
        return propertiesOrdered;
    }

    public boolean isPropertyNamesCacheEnabled() {
        return propertyNamesCacheEnabled;
    }

    public boolean isPropertyFileCacheEnabled() {
        return propertyFileCacheEnabled;
    }


    public boolean isInLocalMode() {
        return false;
    }
}
