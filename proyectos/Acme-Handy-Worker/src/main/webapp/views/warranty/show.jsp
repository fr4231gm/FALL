<%-- elreyrata did that --%>


<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>


<spring:message var="warrantyTitle" code="warranty.title"></spring:message>
<b>${warrantyTitle}:&nbsp;</b> <jstl:out value="${warranty.title}"/>
<br>

<spring:message var="warrantyTerms" code="warranty.terms"></spring:message>
<b>${warrantyTerms}:&nbsp;</b> <jstl:out value="${warranty.terms}"/>
<br>

<spring:message var="warrantyLaws" code="warranty.laws"></spring:message>
<b>${warrantyLaws}:&nbsp;</b> <jstl:out value="${warranty.laws}"/>
<br>



<input type="button" name="back"
	value="<spring:message code='complaint.backbutton'></spring:message>"
	onclick="javascript: relativeRedir('/warranty/list.do');" />




<br>
