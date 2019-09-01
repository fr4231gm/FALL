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

<form:form action="${actionURI}" modelAttribute="fixUpTask">


	<spring:message var="date-format" code="fixUpTask.date-format" />

	<div class="fh5co-cover" data-stellar-background-ratio="0.5">
		<div class="desc">
			<div class="col-sm-8 mt">
				<div class="tabulation animate-box">
					<ul class="nav nav-tabs">
						<li class="active"><a data-toggle="tab">Ticker:
								<jstl:out value="${fixUpTask.ticker}" />
						</a></li>
					</ul>
					<div class="tab-content">
						<div class="tab-pane active" id="flights">
							<div class="row">
								<div class="col-xxs-12 col-xs-6 mt alternate">
									<div class="input-field">
										<form:label path="description" readonly = "true">
											<spring:message code="fixUpTask.description" />:&nbsp;
		</form:label>
										<form:textarea path="description"
											value="${fixUpTask.description}" placeholder="Description"
											rows="4" readonly = "true"/>
										<form:errors path="description" cssClass="error" />
									</div>
								</div>
								<div class="col-xxs-12 col-xs-6 mt alternate">
									<div class="input-field">
										<form:label path="startDate" readonly = "true">
											<spring:message code="fixUpTask.start-date" />:&nbsp;
		</form:label>

										<form:input path="startDate" value="${fixUpTask.startDate}" format="{0,date,yyyy-MM-dd}"
											placeholder="dd/mm/yyyy" readonly = "true"/>
										<form:errors path="startDate" cssClass="error" />
										<br />
									</div>
								</div>

								<div class="col-xxs-12 col-xs-6 mt alternate">
									<div class="input-field">
										<form:label path="endDate" readonly = "true">
											<spring:message code="fixUpTask.end-date" />:&nbsp;
		</form:label>
										<form:input path="endDate" value="${fixUpTask.endDate}" format="{0,date,yyyy-MM-dd}"
											placeholder="dd/mm/yyyy" readonly = "true" />
										<form:errors path="endDate" cssClass="error" />
									</div>
								</div>

								<div class="col-xxs-12 col-xs-6 mt alternate">
									<div class="input-field">
										<form:label path="maximumPrice" readonly = "true">
											<spring:message code="fixUpTask.maximum-price" />:&nbsp;
		</form:label>
										<form:input path="maximumPrice"
											value="${fixUpTask.maximumPrice}" readonly = "true"/>
										<form:errors path="maximumPrice" cssClass="error" />
									</div>
								</div>
								<div class="col-xxs-12 col-xs-6 mt alternate">
									<div class="input-field">
										<form:label path="address" >
											<spring:message code="fixUpTask.address" />:&nbsp;
		</form:label>
										<form:input path="address" value="${fixUpTask.address}" readonly = "true"/>
										<form:errors path="address" cssClass="error" />
									</div>
								</div>

								<div class="col-xxs-12 col-xs-6 mt">
									<section>
										<form:label path="category">
											<spring:message code="fixUpTask.category" />:&nbsp;
		</form:label>
										<form:select id="categories" path="category" readonly = "true">
											<form:options items="${categories}" itemLabel="name"
												itemValue="id" />
											<form:option value="${fixUpTask.category.id}"
												label="${fixUpTask.category.name}" />
										</form:select>
									</section>
								</div>
								<div class="col-xxs-12 col-xs-6 mt">
									<section>
										<form:label path="warranty" >
											<spring:message code="fixUpTask.warranty" />:&nbsp;
		</form:label>
										<form:select id="warranties" path="warranty">
											<form:options items="${warranties}" itemLabel="title"
												itemValue="id" class="cs-select cs-skin-border" />
											<form:option value="${fixUpTask.warranty.id}"
												label="${fixUpTask.warranty.title}" />
										</form:select>
									</section>
								</div>
								<div class="col-xs-12">
								<input
										type="button" name="cancel"
										value="<spring:message code='fixUpTask.cancel'></spring:message>"
										onclick="javascript: relativeRedir('${cancelURI}');" />
								<input  type="button" name="seeprofile"
										value="<spring:message code='fixUpTask.seeprofile'></spring:message>"
										onclick="javascript: relativeRedir('fixUpTask/list.do?customerId=${fixUpTask.customer.id}');" />
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

</form:form>















