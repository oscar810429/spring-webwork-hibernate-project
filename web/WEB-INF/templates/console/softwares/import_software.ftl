<#include "/common/macros.ftl">
<@startPage title="Import Software" heading="Create software by user: ${user.nickname?html}" />
<#include "/common/action_messages.ftl">
<div id="main">

	<div class="inner">
	
	<form id="softwares_form" name="softwares_form" action="${urls.getURL('/console/softwares/save_upload')}" method="post" enctype="multipart/form-data">
	         <input type="hidden" name="cid" value="${cid}">
			<dl id="upload_one_by_one">
			<dt><b>[Import Software]</b> - Browse Files
					<p id="upload_file_div">
					<input id="file" type="file" name="file" size="30" class="fileselect" />
					<input id="uploadbutton" type="submit" value="导入" class="Btn"/>
					&nbsp;&nbsp;<span class="red">注：文件类型 Excel(xls)</span>
					</p>
			</dt>
		</dl>
	
	</form>
	
	</div>
	
</div>	