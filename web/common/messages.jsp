<%-- ActionError Messages - usually set in Actions --%>
<ww:if test="hasActionErrors()">
    <div class="Problem" id="errorMessages">	
      <ww:iterator value="actionErrors">
	    <ww:property escape="false"/><br />
      </ww:iterator>
   </div>
</ww:if>
<%-- ActionMessage - usually set in Actions --%>
<ww:if test="hasActionMessages()">
    <div class="Confirm" id="successMessages">
      <ww:iterator value="actionMessages">
	    <ww:property escape="false"/><br />
      </ww:iterator>
   </div>
</ww:if>
<%-- FieldError Messages - usually set by validation rules --%>
<ww:if test="hasFieldErrors()">
    <div class="Problem" id="errorMessages">	
      <ww:iterator value="fieldErrors">
         <ww:iterator value="value">
         <ww:property escape="false"/><br />
         </ww:iterator>
      </ww:iterator>
   </div>
</ww:if>
<%-- Success Messages --%>
<c:if test="${not empty messages}">
    <div class="Confirm" id="successMessages">	
        <c:forEach var="msg" items="${messages}">
            <c:out value="${msg}" escapeXml="false"/><br />
        </c:forEach>
    </div>
    <c:remove var="messages" scope="session"/>
</c:if>