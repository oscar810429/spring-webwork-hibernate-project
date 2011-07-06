<#include "/common/macros.ftl">
<#assign edit = (message?exists && message.id?exists)>
<#if edit>
<@startPage title="Edit System Message" 
		heading="Edit System Message" />
<#else>
<@startPage title="Create System Message" 
		heading="Create System Message" />
</#if>

<content tag="submenu">
	<ul class="pagesnav">
		<li><a href="${urls.getURL('/console/system/messages')}">Message List</a></li>
		<li><a href="#" class="current">Create Message</a></li>
	</ul>
</content>

<div class="nForm">
	<@ww.form id="message_form" name="message_form" action="messages_save" namespace="/console/system" method="post">
		<#if edit>
		<@ww.hidden id="message_id" name="message.id" value="message.id" />
		</#if>
		<dl>
			<dt>Subject:</dt>
			<dd><@ww.textfield id="message_subject" name="message.subject" value="message.subject" size="40" /></dd>
		</dl>
		<dl>
			<dt>发送对象:</dt>
			<dd>
				<select name="message.targetUserType">
					<option value="">所有用户</option>
					<option value="freeman">仅免费用户</option>
					<option value="vip_business,vip_normal">所有VIP</option>
					<option value="vip_business">仅商业VIP</option>
					<option value="vip_normal">仅普通VIP</option>
				</select>
			</dd>
		</dl>
		<dl>
			<dt>Content:</dt>
			<dd><@ww.textarea id="message_content" name="message.content" value="message.content" cols="45" rows="10" /></dd>
		</dl>
		<dl>
			<dt>Expire Date:</dt>
			<dd><@ww.textfield id="message_expireDate" name="message.expireDate" value="message.expireDate" />(yyyy-MM-dd hh:mm)</dd>
		</dl>

  		<dl>
			<dt>&nbsp;</dt>
			<dd class="submit">
				<input id="submit" type="submit" value="Submit" class="Btn" />
			</dd>
		</dl>
	</@ww.form>
</div>
