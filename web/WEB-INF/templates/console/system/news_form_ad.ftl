<#include "/common/macros.ftl">
<#assign edit = (news?exists && news.id?exists)>
<#if edit>
<@startPage title="Edit System News" 
		heading="Edit System News" />
<#else>
<@startPage title="Create System News" 
		heading="Create System News" />
</#if>

<content tag="sidebar">
	<div class="pagesnav">
		<a href="${urls.getURL('/console/system/news')}"><span>List News</span></a>
		<#if edit><a class="current" href="${urls.getURL('/console/system/edit_news')}"><span>Edit News</span></a></#if>
		<a<#if !edit> class="current"</#if> href="${urls.getURL('/console/system/create_news')}"><span>Create News</span></a>
		<a href="${urls.getURL('/console/system/messages')}"><span>List Messages</span></a>
		<a href="${urls.getURL('/console/system/create_message')}"><span>Create Message</span></a>		
	</div>
</content>

<div class="nForm">
	<@ww.form id="news_form" name="news_form" action="save_news" namespace="/console/system" method="post">
		<#if edit>
		<@ww.hidden id="news_id" name="news.id" value="news.id" />
		</#if>
		<dl>
			<dt>Type:</dt>
			<dd><select name="news.kind" id="news.kind" onchange="javascript:jumpTo(this)">
			<option value="${PN.NEWS_SYSTEM}">system news</option>
			<option value="${PN.NEWS_GROUPS}">group news</option>
			<option value="${PN.NEWS_AD}" selected >advertisement</option>
			</select>
			</dd>
		</dl>
		<dl>
			<dt>advertise javascript: </dt>
			<dd>example:/scripts/ads/groups.js?v20061205.js </dd>
			<dd><@ww.textarea id="news_title" name="news.title" value="news.title" cols="45" rows="4" /></dd>
		</dl>
		<dl>
			<dt>Category:</dt>
			<dd><select name="news.url">
				<option value="${PN.PAGE_KIND}">index</option>
				<#if news?has_content>
					<#if categories?has_content>
						<#list categories as tmpCategory>
							<option value="${tmpCategory.id}" <#if news.url=tmpCategory.id> selected</#if> >${tmpCategory.name}</option>
							<#if tmpCategory.categories?has_content>
								<#list tmpCategory.categories as subCategory>
									<option value="${subCategory.id}" <#if news.url = subCategory.id> selected</#if> >|--${subCategory.name}</option>
								</#list>
							</#if>
						</#list>
					</#if>
				<#else>
					<#if categories?has_content>
					<#list categories as tmpCategory>
						<option value="${tmpCategory.id}" >${tmpCategory.name}</option>
							<#if tmpCategory.categories?has_content>
								<#list tmpCategory.categories as subCategory>
									<option value="${subCategory.id}" >|--${subCategory.name}</option>
								</#list>
							</#if>
					</#list>
					</#if>
				</#if>
				</select></dd>
				<#--
			<@ww.textfield id="news_url" name="news.url" value="news.url" />
				-->
		</dl>
		<dl>
			<dt>Image:</dt>
			<dd><input type="hidden" name="news.image" value="${PN.NEWS_TEXT}">
			text only
			</dd>
			<#--
			<select name="news.image" id="news.image">
			<#if news?has_content>
			<option value="${PN.NEWS_TEXT}" <#if news.image=PN.NEWS_TEXT> selected</#if>>text only</option>
			<option value="${PN.NEWS_IMAGE}" <#if news.image=PN.NEWS_IMAGE> selected</#if>>text & image</option>
			<#else>
			<option value="${PN.NEWS_TEXT}">text only</option>
			<option value="${PN.NEWS_IMAGE}">text & image</option>
			</#if>
			</select>
			-->
			
		</dl>		
		<dl>
			<dt>Content:</dt>
			<dd><@ww.textarea id="news_content" name="news.content" value="news.content" cols="45" rows="10" /></dd>
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
		if (obj.options[obj.selectedIndex].value!=${PN.NEWS_AD}){
			<#if news?has_content>
				document.location.href="${urls.getURL('/console/system/edit_news')}?id=${news.id}";
			<#else>
				document.location.href="${urls.getURL('/console/system/create_news')}";
			</#if>
		}
	}
//]]>
</script>
