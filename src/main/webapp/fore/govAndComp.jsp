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
    <title>单位成品楹联</title>
</head>
<body>
<div class="index-body" id="govAndComp">
    <!--图片-->
    <div class="lunbo" id="lunbo">
        <img src="images/index-bg.png" />
    </div>
    <div class="middle">
        <div class="unit">
            <div class="index-unit">
                <ul>
                    <li class="on" @click="governmentCouplets" id="government"><p>机关事业</p></li><li @click="companyCouplets" id="company"><p>企业商户</p></li>
                </ul>
            </div>
            <div class="bd">
                <ul>
                    <li v-for="item in couplets">
                        <a class="unit-i-a" :href="'coupletsDetails.jsp?id='+item.id"><div class="unit-img"><img v-bind:src="item.picture" /></div></a>
                        <a class="unit-a" :href="'coupletsDetails.jsp?id='+item.id" v-text="item.name"></a>
                        <div class="unit-txt">
                            <span v-text="'￥'+item.price"></span>
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
<script src="js/govAndComp.js"></script>
<script src="js/footerNavigation.js"></script>
</html>


