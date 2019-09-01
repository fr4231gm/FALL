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

<form:form action="${actionURI}" modelAttribute="socialprofile">


	

	<div class="fh5co-cover" data-stellar-background-ratio="0.5">
		<div class="desc">
			<div class="col-sm-8 mt">
				<div class="tabulation animate-box">
					
					<div class="tab-content">
						<div class="tab-pane active" id="flights">
							<div class="row">
								<div class="col-xxs-12 col-xs-6 mt alternate">
									<div class="input-field">
										<form:label path="nick" readonly = "true">
											<spring:message code="socialprofile.nick" />:&nbsp;
		</form:label>

										<form:input path="nick" value="${socialprofile.nick}" 
											readonly = "true"/>
										<form:errors path="nick" cssClass="error" />
										<br />
									</div>
								</div>

								<div class="col-xxs-12 col-xs-6 mt alternate">
									
								
								<div class="col-xxs-12 col-xs-6 mt alternate">
									<div class="input-field">
										<form:label path="socialNetwork" readonly = "true">
											<spring:message code="socialprofile.socialNetwork" />:&nbsp;
		</form:label>
										<form:input path="socialNetwork" value="${socialprofile.socialNetwork}" 
										readonly = "true" />
										<form:errors path="socialNetwork" cssClass="error" />
									</div>
								</div>
								<div class="col-xxs-12 col-xs-6 mt alternate">
									<div class="input-field">
										<form:label path="link" readonly = "true">
											<spring:message code="socialprofile.link" />:&nbsp;
		</form:label>
										<form:input path="link" value="${socialprofile.link}" 
										readonly = "true" />
										<form:errors path="link" cssClass="error" />
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
	</div>

</form:form>




</html>