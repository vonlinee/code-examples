http://mybatis.org/generator/index.html

Github地址：https://github.com/mybatis/generator


generator-master
	core	mybatis-generator-core
	eclipse	 eclipse插件


只提取了core文件夹的部分

MyBatis Generator 基础扩展
https://www.jianshu.com/p/b6d981b25409

MyBatis Generator 本身在设计的时候就考虑到了基础的扩展能力，在MBG中提供了不少可以直接扩展的扩展点，了解这些扩展点对进一步理解MBG工作原理会有不少帮助，下面先简单描述一下MBG文档中关于扩展的部分内容：

MBG被设计成非常灵活，便于扩展的框架，在MBG中，所有的XML和JAVA代码的生成都是设计成DOM形式的，分别放在下面两个包中：

Java DOM在org.mybatis.generator.api.dom.java包中；
XML DOM在org.mybatis.generator.api.dom.xml包中；










