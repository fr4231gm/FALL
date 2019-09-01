<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>


<jstl:if test="${permission}">
	<form:form action="miscellaneousRecord/brotherhood/edit.do" modelAttribute="miscellaneousRecord" id="form">
	
		<form:hidden path="id" />
		
		<form:hidden path="version" />
		
		
		<fieldset>
		<legend> <spring:message code="mr.miscellaneousRecord" />: </legend>
		
		<form:label path="title">
			<spring:message code="history.inceptionRecord.title" />:
		</form:label>
		<form:input path="title"  placeholder="Title" />
		<form:errors cssClass="error" path="title" />
		<br />
		<br />
		
		
		
		<form:label path="description">
			<spring:message code="history.inceptionRecord.description" />:
		</form:label>
		<form:input path="description" placeholder="this miscellaneous record..."  />
		<form:errors cssClass="error" path="description" />
		<br />
		<br />

	<input type="submit" name="save" id="save" value="<spring:message code="mr.save" />" />&nbsp; 
		<jstl:if test="${miscellaneousRecord.id != 0}">
			<input type="submit" name="delete" value="<spring:message code="mr.delete" />"
				onclick="return confirm('<spring:message code="mr.confirm.delete" />')" />&nbsp;
		</jstl:if>
		<jstl:if test="${history.id!=0}">
		<input type="button" name="cancel" value="<spring:message code="mr.cancel" />"
			onclick="javascript: relativeRedir('/history/brotherhood/display.do');" />
		<br />
		</jstl:if>
		
	</fieldset>
	</form:form>

</jstl:if>
<jstl:if test="${!permission}">
<h3><spring:message code="mr.nopermisiontobehere" /></h3>
</jstl:if>






