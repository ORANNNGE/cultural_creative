//

var vm = new Vue({
    el:'#platIntro',
    data:{
        platIntro:null,
        authorList:null,
    },
    created:function(){
        $.ajax({
            url:'getPlatIntro',
            dataType:'json',
            type:'post',
            success:function (result) {
                if(result.success == false){
                    layer.msg(result.msg);
                    return;
                }
                vm.platIntro = result.body.platIntro;
                vm.authorList = result.body.authorList;
            }
        })
    },
    computed:{
        parseBlob:function (data) {
            console.log('*************'+data)
            return function (data) {
                return parseBlob(data);
                console.log('*************'+data)
            }
        },
    },
})