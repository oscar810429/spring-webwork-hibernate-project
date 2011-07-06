<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<#-- Include common set of tag library declarations for each layout -->
<#include "/common/taglibs.ftl">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<#-- Include common set of meta tags for each layout -->
	<#include "/common/meta.ftl">
	<title>${action.getText("webapp.prefix")}${title}</title>
    	
	<#if session?exists && session.id?exists>
	<script type="text/javascript">
	//<![CDATA[
		var app_domain = '${app_domain}';
		var media_root = '${media_root}';
		var auth_hash = '${session.id}';
		<#if session.getAttribute('api_auth')?exists>
		<#assign auth = session.getAttribute('api_auth')>
		var api_key = '${auth.apiKey?default('')}';
		var api_secret = '${auth.secret?default('')}';
		<#if auth.token?exists>
		var api_auth_token = '${auth.token?default('')}';
		</#if>
		</#if>
		var api_endpoint = '${base}/api/rest/';
		var api_response_format = 'json';
		<#if currentUser?exists>
		var current_user = { id: '${currentUser.id}', username: '${currentUser.username?js_string}', nickname: '${currentUser.nickname?js_string}'};
		<#else>
		var current_user = null;
		</#if>
	//]]>
	</script>
	</#if>
	
	<link rel="stylesheet" type="text/css" media="all" href="${media_root}/styles/console.css?v20091126.css" />
	
	<script type="text/javascript" src="${media_root}/scripts/mootools.v1.11.js?v20091126.js"></script>
	<script type="text/javascript" src="${media_root}/scripts/global.js?v20091126.js"></script>
	<script type="text/javascript" src="${media_root}/scripts/admin.js?v20091126.js"></script>
	<#--<script type="text/javascript" src="${media_root}/scripts/api.js?v20091207.js"></script>-->
	<script type="text/javascript" src="${media_root}/scripts/md5.js?v20091207.js"></script>
	<script type="text/javascript" src="${media_root}/scripts/lang_zh_CN.js?v20091207.js"></script>

	
	${head}
</head>

<body<#if page.properties['body.id']?exists> id="${page.properties['body.id']}"</#if>>

<div id="content">
	<#if page.properties['page.submenu']?exists>
	<div id="submenu">
		${page.properties['page.submenu']}
	</div>
	</#if>
	<div id="main">
		<#if page.properties['page.heading']?exists>
		<div id="heading">${page.properties['page.heading']}</div>
		</#if>
		<#include "/common/messages.ftl">
		${body}
	</div>
</div>

<div id="footer">
	<#include "/common/console_footer.ftl">
</div>

<#include "/common/console_menu.ftl">

</body>
</html>

