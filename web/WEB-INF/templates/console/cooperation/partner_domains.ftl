<ul>
	<li><a href="${urls.getURL('/console/cooperation/partner_domains')}">合作域名列表</a></li>
	<li><a href="${urls.getURL('/console/cooperation/create_pdomain')}">创建合作域名</a></li>
</ul>

<table>
	<tr>
		<th>域名</th>
		<th>合作名</th>
		<th>操作</th>
	</tr>
	<tbody>
<#list list as domains>
		<tr>
			<td>${domains.domain?html}</td><td>${domains.partnerName?html}</td> <td><a href="${urls.getURL('/console/cooperation/edit_pdomain?id='+ domains.domain)}">编辑</a></td>
		</tr>
</#list>
	</tbody>
</table>
