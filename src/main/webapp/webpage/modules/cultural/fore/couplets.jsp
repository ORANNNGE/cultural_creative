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
    <title>楹联成品库</title>
</head>
<body>
<div class="index-body" id="couplets">
    <!--图片-->
    <div class="lunbo" id="lunbo">
        <img src="../webpage/modules/cultural/fore/images/index-bg.png" />
    </div>
    <div class="middle">
        <!--楹联成品库-->
        <div class="couplet">
            <div class="coup-tab">
                <ul>
                    <li id="government" @click="governmentCouplets"class="on" >机关事业</li>
                    <li id="company" @click="companyCouplets">企业商户</li>
                    <li id="home" @click="homeCouplets">家庭用户</li>
                </ul>
            </div>
            <div class="cou-ul">
                <ul>
                    <li v-for="item in couplets">
                        <div class="cou-img"><img :src=" '../' + item.picture" /></div>
                        <div class="cou-txt">
                            <p v-text="item.name"></p>
                            <div class="cou-a clearfix">
                                <span v-text=" '￥' + item.price"></span><a href="###">立即购买</a>
                            </div>
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
<script type="text/javascript" src="../webpage/modules/cultural/fore/js/jquery.1.8.2.min.js" ></script>
<script type="text/javascript" src="../webpage/modules/cultural/fore/js/TouchSlide.1.1.js" ></script>
<script src="https://cdn.jsdelivr.net/npm/vue/dist/vue.js"></script>
<script src="../webpage/modules/cultural/fore/js/couplets.js"></script>
<script src="../webpage/modules/cultural/fore/js/footerNavigation.js"></script>
</html>

