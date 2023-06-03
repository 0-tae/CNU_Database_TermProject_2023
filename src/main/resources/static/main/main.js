
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
const button_search=document.getElementById("button_search");


let check_entire=document.getElementById("entire");
let check_electric=document.getElementById("electric");
let check_small=document.getElementById("small");
let check_huge=document.getElementById("huge");
let check_suv=document.getElementById("suv");
let check_hop=document.getElementById("hop");
let date_rentStart=document.getElementById("date_rentStart")
let date_rentEnd=document.getElementById("date_rentEnd")


let check_boxes=[check_entire,
                                check_small,check_huge,
                                check_suv,check_hop]

let username=null;

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
            username=request["cno"];
            session_check();
        }
    });
}

function search(){
    box_printZone.innerHTML = "";
    check_boxes.forEach(function (box){
        if(check_boxes[0].checked && box.name!=="entire"){
            return;
        } else if(box.checked){
            let request={};
            request["startDate"]=date_rentStart.value
            request["endDate"]=date_rentEnd.value
            request["vehicleType"]=box.name

            $.ajax({
                url: "/rent/search",
                type: 'GET',
                dataType: 'text', // 돌려받을 타입, text로 받으면 JSON.parse로 해결한다.
                data: {startDate : date_rentStart.value,
                        endDate : date_rentEnd.value,
                        vehicleType : box.name},
                async : true,
                success: function(data){
                    console.log(data);
                    let givenData=JSON.parse(data);
                    print_availableRentCar(givenData,date_rentStart.value,date_rentEnd.value);
                }
            });
        }
    })
}

function reserve(e){
    let target=e.target;
    let request={};
    request["licensePlateNo"]=target.id;
    request["startDate"]=target.getAttribute("startDate");
    request["endDate"]=target.getAttribute("endDate");
    request["cno"]=username;

    $.ajax({
        url: "/reserve/save",
        type: 'POST',
        dataType: 'text', // 돌려받을 타입, text로 받으면 JSON.parse로 해결한다.
        contentType: "application/json", // 전해줄 타입
        data: JSON.stringify(request),
        async : true,
        success: function(data){
            let givenData=JSON.parse(data);
            print_reservationList(givenData);
            e.target.removeEventListener("click",reserve);
            box_printZone.removeChild(e.target.parentNode);
        }
    });
}
function cancel(e){
    const target=e.target;
    let request={};
    request["licensePlateNo"]=target.id;
    request["startDate"]=target.getAttribute("startDate");
    request["endDate"]=target.getAttribute("endDate");
    request["cno"]=username;

    $.ajax({
        url: "/reserve/cancel",
        type: 'POST',
        dataType: 'text', // 돌려받을 타입, text로 받으면 JSON.parse로 해결한다.
        contentType: "application/json", // 전해줄 타입
        data: JSON.stringify(request),
        async : true,
        success: function(data){
            e.target.removeEventListener("click",cancel);
            box_reserveZone.removeChild(e.target.parentNode);
            search(); // refresh
        }
    });
}


function update_RentalList(){
    $.ajax({
        url: "/rent/save",
        type: 'POST',
        dataType: 'text', // 돌려받을 타입, text로 받으면 JSON.parse로 해결한다.
        contentType: "application/json", // 전해줄 타입
        async : true,
        success: function(){
            console.log("RentalList Updated");
        }
    });
}

function print_rentalListAll(){
    $.ajax({
        url: "/rent/readAll",
        type: 'GET',
        dataType: 'text', // 돌려받을 타입, text로 받으면 JSON.parse로 해결한다.
        contentType: "application/json", // 전해줄 타입
        async : true,
        success: function(data){
            box_returnZone.innerHTML="";
            const rentalList=JSON.parse(data);
            rentalList.forEach(rentCar => print_rentalList(rentCar))
        }
    });
}

function print_rentalList(rentalJSON){
    const myElement = $(
        `<span style="display: inline-block; border: 1px solid black; margin: 5px">
                <p>차량번호: ${rentalJSON.licensePlateNo}</p>
                <p>모델명: ${rentalJSON.modelName}</p>
                <p>대여 시작일 : ${rentalJSON.dateRented}</p>
                <p>반납 예정일: ${rentalJSON.dateDue}</p>
                <p>예상 결제 금액: ${rentalJSON.expectedPayment}</p>
                <p>옵션: ...</p>
                <button id="${rentalJSON.rentCar.licensePlateNo}/Rent" startDate=${rentalJSON.startDate} endDate=${rentalJSON.endDate}>반납 및 결제하기</button>
            </span>`
    );
    box_returnZone.appendChild(myElement[0]);
    document.getElementById(rentalJSON.licensePlateNo+"/Rent")
        .addEventListener("click",update_previousRentalList);
}

