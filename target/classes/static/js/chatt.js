const ws = new WebSocket("ws://" + location.host + "/chat");
ws.onopen=()=>{
    chat.chatOpen();
}

ws.onerror=(ev)=>{
    console.log(ev);
}

const chat ={
    init:(type, url)=>{
        switch (type){
            case "new"
                : chat.roomMake();
                break;
            case "chat"
                :
                let roomId = url.split("/")[url.split("/").length-1];
                sessionStorage.setItem("roomId", roomId);
                let user = JSON.parse(sessionStorage.getItem("userKey"));
                let {userKey, id} = user;
                getId("mid").value = id;
                getId("mid").setAttribute("data-userkey", userKey);
                chat.chatOpen();
                break;
            case "list"
            : chat.chatList();
                break;
            default :
                alert("새로고침후 다시 시도해주세요");
                break;
        }
    },

    userInfo:()=>{
      return JSON.parse(sessionStorage.getItem("userKey"));
    },

    reqFileObjCreate:(msg, type, file)=>{
        let fileReader = new FileReader();
        let arrayBuffer = new ArrayBuffer();
        let data ={};
        data.id = getId('mid').value;
        data.chatRoomId = JSON.parse(sessionStorage.getItem("roomId"));
        data.userKey = getId('mid').getAttribute("data-userkey");
        data.fileYn = true;
        data.type = type;
        data.message = msg;
        data.date = new Date().toLocaleString();
        let temp = JSON.stringify(data);
        ws.send(temp);
        fileReader.onload=async()=>{
            arrayBuffer = fileReader.result;
            console.log(arrayBuffer);
            await ws.send(arrayBuffer); //파일 소켓 전송
            console.log("전송 완료");
        }
        fileReader.readAsArrayBuffer(file);
    },


    reqObjCreate:(msg, type)=>{
        let data ={};
        data.id = getId('mid').value;
        data.chatRoomId = JSON.parse(sessionStorage.getItem("roomId"));
        data.userKey = getId('mid').getAttribute("data-userkey");
        data.type = type;
        data.message = msg;
        data.date = new Date().toLocaleString();
        let temp = JSON.stringify(data);
        return temp;
    },

    chatList:async()=>{
       let data = await getReq("/chat");
       let chatListContainer = getId("chatListContainer");
       console.log(data);
       data.forEach(el => {
            let{name, roomKey, memberList} = el;
            let tr = document.createElement("tr");
            let roomIdTd = tagCreater("td", roomKey);
            let nameTd = tagCreater("td", name);
            let btnTd = tagCreater("td");
            let inputBtn = tagCreater("a", "입장하기", "btn");

            inputBtn.classList.add("btn-primary");
            inputBtn.href=`/chat/${roomKey}`;

            btnTd.appendChild(inputBtn);
            tr.appendChild(roomIdTd);
            tr.appendChild(nameTd);
            tr.appendChild(btnTd);
            chatListContainer.appendChild(tr);
       })
    },

    roomMake:()=>{
        console.log("room Make!!!");
        let makeBtn = getId("make_room");
        let title = getId("title");
        makeBtn.onclick=async()=>{
            if(!title.value){
                alert("채팅방 이름을 입력해주세요");
                return;
            }
            let reqData ={title : title.value, userKey : JSON.parse(sessionStorage.getItem("userKey")).userKey};
            let resData = await req(reqData, "POST", "/chat");
            if(resData.header.code === "0000"){
                alert("채팅방 생성에 성공하였습니다.");
                location.href=`/chat/list`
            }else{
                alert("채팅방 생성에 실패하엿습니다.");
                return;
            }
        }
    },

    chatOpen:async()=>{
        sessionCheck();
        ws.onopen=async()=>{
            let userInfo = chat.userInfo();
            userInfo.roomKey = sessionStorage.getItem("roomId");
            let memberJoin = await req(userInfo, "POST", "/chat/join");

            if(memberJoin.header.code === "0000"){
                let reqData = chat.reqObjCreate("","ENTER");
                sessionStorage.setItem("chatInfo", reqData);
                ws.send(reqData);
                chat.chatt();
            }else{
                alert("채팅방 진입 실패");
                return;
            }
        }
    },

    chatt:()=>{
        let talk = getId('talk');
        let msg = getId("msg");
        let btnSend = getId("btnSend");
        let mid = getId("mid");
        let data = "";
        let item = "";
        let cssClass = "";
        ws.onmessage = function(msg){
            let css;
            if(typeof msg.data == "string"){
                data = JSON.parse(msg.data);
                if(data.id === mid.value){
                    css = 'class=me';
                }else{
                    css = 'class=other';
                }
                cssClass = css;
                item = `<div ${css} >
                            <span><b>${data.id}</b></span><br/>
                          <span>${data.message}</span>
                			</div>`;

                talk.innerHTML += item;
                talk.scrollTop=talk.scrollHeight;//스크롤바 하단으로 이동
            }else{
                let urlObject = window.URL.createObjectURL(msg.data);
                item = `<div ${cssClass} >
                            <img id="test" src="${urlObject}"/>
                        </div>`;

                talk.innerHTML += item;
            }

        }

        msg.onkeyup = function(ev){
            if(ev.keyCode == 13){
                chat.send(msg);
            }
        }

        btnSend.onclick = function(){
            chat.send(msg);
        }
    },

    send:(msg)=>{
        if(msg.value.trim() != ''){
            let file = document.querySelector("#fileSelect").files[0];
            let reqData;
            if(!file){
                reqData = chat.reqObjCreate(msg.value, "CHAT");
                ws.send(reqData);
            }else{
                chat.reqFileObjCreate(msg.value, "CHAT", file);
            }
        }
        msg.value ='';
    },
}


