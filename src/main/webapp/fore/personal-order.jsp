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
    <title>我的订单</title>
</head>
<body style="background: #eee;">
<div class="personal-order" id="orderList">
    <div id="leftTabBox" class="order-more">
        <div class="hd">
            <ul>
                <li class="on">全部</li>
                <li>待付款</li>
                <li>待安装</li>
                <li>已完成</li>
            </ul>
        </div>
        <div class="bd">
            <div class="order-div">
                <ul>
                    <%--楹联订单--%>
                    <li v-for="item in coupletsOrderList" v-if="item">
                        <div class="order-up">
                            <h3><i><img src="images/per-home.png"/></i>文创中国</h3>

                            <span v-if="item.status == 1">待付款</span>
                            <span v-if="item.status == 2">待安装</span>
                            <span v-if="item.status == 3">交易成功</span>
                        </div>
                        <div class="order-middle">
                            <div class="order-img"><img src="images/per-fu.png"/></div>
                            <div class="order-txt clearfix">
                                <div class="order-t">
                                    <h3 v-text="item.couplets.name"></h3>
                                    <p v-text="'规格：' + item.coupletsPrice.sizeName"></p>
                                </div>
                                <div class="order-p">
                                    <p class="order-p-a" v-text="'￥'+item.totalPrice">￥100.00</p>
                                    <%--<p class="order-p-b">￥200.00</p>--%>
                                    <p class="order-p-c" v-text="'X'+item.num"></p>
                                </div>
                            </div>
                        </div>
                        <%--待付款--%>
                        <div class="order-low" v-if="item.status == '1'">
                            <input class="order-btn order-del" type="button" value="删除" />
                            <a class="order-btn order-money">付款</a>
                        </div>
                        <%--待安装--%>
                        <div class="order-low" v-if="item.status == '2'">
                            <input class="order-btn order-ok" type="button" value="已完成" />
                            <%--<a class="order-btn order-callme" type="button" value="联系安装人员" />--%>
                            <a class="order-btn order-callme"  href="tel:10010">联系安装人员</a>
                        </div>
                        <%--已完成--%>
                        <div class="order-low" v-if="item.status == '3'">
                            <input class="order-btn order-ok" type="button" value="删除" />
                            <a class="order-btn order-say on">评价</a>
                        </div>
                    </li>
                    <%--词库订单--%>
                    <li v-for="item in lexiconOrderList" v-if="item">
                        <div class="order-up">
                            <h3><i><img src="images/per-home.png"/></i>文创中国</h3>

                            <span v-if="item.status == 1">待付款</span>
                            <span v-if="item.status == 2">待安装</span>
                            <span v-if="item.status == 3">交易成功</span>
                        </div>
                        <div class="order-middle">
                            <div class="order-img"><img src="images/per-fu.png"/></div>
                            <div class="order-txt clearfix">
                                <div class="order-t">
                                    <h3 v-text="item.lexiconPrice.lexiconName"></h3>
                                    <p v-text="'规格：' + item.lexiconPrice.sizeName"></p>
                                </div>
                                <div class="order-p">
                                    <p class="order-p-a" v-text="'￥'+item.totalPrice">￥100.00</p>
                                    <%--<p class="order-p-b">￥200.00</p>--%>
                                    <p class="order-p-c" v-text="'X'+item.num"></p>
                                </div>
                            </div>
                        </div>
                        <%--待付款--%>
                        <div class="order-low" v-if="item.status == '1'">
                            <input class="order-btn order-del" type="button" value="删除" />
                            <a class="order-btn order-money">付款</a>
                        </div>
                        <%--待安装--%>
                        <div class="order-low" v-if="item.status == '2'">
                            <input class="order-btn order-ok" type="button" value="已完成" />
                            <%--<a class="order-btn order-callme" type="button" value="联系安装人员" />--%>
                            <a class="order-btn order-callme"  href="tel:10010">联系安装人员</a>
                        </div>
                        <%--已完成--%>
                        <div class="order-low" v-if="item.status == '3'">
                            <input class="order-btn order-ok" type="button" value="删除" />
                            <a class="order-btn order-say on">评价</a>
                        </div>
                    </li>
                    <%--其他成品订单--%>
                    <li v-for="item in finishedOrderList" v-if="item">
                        <div class="order-up">
                            <h3><i><img src="images/per-home.png"/></i>文创中国</h3>

                            <span v-if="item.status == 1">待付款</span>
                            <span v-if="item.status == 2">待安装</span>
                            <span v-if="item.status == 3">交易成功</span>
                        </div>
                        <div class="order-middle">
                            <div class="order-img"><img src="images/per-fu.png"/></div>
                            <div class="order-txt clearfix">
                                <div class="order-t">
                                    <h3 v-text="item.name"></h3>
                                    <%--<p v-text="'规格：' + item.lexiconPrice.sizeName"></p>--%>
                                </div>
                                <div class="order-p">
                                    <p class="order-p-a" v-text="'￥'+item.price"></p>
                                    <%--<p class="order-p-b">￥200.00</p>--%>
                                    <p class="order-p-c">X1</p>
                                </div>
                            </div>
                        </div>
                        <%--待付款--%>
                        <div class="order-low" v-if="item.status == '1'">
                            <input class="order-btn order-del" type="button" value="删除" />
                            <a class="order-btn order-money">付款</a>
                        </div>
                        <%--待安装--%>
                        <div class="order-low" v-if="item.status == '2'">
                            <input class="order-btn order-ok" type="button" value="已完成" />
                            <%--<a class="order-btn order-callme" type="button" value="联系安装人员" />--%>
                            <a class="order-btn order-callme"  href="tel:10010">联系安装人员</a>
                        </div>
                        <%--已完成--%>
                        <div class="order-low" v-if="item.status == '3'">
                            <input class="order-btn order-ok" type="button" value="删除" />
                            <a class="order-btn order-say on">评价</a>
                        </div>
                    </li>


                    <%--<li>
                        <div class="order-up">
                            <h3><i><img src="images/per-home.png"/></i>文创中国</h3>
                            <span>等待买家付款</span>
                        </div>
                        <div class="order-middle">
                            <div class="order-img"><img src="images/per-fu.png"/></div>
                            <div class="order-txt clearfix">
                                <div class="order-t">
                                    <h3>花开富贵对联</h3>
                                    <p>规格：1.8m</p>
                                </div>
                                <div class="order-p">
                                    <p class="order-p-a">￥100.00</p>
                                    <p class="order-p-b">￥200.00</p>
                                    <p class="order-p-c">X1</p>
                                </div>
                            </div>
                        </div>
                        <div class="order-low">
                            <input class="order-btn order-del" type="button" value="删除" />
                            <a class="order-btn order-money">付款</a>
                        </div>
                    </li>
                    <li>
                        <div class="order-up">
                            <h3><i><img src="images/per-home.png"/></i>文创中国</h3>
                            <span>交易成功</span>
                        </div>
                        <div class="order-middle">
                            <div class="order-img"><img src="images/per-fu.png"/></div>
                            <div class="order-txt clearfix">
                                <div class="order-t">
                                    <h3>花开富贵对联</h3>
                                    <p>规格：1.8m</p>
                                </div>
                                <div class="order-p">
                                    <p class="order-p-a">￥100.00</p>
                                    <p class="order-p-b">￥200.00</p>
                                    <p class="order-p-c">X1</p>
                                </div>
                            </div>
                        </div>
                        <div class="order-low">
                            <input class="order-btn order-ok" type="button" value="删除" />
                            <a class="order-btn order-say on">评价</a>
                        </div>
                    </li>
                </ul>
            </div>
            <div class="order-div">
                <ul>
                    <li>
                        <div class="order-up">
                            <h3><i><img src="images/per-home.png"/></i>文创中国</h3>
                            <span>等待买家付款</span>
                        </div>
                        <div class="order-middle">
                            <div class="order-img"><img src="images/per-fu.png"/></div>
                            <div class="order-txt clearfix">
                                <div class="order-t">
                                    <h3>花开富贵对联</h3>
                                    <p>规格：1.8m</p>
                                </div>
                                <div class="order-p">
                                    <p class="order-p-a">￥100.00</p>
                                    <p class="order-p-b">￥200.00</p>
                                    <p class="order-p-c">X1</p>
                                </div>
                            </div>
                        </div>
                        <div class="order-low">
                            <input class="order-btn order-del" type="button" value="删除" />
                            <a class="order-btn order-money">付款</a>
                        </div>
                    </li>
                    <li>
                        <div class="order-up">
                            <h3><i><img src="images/per-home.png"/></i>文创中国</h3>
                            <span>等待买家付款</span>
                        </div>
                        <div class="order-middle">
                            <div class="order-img"><img src="images/per-fu.png"/></div>
                            <div class="order-txt clearfix">
                                <div class="order-t">
                                    <h3>花开富贵对联</h3>
                                    <p>规格：1.8m</p>
                                </div>
                                <div class="order-p">
                                    <p class="order-p-a">￥100.00</p>
                                    <p class="order-p-b">￥200.00</p>
                                    <p class="order-p-c">X1</p>
                                </div>
                            </div>
                        </div>
                        <div class="order-low">
                            <input class="order-btn order-del" type="button" value="删除" />
                            <a class="order-btn order-money">付款</a>
                        </div>
                    </li>
                    <li>
                        <div class="order-up">
                            <h3><i><img src="images/per-home.png"/></i>文创中国</h3>
                            <span>等待买家付款</span>
                        </div>
                        <div class="order-middle">
                            <div class="order-img"><img src="images/per-fu.png"/></div>
                            <div class="order-txt clearfix">
                                <div class="order-t">
                                    <h3>花开富贵对联</h3>
                                    <p>规格：1.8m</p>
                                </div>
                                <div class="order-p">
                                    <p class="order-p-a">￥100.00</p>
                                    <p class="order-p-b">￥200.00</p>
                                    <p class="order-p-c">X1</p>
                                </div>
                            </div>
                        </div>
                        <div class="order-low">
                            <input class="order-btn order-del" type="button" value="删除" />
                            <a class="order-btn order-money">付款</a>
                        </div>
                    </li>
                </ul>
            </div>
            <div class="order-div">
                <ul>
                    <li>
                        <div class="order-up">
                            <h3><i><img src="images/per-home.png"/></i>文创中国</h3>
                            <span>等待卖家安装</span>
                        </div>
                        <div class="order-middle">
                            <div class="order-img"><img src="images/per-fu.png"/></div>
                            <div class="order-txt clearfix">
                                <div class="order-t">
                                    <h3>花开富贵对联</h3>
                                    <p>规格：1.8m</p>
                                </div>
                                <div class="order-p">
                                    <p class="order-p-a">￥100.00</p>
                                    <p class="order-p-b">￥200.00</p>
                                    <p class="order-p-c">X1</p>
                                </div>
                            </div>
                        </div>
                        <div class="order-low">
                            <input class="order-btn order-ok" type="button" value="已完成" />
                            <input class="order-btn order-callme" type="button" value="联系安装人员" />
                        </div>
                    </li>
                    <li>
                        <div class="order-up">
                            <h3><i><img src="images/per-home.png"/></i>文创中国</h3>
                            <span>等待卖家安装</span>
                        </div>
                        <div class="order-middle">
                            <div class="order-img"><img src="images/per-fu.png"/></div>
                            <div class="order-txt clearfix">
                                <div class="order-t">
                                    <h3>花开富贵对联</h3>
                                    <p>规格：1.8m</p>
                                </div>
                                <div class="order-p">
                                    <p class="order-p-a">￥100.00</p>
                                    <p class="order-p-b">￥200.00</p>
                                    <p class="order-p-c">X1</p>
                                </div>
                            </div>
                        </div>
                        <div class="order-low">
                            <input class="order-btn order-ok" type="button" value="已完成" />
                            <input class="order-btn order-callme" type="button" value="联系安装人员" />
                        </div>
                    </li>
                    <li>
                        <div class="order-up">
                            <h3><i><img src="images/per-home.png"/></i>文创中国</h3>
                            <span>等待卖家安装</span>
                        </div>
                        <div class="order-middle">
                            <div class="order-img"><img src="images/per-fu.png"/></div>
                            <div class="order-txt clearfix">
                                <div class="order-t">
                                    <h3>花开富贵对联</h3>
                                    <p>规格：1.8m</p>
                                </div>
                                <div class="order-p">
                                    <p class="order-p-a">￥100.00</p>
                                    <p class="order-p-b">￥200.00</p>
                                    <p class="order-p-c">X1</p>
                                </div>
                            </div>
                        </div>
                        <div class="order-low">
                            <input class="order-btn order-ok" type="button" value="已完成" />
                            <input class="order-btn order-callme" type="button" value="联系安装人员" />
                        </div>
                    </li>
                </ul>
            </div>
            <div class="order-div">
                <ul>
                    <li>
                        <div class="order-up">
                            <h3><i><img src="images/per-home.png"/></i>文创中国</h3>
                            <span>交易成功</span>
                        </div>
                        <div class="order-middle">
                            <div class="order-img"><img src="images/per-fu.png"/></div>
                            <div class="order-txt clearfix">
                                <div class="order-t">
                                    <h3>花开富贵对联</h3>
                                    <p>规格：1.8m</p>
                                </div>
                                <div class="order-p">
                                    <p class="order-p-a">￥100.00</p>
                                    <p class="order-p-b">￥200.00</p>
                                    <p class="order-p-c">X1</p>
                                </div>
                            </div>
                        </div>
                        <div class="order-low">
                            <input class="order-btn order-ok" type="button" value="删除" />
                            <a class="order-btn order-say on">评价</a>
                        </div>
                    </li>
                    <li>
                        <div class="order-up">
                            <h3><i><img src="images/per-home.png"/></i>文创中国</h3>
                            <span>交易成功</span>
                        </div>
                        <div class="order-middle">
                            <div class="order-img"><img src="images/per-fu.png"/></div>
                            <div class="order-txt clearfix">
                                <div class="order-t">
                                    <h3>花开富贵对联</h3>
                                    <p>规格：1.8m</p>
                                </div>
                                <div class="order-p">
                                    <p class="order-p-a">￥100.00</p>
                                    <p class="order-p-b">￥200.00</p>
                                    <p class="order-p-c">X1</p>
                                </div>
                            </div>
                        </div>
                        <div class="order-low">
                            <input class="order-btn order-ok" type="button" value="删除" />
                            <a class="order-btn order-say on">评价</a>
                        </div>
                    </li>
                    <li>
                        <div class="order-up">
                            <h3><i><img src="images/per-home.png"/></i>文创中国</h3>
                            <span>交易成功</span>
                        </div>
                        <div class="order-middle">
                            <div class="order-img"><img src="images/per-fu.png"/></div>
                            <div class="order-txt clearfix">
                                <div class="order-t">
                                    <h3>花开富贵对联</h3>
                                    <p>规格：1.8m</p>
                                </div>
                                <div class="order-p">
                                    <p class="order-p-a">￥100.00</p>
                                    <p class="order-p-b">￥200.00</p>
                                    <p class="order-p-c">X1</p>
                                </div>
                            </div>
                        </div>
                        <div class="order-low">
                            <input class="order-btn order-ok" type="button" value="删除" />
                            <a class="order-btn order-say on">评价</a>
                        </div>
                    </li>--%>
                </ul>
            </div>

        </div>
    </div>

</div>
</body>
<script type="text/javascript" src="js/jquery.1.8.2.min.js" ></script>
<script type="text/javascript" src="js/TouchSlide.1.1.js" ></script>`
<script type="text/javascript">TouchSlide({ slideCell:"#leftTabBox",effect:"leftLoop" }); </script>
<script src="../static/plugin/layui/layer/layer.js"></script>
<script src="https://cdn.jsdelivr.net/npm/vue/dist/vue.js"></script>
<script src="js/personal_order.js"></script>
</html>

