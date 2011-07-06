<#include "/common/macros.ftl">
<#assign edit = (textLinkage?exists && textLinkage.id?exists)>
<#if edit>
<@startPage title="Edit text link" heading="Edit text link" />
<#else>
<@startPage title="Create text link" heading="Create text link" />
</#if>

<content tag="submenu">
	<ul class="pagesnav">
		<li><a href="${urls.getURL('/console/link/textLink')}">Text Link List</a></li>
		<li><a href="#" class="current">Create Text Link</a></li>
	</ul>
</content>

<div class="nForm">
	<@ww.form id="text_link_form" name="text_link_form" action="text_link_save" namespace="/console/link" method="post">
		<#if edit>
		<@ww.hidden id="text_link_id" name="textLinkage.id" value="textLinkage.id" />
		</#if>
		<dl>
			<dt>Name:</dt>
			<dd><@ww.textfield id="text_link_name" name="textLinkage.title" value="textLinkage.title"  size="50"/></dd>
		</dl>	
		<dl>
			<dt>Link URL:</dt>
			<dd><@ww.textfield id="text_link_url" name="textLinkage.url" value="textLinkage.url"  size="50"/></dd>
		</dl>	
		<dl>
			<dt>Order:</dt>
			<dd><@ww.textfield id="text_link_order" name="textLinkage.order" value="textLinkage.order" size="16"/></dd>
		</dl>		
  		<dl>
			<dt>&nbsp;</dt>
			<dd class="submit">
				<input id="submit" type="submit" value="Submit" class="Btn" />
			</dd>
		</dl>
	</@ww.form>
</div>