<#include "/common/macros.ftl">
<@startPage title="留言" heading="留言" />

<@ww.form id="search_testimonial_form" name="search_testimonial_form" action="/console/users/testimonial" method="post" >
	<label>搜索用户发表的个人留言:</label>
	<label>用户名：</label><@ww.textfield id="username" name="username" value="username" size="20" />
	<input id="submit" type="submit" value="搜索" class="Btn" />
</@ww.form>
<#if testimonialResult?has_content>
<label>${username?html}发表的留言：</label>
<span id="deselectall" style="display: none;">[ <a href="#" onclick="batch_set_all(false); return false;">全不选</a> ]</span>
<span id="selectall">[ <a href="#" onclick="batch_set_all(true); return false;">全选</a> ]</span>
<div class="listdiv">
	<form name="batchDeleteForm" action="${urls.getURL('/console/users/batch_delete_testimonial')}" method="post">
	<input type="hidden" name="username" value="${username}" />
	<table width="100%" border="0" cellspacing="0" cellpadding="0" class="listable">
		<thead>
		<tr>
			<th></th>
			<th>发表给谁</th>
			<th>留言内容</th>
			<th>发表时间</th>
			<th>操作</th>
		</tr>
		</thead>
		<tbody>
			<#list testimonialResult.data as testimonial>
			
			<tr<#if testimonial_index%2=1> class="odd"</#if>>
				<td><input type="checkbox" id="check_${testimonial.id}" name="ids" value="${testimonial.id}" ></input></td>
				<td><a href="${urls.getUserHomeURL(testimonial.user)}">${testimonial.user.nickname?html}</a></td>
				<td><div class="content" onmouseover="this.style.overflow = 'visible'" onmouseout="this.style.overflow = 'hidden' " style="height:40px;overflow:hidden"><@pn.filter value="${testimonial.content}" find="false" clear="false"/></div></td>
				<td>${testimonial.modifiedDate?date}</td>
				<td>
					<a href="${urls.getURL('/console/users/delete_testimonial')}?id=${testimonial.id}&amp;username=${username?html}" onclick="return confirm('确认删除?')">删除</a>
				</td>
			</tr>
			</#list>
		</tbody>
	</table>
	<input id="submit" type="submit" value="删除所选项" class="Btn" />
	</form>
	<#if (testimonialResult.totalPage > 1)>
		<div class="pages">
			<@yp.pager result="testimonialResult" url="${urls.getURL('/console/users/testimonial') + '?page=$PAGE&amp;username=' + username?html}" />
		</div>
	</#if>
</div>
</#if>
<@script>
<#if testimonialResult?has_content>
	var ids = [<#list testimonialResult.data as testimonial>'${testimonial.id}'<#if testimonial_has_next>, </#if></#list>];
	function batch_set_all(value){
	ids.each(function(item) { $('check_'+item).checked = value; });
	$('deselectall').style.display = value ? 'block' : 'none';
	$('selectall').style.display = value ? 'none' : 'block';
	}
</#if>
</@script>
