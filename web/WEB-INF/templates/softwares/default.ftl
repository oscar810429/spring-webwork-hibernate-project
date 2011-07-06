<#include "/common/macros.ftl">
<@startPage title="开源软件"/>
<#assign authed = currentUser?exists>
<content tag="styles">
<@css_file src="/styles/categorylist.css" v="20100523"/>
</content>
<content tag="nav">
<div class="menu">
            
            <a href="/">首页</a> &gt;
            <font title="开源软件">开源软件</font>
            
</div>
</content>
<div style="height: 10px; width: 100%;"></div>


<div class="brokenews-border">            
<div class="brokenews-info">
                
<div class="informationclass-border">
    <dl>
        <dd class="curr">
            <a href="${urls.getURL('/softwares/tag')}?cid=${category.id}">${category.name}</a></dd>
        
          <#list categories as subcategory>
             <dd><a href="#">${subcategory.name}</a>(${subcategory.amount})</dd>
          </#list>
        
        
    </dl>
    <input type="hidden" value="146" id="hidTodayCount">
</div>
<div id="divInformationList" class="informationlist-border">
    
<div class="informationlist-h">
    <#--<div style="margin-left: 5px; color: rgb(134, 134, 134);" class="float-l">
        今日新增了 <font color="#e44b00" id="lbltodayCount">0</font> 个开源软件</div>-->
    <a rssurl="#" style="margin-right: 10px;" id="btnSubscribe" class="subscribebutton float-r" href="javascript:;"></a>
</div>
<div class="informationlist-m">
    
    <#list result.data as software>
    
    <div class="information-border">
        <div class="information-main">
            <div class="information-title">
                <div style="width: 100%; height: auto;">
                    <a title="${software.name}" target="_blank" href="${urls.getURL('/softwares/view')}?id=${software.id}">${software.name}</a>&nbsp;&nbsp; [${software.category.name}]</div>
                <div style="width: 100%; height: 15px; margin-top: 5px;">${action.getText("label.before", [DateUtils.distanceInWords(software.postedDate)])} | 已有 <font color="#e44b00">0</font> 篇话题评论</div>
            </div>
            <div class="information-content">${software.content}</div>
           <div style="clear: both;"></div>
        </div>
    </div>
    
    </#list>
    
    
</div>

  <#if category?exists>
    <#assign pagerurl = urls.getURL('/softwares/tag?cid=' + category.id + '&amp;page=$PAGE')>
  <#else>
    <#assign pagerurl = urls.getURL('/softwares/tag?page=$PAGE')>
  </#if>

<div class="informationlist-f">
  <div class="pages">
    <@pn.pager result="result" url="${pagerurl}" />
  </div>
</div>

</div>
<div class="hotImgList">
    <div class="title">
        热门开源软件
    </div>
    <#--
    <div class="imgitem">
        <a class="img" target="_blank" href="/Post/299612">
            <img width="120" height="24" onload="ResizeImage(this,120,80,0)" title="#" src="/files/Information/299612/879cb23240031685b9d49936551ccf2d_w_120_0.jpg" style="margin-left: 0pt; margin-top: 28px;">
        </a><a title="陕西省《我心目中的大秦岭》高校大学生涂鸦大赛 开场主题墙制作" target="_blank" href="/Post/299612">#
        </a>
    </div>
     -->
    
</div>

</div>
</div>