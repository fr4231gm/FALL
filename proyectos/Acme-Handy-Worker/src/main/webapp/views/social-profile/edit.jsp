<%-- edit.jsp phase --%>
<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<form:form action="${actionURI}" modelAttribute="socialProfile">
	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="actor.id" />
	
	
	
	
	<b><form:label path="nick">
		<spring:message code="socialprofile.nick">:&nbsp;</spring:message></form:label></b>
	<form:input path="nick"
		 />
	<form:errors path="nick" cssClass="error" />
	<br />

	<b><form:label path="socialNetwork"> <spring:message
			code="socialprofile.socialNetwork">:&nbsp;</spring:message></form:label></b>
	<form:input path="socialNetwork"
		 />
	<form:errors path="socialNetwork" cssClass="error" />
	<br />

	
	<form:label path="link">
		<spring:message code='socialprofile.link' /> :</form:label>
	<form:input path="link" />
	<form:errors cssClass="error" path="link"></form:errors>
	<br />

	<input type="submit" name="save"
		value="<spring:message code='socialprofile.save'></spring:message>"
		onclick="javascript: relativeRedir('socialprofile/edit.do?socialProfileId=${socialprofile.id}');" />
	<input type="button" name="cancel"
		value="<spring:message code='phase.cancel'></spring:message>"
		onclick="javascript: relativeRedir('${cancelURI}');" />

</form:form>
