<#include "/common/macros.ftl">
<#assign edit = (event?exists && event.id?exists)>
<#if edit>
<@startPage title="编辑专题" heading="编辑专题" />
<#else>
<@startPage title="创建专题" heading="创建专题" />
</#if>

<#include "/common/action_messages.ftl">
<content tag="submenu">
	<ul class="pagesnav">
		<li><a href="${urls.getURL('/console/system/event?kind='+PN.THEME)}">活动/征集/专题列表</a></li>
		<li><a href="${urls.getURL('/console/system/event_create')}">创建活动</a></li>
		<li><a href="${urls.getURL('/console/system/collection_create')}">创建征集</a></li>
		<li><a href="#" class="current">创建专题</a></li>
	</ul>
</content>

<div class="nForm">
	<@ww.form id="event_form" name="event_form" action="theme_save" namespace="/console/system" method="post" onsubmit="return formCheck();">
		<@ww.hidden id="event_kind" name="kind" value="${PN.THEME}" />
		<#if edit>
		<@ww.hidden id="event_id" name="id" value="event.id" />
		</#if>
	
		<dl>
			<dt>名称:</dt>
			<dd><@ww.textfield id="event_name" name="name" value="event.name"  size="40"/>(#)</dd>
		</dl>
		<dl>
			<dt>标签:</dt>
			<dd><@ww.textfield id="event_tags" name="tags" value="event.tags"  size="40"/></dd>
		</dl>
		<dl>
			<dt>Logo源地址:</dt>
			<dd><@ww.textfield id="event_logo" name="logo" value="event.logo"  size="40"/>(#)</dd>
		</dl>	
		
		<dl>
			<dt>Samll logo:</dt>
			<dd><@ww.textfield id="event_small_logo" name="smallLogo" value="event.smallLogo"  size="40"/></dd>
		</dl>
		
		<dl>
			<dt>链接地址:</dt>
			<dd><@ww.textfield id="event_url" name="url" value="event.url"  size="40"/>(#)</dd>
		</dl>	
		<dl>
			<dt>开始时间:</dt>
			<dd><@ww.textfield id="event_startDate" name="startDateStr" value="startDateStr"  size="40"/>(格式:YYYY-MM-DD)</dd>
		</dl>	
		<dl>
			<dt>结束时间:</dt>
			<dd><@ww.textfield id="event_endDate" name="endDateStr" value="endDateStr"  size="40"/>(格式:YYYY-MM-DD)</dd>
		</dl>
		<dl>
			<dt>排序号:</dt>
			<#if edit>
			<dd><@ww.textfield id="event_eventOrder" name="eventOrder" value="event.eventOrder"  size="4"/></dd>
			<#else>
			<dd><@ww.textfield id="event_eventOrder" name="eventOrder" value="0"  size="4"/></dd>
			</#if>
		</dl>
		<dl>
			<dt>权值:</dt>
			<#if edit>
			<dd><@ww.textfield id="event_weight" name="weight" value="event.weight"  size="4"/></dd>
			<#else>
			<dd><@ww.textfield id="event_weight" name="weight" value="0"  size="4"/></dd>
			</#if>
		</dl>
		<dl>
			<dt>描述:</dt>
		    <dd><@ww.textarea id="event_description" name="description" value="event.description" cols="45" rows="10" /></dd>
		</dl>
  		<dl>
			<dt>&nbsp;</dt>
			<dd class="submit">
				<input id="submit" type="submit" value="保存" class="Btn" />
				&nbsp;&nbsp;<a href="${urls.getURL('/console/system/event?kind='+PN.THEME)}">${action.getText('返回专题列表')}</a>
			</dd>
		</dl>
	</@ww.form>
</div>
<@script>
 	function formCheck() {
 	if ( $('event_name').value=="" ) {
 		alert("名称不能为空!");
 		return false;
 	}else if( $('event_logo').value=="" ) {
 		alert("Logo源地址不能为空!");
 		return false;
 	}else if( $('event_url').value=="" ) {
 		alert("链接地址不能为空!");
 		return false;
 	}
 	return true;
 	}
</@script>
