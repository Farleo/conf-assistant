$( document ).ready(function() {

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
            type : "POST",
            contentType : "application/json",
            url : "http://localhost:8080/save",
            data : JSON.stringify(formData),
            dataType : 'json'

        });


    }

})