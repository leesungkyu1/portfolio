const board ={
    envMan:{
      apiDomain : '/api/board',
      moveDomain : '/board'
    },
    requestMan : {
        boardInsert:async(data)=> {
            console.log(data);
            let rqResult = await fetch(board.envMan.apiDomain, {
                method : "POST",
                headers : {
                    'Content-Type' : 'application/json;charset=utf-8'
                },
                body : JSON.stringify(data)
            })

            let result = await rqResult.text();
            if(result == 200){
                alert("게시글 등록에 성공하였습니다.");

                location.href='/boards';
            }
        },
        boardSelect:async(page, searchData)=>{
            let rqResult;
            if(searchData){
                rqResult = await fetch(board.envMan.apiDomain + `/list/${searchData.searchKeyword}`)
            }else{
                rqResult = await fetch(board.envMan.apiDomain + "/list");
            }

            let result = await rqResult.json();

            return result;
        },
        boardDetailSelect:async(boardKey)=>{
            console.log(boardKey);
            let rqResult = await fetch(board.envMan.apiDomain + `/${boardKey}`);
            let result = await rqResult.json();

            return result;
        },

        boardUpdate:async(data)=>{
            let rqResult = await fetch(board.envMan.apiDomain, {
                method: "PUT",
                headers : {
                    'Content-Type' : 'application/json;charset=utf-8'
                },
                body : JSON.stringify(data)
            })
            if(rqResult.status === 200){
                alert("수정에 성공했습니다!!");
                location.reload();
            }
        }
    },
    viewMan :{
        boardSelect:(data)=>{
            let contentContainer = document.querySelector("#content_container");
            let odd = document.querySelector(".odd");
            if(odd){
                contentContainer.removeChild(odd);
            }

            board.funcMan.logout();

            data.forEach(el => {
                let tr = board.funcMan.tagCreater("tr");
                let title = board.funcMan.tagCreater("td", el.title);
                let content = board.funcMan.tagCreater("td", el.content);
                let writer = board.funcMan.tagCreater("td", el.writer);
                let openYn = board.funcMan.tagCreater("td", el.openYn);
                let registerDate = board.funcMan.tagCreater("td", el.registerDate);

                tr.appendChild(title);
                tr.appendChild(content);
                tr.appendChild(writer);
                tr.appendChild(openYn);
                tr.appendChild(registerDate);

                contentContainer.appendChild(tr);

                tr.style.cursor="pointer";
                tr.onclick=()=>{
                    location.href=`/board/${el.boardKey}`;
                }
            })
        },

        detailSelect:(data)=>{
            let {boardKey, title, content, writer, openYn, registerDate} = data;
            let titleEl = document.querySelector("#title");
            let contentEl = document.querySelector("#contents");
            let writerEl = document.querySelector(".writer");
            let registerEl = document.querySelector("#register_date");
            let checkBoxEl = document.querySelectorAll("input[name='main_open_yn']");
            let updateBtn = document.querySelector("#btn_update");
            let updateEndBtn = document.querySelector("#btn_end_update");
            let openY = document.querySelector("#open");
            let keyArr = [titleEl, contentEl, checkBoxEl];
            board.funcMan.inputContrl(keyArr, true);
            titleEl.value=title;
            contentEl.value = content;
            writerEl.value = writer;
            openYn === "Y" ? checkBoxEl[0].checked=true : checkBoxEl[1].checked=true;
            registerEl.innerText = registerDate.substring(0,11);

            document.querySelector("#btn_insert").style.display="none";
            updateBtn.style.display="";
            updateBtn.onclick=()=>{
                board.funcMan.inputContrl(keyArr, false);
                updateBtn.style.display="none";
                updateEndBtn.style.display="";
                updateEndBtn.onclick=()=>{
                    console.log('click');
                    let reqData = {
                        boardKey,
                        title : titleEl.value,
                        content : contentEl.value,
                        openYn : openY.checked ? "Y" : "N"
                    };

                    board.requestMan.boardUpdate(reqData);
                }
            }
        }
    },
    funcMan : {
        tagCreater:(tag, value , classNames)=>{
            let tagEl = document.createElement(tag);
            if(value){
                tagEl.innerText = value;
            }
            if(classNames){
                tagEl.classList.add(classNames);
            }
            return tagEl;
        },

        inputContrl:(tagArr, flag)=>{
            tagArr.forEach(key => {
                if(key.length === 2){
                    key.forEach(check =>{
                        check.disabled = flag;
                    })
                }else{
                    key.disabled = flag;
                }
            })
        },

        logout:()=>{
            document.querySelector(".dropdown-divider").nextElementSibling.onclick=async()=>{
                let rqResult = await fetch('/logout');
                alert("로그아웃에 성공하였습니다!!!");
                sessionStorage.clear();
                location.href=await rqResult.url;
            }
        }

    },

    init :{
        boardInit:(url)=>{
            if(url.split('/').length === 5){
                board.boardDetail(url);
            }else{
                board.boardInsert();
            }
        },
    },

    boardInsert:()=> {
        let contentList = document.querySelector("#dataTable2").querySelectorAll("input");
        let insertSwitch = false;
        document.querySelector(".writer").innerText = "흠흠흠";
        document.querySelector("#btn_insert").onclick=async()=> {
            for(let i=0; i<contentList.length; i++){
                if(!contentList[i].value){
                    insertSwitch = false;
                    break;
                }else{
                    insertSwitch = true;
                }
            }

            if(insertSwitch){
                let insertData = {};
                insertData = {
                    "title" : document.querySelector("#title").value,
                    "content" : document.querySelector("#contents").value,
                    "writer" : 1,
                    // "writer" : document.querySelector(".writer").innerText,
                    "openYn" : document.querySelector("#open").checked ? "Y" : "N"
                }

                await board.requestMan.boardInsert(insertData);
            }
        }
    },

    boardDetail:async(url)=>{
        let data = await board.requestMan.boardDetailSelect(url.split('/')[url.split('/').length-1]);

        board.viewMan.detailSelect(data);

    },

    boardSelect:async()=>{
        let data = await board.requestMan.boardSelect();

        board.viewMan.boardSelect(data);
    }
}