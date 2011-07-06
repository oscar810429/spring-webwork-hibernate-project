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

<div class="standard_form" id="profile">       
    <form method="post" action="${urls.getUserURL('/profile/save_profile')}">
  <#if profile?has_content>
  <input type="hidden" name="profile.id" value="${profile.id}" />
  </#if>
    <div style="display: none;" class="error_msg" id="error_msg"></div>

        <label>电子邮箱</label>
         <p><strong>${user.email?replace('@', ' [at] ' , 'i')}</strong></p> 
                 
        <label>请选择职业</label>
         <p>
          <select name="profile.occupation">
              <option value="0">${action.getText('label.profile.choose.occupation')}</option>
                  <OPTION VALUE="2400" <#if profile.occupation?has_content && profile.occupation== 2400>selected</#if> >${action.getText("occuption.name2400")}</OPTION>
              <OPTION VALUE="2401" <#if profile.occupation?has_content && profile.occupation== 2401>selected</#if> >${action.getText("occuption.name2401")}</OPTION>
              <OPTION VALUE="2402" <#if profile.occupation?has_content && profile.occupation== 2402>selected</#if> >${action.getText("occuption.name2402")}</OPTION>
              <OPTION VALUE="2403" <#if profile.occupation?has_content && profile.occupation== 2403>selected</#if> >${action.getText("occuption.name2403")}</OPTION>
              <OPTION VALUE="2404" <#if profile.occupation?has_content && profile.occupation== 2404>selected</#if> >${action.getText("occuption.name2404")}</OPTION>
              <OPTION VALUE="2405" <#if profile.occupation?has_content && profile.occupation== 2405>selected</#if> >${action.getText("occuption.name2405")}</OPTION>
              <OPTION VALUE="2406" <#if profile.occupation?has_content && profile.occupation== 2406>selected</#if> >${action.getText("occuption.name2406")}</OPTION>
              <OPTION VALUE="2407" <#if profile.occupation?has_content && profile.occupation== 2407>selected</#if> >${action.getText("occuption.name2407")}</OPTION>
              <OPTION VALUE="2408" <#if profile.occupation?has_content && profile.occupation== 2408>selected</#if> >${action.getText("occuption.name2408")}</OPTION>
              <OPTION VALUE="2409" <#if profile.occupation?has_content && profile.occupation== 2409>selected</#if> >${action.getText("occuption.name2409")}</OPTION>
              <OPTION VALUE="2410" <#if profile.occupation?has_content && profile.occupation == 2410>selected</#if> >${action.getText("occuption.name2410")}</OPTION>
              <OPTION VALUE="2411" <#if profile.occupation?has_content && profile.occupation == 2411>selected</#if> >${action.getText("occuption.name2411")}</OPTION>
              <OPTION VALUE="2412" <#if profile.occupation?has_content && profile.occupation == 2412>selected</#if> >${action.getText("occuption.name2412")}</OPTION>
              <OPTION VALUE="2413" <#if profile.occupation?has_content && profile.occupation == 2413>selected</#if> >${action.getText("occuption.name2413")}</OPTION>
              <OPTION VALUE="2414" <#if profile.occupation?has_content && profile.occupation == 2414>selected</#if> >${action.getText("occuption.name2414")}</OPTION>
              <OPTION VALUE="2415" <#if profile.occupation?has_content && profile.occupation == 2415>selected</#if> >${action.getText("occuption.name2415")}</OPTION>
              <OPTION VALUE="2416" <#if profile.occupation?has_content && profile.occupation == 2416>selected</#if> >${action.getText("occuption.name2416")}</OPTION>
              <OPTION VALUE="2417" <#if profile.occupation?has_content && profile.occupation == 2417>selected</#if> >${action.getText("occuption.name2417")}</OPTION>
              <OPTION VALUE="2418" <#if profile.occupation?has_content && profile.occupation == 2418>selected</#if> >${action.getText("occuption.name2418")}</OPTION>
              <OPTION VALUE="2419" <#if profile.occupation?has_content && profile.occupation == 2419>selected</#if> >${action.getText("occuption.name2419")}</OPTION>
              <OPTION VALUE="2420" <#if profile.occupation?has_content && profile.occupation == 2420>selected</#if> >${action.getText("occuption.name2420")}</OPTION>
              <OPTION VALUE="2421" <#if profile.occupation?has_content && profile.occupation == 2421>selected</#if> >${action.getText("occuption.name2421")}</OPTION>
              <OPTION VALUE="2422" <#if profile.occupation?has_content && profile.occupation == 2422>selected</#if> >${action.getText("occuption.name2422")}</OPTION>
              <OPTION VALUE="2423" <#if profile.occupation?has_content && profile.occupation == 2423>selected</#if> >${action.getText("occuption.name2423")}</OPTION>
              <OPTION VALUE="2424" <#if profile.occupation?has_content && profile.occupation == 2424>selected</#if> >${action.getText("occuption.name2424")}</OPTION>
              <OPTION VALUE="2425" <#if profile.occupation?has_content && profile.occupation == 2425>selected</#if> >${action.getText("occuption.name2425")}</OPTION>
              <OPTION VALUE="2426" <#if profile.occupation?has_content && profile.occupation == 2426>selected</#if> >${action.getText("occuption.name2426")}</OPTION>
              <OPTION VALUE="2427" <#if profile.occupation?has_content && profile.occupation == 2427>selected</#if> >${action.getText("occuption.name2427")}</OPTION>
              <OPTION VALUE="2428" <#if profile.occupation?has_content && profile.occupation == 2428>selected</#if> >${action.getText("occuption.name2428")}</OPTION>
              <OPTION VALUE="2429" <#if profile.occupation?has_content && profile.occupation == 2429>selected</#if> >${action.getText("occuption.name2429")}</OPTION>
              <OPTION VALUE="2430" <#if profile.occupation?has_content && profile.occupation == 2430>selected</#if> >${action.getText("occuption.name2430")}</OPTION>
              <OPTION VALUE="2431" <#if profile.occupation?has_content && profile.occupation == 2431>selected</#if> >${action.getText("occuption.name2431")}</OPTION>
              <OPTION VALUE="2432" <#if profile.occupation?has_content && profile.occupation == 2432>selected</#if> >${action.getText("occuption.name2432")}</OPTION>
              <OPTION VALUE="2433" <#if profile.occupation?has_content && profile.occupation == 2433>selected</#if> >${action.getText("occuption.name2433")}</OPTION>
              <OPTION VALUE="2434" <#if profile.occupation?has_content && profile.occupation == 2434>selected</#if> >${action.getText("occuption.name2434")}</OPTION>
              <OPTION VALUE="2435" <#if profile.occupation?has_content && profile.occupation == 2435>selected</#if> >${action.getText("occuption.name2435")}</OPTION>
              <OPTION VALUE="2436" <#if profile.occupation?has_content && profile.occupation == 2436>selected</#if> >${action.getText("occuption.name2436")}</OPTION>
              <OPTION VALUE="2437" <#if profile.occupation?has_content && profile.occupation == 2437>selected</#if> >${action.getText("occuption.name2437")}</OPTION>
              <OPTION VALUE="2438" <#if profile.occupation?has_content && profile.occupation == 2438>selected</#if> >${action.getText("occuption.name2438")}</OPTION>
              <OPTION VALUE="2439" <#if profile.occupation?has_content && profile.occupation == 2439>selected</#if> >${action.getText("occuption.name2439")}</OPTION>
              <OPTION VALUE="2440" <#if profile.occupation?has_content && profile.occupation == 2440>selected</#if> >${action.getText("occuption.name2440")}</OPTION>
              <OPTION VALUE="2441" <#if profile.occupation?has_content && profile.occupation == 2441>selected</#if> >${action.getText("occuption.name2441")}</OPTION>
              <OPTION VALUE="2442" <#if profile.occupation?has_content && profile.occupation == 2442>selected</#if> >${action.getText("occuption.name2442")}</OPTION>
              <OPTION VALUE="2443" <#if profile.occupation?has_content && profile.occupation == 2443>selected</#if> >${action.getText("occuption.name2443")}</OPTION>
              <OPTION VALUE="2444" <#if profile.occupation?has_content && profile.occupation == 2444>selected</#if> >${action.getText("occuption.name2444")}</OPTION>
              <OPTION VALUE="2445" <#if profile.occupation?has_content && profile.occupation == 2445>selected</#if> >${action.getText("occuption.name2445")}</OPTION>
              <OPTION VALUE="2446" <#if profile.occupation?has_content && profile.occupation == 2446>selected</#if> >${action.getText("occuption.name2446")}</OPTION>
              <OPTION VALUE="2447" <#if profile.occupation?has_content && profile.occupation == 2447>selected</#if> >${action.getText("occuption.name2447")}</OPTION>
              <OPTION VALUE="2448" <#if profile.occupation?has_content && profile.occupation == 2448>selected</#if> >${action.getText("occuption.name2448")}</OPTION>
              <OPTION VALUE="2449" <#if profile.occupation?has_content && profile.occupation == 2449>selected</#if> >${action.getText("occuption.name2449")}</OPTION>
              <OPTION VALUE="2450" <#if profile.occupation?has_content && profile.occupation == 2450>selected</#if> >${action.getText("occuption.name2450")}</OPTION>
              <OPTION VALUE="2451" <#if profile.occupation?has_content && profile.occupation == 2451>selected</#if> >${action.getText("occuption.name2451")}</OPTION>
              <OPTION VALUE="2452" <#if profile.occupation?has_content && profile.occupation == 2452>selected</#if> >${action.getText("occuption.name2452")}</OPTION>
             </select>                 
       </p>
       
      <label>姓名</label>   
       <p><@ww.textfield id="profile_name" name="profile.name" value="profile.name" cssClass="text" cssStyle="width: 200px;;"/><span>不能超过10个字</span></p>
       
       <label>${action.getText('label.profile.birthday')}</label> 
        <p>
            <@ww.textfield id="birthdayYear" name="birthdayYear" value="birthdayYear" size="4" maxlength="4"/>
            <select name="birthdayMonth">
              <option value="0">${action.getText('label.profile.month')}</option>
              <#list 1..12 as month>
              <option value="${month?c}"<#if birthdayMonth=month> selected="selected"</#if>>${month?c?left_pad(2, "0")}</option>
              </#list>
            </select>
            <select name="birthdayDay">
              <option value="0">${action.getText('label.profile.day')}</option>
              <#list 1..31 as day>
              <option value="${day?c}"<#if birthdayDay=day> selected="selected"</#if>>${day?c?left_pad(2, "0")}</option>
              </#list>
            </select>
            <span class="info_normal">(${action.getText('hint.birthdayYear.input')})</span>
      </p>
      
      <label>${action.getText('label.profile.married')}</label>
       <p><input type="radio" name="profile.marriageState" value="0" id="not_married" <#if profile.marriageState == 0>checked="checked"</#if> /><span id="not_married">${action.getText('married.no')}</span>
            <input type="radio" name="profile.marriageState" value="1" id="married" <#if profile.marriageState == 1>checked="checked"</#if> /><span id="married">${action.getText('married.yes')}</span>
            <#--<input type="radio" name="profile.marriageState" value="2" id="expect_friends" <#if profile.marriageState == 2>checked="checked"</#if> /><label for="expect_friends">${action.getText('married.expect.friend')}</labe>
            <input type="radio" name="profile.marriageState" value="4" id="expect_male_friends" <#if profile.marriageState == 4>checked="checked"</#if> /><label for="expect_male_friends">${action.getText('married.male.expect.friend')}</label>
            <input type="radio" name="profile.marriageState" value="5" id="expect_female_friends" <#if profile.marriageState == 5>checked="checked"</#if> /><label for="expect_female_friends">${action.getText('married.female.expect.friend')}</label>-->
            <input type="radio" name="profile.marriageState" value="3" id="married_secret" <#if profile.marriageState == 3>checked="checked"</#if> /><span id="married_secret">${action.getText('married.secret')}</span>
       </p>
       
        <label>性别</label>   
        <p>
            <input type="radio" name="profile.sex" value="1" id="sex_female" <#if profile.sex.value() == 1>checked="checked"</#if> /><span id="sex_female">${action.getText('sex.female')}</span>
            <input type="radio" name="profile.sex" value="2" id="sex_male" <#if profile.sex.value() == 2>checked="checked"</#if> /><span id="sex_male">${action.getText('sex.male')}</span>
            <#--<input type="radio" name="profile.sex" value="3" id="sex_other" <#if profile.sex.value() == 3>checked="checked"</#if> /><label for="sex_other">${action.getText('sex.other')}</label>-->
            <input type="radio" name="profile.sex" value="0" id="sex_secret" <#if profile.sex.value() == 0>checked="checked"</#if> /><span id="sex_secret">${action.getText('sex.secret')}</span>
      </p>
      
      <label>所在地区</label>   
     <p><select id="userProvince" name="profile.province" onchange="showcity(this.value, document.getElementById('userCity'));">
  <option value="">--请选择省份--</option>
  <option value="安徽" <#if profile.province?has_content><#if profile.province == "安徽">checked="checked"</#if></#if>>安徽</option> 
  <option value="北京" <#if profile.province?has_content><#if profile.province == "北京">checked="checked"</#if></#if>>北京</option> 
  <option value="重庆" <#if profile.province?has_content><#if profile.province == "重庆">checked="checked"</#if></#if>>重庆</option> 
  <option value="福建" <#if profile.province?has_content><#if profile.province == "福建">checked="checked"</#if></#if>>福建</option> 
  <option value="甘肃" <#if profile.province?has_content><#if profile.province == "甘肃">checked="checked"</#if></#if>>甘肃</option> 
  <option value="广东" <#if profile.province?has_content><#if profile.province == "广东">checked="checked"</#if></#if>>广东</option> 
  <option value="广西" <#if profile.province?has_content><#if profile.province == "广西">checked="checked"</#if></#if>>广西</option> 
  <option value="贵州" <#if profile.province?has_content><#if profile.province == "贵州">checked="checked"</#if></#if>>贵州</option> 
  <option value="海南" <#if profile.province?has_content><#if profile.province == "海南">checked="checked"</#if></#if>>海南</option> 
  <option value="河北" <#if profile.province?has_content><#if profile.province == "河北">checked="checked"</#if></#if>>河北</option> 
  <option value="黑龙江" <#if profile.province?has_content><#if profile.province == "黑龙江">checked="checked"</#if></#if>>黑龙江</option> 
  <option value="河南" <#if profile.province?has_content><#if profile.province == "河南">checked="checked"</#if></#if>>河南</option> 
  <option value="湖北" <#if profile.province?has_content><#if profile.province == "湖北">checked="checked"</#if></#if>>湖北</option> 
  <option value="湖南" <#if profile.province?has_content><#if profile.province == "湖南">checked="checked"</#if></#if>>湖南</option> 
  <option value="江苏" <#if profile.province?has_content><#if profile.province == "江苏">checked="checked"</#if></#if>>江苏</option> 
  <option value="江西" <#if profile.province?has_content><#if profile.province == "江西">checked="checked"</#if></#if>>江西</option> 
  <option value="吉林" <#if profile.province?has_content><#if profile.province == "吉林">checked="checked"</#if></#if>>吉林</option> 
  <option value="辽宁" <#if profile.province?has_content><#if profile.province == "辽宁">checked="checked"</#if></#if>>辽宁</option> 
  <option value="内蒙古" <#if profile.province?has_content><#if profile.province == "内蒙古">checked="checked"</#if></#if>>内蒙古</option> 
  <option value="宁夏" <#if profile.province?has_content><#if profile.province == "宁夏">checked="checked"</#if></#if>>宁夏</option> 
  <option value="青海" <#if profile.province?has_content><#if profile.province == "青海">checked="checked"</#if></#if>>青海</option> 
  <option value="山东" <#if profile.province?has_content><#if profile.province == "山东">checked="checked"</#if></#if>>山东</option> 
  <option value="上海" <#if profile.province?has_content><#if profile.province == "上海">checked="checked"</#if></#if>>上海</option> 
  <option value="山西" <#if profile.province?has_content><#if profile.province == "山西">checked="checked"</#if></#if>>山西</option> 
  <option value="陕西" <#if profile.province?has_content><#if profile.province == "陕西">checked="checked"</#if></#if>>陕西</option> 
  <option value="四川" <#if profile.province?has_content><#if profile.province == "四川">checked="checked"</#if></#if>>四川</option> 
  <option value="天津" <#if profile.province?has_content><#if profile.province == "天津">checked="checked"</#if></#if>>天津</option> 
  <option value="新疆" <#if profile.province?has_content><#if profile.province == "新疆">checked="checked"</#if></#if>>新疆</option> 
  <option value="西藏" <#if profile.province?has_content><#if profile.province == "西藏">checked="checked"</#if></#if>>西藏</option> 
  <option value="云南" <#if profile.province?has_content><#if profile.province == "云南">checked="checked"</#if></#if>>云南</option> 
  <option value="浙江" <#if profile.province?has_content><#if profile.province == "浙江">checked="checked"</#if></#if>>浙江</option> 
  <option value="香港" <#if profile.province?has_content><#if profile.province == "香港">checked="checked"</#if></#if>>香港特别行政区</option> 
  <option value="澳门" <#if profile.province?has_content><#if profile.province == "澳门">checked="checked"</#if></#if>>澳门特别行政区</option>
  <option value="台湾" <#if profile.province?has_content><#if profile.province == "台湾">checked="checked"</#if></#if>>台湾</option> 
  <option value="海外" <#if profile.province?has_content><#if profile.province == "海外">checked="checked"</#if></#if>>海外</option>
