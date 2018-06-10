# FerryPermission

[![DOWNLOAD](https://api.bintray.com/packages/warkiz/maven/ferrypermission/images/download.svg)](https://bintray.com/warkiz/maven/ferrypermission/_latestVersion)

A simple runtime permission library on Android. I hope this library enough easy for user.[readme_中文.md](https://github.com/warkiz/FerryPermission/blob/master/README_en.md)

## Screenshot

<img src="https://github.com/warkiz/FerryPermission/blob/master/gif/demo.gif?raw=true" width = "264" height = "464"/>

## Setup

```gradle
implementation 'com.github.warkiz.ferrypermission:ferrypermission:1.0.0'
```

## Usage

Before, make sure the permissions you requested has been registered in AndroidManifest.xml.

1. request CAMERA permission by method: observe();

```Java
new FerryPermission(this)
        .request(Manifest.permission.CAMERA)//the permission you requested
        .observe(new PureResultListener() {
            @Override
            public void result(boolean isGranted) {
                if (isGranted) {
                    //got permission, go ahead.
                } else {
                    //permission denied.
                }
            }
        });
```

2. request the combine permissions by method：observeCombined(), and the result will be called back one time.

```Java
new FerryPermission(this)
        .request(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        .observeCombined(new CombinedResultListener() { //combine request，callback one time.
            @Override
            public void result(Permission permission) {
                if (permission.isGranted) {
                     //got permission, go ahead.
                } else if (permission.shouldShowRequestPermissionRationale) {
                    //At least one permission is denied but will ask again.
                } else {
                    //At least one permission is denied and never ask again.
                    //go to settings
                }
            }
        });
```

3. request each permission of requesting list, and the result will be called back many times.

```Java
 new FerryPermission(this)
        .request(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        .observeEach(new EachResultListener() {
            @Override
            public void result(Permission permission) {//will call back 2 times.
                if (permission.isGranted) {
                     //got permission："permission.name",  go ahead.
                } else if (permission.shouldShowRequestPermissionRationale) {
                      //permission denied but will ask again.
                } else {
                     //permission denied and never ask again.
                }
            }
        });
```

## Support & Contact me

Star to support me , many thanks!

Feel free to contact me if you have any trouble on this project:
1. Create an issue.
2. Send mail to me, "warkiz".concat("4j").concat("@").concat("gmail.com")

## License

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
