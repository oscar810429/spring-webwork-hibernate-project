<#include "/common/macros.ftl">
<@startPage title="达人" heading="" />

<content tag="submenu">
	<ul class="pagesnav">
		<li><a href="#" class="current">达人列表</a></li>
		<li><a href="${urls.getURL('/console/users/daren')}">创建达人</a></li>
	</ul>
</content>

<#if yupoomanResult?has_content>
<@ww.form id="enable_daren_form" name="enable_daren_form" action="/console/users/save_enable_daren" method="post" >
	<label>当前被使用的达人是:</label>
	<select name="id" id="id">
	<#if !yupooman?has_content>
		<option value="0" selected>请选择达人</option>
	</#if>
	<#list yupoomanResult.data as people>
		<option value="${people.id}" <#if yupooman?has_content && yupooman.id== people.id>selected</#if>>${people.user.username}</option>
	</#list>
	</select>
	<input id="submit" type="submit" value="Submit" class="Btn" />
</@ww.form>

<div class="listdiv">
	
	<table width="100%" border="0" cellspacing="0" cellpadding="0" class="listable">
		<thead>
		<tr>
			<th>用户名</th>
			<th>状态</th>
			<th>文件目录</th>
			<th>创建时间</th>
			<th>操作</th>
		</tr>
		</thead>
		<tbody>
			<#list yupoomanResult.data as people>
			
			<tr<#if people_index%2=1> class="odd"</#if>>
				<td><a href="${urls.getURL('/darens/?id='+people.id)}">${people.user.username?html}</td>
				<td>${people.state}</td>
				<td>${people.url}</td>
				<td>${people.createdDate?date}</td>
				<td>
					<a href="${urls.getURL('/console/users/daren_edit')}?id=${people.id}">编辑</a>
					<a href="${urls.getURL('/console/users/daren_delete')}?id=${people.id}" onclick="return confirm('确认删除?')">删除</a>
				</td>
			</tr>
			</#list>
		</tbody>
	</table>

	<#if !yupoomanResult.isFirst() || yupoomanResult.hasNext()>
	<div class="nextprev">
		<#if !yupoomanResult.isFirst()>
		<span class="prev">&laquo; <a href="${urls.getURL('/console/users/daren_list')}?page=${yupoomanResult.page - 1}">上一页</a></span>
		</#if>
		<#if yupoomanResult.hasNext()>
		<span class="next"><a href="${urls.getURL('/console/users/daren_list')}?page=${yupoomanResult.page + 1}">下一页</a> &raquo;</span>
		</#if>
	</div>
	</#if>
</div>
<#else>
<p>当前没有任何达人信息，现在就进行<a href="${urls.getURL('/console/users/daren')}">创建</a></p>
</#if>