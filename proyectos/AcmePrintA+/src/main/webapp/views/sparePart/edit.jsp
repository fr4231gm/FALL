<%@page language="java" contentType="degree/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<jstl:if test="${permission}">
<fmt:formatDate value="${sparePartForm.purchaseDate}" pattern="dd/MM/yyyy"
	var="parsedPurchaseDate" />

	<form:form action="sparePart/company/edit.do" modelAttribute="sparePartForm" id="form">
	
		<form:hidden path="id" />
		<form:hidden path="version" />
		<jstl:if test="${sparePartForm.id > 0}">
			<form:hidden path="inventory" />
		</jstl:if>
	
		<acme:input code="sp.name" path="name" placeholder="engine"/>

		<jstl:if test="${sparePartForm.id eq 0}">
			<acme:select items="${inventories}" itemLabel="ticker" code="sp.inventory" path="inventory"/>
		</jstl:if>
		
		<acme:input code="sp.description" path="description" placeholder="a short description"/>
		
		<acme:input code="sp.purchaseDate" path="purchaseDate" placeholder="11/11/2019 22:00" />
		
		<acme:input code="sp.purchasePrice" path="purchasePrice" placeholder="55.23"/>
		
		<acme:input code="sp.photo" path="photo" placeholder="https://www.google.es"/>

		<input type="submit" name="save" id="save" value="<spring:message code="sp.save" />" />&nbsp; 
			<jstl:if test="${sparePartForm.id != 0}">
				<input type="submit" name="delete" value="<spring:message code="sp.delete" />"
				onclick="return confirm('<spring:message code="sp.confirm.delete" />')" />&nbsp;
			</jstl:if>
		<jstl:if test="${inventory.id!=0}">
		<input type="button" name="cancel" value="<spring:message code="sp.cancel" />"
			onclick="javascript: relativeRedir('/inventory/company/list.do');" />
		<br />
		</jstl:if>
		
	</form:form>

</jstl:if>
<jstl:if test="${!permission}">
<h3><spring:message code="sp.nopermisiontobehere" /></h3>
</jstl:if>






