<%@ page import="fr.mbds.grails.User" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <g:set var="entityName" value="${message(code: 'annonce.label', default: 'Annonce')}"/>
    <title><g:message code="default.edit.label" args="[entityName]"/></title>
</head>

<body>
<a href="#edit-annonce" class="skip" tabindex="-1"><g:message code="default.link.skip.label"
                                                              default="Skip to content&hellip;"/></a>

<div class="nav" role="navigation">
    <ul>
        <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
        <li><g:link class="list" action="index"><g:message code="default.list.label" args="[entityName]"/></g:link></li>
        <li><g:link class="create" action="create"><g:message code="default.new.label"
                                                              args="[entityName]"/></g:link></li>
    </ul>
</div>

<div id="edit-annonce" class="content scaffold-edit" role="main">
    <h1><g:message code="default.edit.label" args="[entityName]"/></h1>
    <g:if test="${flash.message}">
        <div class="message" role="status">${flash.message}</div>
    </g:if>
    <g:hasErrors bean="${this.annonce}">
        <ul class="errors" role="alert">
            <g:eachError bean="${this.annonce}" var="error">
                <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message
                        error="${error}"/></li>
            </g:eachError>
        </ul>
    </g:hasErrors>



    <g:uploadForm controller="annonce" action="update" id="${annonce.id}">
        <g:hiddenField name="version" value="${this.annonce?.version}"/>

        <fieldset class="form">
            <div class="fieldcontain required">
                <label for="title">Title
                    <span class="required-indicator">*</span>
                </label>
                <input type="text" name="title" value="${annonce.title}" required="" id="title">
            </div>

            <div class="fieldcontain required">
                <label for="description">Description
                    <span class="required-indicator">*</span>
                </label>
                <input type="text" name="description" value="${annonce.description}" required="" id="description">
            </div>

            <div class="fieldcontain required">
                <label for="price">Price
                    <span class="required-indicator">*</span>
                </label>
                <input type="number decimal" name="price" value="${annonce.price}" required="" min="0.0" id="price">
            </div>

            <div class="fieldcontain">
                <label>Illustrations</label>
                <g:each in="${annonce.illustrations}" var="illustration">
                    <img type="button" class="btn bg-info" data-toggle="modal" data-target="#exampleModal" alt="${illustration.filename}" src="${grailsApplication.config.illustration.url + illustration.filename}"/>
                </g:each>
            </div>

            <div class="fieldcontain">
                <label>Upload file</label>
                <input type="file" name="file" style="display: inline">
            </div>

            <div class="fieldcontain required">
                <label for="author">Author
                    <span class="required-indicator">*</span>
                </label>
                <g:select name="author.id" from="${User.list()}" optionKey="id" optionValue="username"/>
            </div>
        </fieldset>

        <fieldset class="buttons">
            <input class="save" type="submit"
                   value="${message(code: 'default.button.update.label', default: 'Update')}"/>
        </fieldset>
    </g:uploadForm>
</div>


<div class="modal fade" id="exampleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLabel">Modifier Illustration</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <g:uploadForm controller="annonce" action="modifillu">

                    <fieldset>
                        <div class="fieldcontain">
                            <label>Upload file</label>
                            <input type="file" name="file" style="display: inline">
                        </div>
                    </fieldset>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                        <fieldset class="buttons">
                            <input class="save" type="submit"
                                   value="Enregistre"/>
                        </fieldset>

                    </div>
                </g:uploadForm>
            </div>

        </div>
    </div>
</div>
</body>
<script>
    function affichage(){
        document.write("bonjour");
    }
</script>


</html>

