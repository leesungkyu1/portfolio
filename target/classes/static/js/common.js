
const domain = "/api"

function getId(id){
    return document.querySelector(`#${id}`);
}

async function getReq(url){
    let rqResult = await fetch(domain + url);

    return await rqResult.json();
}

async function req(data, method, url){
    let rqResult = await fetch(domain+url, {
        method : method,
        headers : {
            'Content-Type' : 'application/json;charset=utf-8'
        },
        body:JSON.stringify(data)
    })
    return rqResult;
}


function tagCreater(tag, value , classNames){
    let tagEl = document.createElement(tag);
    if(value){
        tagEl.innerText = value;
    }
    if(classNames){
        tagEl.classList.add(classNames);
    }
    return tagEl;
}


