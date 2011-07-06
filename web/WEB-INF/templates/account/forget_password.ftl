<#include "/common/macros.ftl">
<@startPage title="忘记密码"/>
<#assign authed = currentUser?exists>
<content tag="styles">
<@css_file src="/styles/register.css" v="20100523"/>
</content>
<content tag="nav">
<div class="menu">
            
            <a href="/">首页</a> &gt;
            <font title="忘记密码">忘记密码</font>
            
</div>
</content>
<div style="height: 10px; width: 100%;"></div>
<#--
<div class="sfBox b-hot sfBox-reg clearfix">
		<div class="signinTip">
        <form class="gform" id="frm_reg" method="post" action="${urls.getURL("/account/reset_password.html")}">
			<div class="header"><h2>
				我忘了登录密码，要重设登录密码</h2>
				<div class="smallTip">请输入您注册的邮箱地址，系统将发送重置密码的链接到邮箱中</div>
			</div>
			<div class="signinTable">
				<div style="display: none;" class="error_txt" id="error_msg"></div>
				<table cellspacing="0" cellpadding="0" border="0"><tbody>
				<tr>
					<td>邮件地址</td>
					<td><input type="text" value="" class="input" id="f_email" name="email"/><span>请输入您注册帐号所用的邮箱</span></td>
				</tr>
				<tr>
					<td>验证码</td>
					<td><input type="text" class="input" size="6" name="verifyCode" id="f_vcode"/><span><a href="javascript:_rvi()">看不清吗？</a></span></td>
				</tr>
				<tr>
		    		<td> </td>
		    		<td>
		    			   <img align="absmiddle" src="${urls.getURL("/action/user/captcha")}" alt="..." id="img_vcode"/>
            			<script>function _rvi(){document.getElementById('img_vcode').src = '${urls.getURL("/action/user/captcha")}?t='+Math.random(1000);}</script>
            		</td>
		    	</tr>
				</tbody></table>
				<input type="submit" class="signinbutton" id="f_submit" value="给我发送重置密码链接»"/><span class="loginLink"><a href="${urls.getURL("/account/login.html")}">转到登录页面？</a></span>
			</div>
		</form>
		</div>
</div>
-->
<div id="divRegister" class="loginwindow2">
            <div class="logintitle">
                <div>
                    忘记密码？
                </div>
            </div>

    <div class="logininfo" style="padding: 40px 0pt;">
    
        <form class="gform" id="frm_reg" method="post" action="${urls.getURL("/account/forget_password")}">
              <div class="logininfotext">
                    <strong>|</strong>请输入您注册帐号所用的邮箱
              </div>
              <div class="inputtextdiv">
                    <input type="text" tabindex="10" class="inputtext2" id="txtKeyword" name="email">
              </div>
              <div style="height: 5px;" class="logininfotext2_2"></div>
              <div class="logininfotext">
                    <strong>|</strong>验证码
              </div>
              <div class="inputtextdiv">
                    <input type="text" class="inputtext_code" size="6" name="verifyCode" id="f_vcode" tabindex="11"/>
              </div>
              <div style="line-height: 22px; height: 50px;" class="logininfotext2">
                  <img align="absmiddle" src="${urls.getURL("/action/user/captcha")}" alt="..." id="img_vcode"/>
                  <script type="text/javascript">function _rvi(){document.getElementById('img_vcode').src = '${urls.getURL("/action/user/captcha")}?t='+Math.random(1000);}</script>
                  <a href="javascript:_rvi()">看不清吗？</a>
               </div>   
              <div style="line-height: 22px; height: 50px;" class="logininfotext2">
                    点击[确定]将发送一封密码重置确认信到您的注册邮箱，请注意查收。<br>
                    若无法收到，请查看您的垃圾邮箱中是否存在或重新发送密码重置确认信。
              </div>
              <div style="margin-left: 150px; margin-top: 10px;">
              <input type="submit" class="buttonlogin btnSendEmail" id="btnRegister" tabindex="12" value="确定"> 
              </div>       
        </form>
    
    </div>
    
</div>   