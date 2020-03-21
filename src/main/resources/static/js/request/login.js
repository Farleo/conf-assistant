$(document).ready(function() {
    $('#next').click(function () {
        sendCredential();
    });
});

function sendCredential(){
    // PREPARE FORM DATA
    var formData = new FormData();
    formData.append('email', $("#email").val());
    formData.append('password', $("#password").val());

    // DO POST
    $.ajax({
        type : "POST",
        method : "POST",
        cache : false,
        contentType : false,
        processData: false,
        url : "http://localhost:8080/login-handler",
        data : formData,
        success: function(data, textStatus, xhr) {
            window.location.href = document.referrer;
        },
        error: function(xhr,status,error) {
            $('#loginGroup').empty();
            $('#badCredential').empty();
            if (xhr.status === 401) {
                var variable = "<input id='email' style='border-style: outset; margin-top: 20px' type='hidden' value='" + formData.get('email') + "' class='form-control' name='email' placeholder='Enter email to proceed'>" +
                    "<input id='password' style='border-style: outset; margin-top: 20px' type='password' class='form-control' name='password' placeholder='Enter password to proceed'>"
                $('#loginGroup').append(variable);
            }
            if (xhr.status === 400) {
                var variable = "<input id='email' style='border-style: outset; margin-top: 20px' type='email' class='form-control' name='email' placeholder='Enter email to proceed'>" +
                    "<input id='password' style='border-style: outset; margin-top: 20px' type='hidden' class='form-control' name='password' placeholder='Enter password to proceed'>"
                $('#loginGroup').append(variable);
                var badCredential = "<div class=\"alert alert-danger\" role=\"alert\">Bad credential!</div>";
                $('#badCredential').append(badCredential);
            }
            $('#next').click(function (event) {
                event.preventDefault();
                sendCredential();
            });
        }
    });
}







