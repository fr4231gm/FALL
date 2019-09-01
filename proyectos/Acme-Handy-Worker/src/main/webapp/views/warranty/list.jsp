

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
	<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
	<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
	<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
	<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
	<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
	<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
	<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

	<!-- Listing grid -->

	<display:table name="warranties" id="row" requestURI="${requestURI}" pagesize="5" class="displaytag" keepStatus="true">

		<!-- Attributes -->

		<spring:message code="warranty.title" var="titleHeader" />
		<display:column property="title" title="${titleHeader}" sortable="true" />


		
		
		
		
		<display:column>
	<jstl:if test="${row.isDraft eq 'true' }">
			<a href="warranty/delete.do?warrantyId=${row.id}">
				<spring:message code="warranty.delete" />
			</a>
			
				</jstl:if>

		</display:column>
		
		
		<display:column>
	<jstl:if test="${row.isDraft eq 'true' }">
			<a href="warranty/edit.do?warrantyId=${row.id}">
				<spring:message code="warranty.edit" />
			</a>
	</jstl:if>
		</display:column>
		
		
		
		<display:column>

			<a href="warranty/show.do?warrantyId=${row.id}">
				<spring:message code="warranty.show" />
			</a>

		</display:column>
		
		

	</display:table>
	
	<a href="warranty/create.do"><spring:message
		code="warranty.create"></spring:message></a>