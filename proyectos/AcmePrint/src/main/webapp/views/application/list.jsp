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
	
	<display:column property="moment" titleKey="application.moment"
		sortable="true" format="{0, date, dd/MM/yyyy HH:mm}"/>
		
	<display:column titleKey="application.status" sortable="true">
		<jstl:if test="${row.status eq 'PENDING'}">
			<p class="application-pending"><spring:message code="application.pending"/></p>
		</jstl:if>
		<jstl:if test="${row.status eq 'ACCEPTED'}">
			<p class="application-accepted"><spring:message code="application.accepted"/></p>
		</jstl:if>
		<jstl:if test="${row.status eq 'REJECTED'}">
			<p class="application-rejected"><spring:message code="application.rejected"/></p>
		</jstl:if>
	</display:column>
	
	<display:column property="offeredPrice" titleKey="application.offeredPrice"
		sortable="true" />

	<security:authorize access="hasRole('CUSTOMER')">
		<jstl:if test="${permiso==true}">
			
				<display:column>
				
					<jstl:if test="${row.status eq 'PENDING'}">
				
					<acme:link link="application/customer/accept.do?applicationId=${row.id}"
							code="application.accept" />
							
					</jstl:if>

				</display:column>
				
				<display:column>
				
					<jstl:if test="${row.status eq 'PENDING'}">
				
						<acme:link link="application/customer/reject.do?applicationId=${row.id}"
							code="application.reject" />
							
					</jstl:if>

				</display:column>
				
				<display:column>
				
					<acme:link link="application/customer/display.do?applicationId=${row.id}"
							code="application.display" />

				</display:column>
		</jstl:if>
	</security:authorize>
	
	<security:authorize access="hasRole('COMPANY')">
		<jstl:if test="${permiso==true}">
		
				<display:column>
				
					<acme:link link="application/company/display.do?applicationId=${row.id}"
							code="application.display" />

				</display:column>
				
		</jstl:if>
	</security:authorize>
	
		<security:authorize access="hasRole('COMPANY')">
		<jstl:if test="${permiso==true}">
		
				<display:column>
					<jstl:if test="${row.status eq 'ACCEPTED'}">
					<acme:link link="workplan/company/workplan.do?applicationId=${row.id}"
							code="application.create.workplan" />
	</jstl:if>
				</display:column>
				</jstl:if>
		
	</security:authorize>
	
	

</display:table>

<br>

<acme:back code="application.goback" />