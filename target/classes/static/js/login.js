const login = {
    login:()=>{
        let loginBtn = document.querySelector("#login_btn");
        loginBtn.onclick=async()=>{
            let validate = false;
            let inputs = document.querySelectorAll(".form-control-user");
            for(let i=0; i<inputs.length; i++){
                let input = inputs[i];
                if(!input.value.trim()){
                    alert("아이디나 비밀번호를 확인해주세요");
                    return;
                }else{
                    validate = true;
                }
            }

            if(validate){
                let formData = new FormData;
                formData.append("id", inputs[0].value);
                formData.append("pass", inputs[1].value);
                let resData = await login.postReq(formData);
                if(resData.status === 200){
                    alert("로그인에 성공하였습니다.");
                    sessionStorage.setItem("userKey", JSON.stringify(await resData.json()));
                    location.href="/";
                }else{
                    alert("아이디 및 비밀번호를 확인해주세요");
                }
            }
        }
    },

    postReq:async(data)=>{
        let rqResult = await fetch("http://localhost:8080/api/user/loginCheck", {
            method :"POST",
            body : data
        })

        return rqResult;
    }
}