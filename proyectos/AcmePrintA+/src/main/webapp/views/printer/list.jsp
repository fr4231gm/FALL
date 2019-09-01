<%@page language="java" contentType="degree/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

	<display:table name="printers" id="row" class="displayTag">
		<display:column property="ticker" titleKey="inventory.printer.ticker"
			sortable="true" />
		<display:column property="make" titleKey="inventory.printer.make"
			sortable="true" />
		<display:column property="model" titleKey="inventory.printer.model"
			sortable="true" />
		<display:column titleKey="inventory.printer.shoppingWebsite"
			sortable="true">
			<a href="${row.shoppingWebsite}">Click</a>
		</display:column>
		<display:column property="materials"
			titleKey="inventory.printer.materials" sortable="true" />
		<display:column titleKey="inventory.printer.isActive" sortable="true">
			<jstl:if test="${row.isActive == 'true'}">
				<spring:message code="inventory.printer.active.yes"></spring:message>
			</jstl:if>
			<jstl:if test="${row.isActive == 'false'}">
				<spring:message code="inventory.printer.active.yes"></spring:message>
			</jstl:if>
		</display:column>
	
		<display:column>
			<a href="printer/display.do?printerId=${row.id}"><spring:message
					code="printer.display" /></a>
		</display:column>
	
	</display:table>
	
	<acme:back code="go.back"/>






