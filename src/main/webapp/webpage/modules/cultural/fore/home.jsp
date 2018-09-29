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
    <title>家庭成品楹联</title>
</head>
<body>
<div class="index-body" id="homeCouplets">
    <!--图片-->
    <div class="lunbo" id="lunbo">
        <img src="images/index-bg.png" />
    </div>
    <div class="middle">
        <div class="home">
            <div class="home-ul">
                <ul>
                    <li v-for="item in couplets">
                        <a class="home-i-a" href="###"><div class="home-img"><img :src="item.picture" /></div></a>
                        <a class="home-a" href="###" v-text="item.name"></a>
                        <div class="home-txt">
                            <span v-text=" '￥' + item.price">￥200</span>
                            <p v-if="item.lexicon.isOriginal == '1'">原创</p>
                            <p v-else>常用</p>
                        </div>
                    </li>
                </ul>
            </div>
        </div>
    </div>
    <!--底部Tab-->
    <jsp:include page="include/footerNavigation.jsp"></jsp:include>
</div>
</body>
<script type="text/javascript" src="js/jquery.1.8.2.min.js" ></script>
<script type="text/javascript" src="js/TouchSlide.1.1.js" ></script>
<script src="https://cdn.jsdelivr.net/npm/vue/dist/vue.js"></script>
<script src="js/home.js"></script>
<script src="js/footerNavigation.js"></script>
</html>

