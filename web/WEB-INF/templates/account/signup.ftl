<#include "/common/macros.ftl">
<@startPage title="会员注册"/>
<#assign authed = currentUser?exists>
<content tag="styles">
<@css_file src="/styles/register.css" v="20100523"/>
</content>
<content tag="nav">
<div class="menu">
            
            <a href="/">首页</a> &gt;
            <font title="注册">注册</font>
            
</div>
</content>
<div style="height: 10px; width: 100%;"></div>

<div id="divRegister" class="loginwindow2">
            <div class="logintitle">
                <div>
                    创建我的账户
                </div>
            </div>

            <div class="logininfo">
    <form class="gform" id="frm_reg" method="post"  action="${urls.getURL('/account/signup/')}">
    <#if params['forward']?exists>
    <input type="hidden" name="forward" value="${request.getParameter('forward')?html}" />
    </#if>
    <#if params['from']?exists>
    <input type="hidden" name="from" value="${request.getParameter('from')?html}" />
    </#if>
    <#if user?exists && user.id?exists>
    <input type="hidden" id="user_id" name="user.id" value="${user.id}" />
    </#if>
                <div class="logininfotext">
                    <strong>|</strong>电子邮箱
                </div>
                
                <div class="inputtextdiv">
                    <input type="text" class="inputtext" name="user.email" id="txtRegEmail1" tabindex="16"/>
                    <div class="mistakeicon" style="display: none;">
                    </div>
                </div>
                
                <div style="height: 15px;" class="logininfotext2_2"><#--邮箱地址不能为空！--></div>
                
                <div class="logininfotext">
                    <strong>|</strong>昵称
                </div>
                <div class="inputtextdiv">
                    <input type="text" tabindex="10" class="inputtext2" id="txtRegName" name="user.username">
                    <div class="byicon">
                    </div>
                </div>
                
                <div class="logininfotext2">
                    您在派牛网使用的唯一称呼，可作登录名使用。<br>
                    可由4位以上字符、数字、下划线组成<br>
                    <#--最多10个英文或5个中文-->
                </div>
                <div class="logininfotext">
                    <strong>|</strong>性别
                </div>
                <div class="inputtextdiv1">
                    <input type="radio" tabindex="12" checked="checked" value="1" id="radmale" name="gender">
                    男
                    <input type="radio" tabindex="13" value="0" id="radfemale" name="gender">
                    女
                </div>
                <div class="logininfotext">
                    <strong>|</strong>出生年月日
                </div>
                <div class="inputtextdiv1">
                    <select id="selYear" name="birthdayYear">
                    <option value="2010">2010</option>
                    <option value="2009">2009</option>
                    <option value="2008">2008</option>
                    <option value="2007">2007</option>
                    <option value="2006">2006</option>
                    <option value="2005">2005</option>
                    <option value="2004">2004</option>
                    <option value="2003">2003</option>
                    <option value="2002">2002</option>
                    <option value="2001">2001</option>
                    <option value="2000">2000</option>
                    <option value="1999">1999</option>
                    <option value="1998">1998</option>
                    <option value="1997">1997</option>
                    <option value="1996">1996</option>
                    <option value="1995">1995</option>
                    <option value="1994">1994</option>
                    <option value="1993">1993</option>
                    <option value="1992">1992</option>
                    <option value="1991">1991</option>
                    <option value="1990">1990</option>
                    <option value="1989">1989</option>
                    <option value="1988">1988</option>
                    <option value="1987">1987</option>
                    <option value="1986">1986</option>
                    <option value="1985">1985</option>
                    <option value="1984">1984</option>
                    <option value="1983">1983</option>
                    <option value="1982">1982</option>
                    <option value="1981">1981</option>
                    <option value="1980">1980</option>
                    <option value="1979">1979</option>
                    <option value="1978">1978</option>
                    <option value="1977">1977</option>
                    <option value="1976">1976</option>
                    <option value="1975">1975</option>
                    <option value="1974">1974</option>
                    <option value="1973">1973</option>
                    <option value="1972">1972</option>
                    <option value="1971">1971</option>
                    <option value="1970">1970</option>
                    <option value="1969">1969</option>
                    <option value="1968">1968</option>
                    <option value="1967">1967</option>
                    <option value="1966">1966</option>
                    <option value="1965">1965</option>
                    <option value="1964">1964</option>
                    <option value="1963">1963</option>
                    <option value="1962">1962</option>
                    <option value="1961">1961</option>
                    <option value="1960">1960</option>
                    <option value="1959">1959</option>
                    <option value="1958">1958</option>
                    <option value="1957">1957</option>
                    <option value="1956">1956</option>
                    <option value="1955">1955</option>
                    <option value="1954">1954</option>
                    <option value="1953">1953</option>
                    <option value="1952">1952</option>
                    <option value="1951">1951</option>
                    </select>年
                    <select id="selMonth" name="birthdayMonth">
                    <option selected="" value="1">1</option>
                    <option value="2">2</option>
                    <option value="3">3</option>
                    <option value="4">4</option>
                    <option value="5">5</option>
                    <option value="6">6</option>
                    <option value="7">7</option>
                    <option value="8">8</option>
                    <option value="9">9</option>
                    <option value="10">10</option>
                    <option value="11">11</option>
                    <option value="12">12</option>
                    </select>月
                    <select id="selDay" name="birthdayDay">
                    <option selected="" value="1">1</option>
                    <option value="2">2</option>
                    <option value="3">3</option>
                    <option value="4">4</option>
                    <option value="5">5</option>
                    <option value="6">6</option>
                    <option value="7">7</option>
                    <option value="8">8</option>
                    <option value="9">9</option>
                    <option value="10">10</option>
                    <option value="11">11</option>
                    <option value="12">12</option>
                    <option value="13">13</option>
                    <option value="14">14</option>
                    <option value="15">15</option>
                    <option value="16">16</option>
                    <option value="17">17</option>
                    <option value="18">18</option>
                    <option value="19">19</option>
                    <option value="20">20</option>
                    <option value="21">21</option>
                    <option value="22">22</option>
                    <option value="23">23</option>
                    <option value="24">24</option>
                    <option value="25">25</option>
                    <option value="26">26</option>
                    <option value="27">27</option>
                    <option value="28">28</option>
                    <option value="29">29</option>
                    <option value="30">30</option>
                    <option value="31">31</option>
                    </select>日

                </div>
                <div class="logininfotext">
                    <strong>|</strong>所在地
                </div>
       <div class="inputtextdiv1">
      <select id="selProvince" name="userProvince" onchange="showcity(this.value, document.getElementById('selCity'));" tabindex="14">
       <option selected="selected" value="">请选择所在省份</option>
        <option value="安徽">安徽</option> 
        <option value="北京">北京</option> 
        <option value="重庆">重庆</option> 
        <option value="福建">福建</option> 
        <option value="甘肃">甘肃</option> 
        <option value="广东">广东</option> 
        <option value="广西">广西</option> 
        <option value="贵州">贵州</option> 
        <option value="海南">海南</option> 
        <option value="河北">河北</option> 
        <option value="黑龙江">黑龙江</option> 
        <option value="河南">河南</option> 
        <option value="湖北">湖北</option> 
        <option value="湖南">湖南</option> 
        <option value="江苏">江苏</option> 
        <option value="江西">江西</option> 
        <option value="吉林">吉林</option> 
        <option value="辽宁">辽宁</option> 
        <option value="内蒙古">内蒙古</option> 
        <option value="宁夏">宁夏</option> 
        <option value="青海">青海</option> 
        <option value="山东">山东</option> 
        <option value="上海">上海</option> 
        <option value="山西">山西</option> 
        <option value="陕西">陕西</option> 
        <option value="四川">四川</option> 
        <option value="天津">天津</option> 
        <option value="新疆">新疆</option> 
        <option value="西藏">西藏</option> 
        <option value="云南">云南</option> 
        <option value="浙江">浙江</option> 
        <option value="香港">香港特别行政区</option> 
        <option value="澳门">澳门特别行政区</option>
        <option value="台湾">台湾</option> 
        <option value="海外">海外</option>
       </select>
        <select tabindex="15" id="selCity" name="userCity">
           <option selected="selected" value="">请选择所在城市</option>
            <script src="${media_root}/scripts/getcity.js" type="text/javascript"></script> 
         </select>
          </div>
          
                <div class="logininfotext">
                    <strong>|</strong>登录密码
                </div>
                <div class="inputtextdiv">
                    <input type="password" tabindex="17" class="inputtext2 marginright2px" id="txtRegPwd1" name="user.password">
                    <#--<div id="lowPwd" class="levelborder">
                        低
                    </div>
                    <div id="middlePwd" class="levelborder">
                        中
                    </div>
                    <div id="highPwd" class="levelborder">
                        高
                    </div>-->
                    <div class="byicon">
                    </div>
                </div>
                <div style="height: 15px;" class="logininfotext2">
                    密码为6-16位，高强度的密码由字符(区分大小写)、数字、符号构成
                </div>
                <div class="logininfotext">
                    <strong>|</strong>确认密码
                </div>
                <div class="inputtextdiv">
                    <input type="password" tabindex="18" class="inputtext2" id="txtRegPwd2" name="user.confirmPassword">
                    <div class="byicon">
                    </div>
                </div>
                <div style="height: 15px;" class="logininfotext2">
                    请再次输入登录密码
                </div>
                
                <div style="display: block;" class="logininfotext">
                    <strong>|</strong>验证码
                </div>
                <div style="display: block;" class="inputtextdiv">
                    <input type="text" style="width: 150px;" class="inputtext" name="verifyCode" id="f_vcode" tabindex="19"/>
                </div>
                <div style="height: 5px;" class="logininfotext2_2"></div>
                <div style="display: block;">    
                    <img onclick="this.src='/action/user/captcha?t='+Math.random(1000);" title="看不清？点击更换" alt="看不清？点击更换" src="/action/user/captcha" style="float: left; cursor: pointer;" id="imgDiscussValidateCode">
                </div>
                <div style="height: 5px;" class="logininfotext2_2"></div>
                <div style="display: block;">
                <input type="checkbox" checked="checked" name="readPerm"/> 我已阅读并接受网站的<a href="#">服务条款</a>和<a href="#">社区指南</a>
                </div>
                <div style="height: 5px;" class="logininfotext2_2"></div>
                <div style="display: block;" class="logininfotext">
                <input type="submit" value="提交注册" tabindex="20" id="btnRegister" class="buttonlogin"/>
                </div>
               </form>  
            </div>
          
        </div>



