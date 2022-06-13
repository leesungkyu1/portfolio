const register = {
    env:{
        apiDomain : "/api/register"
    },

    request:{
        getReq:async(method, url)=>{
            let rqResult = await fetch(register.env.apiDomain + url)

            return await rqResult.json();
        },

        req:async(data, method, url)=>{
            let rqResult = await fetch(register.env.apiDomain + url, {
                method : method,
                contentType : 'application/json',
                body:JSON.stringify(data)
            })

            return await rqResult.json();
        }
    },
    view:{},
    func:{
        inputCheck:(inputList)=>{
            let validateFlag = false;
            for(let i=0; i<inputList.length; i++){
                let input = inputList[i];
                if(!input.value.trim()){
                    alert(input.id + "값이 비었습니다.");
                    break;
                }else if(register.getId("exampleInputPassword").value !== register.getId("exampleRepeatPassword").value){
                    alert("비밀번호를 확인해주세요");
                    break;
                }else{
                    validateFlag = true;
                }
            }
            return validateFlag;
        }
    },
    getId:(id)=>{
        return document.querySelector(`#${id}`);
    },
    register:()=>{
        let inputList = register.getId("form_tag").querySelectorAll("input");
        let registerBtn = register.getId("registerBtn");
        registerBtn.onclick=()=>{
            if(register.func.inputCheck(inputList)){
                let reqData = {};
                inputList.forEach(input => {
                    reqData[input.id] = input.value;
                })
                register.request.req(reqData, 'POST', "reigster");
            }
        }
    }
}