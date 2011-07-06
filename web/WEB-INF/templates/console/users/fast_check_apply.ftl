<#include "/common/macros.ftl">
<@startPage title="User manangement" />
<head>
	<link rel="stylesheet" type="text/css" media="all" href="${media_root}/styles/calendar.css?v20060712.css"/>
	<script type="text/javascript" src="${media_root}/scripts/calendar.js?v20061017.js"></script>
	<script type="text/javascript" src="${media_root}/scripts/calendar-en.js?v20061017.js"></script>
	<script type="text/javascript" src="${media_root}/scripts/calendar-setup.js?v20061017.js"></script>
<#if result?exists>
<@script>
var ids = [<#if result?has_content><#list result.data as afc>'${afc.user.id}'<#if afc_has_next>, </#if></#list></#if>];

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
	<h1>User searching</h1>

	<form name="searchForm" action="${urls.getURL('/console/users/fast_apply')}" method="get">
		<dl>
			<dt>Start date:</dt>
			<dd>
				<input type="text" id="search_start_date" name="startDate" value="${startDate?default('')}" />
				<a href="${urls.getURL('/console/users/search')}" onclick="return false;"><img id="cal_trigger_start" src="${media_root}/images/iconCalendar.gif" alt="Cal" /></a>			
			</dd>
			<dt>End date:</dt>
			<dd>
				<input type="text" id="search_end_date" name="endDate" value="${endDate?default('')}" />
				<a href="${urls.getURL('/console/users/search')}" onclick="return false;"><img id="cal_trigger_end" src="${media_root}/images/iconCalendar.gif" alt="Cal" /></a>			
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
	<#if startDate?exists>
	,date		   :	"${startDate}"
	</#if>
});
Calendar.setup({
	inputField     :    "search_end_date",
	ifFormat       :    "%Y-%m-%d",
	button         :    "cal_trigger_end",
	showsTime	   :	"true"
	<#if endDate?exists>
	,date		   :	"${endDate}"
	</#if>
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

<#if person?exists && person !="">
<div class="UserRank">
	<form action="${urls.getURL('/console/users/save_rank')}">
	<input type="hidden" name="from" value="${urls.getURL('/console/users/search?' + query)?replace('$PAGE', page?string)}" />
	<input type="hidden" name="id" value="${person?default('')}" />
	<input type="radio" value="0" name="rank" id="rank_product" /><label for="rank_product">${action.getText('base.user.rank.product')}</label>
	<input type="radio" value="1" name="rank" id="rank_other" /><label for="rank_other">${action.getText('base.user.rank.other')}</label>
	<input type="radio" value="2" name="rank" id="rank_green" /><label for="rank_green">${action.getText('base.user.rank.green')}</label>
	<input type="radio" value="3" name="rank" id="rank_popular" /><label for="rank_popular">${action.getText('base.user.rank.popular')}</label>
	<input type="radio" value="4" name="rank" id="rank_commendatory" /><label for="commendatory">${action.getText('base.user.rank.commendatory')}</label>
	<input type="radio" value="5" name="rank" id="rank_senior" /><label for="rank_senior">${action.getText('base.user.rank.senior')}</label>
	<input type="submit" value="rank it" />
	</form>
</div>
<#--div class="UserType">
	<form action="${urls.getURL('/console/users/save_type')}">
	<input type="hidden" name="from" value="${urls.getURL('/console/users/search?' + query)?replace('$PAGE', page?string)}" />
	<input type="hidden" name="id" value="${person?default('')}" />
	<input type="radio" value="0" name="type" id="type_free" /><label for="type_free">free</label>
	<input type="radio" value="1" name="type" id="type_pay_pro" /><label for="type_pay_pro">pay_pro</label>
	<input type="radio" value="2" name="type" id="type_pay_for_traffic" /><label for="type_pay_for_traffic">pay_for_traffic</label>
	<input type="submit" value="type it" />
	</form>
</div-->
</#if>

</#if>

<#assign dates = DateUtils.getDatesBetweenLastdays(-30)> 
<div class="SearchResult">
	<form id="deleteForm" name="deleteForm" action="${urls.getURL('/console/users/save_ranks')}" method="post">
	<#if result?exists>
	<input type="hidden" name="from" value="${urls.getURL('/console/users/search?' + query)?replace('$PAGE', page?string)}" />
	<#else>
	<input type="hidden" name="from" value="${urls.getURL('/console/users/search')}" />
	</#if>
	<table>
		<tr>
			<td width="100%">
				<span id="hideform" style="display: none;">[ <a href="#" onclick="hideForm(); return false;">Hide Form</a> ]</span>
				<span id="showform">[ <a href="#" onclick="showForm(); return false;">Show Form</a> ]</span>
			</td>
			
		</tr>
	</table>
	<br />
	<#if result?exists && result?has_content>
	<#if !person?has_content && params['local']?exists>
	<ul>
		<#list result.data as afc>
		<li>
			<div class="lightbox lb[console] ListPhoto" id="thumb_${afc.id}">
				<div class="Icon">
					<a href="${urls.getURL('/console/photos/search?person='+afc.id+'&amp;pageSize=72')}" target="_blank"><img class="BuddyIcon" src="<@userIcon user=afc.user/>" alt="${afc.user.nickname?html}" width="48" height="48" /></a>
				</div>
				<input type="checkbox" id="check_${afc.user.id}" name="ids" value="${afc.id}" />
				<a href="${urls.getUserHomeURL(afc.user)}" target="_blank" onclick="return bindClickEvent(this, '${afc.id}', event);">=</a>
			</div>
		</li>
		</#list>
	</ul>
	<#else>
	<ul>
		<#list result.data as afc>
		<li>
			<div class="lightbox lb[console] ListPhoto" id="thumb_${afc.user.id}">
				<div class="Icon">
					<a href="${urls.getURL('/console/photos/search?person='+afc.user.id+'&amp;pageSize=72')}" target="_blank"><img class="BuddyIcon" src="<@userIcon user=afc.user/>" alt="${afc.user.nickname?html}" width="48" height="48" /></a>
					<div class="clear"></div><@yp.filter value="${afc.user.nickname}" length="5" find="false" />
				</div>
				<input type="checkbox" id="check_${afc.user.id}" name="ids" value="${afc.user.id}" />
				<a href="${urls.getUserHomeURL(afc.user)}" target="_blank" onclick="return bindClickEvent(this, '${afc.user.id}', event);" title="${afc.user.nickname?html}">=</a>
				<a href="${urls.getURL('/console/users/edit_role?person='+afc.user.id)}" target="_blank">R</a>
				L${afc.user.userRank.value()}
				<p>${afc.purpose?default('')?html}</p>
			</div>
		</li>
		</#list>
	</ul>
	</#if>
	</form>

	<div class="clear"></div>
	<#if (result.totalPage > 1)>
	<div class="pages">
		<@yp.pager result="result" url="${urls.getURL('/console/users/fast_apply?' + query)}" />
	</div>
	</#if>
	<#else>
		<#if userApply?exists>
	<ul>
		<li>
			<div class="lightbox lb[console] ListPhoto" id="thumb_${userApply.user.id}">
				<div class="Icon">
					<a href="${urls.getURL('/console/photos/search?person='+userApply.user.id+'&amp;pageSiz e=72')}" target="_blank"><img class="BuddyIcon" src="<@userIcon user=userApply.user/>" alt="${userApply.user.nickname?html}" width="48" height="48" /></a>
					<div class="clear"></div><@pn.filter value="${userApply.user.nickname}" length="5" find="false" />
				</div>
				<input type="checkbox" id="check_${userApply.user.id}" name="ids" value="${userApply.user.id}" />
				<a href="${urls.getUserHomeURL(userApply.user)}" target="_blank" onclick="return bindClickEvent(this, '${userApply.user.id}', event);" title="${userApply.user.nickname?html}">=</a>
				<a href="${urls.getURL('/console/users/edit_role?person='+user.id)}" target="_blank">R</a>
				L${userApply.user.userRank.value()}
				<p>${userApply.purpose?default('')?html}</p>
			</div>
		</li>
	</ul>
		</#if>
	</#if>
</div>
