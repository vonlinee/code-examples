@echo off
setlocal enabledelayedexpansion


@REM hadoop 安装目录

echo %HADOOP_HOME%

@REM if defined HADOOP_HOME (
@REM     echo HADOOP_HOME is alread defined, %HADOOP_HOME%
@REM ) else (
@REM     setx HADOOP_HOME C:\hadoop
@REM )
