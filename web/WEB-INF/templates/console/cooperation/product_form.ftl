<#include "/common/macros.ftl">
<#assign edit = (product?exists && product.id?exists)>
<#if edit>
<@startPage title="Edit product" heading="Edit product" />
<#else>
<@startPage title="Create product" heading="Create product" />
</#if>

<content tag="submenu">
	<ul class="pagesnav">
		<li><a href="${urls.getURL('/console/cooperation/product')}">Product list</a></li>
		<li><a href="#" class="current">Create product</a></li>
	</ul>
</content>

<#if partnerList?has_content>
<div class="nForm">
	<form id="product_form" name="product_form" action="product_save" namespace="/console/cooperation" method="post" onsubmit="return formCheck();">
		<#if edit>
		<@ww.hidden id="product_id" name="product.id" value="product.id" />
		</#if>
		<dl>
			<dt>Partner:</dt>
			<dd>
				<select name="partnerId" id="partnerId">
				<#list partnerList as partner>
				<#if (product?has_content)&&(partnerId?has_content)&&(partner.id == partnerId)>
				<option value="${partner.id}"  selected>${partner.name}</option>
				<#else>
				<option value="${partner.id}">${partner.name}</option>
				</#if>
				</#list>
				</select>
			</dd>
		</dl>
		<dl>
			<dt>Name:</dt>
			<dd><@ww.textfield id="product_name" name="product.name" value="product.name"  size="50"/>(#)</dd>
		</dl>	
		
		<dl>
			<dt>Code:</dt>
			<dd><@ww.textfield id="product_code" name="product.code" value="product.code"  size="50"/>(#)</dd>
		</dl>
		<dl>
			<dt>Price:</dt>
			<dd><@ww.textfield id="product_code" name="product.price" value="product.price"  size="50"/>(数值)</dd>
		</dl>
		
		<dl>
			<dt>Description:</dt>
			<dd><@ww.textarea id="product_description" name="product.description" value="product.description"  cols="50" rows="4"/></dd>
		</dl>
		
  		<dl>
			<dt>&nbsp;</dt>
			<dd class="submit">
				<input id="submit" type="submit" value="Submit" />
			</dd>
		</dl>
	</form>
</div>
<#else>
The partner list is empty. First,<a href="${urls.getURL('/console/cooperation/partner_create')}" >create partners</a> ,please!
</#if>

<@script> 
function formCheck() {
	if ($('product_name').value == "" || $('partnerId').value == "" || $('product_code').value == "" ) {
		alert("标记了'#'的为必填项!");
		return false;
	}else {
		return true;
	}
}

function IsInteger(string) 
{  
	var integer; 
	integer = parseInt(string); 
	if (isNaN(integer)) { 
		return false; 
	} else if (integer.toString().length==string.length) {  
		return true; 
	} else 
		return false;  
} 

</@script>