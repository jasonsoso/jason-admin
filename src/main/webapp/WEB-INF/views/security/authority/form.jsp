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
			<!-- 面包屑 -->
			<ul class="breadcrumb">
			  <li>
			    <a href="${ctx }/admin">首页</a> <span class="divider">/</span>
			  </li>
			  <li>
			    <a href="${ctx }/security/authority/list">权限管理</a> <span class="divider">/</span>
			  </li>
			  <li id="breadcrumb_active" class="active">新增/修改</li>
			</ul>
			<!-- 操作 -->
			<ul class="nav nav-tabs">
	          <li title="列表"  class="icon index_collection_link ">
	            <a class="pjax" href="${ctx }/security/authority/list">
	              <i class="icon-th-list"></i>
	              <span>列表</span>
	            </a>
	          </li>
	          <li id="createId" title="新增"  class="icon new_collection_link active">
	            <a class="pjax" href="${ctx }/security/authority/create">
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

			<form:form  class="form-horizontal" method="post" modelAttribute="authority">
				<input type="hidden" name="_method" value="${_method}"></input>
			  <div class="control-group">
			    <label class="control-label" for="name">*名称</label>
			    <div class="controls">
			        <form:input path="name"  placeholder="name"/>
			        <form:errors path="name" cssClass="formError"/>
			    </div>
			  </div>
			  <div class="control-group">
			    <label class="control-label" for="permission">*权限</label>
			    <div class="controls">
			        <form:input path="permission"  placeholder="permission"/>
			        <form:errors path="permission" cssClass="formError"/>
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
			$('#authority_list').addClass('active');
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
