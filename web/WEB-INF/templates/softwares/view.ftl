<#include "/common/macros.ftl">
<@startPage title="开源软件"/>
<#assign authed = currentUser?exists>
<content tag="styles">
<@css_file src="/styles/view.css" v="20100523"/>
</content>
<content tag="nav">
<div class="menu">
            
            <a href="/">首页</a> &gt;
            <font title="开源软件">开源软件</font>
            
</div>
</content>

<div style="width: 700px;" class="site-main-l">
            
            
<div class="information-border">
    
    
    
    <div class="h">
        <div class="h-title-border">
            
            <div class="float-l">
                
            </div>
            
        </div>
    </div>
    
    <div class="m">
        <div id="divInformationTitle" class="title-info">
            ${software.name}
        </div>
        <div class="author-info">
            <div class="float-l">
                
                发布于&nbsp;
                ${software.postedDate?datetime}
            </div>
            
            <#--<div class="head-share-website">
                <span style="float: left; margin-bottom: 1px;">分享到：</span><span style="float: left; margin-top: 1px;"><a target="_blank" href="http://share.renren.com/share/buttonshare.do?link=${urls.getURL('/softwares/view')}?id=${software.id}" id="share_renren" charset="400-8-4">
                        <img src="${media_root}/images/icon/ico_renren.gif" alt="人人"></a> <a target="_blank" href="http://www.kaixin001.com/repaste/share.php?rurl=${urls.getURL('/softwares/view')}?id=${software.id}" id="share_kaixin" charset="400-8-3">
                        <img src="${media_root}/images/icon/ico_kaixin.gif" alt="开心"></a> <a target="_blank" href="http://v.t.sina.com.cn/share/share.php?c=spr_web_bd_youku_weibo&amp;url=${urls.getURL('/softwares/view')}?id=${software.id}" id="share_sina" charset="400-8-5">
                        <img src="${media_root}/images/icon/ico_sina.gif" alt="新浪话题"></a> <a href="javascript:void(((function(s,d,e){var f='http://t.sohu.com/third/post.jsp?link=',u=d.location;function a(){if(!window.open([f,e(u)].join(''),'sohushare',['toolbar=0,status=0,resizable=1,width=660,height=470,left=',(s.width-660)/2,',top=',(s.height-470)/2].join('')))u.href=[f,e(u)].join('');};if(/Firefox/.test(navigator.userAgent))setTimeout(a,0);else a();})(screen,document,encodeURIComponent)))">
                        <img src="${media_root}/images/icon/ico_sohu.gif" alt="搜狐微博"></a>
                </span>
            </div>-->
            
            
        </div>
        
        <div id="divInformationContent" class="content-info">
            <div>
         ${software.content?html}
           </div>
        </div>
        <div class="pager-info">
            
        </div>
        
    </div>
    
    <div class="f">
        
        <div class="float-l">
            
        </div>
        
    </div>
    
</div>


<div class="miniblog-border">
    <div class="h">
        <div class="title">
            分享软件，发起软件评论
            <span id="spanMiniBlogTypeName"></span>
        </div>
        <a class="help float-r" title="如何使用评论？" target="_blank" href="＃"></a>
        <div class="tips">
            还可以输入 <b id="fontMiniBlogCount" style="color: rgb(228, 75, 0);">140</b> 个字</div>
    </div>
    <div class="m">        
        <input type="hidden" value="" id="hidMiniBlogImg">
        <input type="hidden" value="" id="hidPersonalShow">
        <input type="hidden" value="" id="hidPersonalShowUrl">
        <input type="hidden" value="" id="hidMiniBlogVideo">
        <input type="hidden" value="" id="hidMiniBlogWeb">
        <div id="divViewMiniBlogAction" class="viewaction-border">
        </div>
        <div class="textarea-border">
            <div class="textarea-l"></div>
            <div class="textarea-m">
                <textarea id="txtMiniBlogContent" style="background: none repeat scroll 0% 0% rgb(255, 255, 255);"></textarea>
            </div>
            <div class="textarea-r"></div>
        </div>
    </div>    
    <div class="f">
        <#--
        <div style="margin-top: 3px;" class="float-l actions">
        </div>-->
        <a id="btnSendMiniBlog" class="sendblogbutton" href="javascript:;">发 布</a>
        
        <#--<div style="margin-top: 3px;" class="circle">

        </div>-->
        
    </div>
</div>

<div style="float: left; width: 698px; height: 25px; line-height: 25px; text-indent: 10px; border: 1px solid rgb(153, 153, 153); text-align: left; background: none repeat scroll 0% 0% rgb(215, 215, 215); color: rgb(97, 97, 97);">
    共有 <b style="color: rgb(228, 75, 0);">
        0</b> 条话题评论</div>
<div class="float-l" id="divMiniBlogList">
    
</div>

</div>


<div style="width: 292px;" class="site-main-r">           
            
            
<div class="interrelated-border">
    
    <div class="miniblogad-border">
        
        <div class="miniblogad-border-h">
            <div style="width: 252px; height: 18px; font-size: 14px; font-weight: 700; color: rgb(228, 75, 0); text-align: left;" class="float-l">
                ${software.name} 新闻
            </div>
            <a target="_blank" class="help float-r" href="/Help"></a>
        </div>
        <#--<div class="miniblogad-border-list">
            • 对资讯内容发表评论<br>
            • 或对图片进行评论
        </div>
        <div style="color: rgb(228, 75, 0);" class="miniblogad-border-list">
            针对这篇资讯，转载或发评论，写成一个新话题，若话题被推荐到首页，最高奖励1500分。
        </div>
        <a class="btnWriteMiniBlog" href="javascript:;">&nbsp;</a>
        <div class="miniblogad-border-f">
            
            目前该资讯已有 <font color="#e44b00">
                0</font> 个相关话题
        </div>-->
    </div>
    
<div class="hotImgList">
    <div class="title">
     共有0个类似软件
    </div>
    
    <#--
    <div class="imgitem">
        <a class="img" target="_blank" href="/Post/306424">
            <img width="120" height="156" onload="ResizeImage(this,120,80,0)" title="莫文蔚：实体唱片已成传说 新专辑仍旧数字发行" src="/files/Information/306424/c3c24e7ea7e2b8dc24976fed187dc7db_w_120_0.jpg" style="margin-left: 0pt; margin-top: 0pt;">
        </a>
        <a title="莫文蔚：实体唱片已成传说 新专辑仍旧数字发行" target="_blank" href="/Post/306424">
            莫文蔚：实体唱片已...
        </a>
    </div>
    -->
    
    
    
</div>
<div style="clear: both;"></div>
</div>
</div>     