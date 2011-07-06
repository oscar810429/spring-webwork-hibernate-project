<#include "/common/macros.ftl">
<@startPage title="${action.getText('forum.create.title')}" 
		heading="${action.getText('forum.create.heading')}" />

<content tag="submenu">
	<ul class="pagesnav">
		<li><a href="${urls.getURL('/console/system/forums')}">Forum List</a></li>
		<li><a href="#" class="current">Create Forum</a></li>
	</ul>
</content>

<div class="nForm">
	<@ww.form id="forum_form" name="forum_form" action="forums_save" namespace="/console/forums" method="post">
		<#if forum?exists && forum.id?exists>
		<@ww.hidden id="forum_id" name="forum.id" value="forum.id" />
		</#if>
		<dl>
			<dt>${action.getText('forum.create.name')}</dt>
			<dd><@ww.textfield id="forum_name" name="forum.name" value="forum.name" size="30" /></dd>
			
			<dt>${action.getText('forum.create.description')}</dt>
			<dd><@ww.textarea id="forum_description" name="forum.description" value="forum.description" cols="45" rows="5" /></dd>
			
			<dt>${action.getText('forum.create.position')}</dt>
			<dd>
				<@ww.textfield id="forum_position" name="forum.position" value="forum.position" size="6" />
				<#--<span>(${action.getText('forum.create.position.hint')})</span>-->
			</dd>
		</dl>

  		<dl>
			<dt>&nbsp;</dt>
			<dd class="submit">
				<input id="submit" type="submit" value="${action.getText('forum.create.submit')}" />
				<span>(or <a href="#" class="cancel" onclick="toggleDisplay('show-create-account','create-account');"> Cancel</a>)</span>
			</dd>
		</dl>
	</@ww.form>
</div>
