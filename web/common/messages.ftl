<#--
<#if action?exists>
<#if action.hasActionErrors()>
	<div class="Problem" id="errorMessages">	
		<#list actionErrors as error>
		${error}<br />
		</#list>
	</div>
</#if>

<#if action.hasActionMessages()>
	<div class="Confirm" id="successMessages">
		<#list actionMessages as msg>
		${msg}<br />
		</#list>
	</div>
</#if>

<#if action.hasFieldErrors()>
	<div class="Problem" id="errorMessages">
		<#list fieldErrors?keys as field>
		${fieldErrors[field][0]}<br />
		</#list>
	</div>
</#if>
</#if>
-->
<#if pn_errors?has_content>
	<div class="Problem" id="errorMessages">	
		<#list pn_errors as error>
		${error}<br />
		</#list>
	</div>
</#if>
<#if pn_messages?has_content>
	<div class="Confirm" id="successMessages">
		<#list pn_messages as msg>
		${msg}<br />
		</#list>
	</div>
</#if>