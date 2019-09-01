<%@page language="java" contentType="degree/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

	<form:form action="printer/company/display.do" modelAttribute="printer" id="form" >
	
		<form:hidden path="id" />
		<form:hidden path="version" />
		<jstl:if test="${printerForm.id > 0}">
			<form:hidden path="inventory" />
		</jstl:if>
		
		<h2><jstl:out value="${printer.ticker}" /></h2>
		
		<acme:input code="printer.make" path="make" readonly="true" placeholder="Ubisoft"/>
		
		<acme:input code="printer.model" path="model" readonly="true" placeholder="CP - 330" />
		
		<acme:input code="printer.description" path="description" readonly="true" placeholder="printer description" />
		
		<acme:input code="printer.datePurchase" path="datePurchase"  readonly="true" placeholder="05/05/2018"/>
		
		<acme:input code="printer.warrantyDate" path="warrantyDate" readonly="true" placeholder="5/05/2020"/>
		
		<acme:input code="printer.shoppingWebsite" path="shoppingWebsite" readonly="true" placeholder="https://www.google.es"/>
		
		<acme:input code="printer.dimensionX" path="dimensionX" readonly="true" placeholder="101"/>
		
		<acme:input code="printer.dimensionY" path="dimensionY" readonly="true" placeholder="75" />
		
		<acme:input code="printer.dimensionZ" path="dimensionZ" readonly="true" placeholder="115" />
		
		<acme:input code="printer.extruderDiameter" path="extruderDiameter" readonly="true" placeholder="1.55"/>
		
		<acme:input code="printer.nozzle" path="nozzle" readonly="true" placeholder="1.55"/>
		
		<acme:input code="printer.materials" path="materials" readonly="true" placeholder="PETG, ABS..."/>
		
		<acme:image src="${printer.photo}"/>
		<br>
	 
	 <h3>	<spring:message code="printer.isActive"/>: </h3>
		<jstl:if test="${printer.isActive == 'true'}">
				<spring:message code="inventory.printer.active.yes"></spring:message>
			</jstl:if>
			<jstl:if test="${printer.isActive == 'false'}">
				<spring:message code="inventory.printer.active.yes"></spring:message>
			</jstl:if>
		<br/>
		<security:authorize access="hasAnyRole('COMPANY, CUSTOMER')">
		<jstl:choose>
			<jstl:when test="${havePrintSpooler}">
			
				<h3><spring:message code="request.notPrinted" /></h3>
				
				<display:table name = "notPrinted" id="row" pagesize="5" class="displaytag" requestURI="${requestURI}">
				
					<display:column property="number" titleKey="request.position" sortable="true" />
					
					<display:column property="extruderTemp" titleKey="request.extruderTemp"/>
					
					<display:column property="hotbedTemp" titleKey="request.hotbedTemp"/>
					
					<display:column property="layerHeight" titleKey="request.layerHeight"/>
					
					<display:column property="startDate" titleKey="request.startDate"/>
					
					<security:authorize access="hasRole('COMPANY')">
					<display:column titleKey="request.order">
							<acme:link link="order/company/display.do?orderId=${row.order.id}" code = "order.display"/>
					</display:column>
				
					<display:column>
						<acme:link link="request/company/delete.do?requestId=${row.id}" code = "request.delete"/>
					</display:column>
					
					<display:column>
						<jstl:if test="${row.number == 1}">
						<acme:link link="request/company/done.do?requestId=${row.id}" code = "request.done"/>
						</jstl:if>
					</display:column>
					</security:authorize>
					
					
				</display:table>
				
				<h3><spring:message code="request.printed" /></h3>
				
				<display:table name="printed" id="row" pagesize="5" class="displaytag" requestURI="${requestURI}">
					
					<display:column property="extruderTemp" titleKey="request.extruderTemp"/>
					
					<display:column property="hotbedTemp" titleKey="request.hotbedTemp"/>
					
					<display:column property="layerHeight" titleKey="request.layerHeight"/>
					
					<display:column property="startDate" titleKey="request.startDate"/>
					
					<display:column property="endDate" titleKey="request.endDate" sortable="true"/>
					<security:authorize access="hasRole('COMPANY')">
					<display:column titleKey="request.order">
							<acme:link link="order/company/display.do?orderId=${row.order.id}" code = "order.display"/>
					</display:column>
					
					</security:authorize>
					
				</display:table>
			</jstl:when>
			
			
			<jstl:otherwise>
					<security:authorize access="hasRole('COMPANY')">
					<br><br><acme:link link="printer/company/addSpooler.do?printerId=${printer.id}" code="printerSpooler.create"/><br><br>
				</security:authorize>
			
			</jstl:otherwise>
		</jstl:choose>
		
		</security:authorize>
			
		<acme:back code="printer.cancel"/>

		
	</form:form>









