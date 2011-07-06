<#include "/common/macros.ftl">
<@startPage title="System Caches" heading="System Caches" />

<div style="text-align: right; margin-bottom: 1em;">
	<form name="flushForm" action="${urls.getURL('/console/system/caches_flush')}" method="post" onsubmit="return confirm('Are you sure that you want flush all caches?');">
		<input type="submit" value="Flush All" class="Btn" />
	</form>
</div>

<div class="listdiv">	
	<table width="100%" border="0" cellspacing="0" cellpadding="0" class="listable">
		<thead>
			<th>Server</th>
			<th>Stat.</th>
		</thead>
		<tbody>
			<#assign servers = stats?keys>
			<#list servers as server>
			<tr<#if server_index%2=1> class="odd"</#if>>
				<td>${server}</td>
				<td>
					<table width="100%" border="0" cellspacing="0" cellpadding="0" class="listable">
					<#assign serverStats = stats[server]>
					<#assign statkeys = serverStats?keys>
					<#list statkeys as statkey>
						<tr>
							<td>${statkey}:</td>
							<td>${serverStats[statkey]}</td>
						</tr>
					</#list>
					</table>
				</td>
			</tr>
			</#list>
		</tbody>
	</table>
</div>
