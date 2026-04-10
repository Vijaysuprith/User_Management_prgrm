// REGISTER
function register() {
  fetch("/users/register", {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({
      username: document.getElementById("username").value,
      email: document.getElementById("email").value,
      password: document.getElementById("password").value,
      mobile: document.getElementById("mobile").value,
      gender: document.getElementById("gender").value,
      role: "USER",
      dob: document.getElementById("dob").value,
      address: document.getElementById("address").value
    })
  })
  .then(res => res.text())
  .then(data => alert(data));
}

// SEND OTP
function sendOtp() {
  let email = document.getElementById("email").value;

  fetch(`/users/forgot?email=${email}`, {
    method: "POST"
  })
  .then(res => res.text())
  .then(data => alert(data));
}

// RESET PASSWORD
function resetPass() {
  let email = document.getElementById("email").value;
  let otp = document.getElementById("otp").value;
  let newPass = document.getElementById("newPass").value;

  fetch(`/users/reset?email=${email}&otp=${otp}&newPassword=${newPass}`, {
    method: "POST"
  })
  .then(res => res.text())
  .then(data => alert(data));
}

// LOAD USERS
function loadUsers() {
  fetch("/users/all")
    .then(res => res.json())
    .then(data => {
      let table = document.getElementById("usersTable");
      table.innerHTML = "";

      data.forEach(user => {
        table.innerHTML += `
          <tr>
            <td>${user.userId}</td>
            <td>${user.username}</td>
            <td>${user.email}</td>
            <td>${user.gender}</td>
            <td>${user.role}</td>
            <td>
              <button onclick="deleteUser(${user.userId})">Delete</button>
            </td>
          </tr>
        `;
      });
    });
}

// DELETE USER
function deleteUser(id) {
  fetch(`/users/${id}`, { method: "DELETE" })
    .then(res => res.text())
    .then(() => loadUsers());
}