
const popup_form = document.getElementById("popup_background");
const register_button =document.getElementById("button_register");
const login_button =document.getElementById("button_login");
const cancle_button=document.getElementById("button_cancle");
const login_zone=document.getElementById("zone_login");
const select_car=document.getElementById("select_car");
const box_printZone=document.getElementById("box_printZone");
const box_reserveZone=document.getElementById("box_reserveZone");
const box_returnZone=document.getElementById("box_returnZone");
const box_previousRentZone=document.getElementById("box_previousRentZone");
const box_currentRentZone=document.getElementById("box_currentRentZone");
const box_tmiZone=document.getElementById("box_tmiZone");

const id_value=document.getElementById("cno").value;
const pwd_value=document.getElementById("passwd").value;


function register_exit() {
    popup_form.className = "hidableClass";
}

function register_popup() {
    popup_form.classList.remove("hidableClass");
}

function login(){
    $.ajax({
        url: "http://localhost:8080/main/login",
        type: 'POST',
        contentType: 'application/json',
        data: {"cno":id_value, "passwd":pwd_value},
        async : true,
        success: function(data){
            console.log(data);
            login_zone.innerHTML=data.cno;
        }
    });
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

login_button.addEventListener("click", login);
register_button.addEventListener("click", register_popup);
cancle_button.addEventListener("click",register_exit);
select_car.addEventListener("change",view_rentcar);