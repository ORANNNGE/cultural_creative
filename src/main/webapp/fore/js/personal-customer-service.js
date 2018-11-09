//联系客服
var vm = new Vue({
    el:'#customerServ',
    data:{
        customerServ:'',
        leaveMsg:''
    },
    created:function(){
        $.ajax({
            url:'getCustomerServ',
            dataType:'json',
            type:'post',
            success:function (result) {
                if(result.success == false){
                    layer.msg(result.msg);
                    return;
                }
                vm.customerServ = result.body.customerServ;
            }
        })
    },
    methods:{
        addLeaveMsg:function () {
            //留言
            var msg = vm.leaveMsg;
            if(!msg){
                layer.msg('不能为空');
                return;
            }
            console.log(msg);
            var param = {
                'msg':msg,
            }
            $.ajax({
                url:'addLeaveMsg',
                dataType:'json',
                type:'post',
                data:param,
                success:function (result) {
                    if(result.success == false){
                        layer.msg(result.msg);
                        return;
                    }
                    $('textarea').val('');
                    layer.msg(result.msg);
                }
            })
        }
    }
})