<#assign page = parameters.result.page>
<#assign total = parameters.result.totalPage>
<#if (page > total)>
	<#assign page = total + 1>
</#if>
<#if parameters.url?starts_with(base)>
<#assign theurl = response.encodeURL(parameters.url)>
<#else>
<#assign theurl = response.encodeURL(base + parameters.url)>
</#if>
			<#--${action.getText('label.pager')}:-->
<#if (page>1)>
			<a class="nextprev" href="${theurl?replace('$PAGE', (page-1)?c)}">${action.getText('label.pager.prev')}</a>
<#else>
			<span class="nextprev">${action.getText('label.pager.prev')}</span>
</#if>
			
<#if (page>1)>
	<#if (page>8)>
		<#list 1..2 as i>
			<a href="${theurl?replace('$PAGE', i?c)}">${i?c}</a>
		</#list>
			<span class="break">...</span>
		<#if (page>total-3)>
			<#assign offset=total-6>
		<#else>
			<#assign offset=page-3>
		</#if>
		<#list offset..(page-1) as i>
			<a href="${theurl?replace('$PAGE', i?c)}">${i?c}</a>
		</#list>
	<#else>
		<#list 1..(page-1) as i>
			<a href="${theurl?replace('$PAGE', i?c)}">${i?c}</a>
		</#list>
	</#if>
</#if>
			<span class="current">${page?c}</span>
<#if (total-page>0)>
	<#if (total-page>8)>
		<#if (page>3)>
			<#assign offset=page+3>
		<#else>
			<#assign offset=7>
		</#if>
		<#list (page+1)..offset as i>
			<a href="${theurl?replace('$PAGE', i?c)}">${i?c}</a>
		</#list>
			<span class="break">...</span>
		<#list (total-1)..(total) as i>
			<a href="${theurl?replace('$PAGE', i?c)}"<#if i=total> class="end"</#if>>${i?c}</a>
		</#list>
	<#else>
		<#list (page+1)..(total) as i>
			<a href="${theurl?replace('$PAGE', i?c)}"<#if i=total> class="end"</#if>>${i?c}</a>
		</#list>
	</#if>
</#if>
<#if (page<total)>
			<a class="nextprev" href="${theurl?replace('$PAGE', (page+1)?c)}">${action.getText('label.pager.next')}</a>
<#else>
			<span class="nextprev">${action.getText('label.pager.next')}</span>
</#if>
			<#--span class="stat">${action.getText('label.pager.stat', [parameters.result.total])}</span-->
