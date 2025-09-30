@echo off
setlocal enabledelayedexpansion
chcp 65001
set MAIN_CLASS_NAME=%1

:: 检查主类名
IF "%MAIN_CLASS_NAME%"=="" (
@REM 第一个参数
    if not "%~1"=="" (
        set "MAIN_CLASS_NAME=%~1"
    ) else (
        echo ERROR: mainClassName is not set
        echo.
        echo Solutions:
        echo   1. set MAIN_CLASS_NAME=your.package.MainClass
        echo   2. %~nx0 your.package.MainClass
        exit /b 1
    )
)


REM 可配置参数
set PROJECT_ROOT=%~dp0
set MAVEN_OPTS=-DskipTests
set SPARK_MASTER=yarn
@REM 支持传参指定运行驱动类
set MAIN_CLASS=spark.SparkApplication
set APP_NAME=%MAIN_CLASS_NAME%

REM Spark History Server 配置
set SPARK_HISTORY_SERVER_ENABLED=true
set SPARK_EVENTLOG_ENABLED=true
set SPARK_EVENTLOG_DIR=hdfs://localhost:8020/spark-logs
set SPARK_HISTORY_FS_LOGDIR=hdfs://localhost:8020/spark-logs
set SPARK_HISTORY_UI_PORT=18080

REM Spark 其他配置
set SPARK_DRIVER_MEMORY=2g
set SPARK_EXECUTOR_MEMORY=2g
set SPARK_EXECUTOR_CORES=2
set SPARK_NUM_EXECUTORS=2

REM 显示配置信息
echo 配置信息:
echo   项目目录: %PROJECT_ROOT%
echo   Spark Master: %SPARK_MASTER%
echo   主类: %MAIN_CLASS%
echo   应用名称: %APP_NAME%
echo   History Server: %SPARK_HISTORY_SERVER_ENABLED%
echo   事件日志目录: %SPARK_EVENTLOG_DIR%
echo.

REM 切换到项目目录
cd /d "%PROJECT_ROOT%"

REM 执行Maven打包
echo [步骤1] 执行Maven打包...
call mvn clean package %MAVEN_OPTS%

if %errorlevel% neq 0 (
    echo [错误] Maven打包失败，退出代码: %errorlevel%
    goto :error
)

REM 查找最新的jar包（排除sources和original包）
echo.
echo [步骤2] 查找生成的jar包...
set JAR_FILE=
for /f "delims=" %%i in ('dir /s /b /o-d target\*.jar') do (
    echo 找到文件: %%i
    echo %%i | findstr /v "sources" | findstr /v "original" >nul
    if !errorlevel! equ 0 (
        if not defined JAR_FILE (
            set JAR_FILE=%%i
            echo [选定] 使用jar包: !JAR_FILE!
        )
    )
)

if "%JAR_FILE%"=="" (
    echo [错误] 未找到可用的jar包!
    goto :error
)

REM 显示jar包信息
for %%I in ("%JAR_FILE%") do (
    set JAR_SIZE=%%~zI
    set JAR_DIR=%%~dpI
)
echo Jar包大小: %JAR_SIZE% 字节
echo Jar包目录: %JAR_DIR%

REM 构建Spark提交命令
echo.
echo [Step3] 构建Spark提交命令...

set SPARK_SUBMIT_CMD=spark-submit
set SPARK_SUBMIT_CMD=!SPARK_SUBMIT_CMD! --master %SPARK_MASTER%
set SPARK_SUBMIT_CMD=!SPARK_SUBMIT_CMD! --class %MAIN_CLASS%
set SPARK_SUBMIT_CMD=!SPARK_SUBMIT_CMD! --name "%APP_NAME%"

REM 添加资源配置
set SPARK_SUBMIT_CMD=!SPARK_SUBMIT_CMD! --driver-memory %SPARK_DRIVER_MEMORY%
set SPARK_SUBMIT_CMD=!SPARK_SUBMIT_CMD! --executor-memory %SPARK_EXECUTOR_MEMORY%
set SPARK_SUBMIT_CMD=!SPARK_SUBMIT_CMD! --executor-cores %SPARK_EXECUTOR_CORES%
set SPARK_SUBMIT_CMD=!SPARK_SUBMIT_CMD! --num-executors %SPARK_NUM_EXECUTORS%

REM 添加History Server配置
if "%SPARK_HISTORY_SERVER_ENABLED%"=="true" (
    echo [信息] 启用Spark History Server配置

    set SPARK_SUBMIT_CMD=!SPARK_SUBMIT_CMD! --conf spark.eventLog.enabled=%SPARK_EVENTLOG_ENABLED%
    set SPARK_SUBMIT_CMD=!SPARK_SUBMIT_CMD! --conf spark.eventLog.dir=%SPARK_EVENTLOG_DIR%
    set SPARK_SUBMIT_CMD=!SPARK_SUBMIT_CMD! --conf spark.history.fs.logDirectory=%SPARK_HISTORY_FS_LOGDIR%

    REM 其他有用的History Server相关配置
    set SPARK_SUBMIT_CMD=!SPARK_SUBMIT_CMD! --conf spark.history.ui.port=%SPARK_HISTORY_UI_PORT%
    set SPARK_SUBMIT_CMD=!SPARK_SUBMIT_CMD! --conf spark.history.fs.update.interval=10s
    set SPARK_SUBMIT_CMD=!SPARK_SUBMIT_CMD! --conf spark.history.retainedApplications=50
    set SPARK_SUBMIT_CMD=!SPARK_SUBMIT_CMD! --conf spark.history.fs.cleaner.enabled=true
    set SPARK_SUBMIT_CMD=!SPARK_SUBMIT_CMD! --conf spark.history.fs.cleaner.interval=1d
    set SPARK_SUBMIT_CMD=!SPARK_SUBMIT_CMD! --conf spark.history.fs.cleaner.maxAge=7d

    echo [信息] History Server UI地址: http://history-server-host:%SPARK_HISTORY_UI_PORT%
)

REM 添加JAR文件
set SPARK_SUBMIT_CMD=!SPARK_SUBMIT_CMD! "%JAR_FILE%" %MAIN_CLASS_NAME%

REM 显示完整命令
echo.
echo 完整Spark提交命令:
echo !SPARK_SUBMIT_CMD!
echo.

REM 提交Spark任务
echo [步骤4] 提交Spark任务...
!SPARK_SUBMIT_CMD!

if %errorlevel% neq 0 (
    echo [错误] Spark任务提交失败，退出代码: %errorlevel%
    goto :error
)

REM 显示History Server相关信息
if "%SPARK_HISTORY_SERVER_ENABLED%"=="true" (
    echo ========================================
    echo Spark History Server 信息
    echo ========================================
    echo 任务提交成功！您可以在以下地址查看任务历史：
    echo UI地址: http://localhost:%SPARK_HISTORY_UI_PORT%
    echo 事件日志目录: %SPARK_EVENTLOG_DIR%
)

echo.
echo [成功] 所有步骤执行完成!
goto :end

:error
echo.
echo [失败] 执行过程中出现错误
pause
exit /b 1

:end
pause