<#if pn_local_errors?has_content>
	<div class="Problem" id="errorMessages">	
		<#list pn_local_errors as error>
		${error}<br />
		</#list>
	</div>
</#if>
<#if pn_local_messages?has_content>
	<div class="Confirm" id="successMessages">
		<#list pn_local_messages as msg>
		${msg}<br />
		</#list>
	</div>
</#if>