<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<#include "/common/macros.ftl">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
	<meta name="title" content="${_("page.meta.title")}"/>
	<meta name="description" content="${_("page.meta.description")}"/>
	<meta name="keywords" content="${_("page.meta.keywords")}"/>
	
	<title>${_("webapp.prefix")}${_("page.home.title")}</title>
	
	<link rel="shortcut icon" href="${media_root}/images/favicon.ico" type="image/ico"/>
	<link href="${media_root}/styles/index.css?v20100331.css" rel="stylesheet" type="text/css" />
	
	<script type="text/javascript" src="${media_root}/scripts/mt/mootools.v1.2.4.js?v20100329.js"></script>
</head>
<#assign authed = currentUser?exists>
<body>

<div id="Screen">

<div id="hd" class="regPage">
		<div class="lt">
			<a href="/" class="logo" title="${_('webapp.home')}">登录${_('webapp.home')}首页</a>

		</div>
		<div class="rt">
			<div class="t1">
		    <#if authed>
			<a href="${urls.getURL("/profile/")}">${currentUser.nickname?html}</a>
			<#else>
			<a href="${urls.getURL("/account/login.html")}" target="_blank">登录</a>
			</#if>
			<a href="${urls.getURL("/help.html")}" target="_blank" class="svc">客服中心</a></div>
			<div class="t1">如遇注册问题请拨打，<font color="blue">0571-85457713</font></div>
	    </div>

		<div class='clear'></div>
		
</div>
<div id="RKL_Content">

<div id="home-mod-corner">
<div id="hot-cities">
                        <h2 class="notice">全国<span class="yk-highlight">10</span>个城市均已开通，选择你所在的城市，找到更适合你的医院内容！</h2>
			<h2>热门城市选择：</h2>
			<div class="layer1">
				
				<a href="${urls.getURL('/search/citys.html?name=hangzhou')}">杭州</a>
				<a href="${urls.getURL('/search/citys.html?name=shanghai')}">上海</a>
				<a href="${urls.getURL('/search/citys.html?name=guangzhou')}">广州</a>
				<a href="${urls.getURL('/search/citys.html?name=shenzhen')}">深圳</a>
			    <a href="${urls.getURL('/search/citys.html?name=beijing')}">北京</a>

            </div>
			
			<div class="layer2">
			
				<a href="${urls.getURL('/search/citys.html?name=chongqi')}">重庆</a>
				<a href="${urls.getURL('/search/citys.html?name=chengdu')}">成都</a>
				<a href="${urls.getURL('/search/citys.html?name=tianjin')}">天津</a>
				<a href="${urls.getURL('/search/citys.html?name=suzhou')}">苏州</a>
				<a href="${urls.getURL('/search/citys.html?name=fuzhou')}">福州</a>

				<a href="${urls.getURL('/search/citys.html?name=nanjing')}">南京</a>
				<a href="${urls.getURL('/search/citys.html?name=changsha')}">长沙</a>
				<a href="${urls.getURL('/search/citys.html?name=xian')}">西安</a>
				<a href="${urls.getURL('/search/citys.html?name=lijiang')}">丽江</a>
				<a href="${urls.getURL('/search/citys.html?name=sanya')}">三亚</a>

			</div>
