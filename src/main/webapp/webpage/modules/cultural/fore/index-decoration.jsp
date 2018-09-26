<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8" />
    <!--适配当前屏幕-->
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0" />
    <link rel="stylesheet" href="../webpage/modules/cultural/fore/css/base.css" />
    <link rel="stylesheet" href="../webpage/modules/cultural/fore/css/style.css" />
    <link rel="stylesheet" href="../webpage/modules/cultural/fore/css/more-style.css" />
    <title>室内装饰·工艺品</title>
</head>
<body>
<div class="index-body" id="decoration">
    <!--图片-->
    <div class="lunbo" id="lunbo">
        <img src="../webpage/modules/cultural/fore/images/index-bg.png" />
    </div>
    <div class="middle">
        <div class="interior year-pictures">
            <div class="year-ul">
                <ul>
                    <li v-for="item in decorations">
                        <div class="year-img"><img :src="'../'+item.picture" /></div>
                        <div class="year-txt">
                            <p class="year-p" v-text="item.details">室内装饰品</p>
                            <div class="year-a clearfix">
                                <span v-text=" '￥' + item.price"></span><a href="###">立即购买</a>
                            </div>
                        </div>
                    </li>
                    <%--<li>
                        <div class="year-img"><img src="images/index-snzxb.png" /></div>
                        <div class="year-txt">
                            <p class="year-p">室内装饰品</p>
                            <div class="year-a clearfix">
                                <span>￥200</span><a href="###">立即购买</a>
                            </div>
                        </div>
                    </li>
                    <li>
                        <div class="year-img"><img src="images/index-snzxc.png" /></div>
                        <div class="year-txt">
                            <p class="year-p">室内装饰品</p>
                            <div class="year-a clearfix">
                                <span>￥200</span><a href="###">立即购买</a>
                            </div>
                        </div>
                    </li>
                    <li>
                        <div class="year-img"><img src="images/index-snzxa.png" /></div>
                        <div class="year-txt">
                            <p class="year-p">室内装饰品</p>
                            <div class="year-a clearfix">
                                <span>￥200</span><a href="###">立即购买</a>
                            </div>
                        </div>
                    </li>
                    <li>
                        <div class="year-img"><img src="images/index-snzxa.png" /></div>
                        <div class="year-txt">
                            <p class="year-p">室内装饰品</p>
                            <div class="year-a clearfix">
                                <span>￥200</span><a href="###">立即购买</a>
                            </div>
                        </div>
                    </li>
                    <li>
                        <div class="year-img"><img src="images/index-snzxb.png" /></div>
                        <div class="year-txt">
                            <p class="year-p">室内装饰品</p>
                            <div class="year-a clearfix">
                                <span>￥200</span><a href="###">立即购买</a>
                            </div>
                        </div>
                    </li>
                    <li>
                        <div class="year-img"><img src="images/index-snzxc.png" /></div>
                        <div class="year-txt">
                            <p class="year-p">室内装饰品</p>
                            <div class="year-a clearfix">
                                <span>￥200</span><a href="###">立即购买</a>
                            </div>
                        </div>
                    </li>
                    <li>
                        <div class="year-img"><img src="images/index-snzxa.png" /></div>
                        <div class="year-txt">
                            <p class="year-p">室内装饰品</p>
                            <div class="year-a clearfix">
                                <span>￥200</span><a href="###">立即购买</a>
                            </div>
                        </div>
                    </li>--%>
                </ul>
            </div>
        </div>
    </div>
    <!--底部Tab-->
    <jsp:include page="include/footerNavigation.jsp"></jsp:include>
</div>
</body>
<script type="text/javascript" src="../webpage/modules/cultural/fore/js/jquery.1.8.2.min.js" ></script>
<script src="https://cdn.jsdelivr.net/npm/vue/dist/vue.js"></script>
<script src="../webpage/modules/cultural/fore/js/index-decoration.js"></script>
<script src="../webpage/modules/cultural/fore/js/footerNavigation.js"></script>
</html>

