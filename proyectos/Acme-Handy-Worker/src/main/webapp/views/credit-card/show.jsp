<%-- Show a credit card--%>
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<b><spring:message code="creditcard.holdername"></spring:message>:&nbsp;</b>
<jstl:out value="${creditcard.holderName}"></jstl:out>
<br />

<b><spring:message code="creditcard.brandname"></spring:message>:&nbsp;</b>
<jstl:out value="${creditcard.brandName}"></jstl:out>
<br />

<b><spring:message code="creditcard.number"></spring:message>:&nbsp;</b>
<jstl:out value="${creditcard.number}"></jstl:out>
<br />

<b><spring:message code="creditcard.month"></spring:message>:&nbsp;</b>
<fmt:formatNumber type="number" maxIntegerDigits="2" minIntegerDigits="2" value="${creditcard.expirationMonth}"/>
<br />

<b><spring:message code="creditcard.year"></spring:message>:&nbsp;</b>
<jstl:out value="${creditcard.expirationYear}"></jstl:out>
<br />

<security:authorize access="hasRole('CUSTOMER')">
	<b>CVV:&nbsp;</b>
	<jstl:out value="${creditcard.CVV}"></jstl:out>
	<br />
</security:authorize>




