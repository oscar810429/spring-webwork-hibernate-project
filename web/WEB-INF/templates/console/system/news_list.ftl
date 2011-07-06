<#include "/common/macros.ftl">
<@startPage title="System News" heading="System News" />

<content tag="submenu">
	<ul class="pagesnav">
		<li><a href="#" class="current">News List</a></li>
		<li><a href="${urls.getURL('/console/system/news_create')}">Create News</a></li>
	</ul>
</content>

<#assign categories={PN.NEWS_SYSTEM?string: '公告', PN.NEWS_AD?string: 'advertisement',PN.NEWS_GROUPS?string: '群公告',PN.NEWS_IMPORTANT?string: 'Important',PN.NEWS_TAG?string: '未登录首页标语',PN.NEWS_BULLETIN?string: '逛逛处公告',PN.NEWS_BLOG?string: 'Blog新闻'}>
<@ww.form id="tag_form" name="tag_form" action="/console/system/news" method="post" >
	<label>Type:</label>
	<select name="kind" id="kind">
		<option value="${PN.NEWS_SYSTEM}" <#if kind?exists && kind=PN.NEWS_SYSTEM> selected</#if>>公告</option>
		<option value="${PN.NEWS_GROUPS}" <#if kind?exists && kind=PN.NEWS_GROUPS> selected</#if>>群公告</option>
		<option value="${PN.NEWS_AD}" <#if kind?exists && kind=PN.NEWS_AD> selected</#if>>advertisement</option>
		<option value="${PN.NEWS_IMPORTANT}" <#if kind?exists && kind=PN.NEWS_IMPORTANT> selected</#if>>important news</option>
		<option value="${PN.NEWS_TAG}" <#if kind?exists && kind=PN.NEWS_TAG> selected</#if>>未登录首页标语</option>
		<option value="${PN.NEWS_BULLETIN}" <#if kind?exists && kind=PN.NEWS_BULLETIN> selected</#if>>逛逛处公告</option>
		<option value="${PN.NEWS_BLOG}" <#if kind?exists && kind=PN.NEWS_BLOG> selected</#if>>Blog新闻</option>
	</select>
	<input id="submit" type="submit" value="Submit" class="Btn" />
</@ww.form>
<#if result?has_content && result.data.size() gt 0>
<div class="listdiv">
	
	<table width="100%" border="0" cellspacing="0" cellpadding="0" class="listable">
		<thead>
			<th>类型</th>
			<th>标语</th>
			<th>创建时间</th>
			<th>过期时间</th>
			<th>操作</th>
		</thead>
		<tbody>
			<#list result.data as news>
			<tr<#if news_index%2=1> class="odd"</#if>>
				<td>${categories[news.kind?string]}</td>
				<td><#if news.url?has_content><a href="${news.url?default('#')}">${news.title?html}<#else>${news.title}</#if></a></td>
				<td>${news.createdDate?date}</td>
				<td><#if news.expiredDate?exists>${news.expiredDate?date}<#else>Never</#if></td>
				<td>
					<a href="${urls.getURL('/console/system/news_edit')}?id=${news.id}">编辑</a>
					<a href="${urls.getURL('/console/system/news_delete')}?id=${news.id}" onclick="return confirm('确认删除?')">删除</a>
				</td>
			</tr>
			</#list>
		</tbody>
	</table>

	<#--
	<#list result.data as news>
	<div class="BB<#if news_index%2=0> Blue</#if>">
		<h4 class="Top">${news.createdDate?date?string} | 
		<#if news.kind=PN.NEWS_SYSTEM>System news
		<#elseif news.kind=PN.NEWS_GROUPS>Groups news
		<#elseif news.kind=PN.NEWS_AD>advertisement | 
		<#if news.url="${PN.PAGE_KIND}">index page
		<#else>
			<#if categories?has_content>
				<#list categories as tmpCategory>
					<#if news.url=tmpCategory.id>${tmpCategory.name}</#if>
					<#if tmpCategory.categories?has_content>
						<#list tmpCategory.categories as subCategory>
							<#if news.url = subCategory.id>${subCategory.name}</#if>
						</#list>
					</#if>
				</#list>
			</#if>
		</#if>
		<#else><font color="red">error</font>
		</#if></h4>
		<div class="Content">${news.title}</div>
		<form action="${urls.getURL('/console/system/delete_news')}" method="post">
			<input type="hidden" name="id" value="${news.id}" />
			<input type="submit" class="CancelBtn" value="Delete"/>
		</form>
		
		<form action="<#if news.kind=PN.NEWS_AD>${urls.getURL('/console/system/edit_news_ad')}
		<#else>${urls.getURL('/console/system/edit_news')}</#if>" method="post">
			<input type="hidden" name="id" value="${news.id}" />
			<input type="submit" class="Btn" value="Edit"/>
		</form>
		<div class="clear"></div>
	</div>
	</#list>
	
	<div class="clear"></div>
	-->
	<#if !result.isFirst() || result.hasNext()>
	<div class="nextprev">
		<#if !result.isFirst()>
		<span class="prev">&laquo; <a href="${urls.getURL('/console/system/news')}?kind=${kind}&page=${result.page - 1}">上一页</a></span>
		</#if>
		<#if result.hasNext()>
		<span class="next"><a href="${urls.getURL('/console/system/news')}?kind=${kind}&page=${result.page + 1}">下一页</a> &raquo;</span>
		</#if>
	</div>
	</#if>
</#if>
</div>
