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

<script>

</script>
<form:form	modelAttribute="comment" >

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="post" />
	
	<acme:input code="comment.title" path="title" placeholder="Title"/>
	
	<acme:textarea code="comment.description" path="description" placeholder="Add here your comment..."/>
	
	<div class="form-group">
		<form:label path="type">
			<spring:message code="comment.type" />
		</form:label>
		
		<form:select path="type" id="type" name="type">
	                <option value="INFORMATION"><spring:message code="comment.type.information" /></option>
	                <option value="PRINTING EXPERIENCE"><spring:message code="comment.type.printing.experience" /></option>
	    </form:select>
    </div>
    
    <div class="form-group" id="score-wrapper" style="display:none">
		<form:label path="score">
			<spring:message code="comment.score" />
		</form:label>
		<form:input type="text" path="score" id="score" name="score" placeholder="4.5"/>	
		<form:errors cssClass="error" path="score" />
    </div>
    
	<acme:textarea code="comment.pictures" path="pictures" placeholder="https://www.urlToPicture.com, https://www.urlToPicutre2.com..."/>
	<br>
	
	<button type=submit name="save">
		<spring:message code="comment.save" />
	</button>
	
	<acme:cancel code="comment.goback" url="post/list.do"/>
		
</form:form>	

<script>
	var ddl = document.getElementById("type");
	ddl.onchange = showScore;
	function showScore()
	{   
	    var ddl = document.getElementById("type");
	    var selectedValue = ddl.options[ddl.selectedIndex].value;
	
	
	    if (selectedValue == "PRINTING EXPERIENCE")
	    {   document.getElementById("score-wrapper").style.display = "block";
	    }
	    else
	    {
	       document.getElementById("score-wrapper").style.display = "none";
	    }
	}
</script>