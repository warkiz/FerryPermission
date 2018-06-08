# FerryPermission

[![DOWNLOAD](https://api.bintray.com/packages/warkiz/maven/ferrypermission/images/download.svg)](https://bintray.com/warkiz/maven/ferrypermission/_latestVersion)

Android平台运行时动态申请权限库, 我的目标是 —— 简单且易用。

## 截图

<img src="https://github.com/warkiz/FerryPermission/blob/master/gif/demo.gif?raw=true" width = "264" height = "464"/>

## 初始化

```gradle
implementation 'com.github.warkiz.ferrypermission:FerryPermission:1.0.0'
```

## 使用

使用前确保权限已经在AndroidManifest.xml文件中注册.

1. 通过observe申请单个权限：

```Java
new FerryPermission(this)
        .request(Manifest.permission.CAMERA) //申请的照相权限
        .observe(new PureResultListener() {
            @Override
            public void result(boolean isGranted) {
                if (isGranted) {
                    //获得权限,继续下一步.
                } else {
                    //权限拒绝.
                }
            }
        });
```

2. 通过observeCombined方法申请多个权限, 结果合并且回调一次：

```Java
new FerryPermission(this)
        .request(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        .observeCombined(new CombinedResultListener() { //组合式申请，结果回调一次
            @Override
            public void result(Permission permission) {
                if (permission.isGranted) {
                    //获得所有权限,继续下一步.
                } else if (permission.shouldShowRequestPermissionRationale) {
                    //至少有一个权限被拒绝并且下次会再次询问.
                } else {
                    //至少有一个权限被拒绝并且不再询问
                    //引导用户至设置页手动授权
                }
            }
        });
```

3. 通过observeEach方法申请多个权限,并且每个权限都回调结果：

```Java
 new FerryPermission(this)
        .request(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        .observeEach(new EachResultListener() {
            @Override
            public void result(Permission permission) {//回调两次,即每个权限各一次
                if (permission.isGranted) {
                    //获得权限。可获取权限名为 permission.name
                } else if (permission.shouldShowRequestPermissionRationale) {
                    //权限被拒绝,但会再次询问。
                } else {
                    //权限被拒绝,不再询问。
                }
            }
        });
```

## 联系

如果对此项目有疑问, 欢迎通过下面的方式联系我.

1. 提交Issue.
2. 发邮件, warkiz4j@gmail.com

## 许可

	Copyright (C) 2018 zhuangguangquan warkiz

	Licensed under the Apache License, Version 2.0 (the "License");
	you may not use this file except in compliance with the License.
	You may obtain a copy of the License at

	   http://www.apache.org/licenses/LICENSE-2.0

	Unless required by applicable law or agreed to in writing, software
	distributed under the License is distributed on an "AS IS" BASIS,
	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
	See the License for the specific language governing permissions and
	limitations under the License.
