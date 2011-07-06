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

<div id="divSendEmailSuccess" class="loginwindow2">
            <div class="logintitle">
                <div>
                  确认信已发送
                </div>
            </div>

    <div class="protocolinfo">
                <div class="loginlletters">
                </div>
                <div class="successinfo">
                    <div class="loginwelcome">
                        密码重置确认信已发送到您的邮箱中：
                    </div>
                    <div class="loginnameinfo">
                        一封邮件已经发送到您的邮箱，请您在24小时内及时确认并重置密码。
                        <br>
                        <br>
                        <font class="cd27530">若没有收到，请检查您的垃圾邮箱中是否存在，<br>
                        或点击下面的按钮重新发送密码重置确认信</font><br>
                        <a class="linkbutton btnSendEmail" href="javascript:history.back();">重新发送</a>
                    </div>
                    <br>
                </div>
        </div>
    
</div>   