<#include "/common/macros.ftl">
<#assign edit = (yupooman?exists && yupooman.id?exists)>
<#if edit>
<@startPage title="编辑达人" heading="" />
<#else>
<@startPage title="创建达人" heading="" />
</#if>

<content tag="submenu">
	<ul class="pagesnav">
	<li><a href="${urls.getURL('/console/users/daren_list')}">达人列表</a></li>
	<#if edit>
	<li><a href="#" class="current">编辑达人</a></li>
	<li><a href="${urls.getURL('/console/users/daren')}">创建达人</a></li>
	<#else>
	<li><a href="#" class="current">创建达人</a></li>
	</#if>
	</ul>
</content>

<div class="nForm">
	<@ww.form id="yupooman_form" name="yupooman_form" action="daren_save" namespace="/console/users" method="post" onsubmit="return formCheck();">
		<#if edit>
		<@ww.hidden id="yupooman_id" name="yupooman.id" value="yupooman.id" />
		</#if>
		<dl>
			<dt>用户名:</dt>
			<dd><@ww.textfield id="username" name="username" value="username" size="50" />(#)</dd>
		</dl>
		
		<dl>
			<dt>相册IDs:</dt>
		    <dd><@ww.textfield id="album_id1" name="albumId1" value="albumId1" size="50" /></dd>
		    <dd><@ww.textfield id="album_id2" name="albumId2" value="albumId2" size="50" /></dd>
		    <dd><@ww.textfield id="album_id3" name="albumId3" value="albumId3" size="50" /></dd>
		    <dd><@ww.textfield id="album_id4" name="albumId4" value="albumId4" size="50" /></dd>
		</dl>
		
		<dl>
			<dt>状态:</dt>
			<#if yupooman?has_content && yupooman.state?has_content && yupooman.state == 0>
			<dd><input type="radio" name="yupooman.state" value="1" id="is_used" ><label for="is_used">现在被指定</input>
			<input type="radio" name="yupooman.state" value="0" id="is_not_used"  checked="checked" ><label for="is_not_used">暂时不被指定</input></dd>
			<#else>
			<dd><input type="radio" name="yupooman.state" value="1" id="is_used" checked="checked" ><label for="is_used">现在被指定</input>
			<input type="radio" name="yupooman.state" value="0" id="is_not_used"  ><label for="is_not_used">暂时不被指定</input></dd>
			</#if>
		</dl>	
		
		<dl>
			<dt>文件目录名:</dt>
			<dd><@ww.textfield id="yupooman_url" name="yupooman.url" value="yupooman.url" size="50" />(#)</dd>
			<dd>(该达人所使用页面的文件目录名，例如：2007/zola)</dd>
		</dl>
		
		<dl>
			<dt>描述:</dt>
		    <dd><@ww.textarea id="yupooman_description" name="yupooman.description" value="yupooman.description" cols="65" rows="8" /></dd>
		</dl>
	
  		<dl>
			<dt>&nbsp;</dt>
			<dd class="submit">
				<input id="submit" type="submit" value="保存" class="Btn" />
			</dd>
		</dl>
	</@ww.form>
</div>

<@script>
 	function formCheck() {
 	if ( $('username').value=="" ) {
 		alert("用户名不能为空!");
 		return false;
 	}else if ($('yupooman_url').value=="") {
 		alert("文件目录名不能为空!");
 		return false;
 	}
 	return true;
 	}
</@script>