<#include "/common/macros.ftl">
<@startPage title="Product" heading="Product" />

<content tag="submenu">
	<ul class="pagesnav">
		<li><a href="#" class="current">Product list</a></li>
		<li><a href="${urls.getURL('/console/cooperation/product_create')}">Create product</a></li>
	</ul>
</content>

<#if result?has_content>
<div class="listdiv">
	<table width="100%" border="0" cellspacing="0" cellpadding="0" class="listable">
		<thead>
		<tr>
			<th>Name</th>
			<th>Partner</th>
			<th>Code</th>
			<th>Price</th>
			<th>Action</th>
		</tr>
		</thead>
			<#list result.data as product>			
			<tr<#if product_index%2=1> class="odd"</#if>>
				<td>${product.name?string}</td>
				<td>${product.partner.name?string}</td>
				<td>${product.code?string}</td>
				<td>${product.price}</td>
				<td>
					<a href="${urls.getURL('/console/cooperation/product_edit')}?id=${product.id}">Edit</a>
					<a href="${urls.getURL('/console/cooperation/product_delete')}?id=${product.id}" onclick="return confirm('Are you sure that you want delete it?')">Delete</a>
				</td>
			</tr>
			</#list>
		</tbody>
	</table>

	<#if !result.isFirst() || result.hasNext()>
	<div class="nextprev">
		<#if !result.isFirst()>
		<span class="prev">&laquo; <a href="${urls.getURL('/console/cooperation/product')}?page=${result.page - 1}">Previous page</a></span>
		</#if>
		<#if result.hasNext()>
		<span class="next"><a href="${urls.getURL('/console/cooperation/product')}?page=${result.page + 1}">Next page</a> &raquo;</span>
		</#if>
	</div>
	</#if>
</div>
</#if>

