<#include "/common/macros.ftl">
<@startPage title="Edit software" heading="Edit software by user: ${user.nickname?html}" />

<div class="categories">
	<div class="clear"></div>
	<form name="edit" action="${urls.getURL('/console/softwares/categories_add')}" method="post">
	
	<div class="edit_category">
		<input type="hidden" name="id" value="${category.id}">
		<p>category name: <input type="text" name="category.name" value="${category.name}"/></p>
		<#if category.parentCategory?exists>
		<p>parent category:${category.parentCategory.name}</p>
		</#if>
		<#if category.privacyCreateGroup>
		<p>can create software: <input type="radio" name="category.privacyCreateGroup" value="true" checked /> Yes | 
		<input type="radio" name="category.privacyCreateGroup" value="false" /> No</p>
		<#else>
		<p>can create software: <input type="radio" name="category.privacyCreateGroup" value="true"  /> Yes | 
		<input type="radio" name="category.privacyCreateGroup" value="false" checked /> No</p>
		</#if>
		<#if category.privacyCreateSubcategory>
		<p>can create subCategory: <input type="radio" name="category.privacyCreateSubcategory" checked value="true" /> Yes | 
		<input type="radio" name="category.privacyCreateSubcategory" value="false"  /> No</p>
		<p><input type="submit" value="add category"/></p>
		<#else>
		<p>can create subCategory: <input type="radio" name="category.privacyCreateSubcategory" value="true" /> Yes | 
		<input type="radio" name="category.privacyCreateSubcategory" value="false" checked /> No</p>
		<p><input type="submit" value="add category"/></p>
		</#if>
	</div>
	</form>
</div>
