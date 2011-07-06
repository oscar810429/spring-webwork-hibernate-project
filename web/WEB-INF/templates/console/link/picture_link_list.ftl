<#include "/common/macros.ftl">
<@startPage title="Picture link" heading="Picture link" />

<content tag="submenu">
	<ul class="pagesnav">
		<li><a href="#" class="current">picture Link List</a></li>
		<li><a href="${urls.getURL('/console/link/picture_link_create')}">Create Picture Link</a></li>
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
			<#list result.data as pictureLink>
			
			<tr<#if pictureLink_index%2=1> class="odd"</#if>>
				<td>${pictureLink.title?string}</td>
				<td><a href="${pictureLink.url?default('#')}"><img src="${pictureLink.src}" height="50" alt="${pictureLink.alt}" border="0"/></a></td>
				<td>${pictureLink.order}</td>
				<td>${pictureLink.createdDate?date}</td>
				<td>
					<a href="${urls.getURL('/console/link/picture_link_edit')}?id=${pictureLink.id}">Edit</a>
					<a href="${urls.getURL('/console/link/picture_link_delete')}?id=${pictureLink.id}" onclick="return confirm('Are you sure that you want delete it?')">Delete</a>
				</td>
			</tr>
			</#list>
		</tbody>
	</table>

	<#if !result.isFirst() || result.hasNext()>
	<div class="nextprev">
		<#if !result.isFirst()>
		<span class="prev">&laquo; <a href="${urls.getURL('/console/link/pictureLink')}?page=${result.page - 1}">Previous page</a></span>
		</#if>
		<#if result.hasNext()>
		<span class="next"><a href="${urls.getURL('/console/link/pictureLink')}?page=${result.page + 1}">Next page</a> &raquo;</span>
		</#if>
	</div>
	</#if>
</div>
