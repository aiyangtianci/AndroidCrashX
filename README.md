# 介绍
[![](https://jitpack.io/v/aiyangtianci/AndroidCrashX.svg)](https://jitpack.io/#aiyangtianci/AndroidCrashX)
<br/>&nbsp;&nbsp; &nbsp;&nbsp;  🔥AndroidCrashX 能够降低APP线上崩溃次数，支持java 异常捕获、native崩溃和ANR处理、线上bug日志追踪。不需要根权限或任何系统权限。
 
## 特性
* 支持 Android 4.0 - 10（API level 14 - 29）。
* 捕获 java 崩溃、native 崩溃和 ANR。
* 主线程或子线程抛出异常后，迫使主线程Looper持续loop()。
* Activity生命周期中抛出异常，关闭异常页面。
* 当绘制、测量、布局出现问题导致Crash时，关闭异常界面。

![Image](https://img-blog.csdnimg.cn/20200417151421500.jpg)<br/>
[原理讲解，请查看我的博客:https://blog.csdn.net/csdn_aiyang/article/details/105054241](https://blog.csdn.net/csdn_aiyang/article/details/105054241)  <br/>
 
## 使用

#### 1. 增加依赖。

```Gradle
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
}
 
dependencies {
   implementation 'com.github.aiyangtianci:AndroidCrashX:2.0.2'
}
```
#### 2. 初始化 Crashx。

> Java

```Java
public class mApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        CrashX.install(this);
	
      	// 打包上线设为flase，不进行相关log、toast提示
        // CrashX.install(this,new CrashX.InitParameters().setDebug(false));
    }
}
```
 
## 已接入项目

<div>
<img src="https://img-blog.csdnimg.cn/20200417151751717.png" width="70" height="70">

<img src="https://img-blog.csdnimg.cn/20200417151751716.jpeg" width="70" height="70">

<img src="https://img-blog.csdnimg.cn/20200417151751711.png" width="70" height="70">

<img src="https://img-blog.csdnimg.cn/20200417151751727.png" width="70" height="70">
</div>

## 欢迎加入QQ群
<a href="https://img-blog.csdnimg.cn/20191113124915602.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2NzZG5fYWl5YW5n,size_16,color_FFFFFF,t_70">通往Android的阶梯：569614530</a> <br>
<img src ="https://img-blog.csdn.net/20180308141437608?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvY3Nkbl9haXlhbmc=/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70" width="210" height="215"><br>

