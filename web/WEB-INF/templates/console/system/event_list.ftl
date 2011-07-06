<#include "/common/macros.ftl">
<#if kind?exists && kind=PN.EVENT><@startPage title="活动" heading="活动" />
<#elseif kind?exists && kind=PN.COLLECTION><@startPage title="征集" heading="征集" />
<#else>
<@startPage title="专题" heading="专题" />
</#if>

<#include "/common/action_messages.ftl">
<content tag="submenu">
	<ul class="pagesnav">
		<li><a href="#" class="current">活动/征集/专题列表</a></li>
		<li><a href="${urls.getURL('/console/system/event_create')}">创建活动</a></li>
		<li><a href="${urls.getURL('/console/system/collection_create')}">创建征集</a></li>
		<li><a href="${urls.getURL('/console/system/theme_create')}">创建专题</a></li>
	</ul>
</content>

<@ww.form id="tag_form" name="tag_form" action="/console/system/event" method="post" >
	<label>Type:</label>
	<select name="kind" id="kind">
		<option value="${PN.EVENT}" <#if kind?exists && kind=PN.EVENT> selected</#if>>活动</option>
		<option value="${PN.COLLECTION}" <#if kind?exists && kind=PN.COLLECTION> selected</#if>>征集</option>
		<option value="${PN.THEME}" <#if kind?exists && kind=PN.THEME> selected</#if>>专题</option>
	</select>
	<label>Page size</label>
	<@ww.textfield id="pageSize" name="pageSize" value="pageSize" size="4" />
	<input id="submit" type="submit" value="Submit" class="Btn" />
</@ww.form>
<#if result?has_content && result.data.size() gt 0>
<div class="listdiv">
	<table width="100%" border="0" cellspacing="0" cellpadding="0" class="listable">
		<thead>
			<#if kind?exists && kind=PN.EVENT><th>别名</th></#if>
			<th>名称</th>
			<#if kind?exists && (kind != PN.COLLECTION)>
			<th>排序号</th>
			<#else>
			<th>类型</th>
			</#if>
			<th>开启状态</th>
			<th>时间</th>
			<th>操作</th>
		</thead>
		<tbody>
			<#list result.data as event>
			<tr<#if event_index%2=1> class="odd"</#if>>
				<#if kind?exists && kind=PN.EVENT><td>${event.alias}</td></#if>
				<td><a href="${event.url?default('#')}">${event.name}</a></td>
				<#if kind?exists && (kind != PN.COLLECTION)>
				<td>${event.eventOrder}</td>
				<#else>
					<#if event.tags=PN.COLLECTION_NEW>
					<td>最新征集</td> 
					<#elseif event.tags=PN.COLLECTION_LONG>
					<td>长期征集</td> 
					<#elseif event.tags=PN.COLLECTION_PASS>
					<td>入选征集</td> 
					</#if>
				</#if>
				<td><#if event.weight?has_content &&(event.weight > 0 )>开启<#elseif (event.weight = 0 )>关闭<#else>停止</#if></a></td>
				<td>${event.startDate?date}${action.getText('event.until')}${event.endDate?date}</td>
				<td>
					<#if (kind = PN.EVENT)>
				    <a href="${urls.getURL('/console/system/event_edit')}?id=${event.id}">详情</a>
					<a href="${urls.getURL('/console/system/event_edit')}?id=${event.id}">编辑</a>
					<#elseif (kind = PN.COLLECTION)>
				    <a href="${urls.getURL('/console/system/collection_edit')}?id=${event.id}">详情</a>
					<a href="${urls.getURL('/console/system/collection_edit')}?id=${event.id}">编辑</a>
					<#else>
					<a href="${urls.getURL('/console/system/theme_edit')}?id=${event.id}">详情</a>
					<a href="${urls.getURL('/console/system/theme_edit')}?id=${event.id}">编辑</a>
					</#if>
					<a href="${urls.getURL('/console/system/event_delete')}?id=${event.id}" onclick="return confirm('确认删除吗?')">删除</a>
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
		<span class="prev">&laquo; <a href="${urls.getURL('/console/system/event')}?kind=${kind}&page=${result.page - 1}&pageSize=${pageSize}">上一页</a></span>
		</#if>
		<#if result.hasNext()>
		<span class="next"><a href="${urls.getURL('/console/system/event')}?kind=${kind}&page=${result.page + 1}&pageSize=${pageSize}">下一页</a> &raquo;</span>
		</#if>
	</div>
	</#if>
	</#if>
</div>
