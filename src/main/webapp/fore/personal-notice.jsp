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
    <title>公告信息</title>
</head>
<body style="background: #eee;">
<div class="index-body" id="noticeList">
    <div class="announcement">
        <ul>
            <li v-for="item in noticeList" v-if="item" @click="toDetails(item.id)">
                <div class="anno-img"><img src="images/per-hd.png"/><span></span></div>
                <div class="anno-txt">
                    <h3>{{item.title}}<span>{{item.createDate}}</span></h3>
                    <p v-text="item.intro"></p>
                </div>

            </li>
        </ul>
    </div>
</div>
</body>
<script type="text/javascript" src="js/jquery.1.8.2.min.js" ></script>
<script type="text/javascript" src="js/TouchSlide.1.1.js" ></script>
<script src="../static/plugin/layui/layer/layer.js"></script>
<script src="https://cdn.jsdelivr.net/npm/vue/dist/vue.js"></script>
<script src="js/myUtils.js"></script>
<script src="js/personal-notice.js"></script>
</html>

