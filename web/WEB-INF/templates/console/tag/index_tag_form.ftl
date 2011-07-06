<#include "/common/macros.ftl">
<#assign edit = (indexTag?exists && indexTag.id?exists)>
<#if edit>
<@startPage title="Edit Index Tag " heading="Edit Index Tag" />
<#else>
<@startPage title="Create Index Tag" heading="Create Index Tag" />
</#if>

<content tag="submenu">
	<ul class="pagesnav">
		<li><a href="${urls.getURL('/console/tag/hometag')}">Index Tag List</a></li>
		<li><a href="#" class="current">Create Index Tag</a></li>
	</ul>
</content>

<div class="nForm">
	<@ww.form id="indexTag_form" name="indexTag_form" action="index_tag_save" namespace="/console/tag" method="post">
		<#if edit>
		<@ww.hidden id="indexTag_id" name="indexTag.id" value="indexTag.id" />
		</#if>
		<dl>
			<dt>Name:</dt>
			<dd><@ww.textfield id="indexTag_title" name="indexTag.title" value="indexTag.title" size="50" /></dd>
		</dl>
		<dl>
			<dt>Link URL:</dt>
			<dd><@ww.textfield id="indexTag_url" name="indexTag.url" value="indexTag.url" size="50" /><br/>(Empty value means generating URL according to the name)</dd>
		</dl>
		<dl>
			<dt>Weight:</dt>
			<dd><@ww.textfield id="indexTag_weight" name="indexTag.weight" value="indexTag.weight" size="10" /></dd>
		</dl>
		<dl>
			<dt>Order:</dt>
			<dd><@ww.textfield id="indexTag_order" name="indexTag.order" value="indexTag.order" size="10" /></dd>
		</dl>
		<dl>
			<dt>New window:</dt>
			<#if indexTag?has_content && indexTag.urlBlank?has_content && indexTag.urlBlank == 0>
			<dd><input type="radio" name="indexTag.urlBlank" value="0" id="no_indexTag_urlBlank" checked="checked" ><label for="no_indexTag_urlBlank">在本窗口打开链接</input>
			<input type="radio" name="indexTag.urlBlank" value="1" id="is_indexTag_urlBlank"  ><label for="is_indexTag_urlBlank">在新窗口打开链接</input></dd>
			<#else>
			<dd><input type="radio" name="indexTag.urlBlank" value="0" id="no_indexTag_urlBlank" ><label for="no_indexTag_urlBlank">在本窗口打开链接</input>
			<input type="radio" name="indexTag.urlBlank" value="1" id="is_indexTag_urlBlank"  checked="checked"><label for="is_indexTag_urlBlank">在新窗口打开链接</input></dd>
			</#if>
		</dl>	
  		<dl>
			<dt>&nbsp;</dt>
			<dd class="submit">
				<input id="submit" type="submit" value="Submit" class="Btn" />
			</dd>
		</dl>
	</@ww.form>
</div>