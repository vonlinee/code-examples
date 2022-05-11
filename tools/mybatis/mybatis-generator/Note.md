



MyBatis Generator 本身在设计的时候就考虑到了基础的扩展能力，在MBG中提供了不少可以直接扩展的扩展点，了解这些扩展点对进一步理解MBG工作原理会有不少帮助，下面先简单描述一下MBG文档中关于扩展的部分内容：

# 扩展MyBatis Generator

MBG被设计成非常灵活，便于扩展的框架，在MBG中，所有的XML和JAVA代码的生成都是设计成DOM形式的，分别放在下面两个包中：

- Java DOM在org.mybatis.generator.api.dom.java包中；
- XML DOM在org.mybatis.generator.api.dom.xml包中；

简单点说，就是MBG默认的生成代码的方式，并不是使用类似FreeMarker或者Velocity这样的模板生成代码，而是完全把要生成的代码（XML DOM很好理解，特别是Java）代码，封装成了一个一个的类似DOM的结构，然后根据封装好的这个DOM结构，来生成Java和XML代码；这样做很难把所有的结构都考虑在内，但是对于生成代码来说，基本足够了。同时，MyBatis还提供了很多扩展点，适合各种级别的扩展。

扩展MBG一般有两种方式，一种方式就是使用继承MBG中关键类来实现，另一种就是通过MBG提供了Plugin方式扩展，一般情况下建议使用Plugin就可以基本完成大部分的扩展内容，如果真的要做很大的改动，可以使用继承的方式；

# 使用继承方式的扩展点

下面要列出的是MBG中提供了扩展点，通过实现这些接口或者继承这些类，并在配置文件中配置，就可以达到扩展MBG的目的：

## org.mybatis.generator.api.IntrospectedTable

IntrospectedTable是MBG提供的一个比较基础的扩展类，相当于可以重新定义一个runtime，同时，IntrospectedTable也是一个比较低级的扩展点，比较适合做低级的扩展，比如想使用FreeMarker或者Velocity来生成代码（这个也是我们后面想要的）；
如果你要通过继承IntrospectedTable完成扩展，你需要自己来实现生成XML和Java代码的所有代码，在该类中提供了一个org.mybatis.generator.internal.rules.Rules类，来提供基础的生成规则的查询；
 在MBG中，提供了几种默认的IntrospectedTable的实现，其实在context上设置的runtime对应的就是不同的IntrospectedTable的实现，下面就是几种runtime和对应的IntrospectedTable关系：

- MyBatis3 (default)：org.mybatis.generator.codegen.mybatis3.IntrospectedTableMyBatis3Impl
- MyBatis3Simple：
   org.mybatis.generator.codegen.mybatis3.IntrospectedTableMyBatis3SimpleImpl
- Ibatis2Java2：org.mybatis.generator.codegen.ibatis2.IntrospectedTableIbatis2Java2Impl
- Ibatis2Java5：org.mybatis.generator.codegen.ibatis2.IntrospectedTableIbatis2Java5Impl
   注意，其中的IntrospectedTableMyBatis3Impl和IntrospectedTableMyBatis3SimpleImpl两个类是值得我们在自己扩展前仔细阅读的；

当扩展了自己的IntrospectedTable之后，就可以在context的runtime中配置自己的实现类的全限定名即可；



## org.mybatis.generator.api.IntrospectedColumn

IntrospectedColumn类主要用于解析和包装表中的一个列的信息，这些信息主要通过DataBase MetaData获得；如果需要重新写一个IntrospectedTable类的话，一般会重写该类；只需要在context元素的introspectedColumnImpl属性中写该类的全限定名即可完成配置；

## org.mybatis.generator.api.JavaTypeResolver

JavaTypeResolver类主要用于Jdbc类型和Java类型的一个映射关系；默认的实现是org.mybatis.generator.internal.types.JavaTypeResolverDefaultImpl；完成自己的扩展后，只需要在context元素中javaTypeResolver 子元素中配置该类的全限定名即可；

## org.mybatis.generator.api.ShellCallback

ShellCallback主要提供了两个方面的功能：

1. 把project属性或者package属性翻译成目录结构（MBG中面对的还是文件结构）；
2. 当Java或者XML文件存在的时候，负责怎么处理这些重复的文件；

该接口的默认实现为org.mybatis.generator.internal.DefaultShellCallback，默认的这个实现只提供了把project和package直接翻译成文件结构，如果某些文件夹不存在，则创建，另外对于重复存在的文件，默认实现也只能选择覆盖或者忽略；
如果你想把MBG集成在其他应用环境中，这是一个相当值得扩展的类，比如在eclipse中，可以方便的扩展该接口来处理文件方法合并的功能；
但是注意一点，该扩展在generatorConfig XML文件中默认没有提供配置的地方，只能通过在org.mybatis.generator.api.MyBatisGenerator的构造方法中自己去创建一个（该类主要用于实际的生成动作，换句话说，要扩展这个扩展点，就必须要自己去实现一个运行MBG的代码，比如自己实现一个Ant应用或者Maven插件，可以参考org.mybatis.generator.api.ShellRunner来实现自己的启动类，PS：这个ShellRunner其实就是直接使用命令行运行MBG的那个main入口类）；

## org.mybatis.generator.api.ProgressCallback 进度回调

ProgressCallback类也是一个很有用的扩展点，当MBG在生成的过程当中，会不断的调用这个接口里面的指定方法，简单说，这个类非常适合用来做MBG生成的进度条显示；默认的实现是org.mybatis.generator.internal.NullProgressCallback，即简单的忽略所有的进度消息，可以通过实现这个接口完成自己的进度提醒甚至可以在中途取消代码的生成进度；
 同样，该类也不能直接在generatorConfig.xml中配置，需要在org.mybatis.generator.api.MyBatisGenerator.generate()方法中传一个ProgressCallback类的实例即可；



# MBG的plugin机制















