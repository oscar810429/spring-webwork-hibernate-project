<#include "/common/macros.ftl">
<@startPage title="Text link" heading="Text link" />

<content tag="submenu">
	<ul class="pagesnav">
		<li><a href="#" class="current">Text Link List</a></li>
		<li><a href="${urls.getURL('/console/link/text_link_create')}">Create Text Link</a></li>
	</ul>
</content>

<div class="listdiv">
	
	<table width="100%" border="0" cellspacing="0" cellpadding="0" class="listable">
		<thead>
		<tr>
			<th>Name</th>
			<th>URL</th>
			<th>Order</th>
			<th>Created date</th>
			<th>Action</th>
		</tr>
		</thead>
		<tbody>
			<#list result.data as textLink>
			
			<tr<#if textLink_index%2=1> class="odd"</#if>>
				<td>${textLink.title?string}</td>
				<td><a href="${textLink.url?default('#')}">${textLink.url}</a></td>
				<td>${textLink.order}</td>
				<td>${textLink.createdDate?date}</td>
				<td>
					<a href="${urls.getURL('/console/link/text_link_edit')}?id=${textLink.id}">Edit</a>
					<a href="${urls.getURL('/console/link/text_link_delete')}?id=${textLink.id}" onclick="return confirm('Are you sure that you want delete it?')">Delete</a>
				</td>
			</tr>
			</#list>
		</tbody>
	</table>

	<#if !result.isFirst() || result.hasNext()>
	<div class="nextprev">
		<#if !result.isFirst()>
		<span class="prev">&laquo; <a href="${urls.getURL('/console/link/textLink')}?page=${result.page - 1}">Previous page</a></span>
		</#if>
		<#if result.hasNext()>
		<span class="next"><a href="${urls.getURL('/console/link/textLink')}?page=${result.page + 1}">Next page</a> &raquo;</span>
		</#if>
	</div>
	</#if>
</div>
