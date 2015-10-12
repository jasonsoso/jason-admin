<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>登录 杰森博客 管理后台</title>
    <%@include file="/common/taglibs.jsp" %>
    <%@include file="/common/common-header.jsp" %>
    
<link href="${ctx }/resources/css/login.css" rel="stylesheet">
<script src="${ctx }/resources/bootstrap/js/jquery.js"></script>
<script type="text/javascript">
$(document).ready(function(){   
    flushLoginTicket();  // 进入登录页，则获取login ticket，该函数在下面定义。  
});

//登录验证函数, 由 onsubmit 事件触发  
var loginValidate = function(){
    var msg;
    var username = $.trim($('#J_Username').val());
    var password = $.trim($('#J_Password').val());
    var lt = $.trim($('#J_LoginTicket').val());
    var execution = $.trim($('#J_Execution').val());
    
    if (username.length == 0 ){  
        msg = "用户名不能为空。";  
    } else if (password.length == 0 ){  
        msg = "密码不能为空。";  
    }  
    if (msg && msg.length > 0) {  
        $('#J_ErrorMsg').fadeOut().text(msg).fadeIn();
        return false;  
        // Can't request the login ticket.  
    } else if ($('#J_LoginTicket').val().length == 0){  
        //$('#J_ErrorMsg').text('服务器正忙，请稍后再试..');
        $('#J_ErrorMsg').fadeOut().text("服务器正忙，请稍后再试..").fadeIn();
        return false;  
    } else {  
        var _services = 'service=' + encodeURIComponent('http://admin.jasonsoso.com:8087/shiro-cas');
        var url = 'http://sso.jasonsoso.com:9090/login?' + _services + '&n=' + new Date().getTime();

        var data = {username: username, password: password,isajax:true,isframe:false,
            lt: lt, _eventId: "submit", execution: execution};
        $.ajax({
            type: "post",
            async: false,
            url: url,
            data: data,
            dataType: "jsonp",
            jsonp: "callback",//传递给请求处理程序或页面的，用以获得jsonp回调函数名的参数名(一般默认为:callback)
            jsonpCallback:"callback",//自定义的jsonp回调函数名称，默认为jQuery自动生成的随机函数名，也可以写"?"，jQuery会自动为你处理数据
            success: function(response){
                if("success" === response.login){
                	location.replace(response.service+'?ticket='+response.ticket);
                }else{
                	 $('#J_ErrorMsg').fadeOut().text(response.msg).fadeIn();  
                     // 重新刷新 login ticket  
                     flushLoginTicket(); 
                }
            },
            error: function(){
                alert('服务器繁忙，请刷新页面。。。');
            }
        });        
        return true;  
    }  
}  
  
// 登录处理回调函数，将由 iframe 中的页同自动回调  
var feedBackUrlCallBack = function (result) {  
    customLoginCallBack(result);  
    //deleteIFrame('#ssoLoginFrame');// 删除用完的iframe,但是一定不要在回调前删除，Firefox可能有问题的  
};  
  
  
var deleteIFrame = function (iframeName) {  
    var iframe = $(iframeName);   
    if (iframe) { // 删除用完的iframe，避免页面刷新或前进、后退时，重复执行该iframe的请求  
        iframe.remove()  
    }  
};  
  
// 由于一个 login ticket 只允许使用一次, 当每次登录需要调用该函数刷新 lt  
var flushLoginTicket = function(){
    var _services = 'login-at=' + encodeURIComponent('http://admin.jasonsoso.com:8087/shiro-cas');
    var _services2 = 'service=' + encodeURIComponent('http://admin.jasonsoso.com:8087/shiro-cas');
    var services = 'http://sso.jasonsoso.com:9090/login?'+_services+'&'+_services2+'&get-lt=true&n='   
     + new Date().getTime();
    $.ajax({
        type: "get",
        async: false,
        url: services,
        dataType: "jsonp",
        jsonp: "callback",//传递给请求处理程序或页面的，用以获得jsonp回调函数名的参数名(一般默认为:callback)
        jsonpCallback:"callback",//自定义的jsonp回调函数名称，默认为jQuery自动生成的随机函数名，也可以写"?"，jQuery会自动为你处理数据
        success: function(response){
            //alert("lt:"+response.lt+" execution:"+response.execution);  
            // 将返回的 _loginTicket 变量设置到  input name="lt" 的value中。  
            $('#J_LoginTicket').val(response.lt);
            $('#J_Execution').val(response.execution);
        },
        error: function(){
            alert('服务器繁忙，请刷新页面。。。');
        }
    });
}  

