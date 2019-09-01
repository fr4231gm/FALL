<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<p>Finder</p>



<spring:message var="finderKeyword" code="handyworker.keyword"></spring:message>
<b>${finderKeyword}:&nbsp;</b> <jstl:out value="${finder.keyword}"/>
<br>

<spring:message var="finderCategory" code="handyworker.category"></spring:message>
<b>${finderCategory}:&nbsp;</b> <jstl:out value="${finder.category}"/>
<br>

<spring:message var="finderWarranty" code="handyworker.warranty"></spring:message>
<b>${finderWarranty}:&nbsp;</b> <jstl:out value="${finder.warranty}"/>
<br>

<spring:message var="finderMinPrice" code="handyworker.minPrice"></spring:message>
<b>${finderMinPrice}:&nbsp;</b> <jstl:out value="${finder.minprice}"/>
<br>

<spring:message var="finderMaxPrice" code="handyworker.maxprice"></spring:message>
<b>${finderMaxPrice}:&nbsp;</b> <jstl:out value="${finder.maxprice}"/>
<br>

<spring:message var="finderStartDate" code="handyworker.startdate"></spring:message>
<b>${finderStartDate}:&nbsp;</b> <jstl:out value="${finder.startdate}"/>
<br>

<spring:message var="finderEndDate" code="handyworker.enddate"></spring:message>
<b>${finderEndDate}:&nbsp;</b> <jstl:out value="${finder.enddate}"/>
<br>


<input type="button" name="edit"
	value="<spring:message code='handyworker.finder.edit'></spring:message>"
	onclick="javascript: relativeRedir('finder/handyworker/edit.do');" />

<input type="button" name="list"
	value="<spring:message code='handyworker.finder.list'></spring:message>"
	onclick="javascript: relativeRedir('finder/fixuptask/handyworker/list.do');" />





	
    