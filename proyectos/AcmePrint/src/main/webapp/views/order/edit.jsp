<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>

<form:form	modelAttribute="order" >

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="moment" />
	<form:hidden path="status" />
	<form:hidden path="isCancelled" />
	<form:hidden path="post" />
	<form:hidden path="customer" />

	<acme:input code="order.stl" path="stl" placeholder="https://github.com/pedroswe/3Dprint/blob/master/EiffelTower_fixed.stl"/>

	<div class="form-group">
    <form:label path="material">
      <spring:message code="order.material" />
    </form:label>
    
    <form:select path="material">
			<form:options items="${materials}"/>
	</form:select>
    
 
    </div>
	
	<acme:input code="order.comments" path="comments" placeholder="This is a comment"/>
	<br />	
		
	<acme:checkbox code="order.isDraft" path="isDraft"/>
	<br />
	
	<button type="submit" name="save">
		<spring:message code="order.save" />
	</button>
	
<acme:back code="company.go.back"/>		
</form:form>	