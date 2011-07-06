<#include "/common/macros.ftl">
<@startPage title="Melt Applies" heading="Melt Applies" />

<div class="listdiv">
	<table width="100%" border="0" cellspacing="0" cellpadding="0" class="listable">
		<thead>
			<th>User</th>
			<th>Date</th>
			<th>Content</th>
			<th>Accept</th>
			<th>Reject</th>
		</thead>
		<tbody>
			<#list applies as apply>
			<#assign user = apply.user>
			<tr<#if apply_index%2=1> class="odd"</#if>>
				<td><a href="<@userHome user=user/>" rel="contact_${user.id}"><img class="BuddyIcon" src="<@userIcon user=user/>" alt="${user.nickname?html}" width="48" height="48" /><@userName user=user/></a></td>
				<td>${apply.createdDate?datetime}</td>
				<td>${apply.content?default('')?html}</td>
				<td><a href="${urls.getURL('/console/users/unblock')}?apply=${apply.id}&amp;from=${urls.getRequestedURL()?url}" onclick="return confirm('Are you sure?');">Accept</a></td>
				<td><a href="${urls.getURL('/console/users/melt_apply_reject')}?id=${apply.id}&amp;from=${urls.getRequestedURL()?url}" onclick="return confirm('Are you sure?');">Reject</a></td>
			</tr>
			</#list>
		</tbody>
	</table>
</div>
