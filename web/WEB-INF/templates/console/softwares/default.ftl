<#include "/common/macros.ftl">
<@startPage title="Edit group" heading="Edit software by user: ${user.nickname?html}" />

<div class="categories">
	<#if categories?has_content>
	<#list categories as category>
	<div class="BB">
		<div id="parent${category.id}">
			<#if category.categories?has_content>
			<img class="category" src="${media_root}/images/context_closed_click.gif" border="0" onclick="javascript:showSubCategory('${category.id}',this);" />
			<#else><img class="category" src="${media_root}/images/context_open_click.gif" border="0" onclick="javascript:showSubCategory('${category.id}',this);" />
			</#if>
			<a href="${urls.getURL('/console/softwares/categories_softwares')}?id=${category.id}">${category.name}</a> | 
			${action.getText("label.category.canCreateGroup")} <#if category.privacyCreateGroup>Yes<#else>No</#if> |
			${action.getText("label.category.canCreateSubCategory")} <#if category.privacyCreateSubcategory>Yes<#else>No</#if> | 
			<a href="${urls.getURL('/console/softwares/categories_edit')}?id=${category.id}">Edit</a> |
			<a href="${urls.getURL('/console/softwares/import_software')}?cid=${category.id}">Import Software</a>
			<#if !category.categories?has_content> | 
			<a onclick="return window.confirm('Do U wanna delete this category?');" href="${urls.getURL('/console/softwares/categories_delete')}?id=${category.id}">Delete</a>
			</#if>
		</div>
		<div id="parent${category.id}_child" style="display:none;">
		<#list category.categories as subCategory>
			<div>
			<a href="${urls.getURL('/console/softwares/categories_softwares')}?id=${subCategory.id}">${subCategory.name}</a> | 
			${action.getText("label.category.canCreateGroup")} <#if subCategory.privacyCreateGroup>Yes<#else>No</#if> |
			${action.getText("label.category.canCreateSubCategory")} <#if subCategory.privacyCreateSubcategory>Yes<#else>No</#if> | 
			<a href="${urls.getURL('/console/softwares/categories_edit')}?id=${subCategory.id}">Edit</a> | 
			<a href="${urls.getURL('/console/softwares/import_software')}?cid=${category.id}">Import Software</a> | 
			<a onclick="return window.confirm('Do U wanna delete this category?');" href="${urls.getURL('/console/softwares/categories_delete')}?id=${subCategory.id}">Delete</a>		
			</div>
		</#list>
		</div>
	</div>
	</#list>
	<#else>
	<div>We have no category infomation now!</div>
	</#if>
	<div class="clear"></div>
	<form name="add" action="${urls.getURL('/console/softwares/categories_add')}" method="post">
	<div class="categories_add">
		<p>category name: <#--<input type="text" name="category.name" value=""/>--><@ww.textarea id="categoryname_id" name="categoryName" value="" cols="40" rows="5" /></p>
		<p>parent category:<select name="category.parentId"><option value="0">Top Category</option>
		<#if categories?has_content>
		<#list categories as category>
			<#if category.privacyCreateSubcategory>
			<option value="${category.id}">${category.name}</option>
			</#if>
		</#list>
		</#if>
		</select></p>
		<p>can create software: <input type="radio" name="category.privacyCreateGroup" value="true" checked /> Yes | 
		<input type="radio" name="category.privacyCreateGroup" value="false" /> No</p>
		<p>can create subCategory: <input type="radio" name="category.privacyCreateSubcategory" value="true" /> Yes | 
		<input type="radio" name="category.privacyCreateSubcategory" value="false" checked /> No</p>
		<p><input type="submit" value="add category"/></p>
		
	</div>
	</form>
</div>
<@script>

	function showSubCategory(obj,obj2){

		var children = document.getElementById("parent"+obj+"_child");
		if (children.style.display == 'none') {
			children.style.backgroundColor='#f3f3f3';
			children.style.display="block";
			obj2.src='${media_root}/images/context_open_click.gif';
		}else{
			children.style.backgroundColor='#000000';
			children.style.display="none";
			obj2.src='${media_root}/images/context_closed_click.gif';
		}
	}
	
</@script>
