<%@page language="java" contentType="degree/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>

<jstl:if test="${permission}">

	<form:form action="spool/company/edit.do" modelAttribute="spoolForm" id="form">
	
		<form:hidden path="id" />
		<form:hidden path="version" />
		<jstl:if test="${spoolForm.id > 0}">
			<form:hidden path="inventory" />
		</jstl:if>
	
		<jstl:if test="${spoolForm.id eq 0}">
			<acme:select items="${inventories}" itemLabel="ticker" code="spool.inventory" path="inventory"/>
		</jstl:if>
		
		<form:label path="materialName">
     		 <spring:message code="order.material" />
   	 	</form:label>
   	 	
		<form:select path="materialName">
	    	<jstl:forEach items="${materials}" var="material">
	    		<option value="${material}">${material}</option>
	    	</jstl:forEach>
	    </form:select>
		
		<acme:input code="spool.diameter" path="diameter" placeholder="5.30"/>
		
		<acme:input code="spool.length" path="length" placeholder="5.25" />
		
		<acme:input code="spool.remainingLength" path="remainingLength" placeholder="2.25" />
		
		<acme:input code="spool.weight" path="weight" placeholder="3.56"/>
		
		<acme:input code="spool.color" path="color" placeholder="red"/>
		
		<acme:input code="spool.meltingTemperature" path="meltingTemperature" placeholder="155"/>

		<input type="submit" name="save" id="save" value="<spring:message code="spool.save" />" />&nbsp; 
		
		<jstl:if test="${spoolForm.id != 0 and fn:length(printerForm.inventory.spools) > 1}}">
			<input type="submit" name="delete" value="<spring:message code="spool.delete" />" onclick="return confirm('<spring:message code="spool.confirm.delete" />')" />&nbsp;
		</jstl:if>
			
		<input type="button" name="cancel" value="<spring:message code="spool.cancel" />" onclick="javascript: relativeRedir('/inventory/company/list.do');" />

		
	</form:form>

</jstl:if>

<jstl:if test="${!permission}">
	<h3><spring:message code="spool.nopermisiontobehere" /></h3>
</jstl:if>






