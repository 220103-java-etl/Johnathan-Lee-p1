
document.addEventListener("DOMContentLoaded", function() {
    getData()
});

async function getData() {
    let res = await fetch("http://localhost:8080/reimbursements");

    if(res.status = 200) {
        let data = await res.json()
        .then(
            result => populateData(result)
        );
    }
}

function populateData(res) {

    let reimbursementDiv = document.getElementById('reimbursements');
    
    let reimbursementTable = document.createElement('table');
    reimbursementTable.setAttribute('class', 'table');

    let tHead = document.createElement('thead');
    let tableHeaderRow = document.createElement('tr');
    let tHeaders = ['ID', 'Status', 'Author', 'Resolver', 'Amount', 'Description'];
    for(let h of tHeaders) {
        let th = document.createElement('th');
        th.setAttribute('scope', 'col');
        th.innerHTML = h;
        tableHeaderRow.append(th);
    }
    tHead.append(tableHeaderRow);
    reimbursementTable.append(tHead);

    for(let r of res) {
        let tr = document.createElement('tr');

        let tdID = document.createElement('td');
        tdID.innerHTML = r.author.id;
        tr.append(tdID);

        let tdStatus = document.createElement('td');
        tdStatus.innerHTML = r.status;
        tr.append(tdStatus);

        let tdAuthor = document.createElement('td');
        tdAuthor.innerHTML = r.author.username;
        tr.append(tdAuthor);

        let tdResolver = document.createElement('td');
        tdResolver.innerHTML = r.resolver;
        tr.append(tdResolver);

        console.log(r.remainingReimbursement);
        let tdAmount = document.createElement('td');
        tdAmount.innerHTML = r.amount;
        tr.append(tdAmount);

        let tdDescription = document.createElement('td');
        tdDescription.innerHTML = r.description;
        tr.append(tdDescription);

        reimbursementTable.append(tr);
    }
    reimbursementDiv.append(reimbursementTable);
}