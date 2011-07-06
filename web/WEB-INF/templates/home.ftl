<#include "/common/macros.ftl">
<@startPage title="首页"/>

<head>
	<link rel="stylesheet" type="text/css" media="all" href="${media_root}/styles/tuangou.css?v20110318.css"/>
</head>	

<content tag="nav">
<div class="menu">
           <div class="scroll-info">
                <div class="main">
                    <b></b>
                    
                </div>
            </div>  
</div>
</content>

  
<div class="tuan-z16">
<!--团购内容导航部分-->
<div class="tuan-z7">
<div class="tuan-h1">
<p class="l"><a href="#">${area.name}团购网</a></p>
<span class="l"><a class="ty2" href="/">全部</a> | <a href="#">餐饮美食</a> | <a href="#">美容美发</a> | <a href="#">休闲娱乐</a> | <a href="#">生活服务</a> | <a href="#">网上商城优惠券</a> | <a href="#">其他团购</a> | </span>
 <span class="r">${area.name}团购网站共有(${result.total})个团购活动</span>
</div>
<div class="tuan-h2">

<dl class="l">
<dd class="ty-off"><a href="#">人气排序</a><i class="st-up">&nbsp;</i></dd>

<dd><a href="#">价格排序</a><i>&nbsp;</i></dd>

<dd><a href="#">折扣排序</a><i>&nbsp;</i></dd>

<dd><a href="#">时间排序</a><i>&nbsp;</i></dd>

</dl>
</div>
</div>
<!--团购内容导航部分结束-->

<div class="clear"></div>
    <div class="tuan-z17">
    	    	<ul>
    	    	<#list result.data as product>

    		    <li onmouseout="leaveout(${product.id?c})" onmouseover="comejs(519116)">
            	<div class="tuan-z18"><a target="_blank" href="${urls.getRegionURL('/gotourl?id='+product.id?c)}"><img src="${product.imageUrl}"></a>
                <div id="disptime519116" style="display: none;" class="Layer4">
                <span id="tuangoutime519116">还有<span>1</span>天<span>7</span>小时<span>26</span>分<span>58</span>秒结束</span>
                </div>
                </div>
                <input type="hidden" value="2011-03-21 00:00:00" id="timertuangou519116">
                <div class="tuan-z19"><a href="${urls.getRegionURL('/gotosite?id='+product.id?c)}" target="_blank">${product.webSite}</a> 【${product.cityName}】<a target="_blank" style="color: rgb(0, 0, 0);" href="${urls.getRegionURL('/gotourl?id='+product.id?c)}"></a><a target="_blank" style="color: rgb(0, 0, 0);" href="${urls.getRegionURL('/gotourl?id='+product.id?c)}">${product.title}</a></div>
                <div class="tuan-z20">
                  <div class="tuan-z21">
                    <div>团购价：<b style="font-size: 16px; font-family: Arial;">${product.salePrice}</b><b>元</b></div>
                    <div>折扣：<span>${product.discount}折</span></div>
                    <div>原价：${product.basePrice}元</div>
                    <div>已售：<span id="priceid519116">${product.amount}</span>个</div>
                  </div>
                <div class="tuan-z22">
               		<div style="line-height: 14px; margin-top: 2px;"></div>
                    <div style="margin-top: 3px;"><a target="_blank" href="${urls.getRegionURL('/gotourl?id='+product.id?c)}"><img alt="立即团购" src="${media_root}/images/tuan-z22.gif"></a></div>
                    <div class="tuan-z23">
                    	<#--<div style="margin-left: 62px; display: inline;"><a href="/58tuangou/prod-519116.html">(<span id="5191165511">1287</span>)</a>
						</div>-->
                    </div>

                </div>
				</div>
        	</li>
        	 </#list>
        	          
    	</ul>
    	    </div>
    <!--分页-->

<div class="pages">
<#if (result.totalPage > 1)>
    <@pn.pager result="result" url="${urls.getRegionURL('/')}?page=$PAGE" />
</#if>
</div> 

</div>
       
    


