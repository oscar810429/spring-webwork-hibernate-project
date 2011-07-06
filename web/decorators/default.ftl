<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<#-- Include common set of tag library declarations for each layout -->
<#include "/common/taglibs.ftl">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<#-- Include common set of meta tags for each layout -->
	<#include "/common/meta.ftl">
	<title>${title?default('')} - ${action.getText("webapp.prefix")}</title>
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
	<link rel="stylesheet" type="text/css" media="screen" href="${media_root}/styles/global.css?v20100515.css">
	<link rel="stylesheet" type="text/css" media="screen" href="${media_root}/styles/component.css?v20100515.css">
	<#if page.properties['page.styles']?exists>
	${page.properties['page.styles']}
	</#if>
	<script type="text/javascript" src="${media_root}/scripts/mootools.v1.2.4.js?v20100328.js" charset="utf-8"></script>
    <#if page.properties['page.scripts']?exists>
	${page.properties['page.scripts']}
	</#if>
	${head}
</head>

<body<#if page.properties['body.id']?exists> id="${page.properties['body.id']}"</#if>>
<div id="wrapper">
       <#include "/common/header.ftl">
       <div id="content">
        <#if page.properties['page.heading']?exists>
		<h1>${page.properties['page.heading']}</h1>
	   </#if>
       <#if !page.properties['page.messages']?exists>
	   <#include "/common/messages.ftl">
	   </#if>
       ${body}
       </div>
       <#if page.properties['page.sidebar']?exists>
       <div id="sidebar">
       ${page.properties['page.heading']}
       </div>
       </#if>
       <div class="clear"></div>
       <#include "/common/footer.ftl">
       <div class="clear"></div>
</div>
</body>
</html>
