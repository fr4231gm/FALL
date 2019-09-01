<%-- application listing for handyworker --%>
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>



<display:table name="applications" id="row" requestURI="${requestURI}" pagesize="6" class="displaytag">
	<div>
	
		<fmt:formatDate value="${dateSystem}" pattern="yyyy-MM-dd HH:mm" var="systemDateFormated" />

		<jstl:choose>
			<jstl:when
				test="${row.fixUpTask.startDate < systemDateFormated and row.status eq 'PENDING'}">
				<jstl:set var="style" value="grey"></jstl:set>
			</jstl:when>
			<jstl:when
				test="${row.fixUpTask.startDate > systemDateFormated and row.status eq 'PENDING'}">
				<jstl:set var="style" value="white"></jstl:set>
			</jstl:when>
			<jstl:when test="${row.status eq 'ACCEPTED'}">
				<jstl:set var="style" value="green"></jstl:set>
			</jstl:when>
			<jstl:when test="${row.status eq 'REJECTED'}">
				<jstl:set var="style" value="orange"></jstl:set>
			</jstl:when>
		</jstl:choose>

		<security:authorize access="hasRole('CUSTOMER')">
			<display:column>
				<a href="application/customer/edit.do?applicationId=${row.id}"><spring:message
						code="application.edit"></spring:message></a>
			</display:column>
		</security:authorize>

		<display:column
			property="price"
			titleKey="application.price"></display:column>
		<display:column
			property="moment"
			titleKey="application.moment" format="{0,date,yyyy-MM-dd HH:mm}"></display:column>

		<display:column
			property="status"
			titleKey="application.status"
			style="background-color:${style};">	
		</display:column>
		<display:column
			property="comments"
			titleKey="application.comments"></display:column>
		<display:column>
			<a href="application/handyworker/show.do?applicationId=${row.id}"><spring:message
					code="application.seemore"></spring:message></a>
		</display:column>
	</div>	
</display:table>


