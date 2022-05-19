# mapstruct + xmlbeans mre

This project is a minimal reproducible example for a problem occurring between mapstruct and xmlbeans.

The error in question looks like this:

`
[ERROR] Failed to execute goal org.apache.maven.plugins:maven-compiler-plugin:3.8.1:compile (default-compile) on project mapstructxmlbeans: Compilation failure
[ERROR] ~/mapstruct-xmlbeans/src/main/java/CarMapper.java:[6,8] Internal error in the mapping processor: java.lang.RuntimeException: javax.annotation.processing.FilerException: Attempt to recreate a file for type CarMapperImpl  	at org.mapstruct.ap.internal.processor.MapperRenderingProcessor.createSourceFile(MapperRenderingProcessor.java:59)  	at org.mapstruct.ap.internal.processor.MapperRenderingProcessor.writeToSourceFile(MapperRenderingProcessor.java:39)  	at org.mapstruct.ap.internal.processor.MapperRenderingProcessor.process(MapperRenderingProcessor.java:29)  	at org.mapstruct.ap.internal.processor.MapperRenderingProcessor.process(MapperRenderingProcessor.java:24)  	at org.mapstruct.ap.MappingProcessor.process(MappingProcessor.java:338)  	at org.mapstruct.ap.MappingProcessor.processMapperTypeElement(MappingProcessor.java:318)  	at org.mapstruct.ap.MappingProcessor.processMapperElements(MappingProcessor.java:267)  	at org.mapstruct.ap.MappingProcessor.process(MappingProcessor.java:162)  	at jdk.compiler/com.sun.tools.javac.processing.JavacProcessingEnvironment.callProcessor(JavacProcessingEnvironment.java:1023)  	at jdk.compiler/com.sun.tools.javac.processing.JavacProcessingEnvironment.discoverAndRunProcs(JavacProcessingEnvironment.java:939)  	at jdk.compiler/com.sun.tools.javac.processing.JavacProcessingEnvironment$Round.run(JavacProcessingEnvironment.java:1267)  	at jdk.compiler/com.sun.tools.javac.processing.JavacProcessingEnvironment.doProcessing(JavacProcessingEnvironment.java:1382)  	at jdk.compiler/com.sun.tools.javac.main.JavaCompiler.processAnnotations(JavaCompiler.java:1234)  	at jdk.compiler/com.sun.tools.javac.main.JavaCompiler.compile(JavaCompiler.java:916)  	at jdk.compiler/com.sun.tools.javac.api.JavacTaskImpl.lambda$doCall$0(JavacTaskImpl.java:104)  	at jdk.compiler/com.sun.tools.javac.api.JavacTaskImpl.invocationHelper(JavacTaskImpl.java:152)  	at jdk.compiler/com.sun.tools.javac.api.JavacTaskImpl.doCall(JavacTaskImpl.java:100)  	at jdk.compiler/com.sun.tools.javac.api.JavacTaskImpl.call(JavacTaskImpl.java:94)  	at org.codehaus.plexus.compiler.javac.JavaxToolsCompiler.compileInProcess(JavaxToolsCompiler.java:126)  	at org.codehaus.plexus.compiler.javac.JavacCompiler.performCompile(JavacCompiler.java:174)  	at org.apache.maven.plugin.compiler.AbstractCompilerMojo.execute(AbstractCompilerMojo.java:1134)  	at org.apache.maven.plugin.compiler.CompilerMojo.execute(CompilerMojo.java:187)  	at org.apache.maven.plugin.DefaultBuildPluginManager.executeMojo(DefaultBuildPluginManager.java:137)  	at org.apache.maven.lifecycle.internal.MojoExecutor.execute(MojoExecutor.java:210)  	at org.apache.maven.lifecycle.internal.MojoExecutor.execute(MojoExecutor.java:156)  	at org.apache.maven.lifecycle.internal.MojoExecutor.execute(MojoExecutor.java:148)  	at org.apache.maven.lifecycle.internal.LifecycleModuleBuilder.buildProject(LifecycleModuleBuilder.java:117)  	at org.apache.maven.lifecycle.internal.LifecycleModuleBuilder.buildProject(LifecycleModuleBuilder.java:81)  	at org.apache.maven.lifecycle.internal.builder.singlethreaded.SingleThreadedBuilder.build(SingleThreadedBuilder.java:56)  	at org.apache.maven.lifecycle.internal.LifecycleStarter.execute(LifecycleStarter.java:128)  	at org.apache.maven.DefaultMaven.doExecute(DefaultMaven.java:305)  	at org.apache.maven.DefaultMaven.doExecute(DefaultMaven.java:192)  	at org.apache.maven.DefaultMaven.execute(DefaultMaven.java:105)  	at org.apache.maven.cli.MavenCli.execute(MavenCli.java:957)  	at org.apache.maven.cli.MavenCli.doMain(MavenCli.java:289)  	at org.apache.maven.cli.MavenCli.main(MavenCli.java:193)  	at java.base/jdk.internal.reflect.DirectMethodHandleAccessor.invoke(DirectMethodHandleAccessor.java:104)  	at java.base/java.lang.reflect.Method.invoke(Method.java:577)  	at org.codehaus.plexus.classworlds.launcher.Launcher.launchEnhanced(Launcher.java:282)  	at org.codehaus.plexus.classworlds.launcher.Launcher.launch(Launcher.java:225)  	at org.codehaus.plexus.classworlds.launcher.Launcher.mainWithExitCode(Launcher.java:406)  	at org.codehaus.plexus.classworlds.launcher.Launcher.main(Launcher.java:347)  Caused by: javax.annotation.processing.FilerException: Attempt to recreate a file for type CarMapperImpl  	at jdk.compiler/com.sun.tools.javac.processing.JavacFiler.checkNameAndExistence(JavacFiler.java:745)  	at jdk.compiler/com.sun.tools.javac.processing.JavacFiler.createSourceOrClassFile(JavacFiler.java:501)  	at jdk.compiler/com.sun.tools.javac.processing.JavacFiler.createSourceFile(JavacFiler.java:438)  	at org.mapstruct.ap.internal.processor.MapperRenderingProcessor.createSourceFile(MapperRenderingProcessor.java:56)  	... 41 more  
[ERROR] 
`

## how to reproduce?

to reproduce the error, execute

```
mvn clean
mvn compile
mvn compile
```

note that `mvn compile` has to be executed twice. The error will occur on any subsequent invocation of mapstruct.

## known workarounds

### remove xmlbeans

The whole point of this repo is to test mapstruct in combination with xmlbeans, but one way to avoid the error is by getting rid of the xmlbeans build plugin in `pom.xml`.

### clear `deferredMapers` in `org.mapstruct.ap.MappingProcessor::process`

The error occurs on the second invocation of `process` in `MappingProcessor`. If `deferredMappers` is cleared on the second invocation of `process`, the compilation is successful.