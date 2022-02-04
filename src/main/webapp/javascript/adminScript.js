
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
    let tHeaders = ['ID', 'Status', 'Author', 'Resolver', 'Amount', 'Description', 'Approve/Deny', 'Update'];
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
        tdID.innerHTML = r.id;
        tr.append(tdID);

        let tdStatus = document.createElement('td');
        tdStatus.innerHTML = r.status;
        tr.append(tdStatus);

        let tdAuthor = document.createElement('td');
        tdAuthor.innerHTML = r.author.username;
        tr.append(tdAuthor);

        let tdResolver = document.createElement('td');
        if(r.resolver != null) {
            tdResolver.innerHTML = r.resolver.username;
        } else {
            tdResolver.innerHTML = '';
        }
        tr.append(tdResolver);

        let tdAmount = document.createElement('td');
        tdAmount.innerHTML = r.amount.toFixed(2);
        tr.append(tdAmount);

        let tdDescription = document.createElement('td');
        tdDescription.innerHTML = r.description;
        tr.append(tdDescription);

        // Only appears if status is pending but unsure how to access select value inside condition
        if(r.status == 'PENDING') {
            let tdChangeStatus = document.createElement('td');
            let select = document.createElement('select');
            let option1 = document.createElement('option');
            let option2 = document.createElement('option');
            option1.setAttribute('value', 'APPROVED');
            option1.text = 'Approve';
            option2.setAttribute('value', 'DENIED');
            option2.text = 'Deny';
            select.appendChild(option1);
            select.appendChild(option2);
            tdChangeStatus.append(select);
            tr.append(tdChangeStatus);

            let tdUpdate = document.createElement('td');
            let updateButton = document.createElement('button');
            updateButton.onclick = function() {update(r.id, select.value , r.author.username); };

            updateButton.innerHTML = "Save";
            tdUpdate.append(updateButton);
            tr.append(tdUpdate);
        }
        // let tdChangeStatus = document.createElement('td');
        // let select = document.createElement('select');
        // let option1 = document.createElement('option');
        // let option2 = document.createElement('option');
        // select.id = 'newStatus';
        // option1.setAttribute('value', 'APPROVED');
        // option1.text = 'Approve';
        // option2.setAttribute('value', 'DENIED');
        // option2.text = 'Deny';
        // select.appendChild(option1);
        // select.appendChild(option2);
        // tdChangeStatus.append(select);
        // tr.append(tdChangeStatus);

        // let tdUpdate = document.createElement('td');
        // let updateButton = document.createElement('button');
        // updateButton.onclick = function() {update(r.id, select.value , r.author.username); };

        // updateButton.innerHTML = "Save";
        // tdUpdate.append(updateButton);
        // tr.append(tdUpdate);

        reimbursementTable.append(tr);
    }
    reimbursementDiv.append(reimbursementTable);
}

function update(id, status, username) {
    let data = {
        id: id,
        status: status, 
        username: username
    }
    let json = JSON.stringify(data);

    let xhr = new XMLHttpRequest();

    xhr.onreadystatechange = function() {
        if(this.readyState == 4 && this.status == 200) {
            let r = this.responseText;
        }
    }
    xhr.open('post', 'http://localhost:8080/edit', true);
    xhr.setRequestHeader('Content-Type', 'application/json');
    xhr.send(json);
}