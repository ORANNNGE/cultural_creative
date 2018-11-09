//公告详情

var vm = new Vue({
    el:'#noticeDetails',
    data:{
        notice:null,
    },
    created:function(){
        var id = getParaFromURL('id');
        var param = {
            'id':id
        }
        $.ajax({
            url:'getNoticeDetails',
            dataType:'json',
            data:param,
            type:'post',
            success:function (result) {
                if(result.success == false){
                    layer.msg(result.msg);
                    return;
                }
                vm.notice = result.body.notice;
            }
        })
    },
    methods:{
    },
    computed:{
        details:function () {
            var text = vm.notice.details;
            return parseBlob(text);
        },
    }

})