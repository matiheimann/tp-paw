$(document).ready(function() {
    $('#popoverPost').popover();
    $("time.timeago").timeago();
    $("#searchGroup").on('input', searchGroups);
    if($(".header-button-delete-group").length) {
      $(".owned-by").addClass("margin-left-auto");
    }

    $(".group-list-component-owner").click( function(event) {
      event.stopPropagation();
    });

    if($(".sort-indicator").length == 0) {
      if($(".drop-menu").length) {
        $(".drop-menu").addClass("margin-right-160");
      }
      else {
        $(".first-logo").addClass("margin-right-160");
      }

    }

    if($(".time-indicator").length)
    {
      $(".timeFilterButton").removeClass("selectedFilter");
      var timeFilter = $.trim($(".time-indicator").text());
      switch(timeFilter)
      {
        case 'lasthour': $(".js-lhour").addClass("selectedFilter"); break;
        case 'lastday': $(".js-lday").addClass("selectedFilter"); break;
        case 'lastweek': $(".js-lweek").addClass("selectedFilter"); break;
        case 'lastmonth': $(".js-lmonth").addClass("selectedFilter"); break;
        case 'lastyear': $(".js-lyear").addClass("selectedFilter"); break;
        default: $(".js-lall").addClass("selectedFilter"); break;
      }
    }

    $(".reply-button").on('click', function() {
      var replyForm = $(this).parent().parent().find(".reply-comment-form");
      if (replyForm.is( ":hidden")) {
        replyForm.slideDown();
      } else {
        replyForm.hide();
      }
    });
});

function searchGroups() {
    var input = $("#searchGroup");
    if (input.val().length > 32)
      input.val(input.val().replace(/.$/, ''))
    input.val(input.val().replace(/[^a-zA-Z0-9]/g, ''));
    if (input.val() != "") {
      $("#searchGroupForm").attr("href", getContextPath() + "/groups?search=" +  input.val());
      $.ajax({
          type: 'GET',
          url: getContextPath() + '/searchGroup?name=' + input.val(),
          dataType: 'json',
          async: true,
          success: function(result) {
            $('#groupsFound').empty();
            $.each(result, function(index, name) {
              var option =  '<a href="'+ getContextPath() + '/group/' + name +'" class="list-group-item list-group-item-action list-group-item-light">'+ name +'</a>';
              $('#groupsFound').append(option);
            });
          },
          error: function(jqXHR, textStatus, errorThrown) {
              alert("Error: refresh page");
          }
      });
    }
    else {
      $("#searchGroupForm").attr("href", getContextPath() + "/groups");
      var results = document.getElementById('groupsFound');
      results.innerHTML = '';
    }
}
