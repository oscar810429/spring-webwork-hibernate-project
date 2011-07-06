<#include "/common/macros.ftl">
<#assign edit = (collaborator?exists && collaborator.id?exists && collaborator.secret?has_content)>
<#if edit>
<@startPage title="Edit collaborator" heading="Edit collaborator" />
<#else>
<@startPage title="Create collaborator" heading="Create collaborator" />
</#if>
<content tag="submenu">
	<ul class="pagesnav">
		<li><a href="${urls.getURL('/console/cooperation/collaborator')}">Collaborator</a></li>
		<#if edit>
		<li><a href="${urls.getURL('/console/cooperation/collaborator_create')}">Create collaborator</a></li>
		<li><a href="#" class="current">Edit collaborator</a></li>
		<#else>
		<li><a href="#" class="current">Create collaborator</a></li>
		</#if>
	</ul>
</content>

<div class="nForm">
	<#if edit>
	<@ww.form id="collaborator_form" name="collaborator_form" action="collaborator_update" namespace="/console/cooperation" method="post" onsubmit="return formCheck1();">
		<@ww.hidden id="collaborator_id" name="collaborator.id" value="collaborator.id" />
		<dl>
			<dt>Name:</dt>
			<dd><@ww.textfield id="collaborator_name" name="collaborator.name" value="collaborator.name"  size="50"/>(#)</dd>
		</dl>	
		
		<dl>
			<dt>Id:</dt>
			<dd>${collaborator.id}</dd>
		</dl>
		
		<dl>
			<dt>Secret:</dt>
			<dd>${collaborator.secret}</dd>
		</dl>
		
		<dl>
			<dt>Home URL:</dt>
			<dd><@ww.textfield id="collaborator_homeURL" name="collaborator.homeURL" value="collaborator.homeURL" size="50" />(请包含：http://)</dd>
		</dl>
		
		<dl>
			<dt>Logo URL:</dt>
			<dd><@ww.textfield id="collaborator_logoURL" name="collaborator.logoURL" value="collaborator.logoURL" size="50" />(请包含：http://)</dd>
		</dl>
		
		<dl>
			<dt>Host Addresses:</dt>
			<dd><@ww.textfield id="collaborator_hostAddresses" name="hostAddressesStr" value="hostAddressesStr" size="50" maxlength="250"/>(多个地址之间用','分开.)</dd>
		</dl>
		
		<dl>
			<dt>Description:</dt>
			<dd><@ww.textarea id="collaborator_description" name="collaborator.description" value="collaborator.description" cols="45" rows="5" /></dd>
		</dl>
		
  		<dl>
			<dt>&nbsp;</dt>
			<dd class="submit">
				<input id="submit" type="submit" value="Submit" class="Btn" />
			</dd>
		</dl>
	</@ww.form>
	<#else>
	<@ww.form id="collaborator_form" name="collaborator_form" action="collaborator_save" namespace="/console/cooperation" method="post" onsubmit="return formCheck2();">
		<dl>
			<dt>Id:</dt>
			<dd><@ww.textfield id="collaborator_id" name="collaborator.id" value="collaborator.id"  size="50" maxlength="32"/>(#Id在整个合作伙伴的数据中唯一,长度在32位以内)</dd>
		</dl>
		<dl>
			<dt>Name:</dt>
			<dd><@ww.textfield id="collaborator_name" name="collaborator.name" value="collaborator.name"  size="50"/>(#)</dd>
		</dl>	
		<dl>
			<dt>Home URL:</dt>
			<dd><@ww.textfield id="collaborator_homeURL" name="collaborator.homeURL" value="collaborator.homeURL" size="50" /></dd>
		</dl>
		
		<dl>
			<dt>Logo URL:</dt>
			<dd><@ww.textfield id="collaborator_logoURL" name="collaborator.logoURL" value="collaborator.logoURL" size="50" /></dd>
		</dl>
		
		<dl>
			<dt>Host Addresses:</dt>
			<dd><@ww.textfield id="collaborator_hostAddresses" name="hostAddressesStr" value="hostAddressesStr" size="50" maxlength="250"/>(多个地址之间用','分开.)</dd>
		</dl>
		
		<dl>
			<dt>Description:</dt>
			<dd><@ww.textarea id="collaborator_description" name="collaborator.description" value="collaborator.description" cols="45" rows="5" /></dd>
		</dl>
		
  		<dl>
			<dt>&nbsp;</dt>
			<dd class="submit">
				<input id="submit" type="submit" value="Submit" class="Btn" />
			</dd>
		</dl>
	</@ww.form>
	</#if>
</div>

<@script> 
function formCheck1() {
	if( $('collaborator_name').value =="") {
		alert("The name should not be absent!");
	} else 
		return true;
}
function formCheck2() {
	if( $('collaborator_name').value =="" || $('collaborator_id').value =="") {
		alert("The name and id should not be absent!");
	} else 
		return true;
}
</@script>
