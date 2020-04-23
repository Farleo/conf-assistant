$(document).ready(function() {
    $("#LoginForm").submit(function(event) {
        // Prevent the form from submitting via the browser.
        event.preventDefault();
        sendCredential();
    });
});

function logout(){
    var token = $('#_csrf').attr('content');
    var header = $('#_csrf_header').attr('content');
    // PREPARE FORM DATA
    var formData = new FormData();

    // DO POST
    $.ajax({
        type : "POST",
        method : "POST",
        cache : false,
        contentType : false,
        processData: false,
        url : document.location.origin + "/login-handler",
        data : formData,
        beforeSend: function(xhr) {
            xhr.setRequestHeader(header, token);
        },
        success: function(data, textStatus, xhr) {
            console.log("success");
        },
        error: function(xhr,status,error) {
            console.log("error");
        }
    });
}

function sendCredential(){
    var token = $('#_csrf').attr('content');
    var header = $('#_csrf_header').attr('content');
    // PREPARE FORM DATA
    var formData = new FormData();
    formData.append('email', $("#email").val());
    formData.append('password', $("#password").val());
    var chbox;
    chbox = document.getElementById('remember-me');
    if (chbox.checked) {
        formData.append('remember-me', $("#remember-me").val());
    }


    // DO POST
    $.ajax({
        type : "POST",
        method : "POST",
        cache : false,
        contentType : false,
        processData: false,
        url : document.location.origin + "/login-handler",
        data : formData,
        beforeSend: function(xhr) {
            xhr.setRequestHeader(header, token);
        },
        success: function(data, textStatus, xhr) {
            console.log("success");
            window.location.href = document.referrer;
        },
        error: function(xhr,status,error) {
            console.log("error");
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
        }
    });
}







