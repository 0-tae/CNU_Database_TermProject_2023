
const popup_form = document.getElementById("popup_background");
const register_button =document.getElementById("button_register");
const login_button =document.getElementById("button_login");
const cancle_button=document.getElementById("button_cancle");
const button_session=document.getElementById("button_session");
const login_zone=document.getElementById("zone_login");
const box_printZone=document.getElementById("box_printZone");
const box_reserveZone=document.getElementById("box_reserveZone");
const box_returnZone=document.getElementById("box_returnZone");
const box_previousRentZone=document.getElementById("box_previousRentZone");
const box_currentRentZone=document.getElementById("box_currentRentZone");
const box_tmiZone=document.getElementById("box_tmiZone");
const box_sessionZone = document.getElementById("session_zone");
const button_search=document.getElementById("button_search");


let check_entire=document.getElementById("entire");
let check_electric=document.getElementById("electric");
let check_small=document.getElementById("small");
let check_big=document.getElementById("big");
let check_suv=document.getElementById("suv");
let check_hop=document.getElementById("hop");
let date_rentStart=document.getElementById("date_rentStart")
let date_rentEnd=document.getElementById("date_rentEnd")


let check_boxes=[check_entire,check_electric,
                                check_small,check_big,
                                check_suv,check_hop]


function register_exit() {
    popup_form.className = "hidableClass";
}

function register_popup() {
    popup_form.classList.remove("hidableClass");
}

function login(){
    let request={}
    request["cno"]=document.getElementById("id_value").value;
    request["passwd"]=document.getElementById("pwd_value").value;
    $.ajax({
        url: "/main/login",
        type: 'POST',
        dataType: 'text', // 돌려받을 타입, text로 받으면 JSON.parse로 해결한다.
        contentType: "application/json", // 전해줄 타입
        data: JSON.stringify(request), // 일단 json을 String으로 보낸다
        async : true,
        headers: {
            "X-Requested-With": "XMLHttpRequest"
        },
        success: function(data){
            login_zone.innerHTML="로그인 정보: "+data;
        }
    });
}

function search(){
    check_boxes.forEach(function (box){
        if(box.checked){
            let request={};
            request["startDate"]=date_rentStart.value
            request["startEnd"]=date_rentEnd.value
            request["vehicleType"]=box.name

            $.ajax({
                url: "/rent/search",
                type: 'GET',
                dataType: 'json', // 돌려받을 타입, text로 받으면 JSON.parse로 해결한다.
                contentType: "application/json", // 전해줄 타입
                data: JSON.stringify(request), // 일단 json을 String으로 보낸다
                async : true,
                success: function(data){
                    console.log(JSON.parse(data));
                }
            });
        }
    })
}


function view_rentcar(filter_word){
    $.ajax({
        type: "POST",
        url: "/main/view",
        dataType: "json",
        async : true,
        data: filter_word,
        success: function(data){
            print_rentcar(data);
        }
    });
}

function print_rentcar(givenParsedJSONData) {
    box_printZone.innerHTML = "";
    const tableTitle = $(
        `
        <tr>
         <th id="th_1">차종</th>
         <th id="th_2">모델명</th>
         </tr>
        `
    );
    box_printZone.appendChild(tableTitle[0]);
    givenParsedJSONData.forEach(data => {
        const myElement = $(
            `<tr class="playMovieInfo">
          <td>${data.MODELNAME}</td>
          <td>${data.DATARETRUNED}</td>
        </tr>`
        );

        box_printZone.appendChild(myElement[0]);
    });
}

function session_check(){
    $.ajax({
        url: "/session_check",
        type: 'POST',
        async : true,
        headers: {
            "X-Requested-With": "XMLHttpRequest"
        },
        success: function(data){
            console.log(data);
            box_sessionZone.innerHTML=data;
        }
    });
}

login_button.addEventListener("click", login);
register_button.addEventListener("click", register_popup);
cancle_button.addEventListener("click",register_exit);
button_session.addEventListener("click",session_check);
button_search.addEventListener("click",search);