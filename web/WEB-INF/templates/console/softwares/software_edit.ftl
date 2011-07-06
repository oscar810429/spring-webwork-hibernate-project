<#include "/common/macros.ftl">
<#assign edit = (software?exists && software.id?exists)>
<#if edit>
<@startPage title="Edit Software" heading="Edit Software" />
<#else>
<@startPage title="Create Software" heading="Create Software" />
</#if>
<head>
   <script type="text/javascript" src="${media_root}/scripts/kindeditor.js"></script>  
</head>

<content tag="submenu">
	<ul class="pagesnav">
		<li><a href="${urls.getURL("/console/softwares/search")}">Software List</a></li>
		<li><a href="#" class="current">Create software</a></li>
	</ul>
</content>

<div class="nForm">
	<@ww.form id="software_form" name="software_form" action="software_save" namespace="/console/softwares" method="post">
		<#if edit>
		<@ww.hidden id="software_id" name="software.id" value="software.id" />
		</#if>
		<dl>
			<dt>Category:</dt>
			<dd>
			<select name="categoryid">
				<option value="0">Change category</option>
				<#if category?has_content>
					<#assign c = category>
					<#if categories?has_content>
					<#list categories as category>
						<option value="${category.id}" <#if category.id=c.id>selected</#if>>${category.name}</option>
						<#if category.categories?has_content>
						<#list category.categories as subCategory>
						<option value="${subCategory.id}" <#if subCategory.id=c.id>selected</#if>>|--${subCategory.name}</option>
						</#list>
						</#if>
					</#list>
					</#if>
				<#else>
					<#if categories?has_content>
					<#list categories as category>
						<option value="${category.id}">${category.name}</option>
						<#if category.categories?has_content>
						<#list category.categories as subCategory>
						<option value="${subCategory.id}">|--${subCategory.name}</option>
						</#list>
						</#if>
					</#list>
					</#if>
				</#if>
			</select>
			</dd>
		</dl>
		<dl>
			<dt>Name:</dt>
			<dd><@ww.textfield id="software_title" name="software.name" value="software.name" size="40"/></dd>
		</dl>
		
		<dl>
			<dt>Status:</dt>
			<dd>
			    <select name="status">
				 <option value="0" <#if software.state.value()==0>selected</#if>>UNDECLARED</option>
				 <option value="1" <#if software.state.value()==1>selected</#if>>PUBLISHED</option>
				 <option value="2" <#if software.state.value()==2>selected</#if>>CANCELED</option>
				 <option value="3" <#if software.state.value()==3>selected</#if>>DELETED</option>
				 <option value="4" <#if software.state.value()==4>selected</#if>>BLOCKED</option>
				</select>
		    </dd>
		</dl>
		
		<dl>
			<dt>Content:</dt>
			<dd>
			<@ww.textarea id="software_description" name="software.content" value="software.content" cols="110" rows="28" />
			</dd>
		</dl>
		
		<dl>
			<dt>Tags:</dt>
			<dd>
			<input name="tags" id="tags" type="text" size="40" value="${software.tagsAsString?default('')?html}" class="txt" />
			</dd>
		</dl>		
		
  		<dl>
			<dt>&nbsp;</dt>
			<dd class="submit">
				<input id="submit" type="submit" value="Submit" class="Btn" />
			</dd>
		</dl>
	</@ww.form>
	
	   <script type="text/javascript">
			KE.show({
				id : 'software_description',
				cssPath : './index.css',
				imageUploadJson : '${media_root}/uploads/upload_json.jsp',
			    fileManagerJson : '${media_root}/uploads/file_manager_json.jsp',
			    urlType : 'relative',
			    
				afterCreate : function(id) {
					KE.event.ctrl(document, 13, function() {
						KE.util.setData(id);
						document.forms['software_form'].submit();
					});
					KE.event.ctrl(KE.g[id].iframeDoc, 13, function() {
						KE.util.setData(id);
						document.forms['software_form'].submit();
					});
				}
			});
		</script>
</div>