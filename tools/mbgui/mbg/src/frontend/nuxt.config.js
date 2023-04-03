// const pkg = require("./package");
const path = require('path');
const distDirectory = '../main/resources/public';

const contextPath = process.env.NODE_ENV === 'production' ? '' : '';

// https://github.com/microsoft/monaco-editor/tree/main/webpack-plugin
const MonacoWebpackPlugin = require('monaco-editor-webpack-plugin');

const bindQuery2Props = (route) => {
    return _.assign({}, route.query, route.params);
};

const setProps = (router) => {
    router.props = bindQuery2Props;
    if (router.children) {
        for (let i = 0; i < router.children.length; i++) {
            let child = router.children[i];
            setProps(child);
        }
    }
};

module.exports = {
    env: {
        contextPath: contextPath //资源引用路径前缀
    },
    mode: 'spa', // ssr: true,
    srcDir: 'src/',
    head: {
        script: [
            {
                src: contextPath + '/libs/jquery.min.js',
            }, {
                src: contextPath + '/libs/bootstrap/js/bootstrap.min.js',
            }, {
                src: contextPath + '/libs/adminlte/adminlte.min.js',
            }, {
                src: contextPath + '/libs/jquery.fullscreen-min.js',
            },
        ],
        link: [
            {
                rel: 'stylesheet',
                href: '//at.alicdn.com/t/font_1617194_0ttjsrqnukiq.css',
            },
        ],
    },
    router: {
        extendRoutes(routes, resolve) {
            for (let i = 0; i < routes.length; i++) {
                let element = routes[i];
                setProps(element);
            }
        },
        base: contextPath,
        scrollBehavior: function (to, from, savedPosition) {
            return {
                x: 0,
                y: 0,
            };
        },
    }, /*
   ** Customize the progress-bar color
   */
    loading: {
        color: '#fff',
    }, /*
   ** Global CSS
   */
    css: [//"@/assets/css/global.css",
        '@/static/libs/bootstrap/css/bootstrap.min.css',
        '@/static/libs/font-awesome/css/font-awesome.min.css',
        '@/static/libs/adminlte/AdminLTE.min.css',
    ], /*
   ** Plugins to load before mounting the App
   */
    plugins: [
        '@/plugins/element-ui.js',
        '@/plugins/axios.js',
        '@/plugins/components.js',
        '@/plugins/api.js',
        '@/plugins/request.js',
    ],
    generate: {
        /*
         ** 指定编译后文件的输出目录
         */
        dir: path.resolve(distDirectory),
    }, /*
   ** Build configuration
   */
    build: {
        babel: {
            presets({isServer}) {
                const targets = {
                    ie: '11',
                };
                return [
                    [
                        require.resolve('@nuxt/babel-preset-app'), {
                        targets,
                    },
                    ],
                ];
            },
            plugins: [
                [
                    'component', {
                    libraryName: 'element-ui',
                    styleLibraryName: 'theme-chalk'
                }
                ], [
                    '@babel/plugin-proposal-decorators', {
                        // "legacy": true, 两个配置只能用一个
                        'version': 'legacy'
                    }
                ] // ['@babel/plugin-proposal-private-methods', { 'loose': true }],
                // ['@babel/plugin-proposal-private-property-in-object', { 'loose':
                // true }], ['@babel/plugin-proposal-class-properties', { 'loose': true
                // }],
            ]
        },
        extend: function (config, ctx) {
            //source map设置
            if (ctx.isDev && ctx.isClient) {
                config.devtool = 'cheap-module-eval-source-map';
            } else {
                config.devtool = 'cheap-module-source-map';
            }
            // 路径映射
            config.resolve.alias['@'] = path.resolve(__dirname, 'src')
        },
        plugins: [
            new MonacoWebpackPlugin(// Languages are loaded on demand at runtime
                {languages: ['json', 'javascript', 'html', 'xml']}),
        ]
    }, // 开发服务器代理设置
    proxy: {
        '/api': {
            target: 'http://localhost:8068/',
            changeOrigin: true
        }
    }
};
