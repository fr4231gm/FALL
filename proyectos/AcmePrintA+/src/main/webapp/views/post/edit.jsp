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
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<form:form action="${action}" modelAttribute="postForm"
	id="form1">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="designer" />
	<form:hidden path="guideId" />
	<form:hidden path="guideVersion" />

	<fieldset>
		<legend>
			<spring:message code="post.create.new" />
		</legend>
		<acme:input code="post.ticker" path="ticker" placeholder="Jose"
			readonly="true" />
		<br />

		<acme:input code="post.moment" path="moment"
			placeholder="12/05/2019 12:30" readonly="true" />
		<br />

		<acme:input code="post.title" path="title"
			placeholder="This is the title of a post" readonly="$readOnly" />
		<br />

		<acme:input code="post.description" path="description"
			placeholder="This is a description of a post" />
		<br />

		<acme:input code="post.score" path="score" readonly="true" placeholder="0.0" />
		<br />

		<acme:checkbox code="post.isDraft" path="isDraft" />
		<br />

		<acme:input code="post.pictures" path="pictures"
			placeholder="http://www.instagram.com/resekyt" />
		<br />

		<form:label path="category">
			<spring:message code="post.category" />
		</form:label>
		<form:select path="category">
			<form:options items="${categories}" />
		</form:select>

		<acme:input code="post.stl" path="stl"
			placeholder="https://github.com/pedroswe/3Dprint/blob/master/8Star_DragonBall_V2.stl" />
	</fieldset>
	<br>

	<fieldset>
		<legend>
			<spring:message code="post.set.guide" />
		</legend>
		<spring:message code="post.guide.instructions"></spring:message>
		<acme:input code="post.guide.extruder" path="extruderTemp" placeholder="200" />
		<acme:input code="post.guide.hotbed" path="hotbedTemp" placeholder="100" />
		<acme:input code="post.guide.layer" path="layerHeight" placeholder="0.2" />
		<acme:input code="post.guide.speed" path="printSpeed" placeholder="2500" />
		<acme:input code="post.guide.retraction" path="retractionSpeed" placeholder="2000" />
		<acme:textarea code="post.guide.advices" path="advices" placeholder="Leave your advices here..." />
	</fieldset>



	<security:authorize access="hasRole('DESIGNER')">
	<button type="submit" name="save" form="form1">
		<spring:message code="post.save" />
	</button>
	
	<acme:cancel code="post.cancel" url="/post/designer/list.do" />
	</security:authorize>
</form:form>