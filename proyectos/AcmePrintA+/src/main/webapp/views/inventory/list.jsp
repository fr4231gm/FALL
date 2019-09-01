
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>


<display:table name="inventories" id="row" requestURI="${requestURI}"
	pagesize="5" class="displaytag">

	<display:column property="ticker"
		titleKey="inventory.ticker" sortable="true" />

				
	<display:column>
		<acme:link link="inventory/company/display.do?inventoryId=${row.id}" code = "inventory.display"/>
	</display:column>


</display:table>

	
	<a href="inventory/company/create.do"> <spring:message code="inventory.create"/></a>
<acme:back code="company.go.back" />

