

# 基本格式



# 注释



rem

在批处理文件中，单行注释可以使用 `REM` 或者 `::`。不过，批处理文件没有原生的多行注释语法，但可以使用一些技巧来实现多行注释。

```bat
@echo off
REM This is a single line comment
:: This is also a single line comment
echo Hello, World!
```

## 多行注释技巧

可以使用 `goto` 语句结合标签，在脚本中跳过一段代码来实现多行注释

```bat
@echo off

goto skip_comments

:comment_block
REM This is a multi-line comment
REM You can write anything here
REM It will be ignored by the script

:skip_comments
echo This line will execute.
```

## 使用说明

1. **单行注释**: 直接使用 `REM` 或 `::` 开头。
2. **多行注释**: 使用 `goto` 跳过注释块，确保在需要的地方添加一个标签（如 `:skip_comments`）来跳过这些行。

## 注意事项

- 使用 `::` 作为注释的方式在某些情况下可能会导致问题，特别是在某些代码行后，因此更推荐使用 `REM`。
- 确保跳转标签不被其他代码引用，避免影响脚本的执行。







# 定义临时变量

在批处理文件中，可以使用 set 命令定义临时变量。临时变量只在当前命令提示符会话中有效，关闭窗口后会消失。



```bat
@echo off
setlocal

rem 定义临时变量
set "my_var=Hello, World!"

rem 使用临时变量
echo The value of my_var is: %my_var%

rem 进行一些操作
set /a my_number=5 + 10
echo The result of the addition is: %my_number%

rem 清除临时变量（可选）
set "my_var="
set "my_number="

endlocal
```



`setlocal` 命令用于开始一个局部环境，变量在这个环境中是临时的。

使用 `set "variable_name=value"` 语法定义变量，避免了空格问题。

`%variable_name%` 用于引用变量的值。

`endlocal` 命令用于结束局部环境，清除所有在此环境中定义的变量。



# 判断是否定义环境变量

在批处理文件中，可以使用 `if defined` 语句来判断某个环境变量是否已定义。以下是一个示例，展示如何检查环境变量是否存在

```bat
@echo off
setlocal

rem 定义一个环境变量（可选）
set "MY_VAR=Hello, World!"

rem 检查环境变量是否已定义
if defined MY_VAR (
    echo MY_VAR is defined: %MY_VAR%
) else (
    echo MY_VAR is not defined.
)

rem 检查另一个未定义的变量
if defined UNDEFINED_VAR (
    echo UNDEFINED_VAR is defined.
) else (
    echo UNDEFINED_VAR is not defined.
)

endlocal
```



# 常用命令



## setlocal



```cmd
setlocal enabledelayedexpansion
```





## setx



添加到用户环境变量

```cmd
setx PATH "%PATH%;C:\your\new\path"
```

添加到系统环境变量

要添加到系统的 `PATH`，请确保以管理员身份运行命令提示符：

```cmd
setx PATH "%PATH%;C:\your\new\path" /M
```

注意事项

- 添加的新路径将在新的命令提示符会话中生效，当前会话不会立即反映更改。
- 确保路径中没有多余的空格或错误的字符。











