<#include "/common/macros.ftl">
<#assign authed = currentUser?exists>
<@startPage title="个人资料"/>
<content tag="styles">
	<@styles files=['profile'] />
	<@css_file src="/styles/themes/green.css" v="20100523"/>
</content>

 <content tag="scripts">
  <script type="text/javascript" src="${media_root}/scripts/editable.js?v20100713.js"></script>
</content>
 

<content tag="nav">
<div class="menu">
            
            <a href="/">首页</a> &gt;
            <font title="${user.nickname}的个人主页">${user.nickname}的个人主页</font>
            
</div>
</content>

<content tag="profiletop">

<div class="l">
            <div title="点击看大图" class="img ShowBigImage">
                <img width="100" height="100" source="#" src="<@userIcon user=user/>" id="imgCurrUserPhoto" style="margin-left: 0px; margin-top: 0pt;">
            </div>
            <#if authed>
            <a id="btnUpdateUserInfo" class="button" href="javascript:;">修改资料</a>
            <#else>
            <div class="username">
                <div class="username-l">
                </div>
                <div class="username-m">
                    ${user.nickname?html}</div>
                <div class="username-r">
                </div>
            </div>
            </#if>
            
</div>

<#if authed>
<script type="text/javascript">
     var userprofile = new Hash({ realname: '${user.profile.name?js_string}', description: '<#if user.profile.description?has_content>${user.profile.description?js_string}<#else>这个家伙很懒什么也没有留下!!!</#if>'});
     userprofile.extend(current_user);
</script>
</#if>
 
<div class="r">
            <div class="spacename-border">
                    <#if authed>
                        <div style="float: left;" id="divSpaceName">
                        </div>
                        <script type="text/javascript">
                           new leopard.UserEditor('divSpaceName',userprofile, 'title', { displayStyle: 'spacename', inputStyle: 'spaceNameText' } );            
                        </script>
                        
                    <#else>
                       <div style="float: left;" id="divSpaceName"> <div style="display: block;"><span class="spacename">${user.nickname?html}个人空间</span></div></div>
                    </#if>   
   
                    
               
                <a title="如何使用个人空间？" style="float: right;" class="help" target="_blank" href="#"></a>
            </div>
            <div class="userintro-border">
                <#if authed>            
                <div style="float: left;" id="divUserIntro">
                </div>
                    <script type="text/javascript">
                           new leopard.UserEditor('divUserIntro',userprofile, '', { displayStyle: 'userintro', inputStyle: 'userinfoText' } );            
                    </script>
              <#else>
                 <div style="float: left;" id="divUserIntro">
                    <div style="display: block;">
                        <span class="userintro">
                            这家伙很懒,什么也没有留下</span>
                        
                    </div>
                </div>
              </#if>
                
            </div>
            <div class="personshow-border">
                <div class="left">
                </div>
                <div class="middle">
                    <div class="head">
                        <div class="title">
                            我的个人秀：
                        </div>
                        <a title="如何使用个人秀？" style="float: left; margin-top: 4px;" class="help" target="_blank" href="＃">
                        </a>
                        
                        <a style="float: right; margin-top: 5px; width: 60px;" id="btnAddPersonalShow" class="button" href="javascript:;">我还要秀</a>
                        
                    </div>
                    <div id="divPersonalShowList" class="list">
                        <a href="#">个性资料</a>
                    </div>
                </div>
                <div class="right">
                </div>
            </div>
</div> 

</content>

<div style="width: 698px; overflow: visible;" class="site-main-l">
            

<#--
<div class="miniblog-border">
    <div class="h">
        <div class="title">
            记录，分享从这里开始...
            <span id="spanMiniBlogTypeName"></span>
        </div>
        <a class="help float-r" title="如何使用话题？" target="_blank" href="http://www.wohuia.com/Help/?CID=18&amp;CN=%E5%BE%AE%E5%8D%9A%E5%B8%AE%E5%8A%A9"></a>
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
        <div style="margin-top: 3px;" class="float-l actions">
            <a id="btnUploadMiniBlogImg" class="miniblogbutton" href="javascript:;">图片</a>
            <a id="btnInsertMiniBlogVideo" class="miniblogbutton" href="javascript:;">视频</a>
            <a id="btnShareMiniBlogWeb" class="miniblogbutton" href="javascript:;">网页</a>
            <a id="btnQuotePersonalShow" class="miniblogbutton" href="javascript:;">我秀</a>
        </div>
        <a id="btnSendMiniBlog" class="sendblogbutton" href="javascript:;">发 布</a>
        
        <div style="margin-top: 3px;" class="circle">
            发布到圈子
            <select id="selMiniBlogCircle">
                <option value="0">不发到圈子</option>
                
                <option value="1">地球没有新鲜事</option>
                
                <option value="13">休闲搞笑</option>
                
                <option value="2">Girl时尚团</option>
                
                <option value="4">疯狂网购</option>
                
                <option value="6">居民交流圈</option>
                
                <option value="10">IMDB TOP最佳影片</option>
                
                <option value="12">娱乐八卦狗仔队</option>
                
            </select>
        </div>
        
    </div>
</div>


            <div class="shareweb-border">
                <div class="shareweb-h">
                    <div class="shareweb-title">新功能！在任何网站分享新鲜事到“我会啊”</div>
                    <a id="linkCloseShareWebTips" href="javascript:;">关闭提示</a>
                </div>
                <div class="shareweb-f">
                    <div class="shareweb-tips">
                        为浏览器添加“分享精彩网页”功能，让您在任何网页上都可以写话题，分享新鲜事！
                    </div>
                    <a class="shareweb-button" target="_blank" href="/ShareWeb"></a>
                </div>
            </div>
            
      -->      

