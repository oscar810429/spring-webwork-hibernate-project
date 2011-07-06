<#include "/common/macros.ftl">
<#assign edit = (partnerImage?exists && partnerImage.id?exists)>
<#if edit>
<@startPage title="Edit partner" heading="Edit partner" />
<#else>
<@startPage title="Create partner" heading="Create partner" />
</#if>

<content tag="submenu">
	<ul class="pagesnav">
		<li><a href="${urls.getURL('/console/link/partner')}">Partner List</a></li>
		<li><a href="#" class="current">Create Partner</a></li>
	</ul>
</content>

<div class="nForm">
	<@ww.form id="partner_form" name="p_form" action="partner_save" namespace="/console/link" method="post">
		<#if edit>
		<@ww.hidden id="partner_id" name="partnerImage.id" value="partnerImage.id" />
		</#if>
		<dl>
			<dt>Name:</dt>
			<dd><@ww.textfield id="partner_name" name="partnerImage.title" value="partnerImage.title"  size="50"/></dd>
		</dl>	
		<dl>
			<dt>Logo URL:</dt>
			<dd><@ww.textfield id="partnerImage_src" name="partnerImage.src" value="partnerImage.src" size="50" /></dd>
		</dl>
		<dl>
			<dt>Link URL:</dt>
			<dd><@ww.textfield id="partnerImage_url" name="partnerImage.url" value="partnerImage.url"  size="50"/></dd>
		</dl>	
		<dl>
			<dt>Image alt:</dt>
			<dd><@ww.textfield id="partnerImage_alt" name="partnerImage.alt" value="partnerImage.alt" size="50"/></dd>
		</dl>	
		<dl>
			<dt>Order:</dt>
			<dd><@ww.textfield id="partner_order" name="partnerImage.order" value="partnerImage.order" size="16"/></dd>
		</dl>		
  		<dl>
			<dt>&nbsp;</dt>
			<dd class="submit">
				<input id="submit" type="submit" value="Submit" class="Btn" />
			</dd>
		</dl>
	</@ww.form>
</div>