var pathName = window.location.pathname;
console.log('pathname:'+pathName)
if( pathName.indexOf('index') >= 0){
    $('#toIndex').attr('class','foot-a on-a');
}
if(pathName.indexOf('couplets') >= 0){
    $('#toCouplets').attr('class','foot-b on-b');
}
if(pathName.indexOf('lexicon') >= 0){
    $('#toLexicon').attr('class','foot-c on-c');
}
if(pathName.indexOf('personal') >= 0){
    $('#toPersonal').attr('class','foot-d on-d');
}
// if(pathName == 'to/index'){
//     $('#toIndex').attr('class','foot-a on-a');
// }
// if(pathName == 'to/index'){
//     $('#toIndex').attr('class','foot-a on-a');
// }
//跳转到主页
function toIndex(){
    if(pathName.indexOf('index') >= 0) return;
    location.href = 'index.jsp';
}

function toCouplets(){
    if(pathName.indexOf('couplets') >= 0) return;
    location.href = 'couplets.jsp';
}
function toLexicon(){
    if(pathName.indexOf('lexicon') >= 0) return;
    location.href = 'lexicon.jsp';
}
function toPersonal(){
    if(pathName.indexOf('personal') >= 0) return;
    location.href = 'personal.jsp';
}