function update_previousRentalList(e){
    let request={};
    request["licensePlateNo"]=e.id;

    $.ajax({
        url: "/rent/return",
        type: 'POST',
        dataType: 'text', // 돌려받을 타입, text로 받으면 JSON.parse로 해결한다.
        contentType: "application/json", // 전해줄 타입
        data: JSON.stringify(request),
        async : true,
        success: function(data){
            e.target.removeEventListener("click",update_previousRentalList);
            box_returnZone.removeChild(e.target.parentNode);
        }
    });
}

function print_previousRentalListAll(){
    $.ajax({
        url: "/previous/readAll",
        type: 'POST',
        dataType: 'text', // 돌려받을 타입, text로 받으면 JSON.parse로 해결한다.
        contentType: "application/json", // 전해줄 타입
        async : true,
        success: function(){
            console.log("RentalList Updated");
        }
    });
}

function print_reservationListAll(){
    $.ajax({
        url: "/reserve/readAll",
        type: 'GET',
        dataType: 'text', // 돌려받을 타입, text로 받으면 JSON.parse로 해결한다.
        contentType: "application/json", // 전해줄 타입
        async : true,
        success: function(data){
            box_reserveZone.innerHTML="";
            const dataArray=JSON.parse(data);
            dataArray.forEach(data => print_reservationList(data))
        }
    });
}


function print_reservationList(reservationJSON){
        const myElement = $(
            `<span style="display: inline-block; border: 1px solid black; margin: 5px">
                <p>차량번호: ${reservationJSON.rentCar.licensePlateNo}</p>
                <p>모델명: ${reservationJSON.rentCar.carModel.modelName}</p>
                <p>차종: ${reservationJSON.rentCar.carModel.vehicleType}</p>
                <p>대여 시작일 : ${reservationJSON.startDate}</p>
                <p>반납 예정일: ${reservationJSON.endDate}</p>
                <p>예약일: ${reservationJSON.reserveDate}</p>
                <button id="${reservationJSON.rentCar.licensePlateNo}/R" startDate=${reservationJSON.startDate} endDate=${reservationJSON.endDate}>취소하기</button>
            </span>`
        );
        box_reserveZone.appendChild(myElement[0]);
        document.getElementById(reservationJSON.licensePlateNo+"/R")
        .addEventListener("click",cancel);
}

function print_availableRentCar(rentCarsJSON,startDate,endDate){
    rentCarsJSON.forEach(data => {
        const myElement = $(
            `<span style="display: inline-block; border: 1px solid black; margin: 5px">
                <p>차량번호: ${data.licensePlateNo}</p>
                <p>모델명: ${data.carModel.modelName}</p>
                <p>차종: ${data.carModel.vehicleType}</p>
                <p>좌석 수: ${data.carModel.numberOfSeats}</p>
                <p>사용연료: ${data.carModel.fuel}</p>
                <p>일당 결제 금액: ${data.carModel.rentRatePerDay}</p>
                <p>옵션: ...</p>
                <button id="${data.licensePlateNo}" startDate=${startDate} endDate=${endDate}>예약하기</button>
            </span>`
        );
        box_printZone.appendChild(myElement[0]);
        const addedButton=document.getElementById(data.licensePlateNo);
        addedButton.addEventListener("click",reserve);
    });
}
function session_check(event){
    $.ajax({
        url: "/session_check",
        type: 'POST',
        async : true,
        headers: {
            "X-Requested-With": "XMLHttpRequest"
        },
        success: function(session){
            console.log(session)
            if(session.cno!==null) {
                login_zone.innerHTML = "로그인 정보: " + session.cno + ", sessionId: " + session.sessionId;
                username = session.cno;
                return true;
            }
        }
    });

    return false;
}

function load(){
    if(session_check()) {
        print_reservationListAll();
        update_RentalList();
        print_rentalListAll();
    }
}

login_button.addEventListener("click", login);
register_button.addEventListener("click", register_popup);
cancle_button.addEventListener("click",register_exit);
button_search.addEventListener("click",search);
document.addEventListener("DOMContentLoaded", load);