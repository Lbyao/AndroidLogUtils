# AndroidLogUtils

### Gradle:

```maven
maven { url 'https://jitpack.io' }
```

```groovy
implementation 'com.github.Lbyao:AndroidLogUtils:0.2.0'
```

### use

* LogUtils 

```
setLogSwitch      :设置日志是否打印到控制台
setLogTag         :设置日志打印Tag
setFileSwitch     :设置日志是否保存到文件
setSaveFileLevel  :设置日志是否保存到文件的等级
setLogFilePath    ：设置日志文件保存的路径
setLogFileName    ：设置日志文件保存的名字
setLogFileSuffix  ：设置日志文件保存的后缀
```

* CrashUtils

```
init                 :初始化
openCrashSaveFile    :设置是否保存到文件
```