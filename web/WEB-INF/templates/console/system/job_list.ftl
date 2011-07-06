<#include "/common/macros.ftl">
<@startPage title="System Jobs" heading="System Jobs" />

<content tag="submenu">
	<ul class="pagesnav">
		<li><a href="#" class="current">Job List</a></li>
		<li><a href="${urls.getURL('/console/system/jobs_create')}">Create Job</a></li>
	</ul>
</content>

<div class="listdiv">
	<table width="100%" border="0" cellspacing="0" cellpadding="0" class="listable">
		<thead>
			<th>Group Name</th>
			<th>Job Name</th>
			<th>Delete</th>
		</thead>
		<tbody>
			<#assign keys = triggerMap?keys>
			<#list keys as groupName>
			<#assign subkeys = triggerMap[groupName]?keys>
			<#list subkeys as triggerName>
			<tr<#if triggerName_index%2=1> class="odd"</#if>>
				<td>${groupName}</td>
				<td><a href="${urls.getURL('/console/system/jobs_detail?group='+groupName+'&amp;name='+triggerName)}">${triggerName}</a></td>
				<td><a href="${urls.getURL('/console/system/jobs_delete?group='+groupName+'&amp;name='+triggerName)}" onclick="return confirm('Are you sure');">Delete</a></td>
			</tr>
			</#list>
			</#list>
		</tbody>
	</table>
</div>
