
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<form:form modelAttribute="submission">

	
	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="author" />
	<form:hidden path="ticker"/>
	<form:hidden path="moment"/>
	<form:hidden path="status"/>
	<form:hidden path="conference"/>
	<jstl:if test="${submission.id ==0 }">
	<form:hidden path="paper.cameraReadyPaper"/>
</jstl:if>		

	
	
	<acme:input code="submission.paper.title" path="paper.title"/>
	<acme:textarea code="submission.paper.summary" path="paper.summary"/>
	<acme:input code="submission.paper.documet" path="paper.document"/>
	<jstl:if test="${submission.id != 0 and submission.status eq 'ACCEPTED' and fecha.time < submission.conference.submissionDeadline.time}">
	<acme:checkbox code="submission.paper.cameraReadyPaper" path="paper.cameraReadyPaper" value="paper.cameraReadyPaper"/> 

	</jstl:if>
	<acme:submit name="save" code="submission.save" />
	<acme:cancel url="${cancelURI}" code="submission.cancel" />

</form:form>
