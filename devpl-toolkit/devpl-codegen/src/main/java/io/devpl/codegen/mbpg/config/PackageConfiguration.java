package io.devpl.codegen.mbpg.config;

import io.devpl.codegen.mbpg.util.StringUtils;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * 包相关的配置项
 */
public class PackageConfiguration {

    /**
     * 父包名。如果为空，将下面子包名必须写全部， 否则就只需写子包名
     */
    private String parent = "com.baomidou";

    /**
     * 父包模块名
     */
    private String moduleName = "";

    /**
     * Entity包名
     */
    private String entity = "entity";

    /**
     * Service包名
     */
    private String service = "service";

    /**
     * Service Impl包名
     */
    private String serviceImpl = "service.impl";

    /**
     * Mapper包名
     */
    private String mapper = "mapper";

    /**
     * Mapper XML包名
     */
    private String xml = "mapper.xml";

    /**
     * Controller包名
     */
    private String controller = "controller";

    /**
     * 路径配置信息
     */
    private Map<OutputFile, String> pathInfo;

    /**
     * 包配置信息
     *
     * @since 3.5.0
     */
    private final Map<String, String> packageInfo = new HashMap<>();

    /**
     * 父包名
     */

    public String getParent() {
        if (StringUtils.isNotBlank(moduleName)) {
            return parent + "." + moduleName;
        }
        return parent;
    }

    /**
     * 连接父子包名
     *
     * @param subPackage 子包名
     * @return 连接后的包名
     */

    public String joinPackage(String subPackage) {
        String parent = getParent();
        return StringUtils.isBlank(parent) ? subPackage : (parent + "." + subPackage);
    }

    /**
     * 获取包配置信息
     *
     * @return 包配置信息， 返回不可更改的map
     * @since 3.5.0
     */
    public Map<String, String> getPackageInfo() {
        if (packageInfo.isEmpty()) {
            packageInfo.put(ConstVal.MODULE_NAME, this.getModuleName());
            packageInfo.put(ConstVal.ENTITY, this.joinPackage(this.getEntity()));
            packageInfo.put(ConstVal.MAPPER, this.joinPackage(this.getMapper()));
            packageInfo.put(ConstVal.XML, this.joinPackage(this.getXml()));
            packageInfo.put(ConstVal.SERVICE, this.joinPackage(this.getService()));
            packageInfo.put(ConstVal.SERVICE_IMPL, this.joinPackage(this.getServiceImpl()));
            packageInfo.put(ConstVal.CONTROLLER, this.joinPackage(this.getController()));
            packageInfo.put(ConstVal.PARENT, this.getParent());
        }
        return Collections.unmodifiableMap(this.packageInfo);
    }

    /**
     * 获取包配置信息
     *
     * @param module 模块
     * @return 配置信息
     * @since 3.5.0
     */
    public String getPackageInfo(String module) {
        return getPackageInfo().get(module);
    }

    public String getModuleName() {
        return moduleName;
    }

    public String getEntity() {
        return entity;
    }

    public String getService() {
        return service;
    }

    public String getServiceImpl() {
        return serviceImpl;
    }

    public String getMapper() {
        return mapper;
    }

    public String getXml() {
        return xml;
    }

    public String getController() {
        return controller;
    }

    public Map<OutputFile, String> getPathInfo() {
        return pathInfo;
    }

    /**
     * 构建者
     *
     * @author nieqiurong
     * @since 3.5.0
     */
    public static class Builder {

        private final PackageConfiguration packageConfig;

        public Builder() {
            this.packageConfig = new PackageConfiguration();
        }

        public Builder(String parent, String moduleName) {
            this();
            this.packageConfig.parent = parent;
            this.packageConfig.moduleName = moduleName;
        }

        /**
         * 指定父包名
         *
         * @param parent 父包名
         * @return this
         */
        public Builder parent(String parent) {
            this.packageConfig.parent = parent;
            return this;
        }

        /**
         * 指定模块名称
         *
         * @param moduleName 模块名
         * @return this
         */
        public Builder moduleName(String moduleName) {
            this.packageConfig.moduleName = moduleName;
            return this;
        }

        /**
         * 指定实体包名，不指定默认为 entity
         *
         * @param entity 实体包名
         * @return this
         */
        public Builder entity(String entity) {
            this.packageConfig.entity = entity;
            return this;
        }

        /**
         * 指定service接口包名
         *
         * @param service service包名
         * @return this
         */
        public Builder service(String service) {
            this.packageConfig.service = service;
            return this;
        }

        /**
         * service实现类包名
         *
         * @param serviceImpl service实现类包名
         * @return this
         */
        public Builder serviceImpl(String serviceImpl) {
            this.packageConfig.serviceImpl = serviceImpl;
            return this;
        }

        /**
         * 指定mapper接口包名
         *
         * @param mapper mapper包名
         * @return this
         */
        public Builder mapper(String mapper) {
            this.packageConfig.mapper = mapper;
            return this;
        }

        /**
         * 指定xml包名
         *
         * @param xml xml包名
         * @return this
         */
        public Builder xml(String xml) {
            this.packageConfig.xml = xml;
            return this;
        }

        /**
         * 指定控制器包名
         *
         * @param controller 控制器包名
         * @return this
         */
        public Builder controller(String controller) {
            this.packageConfig.controller = controller;
            return this;
        }

        /**
         * 路径配置信息
         *
         * @param pathInfo 路径配置信息
         * @return this
         */
        public Builder pathInfo(Map<OutputFile, String> pathInfo) {
            this.packageConfig.pathInfo = pathInfo;
            return this;
        }

        /**
         * 连接父子包名
         *
         * @param subPackage 子包名
         * @return 连接后的包名
         */

        public String joinPackage(String subPackage) {
            return this.packageConfig.joinPackage(subPackage);
        }

        /**
         * 构建包配置对象
         * <p>当指定{@link #parent(String)} 与 {@link #moduleName(String)}时,其他模块名字会加上这两个作为前缀</p>
         * <p>
         * 例如:
         * <p>当设置 {@link #parent(String)},那么entity的配置为 {@link #getParent()}.{@link #getEntity()}</p>
         * <p>当设置 {@link #parent(String)}与{@link #moduleName(String)},那么entity的配置为 {@link #getParent()}.{@link #getModuleName()}.{@link #getEntity()} </p>
         * </p>
         *
         * @return 包配置对象
         */
        public PackageConfiguration build() {
            return this.packageConfig;
        }
    }
}
