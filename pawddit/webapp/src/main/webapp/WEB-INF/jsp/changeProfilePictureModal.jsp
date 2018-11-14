
<div class="modal fade" id="changeProfileImageModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="exampleModalLabel">
          <spring:message code="user.modal.changeImage.message"/>
        </h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <form:form modelAttribute="changeProfilePictureForm" action="${postPath}" method="post" accept-charset="UTF-8" enctype="multipart/form-data">
        <div class="modal-body">
          <c:if test="${empty userProfile.imageid}">
            <div class="user-image-container-in-modal-no-picture">
              <i class="fas fa-user fa-3x"></i>
            </div>
          </c:if>
          <c:if test="${!empty userProfile.imageid}">
            <div class="justify-content-center">
              <div class="user-image-container-in-modal">
                <img class="profile-picture" src="<c:url value="/image/${userProfile.imageid}"/>" />
              </div>
            </div>
          </c:if>
              <div class="form-group">
                <form:label for='profile-picture' path="file">

                </form:label>
              <br>
                <form:input type="file" path="file" id='profile-image' accept="image/png, image/jpeg"/>
                <form:errors path="file" cssClass="formError" element="p"/>
              </div>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn app-btn-secondary" data-dismiss="modal">
            <spring:message code="user.modal.close.message" />
          </button>
          <button type="submit" class="btn app-btn-primary">
            <spring:message code="user.modal.changeImageConfirmation.message" />
          </button>
        </div>
    </form:form>
    </div>
  </div>
</div>
