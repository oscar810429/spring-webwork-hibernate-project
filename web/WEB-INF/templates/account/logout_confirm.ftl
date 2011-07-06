<#include "/common/macros.ftl">
<@startPage title="${action.getText('logout.title')}" />

<div class="thin">
	<p class="Confirm">${action.getText("messages.logout.success")}</p>
	<p>${action.getText("label.click.here")}, <a href="${urls.getURL('/account/login.html')}">${action.getText("label.login.again")}</a></p>
	<p>${action.getText("label.or")}, <a href="${media_root}">${action.getText("label.return.home")}</a>.</p> 
</div>