</select>
<select id="userCity" name="profile.city"><option value="">--城市--</option></select>
<script src="${app_root}/scripts/getcity.js" type="text/javascript"></script>
</p>

     <label>身份证号</label>   
     <p><@ww.textfield id="profile_postal" name="profile.postal" value="profile.postal"  cssClass="text" cssStyle="width:200px;"/></p>
      
      <label>博客</label>   
      <p><@ww.textfield id="profile_website" name="profile.website" value="profile.website"  cssClass="text" cssStyle="width:200px;"/></p>
      
      <label>手机号码</label>   
      <p><@ww.textfield id="profile_mobile" name="profile.mobile" value="profile.mobile"  cssClass="text"/></p>
      
      <label>MSN</label>    
      <p><@ww.textfield id="profile_msn" name="profile.msn" value="profile.msn" cssClass="text" /></p>
      
      <label>QQ</label>   
       <p><@ww.textfield id="profile_oicq" name="profile.oicq" value="profile.oicq" cssClass="text" /></p>
      
      <label>Skype</label>    
      <p><@ww.textfield id="profile_oicq" name="profile.skype" value="profile.skype" cssClass="text" /></p>
      
      <label>GTalk</label>    
       <p><@ww.textfield id="profile_oicq" name="profile.gtalk" value="profile.gtalk" cssClass="text" /></p>
      
      <label>个人简介</label>   
        <p><@ww.textarea id="profile_description" name="profile.description" value="profile.description" cols="50" rows="6" cssClass="text"  cssStyle="width:350px;"/></p>
      <p>
        <input type="submit" class="submit" value="修改资料"/>
      </p>
</form>
</div>
<script type="text/javascript">
init_province_and_city(document.getElementById('userProvince'),'<#if profile.province?has_content>${profile.province}</#if>',document.getElementById('userCity'),'<#if profile.city?has_content>${profile.city}</#if> ');
</script>
