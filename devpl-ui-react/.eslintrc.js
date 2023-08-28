module.exports = {
    env: {
      browser: true,
      commonjs: true,
      es2021: true,
    },
    extends: [
      "eslint:recommended",
      "plugin:react/recommended",
      "plugin:@typescript-eslint/recommended",
      "plugin:react-hooks/recommended",
    ],
    overrides: [],
    parser: "@typescript-eslint/parser",
    parserOptions: {
      ecmaVersion: "latest",
      sourceType: "module",
    },
    plugins: ["prettier", "react", "react-hooks", "@typescript-eslint"],
    /**
     * "off" 或 0 - 关闭规则
     * "warn" 或 1 - 开启规则，使用警告级别的错误：warn (不会导致程序退出),
     * "error" 或 2 - 开启规则，使用错误级别的错误：error (当被触发的时候，程序会退出)
     */
    rules: {
      "no-cond-assign": 2,
      "no-console": [
        "warn",
        {
          allow: ["log", "warn", "error", "info"],
        },
      ],
      // 禁止 function 定义中出现重名参数
      "no-dupe-args": 2,
      // 禁止对象字面量中出现重复的 key
      "no-dupe-keys": 2,
      // 禁止重复的 case 标签
      "no-duplicate-case": 2,
      // 禁止空语句块
      "no-empty": 2,
      // 禁止对 catch 子句的参数重新赋值
      "no-ex-assign": 2,
      // 禁止不必要的布尔转换
      "no-extra-boolean-cast": 2,
      //强制使用一致的缩进 第二个参数为 "tab" 时，会使用tab，
      // if while function 后面的{必须与if在同一行，java风格。
      "brace-style": [
        2,
        "1tbs",
        {
          allowSingleLine: true,
        },
      ],
      // 控制逗号前后的空格
      "comma-spacing": [
        2,
        {
          before: false,
          after: true,
        },
      ],
      //在数组或迭代器中验证JSX具有key属性
      "react/jsx-key": 2,
      "react-hooks/rules-of-hooks": "error",
      "react-hooks/exhaustive-deps": "warn",
      "react/no-this-in-sfc": 0,
      "react/prop-types": 0,
      "react/display-name": "off",
    },
  };
