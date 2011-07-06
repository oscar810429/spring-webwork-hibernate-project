<#assign current = request.requestURI>
<div id="tabs">
	<ul id="main_tabs">
		<li><a id="menu_home" href="<@c.url value='/home'/>">Home</a></li>
		<#if admin>
		<li><a id="menu_users" href="#"<#if current?contains('users')> class="current"</#if>>Users</a></li>
		</#if>
		<li><a id="menu_photos" href="<@c.url value='/console/photos/search'/>"<#if current?contains('photos')> class="current"</#if>>Photos</a></li>
		<li><a id="menu_forums" href="#"<#if current?contains('forums')> class="current"</#if>>Forums</a></li>
		<li><a id="menu_groups" href="#"<#if current?contains('groups')> class="current"</#if>>Groups</a></li>
		<li><a id="menu_blogs" href="#"<#if current?contains('blogs')> class="current"</#if>>Blogs</a></li>
		<li><a id="menu_tags" href="#"<#if current?contains('tags')> class="current"</#if>>Tags</a></li>
		<li><a id="menu_api" href="#"<#if current?contains('api')> class="current"</#if>>API</a></li>
		<li><a id="menu_system" href="<@c.url value='/console/system/news'/>"<#if current?contains('system')> class="current"</#if>>System</a></li>
	</ul>
	
	<ul id="account_tabs">
		<li><a href="<@c.url value='/account/'/>">${action.getText("header.link.account")}</a></li>
		<li><a href="<@c.url value='/account/logout'/>" onclick="$('logout_form').submit();return false;">${action.getText("header.link.logout")}</a></li>
	</ul>
	<form action="/account/logout" id="logout_form" method="post"></form>
</div>