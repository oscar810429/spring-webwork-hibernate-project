<#include "/common/macros.ftl">
<@startPage title="User IP Record" />
<head>
	<link rel="stylesheet" type="text/css" media="all" href="${media_root}/styles/calendar.css?v20060712.css"/>
	<script type="text/javascript" src="${media_root}/scripts/calendar.js?v20061017.js"></script>
	<script type="text/javascript" src="${media_root}/scripts/calendar-en.js?v20061017.js"></script>
	<script type="text/javascript" src="${media_root}/scripts/calendar-setup.js?v20061017.js"></script>
</head>

<div id="search_form" class="nForm">
	<h1>User Login IP Record</h1>

	<form name="searchForm" action="${urls.getURL('/console/users/ip_record')}" method="get">
		<dl>
			<dt>Start date:</dt>
			<dd>
				<input type="text" id="search_start_date" name="startDate" value="${date?default('')}" />
				<a href="${urls.getURL('/console/users/ip_record')}" onclick="return false;"><img id="cal_trigger_start" src="${media_root}/images/iconCalendar.gif" alt="Cal" /></a>			
			</dd>
			<dt>End date:</dt>
			<dd>
				<input type="text" id="search_end_date" name="endDate" value="${date?default('')}" />
				<a href="${urls.getURL('/console/users/ip_record')}" onclick="return false;"><img id="cal_trigger_end" src="${media_root}/images/iconCalendar.gif" alt="Cal" /></a>			
			</dd>
			<dt>ID/Name:</dt>
			<dd>
				<input type="text" name="id" value="${person?default('')}" size="38" />
			</dd>
			<dt>Page:</dt>
			<dd>
				<input type="text" name="page" value="${page?c}" size="5" />
			</dd>
			<dt>Page Size:</dt>
			<dd>
				<input type="text" name="pageSize" value="${pageSize?c}" size="5" /> photos/page
			</dd>
		</dl>

  		<dl>
			<dt>&nbsp;</dt>
			<dd class="submit">
				<input id="submit" type="submit" value="SEARCH" class="Btn" />
				<#if result?exists>
				<input id="cancel" type="button" value="CANCEL" class="CancelBtn" onclick="hideForm();return false;" />
				</#if>
			</dd>
		</dl>
	</form>
</div>

<@script>
Calendar.setup({
	inputField     :    "search_start_date",
	ifFormat       :    "%Y-%m-%d",
	button         :    "cal_trigger_start",
	showsTime	   :	"true"
});
Calendar.setup({
	inputField     :    "search_end_date",
	ifFormat       :    "%Y-%m-%d",
	button         :    "cal_trigger_end",
	showsTime	   :	"true"
});

var formSlider = new Fx.Slide('search_form', {duration: 500});
<#if result?exists>
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

<#if result?exists>
<#assign query='page=$PAGE'>
<#if startDate?has_content>
	<#assign query=query+'&amp;startDate='+startDate>
</#if>
<#if endDate?has_content>
	<#assign query=query+'&amp;endDate='+endDate>
</#if>
<#if person?has_content>
	<#assign query=query+'&amp;person='+person>
</#if>
<#if pageSize?exists>
	<#assign query=query+'&amp;pageSize='+pageSize?c>
</#if>
<#if params['local']?exists>
	<#assign query=query+'&amp;local=1'>
</#if>



</#if>
<div class="SearchResult">
	<div>
		<span id="hideform" style="display: none;">[ <a href="#" onclick="hideForm(); return false;">Hide Form</a> ]</span>
		<span id="showform">[ <a href="#" onclick="showForm(); return false;">Show Form</a> ]</span>
	</div>
	<br />
	<#if result?exists && result?has_content>
		<table id="IPRecordTable">
		<tr>
			<th>Username</th><th>IP Address</th><th>Login Date</th>
		</tr>
		<#list result.data as userLoginIPs>
		<tr>
			<td><div title="${userLoginIPs.user.nickname?html}">${userLoginIPs.user.nickname?html}</div></td>
			<td>${userLoginIPs.loginIP}</td>
			<td>${userLoginIPs.loginDate}</td>
		</tr>
		</#list>
		</table>
	</#if>

	<div class="clear"></div>
	<#if result?exists && (result.totalPage > 1)>
	<div class="pages">
		<@yp.pager result="result" url="${urls.getURL('/console/users/ip_record?' + query)}" />
	</div>
	</#if>
</div>
