<#include "/common/macros.ftl">
<@startPage title="会员登录"/>
<#assign authed = currentUser?exists>
<content tag="styles">
<@css_file src="/styles/login.css" v="20100523"/>
</content>
<content tag="nav">
<div class="menu">
            
            <a href="/">首页</a> &gt;
            <font title="会员登录">会员登录</font>
            
</div>
</content>
<div style="height: 20px; width: 100%;"></div>
<form class="gform" id="frm_login" name="login_form" action="${urls.getURL('/account/auth/')}" method="post">
<div style="height:280px;float:left;" id="login">
   <#if j_uri?exists>
    <input type="hidden" name="j_uri" value="${j_uri?html}" />
    </#if>
    <#if params['forward']?exists>
    <input type="hidden" name="forward" value="${request.getParameter('forward')?html}" />
    </#if>
<div id="divLogin">
    <div class="h">
        <div class="hl">
        </div>
        <div class="hm">
            <div class="icon-logintitle">
            </div>
        </div>
        <div class="hr">
        </div>
    </div>
    <div class="m">
        <div class="ml">
        </div>
        <div class="mm">
            <div class="link">
            </div>
            <div class="main">
                <div class="tips">
                    <div class="icon">
                    </div>
                    <div class="msg">
                        爱我所爱，汇我所会</div>
                </div>
                <div class="list">
                    <div class="text">
                        注册邮箱／昵称
                    </div>
                    <div class="input">
                        <input type="text"  tabindex="1" <#if session?exists && session.getAttribute('SECURITY_LAST_USERNAME')?has_content> value="${session.getAttribute('SECURITY_LAST_USERNAME')?html}"</#if> id="txtLoginName" class="txt" name="j_username">
                        <a target="_blank" href="${urls.getURL('/account/signup/')}">免费注册</a>
                    </div>
                </div>
                <div class="list">
                    <div class="text">
                        登录密码
                    </div>
                    <div class="input">
                        <input type="password"  tabindex="2" value="" id="txtLoginPwd" class="txt" name="j_password">
                        <a target="_blank" href="${urls.getURL("/account/forget_password")}">忘记密码</a>
                    </div>
                </div>
                <div style="height: 20px; width: 160px; margin-top: 2px;" class="list">
                    <input type="checkbox" tabindex="4" value="1" id="chbRememberLoginName" style="margin-left: 5px;" class="float-l" name="j_remember_me" checked>
                    <div class="float-l text1 autoLogin">
                       ${action.getText('login.remeberMe')}</div>
                    <#--<input type="checkbox" tabindex="4" value="1" id="chbRememberLoginName" style="margin-left: 5px;" class="float-l" name="j_remember_me">
                    <div class="float-l text1 rememberUserName">
                       ${action.getText('login.remeberMe')}</div>-->
                </div>
                <div class="actions">
                <div tabindex="5" id="btnCancelLogin" onmouseout="this.className='btn'" onmouseover="this.className='btnover'">
                    <input name="login" tabindex="5" type="submit" value="登  录" class="btn"/>
                 </div>
                 <#--<div tabindex="6" id="btnCancelLogin" onmouseout="this.className='btn'" onmouseover="this.className='btnover'">
                    <input name="loginreset" tabindex="5" type="reset" value="取  消" class="btn"/>
                 </div>    
                    <div tabindex="5" id="btnSubmitLogin" onmouseout="this.className='btn'" onmouseover="this.className='btnover'" class="btn">
                        登&nbsp;&nbsp;录</div>
                    
                    <div tabindex="6" id="btnCancelLogin" onmouseout="this.className='btn'" onmouseover="this.className='btnover'" class="btn">
                        取&nbsp;&nbsp;消</div>-->
                    
                </div>
            </div>
        </div>
        <div class="mr">
        </div>
    </div>
    <div class="f">
        <div class="fl">
        </div>
        <div class="fm">
        </div>
        <div class="fr">
        </div>
    </div>
</div>
</div>



 <div style="float: right; width: 300px;margin-top:50px;margin-right:0px;margin-bottom:0px;margin-left:50px;" class="tipbox" id="login_tip">
  <h3>登录后可以？</h3>
  <ol>
    <li>参与开源软件/新闻的讨论和评论</li>
    <li>和别人分享软件使用心得</li>
    <li>将你的文章、图书推荐到开源派牛网</li>
  </ol>
  <p>
    </p><h3>本站支持使用下列账号登录</h3>
    <table width="100%" style="margin-top: 20px;"><tbody><tr>
    <td><a href="#"><img src="${media_root}/images/google_25wht.gif"></a></td>
    <td style="padding-top: 6px;"><a href="#"><img src="${media_root}/images/yahoo.gif"></a></td>
    </tr></tbody></table>
  
  </div>
  <div class="clear"></div>



</form>
