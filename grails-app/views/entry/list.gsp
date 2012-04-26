
<%@ page import="blogito.Entry" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'entry.label', default: 'Entry')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
<g:if test="${session.user}">
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
</g:if>
<g:if test="${flash.message}">
  <div class="message" role="status">${flash.message}</div>
</g:if>      
	  <div class="body">
		<div class="list">
          <g:each in="${entryInstanceList}" status="i" var="entryInstance">
            <g:render template="entry" bean="${entryInstance}" var="entryInstance" />
          </g:each>
		</div>
      </div>
      <div class="pagination">
        <g:paginate total="${entryCount}" params="${flash}" />
      </div>
	</body>
</html>
