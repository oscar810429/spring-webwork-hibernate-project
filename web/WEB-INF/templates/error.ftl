<#include "/common/macros.ftl">
<@startPage title="${action.getText('base.hint.error')}" />

<content tag="messages">Do not print</content>
<div align="center">
<#if action.hasActionErrors()>
	<span class="Problem" id="errorMessages">
		<#list actionErrors as error>
		${error}<br />
		</#list>
	</span>
<#else>
	<span class="Problem">${action.getText('base.hint.error')}</span>
</#if>
</div>