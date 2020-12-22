const server_url = "http://localhost:8080/ReimbursmentSystem/";
const id = new URLSearchParams(window.location.search).get("id");

var reimbursement;

async function fetchReimbursement() 
{
    let response = await fetch(server_url + `reimbursements/get_prepared_view`, { method:"GET", credentials: 'include' });
    
    if(response.status===200)
    {
        reimbursement = await response.json();
        document.getElementById('idCell').innerText= reimbursement["id"];
        document.getElementById('amountCell').innerText= `$${ reimbursement["amount"] }`;
        document.getElementById('submittedCell').innerText= formatDate(reimbursement["submitted"]);
        document.getElementById('resolvedCell').innerText= formatDate(reimbursement["resolved"]);
        document.getElementById('descriptionCell').innerText= reimbursement["description"];
        document.getElementById('receiptCell').innerText= "null";
        document.getElementById('authorCell').innerText= reimbursement["author_fullname"];
        document.getElementById('resolverCell').innerText= reimbursement["resolver_fullname"];
        document.getElementById('statusCell').innerText= reimbursement["status"];
        document.getElementById('typeCell').innerText= reimbursement["type"];
        
    }
    else
    {
        document.getElementById('failure').style.display = "block";
    }

    response = await fetch(server_url + 'users/validate', { method:"POST", credentials: 'include' });
    
    if(response.status===200)
    {
        user = await response.json();
        console.log(reimbursement["status"]);
        if (user["role"] == "Manager" && reimbursement["status"] == "Pending")
        {
            document.getElementById("buttonTable").style.display = "inline-table";
        }
    }
    else
    {
        document.getElementById('userDisplay').innerText= "Something went wrong"; 
    }
}

document.getElementById("approveButton").addEventListener('click', async () => 
{
    console.log(reimbursement);
    let response = await fetch(server_url + `reimbursements/approve`, { method:"POST", body: JSON.stringify(reimbursement), credentials: 'include' });
    
    if(response.status===200)
    {  
        window.location.href = "manager.html";
    }
    else
    {
        document.getElementById('failure').style.display = "block";
    }
});

document.getElementById("denyButton").addEventListener('click', async () => 
{
    let response = await fetch(server_url + `reimbursements/deny`, { method:"POST", body: JSON.stringify(reimbursement), credentials: 'include' });
    
    if(response.status===200)
    {  
        window.location.href = "manager.html";
    }
    else
    {
        document.getElementById('failure').style.display = "block";
    }
});

function formatDate(inputDate)
{
    if (inputDate == null)
        return "null"; 
        
    var date = new Date(inputDate);
    if (date == null)
        return "null";
    const months = ["Jan", "Feb", "Mar","Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"];
    return `${ months[date.getMonth()] }. ${ date.getDate() } ${ date.getFullYear() }`;
}

fetchReimbursement();
