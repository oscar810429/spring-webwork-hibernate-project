<#include "/common/macros.ftl">
<@startPage title="System Image" heading="System Image" />

<content tag="submenu">
	<ul class="pagesnav">
		<li><a href="#" class="current">Image List</a></li>
		<li><a href="${urls.getURL('/console/system/image_create')}">Create Image</a></li>
	</ul>
</content>

<#assign categories={PN.NEWS_SYSTEM?string: 'System', PN.NEWS_GROUPS?string: 'Group',PN.NEWS_IMPORTANT?string: 'Important',PN.NEWS_TAG?string: 'Tag',PN.IMAGE_INDEX?string: '未登录首页处照片',PN.IMAGE_ACTIVITY?string: '首页照片',PN.JS_IMAGE_ACTIVITY?string: '首页照片(江苏)',PN.JS_IMAGE_INDEX?string: '未登录首页照片(江苏)',PN.FJ_IMAGE_ACTIVITY?string: '首页照片(福建)',PN.FJ_IMAGE_INDEX?string: '未登录首页照片(福建)'}>

<div class="listdiv">
	
	<table width="100%" border="0" cellspacing="0" cellpadding="0" class="listable">
		<thead>
		<tr>
			<th>类型</th>
			<th>照片</th>
			<th>权值</th>
			<th>创建时间</th>
			<th>操作</th>
		</tr>
		</thead>
		<tbody>
			<#list result.data as news>
			
			<tr<#if news_index%2=1> class="odd"</#if>>
				<td>${categories[news.kind?string]}</td>
				<td><a href="${news.url?default('#')}"><img src="${news.title}" alt="${news.title}" border="0"/></a></td>
				<td>${news.weight?default('')}</td>
				<td>${news.createdDate?date}</td>
				<td>
					<a href="${urls.getURL('/console/system/image_edit')}?id=${news.id}">编辑</a>
					<a href="${urls.getURL('/console/system/image_delete')}?id=${news.id}" onclick="return confirm('确认删除?')">删除</a>
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
		<span class="prev">&laquo; <a href="${urls.getURL('/console/system/image')}?page=${result.page - 1}">上一页</a></span>
		</#if>
		<#if result.hasNext()>
		<span class="next"><a href="${urls.getURL('/console/system/image')}?page=${result.page + 1}">下一页</a> &raquo;</span>
		</#if>
	</div>
	</#if>
</div>
