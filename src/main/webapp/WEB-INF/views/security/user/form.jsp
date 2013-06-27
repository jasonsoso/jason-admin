<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8">
    <title>杰森博客 管理后台</title>
    <%@include file="/common/taglibs.jsp" %>
	<%@include file="/common/common-header.jsp" %>
</head>
<body>
	<%@include file="/WEB-INF/views/admin/header.jsp" %>
    <div class="container-fluid">
      <div class="row-fluid">
        <%@include file="/WEB-INF/views/admin/left.jsp" %>
        <div class="span9">
			<c:if test="${not empty message}">
			     <div class="alert alert-${message.type }">
	                <a class="close" data-dismiss="alert" href="#">×</a>
	                 ${message.text }
	            </div>
			</c:if>
			
			<ul class="breadcrumb"><!-- 面包屑 -->
			  <li>
			    <a href="${ctx }/admin">首页</a> <span class="divider">/</span>
			  </li>
			  <li>
			    <a href="${ctx }/security/user/list">用户管理</a> <span class="divider">/</span>
			  </li>
			  <li id="breadcrumb_active" class="active">新增/修改</li>
			</ul>
			<!-- 操作 -->
			<ul class="nav nav-tabs">
	          <li title="列表"  class="icon index_collection_link ">
	            <a class="pjax" href="${ctx }/security/user/list">
	              <i class="icon-th-list"></i>
	              <span>列表</span>
	            </a>
	          </li>
	          <li id="createId" title="新增"  class="icon new_collection_link active">
	            <a class="pjax" href="${ctx }/security/user/create">
	              <i class="icon-plus"></i>
	              <span>新增</span>
	            </a>
	          </li>
	          <li id="editId" title="编辑"  class="icon new_collection_link active">
	            <a class="pjax">
	              <i class="icon-pencil"></i>
	              <span>编辑</span>
	            </a>
	          </li>
			</ul>

			<form:form  class="form-horizontal" method="post" modelAttribute="userInfo">
				<input type="hidden" name="_method" value="${_method}"></input>
			  <div class="control-group">
			    <label class="control-label" for="username">*账号</label>
			    <div class="controls">
			        <form:input path="username"  placeholder="username"/>
			        <form:errors path="username" cssClass="formError"/>
			    	<p class="help-block">必填.用户名长度必须在6~20之间！</p>
			    </div>
			  </div>
			  <div class="control-group">
			    <label class="control-label" for="truename">真实姓名</label>
			    <div class="controls">
			        <form:input path="truename"  placeholder="truename"/>
			    </div>
			  </div>
			  <div class="control-group">
			    <label class="control-label" for="password">*密码</label>
			    <div class="controls">
			        <form:password path="password"  placeholder="password"/>
			        <form:errors path="password" cssClass="formError"/>
                    <p class="help-block">必填.密码最少6个字符串！</p>
			    </div>
			  </div>
			  
			  <div class="control-group">
			    <label class="control-label" for="email">*Email</label>
			    <div class="controls">
			        <form:input path="email"  placeholder="email"/>
			        <form:errors path="email" cssClass="formError" />
			    </div>
			  </div>
			  <div class="control-group">
                <label class="control-label" for="accountNonLocked">是否锁定</label>
                <div class="controls">
                    <form:radiobutton  path="accountNonLocked" value="true" placeholder="accountNonLocked" label="未鎖"/>
                    
                    <form:radiobutton  path="accountNonLocked" value="false" placeholder="accountNonLocked" label="锁定"/>
                </div>
              </div>
			  <div class="control-group boolean optional">
			    <label class="control-label" for="priority">角色</label>
			    <div class="controls">
			        <c:forEach var="role" items="${roleList }">
			            <label class="checkbox inline">
						  <form:checkbox path="roleMap[${role.id }]" value="${role.id}" label="${role.name } "/>
						</label>
					</c:forEach>
			    </div>
			  </div>
			  <div class="control-group">
			    <div class="controls">
			      <button type="submit" class="btn">提交</button>
			      <button type="button" class="btn" onclick="javascript:history.go(-1);">返回</button>
			    </div>
			  </div>
			</form:form>
          
        </div>
      </div>
	<%@include file="/WEB-INF/views/admin/footer.jsp" %>
    </div>
<%@include file="/common/common-footer.jsp" %>
<script type="text/javascript">
	$(function(){
		load();
		function load(){
			$('#user_list').addClass('active');
			var _method = $('input[name="_method"]').val();
			if(_method == 'put'){
				//当 _method == 'put' 的时候 为edit编辑
				$('#breadcrumb_active').text("编辑");
				$('#createId').hide();
			}else{
				//否则为新增
				$('#breadcrumb_active').text("新增");
				$('#editId').hide();
				
			}
		};
		
	});
</script>
  </body>
</html>
