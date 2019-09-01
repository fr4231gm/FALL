<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<form:form modelAttribute="finder" action="/finder/handy-worker/save.do">
    <form:hidden path="id"/>
    <form:hidden path="version" />

    <form:label path="keyWord"><spring:message code='handyWorker.keyWord'/> :</form:label>
        <form:input path="keyword" value="${finder.keyword}"/>
        <form:errors cssClass="error" path="keyWord"></form:errors>
        <br/>
    <form:label path="minPrice" ><spring:message code='handyWorker.minPrice'/> :</form:label>
        <form:input path="minPrice" value="${finder.minPrice}"/>
        <form:errors cssClass="error" path="minPrice"></form:errors>
        <br/>
    <form:label path="maxPrice" ><spring:message code='handyWorker.maxPrice'/> : </form:label>
        <form:input path="maxPrice" value="${finder.maxPrice}"/>
        <form:errors cssClass="error" path="maxPrice"></form:errors>
        <br/>
    <form:label path="startDate" ><spring:message code='handyWorker.startDate'/> :</form:label>
        <form:input path="startDate" value="${finder.startDate}"/>
        <form:errors cssClass="error" path="startDate"></form:errors>
        <br/>
    <form:label path="endDate" ><spring:message code='handyWorker.endDate'/></form:label>
        <form:input path="endDate" value="${finder.endDate}"/>
        <form:errors cssClass="error" path="endDate"></form:errors>
        <br/>

     <form:label path="category">
		<spring:message code="handyworker.category" />:
	</form:label>
	<form:select id="categories" path="category">
		<form:option value="0" label="----" />		
		<form:options items="${categories}" itemValue="id" itemLabel="name" />
	</form:select>
	<form:errors cssClass="error" path="category" />
	<br />

 <form:label path="warranty">
		<spring:message code="handyworker.warranty" />:
	</form:label>
	<form:select id="warranties" path="warranty">
		<form:option value="0" label="----" />		
		<form:options items="${warranties}" itemValue="id" itemLabel="title" />
	</form:select>
	<form:errors cssClass="error" path="warranty" />
	<br />
        <input type="submit" name="saveFinder" value="<spring:message code='handyWorker.save'/>" />
		
		<input type="button" name="cancel" value="<spring:message code="handyWorker.cancel" />"
		onclick="javascript: relativeRedir('finder/handyworker/show.do');" />
	<br />
    

</form:form>
