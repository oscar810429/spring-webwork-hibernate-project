<#include "/common/macros.ftl">
<@startPage title="Partner" heading="Partner" />

<content tag="submenu">
	<ul class="pagesnav">
		<li><a href="#" class="current">Partner list</a></li>
		<li><a href="${urls.getURL('/console/cooperation/partner_create')}">Create partner</a></li>
	</ul>
</content>

<div class="listdiv">
	
	<table width="100%" border="0" cellspacing="0" cellpadding="0" class="listable">
		<thead>
		<tr>
			<th>Name</th>
			<th>Return url</th>
			<th>Yupoo key</th>
			<th>Token</th>
			<th>Action</th>
		</tr>
		</thead>
		<tbody>
			<#list result.data as partner>			
			<tr<#if partner_index%2=1> class="odd"</#if>>
				<td>${partner.name?string}</td>
				<td><a href="${partner.returnUrl}">${partner.returnUrl}</a></td>
				<td>${partner.ypKey?string}</td>
				<td>${partner.token?string}</td>
				<td>
					<a href="${urls.getURL('/console/cooperation/partner_edit')}?id=${partner.id}">Edit</a>
					<a href="${urls.getURL('/console/cooperation/partner_delete')}?id=${partner.id}" onclick="return confirm('Are you sure that you want delete it?')">Delete</a>
				</td>
			</tr>
			</#list>
		</tbody>
	</table>

	<#if !result.isFirst() || result.hasNext()>
	<div class="nextprev">
		<#if !result.isFirst()>
		<span class="prev">&laquo; <a href="${urls.getURL('/console/cooperation/partner')}?page=${result.page - 1}">Previous page</a></span>
		</#if>
		<#if result.hasNext()>
		<span class="next"><a href="${urls.getURL('/console/cooperation/partner')}?page=${result.page + 1}">Next page</a> &raquo;</span>
		</#if>
	</div>
	</#if>
</div>
