<#include "/common/macros.ftl">
<@startPage title="Tag List" heading="Tag List" />
<div>
<@ww.form id="tag_form" name="tag_form" action="/console/tags/tags" method="post" >
	<label>类型选择:</label>
	<select name="kind" id="kind">
		<option value="${PN.HOME_TAG}" <#if kind?has_content && kind== PN.HOME_TAG>selected </#if>>未登录时首页标签</option>
		<option value="${PN.USER_HOME_TAG}" <#if kind?has_content && kind== PN.USER_HOME_TAG>selected </#if>>PN首页标签</option>
		<option value="${PN.EXPLORE_TAG}" <#if kind?has_content && kind== PN.EXPLORE_TAG>selected </#if>>逛逛处标签</option>
		<option value="${PN.CURRENT_DAY_TAG}" <#if kind?has_content && kind== PN.CURRENT_DAY_TAG>selected </#if>>24小时内标签</option>
		<option value="${PN.HOT_TAG}" <#if kind?has_content && kind== PN.HOT_TAG>selected </#if>>热门标签</option>
	</select>
	<input id="submit" type="submit" value="提交" class="Btn" />
</@ww.form>
<#if tags?has_content && tags.data.size() gt 0 && kind?has_content>
	<div class="listdiv">
	<span id="deselectall" style="display: none;">[ <a href="#" onclick="batch_set_all(false); return false;">全不选</a> ]</span>
	<span id="selectall">[ <a href="#" onclick="batch_set_all(true); return false;">全选</a> ]</span>
	<form name="saveForm" action="${urls.getURL('/console/tags/tags_save')}" method="post">
	<input type="hidden" name="kind" value="${kind}" />
	<input type="hidden" name="page" value="${page}" />
	<input type="hidden" name="pagesize" value="${pagesize}" />
	<table width="100%" border="0" cellspacing="0" cellpadding="0" class="listable">
		<tr>	
			<th> </th>
			<th>标签名</th>
			<th>权值</th>
			<th>排序号</th>
			<th>打开模式</th>
			<th>操作</th>
		</tr>
		<#list tags.data as tagItem>
		<tr<#if tagItem_index%2=1> class="odd"</#if>>
			<input type="hidden" name="tagId_${tagItem.id}" value="${tagItem.id}" />
			<td><input type="checkbox" id="check_${tagItem.id}" name="ids" value="${tagItem.id}" ></input></td>
			<td><label style="font-size:${12+tagItem.weight}px;<#if (tagItem.weight>5)>font-weight:bold;</#if>">${tagItem.title}</label></td>
			<td><input type="text" onkeyup="changeFont(this)" name="weight_${tagItem.id}" value="${tagItem.weight}" size="3"/></td>
			<td><input type="text" name="order_${tagItem.id}" value="${tagItem.order}" size="5"/></td>
			<td>
				<select name="newWindow_${tagItem.id}">
					<option value="0" <#if tagItem.urlBlank?has_content && tagItem.urlBlank == 0>selected</#if>>否</option>
					<option value="1" <#if tagItem.urlBlank?has_content && tagItem.urlBlank == 1>selected</#if>>是</option>
				</select>
			</td>
			<td>
				<a href="${urls.getURL('/console/tags/tag_edit')}?id=${tagItem.id}">编辑</a>
				<a href="${urls.getURL('/console/tags/tag_delete')}?id=${tagItem.id}" onclick="return confirm('确认删除标签 ${tagItem.title}?')">删除</a>
			</td>
		</tr>
		</#list>		
	</table>
	<input type="submit" name="Save" value="保存所有编辑" class="Btn" />
	<input type="botton" name="Clear" onclick="this.form.action='${urls.getURL('/console/tags/tags_clear')}'; this.form.submit()" value="删除所选项" class="Btn" />
	</form>
	</div>
	<div class="clear"></div>
	<#if (tags.totalPage > 1)>
	<div class="tags">
		<@yp.pager result="tags" url="${urls.getURL('/console/tags/tags')}?kind=${kind}&amp;pagesize=${pagesize}&amp;page=$PAGE" />
	</div>
	</#if>
	
</#if>
</div>
<@script>
	function changeFont(el){
		var font = el.getParent().getParent().getElementsByTagName('label')[0];
		if(el.value > 5){
			if(el.value>20){
				el.value = 20;
			}
			font.setStyles({
				"font-size": 12 + el.value.toInt() + 'px',
				"font-weight": "bold"
			});
		}else{
			font.setStyles({
				"font-size": 12 + el.value.toInt() + 'px',
				"font-weight": "normal"
			});
		}
	}
	<#if tags?has_content>
	var ids = [<#list tags.data as tagItem>'${tagItem.id}'<#if tagItem_has_next>, </#if></#list>];
	function batch_set_all(value){
	ids.each(function(item) { $('check_'+item).checked = value; });
	$('deselectall').style.display = value ? 'block' : 'none';
	$('selectall').style.display = value ? 'none' : 'block';
	}
	</#if>
</@script>