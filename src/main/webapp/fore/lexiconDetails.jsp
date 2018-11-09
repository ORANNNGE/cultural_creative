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
    <!--楹联词库(商品详情)-->
    <div class="thesaurus-more">
        <div class="thesam-up coupm-up">
            <!--图片-->
            <div class="thesam-tit">
                <div class="thesam-img"><img :src="data.picture" /> </div></div>
            <!--样式选择-->
            <div class="thesam-div">
                <!--选择字体或书法家-->
                <div class="thesam-font">
                    <p>选择字体</p><span></span> <i><img src="images/coup-next.png"/></i>
                </div>
                <!--选择尺寸-->
                <div class="thesam-size">
                    <p>选择尺寸</p><span></span><i><img src="images/coup-next.png"/></i>
                </div>
                <!--选择专利产品春联框-->
                <%--<div class="thesam-patent">
                    <p>选择专利产品春联框</p><span></span><i><img src="images/coup-next.png"/></i>
                </div>--%>
                <!--选择制作工艺-->
                <div class="thesam-production">
                    <p>选择套餐</p><span></span><i><img src="images/coup-next.png"/></i>
                </div>
            </div>

            <!--评价-->
           <%-- <div class="coupm-eva">
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
        <div class="thesam-low">
            <div class="thesaurm-tit">
                <span></span><h1>图文详情</h1><span></span>
            </div>
            <div class="thesaurm-intr">
                <div class="thesam-intr-tit">商品介绍</div>
                <div class="thesam-intr-txt" v-html="meaning">
                    <%--<p>从传统的寓意来说，龙有喜水、好飞、通天、善变、灵异、征瑞、兆祸、示威等神性。凤有喜火、向阳、秉德、兆瑞、崇高、尚洁、示美、喻情等神性。神性的互补和对应，使龙和凤走到了一起：一个是众兽之君，一个是百鸟之王；一个变化飞腾而灵异，一个高雅美善而祥瑞；两者之间的美好的互助合作关系建立起来，便“龙凤呈祥”了。“龙凤呈祥”吉祥物寓意着会带来一派祥和之气！</p>--%>
                </div>
            </div>
            <div class="thesam-a">
                <label>
                    <p>数量</p>
                    <input type="button" value="-" @click="minus"/>
                    <input type="number" value="1" v-model="num"/>
                    <input type="button" value="+" @click="plus"/>
                </label>
                <p class="thesam-a-pri">总价：<span v-text="'￥' + totalPrice" ></span></p>
                <a href="##" onclick="addLexiconOrder()">立即购买</a>
            </div>
        </div>
        <div class="thesarusm-norm-bg">
            <!--选择尺寸-->
            <div class="thesarusm-size">
                <div class="thesarusm-blank"></div>
                <div class="thesarusm-norm">
                    <!--图片-->
                    <div class="thsm-up">
                        <div class="thsm-up-img"><img src="images/norm-bg.png" /></div>
                    </div>
                    <div class="thsm-down">
                        <div class="thsm-size">
                            <h3>选择尺寸</h3>
                            <div class="thsm-si">
                                <p v-for="item in sizeList" v-text="item.name" :data-id="item.id" data-type="size" onclick="getLexiconPrice(this)"></p>
                            </div>
                        </div>
                    </div>
                    <div class="thsm-buy">
                        <a href="###">确认</a>
                    </div>
                </div>
            </div>
            <!--选择制作工艺-->
            <div class="thesarusm-production">
                <div class="thesarusm-blank"></div>
                <div class="thesarusm-norm">
                    <!--图片-->
                    <div class="thsm-up">
                        <div class="thsm-up-img"><img src="images/norm-bg.png" /></div>
                    </div>
                    <div class="thsm-down">
                        <div class="thsm-production ">
                            <h3>选择套餐</h3>
                            <div class="thsm-prod">
                                <p v-for="item in comboList" v-text="item.name" :data-id="item.id" data-type="combo" onclick="getLexiconPrice(this)"></p>
                            </div>
                        </div>
                    </div>
                    <div class="thsm-buy">
                        <a href="###">确认</a>
                    </div>
                </div>
            </div>
            <!--选择字体-->
            <div class="thesarusm-font">
                <div class="thesarusm-blank"></div>
                <div class="thesarusm-norm">
                    <!--图片-->
                    <div class="thsm-up">
                        <div class="thsm-up-img"><img src="images/norm-bg.png" /></div>
                    </div>
                    <div class="thsm-down">
                        <div class="thsm-font th-font">
                            <h3>选择字体</h3>
                            <div class="thsm-fo">
                                <p v-for="item in typefaceList" v-text="item.intro" :data-id="item.id" data-type="typeface" onclick="getLexiconPrice(this)"></p>
                            </div>
                        </div>
                    </div>
                    <div class="thsm-buy">
                        <a href="###">确认</a>
                    </div>
                </div>
            </div>

        </div>

    </div>
</div>
</body>
<script type="text/javascript" src="js/jquery.1.8.2.min.js" ></script>
<script src="../static/plugin/layui/layer/layer.js"></script>
<script src="https://cdn.jsdelivr.net/npm/vue/dist/vue.js"></script>
<script src="js/myUtils.js"></script>
<script src="js/details.js"></script>
<script src="js/pay.js"></script>
<script src="js/lexiconDetails.js"></script>
<script type="text/javascript" src="js/specification-change.js" ></script>
</html>

