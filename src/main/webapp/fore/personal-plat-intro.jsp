<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8" />
    <!--适配当前屏幕-->
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0" />
    <link rel="stylesheet" href="css/base.css" />
    <link rel="stylesheet" href="css/style.css" />
    <link rel="stylesheet" href="css/more-style.css" />
    <script type="text/javascript" src="js/jquery.1.8.2.min.js" ></script>
    <script type="text/javascript" src="js/TouchSlide.1.1.js" ></script>
    <title>平台介绍</title>
</head>
<body>
<div class="personal-introduce" id="platIntro">
    <div class="intro-up">
        <div class="intro-tit">平台介绍</div>
        <div class="intup-txt" v-html="parseBlob(platIntro.intro)">
        </div>
    </div>
    <div class="intro-ul">
        <div class="intro-tit">团队介绍</div>
        <ul>
            <li v-for="item in authorList">
                <div class="intro-img"><img :src="item.picture"/></div>
                <div class="intro-txt">
                    <h3>
                        {{item.name}}
                        <span v-if="item.type == 1">书法家</span>
                        <span v-if="item.type == 2">美术家</span>
                    </h3>
                    <p v-html="parseBlob(item.details)"></p>
                </div>
            </li>
        </ul>
    </div>
</div>
</body>
<script type="text/javascript" src="js/jquery.1.8.2.min.js" ></script>
<script type="text/javascript" src="js/TouchSlide.1.1.js" ></script>
<script src="https://cdn.jsdelivr.net/npm/vue/dist/vue.js"></script>
<script src="js/myUtils.js"></script>
<script src="js/personal-plat-intro.js"></script>
</html>


