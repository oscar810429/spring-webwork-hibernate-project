<#include "/common/macros.ftl">
<#assign edit = (news?exists && news.id?exists)>
<#if edit>
<@startPage title="Edit Image" heading="Edit Image" />
<#else>
<@startPage title="Create Image" heading="Create Image" />
</#if>

<content tag="submenu">
	<ul class="pagesnav">
		<li><a href="${urls.getURL('/console/system/image')}">Image List</a></li>
		<li><a href="#" class="current">Create Image</a></li>
	</ul>
</content>

<div class="nForm">
	<@ww.form id="news_form" name="news_form" action="image_save" namespace="/console/system" method="post" onsubmit="return formCheck();">
		<#if edit>
		<@ww.hidden id="news_id" name="news.id" value="news.id" />
		</#if>
		<input type="hidden" name="news.image" id="news.image" value="${PN.NEWS_IMAGE}" />
		<dl>
			<dt>Type:</dt>
			<dd><select name="news.kind" id="news.kind">
			<#if news?has_content>
			<option value="${PN.IMAGE_INDEX}" <#if news.kind=PN.IMAGE_INDEX> selected</#if>>未登录首页处照片</option>
			<option value="${PN.IMAGE_ACTIVITY}" <#if news.kind=PN.IMAGE_ACTIVITY> selected</#if>>首页处照片</option>	
			<option value="${PN.JS_IMAGE_INDEX}" <#if news.kind=PN.JS_IMAGE_INDEX> selected</#if>>未登录首页处照片(江苏电信)</option>
			<option value="${PN.JS_IMAGE_ACTIVITY}" <#if news.kind=PN.JS_IMAGE_ACTIVITY> selected</#if>>首页处照片(江苏电信)</option>	
			<option value="${PN.FJ_IMAGE_INDEX}" <#if news.kind=PN.FJ_IMAGE_INDEX> selected</#if>>未登录首页处照片(福建电信)</option>
			<option value="${PN.FJ_IMAGE_ACTIVITY}" <#if news.kind=PN.FJ_IMAGE_ACTIVITY> selected</#if>>首页处照片(福建电信)</option>					
			<#else>
			<option value="${PN.IMAGE_INDEX}">未登录首页处照片</option>
			<option value="${PN.IMAGE_ACTIVITY}">首页处照片</option>
			<option value="${PN.JS_IMAGE_INDEX}">未登录首页处照片(江苏)</option>
			<option value="${PN.JS_IMAGE_ACTIVITY}">首页处照片(江苏)</option>
			<option value="${PN.FJ_IMAGE_INDEX}">未登录首页处照片(福建)</option>
			<option value="${PN.FJ_IMAGE_ACTIVITY}">首页处照片(福建)</option>
			</#if>
			</select>(#)
			</dd>
		</dl>
		<dl>
			<dt>照片源地址:</dt>
			<dd><@ww.textfield id="news_title" name="news.title" value="news.title" size="50" />(#)</dd>
		</dl>
		<dl>
			<dt>链接地址:</dt>
			<dd><@ww.textfield id="news_url" name="news.url" value="news.url"  size="50"/></dd>
		</dl>	
		<dl>
			<dt>照片alt:</dt>
			<dd><@ww.textfield id="news_content" name="news.content" value="news.content" size="50"/></dd>
		</dl>
		<dl>
			<dt>用户名/ID:</dt>
			<#if news?has_content && (news.user?has_content)>
			<dd><@ww.textfield id="news_user" name="username" value="news.user.username" size="50"/></dd>
			<#else>
			<dd><@ww.textfield id="news_user" name="username" value="" size="50"/></dd>
			</#if>
		</dl>		
		<dl>
			<dt>权值:</dt>
			<#if edit>
			<dd><@ww.textfield id="news_weight" name="news.weight" value="news.weight" size="16"/></dd>
			<#else>
			<dd><@ww.textfield id="news_weight" name="news.weight" value="0" size="16"/></dd>
			</#if>
		</dl>	
		<dl>
			<dt>类型:</dt>
			<dd>图片</dd>
		</dl>	
		<dl>
			<dt>打开模式:</dt>
			<#if news?has_content && news.urlBlank?has_content && news.urlBlank == 0>
			<dd><input type="radio" name="news.urlBlank" value="0" id="no_news_urlBlank" checked="checked" /><label for="no_news_urlBlank">在本窗口打开链接</label>
			<input type="radio" name="news.urlBlank" value="1" id="is_news_urlBlank"  /><label for="is_news_urlBlank">在新窗口打开链接</label></dd>
			<#else>
			<dd><input type="radio" name="news.urlBlank" value="0" id="no_news_urlBlank" /><label for="no_news_urlBlank">在本窗口打开链接</label>
			<input type="radio" name="news.urlBlank" value="1" id="is_news_urlBlank"  checked="checked"/><label for="is_news_urlBlank">在新窗口打开链接</label></dd>
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
 	if ( $('news_title').value=="" ) {
 		alert("照片源地址不能为空!");
 		return false;
 	}
 	return true;
 	}
</@script>