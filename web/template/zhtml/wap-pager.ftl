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
			<#--${_('label.pager')}:-->

${page}/${total}${_('wap.page')}	
<#if !(total<2)>

<#if (page>1)>
			<a class="nextprev" href="${theurl?replace('$PAGE', (page-1)?c)}">${_('wap.pre')}</a>
</#if>
<#if (page<total)>
			<a class="nextprev" href="${theurl?replace('$PAGE', (page+1)?c)}">${_('wap.next')}</a>
</#if>	
<br/>
<span class="STYLE3">		
<#if (page>1)>
	<#if (page>5)>
		<#list 1..2 as i>
			<a href="${theurl?replace('$PAGE', i?c)}">${i?c}</a>
		</#list>
			<span class="break">...</span>
		<#if (page>total-1)>
			<#assign offset=total-1>
		<#else>
			<#assign offset=page-1>
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
	<#if (total-page>3)>
		<#if (page>1)>
			<#assign offset=page+1>
		<#else>
			<#assign offset=3>
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
</span>
</#if>			
        <#--span class="stat">${_('label.pager.stat', [parameters.result.total])}</span-->
