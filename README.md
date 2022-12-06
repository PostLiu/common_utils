# common_utils

[![](https://jitpack.io/v/PostLiu/common_utils.svg)](https://jitpack.io/#PostLiu/common_utils)  [![](https://img.shields.io/badge/License-Apache--2.0-blue.svg)](https://github.com/DylanCaiCoding/ViewBindingKtx/blob/master/LICENSE)

## 使用

##### 添加仓库地址

<div class="tab">
  <button class="tablinks" onclick="openTab(event, 'gradle')" id="defaultOpen">Groovy</button>
  <button class="tablinks" onclick="openTab(event, 'kotlin')">Kotlin</button>
</div>

<div id="gradle" class="tabcontent">
<pre>
<code>
repositories {
    maven { url 'https://www.jitpack.io' }
}
</code>
</pre>
</div>

<div id="kotlin" class="tabcontent">
<pre>
<code>
repositories {
    maven("https://www.jitpack.io")
}
</code>
</pre>
</div>

<script>
function openTab(evt, cityName) {
  var i, tabcontent, tablinks;
  tabcontent = document.getElementsByClassName("tabcontent");
  for (i = 0; i < tabcontent.length; i++) {
    tabcontent[i].style.display = "none";
  }
  tablinks = document.getElementsByClassName("tablinks");
  for (i = 0; i < tablinks.length; i++) {
    tablinks[i].className = tablinks[i].className.replace(" active", "");
  }
  document.getElementById(cityName).style.display = "block";
  evt.currentTarget.className += " active";
}

document.getElementById("defaultOpen").click();
</script>

### build.gradle

##### 引入依赖

<div class="tab">
  <button class="tablinks" onclick="openTab(event, 'gradle')" id="defaultOpen">Groovy</button>
  <button class="tablinks" onclick="openTab(event, 'kotlin')">Kotlin</button>
</div>

<div id="gradle" class="tabcontent">
<pre>
<code>
implementation "com.github.PostLiu:common_utils:1.0.0"
</code>
</pre>
</div>

<div id="kotlin" class="tabcontent">
<pre>
<code>
implementation("com.github.PostLiu:common_utils:1.0.0")
</code>
</pre>
</div>

<script>
function openTab(evt, cityName) {
  var i, tabcontent, tablinks;
  tabcontent = document.getElementsByClassName("tabcontent");
  for (i = 0; i < tabcontent.length; i++) {
    tabcontent[i].style.display = "none";
  }
  tablinks = document.getElementsByClassName("tablinks");
  for (i = 0; i < tablinks.length; i++) {
    tablinks[i].className = tablinks[i].className.replace(" active", "");
  }
  document.getElementById(cityName).style.display = "block";
  evt.currentTarget.className += " active";
}

document.getElementById("defaultOpen").click();
</script>

## Thanks

感谢[ViewBindingKTX](https://github.com/DylanCaiCoding/ViewBindingKTX)提供的ViewBinding封装库

## License

```
Copyright (C) 2022. PostLiu

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```