<div class="friendminiblog-border">
    <div class="h">
        <div class="title">
        </div>
        <div class="float-r">
            <a href="/User/MiniBlog/13148/2">
                &gt;&gt; 查看全部话题</a>
        </div>
    </div>
    <div id="divMiniBlogList" class="m">
        
        <div miniblogid="85238" class="miniblog-info">
            <div class="miniblog-info-r">
                <div miniblogid="85238" class="miniblog-content">
                    <div class="miniblog-content-h">
                        
                        <div style="margin-right: 10px; width: 48%; text-align: right; color: rgb(132, 132, 132);" class="float-r">
                            
                            <a miniblogid="85238" style="color: rgb(132, 132, 132);" class="DeleteMiniBlog" href="javascript:;">
                                删除</a> |
                            
                            <a style="color: rgb(132, 132, 132);" title="雪碧之爱" target="_blank" href="/User/13148">
                                雪碧之爱</a>
                            
                            通过<a style="color: rgb(132, 132, 132);" title="该话题是通过“博客”评论发布的" target="_blank" href="http://www.wohuia.com/Help/?CID=10&amp;CN=%E5%B8%B8%E8%A7%81%E9%97%AE%E9%A2%98&amp;HID=183&amp;HN=%E5%A6%82%E4%BD%95%E5%B0%86%E8%B5%84%E8%AE%AF%E8%BD%AC%E5%86%99%E6%88%90%E8%AF%9D%E9%A2%98%EF%BC%9F">博客</a>
                            
                            分享于<a style="color: rgb(132, 132, 132);" target="_blank" href="/t/85238">
                                2010-06-30 02:11
                                </a>
                        </div>
                    </div>
                    <div class="miniblog-content-m">
                        
                        <div style="margin-left: 0pt;" class="miniblog-content-m-r">
                            哈哈。哈哈。哈哈。哈哈。
                            <a target="_blank" style="color: rgb(24, 107, 150); border: 0pt none;" class="linkMarkReadRecommend" href="/t/85238">&gt;&gt;查看详细</a>
                            
                        </div>
                    </div>
                    <div style="margin-top: 7px;" class="miniblog-content-f">
                        
                        <a title="哈哈。" target="_blank" class="roundbutton float-l" href="/Post/298013">
                            <span class="l"></span><font class="m">评自：哈哈。</font>
                            <span class="r"></span></a>
                        
                        <div class="divDiscussAward">
                            <div style="margin-right: 3px; line-height: 16px;" class="float-r">
                                <img align="absmiddle" src="/Images/Icon/discuss.png">
                                <a userid="" miniblogid="85238" style="color: rgb(97, 97, 97);" class="DiscussMiniBlog linkMarkReadRecommend" href="javascript:;">评论（<font>1</font>）</a>
                            </div>
                            
                            <div style="line-height: 16px;" class="float-r">
                                <img align="absmiddle" src="/Images/MiniBlog/award.png">
                                <a title="给话题发布者打赏100积分，让你的好友和粉丝都能看到该话题！" style="color: rgb(97, 97, 97);" userid="" miniblogid="85238" class="SupportMiniBlog linkMarkReadRecommend" href="javascript:;">
                                    打赏</a>
                            </div>
                            
                        </div>
                    </div>
                    
                    <div miniblogid="85238" class="discusscontent-border">
                        <div style="margin: 5px 5px 0pt 0pt;" class="discusshead">
                        </div>
                        <div class="discusscontent">
                        </div>
                    </div>
                    
                </div>
            </div>
        </div>
        
        <div miniblogid="76517" class="miniblog-info">
            <div class="miniblog-info-r">
                <div miniblogid="76517" class="miniblog-content">
                    <div class="miniblog-content-h">
                        
                        <div style="margin-right: 10px; width: 48%; text-align: right; color: rgb(132, 132, 132);" class="float-r">
                            
                            <a style="color: rgb(132, 132, 132);" title="我会啊" target="_blank" href="/User/213">
                                我会啊</a>
                            
                            分享于<a style="color: rgb(132, 132, 132);" target="_blank" href="/t/76517">
                                2010-05-24 16:22
                                </a>
                        </div>
                    </div>
                    <div class="miniblog-content-m">
                        
                        <div class="miniblog-content-m-l">
                            
                            <a title="点击看大图" class="miniblog-img ShowBigImage" href="javascript:;">
                                <img width="120" height="60" border="0" source="/files/MiniBlog/76517/a8b669df-90c3-4ba2-b636-13aaee62ec04.jpg" onload="ResizeImage(this,120, 80, 3)" src="/files/MiniBlog/76517/a8b669df-90c3-4ba2-b636-13aaee62ec04_w_120_0.jpg" style="margin-left: 0pt; margin-top: 10px;">
                            </a>
                            
                        </div>
                        
                        <div class="miniblog-content-m-r">
                            我会啊近期对主题页面做了修改和调整，欢迎大家体验试用！新页面风格更简洁，主题栏目由原来的横向排列换成了在主题左侧位置显示，“参与编辑”的按钮放大、位置也更加明显，方便大家添加主题简介、封面和属性资料。各列表和提示框的颜色由鲜艳的黄色变成低调的黑灰色，可缓解编辑主题属性时视觉疲劳。
                            <a target="_blank" style="color: rgb(24, 107, 150); border: 0pt none;" class="linkMarkReadRecommend" href="/t/76517">&gt;&gt;查看详细</a>
                            
                        </div>
                    </div>
                    <div style="margin-top: 7px;" class="miniblog-content-f">
                        
                        <div class="divDiscussAward">
                            <div style="margin-right: 3px; line-height: 16px;" class="float-r">
                                <img align="absmiddle" src="/Images/Icon/discuss.png">
                                <a userid="" miniblogid="76517" style="color: rgb(97, 97, 97);" class="DiscussMiniBlog linkMarkReadRecommend" href="javascript:;">评论（<font>2</font>）</a>
                            </div>
                            
                            <div style="line-height: 16px;" class="float-r">
                                <img align="absmiddle" src="/Images/MiniBlog/award.png">
                                <a title="给话题发布者打赏100积分，让你的好友和粉丝都能看到该话题！" style="color: rgb(97, 97, 97);" userid="" miniblogid="76517" class="SupportMiniBlog linkMarkReadRecommend" href="javascript:;">
                                    打赏</a>
                            </div>
                            
                        </div>
                    </div>
                    
                    <div miniblogid="76517" class="discusscontent-border">
                        <div style="margin: 5px 5px 0pt 0pt;" class="discusshead">
                        </div>
                        <div class="discusscontent">
                        </div>
                    </div>
                    
                    <div title="为该话题发布者发了红包的居民" class="supportminiblog-border">
                        <img border="0" align="absmiddle" src="/Images/MiniBlog/award.png">&nbsp;
                        
                        <a href="/User/24563">
                            benchen</a>&nbsp;
                        
                    </div>
                    
                </div>
            </div>
        </div>
        
        <div miniblogid="68975" class="miniblog-info">
            <div class="miniblog-info-r">
                <div miniblogid="68975" class="miniblog-content">
                    <div class="miniblog-content-h">
                        
                        <div style="margin-right: 10px; width: 48%; text-align: right; color: rgb(132, 132, 132);" class="float-r">
                            
                            <a style="color: rgb(132, 132, 132);" title="我会啊" target="_blank" href="/User/213">
                                我会啊</a>
                            
                            分享于<a style="color: rgb(132, 132, 132);" target="_blank" href="/t/68975">
                                2010-04-30 18:02
                                </a>
                        </div>
                    </div>
                    <div class="miniblog-content-m">
                        
                        <div class="miniblog-content-m-l">
                            
                            <a class="miniblog-img" target="_blank" href="/Topic/2035064">
                                <img width="120" height="42" border="0" source="/files/Topic/2035064/181d175f-d4a1-4445-9d37-ac708d75130f.png" onload="ResizeImage(this,120, 80, 3)" src="/files/Topic/2035064/181d175f-d4a1-4445-9d37-ac708d75130f.png_w_120_0.jpg" style="margin-left: 0pt; margin-top: 19px;">
                            </a>
                            
                        </div>
                        
                        <div class="miniblog-content-m-r">
                            五一劳动节就要到了，祝大家节日快乐！<a linktopicid="2035064" target="_blank" href="/Topic/2035064">我会啊</a>将于5月1日至3日放假，共3天，5月4日(星期二)正常上班。放假期间，网站正常运营，如果大家遇到与网站相关的问题，请加<a linktopicid="2035064" target="_blank" href="/Topic/2035064">我会啊</a>QQ群52691567，向群主或群管理员反馈，我们将第一时间处理。
                            <a target="_blank" style="color: rgb(24, 107, 150); border: 0pt none;" class="linkMarkReadRecommend" href="/t/68975">&gt;&gt;查看详细</a>
                            <br>
                            <font color="#848484">奖励：<a style="color: rgb(228, 75, 0); font-size: 14px; font-weight: bold;" target="_blank" href="/Goods"><img border="0" align="absmiddle" title="积分" src="/Images/Icon/xinks.png">500</a></font>
                            
                        </div>
                    </div>
                    <div style="margin-top: 7px;" class="miniblog-content-f">
                        
                        <a title="我会啊" class="float-l" target="_blank" href="/Topic/2035064">
                            <img align="absmiddle" src="/Images/MiniBlog/read.png">
                            了解“我会啊”更多详细内容</a>
                        
                        <div class="divDiscussAward">
                            <div style="margin-right: 3px; line-height: 16px;" class="float-r">
                                <img align="absmiddle" src="/Images/Icon/discuss.png">
                                <a userid="" miniblogid="68975" style="color: rgb(97, 97, 97);" class="DiscussMiniBlog linkMarkReadRecommend" href="javascript:;">评论（<font>2</font>）</a>
                            </div>
                            
                            <div style="line-height: 16px;" class="float-r">
                                <img align="absmiddle" src="/Images/MiniBlog/award.png">
                                <a title="给话题发布者打赏100积分，让你的好友和粉丝都能看到该话题！" style="color: rgb(97, 97, 97);" userid="" miniblogid="68975" class="SupportMiniBlog linkMarkReadRecommend" href="javascript:;">
                                    打赏</a>
                            </div>
                            
                        </div>
                    </div>
                    
                    <div miniblogid="68975" class="discusscontent-border">
                        <div style="margin: 5px 5px 0pt 0pt;" class="discusshead">
                        </div>
                        <div class="discusscontent">
                        </div>
                    </div>
                    
                    <div title="为该话题发布者发了红包的居民" class="supportminiblog-border">
                        <img border="0" align="absmiddle" src="/Images/MiniBlog/award.png">&nbsp;
                        
                        <a href="/User/14844">
                            菩提一孤舟</a>&nbsp;
                        
                        <a href="/User/20303">
                            myido</a>&nbsp;
                        
                        <a href="/User/21838">
                            小分队</a>&nbsp;
                        
                        <a href="/User/15529">
                            不知所措</a>&nbsp;
                        
                    </div>
                    
                </div>
            </div>
        </div>
        
        <div miniblogid="63137" class="miniblog-info">
            <div class="miniblog-info-r">
                <div miniblogid="63137" class="miniblog-content">
                    <div class="miniblog-content-h">
                        
                        <div style="margin-right: 10px; width: 48%; text-align: right; color: rgb(132, 132, 132);" class="float-r">
                            
                            <a style="color: rgb(132, 132, 132);" title="我会啊" target="_blank" href="/User/213">
                                我会啊</a>
                            
                            分享于<a style="color: rgb(132, 132, 132);" target="_blank" href="/t/63137">
                                2010-04-07 18:32
                                </a>
                        </div>
                    </div>
                    <div class="miniblog-content-m">
                        
                        <div class="miniblog-content-m-l">
                            
                            <a title="点击看大图" class="miniblog-img ShowBigImage" href="javascript:;">
                                <img width="120" height="26" border="0" source="/files/MiniBlog/63137/44a59f65-fcce-4a34-81c4-aa680ab962f7.png" onload="ResizeImage(this,120, 80, 3)" src="/files/MiniBlog/63137/44a59f65-fcce-4a34-81c4-aa680ab962f7.png_w_120_0.jpg" style="margin-left: 0pt; margin-top: 27px;">
                            </a>
                            
                        </div>
                        
                        <div class="miniblog-content-m-r">
                            划词功能再升级，<a linktopicid="2035064" target="_blank" href="/Topic/2035064">我会啊</a>首页话题新增“填主题词”功能！新功能操作方法如图所示：在首页推荐话题里，找出话题内容中主要人物、地点或事物名称，然后点击话题右边的“填主题词”按钮，将找出的主题词填上就可获得300分/词的奖励。（每个话题每人只能填一个主题词，多人重复填词也同样奖励哦）
                            <a target="_blank" style="color: rgb(24, 107, 150); border: 0pt none;" class="linkMarkReadRecommend" href="/t/63137">&gt;&gt;查看详细</a>
                            <br>
                            <font color="#848484">奖励：<a style="color: rgb(228, 75, 0); font-size: 14px; font-weight: bold;" target="_blank" href="/Goods"><img border="0" align="absmiddle" title="积分" src="/Images/Icon/xinks.png">500</a></font>
                            
                        </div>
                    </div>
                    <div style="margin-top: 7px;" class="miniblog-content-f">
                        
                        <a title="我会啊" class="float-l" target="_blank" href="/Topic/2035064">
                            <img align="absmiddle" src="/Images/MiniBlog/read.png">
                            了解“我会啊”更多详细内容</a>
                        
                        <div class="divDiscussAward">
                            <div style="margin-right: 3px; line-height: 16px;" class="float-r">
                                <img align="absmiddle" src="/Images/Icon/discuss.png">
                                <a userid="" miniblogid="63137" style="color: rgb(97, 97, 97);" class="DiscussMiniBlog linkMarkReadRecommend" href="javascript:;">评论（<font>1</font>）</a>
                            </div>
                            
                            <div style="line-height: 16px;" class="float-r">
                                <img align="absmiddle" src="/Images/MiniBlog/award.png">
                                <a title="给话题发布者打赏100积分，让你的好友和粉丝都能看到该话题！" style="color: rgb(97, 97, 97);" userid="" miniblogid="63137" class="SupportMiniBlog linkMarkReadRecommend" href="javascript:;">
                                    打赏</a>
                            </div>
                            
                        </div>
                    </div>
                    
                    <div miniblogid="63137" class="discusscontent-border">
                        <div style="margin: 5px 5px 0pt 0pt;" class="discusshead">
                        </div>
                        <div class="discusscontent">
                        </div>
                    </div>
                    
                    <div title="为该话题发布者发了红包的居民" class="supportminiblog-border">
                        <img border="0" align="absmiddle" src="/Images/MiniBlog/award.png">&nbsp;
                        
                        <a href="/User/19810">
                            415279652</a>&nbsp;
                        
                        <a href="/User/21838">
                            小分队</a>&nbsp;
                        
                        <a href="/User/18235">
                            fjly</a>&nbsp;
                        
                        <a href="/User/15529">
                            不知所措</a>&nbsp;
                        
                    </div>
                    
                </div>
            </div>
        </div>
        
        <div miniblogid="62082" class="miniblog-info">
            <div class="miniblog-info-r">
                <div miniblogid="62082" class="miniblog-content">
                    <div class="miniblog-content-h">
                        
                        <div style="margin-right: 10px; width: 48%; text-align: right; color: rgb(132, 132, 132);" class="float-r">
                            
                            <a style="color: rgb(132, 132, 132);" title="我会啊" target="_blank" href="/User/213">
                                我会啊</a>
                            
                            分享于<a style="color: rgb(132, 132, 132);" target="_blank" href="/t/62082">
                                2010-04-02 17:34
                                </a>
                        </div>
                    </div>
                    <div class="miniblog-content-m">
                        
                        <div class="miniblog-content-m-l">
                            
                            <a class="miniblog-img" target="_blank" href="/Topic/2035064">
                                <img width="120" height="42" border="0" source="/files/Topic/2035064/181d175f-d4a1-4445-9d37-ac708d75130f.png" onload="ResizeImage(this,120, 80, 3)" src="/files/Topic/2035064/181d175f-d4a1-4445-9d37-ac708d75130f.png_w_120_0.jpg" style="margin-left: 0pt; margin-top: 19px;">
                            </a>
                            
                        </div>
                        
                        <div class="miniblog-content-m-r">
                            <a linktopicid="2035064" target="_blank" href="/Topic/2035064">我会啊</a>2010年清明节放假公告：&nbsp;<a linktopicid="2035064" target="_blank" href="/Topic/2035064">我会啊</a>将于4月3日至5日(星期六至星期一)放假，共3天，4月6日(星期二)正常上班。放假期间，网站正常运营，如果大家遇到与网站相关的问题，请加<a linktopicid="2035064" target="_blank" href="/Topic/2035064">我会啊</a>QQ群52691567，向群主或群管理员反馈，我们将第一时间处理。
                            <a target="_blank" style="color: rgb(24, 107, 150); border: 0pt none;" class="linkMarkReadRecommend" href="/t/62082">&gt;&gt;查看详细</a>
                            <br>
                            <font color="#848484">奖励：<a style="color: rgb(228, 75, 0); font-size: 14px; font-weight: bold;" target="_blank" href="/Goods"><img border="0" align="absmiddle" title="积分" src="/Images/Icon/xinks.png">1000</a></font>
                            
                        </div>
                    </div>
                    <div style="margin-top: 7px;" class="miniblog-content-f">
                        
                        <a title="我会啊" class="float-l" target="_blank" href="/Topic/2035064">
                            <img align="absmiddle" src="/Images/MiniBlog/read.png">
                            了解“我会啊”更多详细内容</a>
                        
                        <div class="divDiscussAward">
                            <div style="margin-right: 3px; line-height: 16px;" class="float-r">
                                <img align="absmiddle" src="/Images/Icon/discuss.png">
                                <a userid="" miniblogid="62082" style="color: rgb(97, 97, 97);" class="DiscussMiniBlog linkMarkReadRecommend" href="javascript:;">评论（<font>0</font>）</a>
                            </div>
                            
                            <div style="line-height: 16px;" class="float-r">
                                <img align="absmiddle" src="/Images/MiniBlog/award.png">
                                <a title="给话题发布者打赏100积分，让你的好友和粉丝都能看到该话题！" style="color: rgb(97, 97, 97);" userid="" miniblogid="62082" class="SupportMiniBlog linkMarkReadRecommend" href="javascript:;">
                                    打赏</a>
                            </div>
                            
                        </div>
                    </div>
                    
                    <div miniblogid="62082" class="discusscontent-border">
                        <div style="margin: 5px 5px 0pt 0pt;" class="discusshead">
                        </div>
                        <div class="discusscontent">
                        </div>
                    </div>
                    
                    <div title="为该话题发布者发了红包的居民" class="supportminiblog-border">
                        <img border="0" align="absmiddle" src="/Images/MiniBlog/award.png">&nbsp;
                        
                        <a href="/User/21838">
                            小分队</a>&nbsp;
                        
                    </div>
                    
                </div>
            </div>
        </div>
        
        <div miniblogid="62066" class="miniblog-info">
            <div class="miniblog-info-r">
                <div miniblogid="62066" class="miniblog-content">
                    <div class="miniblog-content-h">
                        
                        <div style="margin-right: 10px; width: 48%; text-align: right; color: rgb(132, 132, 132);" class="float-r">
                            
                            <a style="color: rgb(132, 132, 132);" title="我会啊" target="_blank" href="/User/213">
                                我会啊</a>
                            
                            分享于<a style="color: rgb(132, 132, 132);" target="_blank" href="/t/62066">
                                2010-04-02 16:39
                                </a>
                        </div>
                    </div>
                    <div class="miniblog-content-m">
                        
                        <div class="miniblog-content-m-l">
                            
                            <a title="点击看大图" class="miniblog-img ShowBigImage" href="javascript:;">
                                <img width="120" height="55" border="0" source="/files/MiniBlog/62066/c6b315cb-40fd-411a-8228-e3aabb4c9405.jpg" onload="ResizeImage(this,120, 80, 3)" src="/files/MiniBlog/62066/c6b315cb-40fd-411a-8228-e3aabb4c9405_w_120_0.jpg" style="margin-left: 0pt; margin-top: 12.5px;">
                            </a>
                            
                        </div>
                        
                        <div class="miniblog-content-m-r">
                            4月2日消息：对话题发表评论，积分加倍奖励咯，欢迎大家踊跃参与话题评论！原来对一个话题发表评论，奖励10分；现在对话题发表评论时，再选择你对话题的感受，可获得20分奖励。&nbsp;如图中所示，选择各种表情符号再发评论就奖励20分。（注：选“毫无感觉”再评论的话奖励10分哦！）
                            <a target="_blank" style="color: rgb(24, 107, 150); border: 0pt none;" class="linkMarkReadRecommend" href="/t/62066">&gt;&gt;查看详细</a>
                            <br>
                            <font color="#848484">奖励：<a style="color: rgb(228, 75, 0); font-size: 14px; font-weight: bold;" target="_blank" href="/Goods"><img border="0" align="absmiddle" title="积分" src="/Images/Icon/xinks.png">1000</a></font>
                            
                        </div>
                    </div>
                    <div style="margin-top: 7px;" class="miniblog-content-f">
                        
                        <a title="我会啊" class="float-l" target="_blank" href="/Topic/2035064">
                            <img align="absmiddle" src="/Images/MiniBlog/read.png">
                            了解“我会啊”更多详细内容</a>
                        
                        <div class="divDiscussAward">
                            <div style="margin-right: 3px; line-height: 16px;" class="float-r">
                                <img align="absmiddle" src="/Images/Icon/discuss.png">
                                <a userid="" miniblogid="62066" style="color: rgb(97, 97, 97);" class="DiscussMiniBlog linkMarkReadRecommend" href="javascript:;">评论（<font>4</font>）</a>
                            </div>
                            
                            <div style="line-height: 16px;" class="float-r">
                                <img align="absmiddle" src="/Images/MiniBlog/award.png">
                                <a title="给话题发布者打赏100积分，让你的好友和粉丝都能看到该话题！" style="color: rgb(97, 97, 97);" userid="" miniblogid="62066" class="SupportMiniBlog linkMarkReadRecommend" href="javascript:;">
                                    打赏</a>
                            </div>
                            
                        </div>
                    </div>
                    
                    <div miniblogid="62066" class="discusscontent-border">
                        <div style="margin: 5px 5px 0pt 0pt;" class="discusshead">
                        </div>
                        <div class="discusscontent">
                        </div>
                    </div>
                    
                    <div title="为该话题发布者发了红包的居民" class="supportminiblog-border">
                        <img border="0" align="absmiddle" src="/Images/MiniBlog/award.png">&nbsp;
                        
                        <a href="/User/21838">
                            小分队</a>&nbsp;
                        
                    </div>
                    
                </div>
            </div>
        </div>
        
        <div miniblogid="58248" class="miniblog-info">
            <div class="miniblog-info-r">
                <div miniblogid="58248" class="miniblog-content">
                    <div class="miniblog-content-h">
                        
                        <div style="margin-right: 10px; width: 48%; text-align: right; color: rgb(132, 132, 132);" class="float-r">
                            
                            <a style="color: rgb(132, 132, 132);" title="我会啊" target="_blank" href="/User/213">
                                我会啊</a>
                            
                            分享于<a style="color: rgb(132, 132, 132);" target="_blank" href="/t/58248">
                                2010-03-18 18:39
                                </a>
                        </div>
                    </div>
                    <div class="miniblog-content-m">
                        
                        <div style="margin-left: 0pt;" class="miniblog-content-m-r">
                            各位朋友请注意：我会啊“锌愿超市”更名为“积分兑换商城”，积分兑换商城里的部分礼品也相应做了调整，欢迎大家兑换！<br>注：愿来的“锌”已更名为“积分”，“锌愿石”更名为“奖券”，积分和奖券的兑换比例为10000分=1张奖券，换礼品时以奖券为兑换单位。
                            <a target="_blank" style="color: rgb(24, 107, 150); border: 0pt none;" class="linkMarkReadRecommend" href="/t/58248">&gt;&gt;查看详细</a>
                            
                        </div>
                    </div>
                    <div style="margin-top: 7px;" class="miniblog-content-f">
                        
                        <div class="divDiscussAward">
                            <div style="margin-right: 3px; line-height: 16px;" class="float-r">
                                <img align="absmiddle" src="/Images/Icon/discuss.png">
                                <a userid="" miniblogid="58248" style="color: rgb(97, 97, 97);" class="DiscussMiniBlog linkMarkReadRecommend" href="javascript:;">评论（<font>2</font>）</a>
                            </div>
                            
                            <div style="line-height: 16px;" class="float-r">
                                <img align="absmiddle" src="/Images/MiniBlog/award.png">
                                <a title="给话题发布者打赏100积分，让你的好友和粉丝都能看到该话题！" style="color: rgb(97, 97, 97);" userid="" miniblogid="58248" class="SupportMiniBlog linkMarkReadRecommend" href="javascript:;">
                                    打赏</a>
                            </div>
                            
                        </div>
                    </div>
                    
                    <div miniblogid="58248" class="discusscontent-border">
                        <div style="margin: 5px 5px 0pt 0pt;" class="discusshead">
                        </div>
                        <div class="discusscontent">
                        </div>
                    </div>
                    
                    <div title="为该话题发布者发了红包的居民" class="supportminiblog-border">
                        <img border="0" align="absmiddle" src="/Images/MiniBlog/award.png">&nbsp;
                        
                        <a href="/User/21838">
                            小分队</a>&nbsp;
                        
                    </div>
                    
                </div>
            </div>
        </div>
        
        <div miniblogid="58245" class="miniblog-info">
            <div class="miniblog-info-r">
                <div miniblogid="58245" class="miniblog-content">
                    <div class="miniblog-content-h">
                        
                        <div style="margin-right: 10px; width: 48%; text-align: right; color: rgb(132, 132, 132);" class="float-r">
                            
                            <a style="color: rgb(132, 132, 132);" title="我会啊" target="_blank" href="/User/213">
                                我会啊</a>
                            
                            分享于<a style="color: rgb(132, 132, 132);" target="_blank" href="/t/58245">
                                2010-03-18 18:33
                                </a>
                        </div>
                    </div>
                    <div class="miniblog-content-m">
                        
                        <div class="miniblog-content-m-l">
                            
                            <a title="点击看大图" class="miniblog-img ShowBigImage" href="javascript:;">
                                <img width="120" height="27" border="0" source="/files/MiniBlog/58245/a0da66a3-437a-4885-afe9-434c374d423e.jpg" onload="ResizeImage(this,120, 80, 3)" src="/files/MiniBlog/58245/a0da66a3-437a-4885-afe9-434c374d423e_w_120_0.jpg" style="margin-left: 0pt; margin-top: 26.5px;">
                            </a>
                            
                        </div>
                        
                        <div class="miniblog-content-m-r">
                            3月18日消息：<a linktopicid="2035064" target="_blank" href="/Topic/2035064">我会啊</a>新版上线咯！网站首页进行了一些小的调整，参与或操作游戏（做任务、属性、爆料、猜猜、投票等）请点击图中“免费赚礼品”按钮，进入“赚积分换奖券”页面，选择要参与的赚分游戏，再点操作按钮进入即可。
                            <a target="_blank" style="color: rgb(24, 107, 150); border: 0pt none;" class="linkMarkReadRecommend" href="/t/58245">&gt;&gt;查看详细</a>
                            
                        </div>
                    </div>
                    <div style="margin-top: 7px;" class="miniblog-content-f">
                        
                        <a title="我会啊" class="float-l" target="_blank" href="/Topic/2035064">
                            <img align="absmiddle" src="/Images/MiniBlog/read.png">
                            了解“我会啊”更多详细内容</a>
                        
                        <div class="divDiscussAward">
                            <div style="margin-right: 3px; line-height: 16px;" class="float-r">
                                <img align="absmiddle" src="/Images/Icon/discuss.png">
                                <a userid="" miniblogid="58245" style="color: rgb(97, 97, 97);" class="DiscussMiniBlog linkMarkReadRecommend" href="javascript:;">评论（<font>2</font>）</a>
                            </div>
                            
                            <div style="line-height: 16px;" class="float-r">
                                <img align="absmiddle" src="/Images/MiniBlog/award.png">
                                <a title="给话题发布者打赏100积分，让你的好友和粉丝都能看到该话题！" style="color: rgb(97, 97, 97);" userid="" miniblogid="58245" class="SupportMiniBlog linkMarkReadRecommend" href="javascript:;">
                                    打赏</a>
                            </div>
                            
                        </div>
                    </div>
                    
                    <div miniblogid="58245" class="discusscontent-border">
                        <div style="margin: 5px 5px 0pt 0pt;" class="discusshead">
                        </div>
                        <div class="discusscontent">
                        </div>
                    </div>
                    
                    <div title="为该话题发布者发了红包的居民" class="supportminiblog-border">
                        <img border="0" align="absmiddle" src="/Images/MiniBlog/award.png">&nbsp;
                        
                        <a href="/User/21838">
                            小分队</a>&nbsp;
                        
                    </div>
                    
                </div>
            </div>
        </div>
        
        <div miniblogid="18049" class="miniblog-info">
            <div class="miniblog-info-r">
                <div miniblogid="18049" class="miniblog-content">
                    <div class="miniblog-content-h">
                        
                        <div style="margin-right: 10px; width: 48%; text-align: right; color: rgb(132, 132, 132);" class="float-r">
                            
                            <a style="color: rgb(132, 132, 132);" title="我会啊" target="_blank" href="/User/213">
                                我会啊</a>
                            
                            分享于<a style="color: rgb(132, 132, 132);" target="_blank" href="/t/18049">
                                2010-03-03 15:02
                                </a>
                        </div>
                    </div>
                    <div class="miniblog-content-m">
                        
                        <div class="miniblog-content-m-l">
                            
                            <a class="miniblog-img" target="_blank" href="/Topic/2035064">
                                <img width="120" height="42" border="0" source="/files/Topic/2035064/181d175f-d4a1-4445-9d37-ac708d75130f.png" onload="ResizeImage(this,120, 80, 3)" src="/files/Topic/2035064/181d175f-d4a1-4445-9d37-ac708d75130f.png_w_120_0.jpg" style="margin-left: 0pt; margin-top: 19px;">
                            </a>
                            
                        </div>
                        
                        <div class="miniblog-content-m-r">
                            <a linktopicid="2035064" target="_blank" href="/Topic/2035064">我会啊</a>新增功能&mdash;&mdash;添加图片到“图片临时文件夹”！使用此功能能快速将其它网站上的图片（最多同时可存12张）存到这个文件夹，再发布到<a linktopicid="2035064" target="_blank" href="/Topic/2035064">我会啊</a>网站上。无需将图片保存到电脑再上传到<a linktopicid="2035064" target="_blank" href="/Topic/2035064">我会啊</a>，上传图片更轻松了。在微博中发图或完善主题封面、照片等属性内容时都可使用该功能，非常方便，欢迎大家试用。
                            <a target="_blank" style="color: rgb(24, 107, 150); border: 0pt none;" class="linkMarkReadRecommend" href="/t/18049">&gt;&gt;查看详细</a>
                            
                        </div>
                    </div>
                    <div style="margin-top: 7px;" class="miniblog-content-f">
                        
                        <a title="我会啊教室,我会啊!" target="_blank" class="roundbutton float-l" href="http://www.wohuia.com/Help/?CID=20&amp;CN=%e5%88%86%e4%ba%ab%e7%bd%91%e9%a1%b5&amp;HID=178&amp;HN=%e5%a6%82%e4%bd%95%e4%bd%bf%e7%94%a8%e5%9b%be%e7%89%87%e4%b8%b4%e6%97%b6%e6%96%87%e4%bb%b6%e5%a4%b9%e5%8a%9f%e8%83%bd%ef%bc%9f"><span class="l"></span>
                            <font class="m">
                                阅读：我会啊教室,我会啊!</font>
                            <span class="r"></span></a>
                        
                        <div class="divDiscussAward">
                            <div style="margin-right: 3px; line-height: 16px;" class="float-r">
                                <img align="absmiddle" src="/Images/Icon/discuss.png">
                                <a userid="" miniblogid="18049" style="color: rgb(97, 97, 97);" class="DiscussMiniBlog linkMarkReadRecommend" href="javascript:;">评论（<font>1</font>）</a>
                            </div>
                            
                            <div style="line-height: 16px;" class="float-r">
                                <img align="absmiddle" src="/Images/MiniBlog/award.png">
                                <a title="给话题发布者打赏100积分，让你的好友和粉丝都能看到该话题！" style="color: rgb(97, 97, 97);" userid="" miniblogid="18049" class="SupportMiniBlog linkMarkReadRecommend" href="javascript:;">
                                    打赏</a>
                            </div>
                            
                        </div>
                    </div>
                    
                    <div miniblogid="18049" class="discusscontent-border">
                        <div style="margin: 5px 5px 0pt 0pt;" class="discusshead">
                        </div>
                        <div class="discusscontent">
                        </div>
                    </div>
                    
                    <div title="为该话题发布者发了红包的居民" class="supportminiblog-border">
                        <img border="0" align="absmiddle" src="/Images/MiniBlog/award.png">&nbsp;
                        
                        <a href="/User/21838">
                            小分队</a>&nbsp;
                        
                    </div>
                    
                </div>
            </div>
        </div>
        
        <div miniblogid="5060" class="miniblog-info">
            <div class="miniblog-info-r">
                <div miniblogid="5060" class="miniblog-content">
                    <div class="miniblog-content-h">
                        
                        <div style="margin-right: 10px; width: 48%; text-align: right; color: rgb(132, 132, 132);" class="float-r">
                            
                            <a style="color: rgb(132, 132, 132);" title="我会啊" target="_blank" href="/User/213">
                                我会啊</a>
                            
                            分享于<a style="color: rgb(132, 132, 132);" target="_blank" href="/t/5060">
                                2010-02-10 16:30
                                </a>
                        </div>
                    </div>
                    <div class="miniblog-content-m">
                        
                        <div style="margin-left: 0pt;" class="miniblog-content-m-r">
                            我发表了博文《<a target="_blank" href="/User/Post/213/168516">我会啊春节放假公告</a>》，欢迎大家来围观。
                            <a target="_blank" style="color: rgb(24, 107, 150); border: 0pt none;" class="linkMarkReadRecommend" href="/t/5060">&gt;&gt;查看详细</a>
                            
                        </div>
                    </div>
                    <div style="margin-top: 7px;" class="miniblog-content-f">
                        
                        <div class="divDiscussAward">
                            <div style="margin-right: 3px; line-height: 16px;" class="float-r">
                                <img align="absmiddle" src="/Images/Icon/discuss.png">
                                <a userid="" miniblogid="5060" style="color: rgb(97, 97, 97);" class="DiscussMiniBlog linkMarkReadRecommend" href="javascript:;">评论（<font>0</font>）</a>
                            </div>
                            
                            <div style="line-height: 16px;" class="float-r">
                                <img align="absmiddle" src="/Images/MiniBlog/award.png">
                                <a title="给话题发布者打赏100积分，让你的好友和粉丝都能看到该话题！" style="color: rgb(97, 97, 97);" userid="" miniblogid="5060" class="SupportMiniBlog linkMarkReadRecommend" href="javascript:;">
                                    打赏</a>
                            </div>
                            
                        </div>
                    </div>
                    
                    <div miniblogid="5060" class="discusscontent-border">
                        <div style="margin: 5px 5px 0pt 0pt;" class="discusshead">
                        </div>
                        <div class="discusscontent">
                        </div>
                    </div>
                    
                    <div title="为该话题发布者发了红包的居民" class="supportminiblog-border">
                        <img border="0" align="absmiddle" src="/Images/MiniBlog/award.png">&nbsp;
                        
                        <a href="/User/21838">
                            小分队</a>&nbsp;
                        
                    </div>
                    
                </div>
            </div>
        </div>
        -->
        
    </div>
    <div class="f">
        <a href="/User/MiniBlog/13148/1">查看我的全部话题</a>
    </div>
