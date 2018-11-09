//公告列表

var vm = new Vue({
    el:'#noticeList',
    data:{
        noticeList:null,
    },
    created:function(){
        $.ajax({
            url:'getNoticeList',
            dataType:'json',
            type:'post',
            success:function (result) {
                if(result.success == false){
                    layer.msg(result.msg);
                    return;
                }
                vm.noticeList = result.body.noticeList;
            }
        })
    },
    filters:{
        formatDate:function (date) {
           return dateFormat(date);
        }
    },
    methods:{
        toDetails:function (id) {
            location.href='personal-notice-details.jsp?id='+id;
        }
    }
})