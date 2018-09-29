$(document).ready(function() {
  $('.post-hover').hover(
       function(){ $(this).parent().addClass('border-hover') },
       function(){ $(this).parent().removeClass('border-hover') }
  )
});
