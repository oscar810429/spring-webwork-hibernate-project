<#include "/common/macros.ftl">

<#assign edit = (tag?exists && tag.id?exists)>
<#if edit>
<@startPage title="Edit Tag " heading="Edit Tag" />
<#else>
<@startPage title="Create Tag" heading="Create Tag" />
</#if>


<div class="nForm">
	<@ww.form id="tag_form" name="tag_form" action="tag_save" namespace="/console/tags" method="post" onsubmit="return formCheck();">
		<#if edit>
		<@ww.hidden id="tag_id" name="tag.id" value="tag.id" />
		</#if>
		<dl>
			<dt>名称:</dt>
			<dd><@ww.textfield id="tag_title" name="tag.title" value="tag.title" size="50" />(#)</dd>
		</dl>
		<dl>
			<dt>类型</dt>
			<dd>
				<select name="tag.kind" id="tag_kind">
					<option value="${PN.HOME_TAG}" <#if tag?has_content && tag.kind== PN.HOME_TAG>selected</#if>>未登录时首页标签</option>
					<option value="${PN.USER_HOME_TAG}" <#if tag?has_content && tag.kind== PN.USER_HOME_TAG>selected</#if>>PN首页标签</option>
					<option value="${PN.EXPLORE_TAG}" <#if tag?has_content && tag.kind== PN.EXPLORE_TAG>selected</#if>>逛逛处标签</option>
					<option value="${PN.CURRENT_DAY_TAG}" <#if tag?has_content && tag.kind== PN.CURRENT_DAY_TAG>selected</#if>>24小时内标签</option>
					<option value="${PN.HOT_TAG}" <#if tag?has_content && tag.kind== PN.HOT_TAG>selected</#if>>热门标签</option>
				</select>
				(#)
			</dd>
		</dl>
		<dl>
			<dt>链接地址:</dt>
			<dd><@ww.textfield id="tag_url" name="tag.url" value="tag.url" size="50" /><br/>(默认为该标签名对应的地址)</dd>
		</dl>
		<dl>
			<dt>权值:</dt>
			<#if edit>
			<dd><@ww.textfield id="tag_weight" name="tag.weight" value="tag.weight" size="10" /></dd>
			<#else>
			<dd><@ww.textfield id="tag_weight" name="tag.weight" value="0" size="10" /></dd>
			</#if>
		</dl>
		<dl>
			<dt>排序号:</dt>
			<#if edit>
			<dd><@ww.textfield id="tag_order" name="tag.order" value="tag.order" size="10" /></dd>
			<#else>
			<dd><@ww.textfield id="tag_order" name="tag.order" value="0" size="10" /></dd>
			</#if>
		</dl>
		<dl>
			<dt>打开模式:</dt>
			<#if tag?has_content && tag.urlBlank?has_content && tag.urlBlank == 0>
			<dd><input type="radio" name="tag.urlBlank" value="0" id="no_tag_urlBlank" checked="checked" ><label for="no_tag_urlBlank">在本窗口打开链接</input>
			<input type="radio" name="tag.urlBlank" value="1" id="is_tag_urlBlank"  ><label for="is_tag_urlBlank">在新窗口打开链接</input></dd>
			<#else>
			<dd><input type="radio" name="tag.urlBlank" value="0" id="no_tag_urlBlank" ><label for="no_tag_urlBlank">在本窗口打开链接</input>
			<input type="radio" name="tag.urlBlank" value="1" id="is_tag_urlBlank"  checked="checked"><label for="is_tag_urlBlank">在新窗口打开链接</input></dd>
			</#if>
		</dl>	
  		<dl>
			<dt>&nbsp;</dt>
			<dd class="submit">
				<input id="submit" type="submit" value="保存" class="Btn" />
			</dd>
		</dl>
	</@ww.form>
</div>
<@script>
 	function formCheck() {
 	if ( $('tag_title').value=="" ) {
 		alert("标签名不能为空!");
 		return false;
 	}
 	return true;
 	}
</@script>