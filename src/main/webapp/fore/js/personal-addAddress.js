//新增收货地址

var vm = new Vue({
    el:'#addressForm',
    data:{
        name:'',
        phoneNum:'',
        province:'',
        city:'',
        district:'',
        details:'',
    },
});

function addAddress() {
    var name = vm.name;
    var phoneNum = vm.phoneNum;
    var district =
        vm.province+'/'+
        vm.city+'/'+
        vm.district;
    var details = vm.details;
    if(!name || !phoneNum || !details){
       layer.msg('请填写完整信息');
       return;
    }
    var param = {
        'name': name,
        'phoneum': phoneNum,
        'district': district,
        'details': details,
    }
    var url = 'addAddress';

    $.ajax({
        type:'post',
        data:param,
        dataType:'json',
        url:url,
        success:function(result) {
            if(!result.success){
                layer.msg(result.msg);
                return;
            }
                layer.msg(result.msg);
        }
    })
}