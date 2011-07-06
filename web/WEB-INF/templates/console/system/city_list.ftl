<#include "/common/macros.ftl">
<@startPage title="System City Messages" heading="System City Manager" />

<content tag="submenu">
	<ul class="pagesnav">
		<#if id?has_content>
	    <li><a href="${urls.getURL('/console/system/subcity')}?id=${id?c}" class="current">City List</a></li>
	    <li><a href="${urls.getURL('/console/system/city_create')}?id=${id?c}">Create City</a></li>
		<#else>
		<li><a href="#" class="current">City List</a></li>
		<li><a href="${urls.getURL('/console/system/city_create')}">Create City</a></li>
		</#if>
	</ul>
</content>
<@ww.form id="tag_form" name="tag_form" action="/console/system/search_city" method="get" >
	<label>CityName:</label>
     <input type="text" name="keyword" value="${keyword?default('')}">
	<input id="submit" type="submit" value="Submit" class="Btn" />
</@ww.form>
<div class="listdiv">
	<table width="100%" border="0" cellspacing="0" cellpadding="0" class="listable">
		<thead>
			<th>Name</th>
			<th>Ename</th>
			<th>Position</th>
			<th>Status</th>
			<th>AliasName</th>
			<th>Action</th>
		</thead>
		<tbody>
			<#list result.data as area>
			<tr<#if area_index%2=1> class="odd"</#if>>
				<td>
				<#if area.childAreas?has_content>
				<a href="${urls.getURL('/console/system/subcity')}?id=${area.id?c}">${area.name?string}</a>
				<#else>
				${area.name?string}
				</#if>
				</td>
				<td>
				${area.ename?default('NA')}
				</td>
				<#if area.position?has_content>
				<td>${area.position}</td>
				<#else>
				<td>0</td>
				</#if>
				<#if area.areaType.value()==1>
				<td>Disable</td>
				<#else>
				<td>Enable</td>
				</#if>
				<td><a href="${urls.getURL('/console/system/subcity')}?id=${area.id?c}">${area.aliasname1?default('NA')},${area.aliasname2?default('NA')}</a></td>
				<td>
					<a href="${urls.getURL('/console/system/city_edit')}?id=${area.id?c}">Edit</a>
					<a href="${urls.getURL('/console/system/city_delete')}?id=${area.id?c}" onclick="return confirm('Are you sure that you want delete it?')">Delete</a>
				</td>
			</tr>
			</#list>
		</tbody>
	</table>

	<#--
	<#list result.data as msg>
	<div class="BB<#if msg.expireDate.after(now)> Green</#if>">
		<h4 class="Top">${msg.subject?string}</h4>
		<div class="Content"><@yp.filter value="${msg.content}" find="false"/></div>
		<span>Expire Date: ${msg.expireDate?date?string}</span>
		<form action="${urls.getURL('/console/system/delete_message')}" method="post">
			<input type="hidden" name="id" value="${msg.id}" />
			<input type="submit" class="CancelBtn" value="Delete"/>
		</form>
		<form action="${urls.getURL('/console/system/edit_message')}" method="post">
			<input type="hidden" name="id" value="${msg.id}" />
			<input type="submit" class="Btn" value="Edit"/>
		</form>
		<div class="clear"></div>
	</div>
	</#list>
	
	<div class="clear"></div>
	-->
	
	 <#if (result.totalPage > 1)>
				<div class="pages">
					<@pn.pager result="result" url="${urls.getURL('/console/system/subcity?id=' + id?c + '&amp;page=$PAGE')}" />
				</div>
    </#if>

	<#--<div class="nextprev">
		<#if !result.isFirst()>
		<span class="prev">&laquo; <a href="${urls.getURL('/console/system/city')}?page=${result.page - 1}">${action.getText('link.recently')}</a></span>
		</#if>
		<#if result.hasNext()>
		<span class="next"><a href="${urls.getURL('/console/system/city')}?page=${result.page + 1}">${action.getText('link.earlier')}</a> &raquo;</span>
		</#if>
	</div>
	-->

</div>