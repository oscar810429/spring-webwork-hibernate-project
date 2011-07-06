<div id="menu"></div>

<script type="text/javascript">
//<![CDATA[
	
	<#if currentUser.isInRole(PN.CS_ROLE)>
	var menu = [
		['user', 'users', ['search','fast_apply']],
		['software', 'softwares', ['search', 'categories']],
		//['group', 'groups', ['search', 'categories']],
		//['photo', 'photos', ['search','comment_search']],
		['system', 'system', ['abuse','taobao_feedback']],
	]
	<#elseif currentUser.isInRole(PN.FINANCE_ROLE)>
	var menu = [
		['Vip', 'vip', ['paid_order','paid_upgrade','manage_order']]
	]
	<#elseif currentUser.isInRole(PN.AGENT_MANAGER_ROLE)>
	var menu = [
		['agent', 'agent', ['agent']]
	]
	<#else>
	var menu = [
		['user', 'users', [<#if currentUser.isInRole('admin')>'edit_role', 'ip_record', </#if>'fast_apply', 'search']],
		['software', 'softwares', ['search', 'categories']],
		//['group', 'groups', ['search', 'categories']],
		['system', 'system', ['city','news', 'messages','image','event','forums'<#if currentUser.isInRole('admin')>,'abuse', 'jobs', 'caches'</#if> ]],
		['link', 'link', ['partner', 'textLink','pictureLink']],
		['tag', 'tags', ['search', 'tags','new_tag']],
		['cooperation', 'cooperation', ['collaborator', 'partner_domains']]
		//['cooperation', 'cooperation', ['collaborator','exchange','stat_exchange', 'partner_domains']]
		<#--if currentUser.isInRole('admin')>
		,['Vip', 'vip', ['product','manage_order', 'paid_order','paid_upgrade', 'blocked_buyer']],
		 ['agent', 'agent', ['agent']]
		</#if-->
	]
	</#if>

	var uri = '${request.requestURI}'.split('/').filter(function(s){ return s!=''; });
	
	var current = -1;
	
	var menuContainer = $('menu');
	var triggers = [];
	var contents = [];
	
	menu.each(function(m, idx) {
		if (uri[1] == m[1]) { current = idx; }
		
		var div = new Element('div').addClass('MGroup').addClass('MEntry').injectInside(menuContainer);
		var trigger = new Element('a').setProperty('href', '#').setHTML(m[0]).injectInside(div);
		var cdiv = new Element('div').addClass('MGroupContent').injectInside(div);
		
		triggers.push(trigger);
		contents.push(cdiv);
		
		m[2].each(function(sm) {
			var txt = sm.replace('_', ' ').capitalize();
			var fdiv = new Element('div').addClass('MFile').addClass('MEntry').injectInside(cdiv); 
			if (current == idx && uri[2].indexOf(sm) == 0) { 
				fdiv.setProperty('id', 'MSelected').setHTML(txt);
			} else {
				new Element('a').setProperty('href', '/' + uri[0] + '/' + m[1] + '/' + sm+'')
					.setHTML(txt).injectInside(fdiv);
			}
		});
	});
	
	new Accordion(triggers, contents, { show: current });
	
   
//]]>
</script>