<%-- edit.jsp phase --%>
<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>



<form:form action="${actionURI}" modelAttribute="socialProfile">
	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="actor" />
	
	
	
	<acme:input code="socialprofile.nick" path="nick" placeholder="NICK"/>
	<acme:input code="socialprofile.socialNetwork" path="socialNetwork" placeholder="linkedIn"/>
	<acme:input code="socialprofile.link" path="link" placeholder="https://www.linkedIn.com"/>
	
	<acme:submit name="save" code="socialprofile.save"/>
	<acme:cancel url="${cancelURI}" code="socialprofile.cancel"/>
	
</form:form>
