<#include "/common/macros.ftl">
<@startPage title="Edit group" heading="Edit group by user: ${user.nickname?html}" />

<div class="group">
	<#if group?has_content>
	
	<div class="BB">
		<a href="${urls.getURL('/groups')}?id=${group.id}">${group.name}</a>
	</div>
	<div><select name="privacy">
	<option value="0"<#if group.getGroupType()=0> selected</#if>>PRIVATE</option>
	<option value="1"<#if group.getGroupType()=1> selected</#if>>VERIFY</option>
	<option value="2"<#if group.getGroupType()=2> selected</#if>>PUBLIC</option>
	</select></div>
	<#list group.categories as category>
	<div>${category.name}</div>
	</#list>
	<#else>
	<div>We have no category in this groups now!</div>
	</#if>
	<div class="clear"></div>
</div>
