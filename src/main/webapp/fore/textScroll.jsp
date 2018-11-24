<%--
  Created by IntelliJ IDEA.
  User: 52555
  Date: 2018/11/15
  Time: 13:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0" />
    <style type="text/css">
        #textScroll{
            white-space:nowrap;
            overflow:hidden;
            width:200px;
            margin:50px;
        }
    </style>
</head>
<script type="text/javascript" src="js/jquery.1.8.2.min.js" ></script>
<script type="text/javascript" src="js/jquery.SuperSlide.2.1.1.js" ></script>
<body>
<div class="txtMarquee-left">
    <p><nobr>平台商城展示春联均为原创作品，凝聚着作者心血，已申请版权保护，未经许可，不得使用。</nobr></p>
</div>
<script>
    jQuery(".txtMarquee-left").slide({mainCell:"p",autoPlay:true,effect:"leftMarquee",interTime:50,trigger:"click"});
</script>


</body>
<script>

</script>
</html>
