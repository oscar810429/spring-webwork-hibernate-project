<#include "/common/macros.ftl">
<#assign edit = (partner?exists && partner.id?exists)>
<#if edit>
<@startPage title="Edit partner" heading="Edit partner" />
<#else>
<@startPage title="Create partner" heading="Create partner" />
</#if>
<content tag="submenu">
	<ul class="pagesnav">
		<li><a href="${urls.getURL('/console/cooperation/partner')}">Partner list</a></li>
		<li><a href="#" class="current">Create Partner</a></li>
	</ul>
</content>

<div class="nForm">
	<@ww.form id="partner_form" name="partner_form" action="partner_save" namespace="/console/cooperation" method="post" onsubmit="return formCheck();">
		<#if edit>
		<@ww.hidden id="partner_id" name="partner.id" value="partner.id" />
		</#if>
		<dl>
			<dt>Name:</dt>
			<dd><@ww.textfield id="partner_name" name="partner.name" value="partner.name"  size="50"/>(#)</dd>
		</dl>	
		<dl>
			<dt>Url:</dt>
			<dd><@ww.textfield id="partner_returnUrl" name="partner.returnUrl" value="partner.returnUrl" size="50" /></dd>
		</dl>
		
		<dl>
			<dt>Yupoo url:</dt>
			<dd><@ww.textfield id="partner_ypUrl" name="partner.ypUrl" value="partner.ypUrl" size="50" />(#)</dd>
		</dl>
		
		<dl>
			<dt>Yupoo key:</dt>
			<dd><@ww.textfield id="partner_ypKey" name="partner.ypKey" value="partner.ypKey" size="50" />(#)</dd>
		</dl>
		
		<dl>
			<dt>YP return url:</dt>
			<dd><@ww.textfield id="partner_ypReturnUrl" name="partner.ypReturnUrl" value="partner.ypReturnUrl" size="50" />(#)</dd>
		</dl>
		
		<dl>
			<dt>Token:</dt>
			<dd><@ww.textfield id="partner_token" name="partner.token" value="partner.token" size="50" />(#)</dd>
		</dl>
		
  		<dl>
			<dt>&nbsp;</dt>
			<dd class="submit">
				<input id="submit" type="submit" value="Submit" class="Btn" />
			</dd>
		</dl>
	</@ww.form>
</div>

<@script> 
function formCheck() {
	if ($('partner_name').value == "" || $('partner_ypUrl').value == "" || $('partner_ypKey').value == "" || $('partner_ypReturnUrl').value == "" || $('partner_token').value == "") {
		alert("Name,yupoo url,yupoo key,yp return url,token should not be absent!");
		return false;
	}else 
		return true;
}
</@script>