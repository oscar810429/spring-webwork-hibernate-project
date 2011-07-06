<#include "/common/macros.ftl">
<#assign edit = (group?exists && group.id?exists)>
<#if edit>
<@startPage title="Create Group" heading="Create group by user: ${user.nickname?html}" />
<#else>
<@startPage title="Edit Group" heading="Edit group by user: ${user.nickname?html}" />
</#if>
<#include "/common/action_messages.ftl">
<div id="main">
	<div class="inner">
	
	<form id="groups_form" name="groups_form" action="${urls.getURL('/console/groups/save_group')}" method="post">
	        <#if edit?has_content>
		    <@ww.hidden id="group_id" name="group.id" value="group.id" />
	        </#if>
			<input name="cid" id="cid" type="hidden" value="${cid}" />
			<dl>
				<#--<dd>${action.getText('label.groups.privacy.hint.'+privacy)} (<a href="${urls.getURL('/groups/create')}">${action.getText('label.groups.privacy.reselect')}</a>)</dd>-->
				
				<dt>${action.getText('label.groups.category')} <span class="Hint">${action.getText('hint.groups.category')}</span></dt>
				<dd>
					<#--<select name="categoryId" id="categoryId"></select>
					<select name="subcategoryid" id="subcategoryid"></select>-->
				
					<select id="group_category" name="categoryId">
						<#if categories?has_content>
						<#list categories as category>
							<#if category.privacyCreateGroup>
							<option value="${category.id}" <#if category.id==cid>selected</#if>>${category.name}</option>
								<#if category.categories?has_content>
								<#list category.categories as subCategory>
								<#if subCategory.privacyCreateGroup>
								<option value="${subCategory.id}">|--${subCategory.name}</option>
								</#if>
								</#list>
								</#if>
							</#if>
						</#list>
						</#if>

					</select>
				</dd>
				
	<#--  <dt>${action.getText('label.groups.areas')}<span class="Hint">${action.getText('hint.groups.areas')}</span></dt>
				<dd>

	<select id="f_region" name="f_region"> 
    <option value=""  selected="selected" >请选择省份...</option>
     <#list areas as area>
    <option value="${area.id?c}">${area.name}</option>
     </#list>
    </select>
    <select id="f_city" name="f_city"> 
    <option value="0" selected>请选择城市...</option>
    </select>
    <select id="f_subcity" name="f_subcity"> 
    <option value="0" selected>请选择县级城市...</option>
    </select>
   
				</dd>
	 -->			
				<dt>${action.getText('label.groups.name')} </dt>
				<dd><@ww.textfield id="group_name" name="group.name" value="group.name" cssClass="txt" size="60"/></dd>
				<dt>${action.getText('label.groups.telephone')}</dt>
				<dd>
					<@ww.textfield id="group_telephone" name="group.groupTelephone" value="group.groupTelephone" cssClass="txt" size="60"/>
				</dd>
				
				 <dt>${action.getText('label.groups.address')}</dt>
				<dd>
					<@ww.textfield id="group_address" name="group.groupAddress" value="group.groupAddress" cssClass="txt" size="60"/>
				</dd>
				
			    <dt>${action.getText('label.groups.website')}</dt>
				<dd>
					<@ww.textfield id="group_website" name="group.website" value="group.website" cssClass="txt" size="60"/>
				</dd>
				
				<dt>${action.getText('label.groups.role')}</dt>
				<dd>
					<@ww.textfield id="group_grouprole" name="group.groupRole" value="group.groupRole" cssClass="txt" size="60"/>
				</dd>
				
				<dt>${action.getText('label.groups.postcode')}</dt>
				<dd>
					<@ww.textfield id="group_postcode" name="group.postcode" value="group.postcode" cssClass="txt" size="60"/>
				</dd>
				
				 <dt>${action.getText('label.groups.owner')}</dt>
				<dd>
					<@ww.textfield id="group_owner" name="group.owner" value="group.owner" cssClass="txt" size="60"/>
				</dd>
				
				<dt>${action.getText('label.groups.device')}</dt>
				<dd>
					<@ww.textfield id="group_device" name="group.groupDevice" value="group.groupDevice" cssClass="txt" size="60"/>
				</dd>
				
				<dt>${action.getText('label.groups.nickname')} <span class="Hint">${action.getText('hint.groups.nickname')}</span></dt>
				<dd>
					<@ww.textarea id="group_nickname" name="group.nickname" value="group.nickname" cols="85" rows="3" />
				</dd>
				
				<dt>${action.getText('label.groups.description')} <span class="Hint">${action.getText('hint.groups.description')}</span></dt>
				<dd>
					<@ww.textarea id="group_description" name="group.description" value="group.description" cols="85" rows="15" />
				</dd>
				
				<dt>所在地区</dt>	
    		<dd><select id="userProvince" name="group.province" onchange="showcity(this.value, document.getElementById('userCity'));">
	<option value="">--请选择省份--</option>
	<option value="安徽" <#if group.province?has_content><#if group.province == "安徽">checked="checked"</#if></#if>>安徽</option> 
	<option value="北京" <#if group.province?has_content><#if group.province == "北京">checked="checked"</#if></#if>>北京</option> 
	<option value="重庆" <#if group.province?has_content><#if group.province == "重庆">checked="checked"</#if></#if>>重庆</option> 
	<option value="福建" <#if group.province?has_content><#if group.province == "福建">checked="checked"</#if></#if>>福建</option> 
	<option value="甘肃" <#if group.province?has_content><#if group.province == "甘肃">checked="checked"</#if></#if>>甘肃</option> 
	<option value="广东" <#if group.province?has_content><#if group.province == "广东">checked="checked"</#if></#if>>广东</option> 
	<option value="广西" <#if group.province?has_content><#if group.province == "广西">checked="checked"</#if></#if>>广西</option> 
	<option value="贵州" <#if group.province?has_content><#if group.province == "贵州">checked="checked"</#if></#if>>贵州</option> 
	<option value="海南" <#if group.province?has_content><#if group.province == "海南">checked="checked"</#if></#if>>海南</option> 
	<option value="河北" <#if group.province?has_content><#if group.province == "河北">checked="checked"</#if></#if>>河北</option> 
	<option value="黑龙江" <#if group.province?has_content><#if group.province == "黑龙江">checked="checked"</#if></#if>>黑龙江</option> 
	<option value="河南" <#if group.province?has_content><#if group.province == "河南">checked="checked"</#if></#if>>河南</option> 
	<option value="湖北" <#if group.province?has_content><#if group.province == "湖北">checked="checked"</#if></#if>>湖北</option> 
	<option value="湖南" <#if group.province?has_content><#if group.province == "湖南">checked="checked"</#if></#if>>湖南</option> 
	<option value="江苏" <#if group.province?has_content><#if group.province == "江苏">checked="checked"</#if></#if>>江苏</option> 
	<option value="江西" <#if group.province?has_content><#if group.province == "江西">checked="checked"</#if></#if>>江西</option> 
	<option value="吉林" <#if group.province?has_content><#if group.province == "吉林">checked="checked"</#if></#if>>吉林</option> 
	<option value="辽宁" <#if group.province?has_content><#if group.province == "辽宁">checked="checked"</#if></#if>>辽宁</option> 
	<option value="内蒙古" <#if group.province?has_content><#if group.province == "内蒙古">checked="checked"</#if></#if>>内蒙古</option> 
	<option value="宁夏" <#if group.province?has_content><#if group.province == "宁夏">checked="checked"</#if></#if>>宁夏</option> 
	<option value="青海" <#if group.province?has_content><#if group.province == "青海">checked="checked"</#if></#if>>青海</option> 
	<option value="山东" <#if group.province?has_content><#if group.province == "山东">checked="checked"</#if></#if>>山东</option> 
	<option value="上海" <#if group.province?has_content><#if group.province == "上海">checked="checked"</#if></#if>>上海</option> 
	<option value="山西" <#if group.province?has_content><#if group.province == "山西">checked="checked"</#if></#if>>山西</option> 
	<option value="陕西" <#if group.province?has_content><#if group.province == "陕西">checked="checked"</#if></#if>>陕西</option> 
	<option value="四川" <#if group.province?has_content><#if group.province == "四川">checked="checked"</#if></#if>>四川</option> 
	<option value="天津" <#if group.province?has_content><#if group.province == "天津">checked="checked"</#if></#if>>天津</option> 
	<option value="新疆" <#if group.province?has_content><#if group.province == "新疆">checked="checked"</#if></#if>>新疆</option> 
	<option value="西藏" <#if group.province?has_content><#if group.province == "西藏">checked="checked"</#if></#if>>西藏</option> 
	<option value="云南" <#if group.province?has_content><#if group.province == "云南">checked="checked"</#if></#if>>云南</option> 
	<option value="浙江" <#if group.province?has_content><#if group.province == "浙江">checked="checked"</#if></#if>>浙江</option> 
	<option value="香港" <#if group.province?has_content><#if group.province == "香港">checked="checked"</#if></#if>>香港特别行政区</option> 
	<option value="澳门" <#if group.province?has_content><#if group.province == "澳门">checked="checked"</#if></#if>>澳门特别行政区</option>
	<option value="台湾" <#if group.province?has_content><#if group.province == "台湾">checked="checked"</#if></#if>>台湾</option> 
	<option value="海外" <#if group.province?has_content><#if group.province == "海外">checked="checked"</#if></#if>>海外</option>
