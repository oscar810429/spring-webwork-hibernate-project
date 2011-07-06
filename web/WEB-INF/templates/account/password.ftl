<#include "/common/macros.ftl">
<#assign authed = currentUser?exists>
<@startPage title="修改密码"/>
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
            <font title="${user.nickname}的个人主页">${user.nickname}的个人主页</font>
            
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
                    ${user.nickname?html}</div>
                <div class="username-r">
                </div>
            </div>
            </#if>
            
</div>

<#if authed>
<script type="text/javascript">
     var userprofile = new Hash({ realname: '${user.profile.name?js_string}', description: '<#if user.profile.description?has_content>${user.profile.description?js_string}<#else>这个家伙很懒什么也没有留下!!!</#if>'});
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
                       <div style="float: left;" id="divSpaceName"> <div style="display: block;"><span class="spacename">${user.nickname?html}个人空间</span></div></div>
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

<div id="divUpdateUserInfo" class="updateuserinfo-border" style="top: 194px; left: 229px; display: block;">
    <ul class="tabhead">
        <li id="showUploadPhoto" class="">上传头像</li>
        <li id="showModifyPwd" class="curr">修改密码</li>
        <li id="showBaseInfo"><a href="${urls.getURL("/profile/admin")}">基本信息</a></li>
        
        <li id="showCutPhoto">裁剪头像</li>
        
    </ul>
    <div class="main" id="divUploadPhotoMain" style="display: block;">
    
    <form class="gform" id="frm_profile" method="POST" action="${urls.getURL("/profile/save_password")}">
    <label>旧的登录密码</label>
    <p><input type="password" style="width: 150px;" class="text" name="oldpassword"/></p>
      <label>新密码</label>    
     <p><input type="password" style="width: 150px;" class="text" maxlength="20" name="password"/></p>
     <label>再次输入新密码</label>    
     <p><input type="password" style="width: 150px;" class="text" name="confirmPassword"/></p>
     <p>
        <input type="submit" class="submit" value="修改密码"/>
     </p>
   </form>

   </div>
   
</div>   
