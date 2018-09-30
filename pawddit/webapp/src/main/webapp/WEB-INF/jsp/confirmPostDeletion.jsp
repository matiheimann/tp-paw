<div class="modal fade" id="confirmPostDeletion" tabindex="-1" role="dialog" aria-labelledby="confirmPostDeletion" aria-hidden="true">
  <div class="modal-dialog modal-dialog-centered" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title"><spring:message code="postConfirmDelete.title"/></h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-footer-buttons">
        <c:url value="/group/${group.name}/${post.postid}/delete" var="postPath"/>
        <form:form action="${postPath}" method="post">
          <button class="app-btn-secondary modal-footer-button" data-dismiss="modal"><spring:message code="postConfirmDelete.cancel.button.message"/></button>
          <button class="app-btn-primary" type="submit"><spring:message code="postConfirmDelete.delete.button.message"/></button>
        </form:form>
      </div>
    </div>
  </div>
</div>
