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
    <title>商品详情</title>
</head>
<body>
<div class="index-body" id="details">
    <!--楹联成品库(商品详情)-->
    <div class="couplet-more">
        <div class="coupm-up">
            <!--标题-->
            <div class="coupm-tit">
                <div class="coupm-img"><img :src="data.picture" /> </div>
                <div class="coupm-txt more">
                    <h3 v-text="data.title"></h3>
                    <p v-text="'￥'+data.price"></p>
                </div>
            </div>
            <!--评价-->
            <%--<div class="coupm-eva">
                <div class="c-eva-tit">
                    <p>宝贝评价</p><a href="###">查看全部<i><img src="images/coup-small.png"/></i></a>
                </div>
                <div class="c-eva">
                    <div class="c-eva-div">
                        <div class="c-eva-img"><img src="images/coup-people.png" /> </div>
                        <div class="c-eva-txt">
                            <h3>江南才子</h3>
                            <p>2018-09-09</p>
                        </div>
                    </div>
                    <div class="c-eva-p">
                        <p>对联很不错，大气，字哈鞥好</p>
                    </div>
                </div>
            </div>--%>
        </div>
        <div class="coupm-low">
            <div class="coupletm-tit">
                <span></span><h1>图文详情</h1><span></span>
            </div>
            <div class="coupm-intr">
                <div class="c-intr-tit">作品简介</div>
                <div v-html="details"></div>
               <%-- <div class="c-book-p">
                    <h3>作者：莫然</h3>
                    <p>书画是绘画和书法的统称。画，是人们生活中创造的结晶，画的起源久远，有着丰富的意思。"画中有诗，诗中有画"，中国古代，诗与画分不开。画的作品也体现了作者的情感和思想，画中常常包含着艺术家强烈的思想感情，因此艺术也深深地孕育在画中。书，一说是书法，也就是俗话说的所谓的字，另一种观点则认为书是指文化内涵。由此可知，书画是指绘画和书法，也可以理解为具有文化内涵的绘画。</p>
                </div>--%>
            </div>
            <%--<div class="coupm-decor">--%>
                <%--<div class="c-foot-img"><img src="images/index-b1.png" /> </div>--%>
                <%--<div class="c-foot-img"><img src="images/index-b2.png" /> </div>--%>
            <%--</div>--%>
            <div class="coupm-a" onclick="addFinishedOrder()">
                <a href="##">立即购买</a>
            </div>
        </div>

    </div>
</div>
</body>
<script type="text/javascript" src="js/jquery.1.8.2.min.js" ></script>
<script type="text/javascript" src="js/specification-change.js" ></script>
<script src="https://cdn.jsdelivr.net/npm/vue/dist/vue.js"></script>
<script src="../static/plugin/layui/layer/layer.js"></script>
<script src="js/myUtils.js"></script>
<script src="js/details.js"></script>
<script src="js/pay.js"></script>
<script src="js/finishedDetails.js"></script>
</html>

