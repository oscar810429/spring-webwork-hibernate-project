<#include "/common/macros.ftl">
<head>
	<link rel="stylesheet" type="text/css" media="all" href="${media_root}/styles/date-picker.css"/>
	<script type="text/javascript" src="${media_root}/scripts/date-picker.js"></script>
<#assign edit = (news?exists && news.id?exists)>
<#if edit>
<@startPage title="Edit System News" heading="Edit System News" />
<#else>
<@startPage title="Create System News" heading="Create System News" />
</#if>
</head>
<content tag="submenu">
	<ul class="pagesnav">
		<li><a href="${urls.getURL('/console/system/news')}">News List</a></li>
		<li><a href="#" class="current">Create News</a></li>
	</ul>
</content>

<div class="nForm">
	<@ww.form id="news_form" name="news_form" action="news_save" namespace="/console/system" method="post">
		<#if edit>
		<@ww.hidden id="news_id" name="news.id" value="news.id" />
		</#if>
		<dl>
			<dt>Type:</dt>
			<dd><select name="news.kind" id="news.kind" onchange="javascript:jumpTo(this)">
			<#if news?has_content>
			<option value="${PN.NEWS_SYSTEM}" <#if news.kind=PN.NEWS_SYSTEM> selected</#if>>system news</option>
			<option value="${PN.NEWS_GROUPS}" <#if news.kind=PN.NEWS_GROUPS> selected</#if>>group news</option>
			<option value="${PN.NEWS_AD}" <#if news.kind=PN.NEWS_AD> selected</#if>>advertisement</option>
			<#else>
			<option value="${PN.NEWS_SYSTEM}">system news</option>
			<option value="${PN.NEWS_GROUPS}">group news</option>
			<option value="${PN.NEWS_AD}">advertisement</option>
			</#if>
			</select>
			</dd>
		</dl>
		<dl>
			<dt>Title:</dt>
			<dd><@ww.textarea id="news_title" name="news.title" value="news.title" cols="45" rows="4" /></dd>
		</dl>
		<dl>
			<dt>URL:</dt>
			<dd><@ww.textfield id="news_url" name="news.url" value="news.url" /></dd>
		</dl>
		<dl>
			<dt>Image:</dt>
			<dd><dd><select name="news.image" id="news.image">
			<#if news?has_content>
			<option value="${PN.NEWS_TEXT}" <#if news.image=PN.NEWS_TEXT> selected</#if>>text only</option>
			<option value="${PN.NEWS_IMAGE}" <#if news.image=PN.NEWS_IMAGE> selected</#if>>text & image</option>
			<#else>
			<option value="${PN.NEWS_TEXT}">text only</option>
			<option value="${PN.NEWS_IMAGE}">text & image</option>
			</#if>
			</select>
			</dd></dd>
		</dl>		
		<dl>
			<dt>Content:</dt>
			<dd><@ww.textarea id="news_content" name="news.content" value="news.content" cols="45" rows="10" /></dd>
		</dl>

		<dl>
			<dt>Expired Date:</dt>
			<dd><input id="news_expired_date" name="expiredDate" value="<#if news?exists && news.expiredDate?exists>${news.expiredDate?date}</#if>" /><img id="news_expired_date_link" src="http://www.PN.com/images/iconCalendar.gif" alt="Cal" /><div id="news_expired_date_calendar" class="date_picker" style="display:none"></div></dd>
		</dl>
		
  		<dl>
			<dt>&nbsp;</dt>
			<dd class="submit">
				<input id="submit" type="submit" value="Submit" class="Btn" />
			</dd>
		</dl>
	</@ww.form>
</div>
<script language="javascript">
//<![CDATA[
	function jumpTo(obj){
		if (obj.options[obj.selectedIndex].value==${PN.NEWS_AD}){
			<#if news?has_content>
				document.location.href="${urls.getURL('/console/system/edit_news_ad')}?id=${news.id}";
			<#else>
				document.location.href="${urls.getURL('/console/system/create_news_ad')}";
			</#if>
		}
	}
	window.addEvent('load', function() {
		if ($('news_expired_date_link')) {
			$('news_expired_date_link').addEvent('click', function() {
				DatePicker.toggleDatePicker('news_expired_date');
			});
		}
	});
//]]>
</script>