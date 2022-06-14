const register = {
    env:{
        apiDomain : "/api/user/register"
    },

    request:{
        getReq:async()=>{
            let rqResult = await fetch(register.env.apiDomain)

            return await rqResult.json();
        },

        req:async(data, method)=>{
            let rqResult = await fetch(register.env.apiDomain, {
                method : method,
                headers : {
                    'Content-Type' : 'application/json;charset=utf-8'
                },
                body:JSON.stringify(data)
            })

            return rqResult;
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
        registerBtn.onclick=async()=>{
            if(register.func.inputCheck(inputList)){
                let reqData = {};
                reqData ={
                    id : inputList[2].value,
                    pass : inputList[3].value
                }
                let resData = await register.request.req(reqData, 'POST');
                if(resData.status === 200){
                    alert("회원가입에 성공하였습니다.");
                    location.href="/login";
                }
            }
        }
    }
}