</div>
<div id="important-cities">
			<h2>重点城市选择：</h2>
			<ol>
				<li>
				<#if groupArea_b?has_content>
				<#list groupArea_a as area>
					<a href="${urls.getURL('/search/citys.html')}?name=${area.ename}">${area.aliasname1}</a>
				</#list>
				</#if>		
				</li>
				<li>
				<#if groupArea_b?has_content>
				<#list groupArea_b as area>
					<a href="${urls.getURL('/search/citys.html')}?name=${area.ename}">${area.aliasname1}</a>
				</#list>
				</#if>	
				</li>
				<li>
				<#if groupArea_c?has_content>
				<#list groupArea_c as area>
					<a href="${urls.getURL('/search/citys.html')}?name=${area.ename}">${area.aliasname1}</a>
				</#list>
				</#if>	
				</li>
				<li>
				<#if groupArea_d?has_content>
				<#list groupArea_d as area>
					<a href="${urls.getURL('/search/citys.html')}?name=${area.ename}">${area.aliasname1}</a>
				</#list>
				</#if>	
				</li>
				<li class="yn-display-none"></li>
				<li>
				<#if groupArea_f?has_content>
				<#list groupArea_f as area>
					<a href="${urls.getURL('/search/citys.html')}?name=${area.ename}">${area.aliasname1}</a>
				</#list>
				</#if>	
				</li>
				<li>
				<#if groupArea_g?has_content>
				<#list groupArea_g as area>
					<a href="${urls.getURL('/search/citys.html')}?name=${area.ename}">${area.aliasname1}</a>
				</#list>
				</#if>	
				</li>
				<li>
				<#if groupArea_h?has_content>
				<#list groupArea_h as area>
					<a href="${urls.getURL('/search/citys.html')}?name=${area.ename}">${area.aliasname1}</a>
				</#list>
				</#if>	
				</li>
				<li class="yn-display-none"></li>
				<li>
                 <#if groupArea_j?has_content>
				<#list groupArea_j as area>
					<a href="${urls.getURL('/search/citys.html')}?name=${area.ename}">${area.aliasname1}</a>
				</#list>
				</#if>	
				</li>
				<li>
				<#if groupArea_k?has_content>
				<#list groupArea_k as area>
					<a href="${urls.getURL('/search/citys.html')}?name=${area.ename}">${area.aliasname1}</a>
				</#list>
				</#if>	
				</li>
				<li>
				<#if groupArea_l?has_content>
				<#list groupArea_l as area>
					<a href="${urls.getURL('/search/citys.html')}?name=${area.ename}">${area.aliasname1}</a>
				</#list>
				</#if>	
				</li>
				<li>
				<#if groupArea_m?has_content>
				<#list groupArea_m as area>
					<a href="${urls.getURL('/search/citys.html')}?name=${area.ename}">${area.aliasname1}</a>
				</#list>
				</#if>	
				</li>
				<li>
				<#if groupArea_n?has_content>
				<#list groupArea_n as area>
					<a href="${urls.getURL('/search/citys.html')}?name=${area.ename}">${area.aliasname1}</a>
				</#list>
				</#if>	
					
				</li>
				<li class="yn-display-none"></li>
				<li>
				<#if groupArea_p?has_content>
				<#list groupArea_p as area>
					<a href="${urls.getURL('/search/citys.html')}?name=${area.ename}">${area.aliasname1}</a>
				</#list>
				</#if>	
					
				</li>
				<li>
				
				<#if groupArea_q?has_content>
				<#list groupArea_q as area>
					<a href="${urls.getURL('/search/citys.html')}?name=${area.ename}">${area.aliasname1}</a>
				</#list>
				</#if>	
					
				</li>
				<li>
				<#if groupArea_r?has_content>
				<#list groupArea_r as area>
					<a href="${urls.getURL('/search/citys.html')}?name=${area.ename}">${area.aliasname1}</a>
				</#list>
				</#if>	
				</li>
				<li>
				<#if groupArea_s?has_content>
				<#list groupArea_s as area>
					<a href="${urls.getURL('/search/citys.html')}?name=${area.ename}">${area.aliasname1}</a>
				</#list>
				</#if>	
				</li>
				<li>
				<#if groupArea_t?has_content>
				<#list groupArea_t as area>
					<a href="${urls.getURL('/search/citys.html')}?name=${area.ename}">${area.aliasname1}</a>
				</#list>
				</#if>	
				</li>
				<li class="yn-display-none"></li>
				<li class="yn-display-none"></li>
				<li>
				<#if groupArea_w?has_content>
				<#list groupArea_w as area>
					<a href="${urls.getURL('/search/citys.html')}?name=${area.ename}">${area.aliasname1}</a>
				</#list>
				</#if>	
				
				</li>
				<li>
				<#if groupArea_x?has_content>
				<#list groupArea_x as area>
					<a href="${urls.getURL('/search/citys.html')}?name=${area.ename}">${area.aliasname1}</a>
				</#list>
				</#if>	
				
				</li>
				<li>
				<#if groupArea_y?has_content>
				<#list groupArea_y as area>
					<a href="${urls.getURL('/search/citys.html')}?name=${area.ename}">${area.aliasname1}</a>
				</#list>
				</#if>	
				</li>
				<li>
				<#if groupArea_z?has_content>
				<#list groupArea_z as area>
					<a href="${urls.getURL('/search/citys.html')}?name=${area.ename}">${area.aliasname1}</a>
				</#list>
				</#if>	
				</li>
			</ol>
		</div>
		
</div>
</div>

    <div class="clear"></div>


    <div id="RKL_Footer">
         <#include "/common/footer.ftl">
    </div>

</div>



</body>
</html>		