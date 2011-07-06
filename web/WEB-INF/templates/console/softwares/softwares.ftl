<#include "/common/macros.ftl">
<@startPage title="Edit group" heading="Edit software by user: ${user.nickname?html}" />

<div class="groups">
	<div>You can view <a href="${urls.getURL('/console/softwares/categories')}">all categories</a> |
	<#if category?has_content>
		<#if category.parentCategory?has_content>
		 parent category <a href="${urls.getURL('/console/softwares/categories_groups')}?id=${category.parentCategory.id}">
			${category.parentCategory.name}</a> | 
		</#if>
	 	current <a href="${urls.getURL('/console/softwares/categories_groups')}?id=${category.id}">${category.name}</a>!
	 </#if>
	 </div>
	 
	 <div id="searchGroup">
		<form name="search" action="${urls.getURL('/console/softwares/search')}" method="post">
			Software name <input type="text" name="name" value="${name?default('')?html}"><input type="submit" value="Search">
			<select name="id">
				<option value="0">Change category</option>
				<#if category?has_content>
					<#assign c = category>
					<#if categories?has_content>
					<#list categories as category>
						<option value="${category.id}" <#if category.id=c.id>selected</#if>>${category.name}</option>
						<#if category.categories?has_content>
						<#list category.categories as subCategory>
						<option value="${subCategory.id}" <#if subCategory.id=c.id>selected</#if>>|--${subCategory.name}</option>
						</#list>
						</#if>
					</#list>
					</#if>
				<#else>
					<#if categories?has_content>
					<#list categories as category>
						<option value="${category.id}">${category.name}</option>
						<#if category.categories?has_content>
						<#list category.categories as subCategory>
						<option value="${subCategory.id}">|--${subCategory.name}</option>
						</#list>
						</#if>
					</#list>
					</#if>
				</#if>
			</select>	
		</form>
	</div>
	 
	<#if result?has_content>
	
	<#if category?exists>
		<#assign pagerurl = urls.getURL('/console/softwares/categories_softwares?id=' + category.id + '&amp;page=$PAGE')>
	<#else>
		<#assign pagerurl = urls.getURL('/console/softwares/categories_softwares?page=$PAGE')>
	</#if>
	

	<#list result.data as group>
	<div class="BB">

		Software name "<a href="${urls.getURL('/softwares/view')}?id=${group.id}" target="_blank">${group.name}</a>" | you can  
		<a href="${urls.getURL('/console/softwares/software_edit')}?id=${group.id}&cid=${id}">Edit</a> property
		<#-- | 
		<a href="${urls.getURL('/profile/groups/department/list')}?id=${group.id}" target="_blank">Group Department List</a>
		 | 
		<a href="${urls.getURL('/profile/groups/people/list')}?id=${group.id}" target="_blank">Group Doctor List</a>
		| -->
		<a onclick="return window.confirm('Do U wanna delete this group?');"  href="${urls.getURL('/console/softwares/delete_software')}?id=${group.id}&cid=${category.id}">Delete</a>

	</div>
	</#list>
	<div class="pages">
		<@pn.pager result="result" url="${pagerurl}" />
	</div>
	<#else>
	<div>We have no groups in this category now!</div>
	</#if>
	
	<div class="clear"></div>
</div>
