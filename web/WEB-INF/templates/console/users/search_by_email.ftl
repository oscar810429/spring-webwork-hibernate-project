<#include "/common/macros.ftl">
<@startPage title="User manangement" />
<content tag="submenu">
	<ul class="pagesnav">
		<li><a href="${urls.getURL('/console/users/search')}">用户查询</a></li>
		<li><a href="#" class="current">按邮件查询</a></li>
		<#--
		<li><a href="${urls.getURL('/console/users/search_out_flow')}">外链流量</a></li>
		<#if admin><li><a href="${urls.getURL('/console/users/search_vip_out_flow')}">VIP外链情况</a></li></#if>
		-->
	</ul>
</content>

<@ww.form id="search_by_email" name="search_by_email" action="/console/users/search_by_email" method="post" >
	<label>根据用户Email搜索用户个人信息:</label>
	<label>Email：</label><@ww.textfield id="email" name="email" value="email" size="30" />
	<input id="submit" type="submit" value="搜索" class="Btn" />
</@ww.form>

<#if email?has_content && (user?has_content)>
<p>用户Email：${email}</p>
<p>用户名：<a href="${urls.getUserHomeURL(user)}">${user.username?html}</a></p>
<p>用户昵称：${user.nickname?html}</p>
<#elseif email?has_content>
<p>查找失败，用户未找到！</p>
</#if>