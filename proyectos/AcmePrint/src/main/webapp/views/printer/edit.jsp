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

	<form:form action="printer/company/edit.do" modelAttribute="printerForm" id="form">
	
		<form:hidden path="id" />
		<form:hidden path="version" />
		<jstl:if test="${printerForm.id > 0}">
			<form:hidden path="inventory" />
		</jstl:if>
	
		<jstl:if test="${printerForm.id eq 0}">
			<acme:select items="${inventories}" itemLabel="ticker" code="printer.inventory" path="inventory"/>
		</jstl:if>
		
		<acme:input code="printer.make" path="make" placeholder="Ubisoft"/>
		
		<acme:input code="printer.model" path="model" placeholder="CP - 330" />
		
		<acme:input code="printer.description" path="description" placeholder="printer description" />
		
		<acme:input code="printer.datePurchase" path="datePurchase" placeholder="05/05/2018 05:10"/>
		
		<acme:input code="printer.warrantyDate" path="warrantyDate" placeholder="5/05/2020 11:10"/>
		
		<acme:input code="printer.shoppingWebsite" path="shoppingWebsite" placeholder="https://www.google.es"/>
		
		<acme:input code="printer.dimensionX" path="dimensionX" placeholder="101"/>
		
		<acme:input code="printer.dimensionY" path="dimensionY" placeholder="75" />
		
		<acme:input code="printer.dimensionZ" path="dimensionZ" placeholder="115" />
		
		<acme:input code="printer.extruderDiameter" path="extruderDiameter" placeholder="1.55"/>
		
		<acme:input code="printer.nozzle" path="nozzle" placeholder="1.55"/>
		
		<acme:input code="printer.materials" path="materials" placeholder="PETG, ABS..."/>
		
		<acme:textarea code="printer.photo" path="photo" placeholder="https://www.google.es"/>
		
		<acme:checkbox code="printer.isActive" path="isActive"/>

		<input type="submit" name="save" id="save" value="<spring:message code="printer.save" />" />&nbsp; 
		
		<jstl:if test="${printerForm.id != 0 and fn:length(printerForm.inventory.printers) > 1}">
			<input type="submit" name="delete" value="<spring:message code="printer.delete" />" onclick="return confirm('<spring:message code="printer.confirm.delete" />')" />&nbsp;
		</jstl:if>
			
		<input type="button" name="cancel" value="<spring:message code="printer.cancel" />" onclick="javascript: relativeRedir('/inventory/company/list.do');" />

		
	</form:form>

</jstl:if>

<jstl:if test="${!permission}">
	<h3><spring:message code="printer.nopermisiontobehere" /></h3>
</jstl:if>






