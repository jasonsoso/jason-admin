<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <title>登录 杰森博客 管理后台</title>
    <%@include file="/common/taglibs.jsp" %>
    <%@include file="/common/common-header.jsp" %>

    <style type="text/css">
      body {
        padding-top: 40px;
        padding-bottom: 40px;
        background-color: #f5f5f5;
      }

      .form-signin {
        max-width: 300px;
        padding: 19px 29px 29px;
        margin: 0 auto 20px;
        background-color: #fff;
        border: 1px solid #e5e5e5;
        -webkit-border-radius: 5px;
           -moz-border-radius: 5px;
                border-radius: 5px;
        -webkit-box-shadow: 0 1px 2px rgba(0,0,0,.05);
           -moz-box-shadow: 0 1px 2px rgba(0,0,0,.05);
                box-shadow: 0 1px 2px rgba(0,0,0,.05);
      }
      .form-signin .form-signin-heading,
      .form-signin .checkbox {
        margin-bottom: 10px;
      }
      .form-signin input[type="text"],
      .form-signin input[type="password"] {
        font-size: 16px;
        height: auto;
        margin-bottom: 15px;
        padding: 7px 9px;
      }
      .user_text{ height:18px; line-height:18px; padding-left:70px; width: 210px; float:left; margin-top:5px;color:red;}

    </style>
   
    </head>

  <body>

    <div class="container">

      <form class="form-signin" action="${ctx }/login" method="post">
        <h2 class="form-signin-heading">请 登 录 ...</h2>
        <input type="text" id="username" name="username" class="input-block-level" placeholder="账号/邮箱">
        <input type="password" id="password" name="password" class="input-block-level" placeholder="密码">
         
		 <div align="center" >
		    <button class="btn btn-large" type="submit">登  录</button>
          </div>
        
        <div class="user_text" id="showError">&nbsp;</div>
      </form>

    </div> <!-- /container -->

<%@include file="/common/common-footer.jsp" %>
<script type="text/javascript">
	
	$(document).ready(function(){
		$("#username").focus();
		
		var error = '${param.code}';
		if(error.length > 0){
			try{
				var errorCode = parseInt(error);
				
				switch(errorCode){
					case 1:
						showError("无效的用户名密码！");
						$("#username").focus();
						break;
					case 2:
						showError("该用户已被锁定！");
						break;
					case 4:
						showError("无效的用户名密码！");
						$("#username").focus();
                        break;
					case 8:
		                showError("验证码错误！");
		                $("#captcha").focus();
		                break;
					default:
						$("#username").focus();
						showError("登陆失败，请重新尝试");
						break;
				}
			}catch(e){
				$("#username").focus();
				showError("登陆失败，请重新尝试");
			}
		}
	});
	
	function showError(msg){
		$("#showError").empty().append("&nbsp;"+msg);
	}
</script>

  </body>
</html>
