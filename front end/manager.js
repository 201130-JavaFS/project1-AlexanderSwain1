var url = "http://localhost:8080/ReimbursmentSystem/";
var reimbursements;
var filteredReimbursements;

document.getElementById("filterSelect").addEventListener('change', function()
{
    if (document.getElementById("filterSelect").value == "All")
    filteredReimbursements = reimbursements;
    else
    filteredReimbursements = reimbursements.filter((item) => { console.log(document.getElementById("filterSelect").value); console.log(item["status"]); return item["status"] === document.getElementById("filterSelect").value; });
    
    var tBody = clearAndCreateTBody();

    for (var i = 0; i < filteredReimbursements.length; i++)
    {
        var tr = tBody.insertRow(-1);
        tr.addEventListener('click', async (e) => 
                {   
                    var target = e.target.parentElement;
                    const index = Array.from(target.parentElement.children).indexOf(target);
                    let response = await fetch(url + `reimbursements/prepare_view/${filteredReimbursements[index]["id"]}`, { method:"POST", credentials: 'include' });
    
                    if (response.status === 200)
                        window.location.href = "reimbursement_view.html";
                    else
                        document.getElementById('userDisplay').innerText= "Something went wrong"; 
                });
        tr.style.backgroundColor = (i % 2 == 0 ? " #F5F5F5" : "#F5F5F5");
        tr.innerHTML = 
        `
            <td style="text-align: center; padding:15px; font-size: 18pt; color: #555555;">${ formatDate(new Date(filteredReimbursements[i]["submitted"])) }</td>
            <td style="                    padding:15px; font-size: 18pt; color: #555555;">${filteredReimbursements[i]["description"]}</td>
            <td style="text-align: center; padding:15px; font-size: 18pt; color: #555555;">$${ filteredReimbursements[i]["amount"] }</td>
            <td style="text-align: center; padding:15px; font-size: 18pt; color: #555555;">${ filteredReimbursements[i]["status"] }</td>
        `;
    }
});
document.getElementById("accountTable").addEventListener('click', function()
{
    document.getElementById("signOutButton").style.display = "block";
});
document.getElementById("signOutButton").addEventListener('click', function()
{
    window.location.href = "login.html";
});

var user;

async function fetchPageInfo() 
{
    let response = await fetch(url + 'users/validate', { method:"POST", credentials: 'include' });
    
    if(response.status===200)
    {
        user = await response.json();
        document.getElementById('userDisplay').innerText= user["first_name"] + " " + user["last_name"]; 
    }
    else
    {
        document.getElementById('userDisplay').innerText= "Something went wrong"; 
    }

    response = await fetch(url + 'reimbursements/', { method:"POST", credentials: 'include' });
    
    if (response.status === 200)
    {
        reimbursements = await response.json();
        if (reimbursements.length > 0)
        {
            filteredReimbursements = reimbursements;
            var tBody = clearAndCreateTBody();
            for (var i = 0; i < filteredReimbursements.length; i++)
            {
                var tr = tBody.insertRow(-1);
                tr.addEventListener('click', async (e) => 
                {   
                    var target = e.target.parentElement;
                    const index = Array.from(target.parentElement.children).indexOf(target);
                    let response = await fetch(url + `reimbursements/prepare_view/${filteredReimbursements[index]["id"]}`, { method:"POST", credentials: 'include' });
    
                    if (response.status === 200)
                        window.location.href = "reimbursement_view.html";
                    else
                        document.getElementById('userDisplay').innerText= "Something went wrong"; 
                });
                tr.style.backgroundColor = (i % 2 == 0 ? " #F5F5F5" : "#E3E3E3");
                tr.innerHTML = 
                `
                    <td style="text-align: center; padding:15px; font-size: 18pt; color: #555555;">${ formatDate(new Date(filteredReimbursements[i]["submitted"])) }</td>
                    <td style="                    padding:15px; font-size: 18pt; color: #555555;">${filteredReimbursements[i]["description"]}</td>
                    <td style="text-align: center; padding:15px; font-size: 18pt; color: #555555;">$${ filteredReimbursements[i]["amount"] }</td>
                    <td style="text-align: center; padding:15px; font-size: 18pt; color: #555555;">${ filteredReimbursements[i]["status"] }</td>
                `;
            }
        }

    }
    else
    {
        console.log("Something went wrong"); 
    }
}

fetchPageInfo();

function clearAndCreateTBody()
{
    document.getElementById('reimbursementsTable').getElementsByTagName('tbody')[0].remove();
    document.getElementById('reimbursementsTable').appendChild(document.createElement('tbody'));
    return document.getElementById('reimbursementsTable').getElementsByTagName('tbody')[0];
}

function formatDate(date)
{
    const months = ["Jan", "Feb", "Mar","Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"];
    return `${ months[date.getMonth()] }. ${ date.getDate() } ${ date.getFullYear() }`;
}