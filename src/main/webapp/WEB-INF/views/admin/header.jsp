<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/common/taglibs.jsp" %>
    <div class="navbar navbar-fixed-top">
      <div class="navbar-inner">
        <div class="container-fluid">
          <a class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </a>
          <a class="brand" href="/" style="padding: 5px 20px 5px;">
          	<img class="img-rounded" width="30" height="30" alt="" src="${ctx}/resources/bootstrap/ico/login.png">
          	杰森 后台管理
          </a>
          <div class="nav-collapse collapse">
            <p class="navbar-text pull-right">
                <shiro:authenticated>
                                                                  亲！欢迎你:<a href="#" class="navbar-link"><shiro:principal></shiro:principal></a>
                    &nbsp; | &nbsp;
                    <a href="${ctx}/logout?service=${jason:getEntryValue('sso.currurlpre')}/admin/" data-method="delete" rel="nofollow"><span class="label label-important">退出</span></a>
                
                </shiro:authenticated>
                <shiro:notAuthenticated>
                                                                    亲！欢迎来杰森！
                     <a href="${jason:getEntryValue('sso.loginurlpre')}/login?service=${jason:getEntryValue('sso.currurlpre')}/shiro-cas" data-method="delete" rel="nofollow"><span class="label label-important">请登录</span></a>
                </shiro:notAuthenticated>
             	 
            </p>
            <ul class="nav">
              <li class="active"><a href="#">首页</a></li>
              <li><a href="#about">关于</a></li>
              <li><a href="#contact">联系我</a></li>
            </ul>
          </div>
        </div>
      </div>
    </div>