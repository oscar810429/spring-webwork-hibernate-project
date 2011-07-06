<#include "/common/macros.ftl">
<@startPage title="User manangement" />
<content tag="submenu">
	<ul class="pagesnav">
		<li><a href="${urls.getURL('/console/users/search')}">用户查询</a></li>
		<li><a href="${urls.getURL('/console/users/search_by_email')}">按邮件查询</a></li>
		<#--<li><a href="${urls.getURL('/console/users/search_out_flow')}">外链流量</a></li>
		<li><a href="#" class="current">VIP外链情况</a></li>-->
	</ul>
</content>

<@ww.form id="search_vip_out_flow" name="search_vip_out_flow" action="/console/users/search_vip_out_flow" method="post" >
	<label>搜索VIP外联流量:</label>
	<label>VIP类型：</label>
	<select name="type" id="type">
		<option value="0" <#if type?exists && (type=0)> selected</#if>>非网店</option>
		<option value="1" <#if type?exists && (type=1)> selected</#if>>网店</option>
	</select>
	<input id="submit" type="submit" value="搜索" class="Btn" />
</@ww.form>
<#if result?has_content>
<div class="listdiv">
	
	<table width="100%" border="0" cellspacing="0" cellpadding="0" class="listable">
		<thead>
		<tr>
			<th>用户名</th>
			<th>上月使用外链流量</th>
			<th>本月使用外联流量</th>
			<th>流量上限</th>
			<th>VIP类型</th>
		</tr>
		</thead>
		<tbody>
			<#list result.data as user>
			<#assign maxFlow =maxFlows.get(user_index)>
			<#assign vipProduct = currentVipProducts.get(user_index)>
			<tr <#if  (maxFlow > 0) && (maxFlow < outFlows.get(user_index))>class="red"<#elseif user_index%2=1>class="odd"</#if> >
				<td><a href="${urls.getURL('/console/users/search_out_flow?id='+user.username)}">${user.username?html}</td>
				<td>${oldFlows.get(user_index)?c}MB</td>
				<td>${outFlows.get(user_index)?c}MB</td>
				<td><#if (maxFlow = -1)>无限<#else>${maxFlow?c}MB</#if></td>
				<td>
					<#if vipProduct?has_content && (vipProduct!='none')>${vipProduct?html}</#if>
				</td>
			</tr>
			</#list>
		</tbody>
	</table>
	<div class="pages">
	<@yp.pager result="result" url="${urls.getURL('/console/users/search_vip_out_flow?type='+type+'&page=$PAGE')}" />
	</div>
</div>
</#if>