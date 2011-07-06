<#include "/common/macros.ftl">
<#assign edit = (pictureLink?exists && pictureLink.id?exists)>
<#if edit>
<@startPage title="Edit picture link" heading="Edit picture link" />
<#else>
<@startPage title="Create picture link" heading="Create picture link" />
</#if>

<content tag="submenu">
	<ul class="pagesnav">
		<li><a href="${urls.getURL('/console/link/pictureLink')}">Picture Link List</a></li>
		<li><a href="#" class="current">Create Picture Link</a></li>
	</ul>
</content>

<div class="nForm">
	<@ww.form id="picture_link_form" name="picture_link_form" action="picture_link_save" namespace="/console/link" method="post">
		<#if edit>
		<@ww.hidden id="picture_link_id" name="pictureLink.id" value="pictureLink.id" />
		</#if>
		<dl>
			<dt>Name:</dt>
			<dd><@ww.textfield id="picture_link_name" name="pictureLink.title" value="pictureLink.title"  size="50"/></dd>
		</dl>	
		<dl>
			<dt>Logo URL:</dt>
			<dd><@ww.textfield id="picture_link_src" name="pictureLink.src" value="pictureLink.src" size="50" /></dd>
		</dl>
		<dl>
			<dt>Link URL:</dt>
			<dd><@ww.textfield id="picture_link_url" name="pictureLink.url" value="pictureLink.url"  size="50"/></dd>
		</dl>	
		<dl>
			<dt>Image alt:</dt>
			<dd><@ww.textfield id="picture_link_alt" name="pictureLink.alt" value="pictureLink.alt" size="50"/></dd>
		</dl>	
		<dl>
			<dt>Order:</dt>
			<dd><@ww.textfield id="picture_link_order" name="pictureLink.order" value="pictureLink.order" size="16"/></dd>
		</dl>		
  		<dl>
			<dt>&nbsp;</dt>
			<dd class="submit">
				<input id="submit" type="submit" value="Submit" class="Btn" />
			</dd>
		</dl>
	</@ww.form>
</div>