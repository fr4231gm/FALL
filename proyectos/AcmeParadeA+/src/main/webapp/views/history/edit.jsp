<%--
 * 
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>


<form:form action="history/brotherhood/edit.do" modelAttribute="inceptionRecord" id="inceptionRecord">

	<form:hidden path="id" />
	
	<form:hidden path="version" />

	<fieldset>
	<legend><spring:message code="history.inceptionRecord" />: </legend>
	
	<form:label path="title">
		<spring:message code="history.inceptionRecord.title" />:
	</form:label>
	<form:input path="title" placeholder="la historia de mi vida" />
	<form:errors cssClass="error" path="title" />
	<br />
	<br />
	
	<form:label path="description">
		<spring:message code="history.inceptionRecord.description" />:
	</form:label>
	<form:input path="description" placeholder="Al oeste Filadelfia crecía y vivia" />
	<form:errors cssClass="error" path="description" />
	<br />
	<br />
	
	<form:label path="pictures">
		<spring:message code="history.inceptionRecord.pictures" />:
	</form:label>
	<form:textarea cols='70' path="pictures" placeholder="url1, url2, url3..."/>
	<form:errors cssClass="error" path="pictures" />
	<br />
	<br />
	</fieldset>
	<br>
	<br>
	
	


<input type="submit" name="save" id="save" value="<spring:message code="history.save" />" />&nbsp; 
	<jstl:if test="${inceptionRecord.id != 0}">
		<input type="submit" name="delete" value="<spring:message code="history.delete" />"
			onclick="return confirm('<spring:message code="history.confirm.delete" />')" />&nbsp;
	</jstl:if>
	<jstl:if test="${inceptionRecord.id!=0}">
	<input type="button" name="cancel" value="<spring:message code="history.cancel" />"
		onclick="javascript: relativeRedir('/history/brotherhood/display.do');" />
	<br />
	</jstl:if>
	<jstl:if test="${inceptionRecord.id==0}">
	<input type="button" name="cancel" value="<spring:message code="history.cancel" />"
		onclick="javascript: relativeRedir('/welcome/index.do');" />
	<br />
	</jstl:if>
</form:form>










