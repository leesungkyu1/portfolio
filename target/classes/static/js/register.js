const register = {
    env:{
        apiDomain : "/api/user/register"
    },
    func:{
        inputCheck:(inputList)=>{
            let validateFlag = false;
            for(let i=0; i<inputList.length; i++){
                let input = inputList[i];
                if(!input.value.trim()){
                    alert(input.id + "값이 비었습니다.");
                    break;
                }else if(getId("exampleInputPassword").value !== getId("exampleRepeatPassword").value){
                    alert("비밀번호를 확인해주세요");
                    break;
                }else{
                    validateFlag = true;
                }
            }
            return validateFlag;
        }
    },

    register:()=>{
        let inputList = getId("form_tag").querySelectorAll("input");
        let registerBtn = getId("registerBtn");
        registerBtn.onclick=async()=>{
            if(register.func.inputCheck(inputList)){
                let reqData = {};
                reqData ={
                    id : inputList[2].value,
                    pass : inputList[3].value
                }
                let resData = await req(reqData, 'POST');
                if(resData.status === 200){
                    alert("회원가입에 성공하였습니다.");
                    location.href="/login";
                }
            }
        }
    }
}