<#include "/common/macros.ftl">
<@startPage title="Report Abuse" heading="Report Abuse" />

<content tag="submenu">
	<ul class="pagesnav">
		<li><a href="#" class="current">Abuse List</a></li>
	</ul>
</content>

<div class="listdiv">
	
	<table width="100%" border="0" cellspacing="0" cellpadding="0" class="listable">
		<thead>
			<th> </th>
			<th>Author</th>
			<th>Date</th>
			<th>Action</th>
		</thead>
		<tbody>
			<#list result.data as abuse>
			<tr>
				<td><input name="checkbox" type="checkbox" value="${abuse.id}" /></td>
				<td><a href="<@userHome user=abuse.user />">${abuse.user.nickname?html}</a></td>
				<td>${abuse.createDate?date}</td>
				<td>
					<#if (abuse.state.value() == 0)>
					<a href="${urls.getURL('/console/system/abuse_done')}?id=${abuse.id}" ">Done</a>
					<#else>
					Have Done!
					</#if>
					<a href="${urls.getURL('/console/system/abuse_delete')}?id=${abuse.id}" onclick="return confirm('Are you sure that you want delete it?')">Delete</a>
				</td>
			</tr>
			<tr class="odd">
				<td colspan="4">
				<h4>${abuse.subject?html}</h4>
				
				<a href="${abuse.url?default('')}">${abuse.content?html} </a>
				
				</td>
			</tr>
			</#list>
		</tbody>
	</table>
	
	<div class="pages">
	<@pn.pager result="result" url="${urls.getURL('/console/system/abuse?page=$PAGE')}" />
	</div>
</div>
