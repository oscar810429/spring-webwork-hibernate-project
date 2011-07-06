<#include "/common/macros.ftl">
<@startPage title="Collaborator" heading="Collaborator" />

<content tag="submenu">
	<ul class="pagesnav">
		<li><a href="#" class="current">Collaborator</a></li>
		<li><a href="${urls.getURL('/console/cooperation/collaborator_create')}">Create collaborator</a></li>
	</ul>
</content>

<div class="listdiv">
	<table width="100%" border="0" cellspacing="0" cellpadding="0" class="listable">
		<thead>
		<tr>
			<th>Name</th>
			<th>Id</th>
			<th>Secret</th>
			<th>Logo</th>
			<th>Action</th>
		</tr>
		</thead>
		<tbody>
			<#list result.data as collaborator>			
			<tr<#if collaborator_index%2=1> class="odd"</#if>>
				<td><#if collaborator.homeURL?has_content><a href="${collaborator.homeURL}" target="_blank">${collaborator.name?string}</a><#else>${collaborator.name?string}</#if></td>
				<td>${collaborator.id?string}</a></td>
				<td>${collaborator.secret?string}</td>
				<td><#if collaborator.logoURL?has_content><img src="${collaborator.logoURL}" height="70" alt="" border="0"/></#if></td>
				<td>
					<a href="${urls.getURL('/console/cooperation/collaborator_edit')}?id=${collaborator.id}">Edit</a>
					<a href="${urls.getURL('/console/cooperation/collaborator_delete')}?id=${collaborator.id}" onclick="return confirm('Are you sure that you want delete it?')">Delete</a>
				</td>
			</tr>
			</#list>
		</tbody>
	</table>

	<#if !result.isFirst() || result.hasNext()>
	<div class="nextprev">
		<#if !result.isFirst()>
		<span class="prev">&laquo; <a href="${urls.getURL('/console/cooperation/collaborator')}?page=${result.page - 1}">Previous page</a></span>
		</#if>
		<#if result.hasNext()>
		<span class="next"><a href="${urls.getURL('/console/cooperation/collaborator')}?page=${result.page + 1}">Next page</a> &raquo;</span>
		</#if>
	</div>
	</#if>
</div>
