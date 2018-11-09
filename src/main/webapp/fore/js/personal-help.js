//联系客服
var vm = new Vue({
    el:'#platHelp',
    data:{
        platHelp:''
    },
    created:function(){
        $.ajax({
            url:'getPlatHelp',
            dataType:'json',
            type:'post',
            success:function (result) {
                if(result.success == false){
                    layer.msg(result.msg);
                    return;
                }
                vm.platHelp = result.body.platHelp;
            }
        })
    },
    computed:{
        parseBlob:function () {
            var text = this.platHelp.details;
            return parseBlob(text);
        }
    }
})