const processRegistration = function(e) {
    let registrationForm = document.getElementById("registration-form");

    // check match password
    let password = document.getElementById("password").value;
    let rePassword = document.getElementById("re-password").value;
    console.log(password, rePassword);
    if (password != rePassword) {
        alert("Password does not match");
        e.preventDefault();
    }
}

window.onload = function() {
    let registrationForm = document.getElementById("registration-form");

    registrationForm.addEventListener("submit", processRegistration);
}