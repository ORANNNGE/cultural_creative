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
    <title>个人中心</title>
</head>
<body style="background: #eee;">
<div class="index-body" id="personalCenter">
    <div class="person-center">
        <div class="person-up">
            <div class="perup-left" id="customerInfo">
                <div class="perup-tou"><img :src="data.headimg" /></div>
                <div class="perup-txt">
                    <h3 v-text="data.nickname"></h3>
                    <p><i><img src="images/per-v.png"/></i>会员用户</p>
                </div>
            </div>
            <div class="perup-map">
                <a href="personal-address.jsp"><i><img src="images/per-map.png"/></i>收货地址</a>
            </div>
        </div>
        <div class="person-middle">
            <div class="permi-order">
                <div class="permi-my">
                    <p>我的订单</p>
                    <a href="###">全部订单<i><img src="images/coup-next.png" /></i></a>
                </div>
                <div class="permi-ul">
                    <ul>
                        <li><a href="###">
                            <i><img src="images/per-all.png" /></i>
                            <span>全部</span>
                        </a></li>
                        <li><a href="###">
                            <i><img src="images/per-fk.png" /></i>
                            <span>待付款</span>
                        </a></li>
                        <li><a href="###">
                            <i><img src="images/per-az.png" /></i>
                            <span>待安装</span>
                        </a></li>
                        <li><a href="###">
                            <i><img src="images/per-wc.png" /></i>
                            <span>已完成</span>
                        </a></li>
                    </ul>
                </div>
            </div>
            <div class="permi-more">
                <a href="personal-base.jsp"><i><img src="images/per-jbzl.png"/></i>基本资料</a>
                <a href="###"><i><img src="images/per-ggxx.png"/></i>公告信息</a>
                <a href="###"><i><img src="images/per-ptjs.png"/></i>平台介绍</a>
                <a href="###"><i><img src="images/per-lxkf.png"/></i>联系客服</a>
                <a href="###"><i><img src="images/per-bzzx.png"/></i>帮助中心</a>
            </div>
        </div>
    </div>

    <!--底部Tab-->
    <jsp:include page="include/footerNavigation.jsp"></jsp:include>
</div>
</body>
<script type="text/javascript" src="js/jquery.1.8.2.min.js" ></script>
<script src="../static/plugin/layui/layer/layer.js"></script>
<script src="https://cdn.jsdelivr.net/npm/vue/dist/vue.js"></script>
<script src="js/footerNavigation.js"></script>
<script src="js/customerInfo.js"></script>
</html>

