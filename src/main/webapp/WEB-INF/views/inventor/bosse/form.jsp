<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="urn:jsptagdir:/WEB-INF/tags"%>

<acme:form>
	<acme:input-textbox code="inventor.chimpum.form.label.title" path="subject" />
	<acme:input-textbox code="inventor.chimpum.form.label.code" path="code" placeholder="yy/mm/dd" />
	<jstl:if test="${isNew == false}">
	<acme:input-moment code="inventor.chimpum.form.label.creationMoment" path="creationMoment" readonly="true"/>
	</jstl:if>
	<acme:input-textarea code="inventor.chimpum.form.label.description" path="summary" />
	<acme:input-moment code="inventor.chimpum.form.label.period" path="period" />
	<acme:input-money code="inventor.chimpum.form.label.budget" path="income" />
	<acme:input-url code="inventor.chimpum.form.label.link" path="moreInfo" />
	<acme:input-select code="inventor.chimpum.form.label.artifact" path="artifactId">
	<jstl:forEach items="${artifact}" var="artifact">
			<acme:input-option code="${artifact.code}" value="${artifact.getId()}" selected="${artifact.getId() == artifactId}"/>
	</jstl:forEach>
	</acme:input-select>
	<jstl:choose>
		<jstl:when test="${isNew == true}">
			<acme:submit code="inventor.chimpum.form.label.create" action="/inventor/bosse/create" />
		</jstl:when>
		<jstl:otherwise>
				<acme:submit code="inventor.chimpum.form.label.update"
					action="/inventor/bosse/update" />
				<acme:submit code="inventor.chimpum.form.label.delete"
					action="/inventor/bosse/delete" />
		</jstl:otherwise>
	</jstl:choose>
</acme:form>
