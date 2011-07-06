<#include "/common/macros.ftl">
<@startPage title="System Messages" heading="System Messages" />

<content tag="submenu">
	<ul class="pagesnav">
		<li><a href="#" class="current">Message List</a></li>
		<li><a href="${urls.getURL('/console/system/messages_create')}">Create Message</a></li>
	</ul>
</content>

<div class="listdiv">
	<table width="100%" border="0" cellspacing="0" cellpadding="0" class="listable">
		<thead>
			<th>Subject</th>
			<th>Expire Date</th>
			<th>Content</th>
			<th>Action</th>
		</thead>
		<tbody>
			<#list result.data as msg>
			<tr<#if msg_index%2=1> class="odd"</#if>>
				<td>${msg.subject?html}</td>
				<td>${msg.expireDate?datetime}</td>
				<td><@pn.filter value="${msg.content?default('')}" clean="true" length="40" url="false" newline="false" find="false"/></td>
				<td>
					<a href="${urls.getURL('/console/system/messages_edit')}?id=${msg.id}">Edit</a>
					<a href="${urls.getURL('/console/system/messages_delete')}?id=${msg.id}" onclick="return confirm('Are you sure that you want delete it?')">Delete</a>
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
	<#if !result.isFirst() || result.hasNext()>
	<div class="nextprev">
		<#if !result.isFirst()>
		<span class="prev">&laquo; <a href="${urls.getURL('/console/system/messages')}?page=${result.page - 1}">${action.getText('link.recently')}</a></span>
		</#if>
		<#if result.hasNext()>
		<span class="next"><a href="${urls.getURL('/console/system/messages')}?page=${result.page + 1}">${action.getText('link.earlier')}</a> &raquo;</span>
		</#if>
	</div>
	</#if>
</div>
