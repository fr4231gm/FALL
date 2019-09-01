
<%-- edit.jsp configuration --%>

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

<form:form modelAttribute="configuration">


	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="lan" />


	<div class="fh5co-cover" data-stellar-background-ratio="0.5">
		<div class="desc">
			<div class="col-sm-8 mt">
				<div class="tabulation animate-box">
					<div class="tab-content">
						<div class="tab-pane active" id="flights">
							<div class="row">
								<div class="col-xxs-12 col-xs-6 mt alternate">
									<div class="input-field">
										<form:label path="systemName">
											<spring:message code="configuration.systemName" />:&nbsp;
										</form:label>
										<form:input path="systemName" value="${configuration.systemName}" />
										<form:errors cssClass="error" path="systemName" />
									</div>
								</div>
								<div class="col-xxs-12 col-xs-6 mt alternate">
									<div class="input-field">
										<form:label path="banner">
											<spring:message code="configuration.banner" />:&nbsp;
										</form:label>
										<form:input path="banner" value="${configuration.banner}" />
										<form:errors cssClass="error" path="banner" />
										<br />
									</div>
								</div>

								<div class="col-xxs-12 col-xs-6 mt alternate">
									<div class="input-field">
										<form:label path="welcomeMessage">
											<spring:message code="configuration.welcomeMessage" />:&nbsp;
										</form:label>
										<form:input path="welcomeMessage" value="${configuration.welcomeMessage}" />
										<form:errors cssClass="error" path="welcomeMessage" />
									</div>
								</div>

								<div class="col-xxs-12 col-xs-6 mt alternate">
									<div class="input-field">
										<form:label path="PNDefaultCountry">
											<spring:message code="configuration.PNDefaultCountry" />:&nbsp;
										</form:label>
										<form:input path="PNDefaultCountry" value="${configuration.PNDefaultCountry}" />
										<form:errors cssClass="error" path="PNDefaultCountry" />
									</div>
								</div>
								<div class="col-xxs-12 col-xs-6 mt alternate">
									<div class="input-field">
										<form:label path="spamWords">
											<spring:message code="configuration.spamWords" />:&nbsp;
										</form:label>
										<form:input path="spamWords" value="${configuration.spamWords}" />
										<form:errors cssClass="error" path="spamWords" />
									</div>
								</div>
								
								<div class="col-xxs-12 col-xs-6 mt alternate">
									<div class="input-field">
										<form:label path="positiveWords">
											<spring:message code="configuration.positiveWords" />:&nbsp;
										</form:label>
										<form:input path="positiveWords" value="${configuration.positiveWords}" />
										<form:errors cssClass="error" path="positiveWords" />
									</div>
								</div>
								
								<div class="col-xxs-12 col-xs-6 mt alternate">
									<div class="input-field">
										<form:label path="negativeWords">
											<spring:message code="configuration.negativeWords" />:&nbsp;
										</form:label>
										<form:input path="negativeWords" value="${configuration.negativeWords}" />
										<form:errors cssClass="error" path="negativeWords" />
									</div>
								</div>
								
									<div class="col-xxs-12 col-xs-6 mt alternate">
									<div class="input-field">
										<form:label path="defaultBrands">
											<spring:message code="configuration.defaultBrands" />:&nbsp;
										</form:label>
										<form:input path="defaultBrands" value="${configuration.defaultBrands}" />
										<form:errors cssClass="error" path="defaultBrands" />
									</div>
								</div>
								
								<div class="col-xxs-12 col-xs-6 mt alternate">
									<div class="input-field">
										<form:label path="VAT">
											<spring:message code="configuration.VAT" />:&nbsp;
										</form:label>
										<form:input path="VAT" value="${configuration.VAT}" />
										<form:errors cssClass="error" path="VAT" />
									</div>
								</div>
								
								<div class="col-xxs-12 col-xs-6 mt alternate">
									<div class="input-field">
										<form:label path="finderLifeSpan">
											<spring:message code="configuration.finderLifeSpan" />:&nbsp;
										</form:label>
										<form:input path="finderLifeSpan" value="${configuration.finderLifeSpan}" />
										<form:errors cssClass="error" path="finderLifeSpan" />
									</div>
								</div>
								
								<div class="col-xxs-12 col-xs-6 mt alternate">
									<div class="input-field">
										<form:label path="maxFinder">
											<spring:message code="configuration.maxFinder" />:&nbsp;
										</form:label>
										<form:input path="maxFinder" value="${configuration.maxFinder}" />
										<form:errors cssClass="error" path="maxFinder" />
									</div>
								</div>

								<div class="col-xs-12">
									<input type="submit" name="save"
										value="<spring:message code="configuration.save"/>" /> 
									<input	type="button" name="cancel"
										value="<spring:message code='configuration.cancel'></spring:message>"
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
