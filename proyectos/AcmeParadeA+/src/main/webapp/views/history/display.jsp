<%--
 * 
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>


<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<link rel="stylesheet" href="styles/table.css" type="text/css">


<spring:message code="history.propietaryOf" var="propietary"/>
<h3> <jstl:out value="${propietary} ${history.brotherhood.userAccount.username}"> </jstl:out> </h3>

<%-- Botones para añadir Records al history --%>
<jstl:if test="${history.brotherhood.id == principal.id }">
	<table class="displayStyle">
		<tr>
			<th colspan="4"><spring:message code="history.buttonsCreationRecords" /></th>
		</tr>
		<tr>
			<td>
				<input type="button" name="addPR"
				value="<spring:message code="history.addPR" />"
				onclick="redirect: location.href = 'periodRecord/brotherhood/create.do';" />
			</td>
			
			<td>
				<input type="button" name="addLR"
				value="<spring:message code="history.addLR" />"
				onclick="redirect: location.href = 'legalRecord/brotherhood/create.do';" />
			</td>
			
			<td>
				<input type="button" name="addMR"
				value="<spring:message code="history.addMR" />"
				onclick="redirect: location.href = 'miscellaneousRecord/brotherhood/create.do';" />
			</td>
			
			<td>
				<input type="button" name="addLR2"
				value="<spring:message code="history.addLR2" />"
				onclick="redirect: location.href = 'linkRecord/brotherhood/create.do';" />
			</td>
		</tr>
		
	</table>
</jstl:if>


<table class="displayStyle">


<tr>
<th> <jstl:out value="${propietary} ${history.brotherhood.userAccount.username}"> </jstl:out>  </th>
<th>   </th>
</tr>

<tr>
<td> <strong> <spring:message code="history.propietary" />: </strong> </td>
<td> <a href="brotherhood/display.do?brotherhoodId=${history.brotherhood.id}"><spring:message code="history.display.brotherhood" /></a> </td>
</tr>


<tr>
<td> <strong> <spring:message code="history.inceptionRecord" />: </strong> </td>
<td>
	<table class="displayStyle">
	<tr>
	<th><strong> <spring:message code="history.inceptionRecord" /></strong></th>
	<th></th>
	</tr>
	
	<tr>
	<td><strong> <spring:message code="history.inceptionRecord.title" />: </strong></td>
	<td><jstl:out value="${history.inceptionRecord.title}"/></td>
	</tr>
	
	<tr>
	<td><strong> <spring:message code="history.inceptionRecord.description" />: </strong></td>
	<td><jstl:out value="${history.inceptionRecord.description}"/></td>
	</tr>
	
	<tr>
	<td><strong> <spring:message code="history.inceptionRecord.pictures" />: </strong></td>

	<jstl:set var="picture2" value="${fn:split(history.inceptionRecord.pictures,', ')}"/>
	<td>
	<jstl:forEach var="i" begin="0" end="${fn:length(picture2)-1}">
	
		<img width=30%, src="${picture2[i]}"/>
	</jstl:forEach></td>
	</tr>
	
	<jstl:if test="${history.brotherhood == principal and history.id!=0}" >
		<tr>
		<td colspan="2">
			<br>
		<input type="button" name="edit"
		value="<spring:message code="history.inceptionRecord.edit" />"
		onclick="redirect: location.href = 'history/brotherhood/edit.do?historyId=${history.id}';" />
		</td></tr>
	</jstl:if>

	
	</table>
</td>
</tr>

<jstl:if test="${ not empty history.periodRecord}" >
<jstl:set var="countPR" value="1"/>
<tr>
<td> <strong> <spring:message code="history.periodRecords" />: </strong> </td>
<td>
	<%-- <jstl:forEach var="PR" items="${history.periodRecord}">
	<table class="displayStyle">
	<tr>
	<th><strong> <spring:message code="history.periodRecord" /><jstl:out value="${countPR}"/></strong></th>
	<th></th>
	</tr>
	
	<tr>
	<td><strong> <spring:message code="history.periodRecord.title" />: </strong></td>
	<td><jstl:out value="${PR.title}"/></td>
	</tr>
	
	<tr>
	<td><strong> <spring:message code="history.periodRecord.description" />: </strong></td>
	<td><jstl:out value="${PR.description}"/></td>
	</tr>
	
	<tr>
	<td><strong> <spring:message code="history.periodRecord.startYear" />: </strong></td>
	<td><jstl:out value="${PR.startYear}" /></td>
	</tr>
	
	<tr>
	<td><strong> <spring:message code="history.periodRecord.endYear" />: </strong></td>
	<td><jstl:out value="${PR.endYear}" /></td>
	</tr>
	
	<tr>
	<td><strong> <spring:message code="history.periodRecord.pictures" />: </strong></td>
	<jstl:set var="picture" value="${fn:split(PR.pictures,', ')}"/>
	<td>
	<jstl:forEach var="i" begin="0" end="${fn:length(picture)-1}">
		<img width=30%, src="${picture[i]}"/>
	</jstl:forEach></td>
	</tr>
	
	<jstl:set var="countPR" value="${countPR+1}"/>
	
	<jstl:if test="${history.brotherhood == principal}" >
	<tr>
		<td colspan="2">
			<br>
			<input type="button" name="edit"
			value="<spring:message code="history.editPR" />"
			onclick="redirect: location.href = 'periodRecord/brotherhood/edit.do?periodRecordId=${PR.id}';" />
		</td>
	</tr>
	</jstl:if>
	
	</table>
	</jstl:forEach>--%>
