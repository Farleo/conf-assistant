$( document ).ready(function() {
    var $form = $('#' + 'customerForm');

    // SUBMIT FORM
    $("#customerForm").submit(function(event) {
        // Prevent the form from submitting via the browser.
        event.preventDefault();
        ajaxPost();
    });


    function ajaxPost(){

        // PREPARE FORM DATA
        var formData = {
            firstName : $("#firstName").val(),
            lastName :  $("#lastName").val()
        }

        // DO POST
        $.ajax({
            type : "post",
            contentType : "application/json",
            url: $form.attr('action'),
            data : JSON.stringify(formData),
            dataType : 'json'

        });


    }

})