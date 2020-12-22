var url = "http://localhost:8080/ReimbursmentSystem/";

document.getElementById("submitButton").addEventListener('click', submitLogin);

async function submitLogin() 
{
    let username = document.getElementById("usernameInput").value;
    let password = document.getElementById("passwordInput").value;

    let user = { username : username, password : password };

    let response = await fetch(url+'users/login', { method:"POST", body: JSON.stringify(user), credentials: 'include' });
    
    if(response.status===200)
    {
        roleObj = await response.json();
        if (roleObj["role"] == "EMPLOYEE")
            window.location.href = "employee.html";
        else if (roleObj["role"] == "MANAGER")
            window.location.href = "manager.html";
        else
            document.getElementById('errorDisplay').innerText="An internal error has occured. Please try again."; 
    }
    else
    {
        document.getElementById('errorDisplay').innerText="Log in failed!! Please try again."; 
    }
}