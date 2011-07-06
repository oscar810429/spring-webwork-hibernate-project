<#include "/common/macros.ftl">
<#assign authed = currentUser?exists>
<@startPage title="站内留言"/>
<content tag="styles">
  <@styles files=['profile'] />
  <@css_file src="/styles/themes/green.css" v="20100523"/>
</content>

 <content tag="scripts">
  <script type="text/javascript" src="${media_root}/scripts/editable.js?v20100713.js"></script>
</content>
 

<content tag="nav">
<div class="menu">
            
            <a href="/">首页</a> &gt;
            <font title="${currentUser.nickname}的个人主页">${currentUser.nickname}的个人主页</font>
            
</div>
</content>

<content tag="profiletop">

<div class="l">
            <div title="点击看大图" class="img ShowBigImage">
                <img width="100" height="100" source="#" src="<@userIcon user=user/>" id="imgCurrUserPhoto" style="margin-left: 0px; margin-top: 0pt;">
            </div>
            <#if authed>
            <a id="btnUpdateUserInfo" class="button" href="javascript:;">修改资料</a>
            <#else>
            <div class="username">
                <div class="username-l">
                </div>
                <div class="username-m">
                    ${currentUser.nickname?html}</div>
                <div class="username-r">
                </div>
            </div>
            </#if>
            
</div>

<#if authed>
<script type="text/javascript">
     var userprofile = new Hash({ realname: '${currentUser.profile.name?js_string}', description: '<#if currentUser.profile.description?has_content>${currentUser.profile.description?js_string}<#else>这个家伙很懒什么也没有留下!!!</#if>'});
     userprofile.extend(current_user);
</script>
</#if>
 
<div class="r">
            <div class="spacename-border">
                    <#if authed>
                        <div style="float: left;" id="divSpaceName">
                        </div>
                        <script type="text/javascript">
                           new leopard.UserEditor('divSpaceName',userprofile, 'title', { displayStyle: 'spacename', inputStyle: 'spaceNameText' } );            
                        </script>
                        
                    <#else>
                       <div style="float: left;" id="divSpaceName"> <div style="display: block;"><span class="spacename">${currentUser.nickname?html}个人空间</span></div></div>
                    </#if>   
   
                    
               
                <a title="如何使用个人空间？" style="float: right;" class="help" target="_blank" href="#"></a>
            </div>
            <div class="userintro-border">
                <#if authed>            
                <div style="float: left;" id="divUserIntro">
                </div>
                    <script type="text/javascript">
                           new leopard.UserEditor('divUserIntro',userprofile, '', { displayStyle: 'userintro', inputStyle: 'userinfoText' } );            
                    </script>
              <#else>
                 <div style="float: left;" id="divUserIntro">
                    <div style="display: block;">
                        <span class="userintro">
                            这家伙很懒,什么也没有留下</span>
                        
                    </div>
                </div>
              </#if>
                
            </div>
            <div class="personshow-border">
                <div class="left">
                </div>
                <div class="middle">
                    <div class="head">
                        <div class="title">
                            我的个人秀：
                        </div>
                        <a title="如何使用个人秀？" style="float: left; margin-top: 4px;" class="help" target="_blank" href="＃">
                        </a>
                        
                        <a style="float: right; margin-top: 5px; width: 60px;" id="btnAddPersonalShow" class="button" href="javascript:;">我还要秀</a>
                        
                    </div>
                    <div id="divPersonalShowList" class="list">
                        <a href="#">个性资料</a>
                    </div>
                </div>
                <div class="right">
                </div>
            </div>
</div> 

</content>  

<div class="main bottomLine_5px">
<div id="msgTabs">
<ul>
	<li><a href="${urls.getURL("/profile/messages/inbox")}">接收到的留言</a></li>
	<li><a class="selected" href="${urls.getURL("/profile/messages/outbox")}">已发留言</a></li>
</ul>
</div>

<div id="msgbox">
<h2>共收到 ${result.total} 条站内留言.</h2>
<ul>
<#list result.data as message>
	<li id="msg_${message.id}">
		<span class="time">${action.getText("label.before", [DateUtils.distanceInWords(message.sentDate)])}</span>
		<span class="sender"><a target="_blank" href="${urls.getURL("/profile/home")}?uid=${message.sender.username}">${message.sender.username}</a></span>
		<span class="msg"><a title="点击查看全文" href="${urls.getURL("/profile/messages/read")}?id=${message.id}">${message.subject}</a></span>
		<span class="opts">
			<a href="${urls.getURL("/profile/messages/delete")}?id=${message.id}">删除</a>
		</span>
		<div class="clear"></div>
	</li>
</#list>	
</ul>
<#if (result.totalPage > 1)>
	<div class="pages">
		<@pn.pager result="result" url="${urls.getURL('/profile/messages/outbox')}?page=$PAGE" />
	</div>
</#if>
</div>

</div>