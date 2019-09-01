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

<display:table name="applications" id="row" requestURI="${requestURI}"
	pagesize="5" class="displaytag">
	
	<display:column property="creationMoment" titleKey="application.creationMoment"
		sortable="true" format="{0, date, dd/MM/yyyy HH:mm}"/>
		
		<display:column titleKey="application.status">
			<jstl:if test="${row.status eq 'ACCEPTED'}">
			<spring:message code="status.accepted"></spring:message>
			</jstl:if>
			<jstl:if test="${row.status eq 'REJECTED'}">
			<spring:message code="status.rejected"></spring:message>
			</jstl:if>
			<jstl:if test="${row.status eq 'SUBMITTED'}">
			<spring:message code="status.submitted"></spring:message>
			</jstl:if>
			
			
</display:column> <%-- 
	<display:column property="status" titleKey="application.status"
		sortable="true" /> --%>
	
	<display:column property="answer" titleKey="application.answer"
		sortable="true" />
	
	<display:column property="linkCode" titleKey="application.linkCode"
		sortable="true" />
	
	<display:column property="submittedMoment" titleKey="application.submittedMoment"
		sortable="true" format="{0, date, dd/MM/yyyy HH:mm}" />

	<security:authorize access="hasRole('ROOKIE')">
		<jstl:if test="${permiso==true}">
			
				<display:column>
				
					<jstl:if test="${row.status eq 'PENDING'}">
				
					<acme:link link="application/rookie/edit.do?applicationId=${row.id}"
							code="application.edit" />
							
					</jstl:if>

				</display:column>
				
				<display:column>
				
					<acme:link link="application/rookie/display.do?applicationId=${row.id}"
							code="application.display" />

				</display:column>
				
				<display:column>
				
					<jstl:if test="${row.status eq 'PENDING'}">
					<jstl:if test="${!(row.linkCode eq '' || row.answer eq '')}">
					<acme:link link="application/rookie/submit.do?applicationId=${row.id}"
							code="application.submit" />
					
					</jstl:if>
					
					
					</jstl:if>

				</display:column>
		</jstl:if>
	</security:authorize>
	
	<security:authorize access="hasRole('COMPANY')">
		<jstl:if test="${permiso==true}">
		
				<display:column>
				
					<acme:link link="application/company/display.do?applicationId=${row.id}"
							code="application.display" />

				</display:column>
			
				<display:column>
				
					<jstl:if test="${row.status eq 'SUBMITTED'}">
				
					<acme:link link="application/company/accept.do?applicationId=${row.id}"
							code="application.accept" />
					
					</jstl:if>

				</display:column>
				
				<display:column>
				
					<jstl:if test="${row.status eq 'SUBMITTED'}">
				
					<acme:link link="application/company/reject.do?applicationId=${row.id}"
							code="application.reject" />
							
					</jstl:if>

				</display:column>
				
		</jstl:if>
	</security:authorize>

</display:table>

<br>

<acme:back code="application.goback" />