</script>
    
</head>
<body>

<!-- 导入头部 -->
<%@include file="/WEB-INF/views/admin/header.jsp" %>

<!-- <form action="http://sso.jasonsoso.com:9090/login?service=http://admin.jasonsoso.com:8087/shiro-cas" method="post" onsubmit="return loginValidate();" target="ssoLoginFrame">  
 -->
    <ul>  
        <span class="red" style="height:12px;" id="J_ErrorMsg"></span>  
  
        <li>  
            <em>用户名：</em>  
            <input name="username" id="J_Username" type="text" value="jasonsoso" autocomplete="off" class="line" style="width: 180px" />  
        </li>  
        <li>  
            <em>密 码：</em>  
            <input name="password" type="password"  value="jasonsoso" id="J_Password" class="line" style="width: 180px" />  
        </li>  
  
        <!-- <li class="mai">  
            <em>&nbsp;</em>  
            <input type="checkbox" name="rememberMe" id="rememberMe" value="true"/>  
            &nbsp;自动登录  
            <a href="/retrieve">忘记密码？</a>  
        </li>   -->
        <li>  
            <em>&nbsp;</em>  
            <input type="hidden" name="lt" value="" id="J_LoginTicket">
            <input type="hidden" name="execution" value="" id="J_Execution">
            <input type="hidden" name="service" value="http://admin.jasonsoso.com:8087/shiro-cas">
            <input name="" type="button" value="登录" class="loginbanner" onclick="loginValidate()" />  
        </li>  
    </ul>  
<!-- </form>  --> 

<%-- 
 <!-- Carousel
    ================================================== -->
    <div id="myCarousel" class="carousel slide">
      <div class="carousel-inner">
        <div class="item active">
          <img src="${ctx}/resources/bootstrap/img/examples/slide-01.jpg" alt="">
          <div class="container">
            <div class="carousel-caption">
              <h1>Example headline.</h1>
              <p class="lead">Cras justo odio, dapibus ac facilisis in, egestas eget quam. Donec id elit non mi porta gravida at eget metus. Nullam id dolor id nibh ultricies vehicula ut id elit.</p>
              <a class="btn btn-large btn-primary" href="#">Sign up today</a>
            </div>
          </div>
        </div>
        <div class="item">
          <img src="${ctx}/resources/bootstrap/img/examples/slide-02.jpg" alt="">
          <div class="container">
            <div class="carousel-caption">
              <h1>Another example headline.</h1>
              <p class="lead">Cras justo odio, dapibus ac facilisis in, egestas eget quam. Donec id elit non mi porta gravida at eget metus. Nullam id dolor id nibh ultricies vehicula ut id elit.</p>
              <a class="btn btn-large btn-primary" href="#">Learn more</a>
            </div>
          </div>
        </div>
        <div class="item">
          <img src="${ctx}/resources/bootstrap/img/examples/slide-03.jpg" alt="">
          <div class="container">
            <div class="carousel-caption">
              <h1>One more for good measure.</h1>
              <p class="lead">Cras justo odio, dapibus ac facilisis in, egestas eget quam. Donec id elit non mi porta gravida at eget metus. Nullam id dolor id nibh ultricies vehicula ut id elit.</p>
              <a class="btn btn-large btn-primary" href="#">Browse gallery</a>
            </div>
          </div>
        </div>
      </div>
      <a class="left carousel-control" href="#myCarousel" data-slide="prev">&lsaquo;</a>
      <a class="right carousel-control" href="#myCarousel" data-slide="next">&rsaquo;</a>
    </div><!-- /.carousel --> --%>
    

      <hr />
      <%@include file="/WEB-INF/views/admin/footer.jsp" %>
      
<%@include file="/common/common-footer.jsp" %>


  </body>
</html>
