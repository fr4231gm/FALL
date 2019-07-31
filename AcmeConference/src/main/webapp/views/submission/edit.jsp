
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
	


	<acme:textarea code="submission.cameraReadyPaper" path="cameraReadyPaper" placeholder="Esto es un papel que es muy listo y vive en una cámara"/>
	
	
	<acme:submit name="save" code="submission.save" />
	<acme:cancel url="${cancelURI}" code="submission.cancel" />

</form:form>