</div>


<div class="newestinformation">
    <div class="head">
        <div class="title">
            我的博文</div>
        <span>
            
            <a class="addpost" href="/User/EditPost/13148/0">&nbsp;</a>
            
        </span>
    </div>
    <div class="tips">
        我已发表了 <font color="red">
            1</font> 篇博文
    </div>
    <div class="main">
        
        <div class="info">
            <div class="title">
                <a style="width: 675px;" class="maxlen" target="_blank" title="哈哈。" href="/User/Post/13148/298013">
                    哈哈。</a></div>
            <div class="datetime">
                2010/6/30 2:10:59</div>
            <table width="675" height="72" cellspacing="0" cellpadding="0" border="0" style="text-align: left; line-height: 22px; font-size: 14px; margin: 0pt auto;">
                <tbody><tr>
                    <td valign="top">
                        
                        哈哈。哈哈。哈哈。哈哈。
                    </td>
                </tr>
            </tbody></table>
        </div>
        
    </div>
    <div class="bottom">
        <a href="/User/Posts/13148/all">查看我的全部博文</a>
    </div>
</div>

</div>

<div style="width: 293px;" class="site-main-r">
                
                
<div class="ownerinfo-border">
    <div class="inner">
        <div class="friends-info">
            <div class="font-border">
                <font>0</font>
                <a href="/User/Friends/13148/1">好友</a>
            </div>
            <div class="font-border">
                <font>1</font>
                <a href="/User/Friends/13148/2">关注</a>
            </div>
            <div style="border-right: 0pt none;" class="font-border">
                <font>0</font>
                <a href="/User/Friends/13148/3">粉丝</a>
            </div>
        </div>
        
        <#if authed>
        <div class="actions">
            <div userid="14013" class="friendbutton btnAddFriend">
                <div class="icon">
                </div>
                加关注
            </div>
            <div userid="14013" sendtype="1" class="messagebutton btnSendMessage">
                <div class="icon">
                </div>
                <a href="${urls.getURL('/profile/messages/compose')}?to=${user.id}">发送消息</a>
            </div>
            <div url="#" class="liuyanbutton btnLiuYan">
                <div class="icon">
                </div>
                给我留言
            </div>
        </div>
        </#if>
            
        <div class="myassets">
            <div class="item">
                性别：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                男
            </div>
           
            <div class="item">
                我的等级：&nbsp;&nbsp;&nbsp;&nbsp;
                <a style="color: rgb(255, 255, 255);" title="用户等级" target="_blank" href="http://www.wohuia.com/Help/?CID=6&amp;CN=%E7%94%A8%E6%88%B7%E7%AD%89%E7%BA%A7&amp;HID=45&amp;HN=%E7%94%A8%E6%88%B7%E7%BA%A7%E5%88%AB%E4%B8%8E%E5%A4%B4%E8%A1%94"><b>Lv.1</b></a>
            </div>
            <div class="item">
                我的积分：
                <a style="color: rgb(248, 246, 158); font-size: 14px; font-weight: bold;" target="_blank" href="/Goods"><img border="0" align="absmiddle" title="积分" src="/Images/Icon/xinks.png">1200</a>
            </div>
            <div class="item">
                我的经验值：
                0
            </div>
            
        </div>
    </div>
