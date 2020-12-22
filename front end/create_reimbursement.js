var url = "http://localhost:8080/ReimbursmentSystem/";

document.getElementById("submitButton").addEventListener('click', submitLogin);

async function submitLogin() 
{
    let amount = document.getElementById("amountInput").value;
    let description = document.getElementById("desciptionInput").value;
    let receipt = document.getElementById("receiptInput").files[0];
    let type = document.getElementById("typeSelect").value;

    let reimbursement = { amount : amount, description : description, receipt : null, type : type };

    console.log(JSON.stringify(reimbursement));
    let response = await fetch(url+'reimbursements/create', { method:"POST", body: JSON.stringify(reimbursement), credentials: 'include' });
    
    if(response.status===200)
    {
        window.location.href = "employee.html";
    }
    else
    {
        document.getElementById('failure').innerText="Internal server error: failed to create reimbursement"; 
    }
}

/*function uploadFile() {
    var blobFile = $('#filechooser').files[0];
    var formData = new FormData();
    formData.append("fileToUpload", blobFile);

    $.ajax({
       url: "upload.php",
       type: "POST",
       data: formData,
       processData: false,
       contentType: false,
       success: function(response) {
           // .. do something
       },
       error: function(jqXHR, textStatus, errorMessage) {
           console.log(errorMessage); // Optional
       }
    });
}*/