</select>
<select id="userCity" name="group.city"><option value="">--城市--</option></select>
<script src="${app_root}/scripts/getcity.js" type="text/javascript"></script>
</dd>
				
		    <#--<dt>${action.getText('label.groups.urlName')} <span class="Hint">${action.getText('hint.groups.urlName')}</span></dt>
				<dd><span class="url">http://www.91kuai.com.cn/</span><@ww.textfield id="group_urlName" name="group.urlName" value="group.urlName" cssClass="txt" /></dd>
			</dl>-->

  			<dl class="submit">
				<dt>&nbsp;</dt>
				<dd>
					<input id="submit" type="submit" value="${action.getText('groups.submit')}" class="Btn" />
					<span>(${action.getText('groups.or')} <a href="${urls.getURL('/groups/')}" class="cancel"> ${action.getText('groups.cancel')}</a>)</span>
				</dd>
			</dl>
		</form>
		
<script type="text/javascript">
init_province_and_city(document.getElementById('userProvince'),'<#if group.province?has_content>${group.province}</#if>',document.getElementById('userCity'),'<#if group.city?has_content>${group.city}</#if> ');
</script>
<#--
		
<@script>
	function subCategoryList(){
		<#assign k=0>
		<#list categories as category>
			<#if category.privacyCreateGroup>
				
				<#if category.categories?has_content>
					<#assign l=0>
					this[${k}] = new Array(${category.categories.size()});
					<#list category.categories as subcategory>
						<#if subcategory.privacyCreateGroup>
							this[${k}][${l}] = new Array("${subcategory.name}","${subcategory.id}");
							<#assign l= l+1>
						</#if>
					</#list>
				<#else>
					this[${k}] = new Array(0);
				</#if>
				<#assign k = k+1>
			</#if>
		</#list>
		this.length=${k};
		return this;
	}
	
	function categoryList() {
		<#assign k=0>
		<#list categories as category>
			<#if category.privacyCreateGroup>
				this[${k}] = new Array("${category.name}","${category.id}");
				<#assign k = k+1>
			</#if>
		</#list>
		this.length=${k};
		return this;
	}
	
	var categoryOp = new categoryList();
	var subCategoryOp = new subCategoryList();

	var categorySelect = new SelectChange(document.getElementById('categoryId'),"${action.getText('label.groups.select.category')}",document.getElementById('subcategoryid'), "${action.getText('label.groups.select.subcategory')}", categoryOp, subCategoryOp);
	categorySelect.initDefaultValue("","");

	$('categoryId').onchange = function(element){
		categorySelect.change();
	}
</@script>-->


	
	</div>
	
</div>

<script type="text/javascript" src="/scripts/tinymce/tiny_mce.js"></script>
<script>
tinyMCE.init({
	content_css : "/scripts/tinymce/editor_content.css",
	language : "zh",
	mode : "exact",
	elements : "group_description",
	theme : "advanced",
	plugins : "safari,emotions,media,inlinepopups",
	convert_urls: false,
	button_tile_map : true,
	invalid_elements : "script,iframe",
	theme_advanced_buttons1 : "bold,italic,underline,strikethrough,forecolor,backcolor,separator,justifyleft,justifycenter,justifyright,separator,bullist,numlist,separator,link,unlink,hr,removeformat,image,emotions,media,separator,fontselect,fontsizeselect,code",
	theme_advanced_buttons2 : "",
	theme_advanced_buttons3 : "",
	theme_advanced_toolbar_location : "top",
	theme_advanced_toolbar_align : "left",
	theme_advanced_path_location : "bottom",
	ask : false,
	tab_focus : ':prev,:next'
});
tinyMCE.baseURL = '/scripts/tinymce';
</script>