</div>
<#--
<div class="userinfo-border">
    <div class="h">
        <a title="隐藏" class="hideicon btnHideOrShow" href="javascript:;">
        </a>
        <div class="infotitle">
            &nbsp;我的心愿</div>
        <a class="button" href="/User/Goods/13148/2">查看更多心愿</a>
    </div>
    <div class="m">
        
        <div style="color: rgb(132, 132, 132); text-align: left;" class="goodslist">
            我目前拥有的积分可兑换
            <a style="color: rgb(228, 75, 0); font-size: 14px; font-weight: bold;" target="_blank" href="/Goods"><img border="0" align="absmiddle" title="兑奖券，1兑奖券=10000积分" src="/Images/Icon/xinys.png">0</a>
            <a style="display: block; margin: 10px auto; width: 100%; text-align: right; font-size: 14px; color: rgb(228, 75, 0);" target="_blank" href="/Wishing/List">&gt;&gt; 立即到许愿树许愿</a>
        </div>
        
        <div style="border-bottom: 0px none;" class="goodslist">
            <div class="tips">
                提示：这个心愿很受欢迎哦，您想不想拥有？</div>
            <div class="left">
                <a title="英伦修身西装套装" target="_blank" href="/Goods/207">
                    <img width="80" height="80" border="0" onload="ResizeImage(this,80,65,1)" src="/files/Goods/207/92600a18-ea7a-482f-9262-f0cfb6746681_w_78_0.jpg" style="margin-left: 0pt; margin-top: 0pt;">
                </a>
            </div>
            <dl class="right">
                <dd>
                    <a title="英伦修身西装套装" target="_blank" href="/Goods/207">
                        英伦修身西装套装</a></dd>
                <dt>
                    <div class="money">
                        <a style="color: rgb(228, 75, 0); font-size: 14px; font-weight: bold;" target="_blank" href="/Goods"><img border="0" align="absmiddle" title="兑奖券，1兑奖券=10000积分" src="/Images/Icon/xinys.png">200</a></div>
                </dt>
            </dl>
        </div>
        
    </div>
