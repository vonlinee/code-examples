ThisBuild / scalaVersion := "2.13.0"
ThisBuild / sbtVersion := "1.11.6"
ThisBuild / version := "0.1.0-SNAPSHOT"
lazy val root = (project in file("."))
  .settings(
    name := "sparkapp-scala"
  )

// Java 版本设置
javacOptions ++= Seq("-source", "1.8", "-target", "1.8")
scalacOptions += "-target:jvm-1.8"

// Spark 核心依赖
val sparkVersion = "3.3.1"
val hadoopVersion = "3.3.4"
libraryDependencies ++= Seq(
  // Provided 作用域表示这些依赖在运行时由集群提供, 如果是在本地直接运行，则需要去掉
  "org.apache.spark" %% "spark-core" % sparkVersion % Provided,
  "org.apache.spark" %% "spark-sql" % sparkVersion % Provided,
  "org.apache.spark" %% "spark-mllib" % sparkVersion % Provided,
  "org.apache.spark" %% "spark-streaming" % sparkVersion % Provided,

  // Hadoop 相关依赖
  "org.apache.hadoop" % "hadoop-common" % hadoopVersion % Provided,
  "org.apache.hadoop" % "hadoop-client" % hadoopVersion % Provided,
  "org.apache.hadoop" % "hadoop-hdfs" % hadoopVersion % Provided,
  "org.apache.hadoop" % "hadoop-aws" % hadoopVersion,

  // 其他常用依赖
  "org.scala-lang" % "scala-library" % scalaVersion.value,

  // 测试依赖
  "org.apache.spark" %% "spark-core" % sparkVersion % Test classifier "tests",
  "org.apache.spark" %% "spark-sql" % sparkVersion % Test classifier "tests",
  "org.scalatest" %% "scalatest" % "3.2.15" % Test
)

// 仓库设置
resolvers ++= Seq(
  "Apache Repository" at "https://repository.apache.org/content/repositories/releases/",
  "Maven Central" at "https://repo1.maven.org/maven2/",
  "Cloudera Repository" at "https://repository.cloudera.com/artifactory/cloudera-repos/"
)

// 打包设置
assembly / assemblyJarName := s"${name.value}-${version.value}.jar"
assembly / assemblyMergeStrategy := {
  case PathList("META-INF", xs@_*) => MergeStrategy.discard
  case x => MergeStrategy.first
}

// 并行执行设置
Test / parallelExecution := false

// 内存设置
run / fork := true
run / javaOptions ++= Seq(
  "-Xms512M",
  "-Xmx2G",
  "-XX:MaxPermSize=512M"
)