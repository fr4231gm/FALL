<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>

<link rel="stylesheet" href="styles/table.css" type="text/css">

<%-- Botones para añadir Datos al inventory --%>
<jstl:if test="${inventory.company.id == principal.id }">
<h2><jstl:out value="${inventory.ticker}"></jstl:out></h2>
	<table class="displayStyle">
		<tr>
			<th colspan="4"><spring:message
					code="inventory.buttonsCreation" /></th>
		</tr>
		<tr>
			<td><input type="button" style="width: inherit;" name="addMR" 
				value="<spring:message code="inventory.addMR" />"
				onclick="redirect: location.href = 'printer/company/create.do?inventoryId=${inventory.id}';" />
			</td>

			<td><input type="button" style="width: inherit;" name="addER" 
				value="<spring:message code="inventory.addER" />"
				onclick="redirect: location.href = 'spool/company/create.do?inventoryId=${inventory.id}';" />
			</td>
			<td><input type="button" style="width: inherit;" name="addPD"
				value="<spring:message code="inventory.addPD" />"
				onclick="redirect: location.href = 'sparePart/company/create.do?inventoryId=${inventory.id}';" />
			</td>
		</tr>

	</table>
</jstl:if>

<table class="displayStyle">


	<tr>
		<th><jstl:out
				value="${propietary} ${inventory.company.userAccount.username}">
			</jstl:out></th>
		<th></th>
	</tr>


	<jstl:if test="${ not empty inventory.spools}">
		<jstl:set var="countPR" value="1" />
		<tr>
			<td style="border-bottom: 1px solid black;"><strong> <spring:message
						code="inventory.spools" />:
			</strong></td>
			<td style="border-bottom: 1px solid black;"><display:table name="inventory.spools" id="row2"
					class="displayTag">
					<display:column property="ticker" titleKey="inventory.spool.ticker"
						 />
					<display:column property="materialName"
						titleKey="inventory.spool.materialName"  />
					<display:column property="diameter"
						titleKey="inventory.spool.diameter"  />
					<display:column property="length"
						titleKey="inventory.spool.length"  />
					<display:column property="remainingLength"
						titleKey="inventory.spool.remainingLength"  />
					<display:column property="weight"
						titleKey="inventory.spool.weight"  />
					<display:column property="color"
						titleKey="inventory.spool.color"  />
					<display:column property="meltingTemperature"
						titleKey="inventory.spool.meltingTemperature"  />
					
					<jstl:if
						test="${inventory.company == principal and inventory.id!=0}">
						<display:column titleKey="inventory.editER" >
							<input type="button" style="width: inherit;" name="edit"
								value="<spring:message code="inventory.editER" />"
								onclick="redirect: location.href = 'spool/company/edit.do?spoolId=${row2.id}';" />
						</display:column>
					</jstl:if>
				</display:table></td>
		</tr>
	</jstl:if>
	
	<%-- Igual para spare part --%>
	<jstl:if test="${ not empty inventory.spareParts}">
		<jstl:set var="countPD" value="1" />
		<tr>
			<td style="border-bottom: 1px solid black;"> <strong> <spring:message
						code="inventory.spareParts" />:
			</strong></td>
			<td style="border-bottom: 1px solid black;"><display:table name="inventory.spareParts" id="row3"
					class="displayTag">
					<display:column property="ticker" 
						titleKey="inventory.sparePart.ticker"  />
					<display:column property="name"
						titleKey="inventory.sparePart.name"  />
					<display:column property="description"
						titleKey="inventory.sparePart.description"  />
					<display:column titleKey="inventory.sparePart.purchaseDate"  >
						 <fmt:formatDate value="${row3.purchaseDate}"  pattern = "yyyy-MM-dd" /></display:column>	
					<display:column property="purchasePrice"
						titleKey="inventory.sparePart.purchasePrice"  />
					<display:column property="photo"
						titleKey="inventory.sparePart.photo"  />
					<display:column titleKey="inventory.sparePart.photo"  >
						 <img src="${row3.photo}" alt="photo" style="width: 50px; height: 50px;"/></display:column>	
					
					<jstl:if
						test="${inventory.company == principal and inventory.id!=0}">
						<display:column titleKey="inventory.editPD" >
							<input type="button" style="width: inherit;" name="edit"
								value="<spring:message code="inventory.editPD" />"
								onclick="redirect: location.href = 'sparePart/company/edit.do?sparePartId=${row3.id}';" />
						</display:column>
					</jstl:if>
				</display:table></td>
		</tr>
	</jstl:if>


	<%-- Igual para printer --%>
	<jstl:if test="${ not empty inventory.printers}">
		<jstl:set var="countMR" value="1" />
		<tr>
			<td style="border-bottom: 1px solid black;"><strong> <spring:message
						code="inventory.printers" />:
			</strong></td>
			<td style="border-bottom: 1px solid black;"><display:table name="inventory.printers" id="row"
					class="displayTag">
					<display:column property="ticker" 
						titleKey="inventory.printer.ticker"  />
					<display:column property="make"
						titleKey="inventory.printer.make"  />
					<display:column property="model"
						titleKey="inventory.printer.model"  />
					<display:column titleKey="inventory.printer.warrantyDate"  >
						 <fmt:formatDate value="${ row.warrantyDate}"  pattern = "yyyy-MM-dd" /></display:column>
					<display:column titleKey="inventory.printer.shoppingWebsite"  >
						 <a href="${row.shoppingWebsite}">Click</a></display:column>
					<display:column property="materials"
						titleKey="inventory.printer.materials"  />
						<display:column titleKey="inventory.printer.isActive" sortable="true">
			<jstl:if test="${row.isActive == 'true'}">
				<spring:message code="inventory.printer.active.yes"></spring:message>
			</jstl:if>
			<jstl:if test="${row.isActive == 'false'}">
				<spring:message code="inventory.printer.active.yes"></spring:message>
			</jstl:if>
		</display:column>
			
						
					<display:column>
						 <a href="printer/company/display.do?printerId=${row.id}"><spring:message code="printer.display"/></a>
					</display:column>
						
					<jstl:if
						test="${inventory.company == principal and inventory.id!=0}">
						<display:column titleKey="inventory.editMR" >
							<input type="button" style="width: inherit;" name="edit"
								value="<spring:message code="inventory.editMR" />"
								onclick="redirect: location.href = 'printer/company/edit.do?printerId=${row.id}';" />
						</display:column>
					</jstl:if>
				</display:table></td>
		</tr>
	</jstl:if>

</table>

<script>
$(document).ready( function () {	
    $('#row').dataTable( {
    	"language": {
        	"url": '${tableLang}'
    	},
		"lengthMenu": [ 3, 5, 10, 25, 50 ]
    } );
} );
</script>

<script>
$(document).ready( function () {	
    $('#row2').dataTable( {
    	"language": {
        	"url": '${tableLang}'
    	},
		"lengthMenu": [ 3, 5, 10, 25, 50 ]
    } );
} );
</script>

<script>
$(document).ready( function () {	
    $('#row3').dataTable( {
    	"language": {
        	"url": '${tableLang}'
    	},
		"lengthMenu": [ 3, 5, 10, 25, 50 ]
    } );
} );
</script>


</html>

