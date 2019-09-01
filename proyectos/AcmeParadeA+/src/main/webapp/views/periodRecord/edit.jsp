<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>


<jstl:if test="${permission}">
	<form:form action="periodRecord/brotherhood/edit.do" modelAttribute="periodRecord" id="form">
	
		<form:hidden path="id" />
		
		<form:hidden path="version" />
		
		
		<fieldset>
		<legend> <spring:message code="pr.periodRecord" />: </legend>
		
		<form:label path="title">
			<spring:message code="history.inceptionRecord.title" />:
		</form:label>
		<form:input path="title"  placeholder="title"/>
		<form:errors cssClass="error" path="title" />
		<br />
		<br />
		
		
		
		<form:label path="description">
			<spring:message code="history.inceptionRecord.description" />:
		</form:label>
		<form:input path="description" placeholder="description"/>
		<form:errors cssClass="error" path="description" />
		<br />
		<br />
		
		<form:label path="startYear">
		<spring:message code="history.periodRecord.startYear" />:
		</form:label>
		<form:input path="startYear"  placeholder="2020"/>
		<form:errors cssClass="error" path="startYear" />
		<br />
		<br />
		
		<form:label path="endYear">
		<spring:message code="history.periodRecord.endYear" />:
		</form:label>
		<form:input path="endYear"  placeholder="2020"/>
		<form:errors cssClass="error" path="endYear"  placeholder="2020"/>
		<br />
		<br />
		
			<form:label path="pictures">
		<spring:message code="history.inceptionRecord.pictures" />:
	</form:label>
	<form:textarea cols='70' path="pictures" placeholder="url1, url2, url3..."/>
	<form:errors cssClass="error" path="pictures" />
	<br />
	<br />

	<input type="submit" name="save" id="save" value="<spring:message code="pr.save" />" />&nbsp; 
		<jstl:if test="${periodRecord.id != 0}">
			<input type="submit" name="delete" value="<spring:message code="pr.delete" />"
				onclick="return confirm('<spring:message code="pr.confirm.delete" />')" />&nbsp;
		</jstl:if>
		<jstl:if test="${history.id!=0}">
		<input type="button" name="cancel" value="<spring:message code="pr.cancel" />"
			onclick="javascript: relativeRedir('/history/brotherhood/display.do');" />
		<br />
		</jstl:if>
		
	</fieldset>
	</form:form>

</jstl:if>
<jstl:if test="${!permission}">
<h3><spring:message code="pr.nopermisiontobehere" /></h3>
</jstl:if>
