<img src="<c:url value='/images/logo5.gif'/>" width="212" height="55" alt="<fmt:message key="header.logo.alt"/>" />
	<div id="topbar">
		<div id="menu">
			<a href="<c:url value='/'/>"><fmt:message key="header.link.home"/></a> |
			<a href="<c:url value='/photos/recent'/>"><fmt:message key="header.link.recent"/></a> |
			<a href="<c:url value='/tags/'/>"><fmt:message key="header.link.tags"/></a> |
			<a href="<c:url value='/groups/'/>"><fmt:message key="header.link.group"/></a> |
			<a href="<c:url value='/'/>"><fmt:message key="header.link.bookmark"/></a>
		</div>

		<% if (request.getRequestURL().indexOf("login") == -1) { %>
		<c:if test="${pageContext.request.remoteUser != null}">
			<fmt:message key="header.account.greeting"/>
			<c:out value="${sessionScope.currentUser.username}" /> [
			<a href="<c:url value='/account/'/>"><fmt:message key="header.link.account"/></a> |
			<a href="<c:url value='/logout.jsp'/>"><fmt:message key="header.link.logout"/></a> ]
		</c:if>
		<c:if test="${pageContext.request.remoteUser == null}">
			<a href="<c:url value='/account/login'/>"><fmt:message key="header.link.login"/></a> |
			<a href="<c:url value='/account/signup'/>"><fmt:message key="header.link.signup"/></a>
		</c:if>
		<% } %>
	</div>
	
	<c:if test="${pageContext.request.remoteUser != null}">
	<div id="submenu">
		<a href="<c:url value='/photos/upload'/>"><fmt:message key="header.link.upload"/></a> | 
		<a href="<c:url value='/photos/'/>"><fmt:message key="header.link.my.photos"/></a> | 
		<a href="<c:url value='/albums/'/>"><fmt:message key="header.link.albums"/></a> | 
		<a href="<c:url value='/photos/organize'/>"><fmt:message key="header.link.organize"/></a> | 
		<a href="<c:url value='/contacts/'/>"><fmt:message key="header.link.contacts"/></a>
	</div>
	</c:if>
	
	<div class="clear">&nbsp;</div>