<#include "/common/macros.ftl">
<#assign edit = (area?exists && area.id?exists)>
<#if edit>
<@startPage title="Edit City" 
		heading="Edit City" />
<#else>
<@startPage title="Create City" 
		heading="Create City" />
</#if>
<#include "/common/action_messages.ftl">
<content tag="submenu">
	<ul class="pagesnav">
		<li><a href="${urls.getURL('/console/system/city')}">City List</a></li>
		<#if edit>
		<li><a href="#" class="current">Edit City</a></li>
		<#else>
		<li><a href="#" class="current">Create City</a></li>
		</#if>
	</ul>
</content>

<div class="nForm">
	<@ww.form id="city_form" name="message_form" action="city_save" namespace="/console/system" method="post">
		<@ww.hidden id="city_grade" name="area.grade" value="area.grade" />
		<#if edit>
		<@ww.hidden id="city_id" name="area.id" value="area.id" />
         </#if>
         
         		
		<dl>
			<dt>Top_CityName:</dt>
			<dd><#if area.parentArea?has_content && area?has_content>${area.parentArea.name}<#else> 无上级城市</#if><#--<@ww.textfield id="top_city_name" name="area.parentArea.name" value="area.parentArea.name" size="40" />--></dd>
		</dl>
		
		<dl>
			<dt>CityName:</dt>
			<dd><@ww.textfield id="city_name" name="area.name" value="area.name" size="40" /></dd>
		</dl>

	     <dl>
			<dt>EName:</dt>
			<dd><@ww.textfield id="city_ename" name="area.ename" value="area.ename" size="40" /></dd>
		</dl>
		
	     <dl>
			<dt>AliasName1:</dt>
			<dd><@ww.textfield id="alias_name1" name="area.aliasname1" value="area.aliasname1" size="40" /></dd>
		</dl>
		
        <dl>
			<dt>AliasName2:</dt>
			<dd><@ww.textfield id="alias_name2" name="area.aliasname2" value="area.aliasname1" size="40" /></dd>
		</dl>
		
		 <dl>
			<dt>Position:</dt>
			<dd><@ww.textfield id="city_position" name="area.position" value="area.position" size="40" /></dd>
		</dl>
		
	      <dl>
			<dt>Status:</dt>
			<#if area.areaType.value()==0>
			<dd><input type="radio" name="area.areaType" value="0" checked="checked"/><label for="no_news_urlBlank">Enable</label>
			<input type="radio" name="area.areaType" value="1" /><label for="is_news_urlBlank">Disable</label></dd>
			<#else>
			<dd><input type="radio" name="area.areaType" value="0" /><label for="no_news_urlBlank">Enable</label>
			<input type="radio" name="area.areaType" value="1"  checked="checked"/><label for="is_news_urlBlank">Disable</label></dd>
			</#if>
		  </dl>
		
	    <dl>
			<dt>&nbsp;</dt>
			<dd class="submit">
				<input id="submit" type="submit" value="Submit" class="Btn" />
			</dd>
		</dl>
	</@ww.form>
</div>