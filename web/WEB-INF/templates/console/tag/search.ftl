<#include "/common/macros.ftl">
<@startPage title="Search Tags" heading="Search Tags"/>
<head>
	<link rel="stylesheet" type="text/css" media="all" href="${media_root}/styles/calendar.css?v20060712.css"/>
	<script type="text/javascript" src="${media_root}/scripts/calendar.js?v20061017.js"></script>
	<script type="text/javascript" src="${media_root}/scripts/calendar-en.js?v20061017.js"></script>
	<script type="text/javascript" src="${media_root}/scripts/calendar-setup.js?v20061017.js"></script>
<#if popularTags?exists>
<@script>
var ids = [<#list popularTags as tagItem>'${tagItem.name}'<#if tagItem_has_next>, </#if></#list>];

function toggle_batch_check(name){
	var el = $(name);
	el.checked = !el.checked;
}

function batch_set_all(value){
	ids.each(function(item) { $('check_'+item).checked = value; });
	$('deselectall').style.display = value ? 'block' : 'none';
	$('selectall').style.display = value ? 'none' : 'block';
}

function squareClicked(e) {
	if (e.shift) {
		var el = $('check_' + this.imgId);
		el.checked = !el.checked;
		e.preventDefault();
		return false;
	}
	return true;
}

function bindClickEvent(ln, id, event) {
	if (!ln.imgId) {
		ln.onclick = squareClicked.bindWithEvent(ln);
		ln.imgId = id;
	}
	return ln.onclick(event || window.event);
}
</@script>
</#if>
</head>

<div id="search_form" class="nForm">
	<form name="searchForm" action="${urls.getURL('/console/tags/search')}" method="post">
		<dl>
			<dt>开始时间:</dt>
			<dd>
				<input type="text" id="search_start_date" name="startDate" value="${startDate?default('')}" />
				<a href="${urls.getURL('/console/tags/search')}" onclick="return false;"><img id="cal_trigger_start" src="${media_root}/images/iconCalendar.gif" alt="Cal" /></a>			
			</dd>
			<dt>截至时间:</dt>
			<dd>
				<input type="text" id="search_end_date" name="endDate" value="${endDate?default('')}" />
				<a href="${urls.getURL('/console/tags/search')}" onclick="return false;"><img id="cal_trigger_end" src="${media_root}/images/iconCalendar.gif" alt="Cal" /></a>			
			</dd>
			<dt>Tag数目:</dt>
			<dd>
				<input type="text" name="size" value="${size?c}" size="8" /> 
			</dd>
			<dt>被使用的次数:</dt>
			<dd>
				<input type="text" name="usedTimes" value="${usedTimes?c}" size="8" /> 
			</dd>
			<dt>&nbsp;</dt>
			<dd class="submit">
				<input id="submit" type="submit" value="SEARCH" class="Btn" />
				<#if popularTags?exists>
				<input id="cancel" type="button" value="CANCEL" class="CancelBtn" onclick="hideForm();return false;" />
				</#if>
			</dd>
		</dl>
	</form>
	<div class="clear"></div>
</div>

<@script>
Calendar.setup({
	inputField     :    "search_start_date",
	ifFormat       :    "%Y-%m-%d %H:%M",
	button         :    "cal_trigger_start",
	showsTime	   :	"true"
});
Calendar.setup({
	inputField     :    "search_end_date",
	ifFormat       :    "%Y-%m-%d %H:%M",
	button         :    "cal_trigger_end",
	showsTime	   :	"true"
});

var formSlider = new Fx.Slide('search_form', {duration: 500});
<#if popularTags?exists>
formSlider.hide();
</#if>

function hideForm() {
	formSlider.slideOut();
	$('showform').show();
	$('hideform').hide();
}
function showForm() {
	formSlider.slideIn();
	$('showform').hide();
	$('hideform').show();
}
</@script>

<#if popularTags?exists>
<#assign query=''>
<#if startDate?has_content>
	<#assign query=query+'&amp;startDate='+startDate>
</#if>
<#if endDate?has_content>
	<#assign query=query+'&amp;endDate='+endDate>
</#if>

<div class="tagSearchResult">
	<form id="addForm" name="addForm" action="${urls.getURL('/console/tags/add')}" method="post">
	<input type="hidden" name="from" value="${urls.getURL('/console/tags/search?' + query)}" />
	<table width="100%">
		<tr>
			<td width="55%">
				<span id="deselectall" style="display: none;">[ <a href="#" onclick="batch_set_all(false); return false;">Deselect All</a> ]</span>
				<span id="selectall">[ <a href="#" onclick="batch_set_all(true); return false;">Select All</a> ]</span>

				<span id="hideform" style="display: none;">[ <a href="#" onclick="hideForm(); return false;">Hide Form</a> ]</span>
				<span id="showform">[ <a href="#" onclick="showForm(); return false;">Show Form</a> ]</span>
			</td>
			<td>
				<label>将选择的标签添加到:</label>
				<select name="tokind" id="tokind">
				<option value="${PN.HOME_TAG}" >未登录时首页标签</option>
				<option value="${PN.USER_HOME_TAG}">PN首页标签</option>
				<option value="${PN.EXPLORE_TAG}" >逛逛处标签</option>
				<option value="${PN.CURRENT_DAY_TAG}">24小时内标签</option>
				<option value="${PN.HOT_TAG}" >热门标签</option>
				</select>
				<input type="submit" name="add" value="添加 " class="Btn" />
			</td>
		</tr>
	</table>
	<br />

	<ul>
		<#list popularTags as tagItem>
		<#if tagItem.name?has_content>
		<li>
			<#assign level = tagItem.getLevel(minTimes,levelTimes)+1>
			<input type="checkbox" id="check_${tagItem.name}" name="ids" value="${level+'/'+tagItem.name}" ><label for="check_${tagItem.name}" <#if (level>5)>style="font-weight:bold;"</#if>>${tagItem.name}</label></input><a href="${urls.getURL('/photos/tags/')}?tag=${tagItem.name?url}" target="_blank">@</a>
		</li>
		</#if>
		</#list>
	</ul>
	</form>
</div>
<div class="clear"></div>
</#if>