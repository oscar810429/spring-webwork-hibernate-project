<#include "/common/macros.ftl">
<#assign edit = (news?exists && news.id?exists)>
<#if edit>
<@startPage title="Edit System News" heading="Edit System News" />
<#else>
<@startPage title="Create System News" heading="Create System News" />
</#if>

<content tag="submenu">
	<ul class="pagesnav">
		<li><a href="${urls.getURL('/console/system/news')}">News List</a></li>
		<li><a href="#" class="current">Create News</a></li>
	</ul>
</content>

<div class="nForm">
	<@ww.form id="news_form" name="news_form" action="news_save" namespace="/console/system" method="post">
		<#if edit>
		<@ww.hidden id="news_id" name="news.id" value="news.id" />
		</#if>
		<dl>
			<dt>Type:</dt>
			<dd><select name="news.kind" id="news.kind">
			<#if news?has_content>
			<option value="${PN.NEWS_SYSTEM}" <#if news.kind=PN.NEWS_SYSTEM> selected</#if>>公告</option>
			<option value="${PN.NEWS_GROUPS}" <#if news.kind=PN.NEWS_GROUPS> selected</#if>>群公告</option>
			<option value="${PN.NEWS_AD}" <#if news.kind=PN.NEWS_AD> selected</#if>>advertisement</option>
			<option value="${PN.NEWS_IMPORTANT}" <#if news.kind=PN.NEWS_IMPORTANT> selected</#if>>import news</option>
			<option value="${PN.NEWS_TAG}" <#if news.kind=PN.NEWS_TAG> selected</#if>>未登录首页标语</option>
			<option value="${PN.NEWS_BULLETIN}" <#if news.kind=PN.NEWS_BULLETIN> selected</#if>>逛逛处公告</option>
			<option value="${PN.NEWS_BLOG}" <#if news.kind=PN.NEWS_BLOG> selected</#if>>Blog新闻</option>
			<#else>
			<option value="${PN.NEWS_SYSTEM}">公告</option>
			<option value="${PN.NEWS_GROUPS}">群公告</option>
			<option value="${PN.NEWS_AD}">advertisement</option>
			<option value="${PN.NEWS_IMPORTANT}">important news</option>
			<option value="${PN.NEWS_TAG}">未登录首页标语</option>
			<option value="${PN.NEWS_BULLETIN}">逛逛处公告</option>
			<option value="${PN.NEWS_BLOG}">Blog新闻</option>
			</#if>
			</select>
			</dd>
		</dl>
		<dl>
			<dt>标语:</dt>
			<dd><@ww.textarea id="news_title" name="news.title" value="news.title" cols="45" rows="4" /></dd>
		</dl>
		<dl>
			<dt>链接地址:</dt>
			<dd><@ww.textfield id="news_url" name="news.url" value="news.url"  size="40"/></dd>
		</dl>
		<dl>
			<dt>类型:</dt>
			<dd>文本</dd>
			<input type="hidden" name="news.image" id="news.image" value="${PN.NEWS_TEXT}" />
		</dl>		
  		<dl>
			<dt>&nbsp;</dt>
			<dd class="submit">
				<input id="submit" type="submit" value="保存" class="Btn" />
			</dd>
		</dl>
	</@ww.form>
</div>