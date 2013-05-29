<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/common/taglibs.jsp" %>
<div class="span3">
          <div class="well sidebar-nav">
            <ul class="nav nav-list">
						<li class="nav-header"><i class="icon-user"></i>系统管理</li>
				            <li id="user_list" ><a href="${ctx }/security/user/list"><i class="icon-list"></i>用户管理</a></li>
				  		
				            <li id="role_list" ><a href="${ctx }/security/role/list"><i class="icon-list"></i>角色管理</a></li>
				  		    
				            <li id="authority_list" ><a href="${ctx }/security/authority/list"><i class="icon-list"></i>权限管理</a></li>
				  		    
				
              <!-- <li class="nav-header">导航父类</li>
              <li><a href="#">导航子类1</a></li>
              <li><a href="#">导航子类2</a></li>
              <li><a href="#">导航子类3</a></li>
              <li><a href="#">导航子类4</a></li> -->
              
              
            </ul>
          </div><!--/.well -->
        </div><!--/span-->
