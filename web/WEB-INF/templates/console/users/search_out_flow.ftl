<#include "/common/macros.ftl">
<@startPage title="User manangement" />
<content tag="submenu">
	<ul class="pagesnav">
		<li><a href="${urls.getURL('/console/users/search')}">用户查询</a></li>
		<li><a href="${urls.getURL('/console/users/search_by_email')}">按邮件查询</a></li>
		<#--<li><a href="#" class="current">外链流量</a></li>
		<#if admin><li><a href="${urls.getURL('/console/users/search_vip_out_flow')}">VIP外链情况</a></li></#if>
		-->
	</ul>
</content>

<@ww.form id="search_out_flow" name="search_out_flow" action="/console/users/search_out_flow" method="post" >
	<label>搜索用户外联流量:</label>
	<label>用户名/ID：</label><input type="text" id="id" name="id" <#if id?has_content>value="${id}"</#if> size="32" />
	<label>年份:</label>
	<select name="year" id="year">
		<#list 2006..(currentYear) as y>
		<option value="${y?c}" <#if year?exists && (y=year)> selected</#if>>${y?c}</option>
		</#list>
	</select>
	<input id="submit" type="submit" value="搜索" class="Btn" />
</@ww.form>

<#if outFlows?has_content && (user?has_content)>
<h3>
<a href="${urls.getUserHomeURL(user)}">${user.nickname?html}</a> ${year?c}年的外链流量使用情况：
</h3>
<#list outFlows as flow>
	<p>${flow_index+1}月：${flow?c}MB</p>
</#list>
<#elseif id?has_content>
<p>用户未找到！</p>
</#if>