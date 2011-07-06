<#include "/common/macros.ftl">
<@startPage title="取回密码"/>
<#assign authed = currentUser?exists>
<content tag="styles">
<@css_file src="/styles/register.css" v="20100523"/>
</content>
<content tag="nav">
<div class="menu">
            
            <a href="/">首页</a> &gt;
            <font title="取回密码">取回密码</font>
            
</div>
</content>
<div style="height: 10px; width: 100%;"></div>

<div id="divRegister" class="loginwindow2">
            <div class="logintitle">
                <div>
                    设置新密码
                </div>
            </div>

    <div class="logininfo" style="padding: 40px 0pt;">
    
            <form method="post" id="passwordForm" name="passwordForm" action="${urls.getUserURL('/account/reset_password')}">
            <input type="hidden" name="token" value="${token}" />
            
             <div class="logininfotext">
                    <strong>|</strong>请输入新密码
              </div>
              <div class="inputtextdiv">
                    <input type="password" tabindex="10" class="inputtext2" id="password" name="password">
              </div>
              <div style="height: 5px;" class="logininfotext2_2"></div>
              <div class="logininfotext">
                    <strong>|</strong>请重复输入新密码
              </div>
              <div class="inputtextdiv">
                    <input type="password" tabindex="11" class="inputtext2" id="confirm_passwd" name="confirmPassword">
              </div>
              <div style="height: 5px;" class="logininfotext2_2"></div>
               <div style="margin-left: 0px; margin-top: 10px;">
              <input type="submit" class="buttonlogin btnSendEmail" id="btnRegister" tabindex="12" value="提 交"> 
              </div>
            
            </form>
    
    </div>
    
</div>   
    				