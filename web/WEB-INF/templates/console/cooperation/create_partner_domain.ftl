<ul>
	<li><a href="${urls.getURL('/console/cooperation/partner_domains')}">合作域名列表</a></li>
	<li><a href="${urls.getURL('/console/cooperation/create_pdomain')}">创建合作域名</a></li>
</ul>

<form action="${urls.getURL('/console/cooperation/create_pdomain')}" method="post">
	<dl>
	<#if partnerDomain?exists && partnerDomain.domain?exists>
	<input type="hidden" name="id" value="${partnerDomain.domain}" />
	<dt><b>Domain</b></dt><dd>${partnerDomain.domain?html}</dd>
	<dt><b>PartnerName</b> (e.g. 星空相册)</dt><dd><input size="50" type="text" name="partnerDomain.partnerName" value="${partnerDomain.partnerName?html}" /></dd>
	<dt><b>UserDomainPattern</b> (e.g. http://${r"${username}"}.yupoo.com)</dt><dd><input size="50" type="text" name="partnerDomain.userDomainPattern" value="${partnerDomain.userDomainPattern?html}" /></dd>
	<dt><b>FlashDomain</b> (e.g. http://f.yupoo.com)</dt><dd><input size="50" type="text" name="partnerDomain.flashDomain" value="${partnerDomain.flashDomain?html}" /></dd>
	<dt><b>Host</b> (e.g. www.yupoo.com)</dt><dd><input size="50" type="text" name="partnerDomain.host" value="${partnerDomain.host?html}" /></dd>
	<dt><b>MediaRoot</b> (e.g. http://www.yupoo.com)</dt><dd><input size="50" type="text" name="partnerDomain.mediaRoot" value="${partnerDomain.mediaRoot?html}" /></dd>
	<dt><b>PhotoURLPattern</b> (e.g. http://photo.yupoo.com/${r"${dir}"}/${r"${filename}"}/${r"${suffix}"}/)</dt><dd><input size="50" type="text" name="partnerDomain.photoURLPattern" value="${partnerDomain.photoURLPattern?html}" /></dd>
	<dt><b>IconURLPattern</b> (e.g. http://ico.yupoo.com/${r"${user}"}/${r"${filename}"}/)</dt><dd><input size="50" type="text" name="partnerDomain.iconURLPattern" value="${partnerDomain.iconURLPattern?html}" /></dd>
	<#else>
	<dt><b>Domain</b> (e.g. yupoo.com)</dt><dd><input type="text" name="partnerDomain.domain"/></dd>
	<dt><b>PartnerName</b> (e.g. 星空相册)</dt><dd><input size="50" type="text" name="partnerDomain.partnerName" /></dd>
	<dt><b>UserDomainPattern</b> (e.g. http://${r"${username}"}.yupoo.com)</dt><dd><input size="50" type="text" name="partnerDomain.userDomainPattern"/></dd>
	<dt><b>FlashDomain</b> (e.g. http://f.yupoo.com)</dt><dd><input size="50" type="text" name="partnerDomain.flashDomain" /></dd>
	<dt><b>Host</b> (e.g. www.yupoo.com)</dt><dd><input size="50" type="text" name="partnerDomain.host" /></dd>
	<dt><b>MediaRoot</b> (e.g. http://www.yupoo.com)</dt><dd><input size="50" type="text" name="partnerDomain.mediaRoot" /></dd>
	<dt><b>PhotoURLPattern</b> (e.g. http://photo.yupoo.com/${r"${dir}"}/${r"${filename}"}/${r"${suffix}"}/)</dt><dd><input size="50" type="text" name="partnerDomain.photoURLPattern" /></dd>
	<dt><b>IconURLPattern</b> (e.g. http://ico.yupoo.com/${r"${user}"}/${r"${filename}"}/)</dt><dd><input size="50" type="text" name="partnerDomain.iconURLPattern" /></dd>
	</#if>
	<dt>&nbsp;</dt><dd><input type="submit" value="Submit" class="Btn"/></dd>
	</dl>
</form>