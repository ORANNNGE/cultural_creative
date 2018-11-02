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
            <div class="coupm-tit" >
                <div class="coupm-img"><img :src="data.picture" /> </div>
                <div class="coupm-txt"><h3 v-text="data.name"></h3></div>
            </div>
            <!--规格-->
            <div class="coupm-speci">
                <p>选择规格<i><img src="images/coup-next.png"/></i></p>
            </div>
            <!--评价-->
            <div class="coupm-eva">
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
            </div>
        </div>
        <div class="coupm-low">
            <div class="coupletm-tit">
                <span></span><h1>图文详情</h1><span></span>
            </div>
            <div class="coupm-intr" >
                <div class="c-intr-tit">商品介绍</div>
                <div v-html="details"></div>
            </div>
            <div class="c-intr-ul">
                <ul>
                    <li>
                        <div class="c-intr-one">
                            <div class="c-intr-ul-img"><img src="images/coup-s3.png" /> </div>
                            <div class="c-intr-ul-txt">
                                <h3>制作工艺：</h3>
                                <p>浮雕烫金工艺是印刷行业中的一种印刷工艺，主要是利用热压转移的原理将铝层转印到产品表面最终在产品上形成特殊的金属效果。制作出来的立体浮雕烫金版、凹凸版工艺效果精度高且美观大</p>
                            </div>
                        </div>
                    </li>
                    <li>
                        <div class="c-intr-two">
                            <div class="c-intr-ul-txt ">
                                <h3>抒写字体：</h3>
                                <p>行草，是介于行书、草书之间的一种书体和书法风格，可以说是行书的草化或草书的行化。它是为了弥补楷书的刻板和草书的难于辨认而产生的。</p>
                            </div>
                            <div class="c-intr-ul-img"><img src="images/coup-s2.png" /> </div>
                        </div>
                    </li>
                </ul>
            </div>
            <div class="coupm-foot">
                <div class="c-foot-img"><img src="images/coup-s1.png" /> </div>
            </div>
            <div class="coupm-a" onclick="addCoupletsOrder()">
                <a href="##" >立即购买</a>
            </div>
        </div>
        <div class="coupletm-norm-bg">
            <div class="coupletm-blank"></div>
            <div class="coupletm-norm">
                <!--￥100-->
                <div class="norm-up">
                    <div class="norm-up-img"><img src="images/norm-bg.png" /></div>
                    <div class="norm-price"><p v-text="'￥'+thePrice.price.price"></p></div>
                </div>
                <div class="norm-down">
                    <ul class="norm-down-ul">
                        <li><!--尺寸-->
                            <h3>选择尺寸</h3>
                            <div class="norm-size">
                                <p v-for="item in sizeList" v-text="item.name" :data-id="item.id" data-type="size" onclick="getCoupletsPrice(this)"></p>
                            </div>
                        </li>
                        <li><!--选择专利-->
                            <h3>选择套餐</h3>
                            <div class="norm-patent">
                                <p v-for="item in comboList" v-text="item.name" :data-id="item.id" data-type="combo" onclick="getCoupletsPrice(this)"></p>
                            </div>
                        </li>
                    </ul>
                    <div class="norm-down-quantity">
                        <div class="norm-q-label">
                            <label>数量</label>
                        </div>
                        <div class="norm-q-btn">
                            <input class="norm-q-minus" type="button" value="-" @click="minus"/>
                            <input class="norm-q-number" type="number" value="1" v-model="num"/>
                            <input class="norm-q-plus" type="button" value="+" @click="plus"/>
                        </div>
                    </div>
                </div>
                <div class="norm-buy">
                    <div class="n-buy-price">
                        <p>总价：<span v-text="'￥' + totalPrice"></span></p>
                    </div>
                    <a class="n-buy-confirm" href="###" >确认</a>
                </div>
            </div>
        </div>

    </div>
</div>
</body>
<script type="text/javascript" src="js/jquery.1.8.2.min.js" ></script>
<script type="text/javascript" src="js/specification-change.js" ></script>
<script src="../static/plugin/layui/layer/layer.js"></script>
<script src="https://cdn.jsdelivr.net/npm/vue/dist/vue.js"></script>
<script src="js/myUtils.js"></script>
<script src="js/details.js"></script>
<script src="js/coupletsDetails.js"></script>
</html>