<display:table name="history.periodRecord" id="row4" 
	 class="displayTag">
	 <display:column
	property	= "title"
	titleKey	= "chapter.title"
	sortable	= "true"
/>
	 <display:column
	property	= "description"
	titleKey	= "history.periodRecord.description"
	sortable	= "true"
/>
	 <display:column
	property	= "startYear"
	titleKey	= "history.periodRecord.startYear"
	sortable	= "true"
/>

	 <display:column
	property	= "endYear"
	titleKey	= "history.periodRecord.endYear"
	sortable	= "true"
/>
	 <display:column
	titleKey	= "history.periodRecord.pictures"
	sortable	= "true"
>
<jstl:set var="picture" value="${fn:split(row4.pictures,', ')}"/>
<jstl:forEach var="i" begin="0" end="${fn:length(picture)-1}">

		<img width=30%, src="${picture[i]}"/>
	</jstl:forEach>
</display:column>
	 <display:column
	titleKey	= "history.editPR"
	sortable	= "true"	
>
<input type="button" name="edit"
			value="<spring:message code="history.editPR" />"
			onclick="redirect: location.href = 'periodRecord/brotherhood/edit.do?periodRecordId=${row4.id}';" />
</display:column>
</display:table>
</td>
</tr>
</jstl:if>

<%-- Hacemos lo mismo para  LegalRecord --%>
<jstl:if test="${ not empty history.legalRecord}" >
<jstl:set var="countLR" value="1"/>
<tr>
<td> <strong> <spring:message code="history.legalRecords" />: </strong> </td>
<td>
	<display:table name="history.legalRecord" id="row3" 
	 class="displayTag">
	 <display:column
	property	= "title"
	titleKey	= "chapter.title"
	sortable	= "true"
/>
	 <display:column
	property	= "description"
	titleKey	= "history.legalRecord.description"
	sortable	= "true"
/>
	 <display:column
	property	= "vatNumber"
	titleKey	= "history.legalRecord.VATNumber"
	sortable	= "true"
/>

	 <display:column
	property	= "legalName"
	titleKey	= "history.legalRecord.legalName"
	sortable	= "true"
/>
	 <display:column
	property	= "applicableLaws"
	titleKey	= "history.legalRecord.applicableLaws"
	sortable	= "true"
/>

	 <display:column
	titleKey	= "history.editLR"
	sortable	= "true"	
>
<input type="button" name="edit"
			value="<spring:message code="history.editLR" />"
			onclick="redirect: location.href = 'legalRecord/brotherhood/edit.do?legalRecordId=${row3.id}';" />
</display:column>
</display:table>
</td>
</tr>

</jstl:if>

<%-- Igual para MiscellaneousRecord --%>
<jstl:if test="${ not empty history.miscellaneousRecord}" >
<jstl:set var="countMR" value="1"/>
<tr>
<td> <strong> <spring:message code="history.miscellaneousRecords" />: </strong> </td>
<td>
	<display:table name="history.miscellaneousRecord" id="row2" 
	 class="displayTag">
	 <display:column
	property	= "title"
	titleKey	= "chapter.title"
	sortable	= "true"
/>
	 <display:column
	property	= "description"
	titleKey	= "history.miscellaneousRecord.description"
	sortable	= "true"
/>
	 <display:column
	titleKey	= "history.editMR"
	sortable	= "true"	
>
<input type="button" name="edit"
			value="<spring:message code="history.editMR" />"
			onclick="redirect: location.href = 'miscellaneousRecord/brotherhood/edit.do?miscellaneousRecordId=${row2.id}';" />
</display:column>
</display:table>
</td>
</tr>
</jstl:if>

<jstl:if test="${ not empty history.linkRecord}" >
<jstl:set var="countLR2" value="1"/>
<tr>
<td> <strong> <spring:message code="history.linkRecords" />: </strong> </td>
<td>
	<display:table name="history.linkRecord" id="row" 
	 class="displayTag">
	 <display:column
	property	= "title"
	titleKey	= "chapter.title"
	sortable	= "true"
/>
	 <display:column
	property	= "description"
	titleKey	= "history.legalRecord.description"
	sortable	= "true"
/>
	 <display:column
	titleKey	= "history.linkRecord.brotherhood"
	sortable	= "true"
> <a href="brotherhood/display.do?brotherhoodId=${row.brotherhood.id}"><jstl:out value="${row.brotherhood.title}"/></a>

</display:column>
	 <display:column
	titleKey	= "history.editLR2"
	sortable	= "true"	
>
<input type="button" name="edit"
			value="<spring:message code="history.editLR2" />"
			onclick="redirect: location.href = 'linkRecord/brotherhood/edit.do?linkRecordId=${row.id}';" />
</display:column>
</display:table>
</td>
</tr>

</jstl:if>

</table>

<div>
<jstl:if test="${history.brotherhood == principal}" >
	<jstl:if test="${history.id!=0}">
		<input type="button" name="edit"
		value="<spring:message code="history.edit" />"
		onclick="redirect: location.href = 'history/brotherhood/edit.do?historyId=${history.id}';" />
	</jstl:if>
</jstl:if>
</div>


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

<script>
$(document).ready( function () {	
    $('#row4').dataTable( {
    	"language": {
        	"url": '${tableLang}'
    	},
		"lengthMenu": [ 3, 5, 10, 25, 50 ]
    } );
} );
</script>


</html>

