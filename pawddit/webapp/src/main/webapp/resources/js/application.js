$(document).ready(function() {
    $('#popoverPost').popover();
    $("time.timeago").timeago();
    $("#searchGroup").on('input', searchGroups);
    if($(".header-button-delete-group").length)
    {
      $(".owned-by").addClass("margin-left-auto");
    }
});

function searchGroups() {
    if ($("#searchGroup").val() != "") {
      $("#searchGroupForm").attr("href", getContextPath() + "/group/" + $("#searchGroup").val());
      $.ajax({
          type: 'GET',
          url: getContextPath() + '/searchGroup/' + $("#searchGroup").val(),
          dataType: 'json',
          async: true,
          success: function(result) {
            var dataList = document.getElementById('groupsFound')
            while (dataList.firstChild) {
              dataList.removeChild(dataList.firstChild);
            }
            $.each(result, function(index, group) {
              var option = document.createElement('option');
              option.value = group.name;
              dataList.appendChild(option);
            });
          },
          error: function(jqXHR, textStatus, errorThrown) {
              alert("Error: refresh page");
          }
      });
    }
    else {
      var dataList = document.getElementById('groupsFound');
      while (dataList.firstChild) {
        dataList.removeChild(dataList.firstChild);
      }
    }
}
