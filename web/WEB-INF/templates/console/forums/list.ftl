<#include "/common/macros.ftl">

<@startPage title="System Forums" heading="System Forums"/>

<content tag="submenu">
	<ul class="pagesnav">
		<li><a href="#" class="current">Forum List</a></li>
		<li><a href="${urls.getURL('/console/system/forums_create')}">Create Forum</a></li>
	</ul>
</content>

<div class="listdiv">
	<table width="100%" border="0" cellspacing="0" cellpadding="0" class="listable">
		<thead>
			<th>Name</th>
			<th>Position</th>
			<th>Description</th>
			<#---
			<th>Action</th>
			-->
		</thead>
		<tbody>
			<#list forums as forum>
			<tr<#if forum_index%2=1> class="odd"</#if>>
				<td>${forum.name}</td>
				<td><#--${forum.position?c}--></td>
				<td><@pn.filter value="${forum.description?default('')}" clean="true" length="40" url="false" newline="false" find="false"/></td>
				<td>
					<a href="${urls.getURL('/console/system/forums_edit')}?id=${forum.id}">Edit</a>
					<a href="${urls.getURL('/console/system/forums_delete')}?id=${forum.id}" onclick="return confirm('Are you sure that you want delete it?')">Delete</a>
				</td>
			</tr>
			</#list>
		</tbody>
	</table>
</div>
