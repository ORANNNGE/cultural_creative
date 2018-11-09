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
<body>
<div class="announ-history" id="noticeDetails">
    <div class="his-tit clearfix">
        <h3 v-text="notice.title"></h3>
        <p v-text="notice.createDate"></p>
    </div>
    <div class="his-txt" v-html="details">

    </div>
</div>
</body>
<script type="text/javascript" src="js/jquery.1.8.2.min.js" ></script>
<script type="text/javascript" src="js/TouchSlide.1.1.js" ></script>
<script src="https://cdn.jsdelivr.net/npm/vue/dist/vue.js"></script>
<script src="js/myUtils.js"></script>
<script src="js/personal-notice-details.js"></script>
</html>

