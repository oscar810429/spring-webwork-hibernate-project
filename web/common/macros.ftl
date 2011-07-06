<#function _ key args=''>
	<#if args?has_content>
		<#return action.getText(key, args)>
	<#else>
		<#return action.getText(key)>
	</#if>
</#function>

<#macro startPage title heading="" wrapper="">
	<title>${title}</title><#rt>
	<#if heading?has_content>
	<content tag="heading"><h1>${heading}</h1></content><#rt>
	</#if>
	<#if wrapper?has_content>
	<content tag="wrapper.id">${wrapper}</content><#rt>
	</#if>
</#macro>

<#macro script>
<script type="text/javascript">
//<![CDATA[
<#nested>
//]]>
</script>
</#macro>

<#macro js_file src v charset=''>
   <script type="text/javascript" src="${media_root}${src}?v${v}.js"<#if charset != ''> charset="${charset}"</#if>></script>
</#macro>

<#macro css_file src v media='all'>
    <link rel="stylesheet" type="text/css" href="${media_root}${src}?v${v}.css" media="${media}" />
</#macro>

<#macro scripts files v='20100328'>
	<#list files as filename>
	<script type="text/javascript" src="${media_root}/scripts/${src}.js?v${v}.js"></script>
	</#list>
</#macro>

<#macro styles files v='20100328'>
	<#list files as filename>
	<link rel="stylesheet" type="text/css" href="${media_root}/styles/${filename}.css?v${v}.css" media="all" />
	</#list>
</#macro>

<#macro joinProperty list property="id" separator=",">
	<#t><#list list as item>${item[property]}<#if item_has_next>${separator}</#if></#list>
</#macro>

<#macro userIcon user=currentUser>
	<#if user.hasBuddyIcon()>
		<#t>${user.buddyIconURL}
	<#else>
		<#t>${media_root}${user.buddyIconURL}
	</#if>
</#macro>

<#macro groupIcon group>
	<#if group.hasLogo()>
		<#t>${group.logoURL}
	<#else>
		<#t>${media_root}${group.logoURL}
	</#if>
</#macro>

<#macro doctorIcon doctor>
	<#if doctor.hasIcon()>
		<#t>${doctor.iconURL}
	<#else>
		<#t>${media_root}${doctor.iconURL}
	</#if>
</#macro>


<#macro userName user>
	<#if currentUser?exists && user==currentUser>
		<#t>${_('label.yourself')}
	<#else>
		<#t>${user.nickname?html}
	</#if>
</#macro>

<#function userNameFunc user>
	<#if currentUser?exists && user==currentUser>
		<#return action.getText('label.yourself')>
	<#else>
		<#return user.nickname?html>
	</#if>
</#function>

<#macro authUser>
	<#if currentUser?exists>
	<#nested>
	</#if>
</#macro>

<#macro sameUser user>
	<#if currentUser?exists && currentUser.equals(user)>
	<#nested>
	</#if>
</#macro>
<#macro notSameUser user>
	<#if !currentUser?exists || currentUser != user>
	<#nested>
	</#if>
</#macro>

<#function oddEven item_index odd="odd" even="even">
	<#if item_index%2 == 0>
		<#return odd>
	<#else>
		<#return even>
	</#if>
</#function>

<#function getURI>
	<#if request.queryString?exists>
		<#return request.requestURI + '?' + request.queryString>
	<#else>
		<#return request.requestURI>
	</#if>
</#function>

<#macro userHome user=currentUser>
	<#t>${urls.getUserHomeURL(user)}
</#macro>

<#function userHomeFunc user=currentUser>
	<#return urls.getUserHomeURL(user)>
</#function>

<#macro userName user>
	<#if currentUser?exists && user==currentUser>
		<#t>${action.getText('label.yourself')}
	<#else>
		<#t>${user.nickname?html}
	</#if>
</#macro>

<#function userNameFunc user>
	<#if currentUser?exists && user==currentUser>
		<#return action.getText('label.yourself')>
	<#else>
		<#return user.nickname?html>
	</#if>
</#function>

<#macro authUser>
	<#if currentUser?exists>
	<#nested>
	</#if>
</#macro>

<#--
<#macro allowedHTML>
	<span class="HtmlCode">
		(<a onclick="window.open('${urls.getURL('/popups/html_tags')}','html_tags','status=yes,scrollbars=yes,resizable=yes,width=400,height=400'); return false" href="${urls.getURL('/popups/html_tags')}" class="plain">${_('label.some.html.is.ok')}</a>)
	</span>
</#macro>
-->
<#macro brief text length>
	<#if (text?length > length)>
	<#t>${text[0..length]}...
	<#else>
	<#t>${text}
	</#if>
</#macro>

<#macro substring str firstIndex lastIndex>
	<#assign a_str = str?replace("<\\/?([\\w]*)>", "", "rmi") />	
	<#if (a_str?length > lastIndex)>
		 ${(a_str[firstIndex..lastIndex]+"...")?html}
	<#else>
	 	${a_str?html}
	</#if>
</#macro>

<#macro uncheckedPhoto size="100">
	<#t>${media_root}/images/unchecked${size}.jpg
</#macro>

<#macro selectFieldGroups name groups parentId multiple>
	<select name="${name}" <#if multiple>multiple="multiple" size="4"</#if>>
	
	<#if !multiple>
		<option value="0">${_("topLevelGroup")}</option>
	</#if>
		

	<#list groups as group1>
	${group1}
        <#if !group?exists || group1.id != group.id>
			<option value="${group1.id}">${group1.name}</option>
			<#--@selectOption group1, parentId/-->
		</#if>
	</#list>


	</select>	
</#macro>

<#--
<#macro selectOption node parentId>
	<#assign len = node.size() - 1>
	
	<#if (len >= 0)>
		<#list 0..len as i>
			<#global level = level + 2>

			<#if !group?exists || node.id != group.id>
				<option value="${node.id}"<#if parentId == node.id> selected="selected"</#if>><#list 0..level as j>&nbsp;</#list>${node.name}</option>
				<@selectOption node, parentId/>
				
				<#global level = level - 2>
			</#if>
		</#list>
	</#if>
</#macro>
-->