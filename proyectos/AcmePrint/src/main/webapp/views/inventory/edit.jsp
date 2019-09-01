
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<form:form action="inventory/company/edit.do" modelAttribute="inventoryForm" id="inventoryForm">

	<form:hidden path="id" />
	
	<form:hidden path="version" />

	<fieldset>
	<legend><spring:message code="inventory.printer" />: </legend>
		
		<acme:input code="printer.make" path="make" placeholder="Ubisoft"/>
		
		<acme:input code="printer.model" path="model" placeholder="CP - 330" />
		
		<acme:input code="printer.description" path="description" placeholder="printer description" />
		
		<acme:input code="printer.datePurchase" path="datePurchase" placeholder="05/05/2018 05:00"/>
		
		<acme:input code="printer.warrantyDate" path="warrantyDate" placeholder="5/05/2020 05:00"/>
		
		<acme:input code="printer.shoppingWebsite" path="shoppingWebsite" placeholder="https://www.google.es"/>
		
		<acme:input code="printer.dimensionX" path="dimensionX" placeholder="101"/>
		
		<acme:input code="printer.dimensionY" path="dimensionY" placeholder="75" />
		
		<acme:input code="printer.dimensionZ" path="dimensionZ" placeholder="115" />
		
		<acme:input code="printer.extruderDiameter" path="extruderDiameter" placeholder="1.55"/>
		
		<acme:input code="printer.nozzle" path="nozzle" placeholder="1.55"/>
		
		<acme:input code="printer.materials" path="materials" placeholder="PETG, ABS..."/>
		
		<acme:textarea code="printer.photo" path="photo" placeholder="https://www.google.es"/>
		
		<acme:checkbox code="printer.isActive" path="isActive"/>
			
	</fieldset>
	<br>
	<br>
	<fieldset>
			<legend><spring:message code="inventory.spool" />: </legend>
			
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
	
		
		</fieldset>

	
	


	<input type="submit" name="save" id="save" value="<spring:message code="inventory.save" />" />&nbsp; 

	<input type="button" name="cancel" value="<spring:message code="inventory.cancel" />"
		onclick="javascript: relativeRedir('/welcome/index.do');" />
	<br />
</form:form>

