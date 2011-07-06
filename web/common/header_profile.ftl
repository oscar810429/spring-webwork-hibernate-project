<#assign authed = currentUser?exists>

<div class="l">
            <div title="点击看大图" class="img ShowBigImage">
                <img width="75" height="100" source="#" src="<@userIcon user=currentUser/>" id="imgCurrUserPhoto" style="margin-left: 12.5px; margin-top: 0pt;">
            </div>
            
            <a id="btnUpdateUserInfo" class="button" href="javascript:;">修改资料</a>
            
</div>

<div class="r">
            <div class="spacename-border">
                <div style="float: left;" id="divSpaceName">
                    <div style="display: block;">
                        <span class="spacename">
                            ${currentUser.nickname?html}个人空间</span>
                        
                        [<a id="btnEditSpaceName" href="javascript:void(0)">编辑</a>]
                        
                    </div>
                </div>
                <a title="如何使用个人空间？" style="float: right;" class="help" target="_blank" href="#"></a>
            </div>
            <div class="userintro-border">
                <div style="float: left;" id="divUserIntro">
                    <div style="display: block;">
                        <span class="userintro">
                            这家伙很懒,什么也没有留下</span>
                        
                        [<a id="btnEditUserIntro" href="javascript:void(0)">编辑</a>]
                        
                    </div>
                </div>
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