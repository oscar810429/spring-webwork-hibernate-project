<#include "/common/macros.ftl">
<@startPage title="Partner" heading="Partner" />

<content tag="submenu">
	<ul class="pagesnav">
		<li><a href="#" class="current">Partner List</a></li>
		<li><a href="${urls.getURL('/console/link/partner_create')}">Create Partner</a></li>
	</ul>
</content>

<div class="listdiv">
	
	<table width="100%" border="0" cellspacing="0" cellpadding="0" class="listable">
		<thead>
		<tr>
			<th>Name</th>
			<th>Logo</th>
			<th>Order</th>
			<th>Created date</th>
			<th>Action</th>
		</tr>
		</thead>
		<tbody>
			<#list result.data as partner>
			
			<tr<#if partner_index%2=1> class="odd"</#if>>
				<td>${partner.title?string}</td>
				<td><a href="${partner.url?default('#')}"><img src="${partner.src}" height="50" alt="${partner.alt}" border="0"/></a></td>
				<td>${partner.order}</td>
				<td>${partner.createdDate?date}</td>
				<td>
					<a href="${urls.getURL('/console/link/partner_edit')}?id=${partner.id}">Edit</a>
					<a href="${urls.getURL('/console/link/partner_delete')}?id=${partner.id}" onclick="return confirm('Are you sure that you want delete it?')">Delete</a>
				</td>
			</tr>
			</#list>
		</tbody>
	</table>

	<#if !result.isFirst() || result.hasNext()>
	<div class="nextprev">
		<#if !result.isFirst()>
		<span class="prev">&laquo; <a href="${urls.getURL('/console/link/partner')}?page=${result.page - 1}">Previous page</a></span>
		</#if>
		<#if result.hasNext()>
		<span class="next"><a href="${urls.getURL('/console/link/partner')}?page=${result.page + 1}">Next page</a> &raquo;</span>
		</#if>
	</div>
	</#if>
</div>
