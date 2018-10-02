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
            $('#groupsFound').empty();
            $.each(result, function(index, group) {
              var option =  '<a href="'+ getContextPath() + '/group/' + group.name +'" class="list-group-item list-group-item-action list-group-item-light">'+ group.name +'</a>';
              $('#groupsFound').append(option);
            });
          },
          error: function(jqXHR, textStatus, errorThrown) {
              alert("Error: refresh page");
          }
      });
    }
    else {
      var results = document.getElementById('groupsFound');
      results.innerHTML = '';
    }
}
