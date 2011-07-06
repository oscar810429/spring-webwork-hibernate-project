<#include "/common/macros.ftl">
<@startPage title="Home Tag" heading="Home Tag" />

<content tag="submenu">
	<ul class="pagesnav">
		<li><a href="#" class="current">Home Tag List</a></li>
		<li><a href="${urls.getURL('/console/tag/index_tag_create')}">Create Home Tag</a></li>
	</ul>
</content>

<div class="listdiv">
	
	<table width="100%" border="0" cellspacing="0" cellpadding="0" class="listable">
		<thead>
		<tr>
			<th>Tag name</th>
			<th>Tag weight</th>
			<th>Tag order</th>
			<th>New window</th>
			<th>Created date</th>
			<th>Action</th>
		</tr>
		</thead>
		<tbody>
			<#list result.data as tag>
			
			<tr<#if tag_index%2=1> class="odd"</#if>>
				<td>${tag.title}</td>
				<td>${tag.weight}</td>
				<td>${tag.order}</td>
				<td>${tag.urlBlank}</td>
				<td>${tag.createdDate?date}</td>
				<td>
					<a href="${urls.getURL('/console/tag/index_tag_edit')}?id=${tag.id}">Edit</a>
					<a href="${urls.getURL('/console/tag/index_tag_delete')}?id=${tag.id}" onclick="return confirm('Are you sure that you want delete it?')">Delete</a>
				</td>
			</tr>
			</#list>
		</tbody>
	</table>

	<#if !result.isFirst() || result.hasNext()>
	<div class="nextprev">
		<#if !result.isFirst()>
		<span class="prev">&laquo; <a href="${urls.getURL('/console/tag/hometag')}?page=${result.page - 1}">Previous page</a></span>
		</#if>
		<#if result.hasNext()>
		<span class="next"><a href="${urls.getURL('/console/tag/hometag')}?page=${result.page + 1}">Next page</a> &raquo;</span>
		</#if>
	</div>
	</#if>
</div>
