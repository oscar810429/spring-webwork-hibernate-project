	<#if session?exists && session.id?exists>
	<script type="text/javascript">
	//<![CDATA[
		var app_domain = '${app_domain}';
		var media_root = '${media_root}';
		var auth_hash = '${session.id}';
		<#if session.getAttribute('api_context')?exists>
		<#assign apiContext = session.getAttribute('api_context')>
		<#assign app = apiContext.application>
		<#if apiContext.authentication?exists>
		<#assign auth = apiContext.authentication>
		</#if>
		var api_key = '${app.apiKey?default('')}';
		var api_secret = '${app.secret?default('')}';
		<#if auth?exists>
		var api_auth_token = '${auth.token?default('')}';
		</#if>
		</#if>
		var api_endpoint = '${base}/api/rest/';
		var api_response_format = 'json';
		<#if currentUser?exists>
		var current_user = { id: '${currentUser.id}', username: '${currentUser.username?js_string}', nickname: '${currentUser.nickname?js_string}' };
		<#else>
		var current_user = null;
		</#if>
	//]]>
	</script>
	</#if>
	<#if !request.requestURL?contains("/photos/organize")>
	<script type="text/javascript" src="${media_root}/scripts/mootools.v1.2.4.js?v20100328.js"></script>
	<#if action?exists && ( admin || manager || cs )>
	<script type="text/javascript" src="${media_root}/scripts/admin.js?v20100328.js"></script>
	</#if>
	</#if>