</div>

<#--
<div class="userinfo-border">
    <div class="h">
        <a title="隐藏" class="hideicon btnHideOrShow" href="javascript:;">
        </a>
        <div class="infotitle">
            &nbsp;邀请好友</div>
    </div>
    <div class="m">
        <div class="invitetips">
            成功邀请您的好友注册[我会啊]，今后您可以从您的好友赚取的积分份额中得到相应的提成。马上获取邀请链接发给您的好友吧!
        </div>
        <a copyinfo="http://www.wohuia.com/Reg?id=13148" copymsg="好友邀请链接复制好了，请按Ctrl+V粘贴到QQ、MSN、论坛..." title="点击复制邀请链接" class="button btnCopyInfo" href="javascript:;">
            点击获取邀请链接
        </a>
    </div>
</div>
<div class="mydynamicinfo">

    <div class="head">
        <div class="title">
            我邀请好友的奖励</div>
        <span>
            <a target="_blank" href="/User/AwardDetails/13148/20">全部记录（<font color="#e44b00">0个</font>）</a>
            &nbsp;
        </span>
    </div>

    <div class="dynamicinfo">
        <a style="display: block; height: 30px; line-height: 30px; margin-left: 10px;" target="_blank" href="http://www.wohuia.com/Help/?CID=10&amp;CN=%E6%96%B0%E6%89%8B%E5%85%A5%E9%97%A8&amp;HID=9&amp;HN=%E5%A6%82%E4%BD%95%E9%82%80%E8%AF%B7%E5%A5%BD%E5%8F%8B%E5%8A%A0%E5%85%A5%E6%88%91%E4%BC%9A%E5%95%8A%EF%BC%9F">邀请好友一起来赚积分，了解如何邀请好友？</a>
    </div>

    <div class="head">
        <div class="title">
            我最新贡献过的主题</div>
        <span>
            <a target="_blank" href="/User/AwardDetails/13148/3">全部记录（<font color="#e44b00">0个</font>）</a>
            &nbsp;
        </span>
    </div>

    <div class="dynamicinfo"> 
        <a style="display: block; height: 30px; line-height: 30px; margin-left: 10px;" target="_blank" href="http://www.wohuia.com/Help/?CID=13&amp;CN=%E4%B8%BB%E9%A2%98%E6%93%8D%E4%BD%9C%E6%8C%87%E5%8D%97">建设主题从属性做起，如何完善主题属性？</a>
    </div>

    <div class="head">
        <div class="title">
            我最新完成的赏积分任务</div>
        <span>
            <a target="_blank" href="/User/AwardDetails/13148/4">全部记录（<font color="#e44b00">0个</font>）</a>
            &nbsp;
        </span>
    </div>

    <div class="dynamicinfo"> 
        <a style="display: block; height: 30px; line-height: 30px; margin-left: 10px;" target="_blank" href="http://www.wohuia.com/Help/?CID=3&amp;CN=%E8%B5%8F%E9%94%8C%E4%BB%BB%E5%8A%A1">做任务赚高积分，了解赏积分任务规则</a>
    </div>

    <div class="head">
        <div class="title">
            我最新发布的精华帖</div>
        <span>
            <a target="_blank" href="/User/AwardDetails/13148/2">全部记录（<font color="#e44b00">0个</font>）</a>
            &nbsp;
        </span>
    </div>

    <div class="dynamicinfo"> 
        <a style="display: block; height: 30px; line-height: 30px; margin-left: 10px;" target="_blank" href=""></a>
    </div>

</div>

-->