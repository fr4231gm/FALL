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

<security:authorize access="hasRole('CUSTOMER')">
	<jstl:if test="${permiso == true }">
		<form:form	modelAttribute="application" >
		
			<form:hidden path="id" />
			<form:hidden path="version" />
			<form:hidden path="moment" />
			<form:hidden path="status"/>
			<form:hidden path="offeredPrice"/>
			<form:hidden path="company" />
			<form:hidden path="order" />
			<form:hidden path="companyComments" />
			
			<jstl:if test="${application.status eq 'ACCEPTED'}">
				<h4><spring:message code="application.add.comments.accepted"></spring:message></h4>
			</jstl:if>
			
			<jstl:if test="${application.status eq 'REJECTED'}">
				<h4><spring:message code="application.add.comments.rejected"></spring:message></h4>
			</jstl:if>
			
			<acme:input code="application.customerComments" path="customerComments"/>
						
			<jstl:if test="${application.status eq 'ACCEPTED'}">
				<button type="submit" name="accept"><spring:message code="application.save"/></button>
			</jstl:if>
			
			<jstl:if test="${application.status eq 'REJECTED'}">
				<button type="submit" name="reject"><spring:message code="application.save"/></button>
			</jstl:if>
		</form:form>
	
	</jstl:if>	

</security:authorize>