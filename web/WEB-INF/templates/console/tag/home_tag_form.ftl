<#include "/common/macros.ftl">
<#assign edit = (homeTag?exists && homeTag.id?exists)>
<#if edit>
<@startPage title="Edit Home Tag " heading="Edit Home Tag" />
<#else>
<@startPage title="Create Home Tag" heading="Create Home Tag" />
</#if>

<content tag="submenu">
	<ul class="pagesnav">
		<li><a href="${urls.getURL('/console/tag/userhometag')}">Home Tag List</a></li>
		<li><a href="#" class="current">Create Home Tag</a></li>
	</ul>
</content>

<div class="nForm">
	<@ww.form id="homeTag_form" name="homeTag_form" action="home_tag_save" namespace="/console/tag" method="post">
		<#if edit>
		<@ww.hidden id="homeTag_id" name="homeTag.id" value="homeTag.id" />
		</#if>
		<dl>
			<dt>Name:</dt>
			<dd><@ww.textfield id="homeTag_title" name="homeTag.title" value="homeTag.title" size="50" /></dd>
		</dl>
		<dl>
			<dt>Link URL:</dt>
			<dd><@ww.textfield id="homeTag_url" name="homeTag.url" value="homeTag.url" size="50" /><br/>(Empty value means generating URL according to the name)</dd>
		</dl>
		<dl>
			<dt>Weight:</dt>
			<dd><@ww.textfield id="homeTag_weight" name="homeTag.weight" value="homeTag.weight" size="10" /></dd>
		</dl>
		<dl>
			<dt>Order:</dt>
			<dd><@ww.textfield id="homeTag_order" name="homeTag.order" value="homeTag.order" size="10" /></dd>
		</dl>
		<dl>
			<dt>New window:</dt>
			<#if homeTag?has_content && homeTag.urlBlank?has_content && homeTag.urlBlank == 0>
			<dd><input type="radio" name="homeTag.urlBlank" value="0" id="no_homeTag_urlBlank" checked="checked" ><label for="no_homeTag_urlBlank">在本窗口打开链接</input>
			<input type="radio" name="homeTag.urlBlank" value="1" id="is_homeTag_urlBlank"  ><label for="is_homeTag_urlBlank">在新窗口打开链接</input></dd>
			<#else>
			<dd><input type="radio" name="homeTag.urlBlank" value="0" id="no_homeTag_urlBlank" ><label for="no_homeTag_urlBlank">在本窗口打开链接</input>
			<input type="radio" name="homeTag.urlBlank" value="1" id="is_homeTag_urlBlank"  checked="checked"><label for="is_homeTag_urlBlank">在新窗口打开链接</input></dd>
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