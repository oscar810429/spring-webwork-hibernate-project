<#include "/common/macros.ftl">
<@startPage title="${_('confirm.heading', [user.nickname])}" wrapper="middleWrapper" />

<content tag="signup">
		<div class="lt">
			<a href="/" class="logo" title="返回登录${_('webapp.home')}首页">登录${_('webapp.home')}首页</a>
			<span class="regTxt">免费注册</span>

		</div>
		<div class="rt">
			<div class="t1">
			<a href="${urls.getURL("/account/login.html")}" target="_blank">登录</a>
			<a href="${urls.getURL("/help.html")}" target="_blank" class="svc">客服中心</a></div>
			<div class="t1">如遇注册问题请拨打，<font color="blue">010-58426775</font></div>
	    </div>

		<div class='clear'></div>

</content>

<div class="nav_bot">
	<ul class="nav_main">
  	<li class="txt">注册步骤：</li>
		<li class="s1n p20">填写注册信息</li>
		<li class="sf">&gt;</li>
		<li class="s2a p20 sa">激活会员</li>	

	</ul>
	<div class="clr"></div>
</div>

<div id="formMain">

<div id="subTip">
	<div class="title">注册成为<b>${_('webapp.home')}</b>会员，<br/>您将免费享受以下会员服务:</div>
	<ul class="con">
		<li>
			<div class="ti"><img alt="" src="${app_root}/images/write_icon.gif"/></div>
			<div class="t1">发布软件供求信息</div><div class="t2">让生意自动找上门!</div>
		</li>
		<li>
			<div class="ti"><img alt="" src="${app_root}/images/promotion_icon.gif"/></div>
			<div class="t1">网上推广软件产品</div><div class="t2">提升软件及公司知名度!</div>
		</li>
		<li>
			<div class="ti"><img alt="" src="${app_root}/images/client_icon.gif"/></div>
			<div class="t1">与客户在线交流软件</div><div class="t2">帮助客户快速解决问题!</div>
		</li>
		<li>
			<div class="ti"><img alt="" src="${app_root}/images/email_icon.gif"/></div>
			<div class="t1">邮箱订阅软件信息</div><div class="t2">不错过任何一个商机!</div>
		</li>
	</ul>
	<div class="goAd"></div>
</div>

<div class="regok" id="formContent">
	<h2>您已成功注册，请先激活此会员账号</h2>
	<div>
	<p>请打开 <u>${user.email}</u> 邮箱，查收我们给您发送的账号激活邮件，下面是两种激活账号的方式</p>
	<ol>
		<li>点击邮件内容中的激活链接</li>
		<li>或者手工输入邮件中提示的激活码
			<div style="display: none;" id="error_msg" class="error_txt"></div>
      <form id="form_activate" method="post" action="#">
			<input type="hidden" value="${user.email}" name="email"/>
			<input type="text" class="acode focusEnable idleField" size="40" name="code" id="txt_activate_code"/>			
			<input type="submit" class="button" value="激活账号"/>    
			<a href="${urls.getURL("/account/login.html?email=zhangsongfu@365-corp.com")}">转到登录页面</a>
      </form>
		</li>
	</ol>
	</div>
</div>
</div>
