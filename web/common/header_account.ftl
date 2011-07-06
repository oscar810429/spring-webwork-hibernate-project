<#assign authed = currentUser?exists>
<div class="buttons">
            <#if authed>
            <a class="btn-header-logout" href="${urls.getURL('/account/logout')}">[退出]</a><a href="${urls.getURL('/profile/')}" class="btn-header-myhome">
                <span></span>${currentUser.nickname?html}的空间
            </a>
            <div class="btn-space"></div>
            <#else>
             <a class="btn-header-register" href="${urls.getURL("/account/signup")}"><span></span>注册</a>
            <div class="btn-space"></div>
            <a class="btn-header-login" href="${urls.getURL("/account/login")}?j_uri=${urls.getRequestedURL()?url}"><span></span>登录</a>
            <div class="btn-space"></div>
             </#if>
            <a class="btn-header-invitation" href="javascript:;"><span></span>邀请</a>
            <a class="btn-header-share" href="javascript:;"><span></span>分享</a>
            
</div>

<div class="search">
            <input value="请输入您关键字" inputfilter="off" class="txt-header-360search" id="txt360Search">
            <a target="_blank" class="btn-header-360search" id="btn360Search" href="#">搜 索</a>
</div>
