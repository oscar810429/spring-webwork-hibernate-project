<#include "/common/macros.ftl">
<@startPage title="软件导航"/>
<#assign authed = currentUser?exists>
<content tag="styles">
<@css_file src="/styles/category.css" v="20100523"/>
</content>
<content tag="nav">
<div class="menu">
            
            <a href="/">首页</a> &gt;
            <font title="软件导航">软件导航</font>
            
</div>
</content>

<div class="domainmap-main">
            <div class="h">
                <div class="font">
                </div>
                <div class="info">
                    当前共有软件分类<span style="color: rgb(24, 107, 150);">&nbsp;10</span>&nbsp;个，包括的开源软件有
                    <span style="color: rgb(24, 107, 150);">&nbsp;0</span>&nbsp;个
                </div>
            </div>
            
            <div class="list">
                <#list categories as category>
                
                <div class="info">
                    <div class="yuan">
                        <a target="_blank" title="${category.name}" href="${urls.getURL('/softwares/tag')}?cid=${category.id}">
                            ${category.name}</a>
                    </div>
                    <div class="info-tab">
                        <div class="top">
                            <div class="left">
                                有&nbsp;<span style="color: rgb(24, 107, 150);">${category.amount}</span>&nbsp;个开源软件</div>
                            <#--<a href="javascript:;" id="div_0" onclick="ChangeListClass(this);" class="font">
                                全部显示</a>-->
                        </div>
                        <div class="info-tab-list">
                            <div id="border_style0" class="border">
                                
                                <#list category.categories as subcategory>
                                <a class="font1" title="${subcategory.name}" target="_blank" href="${urls.getURL('/softwares/tag')}?cid=${subcategory.id}">
                                    ${subcategory.name}</a>&nbsp;
                                </#list>
                                
                            </div>
                        </div>
                    </div>
                </div>
                
                </#list>
                
                
            </div>
</div>

