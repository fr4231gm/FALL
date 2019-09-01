<%-- edit.jsp fix-up task --%>
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<form:form action="${actionURI}" modelAttribute="phase">


	<spring:message var="date-format" code="phase.date-format" />

	<div class="fh5co-cover" data-stellar-background-ratio="0.5">
		<div class="desc">
			<div class="col-sm-8 mt">
				<div class="tabulation animate-box">
					
					<div class="tab-content">
						<div class="tab-pane active" id="flights">
							<div class="row">
								<div class="col-xxs-12 col-xs-6 mt alternate">
									<div class="input-field">
										<form:label path="title" readonly = "true">
											<spring:message code="phase.title" />:&nbsp;
		</form:label>
										<form:textarea path="description"
											value="${phase.description}" placeholder="Description"
											rows="4" cols="40" readonly = "true"/>
										<form:errors path="description" cssClass="error" />
									</div>
								</div>
								<div class="col-xxs-12 col-xs-6 mt alternate">
									<div class="input-field">
										<form:label path="start" readonly = "true">
											<spring:message code="phase.start" />:&nbsp;
		</form:label>

										<form:input path="start" value="${phase.start}" format="{0,date,yyyy-MM-dd}"
											placeholder="dd/mm/yyyy" readonly = "true"/>
										<form:errors path="start" cssClass="error" />
										<br />
									</div>
								</div>

								<div class="col-xxs-12 col-xs-6 mt alternate">
									<div class="input-field">
										<form:label path="end" readonly = "true">
											<spring:message code="phase.end" />:&nbsp;
		</form:label>
										<form:input path="end" value="${phase.end}" format="{0,date,yyyy-MM-dd}"
											placeholder="dd/mm/yyyy" readonly = "true" />
										<form:errors path="end" cssClass="error" />
									</div>
								</div>

								
						
								<div class="col-xs-12">
								<input
										type="button" name="cancel"
										value="<spring:message code='phase.cancel'></spring:message>"
										onclick="javascript: relativeRedir('${cancelURI}');" />
								
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

</form:form>




</html>