<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


<fmt:formatDate value="${finder.startDate}" pattern="dd/MM/yyyy"
	var="parsedStartDate" />
<fmt:formatDate value="${finder.endDate}" pattern="dd/MM/yyyy"
	var="parsedEndDate" />
<form:form modelAttribute="finder">
	<form:hidden path="id" />
	<form:hidden path="version" />

	<form:label path="keyWord">
		<spring:message code='handyworker.keyWord' /> :</form:label>
	<form:input path="keyWord" value="${finder.keyWord}" />
	<form:errors cssClass="error" path="keyWord"></form:errors>
	<br />
	<form:label path="priceMin">
		<spring:message code='handyworker.maxPrice' /> :</form:label>
	<form:input path="priceMin" value="${finder.priceMin}" />
	<form:errors cssClass="error" path="priceMin"></form:errors>
	<br />
	<form:label path="priceMax">
		<spring:message code='handyworker.minPrice' /> : </form:label>
	<form:input path="priceMax" value="${finder.priceMax}" />
	<form:errors cssClass="error" path="priceMax"></form:errors>
	<br />
	<form:label path="startDate">
		<spring:message code='handyworker.startDate' /> :</form:label>
	<form:input path="startDate" value="${parsedStartDate}" />
	<form:errors cssClass="error" path="startDate"></form:errors>
	<br />
	<form:label path="endDate">
		<spring:message code='handyworker.endDate' />
	</form:label>
	<form:input path="endDate" value="${parsedEndDate}" />
	<form:errors cssClass="error" path="endDate"></form:errors>
	<br />

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
	<input type="submit" name="save"
		value="<spring:message code='handyworker.save'/>" />

	<input type="button" name="cancel"
		value="<spring:message code="handyworker.cancel" />"
		onclick="javascript: relativeRedir('/');" />
	<br />
	
	
	


</form:form>
